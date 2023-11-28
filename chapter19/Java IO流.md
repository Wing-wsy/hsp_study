#### 一、什么是文件

##### 1、文件

文件是**保存数据的地方**，比如可以保存文字，图片，视频，音频等。

##### 2、文件流

文件在程序中是以流的形式来操作的

```ba
java 程序(内存)  <------------  文件(磁盘)
                ------------>
【文件到程序：输入流】               
【程序到文件：输出流】
```

##### 3、常用的文件操作

创建文件对象相关构造器和方法

```java
new File(String pathName)            // 根据路径构造一个File对象
new File(File parent,String child)   // 根据父目录文件 + 子路径构建
new File(String parent,String child) // 根据父目录 + 子路径构建
```

##### 4、对文件的简单操作

> 案例：使用三种方式创建三个文件 new1.txt、new2.txt、new3.txt等其他操作（见代码）

#### 二、IO流原理及分类

##### 1、java IO流原理

```markdown
1、IO是Input\Output的缩写，IO技术是非常实用的技术，用于处理数据传输，如读写文件，网络通讯等
2、Java程序中，对于数据的输入\输出操作以“流（stream）”的方式进行
3、java.io包下提供了各种“流”类和接口，用以获取不同种类的数据，并通过方法输入和输出数据
```

##### 2、流的分类

- 按操作数据单位不同分为：**字节流（8bit）**和**字符流（按字符）**
- 按数据流的流向不同分为：**输入流、输出流**
- 按流的角色不同分为：**节点流、处理流（包装流）**

```markdown
问：那一个字符对于几个字节呢？
答：这个根据不同的编码有所不同。

问：那什么时候使用字节流，什么时候使用字符流呢？
答：比如声音、视频等使用的二进制传输的，所以使用字节流（二进制使用字节流），这样保证文件无损操作，不丢失。
而字符流在文本文件使用比较好，因为文本文件是按照字符来组织的。
```

> 下面四个顶级类都是抽象类，只能使用他们的子类

| 抽象基类 | 字节流       | 字符流 |
| -------- | ------------ | ------ |
| 输入流   | InputStream  | Reader |
| 输出流   | OutputStream | Writer |

1. Java 的IO流共涉及40多个类，实际上非常规则，都是从如上4个基类派生的
2. 由这四个基类派生出来的字类名称都是以其父类名作为子类名后缀

#### 三、IO流体系图-常用的类

##### 1、IO流体系图

<img src="https://pic4.zhimg.com/80/v2-47632a05f7acfe749fc272da8dbae9ab_1440w.webp" style="zoom:67%;" />



##### 2、**FileInputStream**使用

（见代码com.hspedu.inputstream_.FileInputStream_）

##### 3、**FileOutputStream**使用

（见代码com.hspedu.outputstream_.FileOutputStream01）

##### 4、FileReader使用

（见代码com.hspedu.reader_.FileReader_）

##### 5、FileWriter使用

（见代码com.hspedu.writer_.FileWriter_）

#### 四、**节点流和处理流**

##### 1、基本介绍

- **节点流**可以从一个特定的数据源读写数据，如FileReader、FileWriter
- **处理流(也叫包装流)**是“连接”在已存在的流（节点流或处理流)之上，为程序提供更为强大的读写功能，也更加灵活,如BufferedReader、BufferedWriter

##### 2、**节点流和处理流一览图**

