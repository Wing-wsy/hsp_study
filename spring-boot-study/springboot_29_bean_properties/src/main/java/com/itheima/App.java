package com.itheima;

import com.itheima.bean.CartoonCatAndMouse;
import com.itheima.bean.Cat;
import com.itheima.bean.ServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication()
//@SpringBootApplication(excludeName = "org.springframework.boot.autoconfigure.context.LifecycleAutoConfiguration")

//@SpringBootConfiguration      √
//    @Configuration            √
//        @Component            √
//    @Indexed                  √
//@EnableAutoConfiguration      √
//    @AutoConfigurationPackage     √
//        @Import(AutoConfigurationPackages.Registrar.class)    ？
//    @Import(AutoConfigurationImportSelector.class)            ？
//@ComponentScan(excludeFilters = { √
//        @ComponentScan.Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
//        @ComponentScan.Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class)
//    })


//    @Import(AutoConfigurationPackages.Registrar.class)    设置当前配置所在的包作为扫描包，后续要针对当前的包进行扫描
//    @Import(AutoConfigurationImportSelector.class)        ？


//@Import(CartoonCatAndMouse.class)
public class App {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(App.class);
//        ServerConfig bean = (ServerConfig)ctx.getBean(ServerConfig.class);
//        System.out.println(bean);
        CartoonCatAndMouse bean = ctx.getBean(CartoonCatAndMouse.class);
        bean.play();
//        System.out.println(ctx.getBean(Cat.class));
    }
}
