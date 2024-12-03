> 博客：https://blog.csdn.net/m0_69519887/article/details/141071290

## 一、什么是ThreadLocal

>在Java中，ThreadLocal 类提供了一种方式，使得每个线程可以独立地持有自己的变量副本，而不是共享变量。这可以避免线程间的同步问题，因为每个线程只能访问自己的ThreadLocal变量。通过ThreadLocal为线程添加的值只能由这个线程访问到，其他的线程无法访问，因此就避免了多线程之间的同步问题

提供了四个方法：

| 方法声明                   | 描述                         |
| -------------------------- | ---------------------------- |
| protected T initialValue() | 返回当前线程局部变量的初始值 |
| public void set(T value)   | 设置当前线程绑定的局部变量   |
| public T get()             | 获取当前线程绑定的局部变量   |
| public void remove()       | 移除当前线程绑定的局部变量   |

使用ThreadLocal时，通常需要实现以下步骤：

- 初始化：创建ThreadLocal变量。

  ```java
  private static ThreadLocal<T> threadLocal = new ThreadLocal<>();
  ```

- 设置值：使用set(T value)方法为当前线程设置值。

  ```java
  threadLocal.set(value);
  ```

- 获取值：使用get()方法获取当前线程的值。

  ```java
  T value = threadLocal.get();
  ```

- 移除值：使用remove()方法在线程结束时清除ThreadLocal变量，以避免内存泄漏。

  ```java
  threadLocal.remove();
  ```

在下面这个示例中，在主线程中存储了一个整形的10，新建一个线程后去取这个值是取不到的，因为该值只属于主线程，故输出为null

```java
public class ThreadLocalExample {
    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
    public static void main(String[] args) {
        // 设置线程局部变量的值
        threadLocal.set(10);
        // 这个值在其他线程中是取不到获取的
        new Thread(() -> {
            Integer value = threadLocal.get();//null
            System.out.println("Thread value: " + value);
        }).start();
    }
}
```

## 二、ThreadLocal的内部结构

> 如果我们不去看源代码的话，可能会猜测 ThreadLocal是这样子设计的：每个ThreadLocal 都创建一个
>
> Map，然后用线程作为 Map 的key ，要存储的局部变量作为Map 的value，这样就能达到各个线程的局部变量
>
> 隔离的效果。这是最简单的设计方法，JDK最早期的ThreadLocal 确实是这样设计的（如下图），JDK8后就不是了

早期的JDK中都是由ThreadLocal来维护这样的一个Map，里面的key则是Thread，就像下图这样

