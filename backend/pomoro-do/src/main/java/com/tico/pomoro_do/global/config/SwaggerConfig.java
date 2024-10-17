package com.tico.pomoro_do.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Pomoro-Do! API 명세서", // 서비스 이름
                description =  "Pomoro-Do!는 Pomodoro 기법을 활용한 작업 시간 관리 서비스입니다. <br>"
                        + "<br>"
                        + "이 API 문서는 Pomoro-Do! 서비스의 주요 기능을 설명합니다. <br>" // 서비스 설명
                        + "<br>"
                        + "API는 HTTPS 프로토콜을 통해 제공되며, 기본 URL은 https://pomorodo.shop/api 입니다. <br>" // API 사용 설명
                        + "테스트 및 개발 환경에서는 http://13.209.240.41:8080/api URL을 사용할 수 있습니다. (도메인 미적용)<br>"
                        + "<br>"
                        + "모든 API 요청에는 JWT 인증이 필요하며, Authorization 헤더에 Bearer 토큰을 포함해야 합니다. <br>"
                        + "단, 회원가입, 로그인, 토큰 재발행 API는 인증이 필요하지 않습니다.", // JWT 인증 설명
                version = "v1", // 서비스 버전
                contact = @Contact(
                        name = "TICO", // 연락처 이름
//                        url = "https://pomorodo.shop/contact", // 연락처 URL
                        email = "tico240516@gmail.com" // 연락처 이메일
                )
        ),
        servers = {
                @Server(url = "/", description = "접속 서버 관계 없이 Swagger에서 테스트 가능"), // 전체 서버 설정
                @Server(url = "http://13.209.240.41:8080", description = "<HTTP 서버> HTTP 서버에서 접속 시 Swagger에서 테스트 가능"), // HTTP 서버
                @Server(url = "https://pomorodo.shop", description = "<HTTPS 서버> HTTPS 서버에서 접속 시 Swagger에서 테스트 가능"), // HTTPS 서버
                @Server(url = "http://pomorodo.shop:8080", description = "<HTTP 서버> HTTP 서버에서 접속 시 Swagger에서 테스트 가능") // HTTP 서버
        }
)
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

    private GroupedOpenApi createGroupedOpenApi(String groupName, String pathPattern) {
        return GroupedOpenApi.builder()
                .group(groupName)
                .pathsToMatch(pathPattern)
                .build();
    }

    @Bean
    public GroupedOpenApi groupedOpenApiV1() {
        return createGroupedOpenApi("Version 1 API", "/api/v1/**");
    }

//    @Bean
//    public GroupedOpenApi groupedOpenApiV2() {
//        return createGroupedOpenApi("Version 2 API", "/api/v2/**");
//    }
//
//    @Bean
//    public GroupedOpenApi groupedOpenApiV3() {
//        return createGroupedOpenApi("Version 3 API", "/api/v3/**");
//    }

}
