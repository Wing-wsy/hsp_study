# 1 为什么学习

比如：有一大段文本，想要提取文本中的全部单词，或者数字，或者IP地址等等。如果用传统方式实现，很麻烦。

为了解决上述问题，Java提供了正则表达式技术，专门用于处理类似文本问题。

**简单的说：正则表达式是对字符串执行模式匹配的技术**

**正则表达式：regular expression => RegExp**

***

# 2 正则表达式基本语法

**介绍**

一个正则表达式，就是用某种模式去匹配字符串的一个公式，很多人因为它们看上去比较古怪而且复杂所以不敢去使用，不过，经过练习后，就觉得这些复杂的表达式写起来还是相当简单的，而且，一旦学会之后，就能把数小时辛苦而且易错的文本处理工作缩短在在几分钟甚至几秒钟内完成。

**底层实现**

```java
/* 不分组 */
public static void main(String[] args) {
    String content = "1997年是发展过程中最重要的一个环节，1998标志着应用开始普遍。最后应用于移动、无线及有限资源的环境";
    // 1. \\d表示任意一个数字
    String regStr = "\\d\\d\\d\\d";
    // 2. 创建模式对象
    Pattern pattern = Pattern.compile(regStr);
    // 3. 创建匹配器
    Matcher matcher = pattern.matcher(content);
    // 4. 开始匹配
    while (matcher.find()) {
        System.out.println("找到：" + matcher.group(0));
    }
}

// 输出结果
找到：1997
找到：1998
```

```java
/* 分组 */
public static void main(String[] args) {
    String content = "1997年是发展过程中最重要的一个环节，1998标志着应用开始普遍。最后应用于移动、无线及有限资源的环境";
    // 1. \\d表示任意一个数字,使用小括号进行分组，有几对小括号就说明分几组，这个在group方法传入索引有关系
    String regStr = "(\\d\\d)(\\d\\d)";
    Pattern pattern = Pattern.compile(regStr);
    Matcher matcher = pattern.matcher(content);
    while (matcher.find()) {
        System.out.println("找到：" + matcher.group(0));
        System.out.println("找到1组：" + matcher.group(1));
        System.out.println("找到2组：" + matcher.group(2));
        System.out.println("---------------------------");
    }
}
// 输出结果
找到：1997
找到1组：19
找到2组：97
---------------------------
找到：1998
找到1组：19
找到2组：98
---------------------------
```

***

# 3 三个常用类

## 3.1 Pattern

`boolean matches()`

作用：验证是否整体匹配成功，返回值是boolean

使用场景：适合用来匹配是否满足指定要求的字符串

```java
String content = "13444999898";
//验证手机号
//String regStr = "^1[3|4|5|8]\\d{9}$";
String regStr = "1[3|4|5|8]\\d{9}"; // matches方法就是整体匹配，所以可以不用使用^和$
boolean matches = Pattern.matches(regStr, content);
System.out.println(matches);
```

## 3.2 Matcher

`start()` 和` end()` 方法

```java
    public static void main(String[] args) {
        String content = "1997年是发展过程中最重要的一个环节，1998标志着应用开始普遍。最后应用于移动、无线及有限资源的环境";
        // 1. \\d表示任意一个数字,使用小括号进行分组，有几对小括号就说明分几组，这个在group方法传入索引有关系
        String regStr = "(\\d\\d)(\\d\\d)";
        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            System.out.println("开始索引：" + matcher.start());
            System.out.println("结束索引：" + matcher.end());
            System.out.println("找到：" + content.substring(matcher.start(),matcher.end()));
        }
    }
```



## 3.3 PatternSyntaxException

# 4 分组、捕获、反向引用、替换

## 4.1 分组

| 常用分组构造形式  | 说明                                                         |
| ----------------- | ------------------------------------------------------------ |
| (pattern)         | 非命名捕获。捕获匹配的子字符串。编号为零的第一个捕获是由整个正则表达式模式匹配的文本，其它捕获结果则根据左括号的顺序从1开始自动编号 |
| (? <name>pattern) | 命名捕获。将匹配的子字符串捕获到一个组名称或编号名称中。用于name的字符串不能包含任何标点符号，并且不能以数字开头。可以使用单引号替代尖括号，例如（?'name'） |

