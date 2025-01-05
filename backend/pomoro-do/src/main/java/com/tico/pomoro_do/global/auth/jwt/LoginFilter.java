package com.tico.pomoro_do.global.auth.jwt;

import com.tico.pomoro_do.domain.auth.service.TokenService;
import com.tico.pomoro_do.global.enums.TokenType;
import com.tico.pomoro_do.global.util.CookieUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collection;
import java.util.Iterator;

//시큐리티 컨피그에 등록해주어야한다.
@RequiredArgsConstructor
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    //JWTUtil 주입
    private final JWTUtil jwtUtil;

    private final TokenService tokenService;

    // JWT 만료 시간 Long 형
    @Value("${jwt.access-expiration}")
    private long accessExpiration; // 1시간

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration; // 24시간

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        //클라이언트 요청에서 username, password 추출
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        //인증 진행
        //스프링 시큐리티에서 username과 password를 검증하기 위해서는 token(바구니)에 담아야 함
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        //token에 담은 검증을 위한 AuthenticationManager로 전달 (AuthenticationManager에서 검증 진행)
        return authenticationManager.authenticate(authToken);
    }

    //로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        log.info("Login success");

        //유저 정보
        //username 가져오기
        String username = authentication.getName();
        //role 가져오기 (동작)
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        //role 가져옴
        String role = auth.getAuthority();

        //토큰 생성 (카테고리, 유저이름, 역할, 만료시간)
        String access = jwtUtil.createJwt(TokenType.ACCESS.name(), username, role, accessExpiration); //60분
        String refresh = jwtUtil.createJwt(TokenType.REFRESH.name(), username, role, refreshExpiration); //24시간

        //Refresh 토큰 저장
        String deviceId = request.getHeader("Device-ID");
        tokenService.createRefreshToken(username, refresh, refreshExpiration, deviceId);

        //응답 설정
        //access 토큰 헤더에 넣어서 응답 (key: value 형태) -> 예시) access: 인증토큰(string)
        response.setHeader(TokenType.ACCESS.name(), access);
        //refresh 토큰 쿠키에 넣어서 응답
        response.addCookie(CookieUtil.createCookie(TokenType.REFRESH.name(), refresh));
        response.setStatus(HttpStatus.OK.value());
    }

    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        log.info("Login fail");

        //로그인 실패시 401 응답 코드 반환
        response.setStatus(401);
    }

}