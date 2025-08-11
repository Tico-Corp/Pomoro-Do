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
                title = "Pomoro-Do! API",
                description = """
            Pomoro-Do!는 Pomodoro 기법을 활용한 작업 시간 관리 서비스입니다.

            이 API 문서는 Pomoro-Do! 서비스의 주요 기능과 사용 방법을 설명합니다.

            API는 HTTPS 프로토콜을 통해 제공되며, Base Path는 `/api/버전` 형식입니다.

            **예)**
            - 운영: https://pomorodo.store/api/v1
            - 로컬: http://localhost:8080/api/v1

            모든 API 요청에는 JWT 인증이 필요하며, Authorization 헤더에 `Bearer <token>`을 포함해야 합니다.
            
            단, 회원가입, 로그인, 토큰 재발행 API는 인증이 필요하지 않습니다.

            **성공 응답 예시**: {"code":"SUCCESS","message":"요청이 성공적으로 처리되었습니다.","data":{...}}
            
            **에러 응답 예시**: {"code":"U-100","message":"이미 사용 중인 이메일입니다."}
            """,
                version = "v1",
                contact = @Contact(name = "TICO", email = "tico240516@gmail.com")
        ),
        servers = {
                @Server(
                        url = "/",
                        description = "Swagger UI를 연 현재 도메인과 포트를 기준으로 API 호출"
                )
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