![](https://i-blog.csdnimg.cn/direct/c71523ad6daf44868294ed13960ee923.png)

```java
private static final ThreadLocal<String> count = new ThreadLocal<>();;
    private static final ThreadLocal<String> money = new ThreadLocal<>();;

//如果代码写了上面两行，按上面的设计，就是count、money 两个ThreadLocal都维护一个ThreadLocalMap，然后里面key则是Thread，value是值

//按下面的设计，就是每个Thread里都维护一个ThreadLocalMap，key则是count、money 两个，value是值
```



> 在JDK8之后每一个线程都会维护一个ThreadLocalMap，这个Map是一个哈希散列结构，如下图所示，每一个元素（Entry）都是一个键值对，key为ThreadLocal，Value为存储的数据，也就是set()方法存储的内容。

![](https://i-blog.csdnimg.cn/direct/7576248ab3d04fcb93ba0304fb80de2d.png)

> JDK8之后设计的好处是：
>
> 1.每个Map存储的Entry数量变少，减少了hash冲突
>
> 2.当Thread销毁的时候，ThreadLocalMap也会随之销毁，减少内存的占用
>
> 
>
> Thread线程数一般往往是大于ThreadLocal的，那么当线程销毁的时候对比俩个方案，JDK8的方案则可以节省更多的内存空间（只需要将对应的ThreadLocalMap删除），JDK8之前的方案由于Thread只是Map的一个节点的key，将其释放掉就会导致这块Map的空间利用率很低。

## 三、ThreadLocal带来的内存泄露问题

> 视频：https://www.bilibili.com/video/BV1N741127FH?spm_id_from=333.788.player.switch&vd_source=dc02a4c6e2a8e915fb8ee431999e5b2c&p=10

首先是内存泄漏的概念：

- 内存溢出：没有足够的内存供申请者使用
- 内存泄漏：程序中已经动态分配的内存由于某种原因未释放或无法释放，造成系统内存的浪费，导致程序运行速度减慢甚至崩溃。此外内存泄漏的堆积最终也会导致内存溢出。

下图是ThreadLocal相关的内存结构图，在栈区中有threadLocal对象和当前线程对象，分别指向堆区真正存储的类对象，这俩个指向都是强引用。在堆区中当前线程肯定是只有自己的Map的信息的，而Map中又存储着一个个的Entry节点；在Entry节点中每一个Key都是ThreadLocal的实例，同时Value又指向了真正的存储的数据位置，以上便是下图的引用关系。

![](https://i-blog.csdnimg.cn/direct/5bfb6d7b0c7344ae93b363d2ba2fd824.png)

那么所谓的内存泄漏，其实就是指的Entry这块内存不能正确释放



强弱引用的概念：

- 强引用(StrongReference)：就是我们最常见的普通对象引用，只要还有强引用指向一个对象，就能表明对象还“活着”，垃圾回收器就不会回收这种对象。
- 弱引用(WeakReference)：垃圾回收器一旦发现了只具有弱引用的对象，不管当前内存空间足够与否，都会回收它的内存。 

### key强引用

我们可以按照强弱引用来分别推算一下，首先是强引用的情况

当我们在业务代码中使用完ThreadLocal，在栈区指向堆区的这个指向关系就会被回收掉了，但是由于Key是强引用指向ThreadLocal，故而堆区中的ThreadLocal无法被回收，此时的Key指向ThreadLocal，另外由于当前线程还没有结束，则下面那条强引用指向关系任然存在。故为下图的关系状态

![](https://i-blog.csdnimg.cn/direct/9491d7d3e0b14d90b23cbde36e8cde29.png)

在这样的情况下，由于栈上的指向已经消失了，我们无法访问到堆上的ThreadLocal，故而无法访问到Entry，但是Entry又有Map指向它，故而无法进行回收。那么此时的Entry即无法访问也无法回收，这就造成了Entry的内存溢出。

***

### key弱引用

其次是弱引用的情况，当我们在业务代码中使用完ThreadLocal就通过垃圾回收（GC）进行了回收，那么由于Key是弱引用，Key此时就指向null，但是由于当前线程还没有结束，则下面那条强引用指向关系任然存在

![](https://i-blog.csdnimg.cn/direct/5f5159528b5440599645bfa73965b382.png)

在这样的情况下，Entry由于仍然有Map指向它所以不会被GC回收掉，但是此时的Key又为null，所以我们无法访问到这个Value。这就导致了这个Value我们即不能访问到也不能进行回收，此时就造成了Value的内存泄漏。

### 总结

通过以上分析，我们得知了不管Entry中的Key是否为弱引用，都会造成内存泄漏的情况，只不过强引用下是Entry的内存泄漏，弱引用下是Value的内存泄漏。造成这样内存泄漏的情况都有这样的共同特性：

- 都没有手动删除Entry
- 当前线程都在运行

也就是说，只要我们在使用完ThreadLocal后，调用其remove()方法删除对应的Entry就可以避免内存泄漏的问题。

并且由于ThreadLoaclMap是Thread的一个属性，故而它的生命周期和线程一样，那么当线程的生命周期结束，自然也就没有Map指向Entry，这也就在根源上解决了问题。

> 综上所述，造成ThreadLoacl内存泄漏的根本原因是：**由于ThreadLoaclMap的生命周期和Thread一样长，如果没有手动删除对应的Key就会导致内存泄漏。**

***

### 抛出疑问

> 为什么使用弱引用?

根据上面的分析，我们知道了：无论ThreadLocalMap中的key使用哪种类型引用都无法完全避免内存泄漏，跟使用弱引用没有关系。

要避免内存泄漏有两种方式：

1. 使用完ThreadLocal，调用其remove方法删除对应的Entry
2. 使用完ThreadLocal，当前Thread也随之运行结束

相对第一种方式，第二种方式显然更不好控制，特别是使用线程池的时候，线程结束是不会销毀的。

> 也就是说，只要记得在使用完ThreadLocal及时的调用remove，无论key是强引用还是弱引用都不会有问题。那么为什么key要用弱引用呢？

事实上，在ThreadLocalMap中的get/getEntry方法中，会对key为null（也即是ThreadLocal为null）进行判断，如果为null的话，那么是会对value置为null的。

这就意味着使用完ThreadLocal , CurrentThread依然运行的前提下，就算忘记调用remove方法，**弱引用比强引用可以多一层保障**：弱引用的ThreadLocal会被回收，对应的value在下一次ThreadLocalMap调用set,get,remove中的任一方法的时候会被清除，从而避免内存泄漏。

***

## 四、ThreadLocal与synchronized的区别

> 虽然ThreadLocal模式 与synchronized关键字都用于处理多线程并发访问变量的问题，不过两者处理问题的角度和思路不同

|        | synchronized                                                 | ThreadLocal                                                  |
| ------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 原理   | 同步机制采用以时间换空间的方式，只提供了一份变量,让不同的线程排队访问 | ThreadLocal采用以空间换时间 的方式，为每一个线程都提供了一份变量的副本,从而实现同时访问而相不干扰 |
| 侧重点 | 多个线程之间访问资源的同步                                   | 多线程中让每个线程之间的数据相互隔离                         |

> 总结：在刚刚的案例中，虽然使用ThreadLocal和synchronized都能解决问题，但是使用ThreadLocal更为合适,因为这样可以使程序拥有更高的并发性
