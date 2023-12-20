### 1 数组与集合的区别

#### 1.1 数组

1. 数组长度开始时必须指定，而且一旦指定，不能更改
2. 保存的必须为同一类型（基本类型/引用类型）的元素
3. 使用数组进行增加/删除元素的代码比较复杂

> 数组扩容，不灵活，比较麻烦，实例如下:

`（见代码 com.hspedu.collection_.ArrayExample ）`

#### 1.2 集合

1. 集合不仅可以用来存储不同类型（不加泛型时）不同数量的对象，还可以保存具有映射关系的数据
2. 集合是可以动态扩展容量，可以根据需要动态改变大小
3. 集合提供了更多的成员方法，能满足更多的需求

### 2 Java集合

#### 2.1 Java集合框架体系

![](https://img-blog.csdnimg.cn/img_convert/cc0fa9e08b38fa11e8850a1c0d05b09c.png)

> 类型简单介绍

- `List`: 存储的元素是**有序**的、**可重复**的
- `Set`: 存储的元素是**无序**的、**不可重复**的
- `Queue`: 按特定的排队规则来确定先后顺序，存储的元素是**有序**的、**可重复**的
- `Map`: 使用键值对（key-value）存储，key 是无序的、不可重复的，value 是无序的、可重复的，每个键最多映射到一个值

> 简单总结

集合主要是分了两组（`单列集合`和`双列集合`），单列集合表明在集合里放的是单个元素，双列集合往往是键值对形式（key-value）

#### 2.2 Collection

```ceylon
Collection 是集合类中的单列集合。他是所有单列集合的父接口。Collection的子接口有List接口和Set接口。这意味着所有的单列集合类都是继承了这个接口。它定义了一些集合共有的基本方法，如添加、删除、查询和遍历元素等方法
```

`演示示例见代码：com.hspedu.collection_.CollectionMethod`

> Iterator迭代器

1. Iterator对象称为迭代器，用于遍历Collection集合中的元素
2. 所有实现了Collection接口的集合类都有一个Iterator()方法，用以返回一个实现了Iterator接口的对象，即可以返回一个迭代器
3. Iterator仅用于遍历集合，Iterator本身并不存放对象

`迭代器使用见代码：com.hspedu.collection_.CollectionIterator `

> for循环增强遍历

增强for循环，可以替代itrator迭代器，特点:增强for就是简化版的iterator，本质一样。只能用于遍历集合或者数组

##### 2.2.1 List

```ceylon
List 接口是 Collection 接口的子接口，常用的List实现类有 ArrayList、Vector、LinkedList
```

- List集合类中元素有序(即添加顺序和取出顺序一致)、且可重复
- List集合中的每个元素都有其对应的顺序索引，即支持索引
- List集合可以添加任意元素，**包括null**，并且**可以添加多个**

> List常用方法

```java
add() // 在index位置插入元素
addAll() // 从index位置开始将所有元素添加进来
get() // 获取指定index位置的元素
indexOf() // 返回在集合中首次出现的位置
lastIndexOf() // 返回在当前集合中末次出现的位置
remove() // 移除index位置的元素，并返回此元素
set() // 设置指定index位置的元素(替换)
subList() // 返回一个范围位置中的子集合
```

`List接口和常用方法使用见代码：com.hspedu.list_.List_ `

`List的三种遍历方式见代码：com.hspedu.list_.ListFor`

###### 2.2.1.1 ArrayList

> （一）ArrayList的底层实现

ArrayList底层维护了一个**Object类型的数组**，所以ArrayList里面可以存放**任意类型**的元素，**transient表示该属性不会被序列化**

**size变量**用来保存当前数组中已经添加了多少元素

![](picture/img02.png)

> （二）ArrayList的扩容机制（面试题）

当创建ArrayList对象时，如果使用的是**无参构造器**，则初始elementData**容量为0**

**第1次**添加，则扩容**elementData为10**，















> 底层分析结论

1. ArrayList是由`数组`来实现数据存储的
2. ArrayList中维护了一个Object类型的`数组elementData`
3. 依上所以ArrayList可以存放任意类型的元素甚至是`空元素`，可以放入多个空值
4. transient Object[] elementData  //transient关键字表示该属性不会被序列化
5. 当创建ArrayList对象时，如果使用的是无参构造器，则初始elementData容量是0，第1次添加，则扩容elementData为10,如需再次扩容，则扩容elementData为1.5倍
6. 如果使用的是指定大小的构造器，则初始elementData容量为指定大小，如果需要扩容，则直接扩容elementData为1.5倍
7. ArrayList基本等同于Vector，ArrayList是线程不安全(执行效率高)，在`多线程`下，不建议用ArrayList

###### 2.2.1.2 Vector

###### 2.2.1.3 LinkedList

##### 2.2.2 Set

```ceylon

```

###### 2.2.2.1 HashSet

###### 2.2.2.2 LinkedHashSet

###### 2.2.2.3 TreeSet

#### 2.3 Map

##### 2.3.1 HashMap

##### 2.3.2 LinkedHashMap

##### 2.3.3 HashTable

##### 2.3.4 TreeMap



#### 三 、List接口和常用方法

##### 4 Vector底层机构和源码剖析

> Vector的基本介绍

- Vector底层也是一个对象数组，protected Object[] elementData
- Vector 是线程同步的，即`线程安全`，Vector类的操作方法带有synchronized
- 在开发中，需要线程同步安全时，优先考虑用Vector

> Vector和List比较

![](https://img-blog.csdnimg.cn/4e1fd49d8b714efd92e7c9ad047c5b15.png#pic_center)

##### 5 LinkList底层结构

- LinkedList实现了**双向链表**和**双端队列**特点
- 可以添加任意元素(元素可以重复)，包括null
- `线程不安全`，没有实现同步

##### 6 LinkList底层操作机制

- LinkedList底层维护了一`双向链表`
- LinkedList中维护了两个属性`first`和`last`分别指向`首节点`和`尾节点`
- 每个节点(Node对象)，里面又维护了`prev`,`next`,`item`三个属性，其中通过prev指向前一个，通过next指向后一个节点 ，最终实现双向链表。
- 所以LinkedList的元素的添加和删除，不是通过数组完成的，相对来说效率较高

> 可以阅读源码比较简单

![](https://img-blog.csdnimg.cn/45cfdd218988441ea8005b8686cba1f2.png#pic_center)

##### 7 LinkLis和ArrayList的区别

![](https://img-blog.csdnimg.cn/20523a0adc4d4fbdaea985e5ee16a3b6.png#pic_center)

#### 四 、Set接口和常用方法

- 无序(添加和取出的顺序不一致)，没有索引
- 不允许元素重复，所以最多包含一个null
- JDK API中Set接口的实现类有:

```markdown
重要结论：虽然添加时的顺序，跟后面遍历的顺序不一致，但是每次后面遍历出来的顺序都是一样的，比如：添加进去[A,B,C,D]，后面每次遍历出来的都是固定的，比如：[C,A,B,D]
```

##### 1 Set接口的遍历方式

同Collection的遍历方式一样，因为Set接口是Collection接口的子接口。

- 可以使用迭代器
- 增强for循环
- **不能使用索引的方式来获取**

`见代码 com.hspedu.set_.SetMethod`

##### 2 HashSet的全面说明

- HashSet实现了Set接口
- HashSet实际上是HashMap(因为底层就是HashMap)
- 可以存放null值，但是只能有一个null
- HashSet不保证元素是有序的，取决于hash后，再确定索引的结果(不保证元素存放和取出顺序一致)
- 不能有重复元素/对象。在前面Set接口使用已经讲过了

> 分析:HashSet底层是HashMap，HashMap底层是(数组+单向链表+红黑树)
> 浓缩成6句话

- HashSet底层是HashMap
- 添加一个元素时，先得到hash值，会转成索引值
- 找到存储数据表table，看这个索引位置是否已经存放的有元素
- 如果没有，直接加入
- 如果有，调用equals比较，如果相同，就放弃添加，如果不相同，则添加到最后
- 在JAVA 8 中，如果一条链表的元素个数，到达TREEIFY_THRESHOLD(默认是8)，并且table的大小>= MIN_TREEIFY_CAPACITY(默认64)就进行树化(红黑树)

> 源码分析
>
> 没写完，可看视频，https://www.bilibili.com/video/BV1YA411T76k/?p=27&spm_id_from=pageDriver&vd_source=dc02a4c6e2a8e915fb8ee431999e5b2c

```java
// 开始分析这两个语句
    /* public HashSet() {
        map = new HashMap<>();
    }*/
// 这里创建了一个 HashMap
HashSet hashSet = new HashSet();   
// add方法最复杂，开始逐步分析
hashSet.add("java");

///////////////////////////////下面是对add方法的具体分析///////////////////////////////

/** 第一步:add进去实际调用的是上面new HashSet()创建出来的 HashMap 集合调用了个put方法，PRESENT没作用，用于占位，共享的 */
 public boolean add(E e) {
     return map.put(e, PRESENT)==null;  // HashMap类的put成功就会返回null，如果存在则返回当前元素
 }

/** 第二步:进去put方法，继续调用了putVal方法 */
 public V put(K key, V value) {
     return putVal(hash(key), key, value, false, true);
 }

/** 第三步:调用putVal方法前，先执行hash方法,该方法调用了 hashCode方法（Object类有该方法）,并做了算法，目的是让最终返回值更加零散 */
 static final int hash(Object key) {
     int h;
     return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
 }

/** 第四步:进去putVal方法（hash是第三步计算的值【相同的key计算出来的值是一样的】）,key是添加的值 */

///////////////////////////////对putVal方法的具体分析-开始///////////////////////////////
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        // 这些是辅助变量
        Node<K,V>[] tab; Node<K,V> p; int n, i;           
        // 这个table是HashMap里的数组 Node<K,V>[] table;Node就是存放数据的，可参考演示类HashSetStructure里的Node
        if ((tab = table) == null || (n = tab.length) == 0)     
            // 第一次添加table=null会走到这里，执行resize方法，resize方法逻辑很复杂，请看下面resize方法的单独分析
            // 第一次添加时，resize会返回一个长度16的数组给tab（第一次扩容16个空间），这样tab的数组长度为 16，n = 16
            n = (tab = resize()).length;
        // 这里根据hash和n的值就能知道当前元素要保存到哪个索引里
        // 当然，保存前会先根据索引去获取tab数组的值，如果该索引没值，那就直接存进去，如果有值则执行下面的else
        // 第一次该索引tab[i]没值，直接存，不走else
        if ((p = tab[i = (n - 1) & hash]) == null)
            tab[i] = newNode(hash, key, value, null);
        else {
            Node<K,V> e; K k;
            if (p.hash == hash &&
                ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
            else if (p instanceof TreeNode)
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
            else {
                for (int binCount = 0; ; ++binCount) {
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, hash);
                        break;
                    }
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                afterNodeAccess(e);
                return oldValue;
            }
        }
        //记录添加的数量
        ++modCount;
        //判断当前元素是否超过了扩容阈值（第一次计算出来得12）是的话扩容，可以看resize方法里是会对threshold赋值的
        if (++size > threshold)
            resize();
        //这个方法是HashMap留给他的实现子类来实现的，类似于钩子函数，在这里是没有执行逻辑的
        afterNodeInsertion(evict);
        // 到这说明添加成功，这里返回了null，所以为什么put成功后就会返回了空
        return null;
    }

///////////////////////////////对putVal方法的具体分析-结束///////////////////////////////

///////////////////////////////对resize方法的具体分析-开始///////////////////////////////

  final Node<K,V>[] resize() {
        // 第一次添加时，table = null
        Node<K,V>[] oldTab = table;
        // oldCap 保存当前table的长度，如果第一次添加table = null，oldCap = 0
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        // threshold：要调整大小的下一个大小值，第一次为0
        int oldThr = threshold;
        int newCap, newThr = 0;
        if (oldCap > 0) {
            if (oldCap >= MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return oldTab;
            }
            else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                     oldCap >= DEFAULT_INITIAL_CAPACITY)
                newThr = oldThr << 1; // double threshold
        }
        else if (oldThr > 0) 
            newCap = oldThr;
        else {               
            //第一次添加时，先赋值默认值 DEFAULT_INITIAL_CAPACITY = 16
            newCap = DEFAULT_INITIAL_CAPACITY;
            // 加载因子DEFAULT_LOAD_FACTOR = 0.75
            // newThr扩容阈值 第一次计算出来得 12 = 0.75 * 16，就是说第一次数组长度为 16，如果数组用了 12 个了，就触发扩容
            // 那为什么不等用完了 16 再扩容呢？这是因为避免多线程时扩容导致阻塞，所以没用完前就开始扩容，这样更保险 
            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
        if (newThr == 0) {
            float ft = (float)newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                      (int)ft : Integer.MAX_VALUE);
        }
        // 第一次走到这里 threshold = 12
        threshold = newThr;
        @SuppressWarnings({"rawtypes","unchecked"})
        Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
        table = newTab;
        if (oldTab != null) {
            for (int j = 0; j < oldCap; ++j) {
                Node<K,V> e;
                if ((e = oldTab[j]) != null) {
                    oldTab[j] = null;
                    if (e.next == null)
                        newTab[e.hash & (newCap - 1)] = e;
                    else if (e instanceof TreeNode)
                        ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                    else { // preserve order
                        Node<K,V> loHead = null, loTail = null;
                        Node<K,V> hiHead = null, hiTail = null;
                        Node<K,V> next;
                        do {
                            next = e.next;
                            if ((e.hash & oldCap) == 0) {
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            }
                            else {
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }
///////////////////////////////对resize方法的具体分析-结束///////////////////////////////
```

##### 课后练习

一、equals和hashCode示例1:

```
练习要求：
1、创建3个Workers放入HashSet 中
2、当name和age一样时就不能添加到HashSet
```

`示例1见代码：com.hspedu.set_.Employee `

二、equals和hashCode示例2:重写2次

```
练习要求：
1、Employ 里只要名字name和出生年份birthday相同就不能添加到HashSet
```

`示例1见代码：com.hspedu.set_.Employee02 `

三、equals和hashCode示例2:重写1次

```
练习要求：
1、Employ 里只要名字name和出生年份birthday相同就不能添加到HashSet
```

`示例1见代码：com.hspedu.set_.Employee03 `

##### 3 LinkedHashSet

> LinkedHashSet的全面说明

- LinkedHashSet是HashSet的子类
- LinkedHashSet底层是一个LinkedHashMap，底层维护了一个**数组+双向链表**
- LinkedHashSet根据元素的hashCode值来决定元素的存储位置，同时使用链表维护元素的次序图(图)，这使得元素看起来是以插入顺序保存的。
- LinkedHashSet不允许添加重复元素

> 说明

- 在LinkedHashSet中维护一个hash表和双向链表(LinkedHashSet 有head和tail)


- 每一个结点有before和after属性，这样可以形成双向链表


- 在添加一个元素时，先求hash值，再求索引，确定该元素在table的位置，然后将添加的元素加入到双向链表(如果已经存在，不添加[原则和hashset一样])

​       tail.next = newElement
​      newElement.pre = tail
​      tail = newElement;

- 这样的话，我们遍历LinkedHashSet 也能确保插入顺序和遍历顺序一致

![](/Users/wing/IdeaProjects/hsp_study/chapter14/picture/img01.png)



#### Collections 工具类

```ceylon
Collections 中提供了大量对集合元素进行排序、查询和修改等操作的方法，还提供了对集合对象设置不可变、对集合对象实现同步控制等方法。以下定义的方法既可用于操作 List集合，也可用于操作Set 和 Queue 
```

> 常用操作

```java
后续补充Collections工具类的常用方法
```