```java
/* 分组 */
public static void main(String[] args) {
    String content = "1997年是发展过程中最重要的一个环节，1998标志着应用开始普遍。最后应用于移动、无线及有限资源的环境";
    // 1. \\d表示任意一个数字,使用小括号进行分组，有几对小括号就说明分几组，这个在group方法传入索引有关系
    // String regStr = "(\\d\\d)(\\d\\d)";
    String regStr = "(?<g1>\\d\\d)(?<g2>\\d\\d)";
    // 2. 创建模式对象
    Pattern pattern = Pattern.compile(regStr);
    // 3. 创建匹配器
    Matcher matcher = pattern.matcher(content);
    // 4. 开始匹配
    while (matcher.find()) {
        System.out.println("找到：" + matcher.group(0));
        System.out.println("找到1组：" + matcher.group(1));
        System.out.println("找到2组：" + matcher.group(2));
        // 也可以使用名称方式获取
        System.out.println("找到1组(名称)：" + matcher.group("g1"));
        System.out.println("找到2组(名称)：" + matcher.group("g2"));
        System.out.println("---------------------------");
    }
}
// 输出结果
找到：1997
找到1组：19
找到2组：97
找到1组(名称)：19
找到2组(名称)：97
---------------------------
找到：1998
找到1组：19
找到2组：98
找到1组(名称)：19
找到2组(名称)：97
---------------------------
```

## 4.2 非捕获分组

可以省略重复的内容

`?:pattern`

描述：匹配 pattern 但不捕获该匹配的子表达式，即它是一个非捕获匹配【就是不能用matcher.group(1)来获取分组的意思】，不存储供以后使用的匹配。这对于用"or"字符(|) 组合模式部件的情况很有用。

例如，"`industr(?:ylies) `是比“`industry l industries`"更经济的表达式。

```java
public static void main(String[] args) {
    String content = "和哦哦了韩顺平教育 集散地韩顺平老师 开始的风景韩顺平同学还送i度分";
    //String regStr = "韩顺平教育|韩顺平老师|韩顺平同学";
    // 上面的写法等价于下面
    String regStr = "韩顺平(?:教育|老师|同学)";
    Pattern pattern = Pattern.compile(regStr);
    Matcher matcher = pattern.matcher(content);
    while (matcher.find()) {
        System.out.println("找到：" + matcher.group(0));
    }
}
```

`?=pattern`

描述：它是一个非捕获匹配，例如，"`Windows (?=95|98|NT|2000) `，匹配"Windows 2000" 中的 “Windows”，但不匹配“Windows 3.1”中的“Windows”

```java
public static void main(String[] args) {
    String content = "和哦哦了韩顺平教育 集散地韩顺平老师 开始的风景韩顺平同学还送i度分";
    String regStr = "韩顺平(?=教育|老师)"; // 会找到两个韩顺平，同学的那个韩顺平没有匹配上
    Pattern pattern = Pattern.compile(regStr);
    Matcher matcher = pattern.matcher(content);
    while (matcher.find()) {
        System.out.println("找到：" + matcher.group(0));
    }
}
```

`?!pattern`

描述：它是一个非捕获匹配，该表达式不处于匹配pattern的字符串的起始点的搜索字符串，例如，"`Windows (?!95|98|NT|2000) `，匹配"Windows 3.1" 中的 “Windows”，但不匹配“Windows 95”中的“Windows”

```java
public static void main(String[] args) {
    String content = "和哦哦了韩顺平教育 集散地韩顺平老师 开始的风景韩顺平同学还送i度分";
    String regStr = "韩顺平(?!教育|老师)"; // 会找到一个韩顺平，同学的那个韩顺平匹配上了
    Pattern pattern = Pattern.compile(regStr);
    Matcher matcher = pattern.matcher(content);
    while (matcher.find()) {
        System.out.println("找到：" + matcher.group(0));
    }
}
```

## 4.3 非贪婪匹配

`?`

```mysql
public static void main(String[] args) {
    String content = "hello11111 ok";
    String regStr = "\\d+"; // 这种是贪婪匹配，会一次匹配到11111
    String regStr = "\\d+?"; // 非贪婪匹配，会尽量匹配最少的，这里会匹配到5个1
    Pattern pattern = Pattern.compile(regStr);
    Matcher matcher = pattern.matcher(content);
    while (matcher.find()) {
        System.out.println("找到：" + matcher.group(0));
    }
}
```

