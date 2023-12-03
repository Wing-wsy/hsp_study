#### 一、Java基础

##### 1 注意事项和细节

>  一个源文件最多只有一个public类(也可以没有)，其它类的个数不限

`代码：org.example.Hello`

当执行Hello类中的main方法后，编译后会生成 `Hello.class`、`Dog.class`、`Cat.class` 三个class文件

> 如果源文件包含一个public类，则文件名必须按该类名命名

###### 1.1 小思考

```markdown
问：Java多个类写到同一个文件和分开写有什么好处呢？
答：有好处和弊端，
好处：方便管理，比如Dog和Cat类写在了Hello类中，而一个文件只能有一个类使用public修饰，所以Dog和Cat不能使用public修饰，所以只能在本包内使用，在其他包无法使用，刚好这两个类我们也仅仅设计成只能 Hello类 来访问，所以这时写到一个文件中。

弊端：不能被其他包的类访问，不方便后续扩展。
```

###### 1.2 学习新技术的方法：

```markdown
1、先学习基本原理和基本语法（不要考虑细节）
2、快速入门案例（基本程序，crud）
3、考虑研究技术的注意事项，使用细节，使用规范，如何优化
```

##### 2 转义字符

###### 2.1 java 常用的转义字符

```markdown
1. \t：一个制表位，实现对齐功能
2. \n：换行符
3. \\：一个\
4. \ "：一个"
5. \ '：一个'
6. \r：一个回车
```

`org.example.ChangeChar`

###### 2.2 转义字符练习

`org.example.ChangeCharExer01`

##### 3 注释

###### 3.1 介绍

用于注解说明**解释程序的文字**就是注释，注释提高了代码的阅读性（可读性）；注释是一个程序员必须要具有的良好变成习惯。将自己的思想通过注释先整理出来，再用代码去实现。

Java中的注释类型

- 单行注释
- 多行注释
- 文档注释 

###### 3.2 注释使用

```markdown
【单行注释】
	格式：//注释的文字

【多行注释】
	格式：/* 注释的文字 */
	
【使用细节】
1、被注释的文字，不会被JVM解释执行
2、多行注释里面不允许哟多行注释嵌套
```

###### 3.3 文档注释

```markdown
注释内容可以被JDK提供的工具javadoc所解析，生成一套以网页文件形式体现的该程序的说明文档，一般写在类。
```

`org.example.Comment02`

生成文档命令：wing@WangShaoYoudeMacBook-Pro example % javadoc -d temp -author -version Comment02.java

生成的文档保存在temp目录下，可以直接点开目录里的index.html就能访问到刚才生成的文档了。

| 标签          | 描述                                                 | 示例                                                         |
| ------------- | ---------------------------------------------------- | ------------------------------------------------------------ |
| @author       | 标识一个类的作者                                     | @author description                                          |
| @deprecated   | 指明一个过期的类或成员                               | @deprecated description                                      |
| {@docRoot}    | 指明当前文档根目录的路径                             | Directory Path                                               |
| @exception    | 标志一个类抛出的异常                                 | @exception exception-name explanation                        |
| {@inheritDoc} | 从直接父类继承的注释                                 | Inherits a comment from the immediate superclass             |
| {@link}       | 插入一个到另一个主题的链接                           | {@link name text}                                            |
| {@linkplain}  | 插入一个到另一个主题的链接，但是该链接显示纯文本字体 | Inserts an in-line link to another topic.                    |
| @param        | 说明一个方法的参数                                   | @param parameter-name explanation                            |
| @return       | 说明返回值类型                                       | @return explanation                                          |
| @see          | 指定一个到另一个主题的链接                           | @see anchor                                                  |
| @serial       | 说明一个序列化属性                                   | @serial description                                          |
| @serialData   | 说明通过writeObject()和writeExternal()方法写的数据   | @serialData description                                      |
| @serialField  | 说明一个ObjectStreamField组件                        | @serialField name type description                           |
| @since        | 标记当引入一个特定的变化时                           | @since release                                               |
| @throws       | 和 @exception标签一样                                | The @throws tag has the same meaning as the @exception tag.  |
| {@value}      | 显示常量的值，该常量必须是static属性                 | Displays the value of a constant,which must be a statuc field |
| @version      | 指定类的版本                                         | @version info                                                |