![](https://pic3.zhimg.com/80/v2-a620a89541306399ddcd5dca0bfca9b2_1440w.webp)



##### 3、**节点流和处理流的区别和联系**

1. 节点流是底层流/低级流,直接跟数据源相接。
2. 处理流(**包装流**)包装节点流，既可以消除不同节点流的实现差异，也可以提供更方 便的方法来完成输入输出。
3. 处理流(也叫包装流)对节点流进行包装，使用了修饰器设计模式，**不会直接与数据源相连[模拟修饰器设计模式]**

##### 4、**处理流的功能**

1. 性能的提高:主要以增加缓冲的方式来提高输入输出的效率。
2. 操作的便捷:处理流可能提供了一系列便捷的方法来一次输入输出大批量的数据,使用更加灵活方便

##### 5、使用简单类比代码模拟 处理流是怎么包装节点流，使得处理流更加强大的

 （见代码com.hspedu.test.Test_）

##### 6、**处理流 BufferedReader 和BufferedWriter**

BufferedReader 和 BufferedWriter属于字符流，是按照字符来读取数据的，关闭时处理流，只需要关闭外层流即可（因为真正工作的是内层流，关闭时只需要关闭外层处理流，会自动关闭内层流）

- BufferedReader使用（见代码com.hspedu.reader_.BufferedReader_）
- BufferedWriter使用（见代码com.hspedu.writer_.BufferedWriter_）

##### 7、处理流拷贝文件案例

（见代码com.hspedu.reader_.BufferedCopy_）

##### 8、**处理流BufferedInputStream 和BufferedOutputStream**

BufferedInputStream是字节流在创建BufferedInputStream时，会创建一个内部缓冲区数组。

BufferedOutputStream是字节流, 实现缓冲的输出流, 可以将多个字节写入底层输出流中，而不必对每次字节写入调用底层系统。

（见代码com.hspedu.outputstream_.BufferedCopy02）

##### 9、**对象流ObjectInputStream 和ObjectOutputStream**

看一个需求：不仅需要保存值，还需要保存数据类型。

1. 将int num = 100这个 int数据保存到文件中,注意不是100 数字，而是int 100，并且，能够从文件中直接恢复int 100
2. 将Dog dog = new Dog(“小黄”，3)这个 dog对象保存到文件中，并且能够从文件恢复.
3. 上面的要求，就是能够将基本数据类型或者对象进行序列化和反序列化操作



> 序列化和反序列化

1. 序列化就是在保存数据时，保存数据的值和数据类型
2. 反序列化就是在恢复数据时，恢复数据的值和数据类型
3. 需要让某个对象支持序列化机制，则必须让其类是可序列化的，为了让某个类是可序列化的，该类必须实现如下两个接口之一:

- Serializable //这是一个标记接口,没有方法
- Externalizable //该接口有方法需要实现，因此我们一般实现上面的接口



##### 10、**对象流介绍**

功能：提供了对基本类型或对象类型的序列化和反序列化的方法

- ObjectOutputStream 提供序列化功能
- ObjectInputStream 提供反序列化功能

##### 11、**应用案例**

使用`ObjectOutputStream`序列化基本数据类型和一个Dog对象(name, age),并 保存到data.dat文件中。

（见代码com.hspedu.outputstream_.ObjectOutStream_）

（见代码com.hspedu.inputstream_.ObjectInputStream_）

> 注意事项和细节说明

1. 读写顺序要一致。
2. 要求序列化或反序列化对象，需要实现`Serializable`。
3. 序列化的类中建议添加`SerialVersionUID`，为了提高版本的兼容性。当加入新属性时，序列化和反序列化会认为是原来的修改版，而不会认为是一个全新的类。
   private static final long serialVersionUID = 1L;
4. 序列化对象时，默认将里面所有属性都进行序列化，但除了static或transient修饰的成员。也就是序列化并不保存`static`或`transient`修饰的信息。
5. 序列化对象时，**要求里面属性的类型也需要实现序列化接口**。（Master是Dog里面的属性，Master也要实现序列化接口）
6. 序列化具备可继承性，也就是如果某类已经实现了序列化，则它的所有子类也已经默认实现了序列化。

##### 12、**标准输入输出流**
传统方法System.out.println("");是使用out 对象将数据输出到显示器。

传统的方法, Scanner是从标准输入键盘接收数据（见代码com.hspedu.standard.InputAndOutput）

##### 13、**转换流InputStreamReader 和OutputStreamWriter**
文件乱码问题，引出学习转换流必要性。

可以这么理解：相当于把InputStream转为Reader，把OutputStream转为Writer

（见代码com.hspedu.transformation.InputStreamReader_ 和 com.hspedu.transformation.OutputStreamWriter_）

```markdown
1. InputStreamReader:Reader的子类，可以将InputStream(字节流)包装成(转换)Reader(字符流)
2. OutputStreamWriter:Writer的子类，实现将OutputStream(字节流) 包装成Writer(字符流)
3. 当处理纯文本数据时，如果使用字符流效率更高，并且可以有效解决中文 问题,所以建议将字节流转换成字符流。
4. 可以在使用时指定编码格式(比如utf-8, gbk , gb2312,ISO8859-1等)

转换流也是一种处理流，它提供了字节流和字符流之间的转换。
在Java IO流中提供了两个转换流：InputStreamReader 和 OutputStreamWriter，
这两个类都属于字符流。其中InputStreamReader将字节输入流转为字符输入流，继承自Reader。
OutputStreamWriter是将字符输出流转为字节输出流，继承自Writer。
```

##### 14、转换流案例

将UTF-8编码的文本文件，转换为GBK编码的文本文件。

  1、指定UTF-8编码的转换流，读取文本文件。

  2、使用GBK编码的转换流，写出文本文件

![](https://img2018.cnblogs.com/blog/1745215/202002/1745215-20200228133235379-1910140449.png)

（见代码com.hspedu.transformation.ConversionStreamTest）

##### 15、**打印流PrintStream 和PrintWriter**

打印流只有输出流，没有输入流

（见代码com.hspedu.printstream.PrintStream_）















