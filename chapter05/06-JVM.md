# 前言

你是否也遇到过这些问题？

- 运行着的线上系统突然卡死，系统无法访问，甚至直接OOM
- 想解决线上JVM GC问题，但却无从下手
- 新项目上线，对各种JVY参数设置一脸茫然，直接默认吧，然后就 jj 了
- 每次面试之前都要重新背一遍JM的一些原理概念性的东西，然布面试官却经常问你在实际项目中如何调优JVN参数，如何解决GC、oON等问题，一脸懵逼

大部分Java开发人员，除会在项目中使用到与Java平台相关的各种高精尖技术，**对于Java技术的核心Java虛拟机了解甚少**

***

> 开发人员如何看待上层框架

- 一些有一定工作经验的开发人员，打心眼儿里觉得SSM、微服务等上层技术才是重点，基础技术并不重要，这其实是一种本末倒置的“病态〞

- 如果我们把核心类库的 API 比做数学公式的话，那么Java 虚拟机的知识就好比公式的推导过程

***

<img src="picture/img1.png" style="zoom:50%;" />

计算机系统体系对我们来说越来越远，在不了解底层实现方式的前提下，通过高级语言很容易编写程序代码，但事实上计算机并不认识高级语言。

***

> 垃圾回收

**垃圾收集机制** 为我们打理了很多繁琐的工作，大大提高了开发的效率，但是，垃圾收集也不是万能的，懂得JVM内部的内存结构、工作机制，是设计高扩展性应用和诊断运行时问题的基础，也是Java工程师进阶的必备能力

> Java生态圈

Java是目前应用最为广泛的软件开发平台之一。随着Java以及Java社区的不断壮大。

Java 也早己不再是简简单单的一门计算机语言了，它更是一个平台、一种文化、一个社区

- **作为一个半台**，Java虚拟机扮演着举足轻重的作用，Groovy、scala、 JRuby、Kotlin等都是Java平台的一部分
- **作为一种文化**，Java几乎成为了 “开源” 的代名词，第三方开源软件和框架。如Tomcat、struts, MyBatis, spring等，就连JDK和JVM自身也有不少开源的实现，如OpenJDK、Harmony
- **作为一个社区**，Java拥有全世界最多的技术拥护者和开源社区支持，有数不清的论坛和资料。从桌面应用软件、联入式开发到企业级应用、后台服务器、中间件，都可以看到Java的身影。其应用形式之复杂、参与人数之众多也令人咋舌。

***

# 1. 内存与垃圾回收篇

## 1.1 JVM与Java体系结构

> 字节码

- 我们平时说的java字节码，指的是用java语言编译成的字节码。准确的说任何能在jvm平台上执行的字节码格式都是一样的。所以应该统称为：jvm字节码。

- 不同的编译器，可以编译出相同的字节码文件，字节码文件也可以在不同的JVM上运行。

- Java 虚拟机与 Java 语言并没有必然的联系，它只与特定的二进制文件格式—Class文件格式所关联，Class 文件中包含了 Java 虛拟机指令集（或者称为宇节码、Bytecodes）和符号表，还有一些其他辅助信息。

### 1.1.1 Java及JVM简介

> Java :跨平台的语言

<img src="picture/img2.png" style="zoom:50%;" />

> JVM：跨语言的平台

<img src="picture/img3.png" style="zoom:50%;" />

**Java虚拟机**根本不关心运行在其内部的程序到底是使用何种编程语言编写的，它只关心“**字节码**”文件。也就是说Java虛拟机拥有语言无关性，并不会单纯地与Java语言“终身绑定”，只要其他编程语言的编译结果满足并包含 Java虛拟机的内部指令集、符号表以及其他的辅助信息，它就是一个有效的字节码文件，就能够被虚拟机所识别并装载运行

> 总结：Java不是最强大的语言，但是JVM是最强大的虛拟机

***

### 1.1.2 虚拟机与Java虚拟机

> 虚拟机

所谓虛拟机(Virtual Mpchine)，就是一台虚拟的计算机。它是一款软件，用来执行一系列虚拟计算机指令。大体上，虚拟机可以分为**系统虚拟机**和**程序虚拟机**

- 大名鼎鼎的visual Box,VMware就属于系统虛拟机，它们**完全是对物理计算机的仿真**，提供了一个可运行完整操作系统的软件平台
- 程序虚拟机的典型代表就是Java虛拟机，它**专门为执行单个计算机程 序而设计**，在Java虚拟机中执行的指令我们称为Java字节码指令

无论是系统虚拟机还是程序虚拟机，在上面运行的软件都被限制于虚拟机提供的资源中

***

> Java 虚拟机

- Java虚拟机是一台执行Java字节码的虚拟计算机，它拥有独立的运行机制，其运行的Java字节码也未必由Java语言编译而成
- JVM平台的各种语言可以共享Java虚拟机带来的跨平台性、优秀的垃圾回器，以及可靠的即时编译器
- **Java技术的核心就是Java虚拟机** (JVM, Java Virtual Machine )，因为所有的Java程序都运行在Java虛拟机内部

***

> 作用

Java虚拟机就是二进制字节码的运行环境，负责装载字节码到其内部，解释/编译为对应平台上的机器指令执行。每一条Java指令，Java虚拟机规范中都有详细定义，如怎么取操作数，拿么处理操作数，处理结果放在哪里

特点：

- 一次编译，到处运行
- 自动内存管理
- 自动垃圾回收功能

***

> JVM 位置

<img src="picture/img4.png" style="zoom:50%;" />

JVM是运行在操作系统之上的，它与硬件没有直接的交互。

***

### 1.1.3 JVM整体结构

- HotSpot VM是目前市面上高性能虚拟机的代表作之一
- 它采用解释器与即时编译器并存的架构

<img src="picture/img5.png" style="zoom:50%;" />

黄色：方法区和堆，是线程共享的；灰色是线程独有的。

***

### 1.1.4 Java代码执行流程

![](picture/img6.png)

***

### 1.1.5 JVM架构模型

Java编译器输入的指令流基本上是一种基子**栈的指令集架构**，另外一种指令集架构则

是基于**寄存器的指令集架构**

具体来说：这两种架构之间的区别：

- 基于栈式架构的特点

  - 设计和实现更简单，适用于资源受限的系统;
  - 避开了寄存器的分配难题：使用零地址指令方式分配
  - 指令流中的指令大部分是零地址指令，其执行过程依赖于操作栈。指令集更小，编译器容易实现
  - 不需要硬件文持，可移植性更好，更好实现跨平台

- 基于寄存器架构的特点

  - 典型的应用是x86的二进制指令集：比如传统的BC以及Android的Davlik虚

    拟机

  - 指令集架构则完全依赖硬件，可移植性差

  - 性能优秀和执行更高效

  - 花费更少的指令去完成一项操作

  - 在大部分情況下，基于寄存器架构的指令集往往都以一地址指令、二地址指令和三地址指令为主，市基于栈式架构的指令集却是以零地址指令为主

> 举例1

同样执行 2 + 3 这种逻辑操作，其指令分别如下：

> 基于栈的计算流程（指令集小，但执行指令更多）

```java
iconst_2   // 常量2入栈
istore_1   // 放到索引1的位置
iconst_3   // 常量3入栈
istore_2   // 放到索引2的位置
iload_1    // 加载进来
iload_2    // 加载进来
iadd       // 常量2，3出栈，执行相加
istore_3   // 结果5放到索引3的位置
```

> 基于寄存器的计算流程（指令集多，但执行指令少）

```java
mov eax,2  // 将eax寄存器的值设为1
add eax,3  // 使eax寄存器的值加3
```

***

> 总结

**由于跨平台性的设计，Java的指令都是根据栈来设计的**。不同平台CPU架构不同，所以不能设计为基于奇存器的。 优点是跨平台，指令集小，编译器容易实现，缺点是性能下降，实现同样的功能需要更多的指令。

时至今日，尽管嵌入式平台已经不是Java程序的主流运行平台了（准确水说应该是HotspotVM 的宿主环境已经不局限于嵌入式平台了），那么为什么不将架构更换为基于奇存器的架构呢？

栈：

跨平台性、指令集小、指令多、；执行性能比寄存器差

***

### 1.1.6 JVM生命周期

> 虚拟机的启动

Java虚拟机的启动是通过引导类加载器 (bootstrap class loader)创建一个初始类(initial class)来完成的，这个类是由虚拟机的具体实现指定的。

***

> 虚拟机的执行

- 一个运行中的Java虚拟机有着一个清哳的任务：执行Java程序。
- ﻿程序开始执行时他才运行，程序结束时他就停止。
- ﻿**执行一个所谓的Java程序的时候，真真正正在执行的是一个叫做Java虚拟机的进程**。

***

> 虛拟机的退出

有如下的几种情况：

- ﻿程序正常执行结束
- ﻿程序在执行过程中遇到了异常或错误而异常终止
- ﻿由于操作系统出现错误而号致Java虛拟机进程终止
- ﻿某线程调用Runtime 类或system类的exit方法，或 Runtime 类的halt方法，并且Java 安全管理器也允许这次exit或halt操作
- 除此之外，JNI ( Java Native Interface)规范描述了用JNI Invocation API来加载或卸载 Java虚拟机时，Java虚拟机的退出情况

***

### 1.1.7 JVM发展历程

> Sun Classic VM

- 早在1996年Java1.0版本的时候，sun公司发布了一款名为Sun Classic VM的Java虚拟机，它同时也是**世界上第一款商用Java虚拟机**，JDK1 .4时完全被淘汰
- 这款虚拟机内部**只提供解释器**
- 如果使用 JIT 编译器，就需要进行外挂。但是一旦使用了JIT编译器，JIT就会接管虚拟机的执行系统。解释器就不再工作。解释器和编译器不能配合工作。
- 现在hotspot内置了此虚拟机。

```java
举例：解释器理解成一行一行执行，如果for循环，也会一行一行执行；
JIT编译器就是将热点代码重复执行的缓存起来。
  
生活案例：
A地点到B地点：解释器就是走路，一说开始，解释器就开始执行了，无需等待；而JIT就理解成坐公交车，说开始时可能等公交车等了十分钟才来（前期等待时间长，无响应），等坐上车之后后续又下车，然后继续等公交车（后续可能会追上步行，但是等确实耗费时间，说不定还没步行快）。

后面将解释器和JIT结合使用，有些地方走路快的，就不等公交车。结合起来效率更高。
```

***

> Exact VM

- 为了解决上一个虚拟机问题，jak1.2时，sun提供了此虛拟机
- Bxact Memory Management： 准确式内存管理
  - 也可以叫Non-conservat ive/Accurate Memory Management
  - **虚拟机可以知道内存中某个位置的数据具体是什么类型。**
- 其备现代高性能虚拟机的雏形
  - 热点探测
  - 编译器与解释器混合工作模式
- 只在Solaris平台短暂使用，其他平台上还是classic vm
  - 英雄气短，终被Hotspot虛拟机替换

***

> SUN公司的 HotSpot VM

- HotSpot历史
  - 最初面一家名为“tongviewrechnologies“的小公司设计
  - 1997年，此公司被sun收购;2009年，sun公司被甲骨文收购
  - JDK1.3时，Hotspot wM成为默认虚拟机
- 目前Hotspot占有绝对的市场地位，称霸武林
  - **不管是现在仍在广泛使用的JDK6，还是使用比例较多的JDK8中，默认的虚拟机都是 HotSpot**
  - Sun/oracle JDK和openJDK的默认虚拟机
  - 因此本课程中默认介绍的虚拟机都是Hotspot，相关机制也主要是指HotSpot的GC机制。（比如其他两个商用虚拟机都没有方法区的慨念）
- 从服务器、桌面到移动端、嵌入式都有应用
- 名称中的Hotspot指的就是它的热点代码探测技术
  - 通过计数器找到最具编译价值代码，触发即时编译或栈上替换
  - 通过编译器与解释器协同工作，在最优化的程序响应时间与最佳执行性能中取得平衡

***

> BEA 的 JRockit

- 专注于服务器端应用
  - 它可以不太关注程序启动速度，因此JRockit内部不包含解析器实现，全部代码都拿即时编译器编译后执行
- 大量的行业基准测试显示，**JRockit JVM是世界上最快的JVM**
  - 使用JRockit产品，客户己经体验到了显著的性能提高（一些超过了70%）和硬件成本的减少（达50%）
- 优势：全而的Java运行时解决方案组合
  - JRockit面问延迟敏感型应用的解决方案JRockit Real Time提供以亳秒或微秒级的JVM响应时间，适合财务、军事指挥、电信网络的需要
  - Missioncontrol服务套件，它是一组以极低的开销来监控、管理和分析生产环境中的应用程序的工具。
- 2008年，BEA被Oracle收购
- oracle表达了整合两大优秀虛拟机的工作，大致在JDR 8中完成。整合的方式是在Hotspot的基础上，移植JRockit的优秀特性
- ﻿高斯林：目前就职于谷歌，研究人工智能和水下机器人

***

> IBM 的 J9

- 全称：IBM Technology for Java Virtual Machine, 简称IT4J，内部代号：J9
- 市场定位与Hotspot接近，服务器端、桌面应用、嵌入式等多用途VM
- 广泛用于IBM的各种Java产品
- 目前，有影响力的三大商用虛拟机之一，也号称是世界上最快的Java虛拟机
- 2017年左右，IBM发布了开源J9 VM，命名为openJ9，交给Eclipse基金会管理，也称为 Eclipse openJ9

***

> TaobaoJVM