###### 3.4 Java代码规范

1. 类、方法的注释，要以javadoc的方式来写
2. 非java Doc的注释，往往是给代码的维护者看的，着重告诉读者为什么要这样写，如何修改，注意什么问题等。（那反过来JavaDoc的注释是给使用者看的，使用者只要知道怎么使用就行）
3. 运算符和 = 两边习惯性各加一个空格，比如 int i = 1 + 2 * 3;
4. 源文件使用utf-8编码
5. 行宽度不要超过80字符
6. 代码编写`次行风格`和`行尾风格`

```java
// 行尾风格(推荐)
public void test{
  
}

// 次行风格
public void test
{
  
}
```

 ##### 4 变量

###### 4.1 变量介绍

变量是程序的基本组成单位

变量有三个基本要素（类型 + 名称 + 值 ）



###### 4.2 +号的使用

1. 当左右两边都是数值类型时，做加法运算
2. 当左右两边有一方为字符串，做拼接运算



###### 4.3 数据类型

每一种数据都定义了明确的数据类型，在内存中分配了不同大小的内存空间（字节）

Java数据类型分为两大类：`基本数据类型`和`引用数据类型`

- `基本数据类型`：有8种，`数值型`[byte,short,int long,float,double] `字符型`[char], `布尔型`[boolean]
- `引用类型`：类、接口、数组

| 类型         | byte[字节] | short[短整型]                 | int[整型]                               | long[长整型] | float | double | boolean | char  |
| ------------ | ---------- | ----------------------------- | --------------------------------------- | ------------ | ----- | ------ | ------- | ----- |
| 占用存储空间 | 1字节      | 2字节                         | 4字节                                   | 8字节        | 4字节 | 8字节  | 1字节   | 2字节 |
| 范围         | -128～127  | -2^15~2^15-1【-32768～32767】 | -2^31~2^31-1【-2147483648～2147483647】 | -2^63~2^63-1 |       |        |         |       |

> 小思考

```markdown
问：如果我的数很大，long也存不进去怎么办呢？
答：可以使用 BigInteger、BigDecimal
```

> 整型的使用细节

- Java各整数类型有固定的范围和字段长度，不受具体OS（操作系统）的影响，以保证Java程序的可移植性
- Java的整型常量默认为int类型（比如直接在代码写 5），声明long类型常量须后加'l'或'L'，建议使用大些L，因为小写和数字1不好区分
- Java程序中变量常声明为int类型，除非不足以表示大数，才使用long
- bit：计算机中的`最小存储单位`，byte：计算机中`基本存储单元`，1byte = 8 bit。

> 浮点类型

| 类型          | 占用存储空间 | 范围                   |
| ------------- | ------------ | ---------------------- |
| 单精度float   | 4字节        | -3.403E38 ~ 3.403E38   |
| 双精度doouble | 8字节        | -1.798E308 ~ 1.798E308 |

说明：

- 关于浮点数在机器中存放形式的简单说明，`浮点数 = 符号位 + 指位数 + 尾位数`
- 尾数部分可能丢失，造成精度损失（小数都是近似值）

> 浮点型的使用细节

- Java的浮点型常量默认为double类型（比如直接在代码写 5.1，见下面演示），声明float类型常量须后加'f'或'F'，建议使用大些F
- 浮点型常量有`两种表示形式`

​       十进制数形式：如：5.12、  512.0f    .512（必须有小数点，见下面演示）

​       科学计数法形式：如：5.12e2[5.12 * 10的2次方].   5.12E-2[5.12 / 10的2次方]

- 通常情况下，应该使用double型，因为它比float型更精确
- 浮点数使用陷进：2.7 和 8.1/3 比较

> 演示

```java
public class Test{
  public static void main(String[] args){
    float num1 = 1.1; // 这里错误，因为浮点型常量默认为double类型，应该这样写 float num1 = 1.1F
    double num2 = .512; // 正确，.512等价于 0.512 0可以省略
    System.out.println(5.12e2); // 512.0
    System.out.println(5.12E-2); // 0.0512
    
    // 比较 double 和 float 的精度
    double num9 = 2.1234567851;
    float num10 = 2.1234567851F;
    System.out.println(num9); //  2.1234567851 (全部能输出，精度更高)
    System.out.println(num10); //  2.1234567  (部分输出，精度比double低)
  }
}
```

