## 1.Servlet生命周期

1.网站中所有的Servlet接口实现类的实例对象，只能由Http服务器负责创建。

开发人员不能手动创建Servlet接口实现类的实例对象



2.在默认的情况下，用户第一次请求的时候，自动创建Servlet接口实现类对象。

(调用无参构造方法)



在手动配置情况下，要求Http服务器在启动时自动创建某个Servlet接口实现类

的实例对象【 <!--填写一个大于0的整数即可-->】

- 基于 XML ⽂件的配置⽅式。 

<servlet> 

<servlet-name>hello</servlet-name> <!--声明一个变量存储servlet接口实现类类路径-->

<servlet-class>com.bjpowernode.controller.OneServlet</servlet-class> <!--声明servlet接口实现类类路径-->

 <load-on-startup>30<load-on-startup><!--填写一个大于0的整数即可-->

</servlet> 



<!--为了降低用户访问Servlet接口实现类难度，需要设置简短请求别名-->

<servlet-mapping> 

<servlet-name>hello</servlet-name> 

<url-pattern>/newhello</url-pattern> <!--设置简短请求别名,别名在书写时必须以"/"为开头-->

</servlet-mapping>



- 基于注解的⽅式。 

@WebServlet("/newhello") 

public class HelloServlet implements Servlet { 

}



3.关于Servlet类中方法的调用次数？

- 构造方法只执行一次。
- init方法只执行一次
- service方法：用户发送一次请求则执行一次，发送N次请求则执行N次。
- destroy方法只执行一次



说明:

- 用户在发送第一次请求的时候Servlet对象被实例化（AServlet的构造方法被执行了。并且执行的是无参数构造方法。）
- AServlet对象被创建出来之后，Tomcat服务器马上调用了AServlet对象的init方法。
- 用户发送第一次请求的时候，init方法执行之后，Tomcat服务器马上调用AServlet对象的service方法。
- 用户在发送第二次，或者第三次，或者第四次请求的时候，Servlet对象并没有新建，还是使用之前创建好的Servlet对象，直接调用该Servlet对象的service方法.



### 注解详细解释

我们的第一个注解：

```java
jakarta.servlet.annotation.WebServlet
```

在Servlet类上使用：@WebServlet，WebServlet注解中有哪些属性呢？

- name属性：用来指定Servlet的名字。等同于：<servlet-name>
- urlPatterns属性：用来指定Servlet的映射路径。可以指定多个字符串。<url-pattern>
- loadOnStartUp属性：用来指定在服务器启动阶段是否加载该Servlet。等同于：<load-on-startup>

```java
//@WebServlet(urlPatterns = {"/welcome1", "/welcome2"})
// 注意：当注解的属性是一个数组，并且数组中只有一个元素，大括号可以省略。
//@WebServlet(urlPatterns = "/welcome")
// 这个value属性和urlPatterns属性一致，都是用来指定Servlet的映射路径的。
//@WebServlet(value = {"/welcome1", "/welcome2"})
// 如果注解的属性名是value的话，属性名也是可以省略的。
//@WebServlet(value = "/welcome1")
//@WebServlet({"/wel", "/abc", "/def"})
@WebServlet("/wel")
public class WelcomeServlet extends HttpServlet {
}
```



## 2.GenericServlet

1. MyServlet直接实现接口，会将该接口中所有方法实现

```java
public class MyServlet implements Servlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
```

2.我们进行改进

我们让抽象类GenericServlet把不需要的方法实现即可，让service变成抽象类。

```java
public abstract class GenericServlet  implements Servlet {
    //ServletConfig servletConfig形参，我们不知道传进来的是什么
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public abstract void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException;

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
public class MyServlet  extends GenericServlet{
    
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

    }
}
```

思考第一个问题MyServlet还能使用GenericServlet类中的init方法吗?

可以。会执行GenericServlet类中的init方法。

思考第二个问题：init方法是谁调用的？

Tomcat服务器调用的。

思考第三个问题：init方法中的ServletConfig对象是谁创建的？是谁传过来的？

 	都是Tomcat干的。

   	 Tomcat服务器先创建了ServletConfig对象，然后调用init方法，将ServletConfig对象传给了init方法。



思考一下Tomcat服务器伪代码：

```java
 public class Tomcat {
          public static void main(String[] args){
              // .....
              // Tomcat服务器伪代码
              // 创建LoginServlet对象（通过反射机制，调用无参数构造方法来实例化MyServlet对象）
              Class clazz = Class.forName("com.bjpowernode.javaweb.servlet.MyservletServlet");
              Object obj = clazz.newInstance();
              
              // 向下转型
              Servlet servlet = (Servlet)obj;
              
              // 创建ServletConfig对象
              // Tomcat服务器负责将ServletConfig对象实例化出来。
              // 多态（Tomcat服务器完全实现了Servlet规范）
              ServletConfig servletConfig = new org.apache.catalina.core.StandardWrapperFacade();
              
              // 调用Servlet的init方法
              //将这个实参传入到那个方法里。
              servlet.init(servletConfig);
              
              // 调用Servlet的service方法
              // ....
              
          }
      }
package javax.servlet;

import java.io.IOException;
import java.util.Enumeration;

public abstract class GenericServlet implements Servlet, ServletConfig,
        java.io.Serializable {
    
    //版本号
    private static final long serialVersionUID = 1L;
	
    private transient ServletConfig config;

    /*
        此构造方法啥也不做，因为Servlet的所有初始化任务都是由init方法完成的。
    */
    public GenericServlet() {
        // NOOP
    }


    @Override
    public void destroy() {
        // NOOP by default
    }

  
    @Override
    public String getInitParameter(String name) {
        return getServletConfig().getInitParameter(name);
    }

  
    @Override
    public Enumeration<String> getInitParameterNames() {
        return getServletConfig().getInitParameterNames();
    }

    
    @Override
    public ServletConfig getServletConfig() {
        /*通过该方法，可以让子类调用父类私有属性*/
        return config;
    }

 
    @Override
    public ServletContext getServletContext() {
        return getServletConfig().getServletContext();
    }

 
    @Override
    public String getServletInfo() {
        return "";
    }


    @Override
    public void init(ServletConfig config) throws ServletException {
        /*该方法是形参，tomcat将实参传进来，然后通过this.config=config
          把局部变量变为全局变量。*/
        this.config = config;
      //Tomcat首先调用init()有参方法，然后调用init()无参方法。
        this.init();
    }

   /*目的是让子类重写init()方法的无参构造方法。*/
    public void init() throws ServletException {
        // NOOP by default
    }

 
    public void log(String msg) {
        getServletContext().log(getServletName() + ": " + msg);
    }


    public void log(String message, Throwable t) {
        getServletContext().log(getServletName() + ": " + message, t);
    }


    @Override
    public abstract void service(ServletRequest req, ServletResponse res)
            throws ServletException, IOException;

 
    @Override
    public String getServletName() {
        return config.getServletName();
    }
}
package GenericServlet;

import javax.servlet.*;
import java.io.IOException;

public class MyServlet  extends GenericServlet{
    //我们需要在MyServlet中重写init方法
    //子类重写第二个(无参的)就可
    public void  init(){
        System.out.println("MyServlet init() method execute!");
    }



    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        //我想在MyServlet子类中使用Servlet对象怎么办?
        ServletConfig config=this.getServletConfig();
    }
}
```