- 由AliJVM 团队发布。阿里，国内使用Java最强大的公司，覆盖云计算、金融、物流、电商等众多领域， 需要解决高并发、高可用、分布式的复合问题。有大量的开源产品
- **基于openJDK 开发了自己的定制版不AlibabaJDK**，简称AJDK。是整个阿里Java体系的基石
- 基于openJDK Hotspot VM 发布的国内第一个优化、**深度定制且开源的高性能服务器版Java虚拟机**
  - 创新的GCIH (GC invisible heap ）技术实现了off-heap，**即将生命周期较长的Java对象从heap中移到heap之外，并且GC不能管理GCIH内部的Java 对象，以此达到降低GC 的回收频率和提升GC 的回收效率的目的**
  - GCIH 中的**对象还能够在多个Java 虚拟机进程中实现共享**
  - 使用crc32 指令实现 JVM intrinsic 降低JNI 的调用开销
  - PMU hardware 的Java profiling tool 和诊断协助功能
  - 针对大数据场景的zenGC
- taobao vm应用在阿里产品上性能高，硬件严重依赖inte1的cpu，损失了兼容性，但提高了性能
  - 目前己经在淘宝、天猫上线，把Oracle 官方JVM 版木全部替换了

***

## 1.2 类加载子系统

### 1.2.1 内存结构概述

> 简图

<img src="picture/img7.png" style="zoom:90%;" />

***

> 详细图

![](picture/img8.png)

![](picture/img9.png)

![](picture/img10.png)

***

### 1.2.2 类加载器与类的加载过程

![](picture/img11.png)

- 类加载器子系统负责从文件系统或者网络中加载Class文件，class文件在文件开头有特定的文件标识
- ClassLoader只负责class文件的加载，至于它是否可以运行，则由Execution Engine决定
- 加载的类信息存放于一块称为方法区的内存空间。除了类的信息外，方法区中还会存放运行时常量池信息，可能还包括字符串字面量和数字常量（这部分常量信息是Class文件中常量池部分的内存映射）

***

> 类加载器ClassLoader角色

![](picture/img12.png)

1. class file 存在于本地硬盘上，可以理解为设计师画在纸上的模板，而最终这个模板在执行的时候是要加载到JVM当中来根据这个文件实例化出n个一模一样的实例。
2. class file 加载到JVM中，被称为DNA元数据模板，**放在方法区**
3. 在.class文件一＞JVM ->最终成为元数据模板，此过程就要一个远输工具(类装载器 Class Loader)，扮演一个快递员的角色。

***

```java
public class HelloLoader {
  public static void main(String[] args) {
    System.out.println("Hello");
  }
}
```

> 类的加载过程

![](picture/img13.png)

> 类的加载过程包括三个阶段：加载、链接、初始化

![](picture/img14.png)

类的加载（广义）过程刚好第一个阶段也叫加载（狭义）

***

#### 1.2.2.1 加载

在加载阶段，Java虚拟机需要完成以下三件事情：

1. 通过一个类的全限定名获取定义此类的二进制字节流
2. 将这个字节流所代表的静态存储结构转化为方法区的运行时数据结构
3. **在内存中生成一个代表这个类的java.lang.Class对象**，作为方法区这个类的各种数据的访问入口

***

> 补充：加载.class文件的方式。上面第一点规则并没有指明要从哪里获取、如何获取。仅仅这一点空隙，Java虚拟机的使用者们就可以在加载阶段搭建出一个相当开放广阔的舞台，如下：

- 从本地系统中直接加载
- 通过网络获取，典型场景：Web Applet
- 从zip压缩包中读取，成为日后jar、war格式的基础
- 运行时计算生成，使用最多的是：动态代理技术
- 由其他文件生成，典型场景：JSP应用
- 从专有数据库中提取。class文件，比较少见
- 从加密文件中获取，典型的防CIass文件被反编译的保护措施

相对于类加载过程的其他阶段，非数组类型的加载阶段（准确的说，是加载阶段中获取类的二进制字节流的动作）是开发人员可控性最强的阶段。

加载阶段结束后，Java虚拟机外部的二进制字节流就按照虚拟机所设定的格式存储在方法区之中了。

加载阶段与链接阶段的部分动作（如一部分字节码文件格式验证动作）是交叉进行的，加载阶段尚未完成，链接阶段可能已经开始，但这些夹在加载阶段之中进行的动作，仍然属于链接阶段的一部分，这两个阶段的开始时间仍然保持着固定的先后顺序。

***

#### 1.2.2.2 链接

##### 1.2.2.2.1 验证

验证是链接阶段的第一步，并且验证阶段的工作量在虚拟机的类加载过程中占了相当大的比重。

Class文件并不一定只能由Java源码编译而来，它可以使用包括键盘 0 和 1 直接在二进制编译器中敲出 Class 文件在内的任何途径产生。Java虚拟机如果不检查输入的字节流，对其完全信任的话，很可能会因为载入了有错误或有恶意企图的字节码流而导致整个系统受攻击甚至崩溃，所以验证字节码是Java虚拟机保护自身的一项必要措施。

- 目的在于确保Class文件的字节流中包含信息符合当前虛拟机要求，保证被加载类的正确性，不会危害虛拟机自身安全
- 主要包括四种验证，**文件格式验证，元数据验证，字节码验证，符号引用验证**

***

<font color="red">1 文件格式验证</font>：第一阶段要验证字节流是否符合Class文件格式的规范，并且能被当前版本的虚拟机处理。这一阶段可能包括下面的验证点：

- 是否以魔术开头
- 主、次版本是否在当前Java虚拟机接受范围之内
- 指向常量的各种索引值中是否有指向不存在的常量或不符合的常量
- ...

该验证阶段的主要目的是保证输入的字节流能正确地解析并存储于方法区之内，格式上符合描述一个Java类型信息的要求。这阶段的验证是基于二进制字节流进行的，只有通过了这个阶段的验证之后，这段字节流才被允许进入 Java 虚拟机内存的方法区中进行存储，所以后面的三个验证阶段全部是基于方法区的存储结构上进行的，不会再直接读取、操作字节流了。

***

<font color="red">2 元数据验证</font>：第二阶段是对字节码描述的信息进行语义分析，以保证其描述的信息符合要求，这个阶段可能包括的验证点如下：

- 这个类是否有父类（除了 Object 之外，所有的类都应当有父类）
- 这个类是否继承了不被允许继承的类（final修饰的类）
- 如果这个类不是抽象类，是否实现了其父类或接口之中要求实现的所有方法。
- ...

第二阶段的主要目的是对类的元数据信息进行语义校验，保证不存在于Java语言规范定义相悖的元数据信息。

***

<font color="red">3 字节码验证</font>：第三阶段是整个验证过程中最复杂的一个阶段，主要目的是通过数据流分析和控制流分析，确定程序语义是合法的、符合逻辑的。在第二阶段对元数据信息中的数据类型校验完毕之后，这阶段就要对类的方法体（Class文件中的 Code属性）进行校验分析，保证被校验类的方法在运行时不会做出危害虚拟机安全的行为，例如：

- 保证任意时刻操作数栈的数据类型与指令代码序列都能配合工作，例如不会出现类似于“在操作栈放置了一个 int 类型的数据，使用时却按 long 类型来加载入本地变量表中”这样的情况。
- 保证任何跳转指令都不会跳转到方法体之外的字节码指令上。
- 保证方法体中的类型转换总是有效的，例如可以把一个子类对象赋值给父类数据类型，这是安全的，但是把父类对象赋值给子类数据类型，甚至把对象赋值给与它毫无继承关系、完全不相干的一个数据类型，则是危险和不合法的。
- ...

如果一个类型中有方法体的字节码没有通过字节码验证，那它肯定是有问题的；但如果通过了验证，也仍然不能保证它一定就是安全的。即使字节码验证阶段中进行了再大量、再严密的检查，也依然不能保证这一点。

***

<font color="red">4 符号引用验证</font>：最后一个阶段的校验行为发生在虚拟机将符号引用转化为直接引用的时候，这个转化动作将在链接的第三个阶段--解析阶段中发生。符号引用验证可以看作是对类自身以外（常量池中的各种符号引用）的各类信息进行匹配性校验，通俗来说就是，该类是否缺少或者被禁止访问它依赖的某些外部类、方法、字段等资源。本阶段通常需要校验下列内容：

- 符号引用中通过字符串描述的全限定名是否能找到对应的类
- 在指定类中是否存在符合方法的字段描述符及简单名称所描述的方法和字段
- 符号引用中的类、字段、方法的可访问性（private、protected、public、<package>是否可被当前类访问）
- ...

符号引用验证的主要目的是确保解析行为能正常执行，如果无法通过符号引用验证，Java虚拟机将会抛出一个 java.lang.IncompatibleClassChangeError 的子类异常，典型的如：java.lang.IllegalAccessError、java.lang.NoSuchFieldError、java.lang.NoSuchMethodError 等。

***

> 总结

​        验证阶段对于虚拟机的类加载机制来说，是一个非常重要的、但却不是必须要执行的阶段，因为验证阶段只有通过或者不通过的差别，只要通过了验证，其后就对程序运行期没有任何影响了。如果程序运行的全部代码（包括自己编写的、第三方包中的、从外部加载的、动态生成的等所有代码）都已经被反复使用和验证过，在生产环境的实施阶段就可以考虑使用` -Xverify:none` 参数来关闭大部分的类验证措施，以缩短虚拟机类加载的时间。

***

##### 1.2.2.2.2 准备

​        准备阶段是正式为类中定义的变量（即静态变量，被static修饰的变量）分配内存并设置类变量初始值的阶段，从概念上讲，这些变量所使用的内存都应当在方法区中进行分配，但必须注意到方法区本身是一个逻辑上的区域，在JDK1.7之前，HotSpot使用永久代来事项方法区时，实现是完全符合这种逻辑概念的；而在JDK1.7及之后，类变量则会随着Class对象一起存放在Java堆中，这时候“类变量在方法区”就完全是一种对逻辑概念的表述了。

**简言之，为类的静态变分配内存，并将其初始化为默认值**

- 为**类变量（static修饰）**分配内存并且设置该类变量的默认初始值，即零值。
- **这里不包含用final修饰的static，因为final在编译的时候就会分配了，准备阶段会显式初始化**
- 这里不会为实例变量分配初始化，类变量会分配在方法区中，而实例变量是会随着对象一起分配到 Java 堆中

```java
public static int value = 123;

变量 value 在准备阶段过后的初始值为 0 而不是 123，因为这时尚未开始执行任何Java方法，而把value赋值为 123 的 putstatic 指令是程序被编译后，存放于类构造器<clinit>（）方法之中，所以把value赋值为 123 的动作要到类的初始化阶段才会被执行。
```

***

> 基本数据类型的零值

| 数据类型 | 零值      | 数据类型  | 零值  |
| -------- | --------- | --------- | ----- |
| int      | 0         | boolean   | false |
| long     | 0L        | float     | 0.0f  |
| short    | (short) 0 | double    | 0.0d  |
| char     | '\u0000'  | reference | null  |
| byte     | (byte) 0  |           |       |

***

如果类字段的字段属性表中存在 ConstantValue 属性，那在准备阶段变量值就会被初始化为 ConstantValue 属性所指定的初始值，假设上面类变量 value 的定义修改为：

```java
public static final int value = 123;
```

编译时 Javac 将会为 value 生成 ConstantValue 属性，在准备阶段虚拟机就会根据 ConstantValue 的设置将 value 赋值为 123

***

##### 1.2.2.2.3 解析

- **将常量池内的符号引用转换为直接引用的过程**
- 事实上，解析操作往往会伴随着JVM在执行完初始化之后再执行
- 符号引用就是一组符号来描述所引用的目标。符号引用的字面量形式明确定义在 《java虛拟机规范》的Class文件格式中。直接引用就是直接指向目标的指针、相对偏移量或一个间接定位到目标的句柄
- 解析动作主要针对类或接口、字段、类方法、接口方法、方法类型等。对应常量池中的CONSTANT_Class_info、 CONSTANT _Fieldref_info、CoNSTANT_Methodref_info等

***

```java
public class HelloApp {
 	private static int a = 1; // prepare阶段： a = 0 ---> initial初始化：a = 1
  public static void main(String[] args) {
  }
}
```

***

#### 1.2.2.3 初始化

**简言之，为类的静态变量赋予正确的初始值**

- 初始化阶段就是执行类构造器方法<C1init>()的过程
- 此方法不需定义，是 javac 编译器自动收集类中的**所有类变量的赋值动作**和**静态代码块中的语句**合并而来（**如果没有static修饰的变量和代码块，字节码不会出现<C1init>**）
- 构造器方法中指令按语句在源文件中出现的顺序执行
- <clinit>()不同于类的构造器。（关联：构造器是虛拟机视角下的<init>()，每个类至少存在一个构造器，对应就是<init>）
- 若该类具有父类，JVM会保证子类的<Clinit>（）执行前，父类的<C1init>(）己经执行完毕
- 虛拟机必须保证一个类的<Clinit>（）方法在多线程下被同步加锁【类加载到内存只需加载一次，<Clinit>（）方法只调用一次】

***

问：Java编译器并不会为所有的类都产生<Clinit>（）初始化方法。哪些类在编译为字节码后，字节码文件中将不会包括<Clinit>（）方法？

- 一个类中并没有声明任何的类变量，也没有静态代码块时
- 一个类中声明类变量，但是没有明确使用类变量的初始化语句以及静态代码块来执行初始化操作时
- 一个类中包含 static final修饰的基本数据类型的字段，这些类字段初始化语句采用编译时常量表达式

***

> 验证子类的<Clinit>执行前，先执行父类的<C1init>

```java
public class ClassInitTest {
    private static int num = 1;
    static {
        num = 2;
        number = 20; // ok 虽然在下面定义，但是在准备阶段已经有默认值了，所以这里可以重新赋值，但不能引用
        System.out.println(num); // ok
        //System.out.println(number); // 报错，非法的前向引用（可以赋值，但是不能使用）
    }
    private static int number = 10; // linking之prepare：number = 0 --> initial: 20 --> 10
    public static void main(String[] args) {
        System.out.println(ClassInitTest.num);   // 2
        System.out.println(ClassInitTest.number);// 10
    }
}
```

> 验证父类优先于子类加载

```java
public class ClinitTest1 {
    static class Father {
        public static int A = 1;
        static {
            A = 2;
        }
    }
    static class Son extends Father {
        public static int B = A;
    }
    public static void main(String[] args) {
        // 先加载Father类（加载-链接-初始化），其次加载Son类
        System.out.println(Son.B);
    }
}
```

> 验证类只被加载一次

```java
public class DeadThreadTest {
    public static void main(String[] args) {
        Runnable r = () -> {
            System.out.println(Thread.currentThread().getName() + "开始");
            DeadThread dead = new DeadThread();
            System.out.println(Thread.currentThread().getName() + "结束");
        };
        Thread t1 = new Thread(r,"线程1");
        Thread t2 = new Thread(r,"线程2");
        t1.start();
        t2.start();
    }
}
class DeadThread {
    static {
        if (true) {
            System.out.println(Thread.currentThread().getName() + "初始化当前类");
            while (true) {}  // 死循环，不会结束，模拟当前类一直加载中
        }
    }
}

线程2开始
线程1开始
线程2初始化当前类
```

***

### 1.2.3 类加载器分类

- JVM 支持两种类型的类加载器，分别为**引导类加载器 (Bootstrap ClassLoader）**和**自定义类加载器 (User-Defined classLoader)**
- 从概念上来讲，自定义类加载器一般指的是程序中由开发人员自定义的一类类加载器，但是Java虚拟机规范却没有这么定义，而是**将所有派生于抽象类ClassIoader的类加载器都划分为自定义类加载器**【按这样划分的话，那么除了Bootstrap ClassLoader，剩下都属于自定义类加载器，见下图】
- 无论类加载器的类型如何划分，在程序中我们最常见的类加载器始终只有3个，如下所示：

<img src="picture/img15.png" style="zoom:50%;" />

**这里的四者之间的关系是包含关系。不是上层下层，也不是子父类的继承关系**

说明：Bootstrap ClassLoader是非java语言编写的，其他都是java语言编写的。

```java
public static void main(String[] args) {
    // 获取系统类加载器
    ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
    System.out.println(systemClassLoader); // sun.misc.Launcher$AppClassLoader@18b4aac2
  
    // 获取其上层：扩展类加载器
    ClassLoader extClassLoader = systemClassLoader.getParent();
    System.out.println(extClassLoader); // sun.misc.Launcher$ExtClassLoader@1540e19d
  
    // 获取其上层：获取不到引导类加载器
    ClassLoader bootClassLoader = extClassLoader.getParent();
    System.out.println(bootClassLoader); // null
  
    // 对于用户自定义类来说，默认使用系统类加载器进行加载
    ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
    System.out.println(classLoader); // sun.misc.Launcher$AppClassLoader@18b4aac2
  
    // String类使用 引导类加载器进行加载 --> java核心类库都是使用引导类进行加载
    ClassLoader stringLoader = String.class.getClassLoader();
    System.out.println(stringLoader); //null
}
```

***

> 虚拟机自带的加载器

- 启动类加载器（也叫引导类加载器，Bootstrap ClassIoader)
  - 这个类加载使用C/C++语言实现的，嵌套在JVM内部
  - 它用来加载Java的核心库 (JAVA HOME/jre/lib/rt.jar、resources. jar等或sun.boot.ciass.path路径下的内容)，用于提供JVM自身需要的类
  - 并不继承自java. lang.classLoader，没有父加载器【C语言写的不可能继承Java的类】
  - **加载扩展类和应用程序 类加载器，并指定为他们的父类加载器**【扩展类和系统类加载器也是由Bootstrap ClassIoader加载，他俩也属于核心库中的】。
  - 出于安全考虑，Bootstrap启动类加载器只加载包名为java、javax、sun等开头的类
- 扩展类加载器 (Extension classLoader)
  - Java语言编写，由sun.misc.Launcher$ExtclassLoader实现
  - 派生于classIoader类
  - 父类加载器为**启动类加载器**
  - 从java.ext.dirs系统属性所指定的目录中加载类库，或从JDK的安装日录的 `jre/lib/ext` 子日录（扩展目录）下加载类库。**如果用户创建的JAR放在此日录下，也会自动由扩展类加载器加载。**
- 应用程序类加载器（系统类加载器 AppCIassIoader)
  - java语言编写，由sun.misc.Launcher$AppclassIoader实现
  - 派生于classIoader类
  - 父类加载器为**扩展类加载器**
  - 它负责加载环境变量classpath或系统属性 java.class.path 指定路径下的类库
  - **该类加载是程序中默认的类加载器**，一般来说，Java应用的类都是由它来完成加载
  - 通过ClassLoader#getsystemclassLoader()方法可以获取到该类加载器

***

> 引导类加载器

```java
 public static void main(String[] args) {
     System.out.println("********** 启动类加载器 ***********");
     URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
     for (URL urL : urLs) {
         System.out.println(urL.toExternalForm());
     }
     // 从 jsse.jar 选择一个类，来看看他的类加载器是什么
     ClassLoader classLoader = Provider.class.getClassLoader();
     System.out.println(classLoader); // null  说明是引导类加载器加载的
   
     System.out.println("********** 扩展类加载器 ***********");
     String extDirs = System.getProperty("java.ext.dirs");
     for (String path : extDirs.split(";")) {
         System.out.println(path);
     }
     // 从 sunec.jar 选择一个类，来看看他的类加载器是什么
     ClassLoader classLoader1 = SunEC.class.getClassLoader();
     System.out.println(classLoader1); //  sun.misc.Launcher$ExtClassLoader@31befd9f  说明是扩展类加载器加载的
 }
********** 启动类加载器 ***********
file:/Library/Java/JavaVirtualMachines/jdk-1.8.jdk/Contents/Home/jre/lib/resources.jar
file:/Library/Java/JavaVirtualMachines/jdk-1.8.jdk/Contents/Home/jre/lib/rt.jar
file:/Library/Java/JavaVirtualMachines/jdk-1.8.jdk/Contents/Home/jre/lib/jsse.jar
file:/Library/Java/JavaVirtualMachines/jdk-1.8.jdk/Contents/Home/jre/lib/jce.jar
file:/Library/Java/JavaVirtualMachines/jdk-1.8.jdk/Contents/Home/jre/lib/charsets.jar
file:/Library/Java/JavaVirtualMachines/jdk-1.8.jdk/Contents/Home/jre/lib/jfr.jar
file:/Library/Java/JavaVirtualMachines/jdk-1.8.jdk/Contents/Home/jre/classes
null
********** 扩展类加载器 ***********
/Users/wing/Library/Java/Extensions:/Library/Java/JavaVirtualMachines/jdk-1.8.jdk/Contents/Home/jre/lib/ext:/Library/Java/Extensions:/Network/Library/Java/Extensions:/System/Library/Java/Extensions:/usr/lib/java
sun.misc.Launcher$ExtClassLoader@31befd9f
```

***

**用户自定义加载器**

- 在Java的日常应用程序开发中，类的加载几乎是由上述3种类加载器相互配合执行的，在必要时，我们还可以自定义类加载器，来定制类的加载方式。
- 为什么要自定义类加载器？
  - 隔离加载类
  - 修改类加载的方式
  - 扩展加载源
  - 防止源码泄漏

***

### 1.2.4 ClassLoader的使用说明

> 关于ClassLoader

Classloader类，它是一个抽象类，其后所有的类加载器都继承自ClassLoader （不包括启动类加载器）

相关方法略。。。

***

### 1.2.5 双亲委派机制

Java虚拟机对class文件采用的是**按需加载**的方式，也就是说当需要使用该类时才会将它的class文件加载到内存生成class对象。而且加载某个类的class 文件时，Java虚拟机采用的是**双亲委派模式**，即把请求交由父类处理，它是一种任务委派模式。

> 我们也创建java.lang包，并且也定义String类（跟核心类库String一样包名和类名）

```java
package java.lang;
public class String {
    static {
        System.out.println("test String");
    }
    // 当执行这个main方法时，会报错，为什么呢？
    // 因为String被引导类加载器加载了，然后去执行核心API String中的main方法，发现没找到，所以报错了
    public static void main(String[] args) {
        System.out.println("hello");
    }
}
```

> 测试

```java
public static void main(String[] args) {
    // 如果这里用的是自定义的String，那么一定会输出 "test String"，但是输出结果并没有
    String str = new String(); // 尝试使用系统类加载器对自定义类进行加载，但是会先向上委托，直到引导类这，引导类加载器看了下是java.lang包下的，所以引导类加载器可以加载，系统类加载器就不会去加载自定义类了，因此这里使用的是核心API的String
    System.out.println("hello" + str);
}
```

> 结论：假如自己定义了java.lang.String，项目中会使用这个而不是用的核心的String会出现什么后果，比如别人给我们一个这样的类，我放到项目中，然后项目直接就挂了

<img src="picture/img16.png" style="zoom:50%;" />

> 工作原理

1. 如果一个类加载器收到了类加载请求： 它并不会自己先去加载，而是把这个请求委托给父类的加载器去执行；
2. 如果父类加载器还存在其父类加载器，则进一步向上委托，依次递归，请求最终将到达顶层的启动类加载器
3. 如果父类加载器可以完成类加载任务，就成功返回，倘若父类加载器无法完成此加载任务，子加载器才会尝试自己去加载，**这就是双亲委派模式。**

***

> 双亲委派机制举例2

![](picture/img17.png)

​        假如我们的程序中需要使用`SPI接口`，该接口属于核心API，使用双亲委派机制，依次到引导类加载器，引导类加载器去加载核心`rt.jar包`，该包存在一些`interface接口`，接口需要具体的实现类，实现类就涉及到`第三方的jar包`了，假如是`jdbc.jar`，这时候呢jar包属于第三方的了，不属于核心API，因此出现反向委派，依次向下委派给`系统类加载器`，因此具体实现类由`系统类加载器`进行加载。

**总结：核心API中的接口是引导类加载器进行加载，而实现类是由系统类加载器进行加载**

优势：

- 避免类的重复加载
- 保护程序安全，防止核心API被随意篡改
  - 自定义类：java.lang.String
  - 自定义类：java.lang.ShkStart

***

```java
/* 在 java.lang 包下写一个自定义类 */
package java.lang;
public class WsyStart {
    
    public static void main(String[] args) {
        System.out.println("hello");
    }
}
// 启动main方法会报错
Exception in thread "main" java.lang.SecurityException: Prohibited package name: java.lang
```

**原因**：定义在 java.lang 包下的类由引导类加载器进行加载，发现这个不是核心API，所以提示不允许加载

**好处**：保护程序，不然随便写个恶意程序攻击引导类加载器，会导致系统崩溃。

***

> 沙箱安全机制

自定义string类，但是在加载自定义string类的时候会率先使用引导类加载器加载，而引导类加载器在加载的过程中会先加载jdk自带的文件(rt.jar包中java\lang\string.class)，报错信息说没有main方法，就是因为加载的是rt.jar包中的string类。这样可以保证对java核心源代码的保护，这就是**沙箱安全机制**

***

### 1.2.6 其他

- 在 JVM 中表示两个 class 对象是否为同一个类，存在两个必要条件：
  - 类的完整类名必须一致，包括包名
  - 加载这个类的ClassLoader(指ClassIoader实例对象）必须相同。

换句话说，在 JVM 中，即使这两个类对象（Class对象）来源同—个Class文件，被同一个虚拟机所加载，但只要加载它们的ClassLoader实例对象不同，那么这两个类对象也是不相等的。

***

> 对类加载器的引用

​        JVM必须知道一个类型是由`启动加载器`加载的还是由`用户类加载器`加载的。如果一个类型是由用户类加载器加载的，那么 JVM 会**将这个类加载器的一个引用作为类型信息的一部分保存在方法区中（这样类调用getClassLoader()就可以知道当前类是哪个加载器加载的了）**。

当解析一个类型到另一个类型的引用的时候，JVM需要保证这两个类型的类加载器是相同的。

***

#### 1.2.6.1 类的主动使用和被动使用

Java程序对类的使用方式分为：**主动使用和被动使用**

- 主动使用，又分为七种情况：

  - 创建类的实例，使用new

  - 访问某个类或接口的静态变量，或者对该静态变量赋值

  - 调用类的静态方法

  - 反射（比如： Class .forName (“com.atguigu.Test"))

  - 初始化一个类的子类

  - Java虚拟机启动时被标明为启动类的类

  - JDK 7开始提供的动态语言支持

    java. lang .invoke .MethodHandle实例的解析结果 REF_ getstatic、 REF putstatic、 REF_ invokestatic句柄对应的类没有初始化，则初始化

除了以上七种情况，其他使用Java类的方式都被看作是对**类的被动使用**，都**不会导致类的初始化**（被动使用也是使用，也会被加载，但是不会被初始化）

也就是说：**并不是在代码中出现的类，就一定会被加载或者初始化。如果不符合主动使用的条件，类就不会初始化**

***

> 被动使用案例

1.静态字段

```java
/*
 * 通过子类引用父类的静态字段，不会导致子类初始化
 */
public class PassiveUse {
    @Test
    public void test() {
        System.out.println(Child.num);  // 获取父类的类变量
    }
}
class Child extends Parent {
    static {
        System.out.println("Child类的初始化");
    }
}
class Parent {
    static {
        System.out.println("Parent类的初始化");
    }
    public static int num = 1;
}

Parent类的初始化
1
```

结论：当通过子类引用父类的静态变量，不会导致**子类**初始化，只有真正声明这个字段的类（父类）才会被初始化。假如下面，现在打印子类类变量，是不是只初始化子类，而不用初始化父类的？错，java基础就是，只要初始化子类，那么要优先初始化它的父类。

```java
public class PassiveUse {
    @Test
    public void test() {
        System.out.println(Child.num1);  // 子类的类变量
    }
}
class Child extends Parent {
    public static int num1 = 1;
    static {
        System.out.println("Child类的初始化");
    }
}
class Parent {
    static {
        System.out.println("Parent类的初始化");
    }
    public static int num = 1;
}
Parent类的初始化
Child类的初始化
1
```

2.数组定义：通过数组定义类引用，不会触发此类的初始化

```java
Parent[] parents= new Parent[10];   // 这里虽然new 了，但是不会初始化 Parent 类。
System.out.println(parents.getClass());
// new的话才会初始化
parents[0] = new Parent();
```

3.引用常量：引用常量不会触发此类或接口的初始化。因为常量在链接阶段就已经被显式赋值了

```java
/*
 * 常量在编译阶段会存入调用类的常量池中，本质上没有直接引用到定义常量的类，
 * 因此不会触发定义常量的类的初始化
 */
public class ConstClass {
    static {
        System.out.println("ConstClass init");
    }
    public static final String HELLO = "hello";
    public static String HELLO1= "hello";
}

class TestConstClass {
    public static void main(String[] args) {
        System.out.println(ConstClass.HELLO);  // 不会初始化
        System.out.println(ConstClass.HELLO1); // 会初始化
    }
}
hello
ConstClass init
hello
```

4.loadClass 方法：调用 ClassLoader 类的 loadClass()方法加载一个类，并不是对类的主动使用，不会导致类的初始化

```java
Class clazz = ClassLoader.getSystemClassLoader().loadClass("com.test.java.Person");
```

***

## 1.3 运行时数据区概述及线程

<img src="picture/img18.png" style="zoom:50%;" />

***

<img src="picture/img19.png" style="zoom:50%;" />

***

​        **内存** 是非常重要的系统资源，是**硬盘**和**CPU** 的中间仓库及桥梁，承载着操作系统和应用程序的实时运行。**JVM 内存布局规定了Java在运行过程中 内存申请、分配、管理的策略**，保证了JVM的高效稳定运行。**不同的 JVM 对于内存的划分方式和管理机制存在着部分差异**。结合 JVM 虚拟机规范，来探讨一下经典的 JVM 内存布局

<img src="picture/img20.png" style="zoom:50%;" />

***

![](picture/img21.png)

***

​        Java虚拟机定义了若干种程序运行期间会使用到的运行时数据区，其中有一些会随着虛拟机启动而创建，随着虛拟机退出而销毀。另外一些则是与线程一一对应的，这些与线程对应的数据区域会随着线程开始和结束而创建和销毁。

**灰色**的为单独线程私有的，**红色**的为多个线程共享的。即：

- 每个线程：独立包括程序计数器、栈、本地栈。

- 线程间共享：堆、堆外内存（永久代或元空间、代码缓存）

  <img src="picture/img22.png" style="zoom:50%;" />

  假如一个进程有5个线程，那么一个进程只有一个堆和方法区，每个线程都单独一份PC、VMS、NMS，5个线程共享一个堆和方法区。

  灰色基本都是进栈和出栈操作，不用进行什么优化，主要优化是在堆和方法区（java8后叫元空间），堆 95% 方法区 5%

**每个JVM只有一个Runtime实例（单例）。即为运行时环境，相当于内存结构的中间的那个框框：运行时环境**

***

> 线程

- 线程是一个程序里的运行单元。JVM允许一个应用有多个线程并行的执行
- 在Hotspot JVM 里，每个线程都与操作系统的本地线程直接映射
  - **当一个Java线程准备好执行以后，此时一个操作系统的本地线程也同时创建**。Java线程执行终止后，本地线程也会回收
- 操作系统负责所有线程的安排调度到任何一个可用的CPU上。一旦本地线程初始化成功，它就会调用Java线程中的run() 方法

***

## 1.4 程序计数器

也叫做 **PC寄存器**

JVM 中的程序计数寄存器 （Program counter Register) 中，Register 的命名源于CPU的寄存器，寄存器存储指令相关的现场信息。CPU只有把数据装载到寄存器才能够运行

这里，并非是广义上所指的物理寄存器，或许将其翻译为PC计数器（或指令计数器）会更加贴切（也称为程序钩子），并且也不容易引起一些不必要的误会。**JVM中的PC寄存器是对物理PC寄存器的一种抽象模拟**

***

> 作用

PC寄存器用来存储指向下一条指令的地址，也即将要执行的指令代码。由执行引擎读取下一条指令。

<img src="picture/img23.png" style="zoom:50%;" />

***

> PC寄存器介绍

- 它是一块很小的内存空间，几乎可以忽略不记。也是运行速度最快的存储区域
- 在 JVM 规范中，每个线程都有它自己的程序计数器，是线程私有的，生命周期与线程的生命周期保持一致。
- 任何时间一个线程都只有一个方法在执行，也就是所谓的**当前方法**。程序序计数器会存储当前线程正在执行的 Java方法的 JVM 指令地址；或者，如果是在执行 native 方法，则是未指定值 （undefined)
- **它是程序控制流的指示器**，分支、循环、跳转、异常处理、线程恢复等基础功能都依赖这个计数器来完成
- 字节码解释器工作时就是通过改变这个计数器的值来选取下一条需要执行的字节码指令
- **它是唯一一个在 Java 虚拟机规范中没有规定任何 outotMemoryError 情况的区域**

**补充：栈只是进栈，出栈，也没有GC垃圾回收，但是会OOM**，PC寄存器比较特殊，即没有GC，也没有OOM。

***

![](picture/img24.png)

***

> 两个常见问题

> 问题1

**使用PC寄存器存储字节码指令地址有什么用呢？**

**为什么使用PC寄存器记录当前线程的执行地址呢？**

因为CPU需要不停的切换各个线程，这时候切换回来以后，就得知道接着从哪开始继续执行。

JVM 的字节码解释器就需要通过改变PC寄存器的值来明确下一条应该执行什么样的字节码指令。

***

> 问题2

**PC寄存器为什么会被设定为线程私有？**

我们都知道所谓的多线程在一个特定的时间段内只会执行其中某一个线程的方法，CPU 会不停地做任务切换，这样必然导致经常中断或恢复，如何保证分毫无差呢？为了能够准确地记录各个线程正在执行的当前字节码指令地址，最好的办法自然是为每一个线程都分配一个PC寄存器，这样一来各个线程之间便可以进行独立计算，从而不会出现相互干扰的情况。

**假如两个线程：第一个线程执行到5行，CPU切换到第二个线程执行到7行，CPU重新回到第一个线程后从哪里继续执行呢，所以需要每个线程一份PC寄存器来单独记录**

***

> CPU时间片

CPU 时间片即 CPU 分配给各个程序的时间，每个线程被分配一个时间段，称作它的时间片

**在宏观上**：我们可以同时打开多个应用程序，每个程序并行不悖，同时运行

**但在微观上**：由于只有一个 CPU，一次只能处理程序要求的一部分，如何处理公平，一种方法就是引入时间片，每个程序轮流执行

![](picture/img25.png)

**并行 VS 串行：**

**并行**：同一个时间点有多个线程同时在执行

**串行**：多个线程排队执行，同一个时间点只有一个线程在执行

***

## 1.5 虚拟机栈

### 1.5.1 虚拟机栈概述

> 虛拟机栈出现的背景

由于跨平台性的设计，Java的指令都是根据栈来设计的。不同平台CPU架构不同，所以不能设计为基于寄存器的

**优点是跨平台，指令集小，编译器容易实现，缺点是性能下降，实现同样的功能需要更多的指令**

***

> 内存中的栈与堆

**栈是运行时的单位，而堆是存储的单位**

即：栈解決程序的运行问题，即程序如何执行，或者说如何处理数据。堆解決的是数据存储的问题，即数据怎么放、放在哪儿

<img src="picture/img26.png" style="zoom:50%;" />

***

> Java虚拟机栈是什么？

Java虚拟机栈 (Java Virtual Machine stack），早期也叫 Java 栈。每个线程在创建时都会创建一个虚拟机栈，其内部保存一个个的栈帧(stack Frame），对应着一次次的 Java方法调用（是线程私有的）

**生命周期**：生命周期与线程一致

**作用**：主管 Java 程序的运行，它保存方法的局部变量（8种基本数据类型、对象的引用地址）、部分结果，并参与方法的调用和返回。

- 局部变量 VS 成员变量（或属性）

***

<img src="picture/img27.png" style="zoom:50%;" />

最上面的栈叫**当前方法**，最上面的绿色出栈了，那么蓝色就是当前方法。

***

> 栈的特点（优点）

- 栈是一种快速有效的分配存储方式，访问速度仅次于程序计数器
- ﻿JVM直接对Java栈的操作只有两个：
  - 每个方法执行，伴随着进栈（入栈、压栈）
  - 执行结束后的出栈工作
- 对于栈来说不存在垃圾回收问题

<img src="picture/img28.png" style="zoom:50%;" />

***

> 栈中可能出现的异常

- Java 虚拟机规范允许 **Java栈的大小是动态的或者是固定不变的**
  - **如果采用固定大小的 Java 虛拟机栈**，那每一个线程的 Java 虚拟机栈容量可以在线程创建的时候独立选定。如果线程请求分配的栈容量超过 Java 虚拟机栈允许的最大容量，Java虚拟机将会抛出一个 **stackoverflowError 异常。**
  - **如果Java虚拟机栈可以动态扩展**，并日在尝试扩展的时候无法申请到足够的内存，或者在创建新的线程时没有足够的内存去创建对应的虚拟机栈，那Java虛拟机将会抛出一个 **OutOfMemoryError 异常**

***

> 设置栈内存大小

我们可以使用参数-Xss  选项来设置线程的最大栈空间，栈的大小直接决定了函数调用的最大可达深度。

***

### 1.5.2 栈的存储单位

> 栈中存什么

- 每个线程都有自己的栈，栈中的数据都是以**栈帧**(stack Frame）的格式存在
- 在这个线程上正在执行的每个方法都各自对应一个栈帧 (stack Frame)
- 栈帧是一个内存区块，是一个数据集，维系着方法执行过程中的各种数据信息

***

> 栈运行原理

- JVM 直接对 Java 栈的操作只有两个，就是对栈帧的**压栈**和**出栈**，遵循“先进后出“，“后进先出”原则
- 在一条活动线程中，一个时间点上，只会有一个活动的栈帧。即只有当前正在执行的方法的栈帧 （栈顶栈帧）是有效的，这个栈帧被称为**当前栈帧**(Current Frame )，与当前栈帧相对应的方法就是**当前方法** (Current Method），定义这个方法的类就是**当前类** (Current class)
- 执行引擎运行的所有字节码指令只针对当前栈帧进行操作。
- 如果在该方法中调用了其他方法，对应的新的栈帧会被创建出米，放在栈的顶端，成为新的当前帧
- 不同线程中所包含的栈帧是不允许存在相互引用的，即不可能在一个栈帧之中引用另外一个线程的栈帧
- 如果当前方法调用了其他方法，方法返回之际，兰前栈帧会传回此方法的执行结果给前一个栈帧，接著，虛拟机会丢弃当前栈帧，使得前一个栈帧重新成为当前栈帧。
- Java方法有两种返回函数的方式，**一种是正常的函数返回，使用return指令；另外一种是拋出异常。不管使用哪种方式，都会导致栈帧被弹出**

<img src="picture/img29.png" style="zoom:50%;" />

***

> 栈桢的内部结构

每个栈帧中存储着：

- **局部变量表（Local Variables)**
- **操作数栈 (operand stack)（或表达式栈）**
- 动态链接 (Dynamic Linking）（或指向运行时常量池的方法引用）
- 方法返回地址（Return Address)（或方法正常退出或者异常退出的定义）
- ﻿一些附加信息

![](picture/img30.png)

***

<img src="picture/img31.png" style="zoom:50%;" />

***

### 1.5.3 局部变量表

- 局部变量表也被称之为局部变最数组或本地变量表
- **定义为一个数字数组（八种数据类型和引用类型就是可以使用数字表示），主要用于存储方法参数和定义在方法体内的局部变量**，这些数据类型包括各类基本数据类型、对象引用 (reference），以及returnAddress 类型
- 由于局部变量表是建立在线程的栈上，是线程的私有数据，因此**不存在数据安全问题**
- **局部变量表所需的容量大小是在编译期确定下来的**，并保存在方法的 Code 属性的max imum local variables 数据项中。在方法运行期间是不会改变局部变量表的大小的。

***

> 解析例子

![](picture/img32.png)

> 编译后使用 jclasslib工具打开（只研究testStatic方法）

![](picture/img33.png)

**<testStatic>**: 当前方法名称是 testStatic

**<()V>**：没有入参，没有返回值

**[public static]**：公共访问权限的静态方法

***

![](picture/img34.png)

![](picture/img35.png)

**字节码**：代码编译后的字节码

**异常表**：当前方法没有异常，所以没有信息

**杂项**：局部变量数为 3 个（**局部变量表所需的容量大小是在编译期确定下来的**）

***

![](picture/img36.png)

**LineNumberTable**：字节码和源代码行号对应表。比如字节码的**0行**对应代码**16行**，字节码的**8行**对应代码**17行**

***

![](picture/img37.png)

**LocalVariableTable：**变量作用域范围（对应字节码行号）。比如**起始PC=8，长度=19**，说明变量test从字节码的第8行作用域开始，长度 19 （注意19不是行号），字节码总共有27行，刚好三个变量**起始PC**和**长度**相加都等于27，说明三个变量作用域都是在变量定义到方法结束。

***

> 变量槽slot的理解和演示

- 参数值的存放总是在局部变量数组的index0开始，到数组``长度-1``的索引结束

- 局部变量表，**最基本的存储单元是Slot（变量槽）**

- 局部变量表中存放编译期可知的各种基本数据类型(8种），引用类型(reference)， returnAddress 类型的变量

- 在局部变量表里，**32位以内的类型只占用一个slot（包括returnAddress 类型），64位的类型（long和double）占用两个slot**

  - byte、short、char 在存储前被转换为int，boolean 也被转换为int，0 表示false，非0 表示true
  - long 和double则占据两个Slot

- JVM 会为局部变量表中的每一个Slot 都分配一个访问素引，通过这个素引即可成功访问到局部变量表中指定的局部变量值

- 当一个实例方法被调用的时候，它的方法参数和方法体内都定义的局部变量将会**按照顺序被复制**到局部变量表中的每一个Slot上

- **如果需要访问局部变量表中一个64bit 的局部变量值时，只需要使用前一个索引即可**。（比

  如：访问 long 或 double 类型变量，占的索引位置是 4和5两个slot，那么只需要访问索引4即可）

- 如果当前帧是由构造方法或者实例方法创建（非静态）的，那么**该对象引用this将会存放在index为0的slot处**（所以实例方法可以使用this，因为局部变量表索引0第一个位置就保存了this），其余的参数按照参数表顺序继续排列。

<img src="picture/img38.png" style="zoom:50%;" />

***

![](picture/img39.png)

![](picture/img40.png)

weigth double类型，占用3和4两个索引位置。当调用weight变量，只要引用起始3索引位置就可以了。

***

> Slot 的重复利用

**栈帧中的局部变量表中的槽位是可以重用的**，如果一个局部变量过了其作用域，那么在其作用域之后申明的新的局部变量就很有可能会复用过期局部变量的槽位，**从而达到节省资源的目的**

![](picture/img41.png)

![](picture/img42.png)

正常有四个局部变量，分别是：this、a、b、c，但是b是代码块里面，出了代码块就出了作用域，这样b的槽位被下面的c重复利用，因此局部变量最大槽位是 3，而不是 4。

![](picture/img43.png)

***

> 静态变量与局部变量的对比

- 参数表分配完毕之后，再根据方法体内定义的变量的顺序和作用域分配
- 我们知道**类变量表**有两次初始化的机会，第一次是在“**准备阶段**”，执行系统初始化，对类变量设置**零值**，另一次则是在 “**初始化**” 阶段，赋予程序员在代码中定义的**初始值**
- **和类变量初始化不同的是，局部变量表不存在系统初始化的过程，这意味着一旦定义了局部变量则必须人为的初始化，否则无法使用**

```java
public void test() {
  int i;
  System.out.println(i);
}
```

这样的代码是错误的，**没有赋值不能够使用**

***

变量的分类：

- 按照数据类型分：1） 基本数据类型 ；2）引用数据类型

-  按照在类中声明的位置分：1） 成员变量：在使用前，都经历过默认初始化赋值  

  ​                                                       **类变量**：linking 的 prepare 阶段：给类变量默认赋值 --> initial 阶段：给类变量显式赋值即静态代码块赋值 。

  ​                                                       **实例变量**：随着对象的创建，会在**堆空间**中分配实例变量空间，并进行默认赋值。

  ​                                   2）局部变量：在使用前，必须要进行显式赋值的，否则编译不通过。

**总结**：成员变量（类变量和实例变量）都有默认初始化，虽然没有显式赋值，也可以直接使用。但是局部变量没有默认值初始化，因此使用必须要显式初始化。



**补充说明**：

- 在栈帧中，与性能调优关系最为密切的部分就是前面提到的局部变量表。在方法执行时，虛拟机使用局部变量表完成方法的传递。
- **局部变量表中的变量也是重要的垃圾回收根节点，只要被局部变量表中直接或间接引用的对象都不会被回收**

***

### 1.5.4 操作数栈

- 每一个独立的栈帧中除了包含局部变量表以外，还包含一个**后进先出**（Last-In-First-Out）的操作数栈，也可以称之为**表达式栈** (Expression stack)
- **操作数栈，在方法执行过程中，根据字节码指令，往栈中写入数据或提取数据，即入栈 (push）/出栈 (pop)**
  - 某些字节码指令将值压入操作数栈，其余的字节码指令将操作数取出栈。使用它们后再把结果压入栈
  - 比如：执行复制、交换、求和等操作

<img src="picture/img44.png" style="zoom:50%;" />

- **如果被调用的方法带有返回值的话，其返回值将会被压入当前栈帧的操作数栈中**，并更新PC寄存器中下一条需要执行的字节码指令
- 操作数栈中元素的数据类型必须与字节码指令的序列严格匹配，这由编译器在编译器期间进行验证，同时在类加载过程中的类检验阶段的数据流分析阶段要再次验证
- 另外，我们说Java虚拟机的**解释引擎是基于栈的执行引擎**，共中的栈指的就是操作数栈。
- 操作数栈，**主要用于保存计算过程的中间结果，同时作为计算过程中变量临时的存储空间**
- 操作数栈就是JVM执行引擎的一个工作区，当一个方法刚开始执行的时候，一个新的栈帧也会随之被创建出来，**这个方法的操作数栈是空的**（操作数栈使用数组实现，操作数栈创建了，说明数组已经创建，创建数组需要指明长度，说明长度也知道了）
- 每一个操作数栈都会拥有一个明确的栈深度用于存储数值，其所需的最大深度在编译期就定义好了，保存在方法的code属性中，为max_ stack的值
- 栈中的任何一个元素都是可以任意的Java数据类型
  - 32bit的类型占用一个栈单位深度
  - 64bit的类型占用两个栈单位深度
- 操作数栈**并非采用访问索引的方式来进行数据访问的**，而是只能通过标准的入栈（push）和出栈 (pop）操作来完成一次数据访问

***

### 1.5.5 代码追踪

```java
public void testAddOperation() {
    // byte、short、char、boolean：都以int型来保存
    byte i = 15;
    int j = 8;
    int k = i + j;
}

// 编译后
 0 bipush 15   
 2 istore_1
 3 bipush 8    
 5 istore_2
 6 iload_1
 7 iload_2
 8 iadd
 9 istore_3
10 return
```

> 现在分析下上面的指令，看哪些与操作数栈相关，哪些与局部变量表相关。

> 开始前，操作数栈、局部变量表都是空的（但是编译完，操作数栈深度，局部变量表数组长度是确定的）

> 第一步：执行` bipush 15`

![](picture/img45.png)

开始时，操作数栈、局部变量表都是空，PC寄存器记录下一个执行指令的地址0，当执行第一条指令时，将15 压入操作数栈

***

> 第二步：执行` istore_1`

![](picture/img46.png)

将操作数栈元素拿到局部变量表中（放到哪个位置呢 istore_1，说明放到数组1的位置，0的位置保存了this），栈就没有元素了，PC寄存器保存下一条指令 2

***

> 第三步：执行` bipush 8`

![](picture/img47.png)

同第一步操作

***

> 第四步：执行` istore_2`

![](picture/img48.png)

同第二步操作

***

> 第五步和第六步

![](picture/img49.png)

![](picture/img50.png)

iload_1 和 iload_2 意思分别是将局部变量表中索引1和2的数据拿出放到操作数栈中。

***

> 第七步

iadd指令意思就是，在操作数栈取出两个元素进行相加，相加的结果重新放到操作数栈中。

![](picture/img51.png)

***

> 第八步：执行` istore_3`

![](picture/img52.png)

同上面操作。最后一步是return结束

***

### 1.5.6 栈顶缓存技术（了解）

前面提过，基于栈式架构的虛拟机所使用的零地址指令更加紧凑，但完成一项操作的时候必然需要使用更多的入栈和出栈指令，这同时也就意味着将需要更多的指令分派 (instruction dispatch）次数和内存读/写次数。

由于操作数是存储在内存中的，因此频繁地执行内存读/写操作必然会影响执行速度。为了解决这个问题，Hotspot JVM 的设计者们提出了栈顶缓存 (Tos, Top-of-stack Cashina）技术，**将栈项元素全部缓存在物理CPU的寄存器中，以此降低对内存的读/写次数，提升执行引擎的执行效率。**

寄存器特点（相对栈）：指令更少，执行速度快

***

### 1.5.7 动态链接

![](picture/img53.png)

> 动态链接(或指向运行时常量池的方法引用）

- 每一个栈帧内部都包含一个指向**运行时常量池**中该栈帧所属方法的引用。包含这个引用的目的就是为了支持当前方法的代码能够实现**动态链接** (Dynamic Linking)。比如：invokedynamic指令

- 在Java源文件被编译到字节码文件中时，**所有的变量和方法引用都作为符号引用（Symbolic Reference） 保存在class文件的常量池里**。比如：描述一个方法调用了另外的其他方法时，就是通过常量池中指向方法的符号引用来表示的，那么**动态链接的作用就是为了将这些符号引用转换为调用方法的直接引用。**

***

![](picture/img54.png)

***

> 为什么需要常量池

常量池的作用，就是为了提供一些符号和常量，便于指令的识别。

***

### 1.5.8 方法的调用：解析与分派

在JVM中，将`符号引用`转换为调用方法的`直接引用`与方法的绑定机制相关

符号引用是一定要转成直接引用的了，关键是这个转换过程是在编译期间确定下来的还是运行期间确定下来的，如果编译期间确定，那么就是静态链接；如果运行期间确定，那就是动态链接。

- **静态链接**
  - 当一个字节码文件被装载进JVM内部时，如果被调用的目标方法在编译期可知，且运行期保持不变时。这种情况下将调用方法的符号引用转换为直接引用的过程称之为静态链接
- **动态链接**
  - 如果**被调用的方法在编译期无法被确定下来**，也就是说，只能够在程序运行期将调用方法的符号引用转换为直接引用，由于这种引用转换过程具备动态性，因此也就被称之为动态链接

***

> 方法的调用

对应的方法的绑定机制为：早期绑定 (Early Binding）和晚期绑定(Late Binding）。**绑定是一个字段、方法或者类在符号引用被替换为直接引用的过程，这仅仅发生一次**

- **早期绑定**

早期绑定就是指被调用的**目标方法如果在编译期可知，且运行期保持不变**时，即可将这个方法与所属的类型进行绑定，这样一来，由于明确了被调用的目标方法究竟是哪一个，因此也就可以使用静态链接的方式将符号引用转换为直接引用

- **晚期绑定**

如果**被调用的方法在编译期无法被确定下来，只能够在程序运行期根据实际的类型绑定相关的方法**，这种绑定方式也就被称之为晚期绑定。

***

> 方法的调用：虚方法与非虚方法

非虚方法：

- 如果方法在编译期就确定了具体的调用版本，这个版本在运行时是不可变的。这样的方法称为非虚方法。
- 静态方法、私有方法、final方法、实例构造器、父类方法都是非虚方法（静态方法、私有方法、final方法这三个是不能被重写的，所以就不存在后面父类对象指向子类引用，这是非虚方法；实例构造器中去调用另一个构造器也是确定的；在子类中调用父类的方法也是确定的）
- 其他方法称为虚方法

**子类对象的多态性的使用前提：1）类的继承关系 2）方法的重写**

***

虛拟机中提供了以下几条方法调用指令：

- 普通调用指令：

  1. `invokestatic`：调用静态方法，解析阶段确定唯一方法版本【非虚方法】
  2. `invokespecial`：调用<init>方法、私有及父类方法，解析阶段确定唯一方法版本【非虚方法】
  3. `invokevirtual`：调用所有虚方法
  4. `invokeinterface`：调用接口方法

- 动态调用指令

  5. `invokedynamic`：动态解析出需要调用的方法，然后执行

     

前四条指令固化在虛拟机内部，方法的调用执行不可人为干预，而invokedynamic指令则支持由用户确定方法版本。其中**invokestatic指令和invokespecial指令调用的方法称为非虛方法，其余的(final修饰的除外）称为虛方法。**

***

```java
/**
 * invokestatic指令和invokespecial指令调用的方法称为非虛方法
 */
class Father {
    public Father() {
        System.out.println("father的构造器");
    }
    public static void showStatic(String str) {
        System.out.println("father " + str);
    }
    public final void showFinal() {
        System.out.println("father show final");
    }
    public void showCommon() {
        System.out.println("father 普通方法");
    }
}
public class Son extends Father{
    public Son() {
        // invokespecial
        super();
    }
    public Son(int age) {
        // invokespecial
        this();
    }
    public static void showStatic(String str) {
        System.out.println("son " + str);
    }
    private void showPrivate(String str) {
        System.out.println("son private " + str);
    }
    public void info() {
    }
    public void show() {
        // invokestatic
        showStatic("Wing");
        // invokestatic
        super.showStatic("good!");
        // invokespecial
        showPrivate("hello!");
        // invokespecial
        super.showCommon();
        // invokevirtual
        showFinal();  // 因为此方法声明有final，不能被子类重写，所以也认为此方法是非虚方法
        // invokevirtual 编译时确定不下来，因为子类可能重写该方法
        showCommon();
        info();

        MethodInterface in = null;
        // invokeinterface
        in.methodA();
    }
    public static void main(String[] args) {
        Son so = new Son();
        so.show();
    }
}
interface MethodInterface {
    void methodA();
}

// 字节码指令
 0 ldc #11 <Wing>
 2 invokestatic #12 <org/example/chapter05/Son.showStatic : (Ljava/lang/String;)V>
 5 ldc #13 <good!>
 7 invokestatic #14 <org/example/chapter05/Father.showStatic : (Ljava/lang/String;)V>
10 aload_0
11 ldc #15 <hello!>
13 invokespecial #16 <org/example/chapter05/Son.showPrivate : (Ljava/lang/String;)V>
16 aload_0
17 invokespecial #17 <org/example/chapter05/Father.showCommon : ()V>
20 aload_0
21 invokevirtual #18 <org/example/chapter05/Son.showFinal : ()V>
24 aload_0
25 invokevirtual #19 <org/example/chapter05/Son.showCommon : ()V>
28 aload_0
29 invokevirtual #20 <org/example/chapter05/Son.info : ()V>
32 aconst_null
33 astore_1
34 aload_1
35 invokeinterface #21 <org/example/chapter05/MethodInterface.methodA : ()V> count 1
40 return

```

***

> 方法的调用：关于invokedynamic指令

- JVM字节码指令集一直比较稳定，一直到Java7中才增加了一个 invokedynamic指令，这是**Java为了实现「动态类型语言」支持而做的一种改进**
- 但是在Java7中并没有提供直接生成invokedynamic指令的方法，需要借助ASM这种底层字节码工具来产生invokedynamic指令。**直到Java8的Lambda。 表达式的出现，invokedynamic指令的生成，在Java中才有了直接的生成方式**
- Java7中增加的动态语言类型支持的本质是对Java虚拟机规范的修改，而不是对Java语言规则的修改，这一块相对来讲比较复杂，增加了虚拟机中的方法调用，最直接的受益者就是运行在Java半台的动态语言的编译器

***

> 方法的调用：方法重写的本质

Java 语言中方法重写的本质：

1. 找到操作数栈顶的第一个元素所执行的对象的实际类型，记作C。
2. 如果在过程结束：如果不通类型 C 中找到与常量中`描述符`和`简单名称`都相符的方法，则进行访问权限校验，如果通过则返回这个方法的直接引用，查找过，则返回 java.lang. IIIegalAccessError 异常
3. 否则，按照继承关系从下往上依次对 C 的各个父类进行第 2步的搜索和验证过程
4. 如果始终没有找到合适的方法，则抛出 Java.lang.AbstractMethodError异常

***

> 方法的调用：虚方法表（避免每次找一个方法都要循环从当前类找到对应的父类，虚方法表直接记录方法指向对于的父类，这样就不用每次循环找了）

- 在面向对象的编程中，会很频繁的使用到动态分派，如果在每次动态分派的过程中都要重新在类的方法元数据中搜索合适的目标的话就可能影响到执行效率。因此，为了提高性能，JVM采用在类的方法区建立一个虚方法表virtual method table）（非虚方法不会出现在表中）来实现。**使用索引表来代替查找**
- 每个类中都有一个虚方法表，表中存放着各个方法的实际入口。
- ﻿那么虚方法表什么时候被创建？【虛方法表会在类加载的链接-解析阶段被创建并开始初始化，类的变量初始值准备完成之后，JVM会把该类的方法表也初始化完毕】

***

### 1.5.9 方法返回地址

![](picture/img53.png)

> 方法返回地址return address

- 存放调用该方法的pc寄存器的值
- 一个方法的结束，有两种方式：
  - 正常执行完成
  - 出现未处理的异常，非正常退出
- 无论通过哪种方式退出，在方法退出后都返回到该方法被调用的位置。方法正常退出时，**调用者的pc计数器的值作为返回地址，即调用该方法的指令的下一条指令的地址**。而通过异常退出的，返回地址是要通过异常表来确定，栈帧中一般不会保存这部分信息
- 本质上，方法的退出就是当前栈帧出栈的过程。此时，需要恢复上层方法的局部变量表、操作数栈、将返回值压入调用者栈帧的操作数栈、设置PC寄存器值等，让调用者方法继续执行下去。
- 正常完成出口和异常完成出口的区别在于：通过异常完成出口退出的不会给他的上层调用者产生任何的返回值

***

### 1.5.10 一些附加信息

略。

***

### 1.5.11 栈的相关面试题

> 1 举例栈溢出的情況？ StackOverFlowError

如果已经设置了固定大小的栈空间了，那么如果栈空间不足，会爆 StackOverFlowError；

如果允许栈空间动态扩展（不足时会申请扩容），如果没有可申请的空间，爆 OOM异常

***

> 2 调整栈大小，就能保证不出现溢出吗？

不一定，比如递归时，调整大小前 5000次就溢出，调整后 6000次才溢出，这只是溢出延后了，但是最终也还是溢出了。也可能调整后，刚好不溢出也是可能的

***

> 3 分配的栈内存越大越好吗？

不是

> 4 垃圾回收是否会涉及到虚拟机栈？

不会

> 5 方法中定义的局部变量是否线程安全？

见下面代码

具体问题具体分析

***

![](picture/img56.png)

|            | GC   | Error |
| ---------- | ---- | ----- |
| 程序计数器 | x    | x     |
| 虚拟机栈   | x    | √     |
| 本地方法栈 | x    | √     |
| 堆         | √    | √     |
| 方法区     | √    | √     |

```java
// s1的声明方式是线程安全的，因为它是在方法内部new的，每个线程都有自己的
public static void m1() {
  StringBuilder s1 = new StringBuilder();
  s1.append("a");
  s1.append("b");
}

// s的操作过程是线程不安全的，因为s是外面传进来的，可能s是多个线程共享的数据。
public static void m2(StringBuilder s) {
  s.append("a");
  s.append("b");
}

// s1的不是线程安全的，因为它被返回出去有可能被其他线程使用
public static StringBuilder m3() {
  StringBuilder s1 = new StringBuilder();
  s1.append("a");
  s1.append("b");
  return s1;
}

// s1的声明方式是线程安全的
public static String m4() {
  StringBuilder s1 = new StringBuilder();
  s1.append("a");
  s1.append("b");
  return s1.toString();
}
```

> 总结：对象在方法内部产生，内部消亡的，是线程安全的；如果对象是传进来，或者对象是内部产生，但是被返回出去的，就不是线程安全的。

***

## 1.6 本地方法接口

![](picture/img57.png)

***

> 什么是本地方法

简单地讲，**一个Native Method就是一个Java调用非Java代码的接口**。一个 Native Method是这样一个Java方法：该方法的实现由非Java语言实现，比如 C。这个特征并非Java所特有，很多其它的编程语言都有这一机制，比如在C++中，你可以用extern "c" 告知C++ 编译器去调用一个 C 的函数。

在定义一个native method时，并不提供实现体（有些像定义一个Java interface），因为其实现体是由非java语言在外面实现的

本地接口的作用是融合不同的编程语言为Java所用，它的初表是融合 C/C++程序

***

> 为什么要使用Native Method？

Java使用起来非常方便，然而有些层次的任务用Java实现起来不容易，或者我们对程序的效率很在意时，问题就来了。



- 与Java环境外交互：

**有时Java应用需要与Java外面的环境交互，这是本地方法存在的主要原因。**

你可以想想Java需要与一些底层系统，如操作系统或某些硬件交换信息时的情况。本地方法正是这样一种交流机制：它为我们提供了一个非常简洁的接口，而且我们无需去了解Java应用之外的繁琐的细节。

- 与操作系统交互：

JVM支持着Java语言本身和运行时库，它是Java程序赖以生存的平台，它由一个解释器（解释字节码）和一些连接到本地代码的库组成。然而不管怎样，它毕竟不是一个完整的系统，它经常依赖手一些底层系统的文持。这些底层系统常常是强大的操作系统。**通过使用本地方法，我们得以用Java实现了jre的与底层系统的交互，甚至 JVM的一些部分就是用c写的。还有，如果我们要使用一些Java语言本身没有提供封装的操作系统的特性时，我们也需要使用本地方法**

***

## 1.7 本地方法栈

- **Java虚拟机栈用于管理Java方法的调用，而本地方法栈用于管理本地方法的调用**
- 本地方法栈，也是线程私有的
- 允许被实现成固定或者是可动态扩展的内存大小。（在内存溢出方面是相同的）
  - 如果线程请求分配的栈容量超过本地方法栈允许的最大容量，Java虚拟机将会抛出一个 stackoverflowError异常
  - 如果本地方法栈可以动态扩展，并且在尝试扩展的时候无法申请到足够的内存，或者在创建新的线程时没有足够的内存去创建对应的本地方法栈，那么Java虛拟机将会抛出一个OutOfMemoryError 异常
- 本地方法是使用C语言实现的
- 它的具体做法是Native Method stack中登记native方法，在 Execution Engine 执行时加载本地方法库

***



## 1.8 堆

### 1.8.1 堆的核心概述

堆针对一个JVM进程来说是唯一的，也就是一个进程只有一个JVM，但是进程包含多个线程，他们是共享同一堆空间的。

![](picture/img72.png)

- 一个JVM实例只存在一个堆内存，堆也是Java内存管理的核心区域。
- Java 堆区在 JVM 启动的时候即被创建，其空间大小也就确定了。是 JVM 管理的最大一块内存空间
  - 堆内存的大小是可以调节的
- 《Java虛拟机规范》规定，堆可以处于**物理上不连续**的内存空间中，但在**逻辑上它应该被视为连续的**
- 所有的线程共享 Java 堆，在这里还可以划分线程私有的缓冲区 (Thread Local Allocation Buffer, TIAB)（因为如果堆都是共享的话，会存在线程问题，所以堆又划分出一小块区域是线程私有的，每个线程一份）
- “几乎”所有的对象实例都在这里分配内存
- 数组和对象可能永远不会存储在栈上，因为栈帧中保存引用，这个引用指向对象或者数组在堆中的位置
- 在方法结束后，堆中的对象不会马上被移除，仅仅在垃圾收集的时候才会被移除
- 堆，是GC （ Garbage collection，垃圾收集器）执行垃圾回收的重点区域

![](picture/img73.png)

***

> 堆内存细分

Java 7 及之前堆内存**逻辑上**分为三部分：**新生区+养老区+<font color="red">永久区</font>**

- Young Generation Space                       新生区   Young/New 
  - 又被划分为Eden区和Survivor区

- Tenure generation space                      养老区 Old/Tenure

- Permanent Space                                   永久区 Perm

***

Java 8及之后堆内存**逻辑上**分为三部分：**新生区+养老区+<font color="red">元空间</font>**

- Young Generation Space                       新生区 Young/New 
  - 又被划分为Eden区和Survivor区

- Tenure generation space                      养老区 Old/Tenure

- Meta Space                                              元空间 Meta

**约定**：新生区（代）<=>年轻代 、 养老区<=>老年区（代）、 永久区<=>永久代

***

> 堆空间内部结构 JDK 7

![](picture/img74.png)



> 堆空间内部结构 JDK 8（永久代 PERMGEN被替换成了元空间METASPACE）

![](picture/img75.png)



***

### 1.8.2 设置堆内存大小与OOM

> 堆空间大小的设置

- Java堆区用于存储Java对象实例，那么堆的大小在JVM启动时就已经设定好了，大家可以通过选项"-Xmx"和"-Xms"来进行设置
  - “-Xms"用于表示堆区的起始内存，等价于`-XX:InitialHeapSize`
  - “-Xmx"则用于表示堆区的最大内存，等价于`-XX:MaxHeapSize`
- 一旦堆区中的内存大小超过“-Xmx"所指定的最大内存时，将会抛出OutOfMemoryError异常
- 通常会将-Xms和-Xmx两个参数配置相同的值，其**目的是为了能够在ava垃圾回收机制清理完堆区后不需要重新分隔计算堆区的大小，从而提高性能**
- 默认情况下
  - 初始内存大小：物理电脑内存大小 / 64
  - 最大内存大小：物理电脑内存大小 / 4

***

```java
/**
 * 1. 设置堆空间大小的参数
 * -Xms 用来设置堆空间（年轻代+老年代）的初始内存大小
 *      -X：是 JVM 的运行参数
 *      ms：是 memory start
 * -Xmx 用来设置堆空间（年轻代+老年代）的最大内存大小
 *
 * 2. 默认堆空间大小
 *    初始内存大小：物理电脑内存大小 / 64
 *    最大内存大小：物理电脑内存大小 / 4
 *
 * 3. 手动设置：-Xms600m -Xmx600m
 *        开发中建议将初始值内存和最大的堆内存设置成相同的值
 *
 * 4.查看设置的参数：
 *        方式一：jps   /  jstat -gc 进程id
 *        方式二：-xx:+PrintGCDetails
 */
public class HeapSpaceInitial {
    public static void main(String[] args) {
        long initialMemory = Runtime.getRuntime().totalMemory() / 1024 / 1024;
        long maxMemory = Runtime.getRuntime().maxMemory() / 1024 / 1024;
        System.out.println("-Xms:" + initialMemory + "M");
        System.out.println("-Xmx:" + maxMemory + "M");

        System.out.println("系统内存大小为:" + initialMemory * 64.0 / 1024 + "G");
        System.out.println("系统内存大小为:" + maxMemory * 4.0 / 1024 + "G");
    }
}
```

> OutOfMemory举例

```java
public class OOMTest {
    public static void main(String[]args){
        ArrayList<Picture> list = new ArrayList<>();
        while(true){
            try {
                Thread.sleep(20);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            list.add(new Picture(new Random().nextInt(1024*1024)));
        }
    }
}

Exception in thread "main" java.lang.OutofMemoryError: Java heap space
    at com.atguigu. java.Picture.<init>(OOMTest. java:25)
    at com.atguigu.java.O0MTest.main(OOMTest.java:16)
```

***

### 1.8.3 年轻代与老年代

堆空间由年轻代与老年代构成

- 存储在JVM中的Java对象可以被划分为两类：
  - 一类是生命周期较短的瞬时对象，这类对象的创建和消亡都非常迅速
  - 另外一类对象的生命周期却非常长，在某些极端的情况下还能够与JVM的生命周期保持一致
- Java堆区进一步细分的话，可以划分为年轻代（YoungGen）和老年代（oldGen）
- 其中年轻代又可以划分为Eden空间、Survivor0空间和Survivor1空间（有时也叫做from区、to区）

![](picture/img76.png)

**白话解释**：对象最开始创建的位置在`Eden（伊甸园区）`，后续GC`Eden`时，有的对象回收成功，而有的对象还存活着，那`Eden`中的对象会被放到 `Survivor （幸存者 区）`（具体0区还是1区后面讲），如果幸存者区的对象一定时间后还继续存活，那么会放到`Old Gen（老年区）`

***

下面这参数开发中一般不会调：

![](picture/img77.png)

- 配置新生代与老年代在堆结构的占比
  - 默认`-XX:NewRatio=2`，表示新生代占1，老年代占2，新生代占整个堆的1/3
  - 可以修改`-XX:NewRatio=4`，表示新生代占1，老年代占4，新生代占整个堆的1/5
- 在HotSpot中，Eden空间和另外两个survivor空间缺省所占的比例是8:1:1
  - `-XX:-UseAdaptivesizePolicy` :关闭自适应的内存分配策略(暂时用不到) 不然看不到8:1:1
- 当然开发人员可以通过选项“`-xx:SurvivorRatio`”调整这个空间比例。比如`-xx:SurvivorRatio=8`
- **几乎所有**的Java对象都是在Eden区被new出来的。（假如new的对象特别大，Eden都放不下，就只能直接在老年代存放了）
- 绝大部分的Java对象的销毁都在新生代进行了
  - IBM公司的专门研究表明，新生代中80%的对象都是“朝生夕死”的。
- 可以使用选项"`-Xmn`"设置新生代最大内存大小（这个是具体指明多大内存，那如果上面也设置了比例呢，怎么知道用多大？如果设置了具体大小，那就按这个设置为准了，上面设置的比例就失效了）
  - 这个参数一般使用默认值就可以了。

***

![](picture/img78.png)

***

### 1.8.4 图解对象分配过程

​        为新对象分配内存是一件非常严谨和复杂的任务，JVM 的设计者们不仅需要考虑内存如何分配、在哪里分配等问题，并且由于内存分配算法与内存回收算法密切相关，所以还需要考虑GC执行完内存回收后是否会在内存空间中产生内存碎片

1.  new的对象先放伊甸园区。此区有大小限制。 
2. 当伊甸园的空间填满时，程序又需要创建对象，JVM 的垃圾回收器将对伊甸园区进行垃圾回收（MinorGC），将伊甸园区中的不再被其他对象所引用的对象进行销毁。再加载新的对象放到伊甸园区 
3. 然后将伊甸园中的剩余对象移动到幸存者0区。 
4. 如果再次触发垃圾回收，此时上次幸存下来的放到幸存者0区的，如果没有回收，就会放到幸存者1区
5. 如果再次经历垃圾回收，此时会重新放回幸存者0区，接着再去幸存者1区。 
6. 啥时候能去养老区呢？可以设置次数。默认是15次
   - 可以设置参数：进行设置`-Xx:MaxTenuringThreshold= N`
7. 在养老区，相对悠闲。当养老区内存不足时，再次触发GC：Major GC，进行养老区的内存清理
8. 若养老区执行了Major GC之后，发现依然无法进行对象的保存，就会产生OOM异常

***

![](picture/img79.png)

​        Eden满了，如果再次new对象，那么会触发MinorGC，经过GC后，红色被清理了，而绿色还存活，那么把这两个提升到幸存者0区，刚从Eden到幸存者区的，都有个age记录，刚过来值是1。最终Eden就被清空了，S0有两个对象

![](picture/img80.png)

​         Eden又满了，如果再次new对象，那么会继续触发MinorGC，那么这时会放到空的幸存者1区，**顺便判断幸存者0区的两个对象是否还存活**，不存活的话就会被GC，如果还继续存活那么幸存者0区的也会拿过来，这时Eden和幸存者0区都空了，而幸存者1区有三个对象（加入0区的两个还继续存活），但age不同了。

![](picture/img81.png)

​        循环上面过程，直到幸存者的age达到阈值，那么就会提升到老年代。



**重点：**

**1）什么时候会触发MinorGC呢？**

**答：Eden满的时候会触发。**

**2）那么S0或S1区满的时候是否触发MinorGC呢？毕竟S区空间是比较小的**

**答：不会触发**

**3）那S区什么时候才进行MinorGC**

**答：当Eden满的时候触发MinorGC时，GC伊甸园区时，也会GC 幸存者区**

**4）有没可能没有到临界值 15 就提升到 老年区呢？**

**答：有**

**5）有没可能刚new的对象直接到老年区呢？**

**答：有，当存一个超大对象时，发现Eden放不下，那么会触发YGC，GC之后还是放不下（GC后Eden是空的），说明是超大对象，Eden全部空间都放不下，那么直接放到老年区但是假如老年区也放不下，那么会触发FGC，然后再放到老年区，如果FGC后老年区还是放不下，就爆OOM异常**。

***

> 流程图

![](picture/img82.png)

流程图有几个晋升老年区的特殊情况：

1. 如果刚new一个大对象时，Eden放不下，执行GC之后还是放不下，此时会直接将对象放到老年区（如果老年区还放不下，那么先执行老年区的FGC，然后放进去，如果还放不进去，报 OOM）
2. 如果Eden已经保存了很多对象，当Eden满了执行 YGC 时，需要回收Eden，剩下不能回收的对象需要从 Eden 保存到 幸存者区，但此时幸存者也满了，所以直接将Eden还幸存的对象保存到老年区。

> 代码

![](picture/img84.png)

> 根据图表示数据存放情况（横坐标-时间，纵坐标-数据量）

![](picture/img83.png)

***

> 总结

关于垃圾回收：频繁在新生区收集，很少在老年代收集，几乎不再永久代和元空间进行收集

***

**常用调优工具（在JVM下篇：性能监控与调优篇会详细介绍）**

- JDK命令行

- Eclipse:Memory Analyzer Tool

- Jconsole

- VisualVM

- Jprofiler

- Java Flight Recorder

- GCViewer

- GC Easy

***

### 1.8.5 Minor GC、Major GC、Full GC

**JVM 调优目的就是减少 GC**，因为GC太频繁会影响用户线程，GC时，会暂停用户线程，GC完之后才会重启用户线程。

JVM 在进行GC时，并非每次都对上面三个内存区域（新生代、老年代；方法区）一起回收的，大部分时候回收的都是指新生代

***

针对Hotspot VM的实现，它里面的GC按照回收区域又分为两大种类型：一种是**部分收集（Partial GC）**，一种是**整堆收集（FullGC）**

- 部分收集：不是完整收集整个Java堆的垃圾收集。其中又分为： 

- - 新生代收集（Minor GC / Young GC）：只是新生代（Eden\S0,S1）的垃圾收集

- - 老年代收集（Major GC / Old GC）：只是老年代的垃圾收集。 

- - - 目前，只有CMS GC 会有单独收集老年代的行为。

- - - **注意，很多时候Major GC会和Full GC混淆使用，需要具体分辨是老年代回收还是整堆回收。**

- - 混合收集（MixedGC）：收集整个新生代以及部分老年代的垃圾收集。 

- - - 目前，只有G1 GC会有这种行为

- 整堆收集（Full GC）：收集整个java堆和方法区的垃圾收集。

***

>  最简单的分代式GC策略的触发条件

**年轻代GC（Minor GC）触发机制**

- 当年轻代空间不足时，就会触发MinorGC，这里的年轻代满指的是**Eden代满**，**Survivor满不会引发GC**。（每次Minor GC会清理年轻代的内存。） 
- 因为Java对象大多都具备朝生夕灭的特性.，所以Minor GC非常频繁，一般回收速度也比较快。这一定义既清晰又易于理解。 
- Minor GC会引发STW（stop the world）（暂停其它用户的线程，等垃圾回收结束，用户线程才恢复运行 ）

***

**老年代GC（Major GC / Full GC）触发机制**

-  指发生在老年代的GC，对象从老年代消失时，我们说 “Major GC” 或 “Full GC” 发生了 

-  出现了Major Gc，经常会伴随至少一次的Minor GC（但非绝对的，在Paralle1 Scavenge收集器的收集策略里就有直接进行MajorGC的策略选择过程） 

- - 也就是在老年代空间不足时，会先尝试触发Minor Gc。如果之后空间还不足，则触发Major GC

-  Major GC的速度一般会比Minor GC慢10倍以上，STW的时间更长 

-  如果Major GC后，内存还不足，就报OOM了 

***

**Full GC触发机制（后面细讲）**

触发Full GC执行的情况有如下五种：

1. 调用System.gc()时，系统建议执行Full GC，但是不必然执行
2. 老年代空间不足
3. 方法区空间不足
4. 通过Minor GC后进入老年代的平均大小大于老年代的可用内存
5. 由Eden区、survivor space0（From Space）区向survivor space1（To Space）区复制时，对象大小大于To Space可用内存，则把该对象转存到老年代，且老年代的可用内存小于该对象大小

**说明：Full GC 是开发或调优中尽量要避免的。这样暂时时间会短一些**

***

### 1.8.6 堆空间分代思想

为什么要把 Java 堆分代？不分代就不能正常工作了吗？

- 经研究，不同对象的生命周期不同。70%-99%的对象是临时对象。
  - 新生代：有Eden、两块大小相同的survivor（又称为from/to，s0/s1）构成，to总为空
  - 老年代：存放新生代中经历多次GC仍然存活的对象

![](picture/img85.png)

​        其实不分代完全可以，分代的唯一理由就是优化GC性能。如果没有分代，那所有的对象都在一块，就如同把一个学校的人都关在一个教室。GC的时候要找到哪些对象没用，这样就会对堆的所有区域进行扫描。而很多对象都是朝生夕死的，如果分代的话，把新创建的对象放到某一地方，当GC的时候先把这块存储“朝生夕死”对象的区域进行回收，这样就会腾出很大的空间出来。

![](picture/img86.png)

***

### 1.8.7 内存分配策略

如果对象在Eden出生并经过第一次Minor GC后仍然存活，并且能被Survivor容纳的话，将被移动到survivor空间中，并将对象年龄设为1。对象在survivor区中每熬过一次MinorGC，年龄就增加1岁，当它的年龄增加到一定程度（默认为15岁，其实每个JVM、每个GC都有所不同）时，就会被晋升到老年代

对象晋升老年代的年龄阀值，可以通过选项`-XX:MaxTenuringThreshold`来设置

针对不同年龄段的对象分配原则如下所示：

- 优先分配到Eden

- 大对象直接分配到老年代（尽量避免程序中出现过多的大对象）

- 长期存活的对象分配到老年代

- 动态对象年龄判断：如果survivor区中相同年龄（假设age=5）的所有对象大小的总和大于Survivor空间的一半，年龄（假设age1=5）大于或等于该年龄（age=5）的对象可以直接进入老年代，无须等到`MaxTenuringThreshold`中要求的年龄。

- 空间分配担保： `-XX:HandlePromotionFailure`

***

> 演示大对象直接进入老年代

![](picture/img87.png)

***

### 1.8.8 为对象分配内存：TLAB

为什么有TLAB（Thread Local Allocation Buffer）？

-  **堆区是线程共享区域，任何线程都可以访问到堆区中的共享数据** （说明可以很方便的实现线程间的通讯）

-  由于对象实例的创建在JVM中非常频繁，因此在并发环境下从堆区中划分内存空间是线程不安全的 

-  为避免多个线程操作同一地址，需要使用加锁等机制，进而影响分配速度。 

***

什么是TLAB？

-  从内存模型而不是垃圾收集的角度，对Eden区域继续进行划分，**JVM为每个线程分配了一个私有缓存区域**，它包含在Eden空间内。 

-  多线程同时分配内存时，使用TLAB可以避免一系列的非线程安全问题，同时还能够提升内存分配的吞吐量，因此我们可以将这种内存分配方式称之为**快速分配策略**。 

-  据我所知所有OpenJDK衍生出来的JVM都提供了TLAB的设计。

***

![](picture/img89.png)

TLAB的再说明:

- 尽管不是所有的对象实例都能够在TLAB中成功分配内存，但**JVM确实是将TLAB作为内存分配的首选。 **

-  在程序中，开发人员可以通过选项“`-XX:UseTLAB`”设置是否开启TLAB空间。 

-  **默认情况下**，TLAB空间的内存非常小，**仅占有整个Eden空间的1%**，当然我们可以通过选项 “`-XX:TLABWasteTargetPercent`” 设置TLAB空间所占用Eden空间的百分比大小。 

-  一旦对象在TLAB空间分配内存失败时，JVM就会尝试着通过使用加锁机制确保数据操作的原子性，从而直接在Eden空间中分配内存。 

***

> 对象分配过程

![](picture/img90.png)

***

### 1.8.9 小结堆空间的参数设置

```java
// 详细的参数内容会在JVM下篇：性能监控与调优篇中进行详细介绍，这里先熟悉下
-XX:+PrintFlagsInitial  //查看所有的参数的默认初始值
-XX:+PrintFlagsFinal  //查看所有的参数的最终值（可能会存在修改，不再是初始值）
-Xms  //初始堆空间内存（默认为物理内存的1/64）
-Xmx  //最大堆空间内存（默认为物理内存的1/4）
-Xmn  //设置新生代的大小。（初始值及最大值）
-XX:NewRatio  //配置新生代与老年代在堆结构的占比
-XX:SurvivorRatio  //设置新生代中Eden和S0/S1空间的比例
-XX:MaxTenuringThreshold  //设置新生代垃圾的最大年龄
-XX:+PrintGCDetails //输出详细的GC处理日志
//打印gc简要信息：①-Xx：+PrintGC ② - verbose:gc
-XX:HandlePromotionFalilure：//是否设置空间分配担保
```

在发生Minor GC之前，虚拟机会  **检查老年代最大可用的连续空间是否大于新生代所有对象的总空间**。

- 如果大于，则此次Minor GC是安全的

- 如果小于，则虚拟机会查看`-XX:HandlePromotionFailure`设置值是否允担保失败。 

- - 如果`HandlePromotionFailure=true`，那么会继续 **检查老年代最大可用连续空间是否大于历次晋升到老年代的对象的平均大小。 **

- - - 如果大于，则尝试进行一次Minor GC，但这次Minor GC依然是有风险的；

- - - 如果小于，则改为进行一次Full GC。

- - 如果`HandlePromotionFailure=false`，则改为进行一次Full Gc。

​        在JDK6 Update24（JDK7）之后，HandlePromotionFailure参数不会再影响到虚拟机的空间分配担保策略（说白了就将HandlePromotionFailure看成是 true），观察openJDK中的源码变化，虽然源码中还定义了HandlePromotionFailure参数，但是在代码中已经不会再使用它。JDK6 Update 24之后的规则变为**只要老年代的连续空间大于新生代对象总大小**或者**历次晋升的平均大小就会进行Minor GC**，否则将进行FullGC。

***

### 1.8.10 堆是分配对象的唯一选择吗

​        在《深入理解Java虚拟机》中关于Java堆内存有这样一段描述：随着JIT编译期的发展与**逃逸分析技术**逐渐成熟，**栈上分配、标量替换优化技术**将会导致一些微妙的变化，所有的对象都分配到堆上也渐渐变得不那么“绝对”了。

​          在Java虚拟机中，对象是在Java堆中分配内存的，这是一个普遍的常识。但是，有一种特殊情况，那就是**如果经过逃逸分析（Escape Analysis）后发现，一个对象并没有逃逸出方法的话，那么就可能被优化成栈上分配**.。这样就无需在堆上分配内存，也无须进行垃圾回收了。这也是最常见的堆外存储技术。

​          此外，前面提到的基于OpenJDK深度定制的TaoBaoVM，其中创新的GCIH（GC invisible heap）技术实现off-heap，将生命周期较长的Java对象从heap中移至heap外，并且GC不能管理GCIH内部的Java对象，以此达到降低GC的回收频率和提升GC的回收效率的目的。

***

> 逃逸分析概述

- 如何将堆上的对象分配到栈，需要使用逃逸分析手段。

- 这是一种可以有效减少Java程序中同步负载和内存堆分配压力的跨函数全局数据流分析算法。

- 通过逃逸分析，Java Hotspot编译器能够分析出一个新的对象的引用的使用范围从而决定是否要将这个对象分配到堆上。

- 逃逸分析的基本行为就是分析对象动态作用域：
  - 当一个对象在方法中被定义后，对象只在方法内部使用，则认为没有发生逃逸。
  - 当一个对象在方法中被定义后，它被外部方法所引用，则认为发生逃逸。例如作为调用参数传递到其他地方中。

***

> 举例1

```java
// V 只在方法内使用，没有发生逃逸，所以可以在栈上分配
public void my_method() {
    V v = new V();
    // use v
    // ....
    v = null;
}
```

没有发生逃逸的对象，则可以分配到栈上，随着方法执行的结束，栈空间就被移除，每个栈里面包含了很多栈帧

```java
public static StringBuffer createStringBuffer(String s1, String s2) {
    StringBuffer sb = new StringBuffer();
    sb.append(s1);
    sb.append(s2);
    return sb;
}
```

sb对象逃逸了，因为返回出去了，如果想要`StringBuffer sb`不发生逃逸，可以这样写

```java
public static String createStringBuffer(String s1, String s2) {
    StringBuffer sb = new StringBuffer();
    sb.append(s1);
    sb.append(s2);
    return sb.toString();
}
```

***

> 举例2

```java
public class EscapeAnalysis {

    public EscapeAnalysis obj;

    /**
     * 方法返回EscapeAnalysis对象，发生逃逸
     * @return
     */
    public EscapeAnalysis getInstance() {
        return obj == null ? new EscapeAnalysis() : obj;
    }

    /**
     * 为成员属性赋值，发生逃逸
     */
    public void setObj() {
        this.obj = new EscapeAnalysis();
    }

    /**
     * 对象的作用于仅在当前方法中有效，没有发生逃逸
     */
    public void useEscapeAnalysis() {
        EscapeAnalysis e = new EscapeAnalysis();
    }

    /**
     * 引用成员变量的值，发生逃逸
     */
    public void useEscapeAnalysis2() {
        EscapeAnalysis e = getInstance();
    }
}
```

**参数设置**

在JDK 6u23 版本之后，HotSpot中默认就已经开启了逃逸分析

如果使用的是较早的版本，开发人员则可以通过：

- 选项“`-XX:+DoEscapeAnalysis`"显式开启逃逸分析

- 通过选项“`-XX:+PrintEscapeAnalysis`"查看逃逸分析的筛选结果

***

**结论**：开发中能使用局部变量的，就不要使用在方法外定义。

***

> 逃逸分析：代码优化

使用逃逸分析，编译器可以对代码做如下优化：

**一、栈上分配：**将堆分配转化为栈分配。如果一个对象在子程序中被分配，要使指向该对象的指针永远不会发生逃逸，对象可能是栈上分配的候选，而不是堆上分配

**二、同步省略：**如果一个对象被发现只有一个线程被访问到，那么对于这个对象的操作可以不考虑同步。

**三、分离对象或标量替换：**有的对象可能不需要作为一个连续的内存结构存在也可以被访问到，那么对象的部分（或全部）可以不存储在内存，而是存储在CPU寄存器中。

***

> 栈上分配

JIT编译器在编译期间根据逃逸分析的结果，发现如果一个对象并没有逃逸出方法的话，就可能被优化成栈上分配。分配完成后，继续在调用栈内执行，最后线程结束，栈空间被回收，局部变量对象也被回收。这样就无须进行垃圾回收了。

**常见的栈上分配的场景**

- 在逃逸分析中，已经说明了。分别是给成员变量赋值、方法返回值、实例引用传递。

***

> 测试（alloc是创建一个对象）

![](picture/img91.png)

第一次关闭逃逸分析（不实用栈上分配）,说明创建的对象都保存到堆中

![](picture/img92.png)

![](picture/img93.png)

第二次修改参数，启动逃逸分析（改成加号）

![](picture/img94.png)

![](picture/img95.png)

***

> 同步省略

线程同步的代价是相当高的，同步的后果是降低并发性和性能。

在动态编译同步块的时候，JIT编译器可以借助逃逸分析来**判断同步块所使用的锁对象是否只能够被一个线程访问而没有被发布到其他线程**。如果没有，那么JIT编译器在编译这个同步块的时候就会取消对这部分代码的同步。这样就能大大提高并发性和性能。这个取消同步的过程就叫同步省略，也叫**锁消除。**

```java
// 同步的目的是共享数据，然后用同一把锁锁住，但是这里的锁（hellis）每个线程都创建新的一个，根本都不是同一个锁，没有意义
public void f() {
    Object hellis = new Object();   
    synchronized(hellis) {
        System.out.println(hellis);
    }
}
```

代码中对hellis这个对象加锁，但是hellis对象的生命周期只在f()方法中，并不会被其他线程所访问到，所以在JIT编译阶段就会被优化掉，优化成：

```java
public void f() {
    Object hellis = new Object();
	  System.out.println(hellis);
}
```

> 看字节码

![](picture/img96.png)

问：刚不是说JIT编译器在处理时会将代码优化，去掉这个同步关键字吗，为什么在字节码还能看到同步关键字的字节码指令？

答：编译成指令还是会有同步的身影的，上面说的**栈上分配，同步省略，是将字节码文件加载到内存中以后才进行的优化**，这个要注意。



***

> 分离对象或标量替换

标量（scalar）是指一个无法再分解成更小的数据的数据。Java中的原始数据类型就是标量

相对的，那些还可以分解的数据叫做聚合量（Aggregate），Java中的对象就是聚合量，因为他可以分解成其他聚合量和标量。

在JIT阶段，如果经过逃逸分析，发现一个对象不会被外界访问的话，那么经过JIT优化，就会**把这个对象拆解成若干个其中包含的若干个成员变量来代替。这个过程就是标量替换。**

> 举例

```java
public static void main(String args[]) {
    alloc();
}
private static void alloc() {
    Point point = new Point(1,2);
    System.out.println("point.x" + point.x + ";point.y" + point.y);
}
class Point {
    private int x;
    private int y;
}
```

```java
private static void alloc() {
    int x = 1;
    int y = 2;
    System.out.println("point.x = " + x + "; point.y=" + y);
}
```

可以看到，Point这个聚合量经过逃逸分析后，发现他并没有逃逸，就被替换成两个标量了。那么标量替换有什么好处呢？就是可以大大减少堆内存的占用。因为一旦不需要创建对象了，那么就不再需要分配堆内存了。 标量替换为栈上分配提供了很好的基础。

***

**标量替换参数设置**

参数-XX:EliminateAllocations：开启了标量替换（默认打开），允许将对象打散分配到栈上。

上述代码在主函数中进行了1亿次alloc。调用进行对象创建，由于User对象实例需要占据约16字节的空间，因此累计分配空间达到将近1.5GB。如果堆空间小于这个值，就必然会发生GC。使用如下参数运行上述代码：

```java
-server -Xmx100m -Xms100m -XX:+DoEscapeAnalysis -XX:+PrintGC -XX:+EliminateAllocations
```

这里设置参数如下：

- 参数`-server`：启动Server模式，因为在server模式下，才可以启用逃逸分析。

- 参数`-XX:+DoEscapeAnalysis`：启用逃逸分析

- 参数`-Xmx10m`：指定了堆空间最大为10MB

- 参数`-XX:+PrintGC`：将打印Gc日志

- 参数`-XX:+EliminateAllocations`：开启了标量替换（默认打开），允许将对象打散分配在栈上，比如对象拥有id和name两个字段，那么这两个字段将会被视为两个独立的局部变量进行分配

***

> 逃逸分析小结：逃逸分析并不成熟

- 关于逃逸分析的论文在1999年就已经发表了，但直到JDK1.6才有实现，而且这项技术到如今也并不是十分成熟。
- 其根本原因就是**无法保证逃逸分析的性能消耗一定能高于他的消耗。虽然经过逃逸分析可以做标量替换、栈上分配、和锁消除。但是逃逸分析自身也是需要进行一系列复杂的分析的，这其实也是一个相对耗时的过程**。 
- 一个极端的例子，就是经过逃逸分析之后，发现没有一个对象是不逃逸的。那这个逃逸分析的过程就白白浪费掉了。
- 虽然这项技术并不十分成熟，但是它也**是即时编译器优化技术中一个十分重要的手段**。
- 注意到有一些观点，认为通过逃逸分析，JVM会在栈上分配那些不会逃逸的对象，这在理论上是可行的，但是取决于JVM设计者的选择。据我所知，**Oracle Hotspot JVM中并未这么做，这一点在逃逸分析相关的文档里已经说明，所以可以明确所有的对象实例都是创建在堆上。**
- 目前很多书籍还是基于JDK7以前的版本，JDK已经发生了很大变化，intern字符串的缓存和静态变量曾经都被分配在永久代上，而永久代已经被元数据区取代。但是，intern字符串缓存和静态变量并不是被转移到元数据区，而是直接在堆上分配，所以这一点同样符合前面一点的**结论：对象实例都是分配在堆上。**

***

## 1.9 方法区

> 从线程共享与否的角度来看

![](picture/img97.png)

**程序计数器**：没有GC，没有OOM

**虚拟机战和本地方方法栈**：没有GC，有OOM

**堆**：有GC，有OOM

***

### 1.9.1 栈、堆、方法区的交互关系

![](picture/img98.png)



***

> 方法区在哪里？

《Java虚拟机规范》中明确说明：“尽管所有的方法区在逻辑上是属于堆的一部分，但一些简单的实现可能不会选择去进行垃圾收集或者进行压缩。”但对于HotSpotJVM而言，方法区还有一个别名叫做**Non-Heap（非堆）**，目的就是要和堆分开。

所以，**方法区看作是一块独立于Java堆的内存空间。**

![](picture/img99.png)

***

### 1.9.2 方法区的理解

- 方法区（Method Area）与Java堆一样，是各个线程共享的内存区域。

- 方法区在JVM启动的时候被创建，并且它的实际的物理内存空间中和Java堆区一样都可以是不连续的。

- 方法区的大小，跟堆空间一样，可以选择固定大小或者可扩展。

- 方法区的大小决定了系统可以保存多少个类，**如果系统定义了太多的类，导致方法区溢出，虚拟机同样会抛出内存溢出错误**：`java.lang.OutOfMemoryError: PermGen space` 或者`java.lang.OutOfMemoryError: Metaspace` 

- - 加载大量的第三方的jar包；Tomcat部署的工程过多（30~50个）；大量动态的生成反射类

- 关闭 JVM 就会释放这个区域的内存。

***

> HotSpot中方法区的演进

**在jdk7及以前，习惯上把方法区，称为永久代。jdk8开始，使用元空间取代了永久代。**

本质上，方法区和永久代并不等价，仅是对hotspot而言的。《Java虚拟机规范》对如何实现方法区，不做统一要求。例如：BEA JRockit / IBM J9 中不存在永久代的概念。

- 现在来看，当年使用永久代，不是好的主意。导致Java程序更容易OOM（超过`-XX:MaxPermsize`上限）

![](picture/img100.png)

而到了JDK8，终于完全废弃了永久代的概念，改用与JRockit、J9一样在本地内存中实现的元空间（Metaspace）来代替

![](picture/img101.png)

**元空间的本质和永久代类似，都是对JVM规范中方法区的实现**。不过元空间与永久代最大的区别在于：**元空间不在虚拟机设置的内存中，而是使用本地内存（正常我们的电脑内存都好几个GB，这样方法区就更不容易出现OOM）。**

永久代、元空间二者并不只是名字变了，内部结构也调整了。

根据《Java虚拟机规范》的规定，如果方法区无法满足新的内存分配需求时，将抛出OOM异常。

***

### 1.9.3 设置方法区大小与OOM

方法区的大小不必是固定的，JVM可以根据应用的需要动态调整

**jdk7及以前**

- 通过`-XX:Permsize`来设置永久代初始分配空间。默认值是20.75M

- 通过`-XX:MaxPermsize`来设定永久代最大可分配空间。32位机器默认是64M，64位机器模式是82M

- 当JVM加载的类信息容量超过了这个值，会报异常`OutOfMemoryError:PermGen space`。

***

**JDK8及以后**

- 元数据区大小可以使用参数 `-XX:MetaspaceSize` 和 `-XX:MaxMetaspaceSize`指定

- 默认值依赖于平台。windows下，`-XX:MetaspaceSize=21M -XX:MaxMetaspaceSize=-1//-1表示没有限制`。

- 与永久代不同，如果不指定大小，默认情况下，虚拟机会耗尽所有的可用系统内存。如果元数据区发生溢出，虚拟机一样会抛出异常`OutOfMemoryError:Metaspace`

- `-XX:MetaspaceSize`：设置初始的元空间大小。对于一个64位的服务器端JVM来说，其默认的`-XX:MetaspaceSize`值为21MB。这就是初始的高水位线，一旦触及这个水位线，Full GC将会被触发并卸载没用的类（即这些类对应的类加载器不再存活），然后这个高水位线将会重置。新的高水位线的值取决于GC后释放了多少元空间。如果释放的空间不足，那么在不超过`MaxMetaspaceSize`时，适当提高该值。如果释放空间过多，则适当降低该值。

- 如果初始化的高水位线设置过低，上述高水位线调整情况会发生很多次。通过垃圾回收器的日志可以观察到Full GC多次调用。为了避免频繁地GC，建议将`-XX:MetaspaceSize`设置为一个相对较高的值。

***

举例:**《深入理解Java虚拟机》的例子**

![](picture/img102.png)

***

> 如何解决这些OOM

1.  要解决OOM异常或heap space的异常，一般的手段是首先通过内存映像分析工具（如Eclipse Memory Analyzer）对dump出来的堆转储快照进行分析，重点是确认内存中的对象是否是必要的，也就是要先分清楚到底是出现了 **内存泄漏（Memory Leak）**还是 **内存溢出（Memory Overflow） **【泄漏：就是该回收的没有回收；溢出：确实都是不该回收的，但在内存确实不足了】
2.  如果是内存泄漏，可进一步通过工具查看泄漏对象到GC Roots的引用链。于是就能找到泄漏对象是通过怎样的路径与GCRoots相关联并导致垃圾收集器无法自动回收它们的。掌握了泄漏对象的类型信息，以及GCRoots引用链的信息，就可以比较准确地定位出泄漏代码的位置。 
3.  如果不存在内存泄漏，换句话说就是内存中的对象确实都还必须存活着，那就应当检查虚拟机的堆参数（`-Xmx`与`-Xms`），与机器物理内存对比看是否还可以调大，从代码上检查是否存在某些对象生命周期过长、持有状态时间过长的情况，尝试减少程序运行期的内存消耗。 

***

### 1.9.4 方法区的内部结构

![](picture/img103.png)

***

> 方法区（Method Area）存储什么？

《深入理解Java虚拟机》书中对方法区（Method Area）存储内容描述如下：**它用于存储已被虚拟机加载的类型信息、常量、静态变量、即时编译器编译后的代码缓存等。**

![](picture/img104.png)

***

> 方法区的内部结构

**类型信息**

对每个加载的类型（类class、接口interface、枚举enum、注解annotation），JVM必须在方法区中存储以下类型信息：

1. 这个类型的完整有效名称（全名=包名.类名）
2. 这个类型直接父类的完整有效名（对于interface或是java.lang.object，都没有父类）
3. 这个类型的修饰符（public，abstract，final的某个子集）
4. 这个类型直接接口的一个有序列表

***

**域（Field）信息**

- JVM必须在方法区中保存类型的所有域的相关信息以及域的声明顺序。
- 域的相关信息包括：域名称、域类型、域修饰符（public，private，protected，static，final，volatile，transient的某个子集）

***

**方法（Method）信息**

JVM必须保存所有方法的以下信息，同域信息一样包括声明顺序：

1. **方法名称**
2. **方法的返回类型**（或void）
3. **方法参数的数量和类型**（按顺序）
4. **方法的修饰符**（public，private，protected，static，final，synchronized，native，abstract的一个子集）
5. **方法的字节码**（bytecodes）、**操作数栈、局部变量表**及大小（abstract和native方法除外）
6. **异常表**（abstract和native方法除外） 

- - 每个异常处理的开始位置、结束位置、代码处理在程序计数器中的偏移地址、被捕获的异常类的常量池索引

- 说明：当class文件被类加载器加载到方法区时，方法区的每个类会记录是被哪个加载器加载的，而加载器也会记录加载了哪些类，互相记录。

***

>  non-final的类变量

- 静态变量和类关联在一起，随着类的加载而加载，他们成为类数据在逻辑上的一部分

- 类变量被类的所有实例共享，即使没有类实例时，你也可以访问它

```java
public class MethodAreaTest {
    public static void main(String[] args) {
        Order order = new Order();
        order.hello();
        System.out.println(order.count);  // 可以访问，但是一般都是‘类名.属性’来访问
    }
}
class Order {
    public static int count = 1;
    public static void hello() {
        System.out.println("hello!");
    }
}
```

> 补充说明：全局常量（static final）

被声明为final的类变量的处理方法则不同，每个全局常量在编译的时候就会被分配最终值了，而只有static修饰的类变量则在类加载的 “链接-准备阶段”会先赋值默认值，在初始化阶段才赋值最终值。

***

> 运行时常量池 VS 常量池

![](picture/img105.png)

- 方法区，内部包含了运行时常量池

- 字节码文件，内部包含了常量池

​        一个有效的字节码文件中除了包含类的版本信息、字段、方法以及接口等描述符信息外，还包含一项信息就是常量池表（Constant Pool Table），包括各种字面量和对类型、域和方法的符号引用

***

> 为什么需要常量池？

一个java源文件中的类、接口，编译后产生一个字节码文件。而Java中的字节码需要数据支持，通常这种数据会很大以至于不能直接存到字节码里，换另一种方式，可以存到常量池，这个字节码包含了指向常量池的引用。在动态链接的时候会用到运行时常量池，之前有介绍。

比如：如下的代码：

```java
public class SimpleClass {
    public void sayHello() {
        System.out.println("hello");
    }
}
```

虽然只有194字节，但是里面却使用了String、System、PrintStream及Object等结构。这里的代码量其实很少了，如果代码多的话，引用的结构将会更多，这里就需要用到常量池了。

![](picture/img106.png)

***

**小结**

​        常量池、可以看做是一张表，虚拟机指令根据这张常量表找到要执行的类名、方法名、参数类型、字面量等类型

***

> 运行时常量池

- 运行时常量池（Runtime Constant Pool）是方法区的一部分。

- 常量池表（Constant Pool Table）是Class文件的一部分，**用于存放编译期生成的各种字面量与符号引用，这部分内容将在类加载后存放到方法区的运行时常量池中。**

- 运行时常量池，在加载类和接口到虚拟机后，就会创建对应的运行时常量池。

- JVM为每个已加载的类型（类或接口）都维护一个常量池。池中的数据项像数组项一样，是通过索引访问的。

- 运行时常量池中包含多种不同的常量，包括编译期就已经明确的数值字面量，也包括到运行期解析后才能够获得的方法或者字段引用。此时不再是常量池中的符号地址了，这里换为 **真实地址。**

- 运行时常量池，相对于Class文件常量池的另一重要特征是：**具备动态性。**

- 运行时常量池类似于传统编程语言中的符号表（symboltable），但是它所包含的数据却比符号表要更加丰富一些。

- 当创建类或接口的运行时常量池时，如果构造运行时常量池所需的内存空间超过了方法区所能提供的最大值，则JVM会抛OutOfMemoryError异常。

***

### 1.9.5 方法区使用举例

```java
public class MethodAreaDemo {
    public static void main(String args[]) {
        int x = 500;
        int y = 100;
        int a = x / y;
        int b = 50;
        System.out.println(a+b);
    }
}
```

![](picture/img107.png)

![](picture/img108.png)

![](picture/img109.png)

![](picture/img110.png)

![](picture/img111.png)

![](picture/img112.png)

![](picture/img113.png)

![](picture/img114.png)

![](picture/img115.png)

![](picture/img116.png)

![](picture/img117.png)

![](picture/img118.png)

![](picture/img119.png)

![](picture/img120.png)

![](picture/img121.png)

![](picture/img122.png)

***

### 1.9.6 方法区的演进细节

1. 首先明确：只有Hotspot才有永久代。BEA JRockit、IBMJ9等来说，是不存在永久代的概念的。原则上如何实现方法区属于虚拟机实现细节，不受《Java虚拟机规范》管束，并不要求统一
2. Hotspot中方法区的变化：

| JDK1.6及之前 | 有永久代（permanet），静态变量存储在永久代上                 |
| ------------ | ------------------------------------------------------------ |
| **JDK1.7**   | **有永久代，但已经逐步 “去永久代”，字符串常量池，静态变量移除，保存在堆中** |
| **JDK1.8**   | **无永久代，类型信息，字段，方法，常量保存在本地内存的元空间，但字符串常量池、静态变量仍然在堆中。** |

![](picture/img123.png)

![](picture/img124.png)

![](picture/img125.png)

***

> 为什么永久代要被元空间替代？

JRockit是和HotSpot融合后的结果，因为JRockit没有永久代，所以他们不需要配置永久代

随着Java8的到来，HotSpot VM中再也见不到永久代了。但是这并不意味着类的元数据信息也消失了。这些数据被移到了一个**与堆不相连的本地内存区域，这个区域叫做元空间（Metaspace）。**

由于类的元数据分配在本地内存中，元空间的最大可分配空间就是系统可用内存空间。

这项改动是很有必要的，原因有：

1. **为永久代设置空间大小是很难确定的。**

在某些场景下，如果动态加载类过多，容易产生Perm区的oom。比如某个实际Web工 程中，因为功能点比较多，在运行过程中，要不断动态加载很多类，经常出现致命错误

而元空间和永久代之间最大的区别在于：**元空间并不在虚拟机中，而是使用本地内存**。 因此，默认情况下，元空间的大小仅受本地内存限制。 

说明：分配空间小了，GC很频繁，分配空间大了，就浪费了

2. **对永久代进行调优是很困难的。**

有些人认为方法区（如HotSpot虚拟机中的元空间或者永久代）是没有垃圾收集行为的，其实不然。《Java虚拟机规范》对方法区的约束是非常宽松的，提到过可以不要求虚拟机在方法区中实现垃圾收集。事实上也确实有未实现或未能完整实现方法区类型卸载的收集器存在（如JDK 11时期的ZGC收集器就不支持类卸载）。 一般来说这个区域的回收效果**比较难**令人满意，尤其是类型的卸载，条件相当苛刻。但是这部分区域的回收有时又确实是必要的。以前Sun公司的Bug列表中，曾出现过的若干个严重的Bug就是由于低版本的HotSpot虚拟机对此区域未完全回收而导致内存泄漏

**方法区的垃圾收集主要回收两部分内容：常量池中废弃的常量和不再使用的类型**

判定一个常量是否“废弃”还是相对简单，而要判定一个类型是否属于“不再被使用的类”的条件就比较苛刻了。需要同时满足下面三个条件：

1. 该类所有的实例都已经被回收，也就是Java堆中不存在该类及其任何派生子类的实例。 
2.  加载该类的类加载器已经被回收，这个条件除非是经过精心设计的可替换类加载器的场景，如OSGi、JSP的重加载等，否则通常是很难达成的。 
3. 该类对应的java.lang.Class对象没有在任何地方被引用，无法在任何地方通过反射访问该类的方法。 

Java虚拟机被允许对满足上述三个条件的无用类进行回收，这里说的仅仅是“被允许”，而并不是和对象一样，没有引用了就必然会回收。关于是否要对类型进行回收，HotSpot虚拟机提供了`-Xnoclassgc`参数进行控制，还可以使用`-verbose:class` 以及 `-XX:+TraceClassLoading`、`-XX:+TraceClassUnLoading`查看类加载和卸载信息

在大量使用反射、动态代理、CGLib等字节码框架，动态生成JSP以及OSGi这类频繁自定义类加载器的场景中，通常都需要Java虚拟机具备类型卸载的能力，以保证不会对方法区造成过大的内存压力。

***

> StringTable为什么要调整位置？

​        jdk7中将StringTable放到了堆空间中。因为永久代的回收效率很低，在full gc的时候才会触发。而full gc是老年代的空间不足、永久代不足时才会触发。这就导致StringTable回收效率不高。而我们开发中会有大量的字符串被创建，回收效率低，导致永久代内存不足。放到堆里，能及时回收内存。

***

> 静态变量存放在那里？

以后研究

***

### 1.9.7 方法区的垃圾回收

方法区的垃圾收集主要回收两部分内容：常量池中废弃的常量和不再使用的类型

先来说说方法区内常量池之中主要存放的两大类常量：字面量和符号引用。字面量比较接近Java语言层次的常量概念，如文本字符串、被声明为final的常量值等。而符号引用则属于编译原理方面的概念，包括下面三类常量：

- 类和接口的全限定名

- 字段的名称和描述符

- 方法的名称和描述符

***

**HotSpot虚拟机对常量池的回收策略是很明确的，只要常量池中的常量没有被任何地方引用，就可以被回收。**

回收废弃常量与回收Java堆中的对象非常类似。

判定一个常量是否“废弃”还是相对简单，而要判定一个类型是否属于“不再被使用的类”的条件就比较苛刻了。需要同时满足下面三个条件：（看上面有）

***

### 1.9.8 总结

![](picture/img126.png)

***

> 常见面试题

百度：

说一下JVM内存模型吧，有哪些区？分别干什么的？

***

蚂蚁金服：

Java8的内存分代改进 JVM内存分哪几个区，每个区的作用是什么？

一面：JVM内存分布/内存结构？栈和堆的区别？堆的结构？为什么两个survivor区？

二面：Eden和survior的比例分配

***

小米：

jvm内存分区，为什么要有新生代和老年代

***

字节跳动：

二面：Java的内存分区

二面：讲讲vm运行时数据库区 什么时候对象会进入老年代？

***

京东： 

JVM的内存结构，Eden和Survivor比例。

JVM内存为什么要分成新生代，老年代，持久代。

新生代中为什么要分为Eden和survivor。

***

天猫：

一面：Jvm内存模型以及分区，需要详细到每个区放什么。

一面：JVM的内存模型，Java8做了什么改

***

拼多多：

JVM内存分哪几个区，每个区的作用是什么？

***

美团：

java内存分配 jvm的永久代中会发生垃圾回收吗？

一面：jvm内存分区，为什么要有新生代和老年代？

***

## 1.10 对象的实例化内存布局与访问定位

### 1.10.1 对象的实例化

![](picture/img127.png)

> 创建对象的方式

- new：最常见的方式、Xxx的静态方法，XxxBuilder/XxxFactory的静态方法

- Class的newInstance方法（条件比较苛刻，不建议使用，建议使用下面的）：反射的方式，只能调用空参的构造器，权限必须是public

- Constructor的newInstance(XXX)（建议使用）：反射的方式，可以调用空参、带参的构造器，权限没有要求

- 使用clone()：不调用任何的构造器，要求当前的类需要实现Cloneable接口，实现clone()

- 使用反序列化：从文件中、从网络中获取一个对象的二进制流

- 第三方库 Objenesis

***

前面所述是从字节码角度看待对象的创建过程，现在从执行步骤的角度来分析：

**1. 判断对象对应的类是否加载、链接、初始化**

​        虚拟机遇到一条new指令，首先去检查这个指令的参数能否在Metaspace的常量池中定位到一个类的符号引用，并且检查这个符号引用代表的类是否已经被加载，解析和初始化（即判断类元信息是否存在）。

如果没有，那么在双亲委派模式下，使用当前类加载器以ClassLoader + 包名 + 类名为key进行查找对应的 .class文件；

- 如果没有找到文件，则抛出ClassNotFoundException异常

- 如果找到，则进行类加载，并生成对应的Class对象

***

**2.为对象分配内存**

​        首先计算对象占用空间的大小，接着在堆中划分一块内存给新对象（所以new的时候分配空间时，就已经知道要在堆中开辟多大空间了）。如果实例的**成员变量**是引用变量，仅分配引用变量空间即可，即4个字节大小（比如下面的Account acct;）

```java
public class Customer{
  int id = 1001;
  Account acct;
}
```

**如果内存规整**：虚拟机将采用的是指针碰撞法（Bump The Point）来为对象分配内存。

- 意思是所有用过的内存在一边，空闲的内存放另外一边，中间放着一个指针作为分界点的指示器，分配内存就仅仅是把指针指向空闲那边挪动一段与对象大小相等的距离罢了。如果垃圾收集器选择的是Serial ，ParNew这种基于压缩算法的，虚拟机采用这种分配方式。一般使用带Compact（整理）过程的收集器时，使用指针碰撞。

**如果内存不规整**：虚拟机需要维护一个空闲列表（Free List）来为对象分配内存。

- 已使用的内存和未使用的内存相互交错，那么虚拟机将采用的是空闲列表来为对象分配内存。意思是虚拟机维护了一个列表，记录上那些内存块是可用的，再分配的时候从列表中找到一块足够大的空间划分给对象实例，并更新列表上的内容。

选择哪种分配方式由Java堆是否规整所决定，而Java堆是否规整又由所采用的垃圾收集器是否带有压缩整理功能决定

***

**3. 处理并发问题**

- 采用CAS失败重试、区域加锁保证更新的原子性

- 每个线程预先分配一块TLAB：通过设置 `-XX:+UseTLAB`参数来设定

***

**4.初始化分配到的内存**

所有属性设置默认值，保证对象实例字段在不赋值时可以直接使用

***

**5. 设置对象的对象头**

将对象的所属类（即类的元数据信息）、对象的HashCode和对象的GC信息、锁信息等数据存储在对象的对象头中。这个过程的具体设置方式取决于JVM实现。

***

**6.执行init方法进行初始化**

在Java程序的视角看来，初始化才正式开始。初始化成员变量，执行实例化代码块，调用类的构造方法，并把堆内对象的首地址赋值给引用变量。

因此一般来说（由字节码中跟随invokespecial指令所决定），new指令之后会接着就是执行方法，把对象按照程序员的意愿进行初始化，这样一个真正可用的对象才算完成创建出来。

**给对象属性赋值的操作**

- 属性的默认初始化

- 显式初始化

- 代码块中初始化

- 构造器中初始化

**对象实例化的过程**

1. 加载类元信息
2. 为对象分配内存
3. 处理并发问题
4. 属性的默认初始化（零值初始化）
5. 设置对象头信息
6. 属性的显示初始化、代码块中初始化、构造器中初始化

***

### 1.10.2 对象的内存布局

![](picture/img128.png)

> 对象头（Header）

对象头包含了两部分，分别是运行时元数据（Mark Word）和类型指针。如果是数组，还需要记录数组的长度

**运行时元数据**

- 哈希值（HashCode）（栈引用记录的值）

- GC分代年龄（幸存者区0和1切换时，记录）

- 锁状态标志

- 线程持有的锁

- 偏向线程ID

- 偏向时间戳



**类型指针**

指向类元数据InstanceKlass，确定该对象所属的类型（简单理解记录我这个实例是哪个Class创建的）。

***

> 实例数据（Instance Data）

它是对象真正存储的有效信息，包括程序代码中定义的各种类型的字段（包括从父类继承下来的和本身拥有的字段）

- 相同宽度的字段总是被分配在一起

- 父类中定义的变量会出现在子类之前

- 如果CompactFields参数为true（默认为true）：子类的窄变量可能插入到父类变量的空隙

***

> 对齐填充（Padding）

不是必须的，也没有特别的含义，仅仅起到占位符的作用

***

> 例子和图示

```java
public class Customer{
    int id = 1001;
    String name;
    Account acct;
    {
        name = "匿名客户";
    }
    public Customer() {
        acct = new Account();
    }
}
public class CustomerTest{
    public static void main(string[] args){
        Customer cust=new Customer();
    }
}
```

![](picture/img129.png)

***

### 1.10.3 对象的访问定位

JVM是如何通过栈帧中的对象引用访问到其内部的对象实例呢？

> 方式一：句柄访问

![](picture/img130.png)

reference中存储稳定句柄地址，对象被移动（垃圾收集时移动对象很普遍）时只会改变句柄中实例数据指针即可，reference本身不需要被修改

***

> 方式二：直接指针（HotSpot采用）

![](picture/img131.png)

直接指针是局部变量表中的引用，直接指向堆中的实例，在对象实例中有类型指针，指向的是方法区中的对象类型数据

***

## 1.11 直接内存

为什么讲这个，是因为JDK8的方法区保存在直接内存

> 直接内存概述

​         不是虚拟机运行时数据区的一部分，也不是《Java虚拟机规范》中定义的内存区域。直接内存是在Java堆外的、直接向系统申请的内存区间。来源于NIO，通过存在堆中的DirectByteBuffer操作Native内存。通常，访问直接内存的速度会优于Java堆，即读写性能高。

因此出于性能考虑，读写频繁的场合可能会考虑使用直接内存。

Java的NIO库允许Java程序使用直接内存，用于数据缓冲区

后面细节略不讲。以后再研究。

***

## 1.12 执行引擎

### 1.12.1 执行引擎概述













































### 1.12.2 Java代码编译和执行过程

### 1.12.3 机器码、指令、汇编语言

### 1.12.4 解释器

### 1.12.5 JIT编译器









































## 1.13 StringTable

## 1.14 垃圾回收概述

## 1.15 垃圾回收相关算法

## 1.16 垃圾回收相关概念

## 1.17 垃圾回收器  













# 2. 字节码与类的加载篇

# 3. 性能监控与调优篇

## 4 大厂面试篇







































javap -verbose TestClass.class （这条指令反编译的结果不会有private的属性，要想看到需要再加 -p ，如：javap -v -p TestClass.class ）

> 看class文件十六进制字节码命令

 vim TestClass.class

:%!xxd





***

# 4 类文件结构

​         Java 技术能够一直保持着非常良好的向后兼容性，Class文件结构的稳定功不可没，任何一门程序语言能够获得商业上的成功，都不可能去做升级版本后，旧版本编译的产品就不再能够运行这种事情。现在讲的 Class 文件结构的内容，绝大部分都是在第一版中就已经定义好的，内容虽然古老，但时至今日，Java 发展经历了十余个大版本，对 Class 文件格式进行了几次更新，但基本上只是在原有结构基础上新增内容、扩充功能，并对已定义的内容作出修改。

```markdown
注意：
    任何一个 Class 文件都对应着唯一的一个类或接口的定义信息，但是反过来说，类或接口并不一定都定义在文件里（譬如类或接口也可以动态生成，直接送入类加载器中）。
```

## 4.1 Class 类文件的结构

​        Class文件是一组以字节为基础单位的二进制流，各个数据项目严格按照顺序紧凑地排列在文件之中，中间没有添加任何分隔符，这使得整个Class文件中存储的内容几乎全部是程序运行的必要数据，没有空隙存在。当遇到需要占用单个字节以上空间的数据项时，则会按照高位在前的方式分割成若干个字节进行存储。

​        Class文件格式采用一种类似于C语言结构体的伪结构来存储数据，这种伪结构中只有两种数据类型：**“无符号数”和“表”**。后面的解析都要以这两种数据类型为基础。

- <font color="red">无符号数</font>：属于基本数据类型，以u1、u2、u4、u8 来分别代表 1个字节、 2个字节、 4个字节、 8个字节的无符号数，无符号数可以用来描述数字、索引引用、数量值或者按照UTF-8编码构成字符串值。
- <font color="red">表</font>：是由多个无符号数或者其他表作为数据项构成的复合数据类型，为了便于区分，所有表的命名都习惯性的以`“_info”`结尾。表用于描述有层次关系的复合结构的数据，整个Class文件本质上也可以视作是一张表，这张表由下图所示的数据项按严格顺序排列构成。

> Class文件格式

| 类型           | 名称                | 数量                  |
| -------------- | ------------------- | --------------------- |
| u4             | magic               | 1                     |
| u2             | minor_version       | 1                     |
| u2             | major_version       | 1                     |
| u2             | constant_pool_count | 1                     |
| cp_info        | constant_pool       | constant_pool_count-1 |
| u2             | access_flags        | 1                     |
| u2             | this_class          | 1                     |
| u2             | super_class         | 1                     |
| u2             | interfaces_count    | 1                     |
| u2             | interfaces          | interfaces_count      |
| u2             | fields_count        | 1                     |
| field_info     | fields              | fields_count          |
| u2             | methods_count       | 1                     |
| method_info    | methods             | methods_count         |
| u2             | attributes_count    | 1                     |
| attribute_info | attributes          | attributes_count      |

​        无论是无符号数还是表，当需要描述同一类型但数量不定的多个数据时，经常会使用一个前置的容量计数器加若干个连续的数据项的形式，这时候称这一系列连续的某一类型的数据为某一类型的 `“集合”`。

​        需要强调一次，Class的结构不像 XML 等描述语言，由于它没有任何分隔符号，所以在上表中的数据项，无论是顺序还是数量，甚至于数据存储的字节序（Byte Ordering，Class 文件中字节序为 Big-Endian）这样的细节，都是被严格限定的，哪个字节代表什么含义，长度是多少，先后顺序如何，全部都不允许改变。接下来，一起看看这个表中各个数据项的具体含义。



```java
wing@WangShaoYoudeMacBook-Pro example % javap -verbose TestClass.class 
Classfile /Users/wing/IdeaProjects/hsp_study/chapter05/target/classes/org/example/TestClass.class
  Last modified 2024年2月23日; size 1900 bytes
  MD5 checksum b8e1697ca5fc215c5d104b0c4f12ca41
  Compiled from "TestClass.java"
public class org.example.TestClass
  minor version: 0
  major version: 52
  flags: (0x0021) ACC_PUBLIC, ACC_SUPER
  this_class: #18                         // org/example/TestClass
  super_class: #19                        // java/lang/Object
  interfaces: 0, fields: 4, methods: 10, attributes: 1
Constant pool:
   #1 = Methodref          #19.#62        // java/lang/Object."<init>":()V
   #2 = Fieldref           #63.#64        // java/lang/System.out:Ljava/io/PrintStream;
   #3 = String             #65            // Hello World
   #4 = Methodref          #66.#67        // java/io/PrintStream.println:(Ljava/lang/String;)V
   #5 = Class              #68            // java/lang/Exception
   #6 = Fieldref           #18.#69        // org/example/TestClass.name:Ljava/lang/String;
   #7 = Fieldref           #18.#70        // org/example/TestClass.age:I
   #8 = Class              #71            // java/lang/StringBuilder
   #9 = Methodref          #8.#62         // java/lang/StringBuilder."<init>":()V
  #10 = String             #72            // TestClass{name=\'
  #11 = Methodref          #8.#73         // java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
  #12 = Methodref          #8.#74         // java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
  #13 = String             #75            // , age=
  #14 = Methodref          #8.#76         // java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
  #15 = Methodref          #8.#77         // java/lang/StringBuilder.toString:()Ljava/lang/String;
  #16 = String             #78            // China
  #17 = Fieldref           #18.#79        // org/example/TestClass.country:Ljava/lang/String;
  #18 = Class              #80            // org/example/TestClass
  #19 = Class              #81            // java/lang/Object
  #20 = Utf8               nationNum
  #21 = Utf8               I
  #22 = Utf8               ConstantValue
  #23 = Integer            56
  #24 = Utf8               country
  #25 = Utf8               Ljava/lang/String;
  #26 = Utf8               name
  #27 = Utf8               age
  #28 = Utf8               <init>
  #29 = Utf8               ()V
  #30 = Utf8               Code
  #31 = Utf8               LineNumberTable
  #32 = Utf8               LocalVariableTable
  #33 = Utf8               this
  #34 = Utf8               Lorg/example/TestClass;
  #35 = Utf8               main
  #36 = Utf8               ([Ljava/lang/String;)V
  #37 = Utf8               args
  #38 = Utf8               [Ljava/lang/String;
  #39 = Utf8               getStaticNum
  #40 = Utf8               ()I
  #41 = Utf8               a
  #42 = Utf8               b
  #43 = Utf8               c
  #44 = Utf8               inc
  #45 = Utf8               x
  #46 = Utf8               e
  #47 = Utf8               Ljava/lang/Exception;
  #48 = Utf8               StackMapTable
  #49 = Class              #68            // java/lang/Exception
  #50 = Class              #82            // java/lang/Throwable
  #51 = Utf8               getName
  #52 = Utf8               ()Ljava/lang/String;
  #53 = Utf8               setName
  #54 = Utf8               (Ljava/lang/String;)V
  #55 = Utf8               getAge
  #56 = Utf8               setAge
  #57 = Utf8               (I)V
  #58 = Utf8               toString
  #59 = Utf8               <clinit>
  #60 = Utf8               SourceFile
  #61 = Utf8               TestClass.java
  #62 = NameAndType        #28:#29        // "<init>":()V
  #63 = Class              #83            // java/lang/System
  #64 = NameAndType        #84:#85        // out:Ljava/io/PrintStream;
  #65 = Utf8               Hello World
  #66 = Class              #86            // java/io/PrintStream
  #67 = NameAndType        #87:#54        // println:(Ljava/lang/String;)V
  #68 = Utf8               java/lang/Exception
  #69 = NameAndType        #26:#25        // name:Ljava/lang/String;
  #70 = NameAndType        #27:#21        // age:I
  #71 = Utf8               java/lang/StringBuilder
  #72 = Utf8               TestClass{name=\'
  #73 = NameAndType        #88:#89        // append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
  #74 = NameAndType        #88:#90        // append:(C)Ljava/lang/StringBuilder;
  #75 = Utf8               , age=
  #76 = NameAndType        #88:#91        // append:(I)Ljava/lang/StringBuilder;
  #77 = NameAndType        #58:#52        // toString:()Ljava/lang/String;
  #78 = Utf8               China
  #79 = NameAndType        #24:#25        // country:Ljava/lang/String;
  #80 = Utf8               org/example/TestClass
  #81 = Utf8               java/lang/Object
  #82 = Utf8               java/lang/Throwable
  #83 = Utf8               java/lang/System
  #84 = Utf8               out
  #85 = Utf8               Ljava/io/PrintStream;
  #86 = Utf8               java/io/PrintStream
  #87 = Utf8               println
  #88 = Utf8               append
  #89 = Utf8               (Ljava/lang/String;)Ljava/lang/StringBuilder;
  #90 = Utf8               (C)Ljava/lang/StringBuilder;
  #91 = Utf8               (I)Ljava/lang/StringBuilder;
{
  static final int nationNum;
    descriptor: I
    flags: (0x0018) ACC_STATIC, ACC_FINAL
    ConstantValue: int 56

  public static java.lang.String country;
    descriptor: Ljava/lang/String;
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC

  public java.lang.String name;
    descriptor: Ljava/lang/String;
    flags: (0x0001) ACC_PUBLIC

  public org.example.TestClass();
    descriptor: ()V
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 7: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   Lorg/example/TestClass;

  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=1, args_size=1
         0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
         3: ldc           #3                  // String Hello World
         5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
         8: return
      LineNumberTable:
        line 9: 0
        line 10: 8
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       9     0  args   [Ljava/lang/String;

  public static int getStaticNum();
    descriptor: ()I
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=3, args_size=0
         0: bipush        10
         2: istore_0
         3: bipush        20
         5: istore_1
         6: iload_0
         7: iload_1
         8: iadd
         9: istore_2
        10: iload_2
        11: ireturn
      LineNumberTable:
        line 18: 0
        line 19: 3
        line 20: 6
        line 21: 10
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            3       9     0     a   I
            6       6     1     b   I
           10       2     2     c   I

  public int inc();
    descriptor: ()I
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=1, locals=5, args_size=1
         0: iconst_1
         1: istore_1
         2: iload_1
         3: istore_2
         4: iconst_3
         5: istore_1
         6: iload_2
         7: ireturn
         8: astore_2
         9: iconst_2
        10: istore_1
        11: iload_1
        12: istore_3
        13: iconst_3
        14: istore_1
        15: iload_3
        16: ireturn
        17: astore        4
        19: iconst_3
        20: istore_1
        21: aload         4
        23: athrow
      Exception table:
         from    to  target type
             0     4     8   Class java/lang/Exception
             0     4    17   any
             8    13    17   any
            17    19    17   any
      LineNumberTable:
        line 27: 0
        line 28: 2
        line 33: 4
        line 28: 6
        line 29: 8
        line 30: 9
        line 31: 11
        line 33: 13
        line 31: 15
        line 33: 17
        line 34: 21
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            2       6     1     x   I
            9       8     2     e   Ljava/lang/Exception;
           11       6     1     x   I
            0      24     0  this   Lorg/example/TestClass;
           21       3     1     x   I
      StackMapTable: number_of_entries = 2
        frame_type = 72 /* same_locals_1_stack_item */
          stack = [ class java/lang/Exception ]
        frame_type = 72 /* same_locals_1_stack_item */
          stack = [ class java/lang/Throwable ]

  public java.lang.String getName();
    descriptor: ()Ljava/lang/String;
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: getfield      #6                  // Field name:Ljava/lang/String;
         4: areturn
      LineNumberTable:
        line 38: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   Lorg/example/TestClass;

  public void setName(java.lang.String);
    descriptor: (Ljava/lang/String;)V
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=2, locals=2, args_size=2
         0: aload_0
         1: aload_1
         2: putfield      #6                  // Field name:Ljava/lang/String;
         5: return
      LineNumberTable:
        line 42: 0
        line 43: 5
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       6     0  this   Lorg/example/TestClass;
            0       6     1  name   Ljava/lang/String;

  public int getAge();
    descriptor: ()I
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: getfield      #7                  // Field age:I
         4: ireturn
      LineNumberTable:
        line 46: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   Lorg/example/TestClass;

  public void setAge(int);
    descriptor: (I)V
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=2, locals=2, args_size=2
         0: aload_0
         1: iload_1
         2: putfield      #7                  // Field age:I
         5: return
      LineNumberTable:
        line 50: 0
        line 51: 5
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       6     0  this   Lorg/example/TestClass;
            0       6     1   age   I

  public java.lang.String toString();
    descriptor: ()Ljava/lang/String;
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=2, locals=1, args_size=1
         0: new           #8                  // class java/lang/StringBuilder
         3: dup
         4: invokespecial #9                  // Method java/lang/StringBuilder."<init>":()V
         7: ldc           #10                 // String TestClass{name=\'
         9: invokevirtual #11                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        12: aload_0
        13: getfield      #6                  // Field name:Ljava/lang/String;
        16: invokevirtual #11                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        19: bipush        39
        21: invokevirtual #12                 // Method java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        24: ldc           #13                 // String , age=
        26: invokevirtual #11                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        29: aload_0
        30: getfield      #7                  // Field age:I
        33: invokevirtual #14                 // Method java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        36: bipush        125
        38: invokevirtual #12                 // Method java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        41: invokevirtual #15                 // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
        44: areturn
      LineNumberTable:
        line 55: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      45     0  this   Lorg/example/TestClass;

  static {};
    descriptor: ()V
    flags: (0x0008) ACC_STATIC
    Code:
      stack=1, locals=0, args_size=0
         0: ldc           #16                 // String China
         2: putstatic     #17                 // Field country:Ljava/lang/String;
         5: return
      LineNumberTable:
        line 13: 0
}
SourceFile: "TestClass.java"
wing@WangShaoYoudeMacBook-Pro example % 



```



***

> 代码清单 6-1 简单的 Java 代码

```java
package org.fenixsoft.clazz;
public class TestClass {
  private int m;
  public int inc(){
    return m + 1;
  }
}
```

下面图 6-3 显示的是使用十六进制编辑器打开这个代码编译后 Class 文件的结果 。

> 图 6-3

![](picture/img58.png)

***

### 4.1.1 魔数与Class文件的版本

​        每个Class文件的头 4 个字节被称为魔数，它的作用是确定这个文件是否为一个能被虚拟机接受的 Class 文件。不仅是 Class 文件，很多文件格式标准中都有使用魔数来进行身份识别的习惯，譬如图片格式，如GIF或者JPEG等在文件头中都存有魔数。使用魔数而不是扩展名来进行识别主要是基于安全考虑，因为文件扩展名可以随意改动。

​       Class 文件的魔数值为 **0xCAFEBABE**，这个魔数值在Java还被称为 “Oak”语言的时候（大约是1991年前后）就已经确定下来了。

​        紧接着魔数的 4 个字节存储的是 Class 文件的版本号：**第 5 和 第 6 个字节**是次版本号（Minor Version），**第 7 和 第 8 个字节**是主版本号 (Major Version)。

***

### 4.1.2 常量池

​        紧接着主、次版本号之后的是常量池入口，**常量池可以比喻为 Class 文件里的资源仓库**，它是 Class 文件结构中与其他项目关联最多的数据，通常也是占用 Class 文件空间最大的数据项目之一，另外，它还是在 Class 文件中第一个出现的表类型数据项目。

​        由于常量池中常量的数量是不固定的，所以在常量池的入口需要放置一项 u2 类型的数据，代表常量池容量计数值（constant_pool_count）。与Java中语言习惯不同，**这个容量计数是从 1 而不是 0 开始的。**

​        常量池中主要存放两大类常量：**字面量**（Literal）和 **符号引用**（Symbolic References）。字面量比较接近于 Java 语言层面的常量概念，如文本字符串、被声明为 final 的常量值等。而符号引用则属于编译原理方面的概念，主要包括下面几类常量：

- 被模块导出或者开放的包（Package）
- 类和接口全限定名（Fully Qualified Name）
- 字段的名称和描述符（Descriptor）
- 方法的名称和描述符
- 方法句柄和方法类型（Method Handle、Method Type、Invoke Dynamic）
- 动态调用点和动态常量（Dynamically-Computed Call Site、Dynamically-Computed Constant）

***

​          Java 代码在进行 Javac 编译的时候，并不像 C 和 C++ 那样有 “连接” 这一步骤，**而是在虚拟机加载 Class 文件的时候进行动态连接**。也就是说，在 Class 文件中不会保存各个方法、字段最终在内存中的布局信息，这些字段、方法的符号引用不经过虚拟机在运行期转换的话是无法得到真正的内存入口地址，也就无法直接被虚拟机使用的。当虚拟机做类加载时，将会从常量池获得对应的符号引用，再在类创建时或运行时解析、翻译到具体的内存地址之中。

​         常量池中每一项常量都是一个表，最初常量表中共有 11 种结构各不相同的表结构数据，后来为了更好地支持动态语言调用，额外增加了 4 种 动态语言相关的常量，为了支持 Java 模块化系统，又加入了 `CONSTANT_Module_info` 和 `CONSTANT_Package_info` 两个常量，所以截止 JDK 13，常量表中分别有 17 种不同类型的常量。

​         这 17 类表都有一个共同的特点，表结构起始的第一位是个 u1 类型的标志位（tag，取值见下表），代表着当前常量属于哪种常量类型。17 种 常量类型所代表的具体含义如下表所示。

> 表 6-3 常量池的项目类型

| 类型                             | 标志 | 描述                           |
| -------------------------------- | ---- | ------------------------------ |
| CONSTANT_Utf8_info               | 1    | UTF-8编码的字符串              |
| CONSTANT_Integer_info            | 3    | 整型字面量                     |
| CONSTANT_Float_info              | 4    | 浮点型字面量                   |
| CONSTANT_Long_info               | 5    | 长整型字面量                   |
| CONSTANT_Double_info             | 6    | 双精度浮点型字面量             |
| CONSTANT_Class_info              | 7    | 类或接口的符号引用             |
| CONSTANT_String_info             | 8    | 字符串类型字面量               |
| CONSTANT_Fieldref_info           | 9    | 字段的符号引用                 |
| CONSTANT_Methodref_info          | 10   | 类中方法的符号引用             |
| CONSTANT_InterfaceMethodref_info | 11   | 接口中方法的符号引用           |
| CONSTANT_NameAndType_info        | 12   | 字段或方法的部分符号引用       |
| CONSTANT_MethodHandle_info       | 15   | 表示方法句柄                   |
| CONSTANT_MethodType_info         | 16   | 表示方法类型                   |
| CONSTANT_Dynamic_info            | 17   | 表示一个动态计算常量           |
| CONSTANT_InvokeDynamic_info      | 18   | 表示一个动态方法调用点         |
| CONSTANT_Moduleinfo              | 19   | 表示一个模块                   |
| CONSTANT_Package_info            | 20   | 表示一个模块中开放或者导出的包 |

​        之所以说常量池是最烦琐的数据，是因为这 17 种常量类型各自有着完全独立的数据结构，两两之间并没有什么共性和联系，因此只能逐项进行讲解。

​        看图 6-3 中常量池的第一项常量，它的标志位（偏移地址：0x0000000A）是 0x07，查表 6-3 的标志列可知这个常量属于 `CONSTANT_Class_info` 类型，此类型的常量代表一个类或者接口的符号引用。`CONSTANT_Class_info`的结构比较简单，如表 6-4 所示。

> 表 6-4 CONSTANT_Class_info 型常量的结构

| 类型 | 名称       | 数量 |
| ---- | ---------- | ---- |
| u1   | tag        | 1    |
| u2   | name_index | 1    |

​        `tag` 是标志位，它用于区分常量类型；`name_index` 是常量池的索引值，它指向常量池中一个`CONSTANT_Utf8_info`类型常量，此常量代表这个类（或者接口）的全限定名，本例中的`name_index`值（偏移地址：0x0000000B）为 0x0002，也就是指向常量池中的第二项常量。继续从图 6-3 中查找第二项常量，它的标志位（偏移地址：0x0000000D）是0x01，查表 6-3 可知确实是一个 `CONSTANT_Utf8_info`类型的常量。`CONSTANT_Utf8_info`类型的结构如表 6-5 所示。

***

> 表 6-5 CONSTANT_Utf8_info 型常量的结构

| 类型 | 名称   | 数量   |
| ---- | ------ | ------ |
| u1   | tag    | 1      |
| u2   | length | 1      |
| u1   | bytes  | length |

​         length 值说明了这个 UTF-8 编码的字符串长度是多少字节，它后面紧跟着的长度为 length 字节的连续数据是一个使用 UTF-8 缩略编码表示的字符串。UTF-8 缩略编码与普通 UTF-8 编码的区别是：从 ‘\u0001’到‘\u007f’之间的字符（相当于1 ～ 127 的 ASCII 码）的缩略编码使用一个字节表示，从 ‘\u0080’到‘\u07ff’之间的所有字符的缩略编码使用两个字节表示，从 ‘\u0800’到‘\uffff’之间的所有字符的缩略编码就按照普通 UTF-8 编码规则使用三个字节表示。

​         顺便提一下，由于 Class 文件中方法、字段等都需要引用`CONSTANT_Utf8_info`型常量来描述名称，所以`CONSTANT_Utf8_info`型常量的最大长度也就是 Java 中方法、字段名的最大长度。而这里的最大长度就是 length 的最大值，既 u2 类型能表达的最大值 65535。所以 Java 程序中如果定义了超过 64 KB 英文字符的变量或方法名，即使规则和全部字符都是合法的，也会无法编译。

​        本例中这个字符串的 length 值（偏移地址：0x0000000E）是 0x001D，也就是长29个字节，往后29个字节正好都在 1～127的 ASCII 码范围以内，内容为` “org/fenixsoft/clazz/TestClass”`，有兴趣可以自己逐个字节换算一下，换算结果如下图选中的部分所示。

![](picture/img59.png)

​      到此为止，我们仅仅分析了 TestClass.class 常量池中 21 个常量中的两个，还未提到的其余 19 个常量都可以通过类似的方法逐一计算出来，为了避免计算过程占用笔记，后续的 19 个常量的计算过程就不手工去做了，而借助计算机软件来帮忙完成。**在JDK的bin目录中，Oracle公司已经为我们准备好一个专门用于分析 Class 文件字节码的工具：javap。**代码清单 6-2 中列出了使用 javap 工具的 -verbose 参数输出的 TestClass.class文件字节码内容（为节省篇幅，此清单中省略了常量池以外的信息）。曾经提到过 Class 文件中还有很多数据项都要引用常量池中的常量，这个清单在后面讲解会频繁使用到。

> 代码清单 6-2 

![](picture/img60.png)

![](picture/img61.png)

​        从代码清单 6-2 中可以看到，计算机已经帮我们把整个常量池的 21 项常量都计算了出来，并且第1、2项常量的计算结果与我们手工计算的结果完全一致。仔细看一下会发现，其中有些常量似乎从来没有在代码中出现过，如 `“I”`，`“V”`，`“<init>”`，`“LineNumberTable”`，`“LocalVariableTable”` 等，这些看起来在源代码中不存在的常量是哪里来的？

​       这部分常量的确不来源于 Java 源代码，它们都是编译器自动生成的，会被后面即将讲到的 **字段表（field_info）、方法表（method_info）、属性表（attribute_info）**所引用，它们将会被用来描述一些不方便使用“固定字节”进行表达的内容，譬如描述方法的返回值是什么，有几个参数，每个参数的类型是什么。因为 Java 中的 “类” 是无穷无尽的，无法通过简单的无符号数来描述一个方法用到了什么类，因此在描述方法的这些信息时，需要引用常量表中的符号引用进行表达。

> 表 6-6 常量池中的 17 种数据类型的结构总表

| 常量                               | 项目                       | 类型 | 描述                                                         |
| ---------------------------------- | -------------------------- | ---- | ------------------------------------------------------------ |
| `CONSTANT_Utf8_info`               | tag                        | u1   | 值为1                                                        |
|                                    | length                     | u2   | UTF-8 编码的字符串占用了字节数                               |
|                                    | bytes                      | u1   | 长度为 length 的 UTF-8 编码的字符串                          |
| `CONSTANT_Integer_info`            | tag                        | u1   | 值为3                                                        |
|                                    | bytes                      | u4   | 按照高位在前存储的 int 值                                    |
| `CONSTANT_Float_info`              | tag                        | u1   | 值为4                                                        |
|                                    | bytes                      | u4   | 按照高位在前存储的 float 值                                  |
| `CONSTANT_Long_info`               | tag                        | u1   | 值为5                                                        |
|                                    | bytes                      | u8   | 按照高位在前存储的 long 值                                   |
| `CONSTANT_Double_info`             | tag                        | u1   | 值为5                                                        |
|                                    | bytes                      | u8   | 按照高位在前存储的 double 值                                 |
| `CONSTANT_Class_info`              | tag                        | u1   | 值为7                                                        |
|                                    | index                      | u2   | 指向全限定名常量项的索引                                     |
| `CONSTANT_String_info`             | tag                        | u1   | 值为8                                                        |
|                                    | index                      | u2   | 指向字符串字面量的索引                                       |
| `CONSTANT_Fieldref_info`           | tag                        | u1   | 值为9                                                        |
|                                    | index                      | u2   | 指向声明字段的类或者接口描述符 `CONSTANT_Class_info` 的索引项 |
|                                    | index                      | u2   | 指向字段描述符`CONSTANT_NameAndType_info`的索引项            |
| `CONSTANT_Methodref_info`          | tag                        | u1   | 值为10                                                       |
|                                    | index                      | u2   | 指向声明方法的类描述符 `CONSTANT_Class_info` 的索引项        |
|                                    | index                      | u2   | 指向名称及类型描述符`CONSTANT_NameAndType_info`的索引项      |
| `CONSTANT_InterfaceMethodref_info` | tag                        | u1   | 值为11                                                       |
|                                    | index                      | u2   | 指向声明方法的接口描述符 `CONSTANT_Class_info` 的索引项      |
|                                    | index                      | u2   | 指向名称及类型描述符`CONSTANT_NameAndType_info`的索引项      |
| `CONSTANT_NameAndType_info`        | tag                        | u1   | 值为12                                                       |
|                                    | index                      | u2   | 指向该字段或方法名称常量项的索引                             |
|                                    | index                      | u2   | 指向该字段或方法描述常量项的索引                             |
| `CONSTANT_MethodHandle_info`       | tag                        | u1   | 值为15                                                       |
|                                    | reference_kind             | u1   | 值必须在 1 至 9 之间（包括1和9），它决定了方法句柄的类型。方法句柄类型的值表示方法句柄的字节码行为 |
|                                    | reference_index            | u2   | 值必须是对常量值的有效索引                                   |
| `CONSTANT_MethodType_info`         | tag                        | u1   | 值为16                                                       |
|                                    | descriptor_index           | u2   | 值必须是对常量值的有效索引，常量池在该索引处的项必须是 `CONSTANT_Utf8_info`结构，表示方法的描述符 |
| `CONSTANT_Dynamic_info`            | tag                        | u1   | 值为17                                                       |
|                                    | bootstrap_method_arr_index | u2   | 值必须是对当前 Class 文件中引导方法表的 bootstrap_methods[] 数组的有效索引 |
|                                    | name_and_type_index        | u2   | 值必须是对当前常量池的有效索引，常量池在该索引处的项必须是`CONSTANT_NameAndType_info`结构，表示方法名和方法描述符 |
| `CONSTANT_InvokeDynamic_info`      | tag                        | u1   | 值为18                                                       |
|                                    | bootstrap_method_arr_index | u2   | 值必须是对当前 Class 文件中引导方法表的 bootstrap_methods[] 数组的有效索引 |
|                                    | name_and_type_index        | u2   | 值必须是对当前常量池的有效索引，常量池在该索引处的项必须是`CONSTANT_NameAndType_info`结构，表示方法名和方法描述符 |
| `CONSTANT_Module_info`             | tag                        | u1   | 值为19                                                       |
|                                    | name_index                 | u2   | 值必须是对常量池的有效索引，常量池在该索引处的项必须是`CONSTANT_Utf8_info`结构，表示模块名字 |
| `CONSTANT_Package_info`            | tag                        | u1   | 值为20                                                       |
|                                    | name_index                 | u2   | 值必须是对常量池的有效索引，常量池在该索引处的项必须是`CONSTANT_Utf8_info`结构，表示包名称 |

***

### 4.1.3 访问标志

​        在常量池结束之后，紧接着的 2 个字节代表访问标志（access_flags），**这个标志用于识别一些类或者接口层次的访问信息**，包括：这个 Class 是类还是接口；是否定义为 public 类型；是否定义为 abstract 类型；如果是类的话，是否被声明为 final ；等等。具体的标志位以及标志的含义见表 6-7

> 表 6-7 访问标志

| 标志名称       | 标志值 | 含义                                                         |
| -------------- | ------ | ------------------------------------------------------------ |
| ACC_PUBLIC     | 0x0001 | 是否为 public 类型                                           |
| ACC_FINAL      | 0x0010 | 是否被声明为 final ，只有类可设置                            |
| ACC_SUPER      | 0x0020 | 是否允许使用 invokespecial 字节码指令的新语义， invokespecial 指令的语义在 JDK 1.0.2 发生过改变，为了区别这条指令使用哪种语义，JDK 1.0.2 之后编译出来的类的这个标志都必须为真 |
| ACC_INTERFACE  | 0x0200 | 标识这个一个接口                                             |
| ACC_ABSTRACT   | 0x0400 | 是否为 abstract 类型，对于接口或者抽象类来说，次标志值为真，其他类型值为假 |
| ACC_SYNTHETIC  | 0x1000 | 标志这个类并非由用户代码产生的                               |
| ACC_ANNOTATION | 0x2000 | 标识这是一个注解                                             |
| ACC_ENUM       | 0x4000 | 标识这是一个枚举                                             |
| ACC_MODULE     | 0x8000 | 标识这是一个模块                                             |

​         **access_flags** 中一共有 16 个标志位可以使用，当前只定义了其中 9 个，没有使用到的标志位要求一律为零。以代码清单 6-1 中的代码为例，TestClass 是一个普通 Java 类，不是接口、枚举、注解或者模块，被 public 关键字修饰但没有被声明为 final 和 abstract，并且它使用了 JDK 1.2 之后的编译器进行编译，因此它的 `ACC_PUBLIC`、`ACC_SUPER`标志应当为真，而`ACC_FINAL`、`ACC_INTERFACE`、`ACC_ABSTRACT`、`ACC_SYNTHETIC`、`ACC_ANNOTATION`、`ACC_ENUM`、`ACC_MODULE`七个标志应当为假，因此它的 access_flags 值应为： 0x0001 ｜ 0x0020 = 0x0021。从图 6-5 中看到，access_flags 标志（偏移地址：0x000000EF ）的确为 0x0021 。

![](picture/img62.png)

***

### 4.1.4 类索引、父类索引与接口索引集合

​        **类索引（this_class）**和 **父类索引（super_class）**都是一个 u2 类型的数据，而接口索引集合（interfaces）是一组 u2 类型的数据的集合，**Class 文件中由这三项数据来确定该类型的继承关系**。类索引用于确定这个类的全限定名，父类索引用于确定这个类的父类的全限定名。由于 Java 语言不允许多重继承，所以父类索引只有一个，除了 `java.lang.Object`之外， 所有 Java 类的父类索引都不为 0 。接口索引集合就用来描述这个类实现了哪些接口，这些被实现的接口将按 implements 关键字（如果这个 Class 文件表示的是一个接口，则应当是 extends 关键字）后的接口顺序从左到右排列在接口索引集合中。

​        类索引、父类索引和接口索引集合都按顺序排列在访问标志之后，类索引和父类索引用两个 u2 类型的索引值表示，它们各自指向一个类型为 `CONSTANT_Class_info`的类描述符常量，通过 `CONSTANT_Class_info`类型的常量中的索引值可以找到定义在 `CONSTANT_Utf8_info`类型的常量中的全限定名字符串。图 6-6 演示了代码清单 6-1 中代码的类索引查找过程。

> 图 6-6 类索引查找全限定名的过程

![](picture/img63.png)

​        对于接口索引集合，入口的第一项 u2 类型的数据为接口计数器（interfaces_count），表示索引表的容量。如果该类没有实现任何接口，则该计数器值为 0 ，后面接口的索引表不再占用任何字节。代码清单 6-1 中的代码的类索引、父类索引与接口表索引的内容如图 6-7 所示。

> 图 6-7 类索引、父类索引与接口索引集合

![](picture/img64.png)

​         从偏移地址 0x000000F1 开始的 3 个 u2 类型的值分别为 0x0001、0x0003、0x0000，也就是类索引为 1，父类索引为 3 ，接口索引集合大小为 0（该类没有实现任何接口）。查询前面代码消单 6-2 中 javap 命令计算出来的常量池，找出对应的类和父类的常量，结果如代码清单 6-3 所示。

![](picture/img65.png)

***

### 4.1.5 字段表集合

​	    **字段表（field_info）**用于描述接口或者类中声明的变量。Java 语言中的“字段”(Field) 包括  **类级变量** 以及 **实例级变量** ，但不包括在方法内部声明的局部变量。读者可以回忆一下在 Java 语言中描述一个字段可以包含哪些信息。字段可以包括的修饰符有字段的作用域(public、private、protected 修饰符）、是实例变量还是类变量（static 修饰符）、可变性 (final)、并发可见性 (volatile 修饰符，是否强制以主内存读写)、可否被序列化（transient 修饰符)、字段数据类型（基本类型、对象、数组）、字段名称。上述这些信息中，各个修饰符都是布尔值，要么有某个修饰符，要么没有，很适合使用标志位来表示。**而字段叫做什么名字、字段被定义为什么数据类型，这些都是无法固定的，只能引用常量池中的常量来描述**。表 6-8 中列出了字段表的最终格式。

> 表 6-8 字段表结构

| 类型           | 名称             | 数量             |
| -------------- | ---------------- | ---------------- |
| u2             | access_flags     | 1                |
| u2             | name_index       | 1                |
| u2             | descriptor_index | 1                |
| u2             | attributes_count | 1                |
| attribute_info | attributes       | attributes_count |

​        字段修饰符放在 access_flags 项目中，它与类中的 access_flags 项目是非常类似的，都是一个 u2 的数据类型，其中可以设置的标志位和含义如表 6-9 所示。

> 表 6-9 字段访问标志

| 标志名称      | 标志值 | 含义                      |
| ------------- | ------ | ------------------------- |
| ACC_PUBLIC    | 0x0001 | 字段是否 public           |
| ACC_PRIVATE   | 0x0002 | 字段是否 private          |
| ACC_PROTECTED | 0x0004 | 字段是否 protected        |
| ACC_STATIC    | 0x0008 | 字段是否 static           |
| ACC_FINAL     | 0x0010 | 字段是否 final            |
| ACC_VOLATILE  | 0x0040 | 字段是否 volatile         |
| ACC_TRANSIENT | 0x0080 | 字段是否 transient        |
| ACC_SYNTHETIC | 0x1000 | 字段是否 由编译器自动产生 |
| ACC_ENUM      | 0x4000 | 字段是否 enum             |

​        很明显，由于语法规则的约束，`ACC_PUBLIC`、`AC_PRIVATE`、 `ACC_PROTECTED`三个标志最多只能选择其一，`ACC_FINAL`、`ACC_VOLATILE` 不能同时选择。接口之中的字段必须有 `ACC_PUBLIC`、`ACC_STATIC`、 `ACC _FINAL` 标志，这些都是曲 Java本身的语言规则所导致的。

​        跟随 access_flags 标志的是两项索引值：`name_index` 和 `descriptor_index`。它们都是对常量池项的引用，分别代表着字段的`简单名称`以及`字段和方法的描述符`。现在需要解释一下`“简单名称” “描述符”`以及前面出现过多次的`“全限定名”`这三种特殊字符串的概念。

​        **全限定名** 和 **简单名称** 很好理解，以代码清单6-1中的代码为例，`“org/fenixsoft/clazz/TestClass" `是这个类的全限定名，仅仅是把类全名中的 `“.”` 替换成了 `“/”` 而已，为了使连续的多个全限定名之间不产生混淆，在使用时最后一般会加人一个 `“;”` 号表示全限定名结束。**简单名称** 则就是指没有类型和参数修饰的方法或者字段名称，这个类中的 inc() 方法和 m 字段的简单名称分别就是 `“inc”` 和 `“m”`

​        相比于全限定名和简单名称，**方法和字段的描述符** 就要复杂一些。**描述符 的作用是： 1）用来描述字段的数据类型；2）用来描述方法的参数列表（包括数量、类型以及顺序) 和返回值**。根据描述符规则，基本数据类型 (byte、char、double、float、int、 long、 short、 boolean）以及代表无返回值的 void 类型都用一个大写字符来表示，而**对象类型则用字符` L `加对象的全限定名来表示**，详见表 6-10。

> 表 6-10 描述符标识字符含义

| 标识字符 | 含义                            |
| -------- | ------------------------------- |
| B        | 基本类型 byte                   |
| C        | 基本类型 char                   |
| D        | 基本类型 double                 |
| F        | 基本类型 float                  |
| I        | 基本类型 int                    |
| J        | 基本类型 long                   |
| S        | 基本类型 short                  |
| Z        | 基本类型 boolean                |
| V        | 特殊类型 void                   |
| L        | 对象类型，如 Ljava/lang/Object; |

​        对于数组类型，每一维度将使用一个前置的 `“[”` 字符来描述，如一个定义为 `java.lang.String[][]`[][]  类型的二维数组将被记录成` “[[java/lang/String;"`，一个整型数组 `“int[]”`将被记录成 `“[I”`。

​        用描述符来描述方法时，按照先参数列表、后返回值的顺序描述，参数列表按照参数的严格顺序放在一组小括号 `“()”` 之内。如方法 `void inc()`  的描述符为 `“()V” `，方法 `java.lang.String toString()` 的描述符为 `“()Ljava/lang/String;”`，方法 `int indexOf(char[] source, int sourceOffset,int sourceCount, char[] target, int targetOffset, int targetCount, int fromIndex) `的描述符为 `([CII[CIII]])I`

​         对于代码清单 6-1所编泽的 TestClass.class 文件来说，字段表集合从地址 0x000000F8 开始，第一个u2类型的数据为容量计数器 `fields_count`，如图 6-8 所示，其值为 0x0001，说明这个类只有一个字段表数据。接下来紧跟着容量计数器的是 `access_flags` 标志，值为0x002，代表 `private` 修饰符的 `ACC_PRIVATE` 标志位为真( `ACC_PRIVATE` 标志的值为 0x0002），其他修饰符为假。代表字段名称的 `name_index `的值为 0x0005，从代码清单6-2列出的常量表中可查得第五项常量是一个`CONSTANT_Utf8_info` 类型的字符串，其值为` “m”`，代表字段描述符的 `desoriptor_index` 的值为 0x0006，指向常量池的字符串 `“I”`。根据这些信息，我们可以推断出原代码定义的字段为` “private int m;”`

> 图 6-8 字段表结构实例

![](picture/img66.png)

​        字段表所包含的固定数据项目到 `descriptor_index` 为止就全部结束了，不过在 `descriptor_index`之后跟随着一个**属性表集合**，用于存储一些额外的信息，字段表可以在属性表中附加描述零至多项的额外信息。对于本例中的字段 m，它的属性表计数器为 0，也就是没有要额外描述的信息，但是，如果将字段 m 的声明改为`“final static int m = 123;”`，那就可能会存在一项名称为 `Constantvalue` 的属性，其值指向常量123。关于`attribute_info` 的其他内容，将在后面介绍属性表的数据项目时再做进一步讲解。

​        字段表集合中不会列出从父类或者父接口中继承而来的字段，但有可能出现原本 java 代码之中不存在的字段，譬如在内部类中为了保持对外部类的访问性，编译器就会自动添加指向外部类实例的字段。另外，在Java 语言中字段是无法重载的，两个字段的数据类型、修饰符不管是否相同，都必须使用不一样的名称，但是对于 CIass 文件格式来说，只要两个字段的描述符不是完全相同，那字段重名就是合法的。

***

### 4.1.6 方法表集合

​        如果理解了上一节关于字段表的内容，那本节关于方法表的内容将会变得很简单。Class 文件存储格式中对方法的描述与对字段的描述采用了几乎完全一致的方式，方法表的结构如同字段表一样，依次包括访问标志（access_flags）、名称索引（name_index）、描述符索引（descriptor_index）、属性表集合（attributes）几项，如表 6-11 所示。这些数据项目的含义也与字段表中的非常类似，仅在访问标志和属性表集合的可选项中有所区别。

> 表 6-11 方法表结构

| 类型           | 名称             | 数量             |
| -------------- | ---------------- | ---------------- |
| u2             | access_flags     | 1                |
| u2             | name_index       | 1                |
| u2             | descriptor_index | 1                |
| u2             | attributes_count | 1                |
| attribute_info | attributes       | attributes_count |

​       因为 volatile 关键字和 transient 关键字不能修饰方法，所以方法表的访问标志中没有了 `ACC_VOLATILE`标志和 `ACC_TRANSIENT`标志。与之相对，synchronized、native、strictfp 和 abstract 关键字可以修饰方法，方法表的访问标志中也相应地增加了 `ACC_SYNCHRONIZED`、`ACC_NATIVE`、`ACC_STRICTFP`和`ACC_ABSTRACT`标志。对于方法表，所有标志位及其取值可参见表 6-12。

> 表 6-12 方法访问标志

| 标志名称         | 标志值 | 含义                             |
| ---------------- | ------ | -------------------------------- |
| ACC_PUBLIC       | 0x0001 | 方法是否为 public                |
| ACC_PRIVATE      | 0x0002 | 方法是否为 private               |
| ACC_PROTECTED    | 0x0004 | 方法是否为 protected             |
| ACC_STATIC       | 0x0008 | 方法是否为 static                |
| ACC_FINAL        | 0x0010 | 方法是否为 final                 |
| ACC_SYNCHRONIZED | 0x0020 | 方法是否为 synchronized          |
| ACC_BRIDGE       | 0x0040 | 方法是不是由编译器产生的桥接方法 |
| ACC_VARARGS      | 0x0080 | 方法是否接受不定参数             |
| ACC_NATIVE       | 0x0100 | 方法是否为 navice                |
| ACC_ABSTRACT     | 0x0400 | 方法是否为 abstract              |
| ACC_STRICT       | 0x0800 | 方法是否为 strictfp              |
| ACC_SYNTHETIC    | 0x1000 | 方法是否由编译器自动产生         |

​        行文至此，也许有的读者会产生疑问，方法的定义可以通过访问标志、名称索引、描述符索引来表达清楚，但方法里面的代码去哪里了？**方法里的 Java 代码，经过 Javac 编译器编译成字节码指令之后，存放在方法属性表集合中一个名为`“Code"`的属性里面**，属性表作为 Class 文件格式中最具扩展性的一种数据项目，将在下一节中详细讲解。

​         我们继续以代码清单 6-1中的CIass文件为例对方法表集合进行分析。如图6-9所示，方法表集合的入口地址为 0x00000101，第一个 u2 类型的数据 （即计数器容量）的值为 0x0002，代表集合中有两个方法，这两个方法为编译器添加的`实例构造器<init>`和源码中定义的`方法 inc()` 。第一个方法的访问标志值为0x0001，也就是只有 ACC_PUBLIC 标志为真，名称索引值为0x0007，查代码清单6-2的常量池得方法名为`“<init>”`，描述符索引值为0x0008，对应常量为 	`“()V”`，属性表计数器 `attibutes_count` 的值为 0x0001， 表示此方法的属性表集合有 1 项属性，属性名称的索引值为 0x0009，对应常量为 `“Code”`，说明此属性是方法的字节码描述。

> 图 6-9 方法表结构实例

![](picture/img67.png)

​        与字段表集合相对应地，如果父类方法在子类中没有被重写(Override），方法表集合中就不会出现来自父类的方法信息。但同样地，有可能会出现由编译器自动添加的方法，最常见的便是类构造器` “<clinit>()” `方法和实例构造器`“<init>()”`方法

​        在 Java 语言中，要重载(Overload）一个方法，除了要与原方法具有相同的简单名称之外，还要求必须拥有一个与原方法不同的特征签名。特征签名是指一个方法中各个参数在常量池中的字段符号引用的集合，也正是因为返回值不会包含在特征签名之中，所以 Java语言里面是无法仅仅依靠返回值的不同来对一个已有方法进行重载的。但是在Class 文件格式之中，特征签名的范围明显要更大一些，只要描述符不是完全一致的两个方法就可以共存。也就是说，如果两个方法有相同的名称和特征签名，但返回值不同，那么也是可以合法共存于同一个 Class 文件中的。

***

### 4.1.7 属性表集合

​        **属性表（attribute_info）**在前面的讲解之中已经出现过数次，Class 文件、字段表、方法表都可以携带自己的属性表集合，以描述某些场景专有的信息。

​        与 Class 文件中其他的数据项目要求严格的顺序、长度和内容不同，属性表集合的限制稍微宽松一些，不再要求各个属性表具有严格顺序。

> 表 6-13 虚拟机规范预定义的属性

| 属性名称                          | 使用位置                      | 含义                                                         |
| --------------------------------- | ----------------------------- | ------------------------------------------------------------ |
| Code                              | 方法表                        | Java 代码编译成的字节码指令                                  |
| ConstantValue                     | 字段表                        | 由 final 关键字定义的常量值                                  |
| Deprecated                        | 类、方法表、字段表            | 被声明为 deprecated 的方法和字段                             |
| Exceptions                        | 方法表                        | 方法抛出的异常列表                                           |
| EnclosingMethod                   | 类文件                        | 仅当一个类为局部类或者匿名类时才能拥有这个属性，这个属性用于标示这个类所在的外围方法 |
| InnerClasses                      | 类文件                        | 内部类列表                                                   |
| LineNumberTable                   | Code 属性                     | Java 源码的行号与字节码指令的对应关系                        |
| LocalVariableTable                | Code 属性                     | 方法的局部变量描述                                           |
| StackMapTable                     | Code 属性                     | JDK 6 中新增的属性，供新的类型检查验证器（Type Checker）检查和处理目标方法的局部变量和操作数栈所需要的类型是否匹配 |
| Signature                         | 类、方法表、字段表            | 看书                                                         |
| SourceFile                        | 类文件                        | 记录源文件名称                                               |
| SourceDebugExtension              | 类文件                        | 看书                                                         |
| Synthetic                         | 类、方法表、字段表            | 标识方法或字段为编译器自动生成的                             |
| LocalVariableTypeTable            | 类                            | JDK 5 中新增的属性，他使用特征签名代替描述符，是为了引入泛型语法之后能描述泛型参数化类型而添加 |
| RuntimeVisibleAnnotations         | 类、方法表、字段表            | 以下都看书                                                   |
| RuntimeInvisibleAnnotations       | 类、方法表、字段表            |                                                              |
| RuntimeVisibleParameAnnotations   | 方法表                        |                                                              |
| RuntimeInvisibleParameAnnotations | 方法表                        |                                                              |
| AnnotationDefault                 | 方法表                        |                                                              |
| BootstrapMethods                  | 类文件                        |                                                              |
| RuntimeVisibleTypeAnnotations     | 类、方法表、字段表，Code 属性 |                                                              |
| RuntimeInvisibleTypeAnnotations   | 类、方法表、字段表，Code 属性 |                                                              |
| MethodParameters                  | 方法表                        |                                                              |
| Module                            | 类                            |                                                              |
| ModulePackages                    | 类                            |                                                              |
| ModuleMainClass                   | 类                            |                                                              |
| NestHost                          | 类                            |                                                              |
| NestMembers                       | 类                            |                                                              |

​        对于每一个属性，**它的名称**都要从常量池中引用一个 `CONSTANT_Utf8_info` 类型的常量来表示，而**属性值**的结构则是完全自定义的，只需要通过一个 u4 的长度属性去说明属性值所占用的位数即可。一个符合规则的属性表应该满足下表所定义的结构。

> 表 6-14 属性表结构

| 类型 | 名称                 | 数量             |
| ---- | -------------------- | ---------------- |
| u2   | attribute_name_index | 1                |
| u4   | attribute_length     | 1                |
| u1   | info                 | attribute_length |

***

**1. Code 属性**

​        Java 程序方法体里面的代码经过 Javac 编译器处理之后，最终变为字节码指令存储在 Code 属性内。Code 属性出现在方法表的属性集合之中，但并非所有的方法表都必须存在这个属性，譬如接口或者抽象类中的方法就不存在 Code属性，如果方法表有Code 属性存在，那么它的结构将如表 6-15 所示

> 表 6-15 Code 属性表的结构

| 类型           | 名称                   | 数量                   |
| -------------- | ---------------------- | ---------------------- |
| u2             | attribute_name_index   | 1                      |
| u4             | attribute_length       | 1                      |
| u2             | max_stack              | 1                      |
| u2             | max_locals             | 1                      |
| u4             | code_length            | 1                      |
| u1             | code                   | code_length            |
| u2             | exception_table_length | 1                      |
| exception_info | exception_table        | exception_table_length |
| u2             | attribute_count        | 1                      |
| attribute_info | attributes             | attribute_count        |

​        ` attribute_name_index` 是一项指向 `CONSTANT_Utf8_info`型常量的索引，此常量值固定为。`“Code”`，它代表了该属性的属性名称，`attribute_length` 指示了属性值的长度，由于属性名称索引与属性长度一共为 6 个字节，所以属性值的长度固定为整个属性表长度减去 6 个字节。

​        `max_stack` 代表了操作数栈（Operand Stack）深度的最大值。在方法执行的任意时刻，操作数栈都不会超过这个深度。虚拟机运行的时候需要根据这个值来分配栈帧（Stack Frame）中的操作栈深度。

​        `max_locals` 代表了局部变量表所需的存储空间。在这里，max_locals 的单位是**变量槽（Slot）**，**变量槽是虚拟机为局部变量分配内存所使用的最小单位。**对于 byte、char、float、int、short、boolean 和 returnAddress 等长度不超过 **32 位**的数据类型，每个局部变量占用一个变量槽，而 double 和 long 这两种 **64 位** 的数据类型则需要两个变量槽来存放。**方法参数**（包括实例方法中的隐藏参数 **“this”** ) 、**显式异常处理程序的参数**（Exception Handler Parameter 就是try-catch 语句中catch块中所定义的异常）、**方法体中定义的局部变量**都需要依赖局部变量表来存放。注意，并不是在方法中用了多少个局部些量，就把这些局部变量所占变量槽数量之和作为 max_locals 的值，操作数栈和局部变量表直接決定一个该方法的栈帧所耗费的内存，不必要的操作数栈深度和变量槽数量会造成内存的浪费。

​        Java 虚拟机的做法是将局部变量表中的变量槽进行重用，当代码执行超出一个局部变量的作用域时，这个局部变量所占的变量槽可以被其他局部变量所使用，Javac 编译器会根据变量的作用域来分配变量槽给各个变量使用，根据同时生存的最大局部变量数量和类型计算出 max_locals 的大小。

​         `code_length` 和 `code` 用来存储 Java 源程序编译后生成的字节码指令。code length 代表字节码长度，code 是用于存储字节码指令的一系列字节流。既然叫字节码指令，那顾名思义每个指令就是一个u1 类型的单字节，当虚拟机读取到 code 中的一个字节码时，就可以对应找出这个字节码代表的是什么指令，并且可以知道这条指令后面是否需要跟随参数，以及后续的参数应当如何解析。我们知道一个 u1 数据类型的取值范围为 0x00~0xFF，对应十进制的 0~255，也就是一共可以表达 256 条指令。目前，《 Java 虛拟机规范》已经定义了其中约 200 条编码值对应的指令含义，编码与指令之间的对应关系可查阅本书的附录：**“虚拟机字节码指令表”。**

​         关于code_length，有一件值得注意的事情，虽然它是一个 u4 类型的长度值，理论上最大值可以达到 2 的32 次幂，但是《Java 虚拟机规范》 中明确限制了一个方法不允许超过 65535 条字节码指令，即它实际只使用了 u2 的长度，如果超过这个限制，Javac 编译器就会拒绝编译。一般来讲，编写 Java 代码时只要不是刻意去编写一个超级长的方法来为难编译器，是不太可能超过这个最大值的限制的。但是，某些特殊情况，例如在编译一个很复杂的 JSP 文件时，某些 JSP 编译器会把 JSP 内容和页面输出的信息归并于一个方法之中，就有可能因为方法生成字节码超长的原因而导致编译失败。

​        **Code 属性是 Class 文件中最重要的一个属性**，如果把一个 Java 程序中的信息分为**代码** （Code,方法体里面的 Java 代码）和**元数据**（Metadata，包括类、字段、方法定义及其他信息）两部分，那么在整个Class文件里，Code属性用于描述代码，所有的其他数据项目都用于描述元数据。了解 Code 属性是学习后面两章关于字节码执行引擎内容的必要基础，能直接阅读字节码也是工作中分析 Java 代码语义问题的必要工具和基本技能，为此，笔者准备了一个比较详细的实例来讲解虚拟机是如何使用这个属性的。

​        继续以代码清单 6-1 的 TestClass.class 文件为例，如图 6-10 所示，这是上一节分析过的实例构造器“<init>()” 方法的 Code 属性。它的**操作数栈的最大深度**和**本地变量表的容量**都为 0x0001,字节码区域所占空间的长度为 0x0005 。虚拟机读取到字节码区域的长度后，按照顺序依次读入紧随的 5 个字节，并根据字节码指令表翻译出所对应的字节码指令。翻译  `“2A B7 00 0A B1”` 的过程为：

1. 读入 2A，查表得 0x2A 对应的指令为` aload_0`，这个指令的含义是将第 0 个变量槽中为 reference 类型的本地变量推送到操作数栈顶。
2. 读入 B7，查表得 0xB7 对应的指令为`invokespecial `，这条指令的作用是以栈顶的 reference 类型的数据所指向的对象作为方法接收者，调用此对象的实例构造器方法、private 方法或者它的父类的方法。这个方法有一个 u2 类型的参数说明具体调用哪一个方法，它指向常量池中的一个 `CONSTANT_Methodref_info`类型常量，即此方法的符号引用。
3. 读入 00 0A ，这是 `invokespecial ` 指令的参数，代表一个符号引用，查常量池得 0x000A 对应的常量为实例构造器 `<init>()`方法的符号引用。
4. 读入 B1，查表得 0xB1对应的指令为 `return`，含义是从方法的返回，并返回值为 void 。这条指令执行后，当前方法正常结束。

> 图 6-10 Code 属性结构实例

![](picture/img68.png)

​        这段字节码虽然很短，但我们可以从中看出它执行过程中的数据交换、方法调用等操作都是基于栈（操作数栈）的。我们可以初步猜测，Java 虚拟机执行字节码应该是基于栈的体系结构。但又发现与通常基于栈的指令集里都是无参数的又不太一样，某些指令（如 invokespecial）后面还会带有参数，关于虚拟机字节码执行的讲解是后面讲解的话题，我们不妨把这个问题放到后面解决。

​        我们再次使用 javap 命令把此 Class 文件中的另一个方法的字节码指令也计算出来，结果如代码清单 6-4 所示。

```java
// 原始Java代码
public class TestClass {
  private int m;
  public int inc(){
    return m + 1;
  }
}
```

> 代码清单 6-4 用 Javap 命令计算字节码指令（常量表部分的输出见代码清单 6-2，因版面原因这里省略掉）

![](picture/img69.png)

​         如果大家注意到 javap 中输出的 `“Args_size”` 的值，可能还会有疑问：这个类有两个方法----实例构造器 `<init>() `和 `inc()`，这两个方法很明显都是没有参数的，为什么 `Args_size` 会为 1 ？而且无论是在参数列表里还是方法体内，都没有定义任何局部变量，那 `Looals` 又为什么会等于 1 ？如果有这样疑问的读者，大概是忽略了一条 Java 语言里面的潜规则：**在任何实例方法里面，都可以通过 `“this”` 关键字访问到此方法所属的对象**。这个访问机制对 this 关键字的访问转变为对一个普通方法参数的访问，，而它的实现非常简单，仅仅是通过在 Javac 编译器编译的时候把对 this 的访问转变为对一个普通方法参数的访问，然后在虚拟机调用实例方法时自动传入此参数而已。因此在实例方法的局部变量表中至少会存在一个指向当前对象实例的局部变量，局部变量表中也会预留出第一个变量槽位来存放对象实例的引用，所以实例方法参数值以1开始计算。这个处理只对实例方法有效，如果代码清单6-1 中的 inc() 方法被声明为static，那 Args_size 就不会等于1 而是等于0了。

​        在字节码指令之后的是这个方法的显式异常处理表（下文简称“异常表”）集合，异常表对于 Code 属性来说并不是必须存在的，如代码清单 6-4 中就没有异常表生成。

​        如果存在异常表，那它的格式应如下表 6-16 所示，包含四个字段，这些字段的含义为： 如果当字节码从第 `start_pc` 行到第 `end_pc`行之间（不含第end_pc行） 出现了类型为 `catch_type`或者其子类的异常（catch_type 为指向一个 `CONSTANT_Class_info`型常量的索引 ），则转到第 `handler_pc`行继续处理。当 catch_type 的值为 0 时，代表任意异常情况都需要转到 handler_pc 处进行处理。

>  表 6-16 属性表结构

| 类型 | 名称       | 数量 |
| ---- | ---------- | ---- |
| u2   | start_pc   | 1    |
| u2   | end_pc     | 1    |
| u2   | handler_pc | 1    |
| u2   | catch_type | 1    |

​         代码清单 6-5 是一段演示异常表如何运作的例子，这段代码主要演示了在字节码层面 try-catch-finally 是如何体现的。先看看下面的java源码，想一下这段代码的返回值在出现异常和不出现异常的情况下分别应该是多少？

```java
//java 源码
public int inc() {
  int x;
  try {
    x = 1;
    return x;
  } catch(Exception e) {
    x = 2
    return x;
  } finally {
    x = 3;
  }
}
```

> 对应字节码和异常表

![](picture/img70.png)

![](picture/img71.png)

​      编译器为这段 Java 源码生成了三条异常表记录，对应三条可能出现的代码执行路径。

从 Java 代码的语义上讲，这三条执行路径分别为：

- 如果 try 语句块中出现属于 Exception 或其子类的异常，转到 catch 语句块处理;

- 如果 try 语句块中出现不属于 Exception 或其子类的异常，转到 finally 语句块处理;

- 如果 catch 语句块中出现任何异常，转到 finally 语句块处理。

​      返回到我们上面提出的问题，这段代码的返回值应该是多少？熟悉 Java 语言的读者应该很容易说出答案：如果没有出现异常，返回值是1；如果出现了 Exception 异常，返回值是2：如果出现了Exception 以外的异常，方法非正常退出，没有返回值。我们一起来分析一下字节码的执行过程，从宇节码的层面上看看为何会有这样的返回结果。

​        字节码中第 0~4 行所做的操作就是将整数 1 赋值给变量 x ，并且将此时 x 的值复制一份副本到最后一个本地变量表的变量槽中（这个变量槽里面的值在 ireturn 指令执行前将会被重新读到操作栈顶，作为方法返回值使用。为了讲解方便，笔者给这个变量槽起个名字：returnValue）。如果这时候没有出现异常，则会继续走到第 5～9 行，将变量 x 赋值为 3，然后将之前保存在 returnValue 中的整数 1 读入到操作栈顶，最后 ireturn 指令会以 int 形式返回操作栈顶中的值，方法结束。如果出现了异常， PC 寄存器指针转到第 10 行，第 10～20 行所做的事情是将 2 赋值给变量 x ，然后将变量 x 此时的值赋值给 returnValue ，最后再将变量 x 的值改为 3 。方法返回前同样将 returnValue 中保留的整数 2 读到了操作栈顶。从第 21 行开始的代码，作用是将变量 x 的值赋为 3 ，并将栈顶的异常抛出，方法结束。

​     尽管大家都知道这段代码出现异常的概率非常之小，但是并不影响它为我们演示异常表的作用。如果大家到这里仍然对字节码的运作过程比较模糊，其实也不要紧，关于虚拟机执行字节码的过程，后面章节会有更详细的讲解。

***

**2. Exception 属性**

​        这里的 Exceptions 属性是在方法表中与 Code 属性平级的一项属性，读者不要与前面刚刚讲解完的异常表产生混淆。 Exceptions 属性的作用是列举出方法中可能抛出的受查异常 （Checked Exceptions），也就是方法描述时在 throws 关键字后面列举的异常。它的结构见表 6-17。

> 表 6-17 Exception

| 类型 | 名称                  | 数量                 |
| ---- | --------------------- | -------------------- |
| u2   | attribute_name_index  | 1                    |
| u4   | attribute_length      | 1                    |
| u2   | number_of_exceptions  | 1                    |
| u2   | exception_index_table | number_of_exceptions |

​        此属性中的 number_of_exceptions 项表示方法可能抛出 number_of_exceptions 种受查异常，每一种受查异常使用一个 exception_index_table 项表示，exception_index_table是一个指向常量池中 CONSTANT_Class_info 型常量的索引，代表了该受查异常的类型。

***

**3. LineNumberTable 属性**

​        `LineNumberTable` 属性用于描述 **Java 源码行号**与**字节码行号**(字节码的偏移量)之间的对应关系。它并不是运行时必需的属性，但默认会生成到 Class 文件之中，可以在 Javac 中使用 `-g:none` 或 `-g:lines` 选项来取消或要求生成这项信息。**如果选择不生成 LineNumberTable 属性，对程序运行产生的最主要影响就是当抛出异常时，堆栈中将不会显示出错的行号，并且在调试程序的时候，也无法按照源码行来设置断点**。 

```markdown
面试问：为什么jvm执行字节码指令报错了，可以很快定位到是哪一行源代码报错？
答：因为 LineNumberTable 记录了Java源码行号与字节码行号(字节码的偏移量)之间的对应关系。
```



LineNumberTable 属性的结构如表 6-18 所示。

> 表 6-18 LineNumberTable 属性结构

| 类型             | 名称                     | 数量                     |
| ---------------- | ------------------------ | ------------------------ |
| u2               | attribute_name_index     | 1                        |
| u4               | attribute_length         | 1                        |
| u2               | line_number_table_length | 1                        |
| line_number_info | line_number_table        | line_number_table_length |

`line_number_table` 是一个数量为 `line_number_table_length`、类型为 `line_number_info` 的集合，line_number_info 表包含 `start_pc `和` line_number` 两个 u2 类型的数据项，前者是字节码行号，后者是 Java 源码行号。

***

**4. LocalVariableTable 及 LocalVariableTypeTable 属性**

​     LocalVariableTable 属性用于描述栈帧中局部变量表的变量与 Java 源码中定义的变量之间的关系，它也不是运行时必需的属性，但默认会生成到 Class 文件之中，可以在 Javac 中使用 `-g:none`或 `-g:vars` 选项来取消或要求生成这项信息。如果没有生成这项属性，最大的影响就是当其他人引用这个方法时，所有的参数名称都将会丟失，譬如 IDE 将会使用诸如  arg0、arg1 之类的占位符代替原有的参数名，这对程序运行没有影响，但是会对代码编写带来较大不便，而且在调试期间无法根据参数名称从上下文中获得参数值。LocalVariableTable 属性的结构如表 6-19 所示。

> 表 6-19 LocalVariableTable 属性结构

| 类型                | 名称                        | 数量                        |
| ------------------- | --------------------------- | --------------------------- |
| u2                  | attribute_name_index        | 1                           |
| u4                  | attribute_length            | 1                           |
| u2                  | local_variable_table_length | 1                           |
| local_variable_info | local_variable_table        | local_variable_table_length |

​       其中 local_variable_info 项目代表了一个栈帧与源码中的局部变量的关联，结构如表 6-20 所示。

> 表 6-20 local_variable_info 项目结构

| 类型 | 名称             | 数量 |
| ---- | ---------------- | ---- |
| u2   | start_pc         | 1    |
| u2   | length           | 1    |
| u2   | name_index       | 1    |
| u2   | descriptor_index | 1    |
| u2   | index            | 1    |

​        `start_pc`  和 `length` 属性分別代表了这个局部变量的生命周期开始的字节码偏移量

用范围覆盖的长度，两者结合起来就是这个局部变量在字节码之中的作用域范围。

​         `name_index`和 `deseriptor_index` 都是指向常量池中 `CONSTANT_Utf8_info` 型常量的索引，分别代表了局部变量的名称以及这个局部变量的描述符。

​        `index` 是这个局部变量在栈帧的局部变量表中变量槽的位置。当这个变量数据类型是 64 位类型时（ double 和 long )，它占用的变量槽为 `index` 和` index + 1` 两个。

​        顺便提一下，在 JDK 5 引入泛型之后， LocalVariableTable 属性增加了一个 “姊妹属性” -- LocalVariableTypeTable。这个新增的属性结构与 LocalVariableTable 非常相似，仅仅是把记录的字段描述符的 descriptor_index 替换成了字段的特征签名（Signature）。对于非泛型类型来说，描述符和特征签名能描述的信息是能吻合一致的，但是泛型引入之后，由于描述符中泛型的参数化类型被擦除掉，描述符就不能准确描述泛型类型了。因此出现了 LocalVariableTypeTable 属性，使用字段的特征签名来完成泛型的描述。

***

**5. SourceFile 及 SourceDebugExtension 属性**

略

***

**6. ConstantValue 属性**

​     `ConstantValue` 属性的作用是通知虚拟机自动为静态变量赋值。只有被 static 关键字修饰的变量（类变量）才可以使用这项属性。类似 `"int x = 123"` 和 `"static int x = 123"` 这样的变量定义在 Java 程序里面是非常常见的事情，但虚拟机对这两种变量赋值的方式和时刻都有所不同。对非 static 类型的变量（也就是实例变量）的赋值是在实例构造器 <init>() 方法中进行的；而对于类变量，则有两种方式可以选择：在类构造器 <clinit>() 方法中或者使用 ConstantValue 属性。目前 Oracle 公司实现的 Javac 编译器的选择是，如果同时使用 final 和 static 来修饰一个变量（按照习惯。这里称“常量”更贴切， 并且这个常量的数据类型是基本类型或者 java.lang.String 的话，就将会生成 ConstantValue 属性来进行初始化；如果这个变量没有被 final 修饰，或者并非基本类型及字符串，则将会选择在 <clinit> 方法中进行初始化。





