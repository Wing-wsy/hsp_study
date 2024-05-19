# Mac配置JDK环境变量

较高版本的JDK一般在安装的时候就自动配置好了环境变量

## 1 验证

在资源库里面的java文件夹下查看已经安装的jdk

![](https://img-blog.csdnimg.cn/direct/2362578ccf1d4a8d9bbfecceb566bb4d.png)

在终端输入`echo $PATH`可以查看当前已配置的环境变量

![](https://img-blog.csdnimg.cn/direct/8dac1660c7084f3d9cd4083c213ccd63.png)

在终端输入`java -version`可以查看当前jdk版本信息

![](https://img-blog.csdnimg.cn/direct/8b84a90ac0cd4b52837bc6a5df9df45b.png)

我的电脑上有jdk1.8和jdk17，都没有手动配置[环境变量](https://so.csdn.net/so/search?q=环境变量&spm=1001.2101.3001.7020)，但是在终端输入`java -version`只显示了jdk17的版本信息，可见高版本的jdk是安装的时候自动配置好的。

## 2 手动配置环境变量

我们就以jdk1.8为例：
要将环境变量写进配置文件内，这里有两个配置文件：`.bash_profile文件`和`.zshrc文件`，Mac系统如果在凯特琳娜版本及之前用前者。

（可以两个文件都配置上，不同命令终端读取的配置文件可能不太一样，这里可以在两个主要配置文件中都写入环境变量（配置内容是一样的，拷贝就行），省很多麻烦）

【下面演示只配置.zshrc文件】

### 2.1 打开配置文件

终端输入回车之后，自动打开，初始可能有一些东西在里面

```sh
open -e .zshrc
# 如果一开始没有这个文件，那么久创建
touch .zshrc
```

### 2.2 写配置文件

将下面代码直接复制到打开的配置文件中然后保存（JAVA_HOME只要改成自己的，其它不变）

```xml
JAVA_HOME=我的jdk路径
#我这里本机的jdk路径是：/Library/Java/JavaVirtualMachines/jdk-1.8.jdk/Contents/Home
#所以配置为JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-1.8.jdk/Contents/Home
PATH=$JAVA_HOME/bin:$PATH:.
CLASSPATH=$JAVA_HOME/lib/tools.jar:$JAVA_HOME/lib/dt.jar:.
export JAVA_HOME
export PATH
export CLASSPATH
```

### 2.3 使配置生效

```sh
source ~/.zshrc
source .bash_profile
```

**然后就配置成功了**

## 3 验证是否成功

关闭终端，再重新打开输入`java -version`，此时显示的是1.8版本

![](https://img-blog.csdnimg.cn/direct/6ea09b1adf0841c89fdec7bf5cdd6209.png)

**在命令行中使用jdk的时候**，系统默认配置的 JDK [17](https://so.csdn.net/so/search?q=17&spm=1001.2101.3001.7020) 只会在没有手动配置 JDK 环境变量时发挥作用，它会将 JDK 17 加入到系统的默认搜索路径中。但是，如果你手动配置了 JDK 8 的环境变量，那么系统会优先使用手动配置的 JDK 8。

![](https://img-blog.csdnimg.cn/direct/319d76e7eab149e7b0f82edcb0a16c6e.png)

结束！
