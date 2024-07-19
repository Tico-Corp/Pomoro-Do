package com.tico.pomoro_do.global.config;

import io.jsonwebtoken.lang.Arrays;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.google.gson.internal.$Gson$Types.arrayOf;

@OpenAPIDefinition(
        info = @Info(
                title = "Pomoro-Do! API 명세서", // 서비스 이름
                description =  "Pomoro-Do!는 Pomodoro 기법을 이용한 작업 시간 관리 서비스입니다. 이 API 명세서는 Pomoro-Do! 서비스의 다양한 기능을 설명합니다. <br>" // 서비스 설명
                        + "API는 모두 HTTPS 프로토콜을 사용하며, 기본 URL은 https://pomorodo.shop/api 입니다.", // API 사용 설명
                version = "v1", // 서비스 버전
                contact = @Contact(
                        name = "TICO", // 연락처 이름
                        url = "https://pomorodo.shop/contact", // 연락처 URL
                        email = "tico240516@gmail.com" // 연락처 이메일
                )
        ),
        servers = {
                @Server(url = "/", description = "접속 서버 관계 없이 Swagger에서 테스트 가능"), // 전체 서버 설정
                @Server(url = "https://pomorodo.shop", description = "<HTTPS 서버> HTTPS 서버에서 접속 시 Swagger에서 테스트 가능"), // HTTPS 서버
                @Server(url = "http://pomorodo.shop:8080", description = "<HTTP 서버> HTTP 서버에서 접속 시 Swagger에서 테스트 가능") // HTTP 서버
        }
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
                .scheme(BEARER_TOKEN_PREFIX)
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
