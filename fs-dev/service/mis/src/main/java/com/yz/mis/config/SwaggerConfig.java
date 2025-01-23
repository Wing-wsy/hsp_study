package com.yz.mis.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "mis",
                description = "后台子系统",
                version = "1.0"
        )
)
@Configuration
public class SwaggerConfig {

}