### 

### ServletConfig

- 什么是ServletConfig？ 

- - ServletConfig是一个接口。是Servlet规范中的一员。
  - ServletConfig对象中封装了标签中的配置信息。（web.xml文件中servlet的配置信息）

-  一个Servlet对应一个ServletConfig对象。 
-  Servlet对象是Tomcat服务器创建，并且ServletConfig对象也是Tomcat服务器创建。并且默认
- 情况下，他们都是在用户发送第一次请求的时候创建。 
-  Tomcat服务器调用Servlet对象的init方法的时候需要传一个ServletConfig对象的参数给init方法。 
-  ServletConfig接口的实现类是Tomcat服务器给实现的。（Tomcat服务器说的就是WEB服务器。） 
-  ServletConfig接口有哪些常用的方法？ 

```java
public String getInitParameter(String name); // 通过初始化参数的name获取value
public Enumeration<String> getInitParameterNames(); // 获取所有的初始化参数的name
public ServletContext getServletContext(); // 获取ServletContext对象
public String getServletName(); // 获取Servlet的name
```

以上方法在Servlet类当中，都可以使用this去调用。因为GenericServlet实现了ServletConfig接口。 



### ServletContext(应用域)

-  一个ServletContext对象通常对应的是一个web.xml文件。 
- 只要在同一个webapp当中，只要在同一个应用当中，所有的Servlet对象都是共享同一个ServletContext对象的。
- ServletContext对象在服务器启动阶段创建，在服务器关闭的时候销毁。这就是ServletContext对象的生命周期。ServletContext对象是应用级对象。
-  ServletContext是一个接口，Tomcat服务器对ServletContext接口进行了实现。 

- - ServletContext对象的创建也是Tomcat服务器来完成的。启动webapp的时候创建的。

ServletContext接口中有哪些常用的方法？ 

```java
public String getInitParameter(String name); // 通过初始化参数的name获取value
public Enumeration<String> getInitParameterNames(); // 获取所有的初始化参数的name
<!--以上两个方法是ServletContext对象的方法，这个方法获取的是什么信息？是以下的配置信息-->
<context-param>
    <param-name>pageSize</param-name>
    <param-value>10</param-value>
</context-param>
<context-param>
    <param-name>startIndex</param-name>
    <param-value>0</param-value>
</context-param>
<!--注意：以上的配置信息属于应用级的配置信息，一般一个项目中共享的配置信息会放到以上的标签当中。-->
<!--如果你的配置信息只是想给某一个servlet作为参考，那么你配置到servlet标签当中即可，使用ServletConfig对象来获取。-->
// 获取应用的根路径（非常重要），因为在java源代码当中有一些地方可能会需要应用的根路径，这个方法可以动态获取应用的根路径
// 在java源码当中，不要将应用的根路径写死，因为你永远都不知道这个应用在最终部署的时候，起一个什么名字。
public String getContextPath();
//String contextPath = application.getContextPath();
// 获取文件的绝对路径（真实路径）
public String getRealPath(String path);
// 通过ServletContext对象也是可以记录日志的
public void log(String message);
public void log(String message, Throwable t);
// 这些日志信息记录到哪里了？
// localhost.2021-11-05.log

// Tomcat服务器的logs目录下都有哪些日志文件？
//catalina.2021-11-05.log 服务器端的java程序运行的控制台信息。
//localhost.2021-11-05.log ServletContext对象的log方法记录的日志信息存储到这个文件中。
//localhost_access_log.2021-11-05.txt 访问日志
// ServletContext对象还有另一个名字：应用域（后面还有其他域，例如：请求域、会话域）

// 如果所有的用户共享一份数据，并且这个数据很少的被修改，并且这个数据量很少，
// 可以将这些数据放到ServletContext这个应用域中

// 存（怎么向ServletContext应用域中存数据）
public void setAttribute(String name, Object value); // map.put(k, v)
// 取（怎么从ServletContext应用域中取数据）
public Object getAttribute(String name); // Object v = map.get(k)
// 删（怎么删除ServletContext应用域中的数据）
public void removeAttribute(String name); // map.remove(k)
```

注意：

以后我们编写Servlet类的时候，实际上是不会去直接继承GenericServlet类的，因为我们是B/S结构的系统，这种系统是基于HTTP超文本传输协议的，在Servlet规范当中，提供了一个类叫做HttpServlet，它是专门为HTTP协议准备的一个Servlet类。我们编写的Servlet类要继承HttpServlet。（HttpServlet是HTTP协议专用的。）使用HttpServlet处理HTTP协议更便捷。但是你需要直到它的继承结构： 

```java
jakarta.servlet.Servlet（接口）【爷爷】
jakarta.servlet.GenericServlet implements Servlet（抽象类）【儿子】
jakarta.servlet.http.HttpServlet extends GenericServlet（抽象类）【孙子】

我们以后编写的Servlet要继承HttpServlet类。
```

## 

## 3.HttpServlet

- HttpServlet类是专门为HTTP协议准备的。比GenericServlet更加适合HTTP协议下的开发。
- HttpServlet在哪个包下？ 

- - jakarta.servlet.http.HttpServlet

- http包下都有哪些类和接口呢？jakarta.servlet.http.*; 

