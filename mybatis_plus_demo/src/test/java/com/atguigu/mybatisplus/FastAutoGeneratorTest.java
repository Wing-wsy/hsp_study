package com.atguigu.mybatisplus;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * @author wing
 * @create 2024/5/13
 */

public class FastAutoGeneratorTest {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://8.218.31.154:3306/mexico_loanover?useSSL=false&serverTimezone=GMT%2B7", "用户名", "密码")
                        .globalConfig(builder -> {
                            builder.author("wing") // 设置作者
                            // .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("/Users/wing/architect/MybatisPlusAutoCode"); // 指定输出目录
                        })
                        .packageConfig(builder -> {
                            builder.parent("com.test") // 设置父包名
                                    .moduleName("mybatisplus") // 设置父包模块名
                                    .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "/Users/wing/architect/MybatisPlusAutoCode")); // 设置mapperXml生成路径
                        })
                        .strategyConfig(builder -> {
                            builder.addInclude("t_app_cardid_occupation") // 设置需要生成的表名
//                                    .addTablePrefix("t_", "c_") // 设置过滤表前缀
                            ;
                             })
                        .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker 引擎模板，默认的是Velocity引擎模板
                        .execute();
    }
}