## 4.4 反向引用

1. 匹配连续两个相同的数字

```java
public static void main(String[] args) {
    String content = "1997年是发展过程中最重要的一个环节，1998标志着应用开始普遍。最后应用于移动、无线及有限资源的环境，tom11,hei22,23,44";
    // 匹配连续两个相同的数字  \\1意思是第一个\\d再重复一次
    String regStr = "(\\d)\\1";
    Pattern pattern = Pattern.compile(regStr);
    Matcher matcher = pattern.matcher(content);
    while (matcher.find()) {
        System.out.println("找到：" + matcher.group(0));
    }
}
```

2. 匹配连续五个相同的数字

```java
public static void main(String[] args) {
    String content = "1998标志着应用开始普遍 88888。最后应用于移动99999、无线及有限资源的环境，tom11,hei22,23,44";
    // 匹配连续五个相同的数字 \\4意思是第一个\\d再重复四次
    String regStr = "(\\d)\\1{4}";
    Pattern pattern = Pattern.compile(regStr);
    Matcher matcher = pattern.matcher(content);
    while (matcher.find()) {
        System.out.println("找到：" + matcher.group(0));
    }
}
```

3. 匹配对称的四位数字

```java
public static void main(String[] args) {
    String content = "应用开始普遍 88888。最后应7887用于移动99999、无线及有限资源的环境5225，tom11,hei22,23,44";
    // 匹配对称的四位数字 \\2意思是第二个\\d重复一次，\\2意思是第一个\\d重复一次
    String regStr = "(\\d)(\\d)\\2\\1";
    Pattern pattern = Pattern.compile(regStr);
    Matcher matcher = pattern.matcher(content);
    while (matcher.find()) {
        System.out.println("找到：" + matcher.group(0));
    }
}
```

4. 请在字符串中检素商品编号，，形式如：12321-333999111 这样的号码，要求满足前面是一个五位数，然后一个-号，然后是一个九位数，连续的每三位要相同

```java
public static void main(String[] args) {
    String content = "标志着12321-333999111应用开始普遍 88888。最后应7887用";
    // 请在字符串中检素商品编号，，形式如：12321-333999111 这样的号码，要求满足前面是一个五位数，然后一个-号，然后是一个九位数，连续的每三位要相同
    String regStr = "\\d{5}-(\\d)\\1{2}(\\d)\\2{2}(\\d)\\3{2}";
    Pattern pattern = Pattern.compile(regStr);
    Matcher matcher = pattern.matcher(content);
    while (matcher.find()) {
        System.out.println("找到：" + matcher.group(0));
    }
}
```

## 4.5 替换指定字符串

```java
public static void main(String[] args) {
        String content = "标志着应用开始普遍 。最开始后应用";
        String regStr = "开始";
        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(content);
        String s = matcher.replaceAll("");
        System.out.println(s);  // 输出：标志着应用普遍 。最后应用
    }
```

# 5 String类替换指定字符串

## 5.1 将 jdk1.3 和 jdk1.4 替换成 jdk

```java
public static void main(String[] args) {
    String content = "标志着应用开始普遍 。最开始jdk1.3后应用jdk1.4";
    String s = content.replaceAll("jdk1\\.3|jdk1\\.4", "jdk");  // 将 jdk1.3 和 jdk1.4 替换成 jdk
    System.out.println(s);
}
```

## 5.2 验证手机号

```java
public static void main(String[] args) {
    String content = "13988889999";
    boolean matches = content.matches("1(38|39)\\d{8}");
    if (matches) {
        System.out.println("验证成功");
    }else {
        System.out.println("验证失败");
    }
}
```

## 5.3 指定字符分割

```java
public static void main(String[] args) {
    String content = "hello*adsf-jksd122shmi+北京";
    String[] split = content.split("\\*|-|\\+|\\d+");
    for (String s : split) {
        System.out.println(s);
    }
}
hello
adsf
jksd
shmi
北京
```



# 6 元字符

转义号：\ \

\ \ 符号说明：在我们使用正则表达式去检索某些特殊字符的时候，需要用到转义字符，否则检索不到结果，甚至会报错。

再次提示：在Java的正则表达式中，两个 \ \ 代表其它语言中的一个\