- - jakarta.servlet.http.HttpServlet （HTTP协议专用的Servlet类，抽象类）
  - jakarta.servlet.http.HttpServletRequest （HTTP协议专用的请求对象）
  - jakarta.servlet.http.HttpServletResponse （HTTP协议专用的响应对象）

- HttpServletRequest对象中封装了什么信息？ 

- - HttpServletRequest，简称request对象。
  - HttpServletRequest中封装了请求协议的全部内容。
  - Tomcat服务器（WEB服务器）将“请求协议”中的数据全部解析出来，然后将这些数据全部封装到request对象当中了。
  - 也就是说，我们只要面向HttpServletRequest，就可以获取请求协议中的数据。

- HttpServletResponse对象是专门用来响应HTTP协议到浏览器的。
- 回忆Servlet生命周期？ 

- - 用户第一次请求 

- - - Tomcat服务器通过反射机制，调用无参数构造方法。创建Servlet对象。(web.xml文件中配置的Servlet类对应的对象。)
    - Tomcat服务器调用Servlet对象的init方法完成初始化。
    - Tomcat服务器调用Servlet对象的service方法处理请求。

- - 用户第二次请求 

- - - Tomcat服务器调用Servlet对象的service方法处理请求。

- - 用户第三次请求 

- - - Tomcat服务器调用Servlet对象的service方法处理请求。

- - .... 

- - - Tomcat服务器调用Servlet对象的service方法处理请求。

- - 服务器关闭 

- - - Tomcat服务器调用Servlet对象的destroy方法，做销毁之前的准备工作。
    - Tomcat服务器销毁Servlet对象。

- HttpServlet源码分析：

```java
public class HelloServlet extends HttpServlet {
	// 用户第一次请求，创建HelloServlet对象的时候，会执行这个无参数构造方法。
	public HelloServlet() {
    }
    
}

public abstract class GenericServlet implements Servlet, ServletConfig,
        java.io.Serializable {
           
	/* 用户第一次请求的时候，HelloServlet对象第一次被创建之后，因为HelloServlet
       没有init方法，HttpServlet也没有，所以执行GenericServlet的init有参构造.。  */
    public void init(ServletConfig config) throws ServletException {
        this.config = config;
        this.init();
    }
	// 用户第一次请求的时候，带有参数的init(ServletConfig config)执行之后，会执行这个没有参数的init()
	public void init() throws ServletException {
        // NOOP by default
    }
}

// HttpServlet模板类。
public abstract class HttpServlet extends GenericServlet {
    // 用户发送第一次请求的时候这个service会执行(HelloServlet没有service,执行Httpservlet的)
    // 用户发送第N次请求的时候，这个service方法还是会执行。
    // 用户只要发送一次请求，这个service方法就会执行一次。
    @Override
    public void service(ServletRequest req, ServletResponse res)
        throws ServletException, IOException {

        HttpServletRequest  request;
        HttpServletResponse response;

        try {
            // 将ServletRequest和ServletResponse向下转型为带有Http的HttpServletRequest和HttpServletResponse
            request = (HttpServletRequest) req;
            response = (HttpServletResponse) res;
        } catch (ClassCastException e) {
            throw new ServletException(lStrings.getString("http.non_http"));
        }
        // 调用重载的service方法。
        service(request, response);
    }
    
    // 这个service方法的两个参数都是带有Http的。
    // 这个service是一个模板方法。
    // 在该方法中定义核心算法骨架，具体的实现步骤延迟到子类中去完成。
    protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        // 获取请求方式
        // 这个请求方式最终可能是：""
        // 注意：request.getMethod()方法获取的是请求方式，可能是七种之一：
        // GET POST PUT DELETE HEAD OPTIONS TRACE
        String method = req.getMethod();

        // 如果请求方式是GET请求，则执行doGet方法。
        if (method.equals(METHOD_GET)) {
            long lastModified = getLastModified(req);
            if (lastModified == -1) {
                // servlet doesn't support if-modified-since, no reason
                // to go through further expensive logic
                doGet(req, resp);
            } else {
                long ifModifiedSince;
                try {
                    ifModifiedSince = req.getDateHeader(HEADER_IFMODSINCE);
                } catch (IllegalArgumentException iae) {
                    // Invalid date header - proceed as if none was set
                    ifModifiedSince = -1;
                }
                if (ifModifiedSince < (lastModified / 1000 * 1000)) {
                    // If the servlet mod time is later, call doGet()
                    // Round down to the nearest second for a proper compare
                    // A ifModifiedSince of -1 will always be less
                    maybeSetLastModified(resp, lastModified);
                    doGet(req, resp);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                }
            }

        } else if (method.equals(METHOD_HEAD)) {
            long lastModified = getLastModified(req);
            maybeSetLastModified(resp, lastModified);
            doHead(req, resp);

        } else if (method.equals(METHOD_POST)) {
            // 如果请求方式是POST请求，则执行doPost方法。
            doPost(req, resp);

        } else if (method.equals(METHOD_PUT)) {
            doPut(req, resp);

        } else if (method.equals(METHOD_DELETE)) {
            doDelete(req, resp);

        } else if (method.equals(METHOD_OPTIONS)) {
            doOptions(req,resp);

        } else if (method.equals(METHOD_TRACE)) {
            doTrace(req,resp);

        } else {
            //
            // Note that this means NO servlet supports whatever
            // method was requested, anywhere on this server.
            //

            String errMsg = lStrings.getString("http.method_not_implemented");
            Object[] errArgs = new Object[1];
            errArgs[0] = method;
            errMsg = MessageFormat.format(errMsg, errArgs);

            resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, errMsg);
        }
    }
    
    /*子类没有重写doGet就会走父类的，报405错误*/
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException{
        // 报405错误
        String msg = lStrings.getString("http.method_get_not_supported");
        sendMethodNotAllowed(req, resp, msg);
    }
        /*子类没有重写doPost就会走父类的，报405错误*/
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        // 报405错误
        String msg = lStrings.getString("http.method_post_not_supported");
        sendMethodNotAllowed(req, resp, msg);
    }
    
}

/*
通过以上源代码分析：
	假设前端发送的请求是get请求，后端程序员重写的方法是doPost
	假设前端发送的请求是post请求，后端程序员重写的方法是doGet
	会发生什么呢？
		发生405这样的一个错误。
		405表示前端的错误，发送的请求方式不对。和服务器不一致。不是服务器需要的请求方式。
	
	通过以上源代码可以知道：只要HttpServlet类中的doGet方法或doPost方法执行了，必然405.

怎么避免405的错误呢？
	后端重写了doGet方法，前端一定要发get请求。
	后端重写了doPost方法，前端一定要发post请求。
	这样可以避免405错误。
	
	这种前端到底需要发什么样的请求，其实应该后端说了算。后端让发什么方式，前端就得发什么方式。
	

*/
```

