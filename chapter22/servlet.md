# 1 引言

## 1.1 C/ S架构和B/S架构

> `C/S`和`B/S`是软件发展过程中出现的两种软件架构方式

## 1.2 C/S 架 构 (Client/Server客户端/服务器 )

> - `特点`：必须在客户端安装特定软件
> - `优点`：图形效果显示较好( 如:3D游戏）
> - `缺点`：服务器的软件和功能进行升级，客户端也必须升级、不利于维护 。 
> - 常见的C/S 程序 : QQ、微信等

![](pictrue/img01.png)

## 1.3 B/S 架构 (Browser/Server浏览器/服务器 )

> - `特点` : 无需安装客户端 ， 任何浏览器都可直接访问
> - `优点`：涉及到功能的升级，只需要升级服务器端
> - `缺点`：图形显示效 果不如C/S架构
> - 需要通过HTTP协议访问

![](pictrue/img02.png)

***

# 2 服务器

## 2.1 概念

### 2.1.1 什么是Web

> Web（World Wide Web) 称为万维网，简单理解就是网站， 它用来表示Internet主机上供外界访问的资源。
>
> internet 上供外界访问的资源分为两大类
>
> - `静态资源`：指Web页面中供人们浏览的数据始终是不变的。( HTML、CSS)
> - `动态资源`：指Web 页面中供人们浏览的数据是由程序产生的，不同时间点，甚至不同设备访问Web页面看到的内容各不相同。 (JSP/Servlet)
> - 在 Java中 ，动态Web资源开发技术我们统称为Java Web 。

### 2.1.2 什么是Web服务器

> Web服务器是运行及发布Web应用的容器， 只有将开发的Web项目放置到该容器中，才能使网络中的所有用户通过浏览器进行访问。

## 2.2 常见服务器

