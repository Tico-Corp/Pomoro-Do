package com.tico.pomoro_do.global.config;

import com.tico.pomoro_do.global.auth.jwt.LoginFilter;
import com.tico.pomoro_do.global.auth.jwt.JWTFilter;
import com.tico.pomoro_do.global.auth.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    //AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;
    //JWTUtil 주입
    private final JWTUtil jwtUtil;

    // 허용 주소
    private static final String[] WHITE_LIST = {
            "/api",
            "/api/admins/**",
            "/api/auth/google/**", "/api/auth/tokens/**"
    };

    private static final String[] swaggerURL = {
            "/graphiql", "/graphql",
            "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**"
    };

    // 스프링 시큐리티 기능 비활성화 ('인증','인가' 서비스 적용x)
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/error", "/favicon.ico")
                .requestMatchers("/actuator/**")
                .requestMatchers(swaggerURL);
    }

    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    // 비밀번호 암호화
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //비활성화
        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF 보안 disable - JWT 방식으로 관리하여 세션을 사용하지 않으므로 방어할 필요X
                .formLogin(AbstractHttpConfigurer::disable) //From 로그인 방식 disable - JWT 방식으로 관리
                .httpBasic(AbstractHttpConfigurer::disable); //HTTP Basic 인증 방식 disable - JWT 방식으로 관리
//                .oauth2Login(Customizer.withDefaults()); //oauth2 사용하면 적용

        //경로별 인가 작업 - 특정 경로에 대한 권한 관리 옵션
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(WHITE_LIST).permitAll() // 해당 경로에 대해 모든 권한 허용
                        .requestMatchers(swaggerURL).permitAll() // 해당 경로에 대해 모든 권한 허용
//                        .requestMatchers("/admin").hasRole("ADMIN") // 어드민 권한을 가진 사용자만 접근 가능
                        .anyRequest().authenticated()); // 그 외의 요청에 대해 로그인한 사용자만 접근 가능

        //JWTFilter 등록 (JWT 검증)
        //JWTFilter를 먼저 실행하도록 설정
        //(UsernamePasswordAuthenticationFilter 앞에 배치하여, 모든 요청에서 JWT 검증이 먼저 이루어지도록 설정)
        http
                .addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        /* 서버 자체 로그인 삭제 - START */
        //LoginFilter 앞에 넣어준다. (서버 자체 로그인 삭제)
//        http
//                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);

        //로그인 검증 (서버 자체 로그인 삭제)
        //필터 추가 LoginFilter()는 인자를 받음 (AuthenticationManager() 메소드에 authenticationConfiguration 객체를 넣어야 함) 따라서 등록 필요
        //AuthenticationManager()와 JWTUtil 인수 전달
//        http
//                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, refreshRepository), UsernamePasswordAuthenticationFilter.class);
        //addFilterAt은 UsernamePasswordAuthenticationFilter 대체
        /* 서버 자체 로그인 삭제 - END */

        //세션 설정 : STATELESS - 사용 안함
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}