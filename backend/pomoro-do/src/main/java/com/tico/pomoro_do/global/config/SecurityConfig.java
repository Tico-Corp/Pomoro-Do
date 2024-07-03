package com.tico.pomoro_do.global.config;

import com.tico.pomoro_do.global.auth.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration) {

        this.authenticationConfiguration = authenticationConfiguration;
    }

    // 허용 주소
    private static final String[] WHITE_LIST = {
            "/login", "/", "/join"
    };

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
                        .requestMatchers("/admin").hasRole("ADMIN") // 어드민 권한을 가진 사용자만 접근 가능
                        .anyRequest().authenticated()); // 그 외의 요청에 대해 로그인한 사용자만 접근 가능

        //로그인 검증
        //필터 추가 LoginFilter()는 인자를 받음 (AuthenticationManager() 메소드에 authenticationConfiguration 객체를 넣어야 함) 따라서 등록 필요
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration)), UsernamePasswordAuthenticationFilter.class);
        //addFilterAt은 UsernamePasswordAuthenticationFilter 대체

        //세션 설정 : STATELESS - 사용 안함
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        return http.build();
    }
}