> - ﻿开源：OpenSource（1、开放源代码；2、免费）
>   - `Tomcat`(主流web服务器之一，适合初学者）
>   - `jetty `(淘宝，运行效率比Tomcat高）
>   - ﻿`resin`(新浪，所有开源服务器软件中，运行效率最高的）
>   - 三者的用法从代码角度完全相同，只有在开启、 关闭服务器软件时对应的命令稍有区别。掌握一个即掌握所有
>
> - ﻿收费
>   - WebLogic (Oracle)
>   - ﻿﻿WebSphere (IBM)
>   - 提供相应的服务与支持，软件大，耗资源

## 2.3 Tomcat服务器

> Tomcat是Apache 软件基金会 (Apache Sofware Foundation)的Jakarta 项目中的一个核心项目，免费开源、并支持Servlet 和JSP 规范。目前Tomcat最新版本为9.0。
>  Tomcat 技术先进、性能稳定，深受Java 爱好者喜爱并得到了部分软件开发商的认可，成为目前比较流行的web 应用服务器

## 2.4 Tomcat安装

### 2.4.1 下载

> 官网下载(http://tomcat.apache.org/） Tomcat8.5解压缩版本

### 2.4.2 解压安装

> 将Tomcat解压到一个没有特殊符号的目录中（一般纯英文即可）
>
> 注意：
>
> - ﻿不建议将服务器软件放在磁盘层次很多的文件夹
> - ﻿不建议放在中文路径下

### 2.4.3 Tomcat目录结构

![](pictrue/img04.png)

<img src="pictrue/img03.png" style="zoom:50%;" />

## 2.5 Tomcat启动和停止

### 2.5.1 启动

> 进入tomcat安装目录bin下，双击startup.bat 启动程序，出现如下界面

<img src="pictrue/img05.png" style="zoom:50%;" />

### 2.5.2 验证

> 打开浏览器，输入 http://ocathost:8080
>
> 如果出现以下界面证明Tomcat启动成功。

<img src="pictrue/img06.png" style="zoom:50%;" />

### 2.5.3 停止

> 双击shutdown.bat即可关闭Tomcat启动窗口。

### 2.5.4 修改端口号

> Tomcat默认端口号为8080，可以通过conf/server.xml文件修改

```xml
<Connector port="8081" protocol="HTTP/1.1"
connectionTimeout="20000" redirectPort="8443" />
```

- 注意：修改端口号需要重新启动Tomcat才能生效

***

## 2.6 项目部署及访问静态资源

> Tomcat是web服务器，我们的项目应用是部署在webapps下，然后通过特定的 URL访问。

### 2.6.1 创建项目

> - ﻿在webapps中建立文件夹（项目应用），比如：myweb
>   - 创建WEB-INF文件夹，用于存放项目的核心内容
>     - 创建classes，用于存放.class文件﻿
>     - 创建lib，用于存放jar文件
>     - ﻿创建web.xml，项目配置文件（到ROOT项目下的WEB-INF复制即可）
>   - 把网页hello.html复制到myweb文件夹中，与WEB-INF在同级目录

![](pictrue/img07.png)

### 2.6.2 URL访问资源

> 浏览器地址中输入URL： http://locathost:8080/myweb/hello.html

### 2.6.3 Tomcat响应流程图

<img src="pictrue/img08.png" style="zoom:50%;" />

## 2. 7常见错误

### 2.7.1Tomcat控制台闪退

> 闪退问题是由于JAVA_ HOME配置导致的，检查JAVA_ HOME配置是否正确

### 2.7.2 404

> 访问资源不存在，出现404错误

![](pictrue/img09.png)

# 3 servlet【重点】

## 3.1概念

> - ﻿﻿Servlet：Server Applet的简称，是服务器端的程序（代码、功能实现），可交互式的处理客户端发送到服务端的请求，并完成操作响应。
> - ﻿Servlet也是动态网页技术
> - ﻿Javaweb程序开发的基础，JavaEE规范（一套接口）的一个组成部分。

### 3.1.1 servlet作用

> - ﻿接收客户端请求，完成操作。
> - ﻿动态生成网页（页面数据可变）。
> - ﻿将包含操作结果的动态网页响应给客户端。

## 3.2 Servlet开发步骤

### 3.2.1 搭建开发环境

> 将Servlet相关jar包 (lib\servlet-api.jar)配置到classpath中

### 3.2.2 编写Servlet

> - ﻿实现javax.servlet.Servlet
> - ﻿重写5个主要方法
> - ﻿在核心的service()方法中编写输出语句，打印访问结果

```java
package com.qf.servlet;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
public class MyServlet implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {}
  
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("My First servlet");
    }
    @Override
    public void destroy() {}
  
    @Override
    public ServletConfig getServletConfig() {
        return null;
    }
    @Override
    public String getServletInfo() {
        return null;
    }
}
```

### 3.2.3 部署Servlet

> 编译Myservlet后，将生成的.class文件放在WEB-INF/classes文件中。

### 3.2.4 配置Servlet

> 编写WEB-INF下项目配置文件web.xml

```xml
<?xmi version="1.0" encoding= "UTF-8"?> 
<web-app xmIns:si="http: / /www.w.org/2001/XMLSchema-instance" xmIns="http://xmIns.jcp.org/xmI/ns/javaee" si:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http: //xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd" version="3.1">
  <!--1、添加servlet节点-->
  <servlet>
    <!-- 别名 -->
    <servlet-name>MyServlet</servlet-name> 
    <servlet-class>com.gf.servlet.MyServlet</servlet-class>
  </servlet>
  <!--2、添加servlet-mapping节点-->
  <servlet-mapping>
      <!-- 对应上面别名 -->
      <servlet-name>MyServlet<servlet-name> 
      <url-pattern>/myservlet</url-pattern>
  </servlet-mapping>
</web-app>
```

- 注意：url-pattern配置的内容就是浏览器地址栏输入的URL中项目名称后资源的内容

## 3.3运行测试

> - 启动Tomcat， 在浏览器地址栏中输入 http:/locathost:8080/myweb/myservlet 访问，在Tomcat中打印时间表示成功。

<img src="pictrue/img10.png" style="zoom:50%;" />

# 4 IDEA创建Web项目

> 创建项目窗又, 选择JavaEE7,井勾选Web Application

<img src="pictrue/img11.png" style="zoom:50%;" />

> 输入项目名称和项目保存位置，点击Finish,完成项目创建

<img src="pictrue/img12.png" style="zoom:50%;" />

> web项目目录介绍

<img src="pictrue/img13.png" style="zoom:50%;" />

## 4.2 IDEA开发Servlet

> 使用开发工具编写Servlet， 仍要手工导入 servletapi.jar文件，并与项目关联。

### 4.2.1 编写Servlet

> 创建MyServlet，实现Servlet接口，覆盖5个方法

### 4.2.2 配置web.xml

```xml
<?xmi version="1.0" encoding= "UTF-8"?> 
<web-app xmIns:si="http: / /www.w.org/2001/XMLSchema-instance" xmIns="http://xmIns.jcp.org/xmI/ns/javaee" si:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http: //xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd" version="3.1">
  <!--1、添加servlet节点-->
  <servlet>
    <!-- 别名 -->
    <servlet-name>MyServlet</servlet-name> 
    <servlet-class>com.gf.servlet.MyServlet</servlet-class>
  </servlet>
  <!--2、添加servlet-mapping节点-->
  <servlet-mapping>
      <!-- 对应上面别名 -->
      <servlet-name>MyServlet<servlet-name> 
      <url-pattern>/myservlet</url-pattern>
  </servlet-mapping>
</web-app>
```

### 4.2.3 部署web项目

注意上面第一个项目的目录是myweb，这次全新的项目是WebProject，跟myweb同级别

![](pictrue/img14.png)

> - ﻿在Tomcat的webapps目录下，新建WebProject项目文件夹
>   - 创建WEB-INF，存放核心文件
>   - ﻿在WEB-INF下，创建classes文件夹，将编译后的MyServlet.class文件复制至此。

都放好后重启服务，这次请求：http:/locathost:8080/WebProject/myservlet，也在Tomcat控制台打印成功。

- ﻿**问题：每当我们编写了新的Servlet或者重新编译，都需要手工将新的.class部署到Tomcat中，较为麻烦。如何实现自动部署？**

***

## 4.3 IDEA部署web项目

> 前面我们是在Tomcats webapps目录新建应用程序目录myweb,然后把静态资源和servlet复制到相关目录下。使用IDEA不需要我们复制了。可以通过IDEA集成Tomcat服务器，实现自动部署。

### 4.3.1 IDEA集成Tomcat

> 点击File选项，选择Settings

<img src="pictrue/img15.png" style="zoom:50%;" />

> 选择Build, Execution, Deployment下的Application Serverso

<img src="pictrue/img16.png" style="zoom:50%;" />

> 点击+号，选择Tomcat Server

![](pictrue/img17.png)

> 选择Tomcat安装目录，点击OK即可

![](pictrue/img18.png)

> 最后，点击OK

![](pictrue/img19.png)

### 4.3.2 项目部君 Tomcat

> 点击 Add Configuration

![](pictrue/img20.png)

> 点击+号，选择Tomcat Server，选择Local

![](pictrue/img21.png)

> 点击+号，选择 Artifact， 添加当前项目

![](pictrue/img22.png)

![](pictrue/img23.png)

> 点击运行按钮，即可运行项目

![](pictrue/img24.png)

> 启动后，信息会打印在IDEA控制台里

![](pictrue/img25.png)

> 自动帮我们打开这个网页

![](pictrue/img26.png)

> 跟这里是对应的，但是我们就不改了

![](pictrue/img27.png)

> 访问：http:/locathost:6060/WebProject_war_exploded/myservlet

![](pictrue/img28.png)

> 至此，IDEA集成本地Tomcat完成

## 4.4 其他操作

### 4.4.1 关联第三方jar包

> 在WEB-INF 目录下新建lib目录，复制jar包到lib目录中

![](pictrue/img29.png)

> 右击lib目录，选择Add as Library.

![](pictrue/img30.png)

> 选择Project Library,完成。
>
> - ﻿Global Library 表示所有工程都可以使用。
> - ﻿Project Library 表示当 前工程中所有横块都可以使用。
> - ﻿module Library 花示当前模块可以使用。

![](pictrue/img31.png)

### 4.4.2 如何导出war包

上面在IDEA，写了代码可以直接点击重启就能看到效果，这是热部署，并没有在本地Tomcat目录中看到我们的项目的。如果我们在IDEA调试完成后，需要打包一个没问题的版本，然后放到本地Tomcat目录中，这样就能启动本地Tomcat进行访问了。

> 项目完成后，有时候需要打成war方便部署。war包可以直接放入Tomcat的webapps目录中，启动Tomcat后自动解压，即可访问。

> 点击项目结构

![](pictrue/img32.png)

>选择Artifacts，点击+号，就能看到有一个开发版

![](pictrue/img33.png)

> 现在需要将开发版变成发布版，点击+号

![](pictrue/img34.png)

> 到这里只是指明要对哪个项目生成war包，还没真正打出war包

![](pictrue/img35.png)

> 下面开始真正去打war包

![](pictrue/img36.png)

![](pictrue/img37.png)

> 到这里才打出了真正的war包

![](pictrue/img38.png)

> 把打出的war包放到Tomcat中（生产的服务器Tomcat中），然后停止开发的Tomcat

![](pictrue/img39.png)

> 下一步启动将来公司服务器部署的Tomcat，startup.bat，启动成功后，默认出现了WebProject_war这个文件夹

![](pictrue/img40.png)

> 访问：http:/locathost:6060/WebProject_war/myservlet；那么控制台就会出现：My First Web Project

> 注意：上面已经打包成发布版的war包以后，把war包放到了Tomcat以后，而项目如果在开发仍在改变，那么等开发完不再改变了，就再次打成war包后替换Tomcat的旧war包。

# 5 HTTP协议

## 5.1什么是HTTP

> 超文本传输协议 (HTTP, HyperText Transter Protocol)是互联网上应用最为广泛的一种网络协议,是一个基于请求与响应模式的、无状态的、应用层的协议，运行于TCP协议基础之上。

## 5.2 HTTP协议特点

> - ﻿支持客户端(浏览器）/服务器模式。
> - ﻿简单快速：客户端只向服务器发送请求方法和路径，服务器即可响应数据，因而通信速度很快。请求方法常用的有GET、POST
>    等。
> - ﻿灵活：HTTP允许传输任意类型的数据，传输的数据类型由Content-Type标识。
> - ﻿无连接：无连接指的是每次TCP连接只处理一个或多个请求，服务器处理完客户的清求后，即断开连接。采用这种方式可以节省传输时间。
>   - HTTP1.0版本是一个请求响应之后，直接就断开了。称为`短连接`
>   - HTTP1.1版本不是响应后直接就断开了，而是等几秒钟（时间可以设置），这几秒钟之内有新的请求，那么还是通过之前的连接通道来收发消息，如果过了这几秒钟用户没有发送新的请求，就会断开连接。称为`长连接`。
> - ﻿无状态：HTTP协议是无状态协议。
>   - ﻿无状态是指协议对于事务处理没有记忆能力

## 5.3 HTTP协议通信流程

> - ﻿客户与服务器建立连接（三次握手）。
> - ﻿客户向服务器发送请求。
> - ﻿服务器接受请求，并根据请求返回相应的文件作为应答。
> - ﻿客户与服务器关闭连接（四次挥手）

![](pictrue/img41.png)

## 5.4 请求报文和响应报文【了解】

### 5.4.1 HTTP请求报文

> 当浏览器向Web服务器发出请求时，它向服务器传递了一个数据块，也就是请求信息(请求报文），HTTP请求信息由4部分组成： 1、请求行(请求方法/地址 URI协议/版本)；
>
> 2、请求头(Request Header) ；
>
> 3、空行(用来分割请求头和请求正文，解析到空行了，说明请求头结束了，下面就是请求正文了)；
>
> 4、请求正文

![](pictrue/img42.png)

> Accept：说明我们（发送方）能接收的格式，后端就要按照这个格式来响应
>
> Accept-Language：能接收的语言
>
> User-Agent：浏览器的一些信息

### 5.4.2 HTTP响应报文

> HTTP响应报文与HTTP请求报文相似，HTTP响应也由4个部分组成：
>
> 1、状态行
>
> 2、响应头(Response Header) 
>
> 3、空行
>
> 4、响应正文

> 下面是一个HTTP响应的例子：

![](pictrue/img43.png)

> 响应头

![](pictrue/img44.png)

### 5.4. 3常见状态码

![](pictrue/img45.png)

# 6 Servlet详解【重点】

## 6.1 Servlet核心接口和类

> 在Servlet体系结构中，除了实现Servlet接口，还可以通过继承GenericServlet 或 HttpServlet类，完成编写。

### 6.1.1 Servlet接口

> 在Servlet API中最重要的是Servlet接口，所有Servlet都会直接或间接的与该接口发生联系，或是直接实现该接口，或间接继承自实现了
>  该接口的类。该接口包括以下五个方法：
>
> - ﻿﻿init(ServletConfig config)
> - ﻿﻿ServletConfig getServletConfig()
> - ﻿﻿service(ServletRequest req‚ServletResponse res)
> - ﻿﻿String getServletInfo()
> - ﻿﻿destroy()

### 6.1.2 GenericServlet抽象类

> GenericServlet 使编写 Servlet 变得更容易。它提供生命周期方法 init 和destroy 的简单实现，要编写一般的 Servlet，只需重写抽象service 方法即可。

```java
// 继承GenericServlet抽象类就可以不用实现这么多方法
public class GenServlet extends GenericServlet {
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
    }
}
```

> Servlet接口和GenericServlet抽象类处理的请求是全部的类型，即使不是HTTP协议过来的请求也能处理，属于通用的。
>
> 而下面HttpServlet类处理的请求都是和HTTP协议相关的请求

### 6.1.3 HttpServlet类

> HttpServlet是继承Genericservlet的基础上进一步的扩展。提供将要被子类化以创建适用于 Web 站点的 HTTP servlet 的抽象类。HttpServlet 的子类至少必须重写一个方法，该方法通常是以下这些方法之一：
>
> - doGet，如果 servlet 支持 HTTP GET 请求 
> - doPost， 用于 HTTP POST 请求 
> - doPut，用于 HTTP PUT 请求 
> - doDelete， 用于 HTTP DELETE 请求

## 6.2 Servlet 两种创建方式

### 6.2.1 实现接口Servlet

```java
public class MyServlet implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
    }
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("hello world");
    }
    @Override
    public void destroy() {
    }
    @Override
    public ServletConfig getServletConfig() {
        return null;
    }
    @Override
    public String getServletInfo() {
        return null;
    }
}
```

- 该方式比较麻烦，需要实现接口中所有方法。

### 6.2.2 继承HttpServlet(推荐)

这是与HTTP协议有关的类

```java
public class HttpsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("get request");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("post request");
    }
}
```

## 6.3 Servlet两种配置方式

### 6.3.1 使用web.xml(Servlet2.5之前使用）

```xml
<?xmi version="1.0" encoding= "UTF-8"?> 
<web-app xmIns:si="http: / /www.w.org/2001/XMLSchema-instance" xmIns="http://xmIns.jcp.org/xmI/ns/javaee" si:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http: //xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd" version="3.1">
  <!--1、添加servlet节点-->
  <servlet>
    <!-- 别名 -->
    <servlet-name>MyServlet</servlet-name> 
    <servlet-class>com.gf.servlet.MyServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <!--2、添加servlet-mapping节点-->
  <servlet-mapping>
      <!-- 对应上面别名 -->
      <servlet-name>MyServlet<servlet-name> 
      <url-pattern>/myservlet</url-pattern>
  </servlet-mapping>
</web-app>
```

### 6.3.2配置属性

```java
ur1-pattern定义匹配规则，取值说明：
精确匹配    /具体的名称   只有ur1路径是具体的名称的时候才会触发Servlet
后缀匹配    *.xxx       只要是以xxx结尾的就匹配触发Servlet
通配符匹配   '/*'       匹配所有请求，包含服务器的所有资源(不会影响精确匹配)
通配符匹配    /         匹配所有请求，包含服务器的所有资源，不包括. jsp(不会影响精确匹配)
  
load-on-startup
1元素标记容器是否应该在web应用程序启动的时候就加载这个servlet。
2它的值必须是一个整数，表示servlet被加载的先后顺序。
3如果该元素的值为负数或者没有设置，则容器会当Servlet被请求时再加载。
4如果值为正整数或者0时，表示容器在应用启动时就加载并初始化这个servlet，值越小，servlet的优先级越高，就越先被加载。值相同时，容器就会自己选择顺序来加载。
```

### 6.3.3使用注解(Servlet3.0后支持，推荐）

```java
@WebServlet("/hello")
public class HttpsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("get request");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("post request");
    }
}
```

### 6.3.4 @WebServlet注解常用属性

> - ﻿name: serlvet名字（可选)
> - ﻿value:配置url路径,可以配置多个
> -  urlPatterns：配置ur路径，和value作用一样，不能同时使用
> -  loadOnStartup:配置servlet的创建的时机，如果是0或者正数 启动程序时创建，如果是负数，则访问时创建。数子越小优先级越高。

# 7 servlet应用【 重点】

## 7.1 request对象

> 在Servlet中用来处理客户端请求需要用doGet或doPost方法的request对象

![](pictrue/img46.png)

### 7.1.1 get和post区别

> get请求
>
> - ﻿get提交的数据会放在URL之后，以？分割URL和传输数据，参数之间以&相连
> - ﻿get方式明文传递，数据量小，不安全
> - ﻿效率高，浏览器默认请求方式为GET请求
> - ﻿对应的Servlet的方法是doGet
>
> post请求
>
> - ﻿post方法是把提交的数据放在HTTP包的Body中
> - ﻿密文传递数据，数据量大，安全
> - ﻿效率相对没有GET高
> - ﻿对应的Servlet的方法是doPost

### 7.1.2 request主要方法

| 方法名                                    | 说明                         |
| ----------------------------------------- | ---------------------------- |
| String getParameter (String name)         | 根据表单组件名称获取提交数据 |
| void setCharacterEncoding(String charset) | 指定每个请求的编码           |

### 7.1.3 request应用

> HTML页面

```xml
< !DOCTYPE html> 
<html>
<head> 
<meta charset="UTF-8">
<title>欢迎页面</title>
</head> 
<bodv>
<h1>欢迎你</h1>
<div>
<form action="项目名/hello" method="post">
<label>姓名：</1abel><input type="text" name="name"><br/>
<label>年龄：</label><input type="text" name="age"><br/>
<input type="submit" value="提交">
</form>
</div>
</bodv>
</html>
```

> Servlet代码

```java
@WebServlet("/hello")
public class HttpsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String age = req.getParameter("age");
        System.out.println("name="+name+",age="+age);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
```

### 7.1.4 get请求收参问题

> 产生乱码是因为服务器和客户端沟通的编码不一致造成的，因此解决的办法是：在客户端和服务器之问设置一个统一的编码，之后就按照此编码进行数据的传输和接收

### 7.1.5 get中文乱码

> 在Tomcat7及以下版本，客户端以UTF-8的编码传输数据到服务器端，而服务器端的request对象使用的是ISO8859-1这个字符编码来接收数据，服务器和客户端沟通的编码不一致因此才会产生中文乱码的。
>
> - ﻿解決办法：在接收到数据后，先获取request对象以I$O8859-1字符编码接收到的原始数据的字节数组，然后通过字节数组以指定的编码构建字符串，解决乱码问题。
> - ﻿Tomcat8的版本中get方式不会出现乱码了，因为服务器对ur的编码格式可以进行自动转换

![](pictrue/img47.png)

### 7.1.6 post中文乱码

> 由于客户端是以UTF-8字符编码将表单数据传输到服务器端的，因此服务器也需要设置以UTF-8字符编码进行接收。
>
> - 解决方案：使用从ServletRequest接口继承而来的setCharacterEncoding(charset)方法进行统一的编码设置

![](pictrue/img48.png)

## 7.2 response对象

> response对象用于响应客户请求并向客户端输出信息。

![](pictrue/img49.png)

### 7.2.1 response主要方法

| 方法名称                     | 作用                               |
| ---------------------------- | ---------------------------------- |
| setHeader(name,value)        | 设置响应信息头                     |
| setContentType(String)       | 设置响应文件类型、响应式的编码格式 |
| setCharacterEncoding(String) | 设置服务端响应内容编码格式         |
| getWriter()                  | 获取字符输出流                     |

> 案例：使用response对象向浏览器输出HTML内容，实现用户登录后，输出“Login Success”

```java
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // 1.获取用户请求数据
    String name = req.getParameter("name");
    String age = req.getParameter("age");
    // 2.响应数据给客户端
    PrintWriter writer = resp.getWriter();
    writer.println("register success");
}
```

![](pictrue/img50.png)

> 如果输出内容包含中文，则出现乱码，因为服务器默认采用IS08859-1编码响应内容

### 7.2.3 解决输出中文乱码

> - ﻿设置服务器端响应的编码格式
> - ﻿设置客户端响应内容的头内容的文件类型及编码格式

```java
response.setCharacterEncoding("utf-8");//设置响应编码格式为utf-8
response.setHeader("Content-type","text/html;charset=UTF-8”);
```

- **不推荐**

> 同时设置服务端的编码格式和客户端响应的文件类型及响应时的编码格式

```java
response.setContentType("text/html;charset=UTF-8");
```

- **推荐**

## 7.3综合案例(Servlet +JDBC)

略。关键截图：

![](pictrue/img51.png)

# 8 转发与重定向

## 8.1 现有问题

> 在7.3案例中，调用业务逻辑和显示结果页面都在同一个Servlet里，就会产生设计问题
>
> - ﻿不符合单一职能原则、各司其职的思想
> - ﻿不利于后续的维护
>
> 应应该将业务逻辑和显示结果分离开

![](pictrue/img52.png)

### 8.1.1业务、显示分离

![](pictrue/img53.png)

![](pictrue/img54.png)

![](pictrue/img55.png)

- ﻿问题：业务逻辑和显示结果分离后，如何跳转到显示结果的Servlet？
- ﻿业务逻辑得到的数据结果如何传递给显示结果的Servlet？

## 8.2 转发

> 转发的作用在服务器端，将请求发送给服务器上的其他资源，以共同完成一次请求的处理。

### 8.2.1 页面跳转

> 在调用业务逻辑的Servlet中，编写以下代码
>
> - request.getRequestDispatcher(/目标URL-pattern").forward(request, response);

![](pictrue/img56.png)

- 使用forward跳转时，是在服务器内部跳转，地址栏不发生变化，属于同一次请求

### 8.2.2 数据传递

> forward表示一次请求，是在服务器内部跳转，可以共享同一次request作用域中的数据
>
> - ﻿request作用域：拥有存储数据的空间，作用范围是一次请求有效(一次请求可以经过多次转发）
>   - ﻿可以将数据存入request后，在一次请求过程中的任何位置进行获取
>   - ﻿可传递任何数据(基本数据类型、对象、数组、集合等）
> - ﻿存数据：request.setAttribute(key,value);
>   - ﻿以键值对形式存储在request作用域中。key为string类型，value为objec类型
> - ﻿取数据：request.getAttribute(key);
>   - ﻿通过String类型的key访问object类型的value

![](pictrue/img57.png)

![](pictrue/img58.png)

### 8.2.3 转发特点

> - ﻿转发是服务器行为
> - ﻿转发是浏览器只做了一次访问请求
> - ﻿转发浏览器地址不变
> - ﻿转发两次跳转之间传输的信息不会丢失，所以可以通过request进行数据的传递、
> - ﻿转发只能将请求转发给同一个Web应用中的组件

## 8.3 重定向

> 重定向作用在客户端，客户端将请求发送给服务器后，服务器响应给客户端一个新的请求地址，客户端重新发送新请求。

### 8.3.1 页面跳转

> 在调用业务逻辑的Servlet中，编写以下代码
>
> - ﻿response.sendRedirect(“目标URI")；

- ﻿URI:统一资源标识符(Uniform Resource ldentifier)，用来表示服务器中定位一个资源，资源在web项目中的路径(/project/source)

![](pictrue/img59.png)

- 使用redirect跳转时，是在客户端跳转，地址栏发生变化，属于多次请求

### 8.3.2 数据传递

> sendRedirect跳转时，地址栏改变，代表客户端重新发送的请求。属于两次请求
>
> - ﻿response没有作用域，两次request请求中的数据无法共享
> - ﻿传递数据：通过URI的拼接进行数据传递("/WebProject/b?username=tom”);
> - ﻿获取数据：request. getParameter("username");

### 8.3.3 重定向特点

> - 重定向是客户端行为。
>
> - ﻿重定向是浏览器做了至少两次的访问请求。
> - ﻿重定向浏览器地址改变。
> - ﻿重定向两次跳转之间传输的信息会丟失 (request范围）。
> - ﻿重定向可以指向任何的资源，包括当前应用程序中的其他资源、同一个站点上的其他应用程序中的资源、其他站点的资源。

## 8.4 转发、重定向总结

> 当两个Servlet需要传递数据时，选择forward转发。不建议使用sendRedirect进行传递

# 9 Servlet生命周期

## 9.1 生命周期四个阶段

### 9.1.1 实例化

> 当用户第一次访问Servlet时，由容器调用Servlet的构造器创建具体的Servlet对象。也可以在容器启动之后立刻创建实例。使用如下代码可以设置servlet是否在服务器启动时就创建。
>
> <load-on-startup>1</load-on-startup>
>
> - 注意：只执行一次

### 9.1.2 初始化

> 在初始化阶段，init()方法会被调用。这个方法在javax.servlet.Servlet接口中定义。其中，方法以一个ServletConfig类型的对象作为参数。
>
> - 注意：init()只被执行一次

### 9.1.3 服务

> 当客户端有一个请求时，容器就会将请求ServletRequest与响应 ServletResponse对象转给Servlet，以参数的形式传给service方法。
>
> - 此方法会执行多次

### 9.1.4 销毁

> 当Servlet容器停止或者重新启动都会引起销毁Servlet对象并调用destroy方法。
>
> - destroy方法执行一次

### 9.1.5 Servlet执行流程

![](pictrue/img60.png)

# 10 Servlet 特性

## 10.1 线程安全问题

> Servlet在访问之后，会执行实例化操作，创建一个Servlet对象。而我们Tomcat容器可以同时多个线程并发访问同一个Servlet，如果在方法中对成员变量做修改操作，就会有线程安全的问题。

## 10.2 如何保证线程安全

> - ﻿﻿synchronized
>   - ﻿将存在线程安全问题的代码放到同步代码块中
> - ﻿实现SingleThreadModel接口
>   - ﻿servlet实现SingleThreadModel接口后，每个线程都会创建servlet实例，这样每个客户端请求就不存在共享资源的问题，但是servlet响应客户端请求的效率太低，所以已经淘汰。
> - ﻿尽可能使用局部变量

# 11 状态管理

## 11.1 现有问题

> - ﻿HTTP协议是无状态的，不能保存每次提交的信息
> - ﻿如果用户发来一个新的请求，服务器无法知道它是否与上次的请求有联系。
> - ﻿对于那些需要多次提交数据才能完成的Web操作，比如登录来说，就成问题了。

## 11.2 概念

> 将浏览器与web服务器之间多次交互当作一个整体来处理，并且将多次交互所涉及的数据（即状态）保存下来。

## 11.3 状态管理分类

> - ﻿客户端状态管理技术：将状态保存在客户端。代表性的是Cookie技术。
> - ﻿服务器状态管理技术：将状态保存在服务器端。代表性的是session技术（服务器传递sessionlD时需要使用cookie的方式）

# 12 cookie的使用

## 12.1 什么是cookie

> - ﻿cookie是在浏览器访问Web服务器的某个资源时，由Web服务器在HTTP响应消息头中附带传送给浏览器的一小段数据。
> - ﻿一旦Web浏览器保存了 某个cookie，那么它在以后每次访问该Web服务器时，都应在HTTP请求头中将这个Cookie回传给Web服务器。
> - ﻿一个cookie主要由标识该信息的名称 (name） 和值(value）组成

![](pictrue/img61.png)

## 12.2 创建Cookie

```java
//创建Cookie
Cookie ck = new Cookie ("code", code) ;
ck.setPath("/WebProject_war_exploded/get");//设置Servlet路径，设置的路径将来Cookie才能访问
ck.setMaxAge(60*60)：//内存存储，取值有三种：>0有效期，单位秒;=0浏览器关闭;<0内存存储，默认-1
response.addcookie(ck);//添加到response对象中，响应时发送给客户端
//注意：有效路径：当前访问资源的上一级目录，不带主机名
```

![](pictrue/img62.png)

## 12.3 获取Cookie

![](pictrue/img63.png)

## 12.4 修改Cookie

> 只需要保证Cookie的名和路径一致即可修改

![](pictrue/img64.png)

## 12.5 Cookie编码与解码

> cookie默认不支持中文，只能包含ASCII字符，所以Cookie需要对Unicode字符进行编码，否则会出现乱码。
>
> - ﻿编码可以使用java.net.URLEncoder类的encode (String str,String encoding)方法
> - ﻿解码使用java.net.URLDecoder类的decode(String str, String encoding)方法

### 12.5.1 创建带中文Cookie

![](pictrue/img65.png)

### 12.5.2 读取带中文Cookie

![](pictrue/img66.png)

## 12.6 Cookie优点和缺点

### 12.6.1 优点

> - ﻿可配置到期规则。
> - ﻿简单性：cookie 是一种基于文本的轻量结构，包含简单的键值对。
> - ﻿数据持久性：cookie默认在过期之前是可以一直存在客户端浏览器上的。

### 12.6.2 缺点

> - ﻿大小受到限制：大多数浏览器对 cookie 的大小有 4K、8K字节的限制。
> - ﻿用户配置为禁用：有些用户禁用了浏览器或客户端设备接收 Cookie 的能力，因此限制了这一功能。、
> - ﻿潜在的安全风险：Cookie 可能会被篡改。会对安全性造成潜在风险或者导致依赖于cookie 的应用程序失败。

# 13 Session对象【重点】

## 13.1 Session概述

> - ﻿Session用于记录用户的状态。Session指的是在一段时间内，单个客户端与Web服务器的一连串相关的交互过程。
> - ﻿在一个Session中，客户可能会多次请求访问同一个资源，也有可能请求访问各种不同的服务器资源。

## 13.2 Session原理

> - ﻿服务器会为每一次会话分配一个Session对象
> - ﻿同一个浏览器发起的多次请求，同属于一次会话(Session)
> - ﻿首次使用到Session时，服务器会自动创建Session，并创建Cookie存储Sessionld发送回客户端

- ﻿注意：session是由服务端创建的。

## 13.3 Session使用

> - ﻿Session作用域：拥有存储数据的空间，作用范围是一次会话有效
>   - 一次会话是使用同一浏览器发送的多次请求。一旦浏览器关闭，则结束会话
>   - ﻿可以将数据存入Session中，在一次会话的任意位置进行获取
>   - ﻿可传递任何数据(基本数据类型、对象、集合、数组）

### 13.3.1 获取Session

> session是服务器端自动创建的，通过request对象获取

```java
//获取Session对象
HttpSession session = request.getSession():
System.out.printin( "Id: "+ session.getId(）);//唯一标记，
```

![](pictrue/img67.png)

> 服务端如何把sessionID给到客户端呢？
>
> sessionID会自动借助Cookie来发送给客户端

> 再次请求，response headers 不会有服务器端响应给客户端sessionId，因为第一次请求已经给了，后面还是当前浏览器继续请求，客户端会在request headers中携带SessionId给服务端

![](pictrue/img68.png)

### 13.3.2 Session保存数据

> setAttribute(属性名,Object)保存数据到session中

```java
session.setAttribute(“key”, value);//以键值对形式存储在session作用域中
```

### 13.3.3 Session获取数据

```java
session.getAttribute("key");//通过String类型的key访问object类型的value
```

### 13.3.4 Session移除数据

```java
session.removeAttribute ("key")：//通过键移除session作用域中的值
```

## 13.4 Session与Request应用区别

> request是一次请求有效，请求改变，则request改变
>
> session是一次会话有效，浏览器改变，贝session改变

### 13.4.1 Session应用

> 使用重定向的话，request就不再使用的同一个，只有路由转发，这个时候request才是同一个。
>
> 那么我们验证重定向，sesssion域是否还能使用同一个，答案是可以的。

![](pictrue/img69.png)

![](pictrue/img70.png)

![](pictrue/img71.png)

## 13.5 Session的生命周期

> - ﻿开始：第一次使用到Session的请求产生，则创建Session
> - ﻿结束：
>   - ﻿浏览器关闭，则失效（自动的方式）
>   - ﻿Session超时，则失效
>     - ﻿﻿session.setMaxlnactivelnterval(seconds)：//设置最大有效时间（单位：秒）
>   - 手工销毀，则失效
>     - ﻿session.invalidate();  // 登录退出、注销

### 13.5.1 Session失效

```java
session.setMaxInactiveInterval(60*60);//设置session最大有效期为一小时
ssession.invalidate();//手工销毀
```

## 13.6  浏览器禁用Cookie解决方案【了解】

### 13.6.1浏览器禁用Cookie的后果

> 服务器在默认情况下，会使用Cookie的方式将sessionID发送给浏览器，如果用户禁止Cookie，则sessionID不会被浏览器保存，此时，服务器可以使用如URL重写这样的方式来发送sessionlD

### 13.6.2 URL重写

> 浏览器在访问服务器上的某个地址时，不再使用原来的那个地址，而是使用经过改写的地址(即在原来的地址后面加上了sessionID) 

### 13.6.3 实现URL重写

> response.encodeRedirectURL(String url) 生成重写的URL。

```java
HttpSession session = request.getSession();
//重写URL追加SessionId
String newUrl = response.encodeRedirectURL ("/WebProject_war_exploded/cs");
System.out.println(newUrl);
response.sendRedirect(newUr12);
```

# 14 Servletcontext对象【重点】

## 14.1 ServletContext概述

> - ﻿全局对象，也拥有作用域，对应一个Tomcat中的Web应用
> - ﻿当web服务器启动时，会为每—个Web应用程序创建一块共享的存储区域 (ServletContext)
> - ﻿Servletcontext在Web服务器启动时创建，服务器关闭时销毀。

## 14.2 获取ServletContext对象

> - ﻿GenericServlet提供了getServletContext()方法。（推荐） this.getServletContext();
> - ﻿HttpServletRequest提供了 getServletContext()方法。（推荐）
> - ﻿HttpSession提供了getServletContext()方法
>
> 上面全部方式获取到的ServletContext对象是同一个

## 14.3 ServletContext作用

### 14.3.1 获取项目真实路径

> 获取当前项目在服务器发布的真实路径

```java
String realpath=servletContext.getRealPath("/");
```

### 14.3.2 获取项目上下文路径

> 获取当前项目上下文路径（应用程序名称）

```java
servletContext.getcontextPath();//上下文路径(应用程序名称）
request.getContextPath();
```

### 14.3.3 全局容器

> ServletContext拥有作用域，可以存储数据到全局容器中
>
> - ﻿存储数据：servletContext.setAttribute("name", value);
> - ﻿获取数据：servletContext.getAttribute ("name");
> - ﻿移除数据：servletContext.removeAttribute("name");

## 14.4 ServletContext特点

> - ﻿唯一性：一个应用对应一个ServletContext。
> - ﻿生命周期：只要容器不关闭或者应用不卸载，ServletContext就一直存在

## 14.5 ServletContext应用场景

> ServletContex统计当前项目访问次数

![](pictrue/img72.png)

## 14.6 作用域总结

> - ﻿HttpServletRequest：一次请求，请求响应之前有效
> - ﻿﻿Httpsession：一次会话开始，浏览器不关闭或不超时之前有效
> - ﻿ServletContext：服务器启动开始，服务器停止之前有效

# 15 过滤器【重点】

## 15.1 现有问题

> 在以往的servlet中，有没有冗余的代码，多个servlet都要进行编写

## 15.2 概念

> 过滤器 （Filter）是处于客户端与服务器目标资源之间的一道过滤技术。

![](pictrue/img73.png)

## 15.3过滤器作用

> - ﻿执行地位在Servlet之前，客户端发送请求时，会先经过Filter，再到达目标Servlet中；响应时，会根据执行流程再次反向执行Filter
> - ﻿可以解决多个Servlet共性代码的冗余问题（例如：乱码处理、登录验证）

## 15.4 编写过滤器

> servletAPI中提供了一个Filter接口，开发人员编写一个Java类实现了这个接口即可，这个Java类称之为过滤器 (Fiter)

### 15.4.1 实现过程

> - ﻿编写Java类实现Filter接口
> - ﻿在doFilter方法中编写拦截逻辑
> - ﻿设置拦截路径

![](pictrue/img74.png)

## 15.5 过滤器配置

### 15.5.1 注解配置(推荐)

> 在自定义的Filter类上使用注解@WebFilter(value=“/过滤目标资源”)

### 15.5.2 xml配置(了解)

```xml
<!-- 过滤器的xml配置 -->
<filter>
  <filter-name>sf</filter-name>
  <!-- 过滤器类全称 -->
  <filter-class>com.qf.web.filter. SecondFilter</filter-class>
</filter>
<!-- 映射路径配置 -->
<filter-mapping>
  <filter-name>sf</filter-name>
  <!-- 过滤的ur1匹配规则和Servlet类似 -->
  <url-pattern>/*</url-pattern> 
</filter-mapping>
```

### 15.5.3 过滤器路径

> 过滤器的过滤路径通常有三种形式：
>
> 精确过滤匹配，比如/index.jsp 、/myservlet1
>
> 后缀过滤匹配，比如 * .jsp、* .html、*.jpg
>
> 通配符过滤匹配 /*，表示拦截所有。注意过滤器不能使用/匹配。
>
> /aaa/bbb/* 允许

## 15.6 过滤器链和优先级

### 15.6.1 过滤器链

> 客户端对服务器请求之后，服务器调用Servlet之前会执行一组过滤器（多个过滤器），那么这组过滤器就称为一条过滤器链。
>
> 每个过滤器实现某个特定的功能，当第一个Filter的doFilter方法被调用时，Web服务器会创建一个代表Filter链的FilterChain对象传递给该方法。在doFitter方法中，开发人员如果调用了 FilterChain对象的doFilter方法，则Web服务器会检查FilterChain对象中是否还有filter,如果有，则调用第2个filter，如果没有，则调用目标资源。

![](pictrue/img75.png)

### 15.6.2 过滤器优先级

> 在一个Web应用中，可以开发编写多个Filter，这些Filter组合起来称之为一个Filter链。优先级：
>
> - ﻿如果为注解的话，是按照类全名称的字符串顺序决定作用顺序
> - ﻿如果web.xml，按照 filter-mapping注册顺序，从上往下
> - ﻿web.xml配置高于注解方式
> - ﻿如果注解和web.xml同时配置，会创建多个过滤器对象，造成过滤多次。

## 15.7 过滤器典型应用

### 15.7.1 过滤器解决编码

![](pictrue/img76.png)