```java
public static void main(String[] args) {
    String content = "abc$(abc(123";
    //String regStr = "(";  这样写代码会报错
    String regStr = "\\(";
    Pattern pattern = Pattern.compile(regStr);
    Matcher matcher = pattern.matcher(content);
    while (matcher.find()) {
        System.out.println("找到：" + matcher.group(0));
    }
}
```

需要用到转义字符的字符有以下：`.`、`*`、 `+`、 `(`、` )`、 `$`、` /`、 `\`、 `?`、` [`、 `]`、` ^`、 `{`、` }`

***

## 6.1 限定符 

用于指定其前面的字符和组合项连续出现多少次

| 符号  | 含义                             | 示例        | 说明                                               | 匹配输入                |
| ----- | -------------------------------- | ----------- | -------------------------------------------------- | ----------------------- |
| *     | 指定字符重复0次或n次（无要求）   | (abc)*      | 仅包含任意个abc的字符串，等效于\w*                 | abc、abcabcabc          |
| +     | 指定字符重复1次或n次（至少一次） | m+(abc)*    | 以至少1个m开头，后接任意个abc的字符串              | m、mabc、mabcabc        |
| ？    | 指定字符重复0次或1次（最多一次） | m+abc?      | 以至少1个m开头，后接ab或abc的字符串                | mab、mabc、mmmab、mmabc |
| {n}   | 只能输入n个字符                  | [abcd]{3}   | 由abcd中字母组成的任意长度为3的字符串              | abc、dbc、adc           |
| {n,}  | 指定至少n个匹配                  | [abcd]{3,}  | 由abcd中字母组成的任意长度不小于3的字符串          | aab、dbc、aaabdc        |
| {n,m} | 指定至少n个但不多于 m 个匹配     | [abcd]{3,5} | 由abcd中字母组成的任意长度不小于3，不大于5的字符串 | abc、abcd、aaaaa、bcdab |

```java
public static void main(String[] args) {
    String content = "1111aaaaaahello";
    // 细节：java匹配默认贪婪匹配，即尽可能匹配多的
    String regStr = "a{3,4}";  // 表示匹配 aaa 或 aaaa 
    Pattern pattern = Pattern.compile(regStr);
    Matcher matcher = pattern.matcher(content);
    while (matcher.find()) {
        System.out.println("找到：" + matcher.group(0));
    }
}
aaaa
```



## 6.2 选择匹配符

在四配某个宇符串的时候是选择性的，即：既可以匹配这个，又可以匹配那个，这时你需要用到 选择匹配符号

| 符号 | 符号                        | 示例   | 解释     |
| ---- | --------------------------- | ------ | -------- |
| ｜   | 匹配 “｜”之前或之后的表达式 | ab\|cd | ab或者cd |

```java
String content = "han 春天 黑夜";
String regStr = "han|春|夜"
Pattern pattern = Pattern.compile(regStr);
Matcher matcher = pattern.matcher(content);
while (matcher.find()) {
    System.out.println("找到：" + matcher.group(0));
}
找到：han
找到：春
找到：夜
```

## 6.3 分组组合和反向引用符

## 6.4 特殊字符

## 6.5 字符匹配符

| 符号  | 含义                                                         | 示例            | 解释                                                         | 匹配输入               |
| ----- | ------------------------------------------------------------ | --------------- | ------------------------------------------------------------ | ---------------------- |
| [ ]   | 可接收的字符列表                                             | [efgh]          | e、f、g、h 的任意1个字符                                     |                        |
| [ ^ ] | 不接收的字符列表                                             | [^abc]          | 除 a、b、c 之外的任意1个字符，包括数字和特殊符号             |                        |
| -     | 连字符                                                       | A-Z             | 任意单个大写字母                                             |                        |
| .     | 匹配除 \n 以外的任何字符                                     | a..b            | 以a开头，b结尾，中间包括2个任意字符的长度为4的字符串         | aaab、aefb、a35b、a#*b |
| \ \d  | 匹配单个数字字符，相当于[0-9]                                | \ \d{3}(\ \d)？ | 包含3个或4个数字的字符串（？代表0个或1个）                   | 123、9876              |
| \ \D  | 匹配单个非数字字符，相当于[ ^ 0-9]                           | \ \D(\ \d)*     | 以单个非数字字符开头，后接任意个数字字符串                   | a、A342                |
| \ \w  | 匹配单个数字、大小写字母字符，还有“-”，相当于[0-9a-zA-Z] 和 “-” | \ \d{3}\ \w{4}  | 以3个数字字符开头的长度为7的数字字符串                       | 234pbed、12345Pe       |
| \ \W  | 匹配单个非数字、大小写字母字符相当于[ ^ 0-9a-zA-Z]           | \ \W + \ \d{2}  | 以至少1个非数字字母字符开头,2个数字字符结尾的字符串（+ 代表1个或多个） | \#29.、#?@10           |

***

例子1：比如[a-z]、[A-Z]去匹配 a11c8，看会得到什么

```java
public static void main(String[] args) {
    String content = "a11c8";
    String regStr = "[a-z]";
    Pattern pattern = Pattern.compile(regStr);
    Matcher matcher = pattern.matcher(content);
    while (matcher.find()) {
        System.out.println("找到：" + matcher.group(0));
    }
}