- 我们编写的HelloServlet直接继承HttpServlet，直接重写HttpServlet类中的service()方法行吗？ 

- - 可以，只不过你享受不到405错误。享受不到HTTP协议专属的东西。

-  到今天我们终于得到了最终的一个Servlet类的开发步骤： 

- - 第一步：编写一个Servlet类，直接继承HttpServlet
  - 第二步：重写doGet方法或者重写doPost方法，到底重写谁，javaweb程序员说了算。
  - 第三步：将Servlet类配置到web.xml文件当中。
  - 第四步：准备前端的页面（form表单），form表单中指定请求路径即可。



### 欢迎资源文件



1.前提：

用户可以记住网站名，但是不会记住网站资源文件名





2.默认欢迎资源文件:



用户发送了一个针对某个网站的【默认请求】时，

此时由Http服务器自动从当前网站返回的资源文件



正常请求： http://localhost:8080/myWeb/index.html



默认请求： http://localhost:8080/myWeb/



3.Tomcat对于默认欢迎资源文件定位规则



1）规则位置：Tomcat安装位置/conf/web.xml



2）规则命令：<welcome-file-list>

<welcome-file>index.html</welcome-file>

<welcome-file>index.htm</welcome-file>

<welcome-file>index.jsp</welcome-file>

</welcome-file-list>



4.设置当前网站的默认欢迎资源文件规则



1)规则位置:网站/web/WEB-INF/web.xml



2)规则命令:<welcome-file-list>

<welcome-file>login.html</welcome-file>

</welcome-file-list>

3)网站设置自定义默认文件定位规则，此时Tomcat自带定位规则将失效



-  动态资源：Servlet类。 

- -  步骤： 

- - -  第一步：写一个Servlet 

```java
public class WelcomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print("<h1>welcome to bjpowernode!</h1>");
    }
}
```

- - -  第二步：在web.xml文件中配置servlet 

```java
    <servlet>
        <servlet-name>welcomeServlet</servlet-name>
        <servlet-class>com.bjpowernode.javaweb.servlet.WelcomeServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>welcomeServlet</servlet-name>
        <url-pattern>http://localhost:8080/myWeb/login.html</url-pattern>
    </servlet-mapping>
```

 第三步：在web.xml文件中配置欢迎页

```java
    <welcome-file-list>
        <welcome-file>http://localhost:8080/myWeb/login.html</welcome-file>
    </welcome-file-list>
```

- 关于WEB-INF目录



- 在WEB-INF目录下新建了一个文件：welcome.html
- 打开浏览器访问：http://localhost:8080/servlet07/WEB-INF/welcome.html 出现了404错误。
- 注意：放在WEB-INF目录下的资源是受保护的。在浏览器上不能够通过路径直接访问。所以像HTML、CSS、JS、image等静态资源一定要放到WEB-INF目录之外。

## 4.HttpServletRequest接口详解

-  HttpServletRequest是一个接口，全限定名称：jakarta.servlet.http.HttpServletRequest 
- HttpServletRequest对象中都有什么信息？都包装了什么信息？ 

- - HttpServletRequest对象是Tomcat服务器负责创建的。这个对象中封装了什么信息？封装了HTTP的请求协议。
  - 实际上是用户发送请求的时候，遵循了HTTP协议，发送的是HTTP的请求协议，Tomcat服务器将HTTP协议中的信息以及数据全部解析出来，然后Tomcat服务器把这些信息封装到HttpServletRequest对象当中，传给了我们javaweb程序员。
  - javaweb程序员面向HttpServletRequest接口编程，调用方法就可以获取到请求的信息了。

-  request和response对象的生命周期？ 

- - request对象和response对象，一个是请求对象，一个是响应对象。这两个对象只在当前请求中有效。
  - 一次请求对应一个request。
  - 两次请求则对应两个request。
  - .....

### 常用的方法

#### 获取用户提交的数据



- -  怎么获取前端浏览器用户提交的数据？

```java
Map<String,String[]> getParameterMap() 这个是获取Map
Enumeration<String> getParameterNames() 这个是获取Map集合中所有的key
String[] getParameterValues(String name) 根据key获取Map集合的value
String getParameter(String name)  获取value这个一维数组当中的第一个元素。这个方法最常用。
// 以上的4个方法，和获取用户提交的数据有关系。
```

- - - 思考：如果是你，前端的form表单提交了数据之后，你准备怎么存储这些数据，你准备采用什么样的数据结构去存储这些数据呢？ 

- - - -  前端提交的数据格式：username=abc&userpwd=111&aihao=s&aihao=d&aihao=tt 
      -  我会采用Map集合来存储： 

```java
Map<String,String>
    key存储String
    value存储String
    这种想法对吗？不对。
    如果采用以上的数据结构存储会发现key重复的时候value覆盖。
    key         value
    ---------------------
    username    abc
    userpwd     111
    aihao       s
    aihao       d
    aihao       tt
    这样是不行的，因为map的key不能重复。
Map<String, String[]>
    key存储String
    value存储String[]
    key				value
    -------------------------------
    username		{"abc"}
    userpwd			{"111"}
    aihao			{"s","d","tt"}
```

- 注意：前端表单提交数据的时候，假设提交了120这样的“数字”，其实是以字符串"120"的方式提交的，所以服务器端获取到的一定是一个字符串的"120"，而不是一个数字。（前端永远提交的是字符串，后端获取的也永远是字符串。）

**四个方法的使用**