> 字符类型

> 字符类型的使用细节

- 字符常量是用单引号（''）括起来的单个字符。例如：char c1 = 'a'; char c2 = '王';
- java中还允许使用转义字符'\'来将其后的字符转变为特殊字符型常量。例如： char c3 = '\n'；
- 在Java中，char的本质是一个整数，在输出时，是 Unicode码对应的字符，要输出对应的数字，可以(int)字符，见下面演示
- char类型是可以进行运算的，相当于一个整数，因为它都对应有Unicode码

> 演示

```java
public class Test{
  public static void main(String[] args){
    char c1 = 'a';
    char c2 = '\t';
    char c3 = '王';
    char c4 = 97;
    System.out.println(c1); // a
    System.out.println(c2); // 
    System.out.println(c3); // 王
    System.out.println(c4); // a   [会输出97表示的字符]
    System.out.println((int)c1); // 97
  }
}
```



字符类型可以使用单个字符，字符类型是char，char是两个字节（可以存放汉子），多个字符使用字符串String

###### 4.4 编码

> 字符编码表

- ASCII(ASCII编码表，`一个字节`表示，一共 128 个字符，实际上一个字节可以表示 256 个字符，只用了 128个)
- Unicode(Unicode编码表固定大小的编码，使用两个字节来表示字符，字母和汉字统一都是占用两个字节，这样浪费空间)
- Utf-8（编码表，大小可变的编码，字母使用1个字节，汉字使用3个字节）
- Gbk（可以表示汉字，而且范围广，字母使用1个字节，汉字2个字节）
- Gb2312(可以表示汉字，gb2312 < gbk，用得少，了解)
- big5码（繁体中文，台湾，香港）



> 小思考

```markdown
问：有了ASCII为什么还要用Unicode呢？
答：因为一开始只有美国人用，美国只有字母 ASCII 128 就够表示了，但是这时中国人也要用，那128不够用了（其他国家也是一样不够用），即使使用一个字节用满 256 个也不够用了，所以机构出来了重新设计了一个在ASCII标准下的另一个标准 Unicode（使用两个字节来表示）。
Unicode码兼容ASCII码（比如a在Unicode表示97，在ASCII码中也是）

补充：utf-8也是在Unicode之上进行的改进，因为Unicode都是使用的两个字节表示（字母也是），而utf-8变得更加灵活了（字母使用1个字节，汉字使用3个字节表示）

补充：相同的一个.java文件，如果文件里面的内容仅仅就是 “abc王”,如果保存该文件使用不同的编码方式保存，那么文件的大小也不一样，如果使用utf-8保存，那么文件大小为：6字节；如果使用gbk保存相同的文件，那么文件大小为：5字节；

补充：如果有一个文件里面很极端，内容是 aabbccaabbccaabbccaabbcc王aabbccaabbcc，很长的内容里面只有一个汉字，如果使用Unicode编码，那么这就很浪费存储空间了,文件就会变得很大，因为不管字母还是汉字都是使用2个字节来表示；但此时使用utf-8好处就出现了，这时字母只需要1个字节，汉字3个字节，也因为汉字使用3子字节表示，所以能表示的范围更多了，gbk虽然字母也使用一个字节，但是汉字使用两个字节，表示的范围没有utf-8范围广。

补充：utf-8是一种变长的编码方式，它可以使用 1-6 个字节来表示一个符号，根据不同的符号而变化字节长度。
```

> 布尔类型

- 布尔类型也叫boolean类型，boolean类型数据只允许取值 true 和 false，无 null
- boolean 类型占用 1 个字节
- boolean 类型适用于逻辑运算，一般用于程序流程控制（if、while、do-while、for）

###### 4.5 数据类型转换













#####  作业

> 1、JDK,JRE,JVM的关系

1. JDK = JRE + Java开发工具
2. JRE = JVM + 核心类库



##### java API在线文档

> 这个网站很强大，里面有很多技术的文档

https://www.matools.com/

https://www.matools.com/api/java8













