// [A-Z] 不演示，因为区分大小写，因此匹配不到任何结果

// 输出结果
找到：a
找到：c
```

例子2：java 正则表达式默认是区分字母大小写的，如何实现不区分大小写

- `(?i)abc`：表示 abc 都不区分大小写
- `a(?i)bc`：表示 bc 不区分大小写
- `a((?i)b)c`：表示 b 不区分大小写
- 也可以使用：Pattern.compile(regStr,Pattern.CASE_INSENSITIVE) 不区分大小写

```java
/* 方式1 */
public static void main(String[] args) {
    String content = "a11c8ABc";
    String regStr = "(?i)abc";
    Pattern pattern = Pattern.compile(regStr);
    Matcher matcher = pattern.matcher(content);
    while (matcher.find()) {
        System.out.println("找到：" + matcher.group(0));
    }
}
// 输出结果
找到：ABc
```

```java
/* 方式2 */
public static void main(String[] args) {
    String content = "a11c8ABc";
    String regStr = "abc";
    // Pattern.CASE_INSENSITIVE 表示匹配时不区分字母大小写
    Pattern pattern = Pattern.compile(regStr,Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(content);
    while (matcher.find()) {
        System.out.println("找到：" + matcher.group(0));
    }
}
// 输出结果
找到：ABc
```

## 5.6 定位符

定位符，规定要匹配的字符串出规的位置，比如在字符串的开始还是在结束的位置，这个也是相当有用的，必须掌握。

| 符号 | 含义                   | 示例               | 说明                                                         | 匹配输入                                                     |
| ---- | ---------------------- | ------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| ^    | 指定起始字符           | ^[0-9]+[a-z]*      | 以至少1个数字开头，后接任意个小写字母的字符串                | 123、6aa、555edf                                             |
| $    | 指定结束字符           | ^[0-9]\ \ -[a-z]+$ | 以至少1个数字开头后接连接字符“-”，并以至少1个小写字母结尾的字符串 | 1-a                                                          |
| \ \b | 匹配目标字符串的边界   | han\ \ b           | 这里说的字符串的边界指的是子串间有空格，或者是目标字符串的结束位置 | hanshunping sp<font color="red">han</font> nn<font color="red">han</font> |
| \B   | 匹配目标字符串的非边界 | han\ \ B           | 和\b的含义刚刚相反                                           | <font color="red">han</font>shunping sphan nnhan             |

# 7 正则表达式应用实例

1.验证汉字

```java
String regStr = "^[\u0391-\uffe5]+$";
```

2.验证邮政编码

要求：1-9开头的一个六位数，比如：123890

```java
String regStr = "^[1-9]\\d{5}$";
```

3.验证QQ

要求：1-9开头的一个5~10位数，比如：12389,1234567891

```java
String regStr = "^[1-9]\\d{4,9}$";
```

4.手机号码

要求：必须以13、14、15、18开头的11位数，比如：13444999898

```java
String regStr = "^1[3|4|5|8]\\d{9}$";
```

5.验证URL

看视频：https://www.bilibili.com/video/BV1Eq4y1E79W?p=17&spm_id_from=pageDriver&vd_source=dc02a4c6e2a8e915fb8ee431999e5b2c

```java
// 这个可以验证一个完整域名 比如：https://www.bilibili.com
String regStr = "^((http|https)://)([\\w-]+\\.)+[\\w-]+";
```

