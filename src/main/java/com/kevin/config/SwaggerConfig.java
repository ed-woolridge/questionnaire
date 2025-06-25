package com.kevin.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Swagger配置类
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("问卷系统API文档")
                        .description("旅游调查问卷系统后端API接口文档")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("开发团队")
                                .email("admin@example.com")
                                .url("https://github.com/your-repo"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server().url("http://localhost:8089").description("本地开发环境"),
                        new Server().url("https://api.example.com").description("生产环境")
                ));
    }
} 