```java
//username=zhangsan&userpwd=123&interest=s&interest=d
/*Map<String,String[]>
     key				value
  ---------------------------
  "username"		{"zhangsan"}
  "userpwd"		  {"123"}
  "interest"		{"s", "d"}  */

public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException,ServletException{
  
         /*1.最常用的方式  String getParameter(String name)*/
       	String username = request.getParameter("username");
        String userpwd = request.getParameter("userpwd");
  			
  				System.out.println(username);
       	  System.out.println(userpwd);
  
  
				/*2.既然是checkbox，调用方法：request.getParameterValues("interest")*/
      	String[] interests = request.getParameterValues("interest");
				
  					for(String  i : interests){
           			 System.out.println(i);
       	 }
				
  			/*3.直接通过getParameterNames()这个方法，可以直接获取这个Map集合的所有key*/
        Enumeration<String> names = request.getParameterNames();
        while(names.hasMoreElements()){
            String name = names.nextElement();
            System.out.println(name);
        }
			
  			
  		/*4.获取参数Map集合*/
        Map<String,String[]> parameterMap = request.getParameterMap();
        // 遍历Map集合(获取Map集合中所有的key，遍历)
        Set<String> keys = parameterMap.keySet();
        Iterator<String> it = keys.iterator();
        while(it.hasNext()){
            String key = it.next();
					  System.out.print(key+"=");
            // 通过key获取value
            String[] values = parameterMap.get(key);
          for(String value : values){
                System.out.print(value+",");
            }

            System.out.println();
          
          /*4.输出结果
          username=zhangsan,
					userpwd=123,
					interest=s,d,

}
```

#### HttpServletRequest接口的其他常用方法：

```java
			// 获取客户端的IP地址
          String remoteAddr = request.getRemoteAddr();
          
          // get请求在请求行上提交数据。
          // post请求在请求体中提交数据。
          // 设置请求体的字符集。（显然这个方法是处理POST请求的乱码问题。这种方式并不能解决get请求的乱码问题。）
          // Tomcat10之后，request请求体当中的字符集默认就是UTF-8，不需要设置字符集，不会出现乱码问题。
          // Tomcat9前（包括9在内），如果前端请求体提交的是中文，后端获取之后出现乱码，怎么解决这个乱码？执行以下代码。
          request.setCharacterEncoding("UTF-8");
          
          // 在Tomcat9之前（包括9），响应中文也是有乱码的，怎么解决这个响应的乱码？
          response.setContentType("text/html;charset=UTF-8");
          // 在Tomcat10之后，包括10在内，响应中文的时候就不在出现乱码问题了。以上代码就不需要设置UTF-8了。
          
          // 注意一个细节
          // 在Tomcat10包括10在内之后的版本，中文将不再出现乱码。（这也体现了中文地位的提升。）
          
          // get请求乱码问题怎么解决？
          // get请求发送的时候，数据是在请求行上提交的，不是在请求体当中提交的。
          // get请求乱码怎么解决
          // 方案：修改CATALINA_HOME/conf/server.xml配置文件
          <Connector URIEncoding="UTF-8" />
          // 注意：从Tomcat8之后，URIEncoding的默认值就是UTF-8，所以GET请求也没有乱码问题了。
              
          // 获取应用的根路径
          String contextPath = request.getContextPath();
          
          // 获取请求方式
          String method = request.getMethod();
          
          // 获取请求的URI
          String uri = request.getRequestURI();  // /aaa/testRequest
          
          // 获取servlet path
          String servletPath = request.getServletPath(); //   /testRequest
```

### 

### 请求域对象request

请求域”对象要比“应用域”对象范围小很多。生命周期短很多。请求域只在一次请求内有效。

一个请求对象request对应一个请求域对象。一次请求结束之后，这个请求域就销毁了。

-  请求域对象也有这三个方法：

```java
// 存（怎么向request请求域中存数据）
public void setAttribute(String name, Object value); // map.put(k, v)
// 取（怎么从request请求域中取数据）
public Object getAttribute(String name); // Object v = map.get(k)
// 删（怎么删除request请求域中的数据）
public void removeAttribute(String name); // map.remove(k)
```

- 请求域和应用域的选用原则？

尽量使用小的域对象，因为小的域对象占用的资源较少。



两个Servlet怎么共享数据？

- 将数据放到ServletContext应用域当中，当然是可以的，但是应用域范围太大，占用资源太多。不建议使用。
- 可以将数据放到request域当中，然后AServlet转发到BServlet，保证AServlet和BServlet在同一次请求当中，这样就可以做到两个Servlet，或者多个Servlet共享同一份数据。

### 请求转发

1.原理： 是由WEB服务器来控制的。A资源跳转到B资源，这个跳转动作是Tomcat服务器内部完成的。

```java
	// 第一步：获取请求转发器对象
	RequestDispatcher dispatcher = request.getRequestDispatcher("/b");
	// 第二步：调用转发器的forward方法完成跳转/转发
	dispatcher.forward(request,response);
            
	// 第一步和第二步代码可以联合在一起。
	 request.getRequestDispatcher("/b").forward(request,response);
	
//注意:转发的时候是一次请求,会将当前的request和response对象传递给下一个servlet.
         
```

- 转发的下一个资源必须是一个Servlet吗？

不一定，只要是Tomcat服务器当中的合法资源，都是可以转发的。例如：html....

注意：转发的时候，路径的写法要注意，转发的路径以“/”开始，不加项目名。



关于request对象中两个非常容易混淆的方法：

```java
			// uri?username=zhangsan&userpwd=123&sex=1
          String username = request.getParameter("username");
          
          // 之前一定是执行过：request.setAttribute("name", new Object())
          Object obj = request.getAttribute("name");
          
          // 以上两个方法的区别是什么？
          // 第一个方法：获取的是用户在浏览器上提交的数据。
          // 第二个方法：获取的是请求域当中绑定的数据。
```

![img](https://cdn.nlark.com/yuque/0/2022/png/22016332/1642585818323-4c0e7327-7e0d-4ddb-ad35-b9cef74bb733.png)

特点:

在请求转发过程中，浏览器只发送一个了个Http请求协议包。

参与本次请求的所有Servlet共享同一个请求协议包，因此

这些Servlet接收的请求方式与浏览器发送的请求方式保持一致

### 重定向

1.工作原理:是浏览器完成的。具体跳转到哪个资源，是浏览器说了算。。



2.实现命令

```java
 response.sendRedirect("请求地址")
 //注意:重定向地址需要加上 项目名
 //因为浏览器发送请求是需要加上项目名的
```

3.特征:



1)请求地址：

既可以把当前网站内部的资源文件地址发送给浏览器 （/网站名/资源文件名）

也可以把其他网站资源文件地址发送给浏览器(http://ip地址:端口号/网站名/资源文件名)



2)请求次数



