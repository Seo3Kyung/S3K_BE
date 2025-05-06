package com.s3k.backend.com;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  @Bean
  public OpenAPI openAPI() {
    Info info = new Info();
    info.title("Seoul3Kyung API Documentation").description("Seoul3Kyung API 문서입니다.")
        .version("1.0.0");

    SecurityRequirement schemaRequirement = new SecurityRequirement();

    return new OpenAPI()
        .addSecurityItem(schemaRequirement)
        .info(info);
  }
}
