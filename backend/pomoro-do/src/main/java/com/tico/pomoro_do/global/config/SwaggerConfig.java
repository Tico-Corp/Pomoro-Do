package com.tico.pomoro_do.global.config;

import io.jsonwebtoken.lang.Arrays;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Pomoro-Do API 명세서", // 서비스 이름
                description = "Pomoro-Do 서비스의 API 명세서입니다.", // 서비스 설명
                version = "v1" // 서비스 버전
        )
)
//@SecurityScheme(
//        name = "bearerAuth",
//        type = SecuritySchemeType.HTTP,
//        scheme = "Bearer",
//        bearerFormat = "JWT"
//)
@Configuration
public class SwaggerConfig {

    // Swagger에 JWT를 가능하게 하기 위해서는 Bean 등록을 해주면 된다.

    // JWT 토큰을 사용하는 보안 스키마의 접두사 규칙 설정
    private static final String BEARER_TOKEN_PREFIX = "Bearer";

    @Bean
    public OpenAPI openAPI() {

        // 인증 요청 방식에 HEADER 추가
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        // bearerAuth 이름으로 보안 요구사항 정의
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("bearerAuth");

        return new OpenAPI()
                // SecurityRequirement에 정의한 bearerAuth, 위에서 정의한 securityScheme 추가
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                // 보안 규칙 추가
                .addSecurityItem(securityRequirement);
    }

}
