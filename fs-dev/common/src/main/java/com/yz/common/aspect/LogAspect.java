package com.yz.common.aspect;

import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import com.yz.common.constant.Strings;
import com.yz.common.util.JSONUtils;
import com.yz.common.util.StrUtils;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Aspect
@Component
/**
 * 日志切面
 */
public class LogAspect {

    /**
     * 切入点
     * 【controller包下，包括子包以 Controller结尾的控制器全部方法打印请求接口入参和接口响应出参】
     */
    @Pointcut("execution(public * com.yz.*.controller..*Controller.*(..))")
    public void log() {
    }

    /**
     *  Before建议通常在连接点之前执行，而Around建议可以在方法调用前后执行自定义行为。
     *  因此，@Before和@Around的执行顺序取决于它们在切面中的声明顺序。通常，@Before会先执行
     *  在本系统@Before会先执行
     */

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        // 开始打印请求日志
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();

        // 打印请求信息
        log.info("------------- 开始 -------------");
        // 完整URL：http://127.0.0.1:8081/cst/h/hello
        String requestURL = request.getRequestURL().toString();
        int index = StrUtils.findNthOccurrence(requestURL, Strings.SLASH.charAt(0), 3);
        String uri = requestURL.substring(index,requestURL.length());
        log.info("请求uri: {}", uri);
//        log.info("类名方法: {}.{}", signature.getDeclaringTypeName(), name);
//        log.info("远程地址: {}", request.getRemoteAddr());

        // 打印请求参数
        Object[] args = joinPoint.getArgs();
//        log.info("请求参数: {}", JSONUtils.toJsonStr(args));

        // 排除特殊类型的参数，如文件类型
        Object[] arguments = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest
                    || args[i] instanceof ServletResponse
                    || args[i] instanceof MultipartFile) {
                continue;
            }
            arguments[i] = args[i];
        }

        // 请求参数排除字段，敏感字段或太长的字段不显示：身份证、手机号、邮箱、密码等
//        String[] excludeProperties = {"mobile","password"};
        // 目前先不排除，敏感字段也正常打印
        String[] excludeProperties = {};
        PropertyPreFilters filters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludefilter = filters.addFilter();
        excludefilter.addExcludes(excludeProperties);
        log.info("请求参数: {}", JSONUtils.toJSONString(arguments, excludefilter));
    }

    @Around("log()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 1.执行真正的controller之前的自定义操作
        long startTime = System.currentTimeMillis();

        // 2.执行真正的controller
        Object result = joinPoint.proceed();
        String pointName = joinPoint.getTarget().getClass().getName()
                + "."
                + joinPoint.getSignature().getName();

        // 3.执行真正的controller之后的自定义操作
        // 响应结果排除字段，敏感字段或太长的字段不显示：身份证、手机号、邮箱、密码等
        String[] excludeProperties = {};
        PropertyPreFilters filters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludefilter = filters.addFilter();
        excludefilter.addExcludes(excludeProperties);
        log.info("返回结果: {}", JSONUtils.toJSONString(result, excludefilter));
        log.info("------------- 结束 耗时：{} ms -------------", System.currentTimeMillis() - startTime);
        long endTime = System.currentTimeMillis() - startTime;
        if (endTime > 2000) {
            log.error("执行位置{}，执行时间太长了，耗费了{}毫秒", pointName, endTime);
        } else if (endTime > 1000) {
            log.warn("执行位置{}，执行时间稍微有点长，耗费了{}毫秒", pointName, endTime);
        } else {
//            log.debug("执行位置{}，执行时间正常，耗费了{}毫秒", pointName, endTime);
        }
        return result;
    }

}