浏览器至少发送两次请求，但是只有第一次请求是用户手动发送。

后续请求都是浏览器自动发送的。



\3) 请求方式：

重定向解决方案中，通过地址栏通知浏览器发起下一次请求，因此

通过重定向解决方案调用的资源文件接收的请求方式一定是【GET】



4.缺点:

重定向解决方案需要在浏览器与服务器之间进行多次往返，大量时间

消耗在往返次数上，增加用户等待服务时间

![img](https://cdn.nlark.com/yuque/0/2022/png/22016332/1642586316869-6ec8316f-b8af-49a8-b7d0-042fac7c9f57.png)

#### 转发和重定向区别

转发和重定向应该如何选择?什么时候使用转发，什么时候使用重定向?



- 如果在上一个Servlet当中向request域当中绑定了数据，希望从下一个Servlet当中把request域里面的数据取出来，使用转发机制。



- 剩下所有的请求均使用重定向。(重定向使用较多。)
- 他俩跳转的资源只要是服务器内部合法的资源即可。(Servlet,JSP,HTML).......
- 转发会存在浏览器刷新问题



## 5.session和cookie

### session

- 什么是会话？

- - 用户打开浏览器，进行一系列操作，然后最终将浏览器关闭，这个整个过程叫做：一次会话。会话在服务器端也有一个对应的java对象，这个java对象叫做：session。
  - 什么是一次请求：用户在浏览器上点击了一下，然后到页面停下来，可以粗略认为是一次请求。请求对应的服务器端的java对象是：request。
  - 一个会话当中包含多次请求。（一次会话对应N次请求。）

- 在java的servlet规范当中，session对应的类名：HttpSession（jarkata.servlet.http.HttpSession）
- session机制属于B/S结构的一部分。如果使用php语言开发WEB项目，同样也是有session这种机制的。session机制实际上是一个规范。然后不同的语言对这种会话机制都有实现。
- session对象最主要的作用是：保存会话状态。（用户登录成功了，这是一种登录成功的状态，你怎么把登录成功的状态一直保存下来呢？使用session对象可以保留会话状态。）
- 为什么需要session对象来保存会话状态呢？

- - 因为HTTP协议是一种无状态协议。
  - 什么是无状态：请求的时候，B和S是连接的，但是请求结束之后，连接就断了。为什么要这么做？HTTP协议为什么要设计成这样？因为这样的无状态协议，可以降低服务器的压力。请求的瞬间是连接的，请求结束之后，连接断开，这样服务器压力小。
  - 只要B和S断开了，那么关闭浏览器这个动作，服务器知道吗？

- - - 不知道。服务器是不知道浏览器关闭的。

- 张三打开一个浏览器A，李四打开一个浏览器B，访问服务器之后，在服务器端会生成：

- - 张三专属的session对象
  - 李四专属的session对象

- 为什么不使用request对象保存会话状态？为什么不使用ServletContext对象保存会话状态？

- - request.setAttribute()存，request.getAttribute()取，ServletContext也有这个方法。request是请求域。ServletContext是应用域。
  - request是一次请求一个对象。
  - ServletContext对象是服务器启动的时候创建，服务器关闭的时候销毁，这个ServletContext对象只有一个。
  - ServletContext对象的域太大。
  - request请求域（HttpServletRequest）、session会话域（HttpSession）、application域（ServletContext）
  - request < session < application

- 思考一下：session对象的实现原理。

- - HttpSession session = request.getSession();
  - 这行代码很神奇。张三访问的时候获取的session对象就是张三的。李四访问的时候获取的session对象就是李四的。

session的实现原理：

- JSESSIONID=xxxxxx 这个是以Cookie的形式保存在浏览器的内存中的。浏览器只要关闭。这个cookie就没有了。
- session列表是一个Map，map的key是sessionid，map的value是session对象。
- 用户第一次请求，服务器生成session对象，同时生成id，将id发送给浏览器。
- 用户第二次请求，自动将浏览器内存中的id发送给服务器，服务器根据id查找session对象。
- 关闭浏览器，内存消失，cookie消失，sessionid消失，会话等同于结束

![img](https://cdn.nlark.com/yuque/0/2022/png/22016332/1646276974302-6f58ef8c-8c6a-46f4-bb13-afa557983922.png)

- Cookie禁用了，session还能找到吗？

- - cookie禁用是什么意思？服务器正常发送cookie给浏览器，但是浏览器不要了。拒收了。并不是服务器不发了。
  - 找不到了。每一次请求都会获取到新的session对象。
  - cookie禁用了，session机制还能实现吗？

- - - 可以。需要使用URL重写机制。
    - http://localhost:8080/servlet12/test/session;jsessionid=19D1C99560DCBF84839FA43D58F56E16
    - URL重写机制会提高开发者的成本。开发人员在编写任何请求路径的时候，后面都要添加一个sessionid，给开发带来了很大的难度，很大的成本。所以大部分的网站都是这样设计的：你要是禁用cookie，你就别用了。

- 总结一下到目前位置我们所了解的域对象：

- - request（对应的类名：HttpServletRequest）

- - - 请求域（请求级别的）

- - session（对应的类名：HttpSession）

- - - 会话域（用户级别的）

- - application（对应的类名：ServletContext）

- - - 应用域（项目级别的，所有用户共享的。）

- - 这三个域对象的大小关系

- - - request < session < application

- - 他们三个域对象都有以下三个公共的方法：

- - - setAttribute（向域当中绑定数据）
    - getAttribute（从域当中获取数据）
    - removeAttribute（删除域当中的数据）

- - 使用原则：尽量使用小的域。



### cookie

- session的实现原理中，每一个session对象都会关联一个sessionid，例如：

- - JSESSIONID=41C481F0224664BDB28E95081D23D5B8
  - 以上的这个键值对数据其实就是cookie对象。
  - 对于session关联的cookie来说，这个cookie是被保存在浏览器的“运行内存”当中。
  - 只要浏览器不关闭，用户再次发送请求的时候，会自动将运行内存中的cookie发送给服务器。
  - 例如，这个Cookie: JSESSIONID=41C481F0224664BDB28E95081D23D5B8就会再次发送给服务器。
  - 服务器就是根据41C481F0224664BDB28E95081D23D5B8这个值来找到对应的session对象的。

- cookie怎么生成？cookie保存在什么地方？cookie有啥用？浏览器什么时候会发送cookie，发送哪些cookie给服务器？？？？？？？
- cookie最终是保存在浏览器客户端上的。

- - 可以保存在运行内存中。（浏览器只要关闭cookie就消失了。）
  - 也可以保存在硬盘文件中。（永久保存。）

- cookie有啥用呢？

- - cookie和session机制其实都是为了保存会话的状态。
  - cookie是将会话的状态保存在浏览器客户端上。（cookie数据存储在浏览器客户端上的。）
  - session是将会话的状态保存在服务器端上。（session对象是存储在服务器上。）
  - 为什么要有cookie和session机制呢？因为HTTP协议是无状态 无连接协议。

- cookie的经典案例

- - 京东商城，在未登录的情况下，向购物车中放几件商品。然后关闭商城，再次打开浏览器，访问京东商城的时候，购物车中的商品还在，这是怎么做的？我没有登录，为什么购物车中还有商品呢？

- - - 将购物车中的商品编号放到cookie当中，cookie保存在硬盘文件当中。这样即使关闭浏览器。硬盘上的cookie还在。下一次再打开京东商城的时候，查看购物车的时候，会自动读取本地硬盘中存储的cookie，拿到商品编号，动态展示购物车中的商品。

- - - - 京东存储购物车中商品的cookie可能是这样的：productIds=xxxxx,yyyy,zzz,kkkk
      - 注意：cookie如果清除掉，购物车中的商品就消失了。

- - 126邮箱中有一个功能：十天内免登录

- - - 这个功能也是需要cookie来实现的。
    - 怎么实现的呢？

- - - - 用户输入正确的用户名和密码，并且同时选择十天内免登录。登录成功后。浏览器客户端会保存一个cookie，这个cookie中保存了用户名和密码等信息，这个cookie是保存在硬盘文件当中的，十天有效。在十天内用户再次访问126的时候，浏览器自动提交126的关联的cookie给服务器，服务器接收到cookie之后，获取用户名和密码，验证，通过之后，自动登录成功。
      - 怎么让cookie失效？

- - - - - 十天过后自动失效。
        - 或者改密码。
        - 或者在客户端浏览器上清除cookie。

- cookie机制和session机制其实都不属于java中的机制，实际上cookie机制和session机制都是HTTP协议的一部分。php开发中也有cookie和session机制，只要是你是做web开发，不管是什么编程语言，cookie和session机制都是需要的。
- HTTP协议中规定：任何一个cookie都是由name和value组成的。name和value都是字符串类型的。
- 在java的servlet中，对cookie提供了哪些支持呢？

- - 提供了一个Cookie类来专门表示cookie数据。jakarta.servlet.http.Cookie;
  - java程序怎么把cookie数据发送给浏览器呢？response.addCookie(cookie);

- 在HTTP协议中是这样规定的：当浏览器发送请求的时候，会自动携带该path下的cookie数据给服务器。（URL。）
- 关于cookie的有效时间

- - 怎么用java设置cookie的有效时间

- - - cookie.setMaxAge(60 * 60); 设置cookie在一小时之后失效。

- - 没有设置有效时间：默认保存在浏览器的运行内存中，浏览器关闭则cookie消失。
  - 只要设置cookie的有效时间 > 0，这个cookie一定会存储到硬盘文件当中。
  - 设置cookie的有效时间 = 0 呢？

- - - cookie被删除，同名cookie被删除。

- - 设置cookie的有效时间 < 0 呢？

- - - 保存在运行内存中。和不设置一样。

- 关于cookie的path，cookie关联的路径：

- - 假设现在发送的请求路径是“http://localhost:8080/servlet13/cookie/generate”生成的cookie，如果cookie没有设置path，默认的path是什么？

- - - 默认的path是：http://localhost:8080/servlet13/cookie 以及它的子路径。
    - 也就是说，以后只要浏览器的请求路径是http://localhost:8080/servlet13/cookie 这个路径以及这个路径下的子路径，cookie都会被发送到服务器。

- - 手动设置cookie的path

- - - cookie.setPath(“/servlet13”); 表示只要是这个servlet13项目的请求路径，都会提交这个cookie给服务器。

浏览器发送cookie给服务器了，服务器中的java程序怎么接收？

```java
Cookie[] cookies = request.getCookies(); // 这个方法可能返回null
if(cookies != null){
    for(Cookie cookie : cookies){
        // 获取cookie的name
        String name = cookie.getName();
        // 获取cookie的value
        String value = cookie.getValue();
    }
}
```

使用cookie实现一下十天内免登录功能。



## 6.过滤器和监听器

### 过滤器

-  当前的OA项目存在什么缺陷？ 

- - DeptServlet、EmpServlet、OrderServlet。每一个Servlet都是处理自己相关的业务。在这些Servlet执行之前都是需要判断用户是否登录了。如果用户登录了，可以继续操作，如果没有登录，需要用户登录。这段判断用户是否登录的代码是固定的，并且在每一个Servlet类当中都需要编写，显然代码没有得到重复利用。包括每一个Servlet都要解决中文乱码问题，也有公共的代码。这些代码目前都是重复编写，并没有达到复用。怎么解决这个问题? 

- - - 可以使用Servlet规范中的Filter过滤器来解决这个问题。

-  Filter是什么，有什么用，执行原理是什么？ 

- - Filter是过滤器。
  - Filter可以在Servlet这个目标程序执行之前添加代码。也可以在目标Servlet执行之后添加代码。之前之后都可以添加过滤规则。
  - 一般情况下，都是在过滤器当中编写公共代码。

-  一个过滤器怎么写呢？ 

- -  第一步：编写一个Java类实现一个接口：jarkata.servlet.Filter。并且实现这个接口当中所有的方法。 

- - - init方法：在Filter对象第一次被创建之后调用，并且只调用一次。
    - doFilter方法：只要用户发送一次请求，则执行一次。发送N次请求，则执行N次。在这个方法中编写过滤规则。
    - destroy方法：在Filter对象被释放/销毁之前调用，并且只调用一次。

- -  第二步：在web.xml文件中对Filter进行配置。这个配置和Servlet很像。 

```java
<filter>
    <filter-name>filter2</filter-name>
    <filter-class>com.bjpowernode.javaweb.servlet.Filter2</filter-class>
</filter>
<filter-mapping>
    <filter-name>filter2</filter-name>
    <url-pattern>*.do</url-pattern>
</filter-mapping>
```

- - 或者使用注解：@WebFilter({"*.do"})

-  注意： 

- - Servlet对象默认情况下，在服务器启动的时候是不会新建对象的。
  - Filter对象默认情况下，在服务器启动的时候会新建对象。
  - Servlet是单例的。Filter也是单例的。（单实例。）

-  目标Servlet是否执行，取决于两个条件： 

- - 第一：在过滤器当中是否编写了：chain.doFilter(request, response); 代码。
  - 第二：用户发送的请求路径是否和Servlet的请求路径一致。

-  chain.doFilter(request, response); 这行代码的作用： 

- - 执行下一个过滤器，如果下面没有过滤器了，执行最终的Servlet。

-  注意：Filter的优先级，天生的就比Servlet优先级高。 

- - /a.do 对应一个Filter，也对应一个Servlet。那么一定是先执行Filter，然后再执行Servlet。

-  关于Filter的配置路径： 

- - /a.do、/b.do、/dept/save。这些配置方式都是精确匹配。
  - /* 匹配所有路径。
  - *.do 后缀匹配。不要以 / 开始
  - /dept/*  前缀匹配。

-  在web.xml文件中进行配置的时候，Filter的执行顺序是什么？ 

- - 依靠filter-mapping标签的配置位置，越靠上优先级越高。

-  过滤器的调用顺序，遵循栈数据结构。 
-  使用@WebFilter的时候，Filter的执行顺序是怎样的呢？ 

- - 执行顺序是：比较Filter这个类名。
  - 比如：FilterA和FilterB，则先执行FilterA。
  - 比如：Filter1和Filter2，则先执行Filter1.

-  Filter的生命周期？ 

- - 和Servlet对象生命周期一致。
  - 唯一的区别：Filter默认情况下，在服务器启动阶段就实例化。Servlet不会。

-  Filter过滤器这里有一个设计模式： 

- - 责任链设计模式。
  - 过滤器最大的优点： 

- - - 在程序编译阶段不会确定调用顺序。因为Filter的调用顺序是配置到web.xml文件中的，只要修改web.xml配置文件中filter-mapping的顺序就可以调整Filter的执行顺序。显然Filter的执行顺序是在程序运行阶段动态组合的。那么这种设计模式被称为责任链设计模式。

- - 责任链设计模式最大的核心思想： 

- - - 在程序运行阶段，动态的组合程序的调用顺序。

过滤器的实现原理

![img](https://cdn.nlark.com/yuque/0/2022/png/22016332/1647507375441-5d3181ff-2d40-4bed-b833-30c194c1674d.png)



### Listener监听器

- 什么是监听器？

- - 监听器是Servlet规范中的一员。就像Filter一样。Filter也是Servlet规范中的一员。
  - 在Servlet中，所有的监听器接口都是以“Listener”结尾。

- 监听器有什么用？

- - 监听器实际上是Servlet规范留给我们javaweb程序员的特殊时机。
  - 特殊的时刻如果想执行这段代码，你需要想到使用对应的监听器。

- Servlet规范中提供了哪些监听器？

- - jakarta.servlet包下：

- - - ServletContextListener
    - ServletContextAttributeListener
    - ServletRequestListener
    - ServletRequestAttributeListener

- - jakarta.servlet.http包下：

- - - HttpSessionListener
    - HttpSessionAttributeListener

- - - - 该监听器需要使用@WebListener注解进行标注。
      - 该监听器监听的是什么？是session域中数据的变化。只要数据变化，则执行相应的方法。主要监测点在session域对象上。

- - - HttpSessionBindingListener

- - - - 该监听器不需要使用@WebListener进行标注。
      - 假设User类实现了该监听器，那么User对象在被放入session的时候触发bind事件，User对象从session中删除的时候，触发unbind事件。
      - 假设Customer类没有实现该监听器，那么Customer对象放入session或者从session删除的时候，不会触发bind和unbind事件。

- - - HttpSessionIdListener

- - - - session的id发生改变的时候，监听器中的唯一一个方法就会被调用。

- - - HttpSessionActivationListener

- - - - 监听session对象的钝化和活化的。
      - 钝化：session对象从内存存储到硬盘文件。
      - 活化：从硬盘文件把session恢复到内存。

- 实现一个监听器的步骤：以ServletContextListener为例。

- - 第一步：编写一个类实现ServletContextListener接口。并且实现里面的方法。

```java
void contextInitialized(ServletContextEvent event)
void contextDestroyed(ServletContextEvent event)
```

- - 第二步：在web.xml文件中对ServletContextListener进行配置，如下：

```xml
<listener>
  <listener-class>com.bjpowernode.javaweb.listener.MyServletContextListener</listener-class>
</listener>
```

- 当然，第二步也可以不使用配置文件，也可以用注解，例如：@WebListener
- 注意：所有监听器中的方法都是不需要javaweb程序员调用的，由服务器来负责调用？什么时候被调用呢？

- - 当某个特殊的事件发生（特殊的事件发生其实就是某个时机到了。）之后，被web服务器自动调用。

- 思考一个业务场景：

- - 请编写一个功能，记录该网站实时的在线用户的个数。
  - 我们可以通过服务器端有没有分配session对象，因为一个session代表了一个用户。有一个session就代表有一个用户。如果你采用这种逻辑去实现的话，session有多少个，在线用户就有多少个。这种方式的话：HttpSessionListener够用了。session对象只要新建，则count++，然后将count存储到ServletContext域当中，在页面展示在线人数即可。
  - 业务发生改变了，只统计登录的用户的在线数量，这个该怎么办？

- - - session.setAttribute("user", userObj); 
    - 用户登录的标志是什么？session中曾经存储过User类型的对象。那么这个时候可以让User类型的对象实现HttpSessionBindingListener监听器，只要User类型对象存储到session域中，则count++，然后将count++存储到ServletContext对象中。页面展示在线人数即可。

- 实现oa项目中当前登录在线的人数。

- - 什么代表着用户登录了？

- - - session.setAttribute("user", userObj); User类型的对象只要往session中存储过，表示有新用户登录。

- - 什么代表着用户退出了？

- - - session.removeAttribute("user"); User类型的对象从session域中移除了。
    - 或者有可能是session销毁了。（session超时）


 
