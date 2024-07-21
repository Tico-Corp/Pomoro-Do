package com.tico.pomoro_do.global.auth.jwt;

import com.google.gson.Gson;
import com.tico.pomoro_do.domain.user.dto.UserDTO;
import com.tico.pomoro_do.global.auth.CustomUserDetails;
import com.tico.pomoro_do.global.code.ErrorCode;
import com.tico.pomoro_do.global.exception.ErrorResponseEntity;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

//OncePerRequestFilter: 요청에 대해 한번만 동작
@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    //토큰 검증
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //필터 위치에 따라 OAuth2 인증을 진행하는 필터보다 JWTFilter가 앞에 존재하는 경우 에러 발생
        String requestUri = request.getRequestURI();
        //JWTFilter 내부에 if문을 통해 특정 경로 요청은 넘어가도록 수정
        if (requestUri.matches("^\\/api/auth/google/login(?:\\/.*)?$")) {

            filterChain.doFilter(request, response);
            return;
        }
        if (requestUri.matches("^\\/api/auth/google/join(?:\\/.*)?$")) {

            filterChain.doFilter(request, response);
            return;
        }
        if (requestUri.matches("^\\/token/reissue(?:\\/.*)?$")) {

            filterChain.doFilter(request, response);
            return;
        }
        //관리자
        if (requestUri.matches("^\\/api/admin/login(?:\\/.*)?$")) {

            filterChain.doFilter(request, response);
            return;
        }
        if (requestUri.matches("^\\/api/admin/join(?:\\/.*)?$")) {

            filterChain.doFilter(request, response);
            return;
        }

        /** Access 토큰 필터 시작 **/
        log.info("Access 토큰 검증 시작");
        //request에서 Authorization 헤더를 찾음
        //헤더에서 Authorization키에 담긴 토큰을 꺼냄
        String authorization= request.getHeader("Authorization");

        //null 값 검증 시작

        //Authorization 헤더 검증
        //Authorization 헤더가 없거나, Bearer 접두사가 없는 경우 다음 필터로 넘김
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.info("토큰이 없습니다.");
            // 권한이 필요없는 요청도 있기 때문에 doFilter로 넘긴다.
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }
        //null 검증 성공
        log.info("토큰이 존재합니다.");

        //소멸 시간 검증 시작

        //Bearer 부분 제거 후 순수 토큰만 획득
        String accessToken = authorization.split(" ")[1];
//        String accessToken = authorization.substring(7);

        //토큰이 있다면
        //토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
        try {
            // 토큰이 만료되었는 지 확인
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {
            //Access 토큰이 만료된 경우
            log.info("Access 토큰이 만료되었습니다.");

            //만료되었으면 filterChain.doFilter로 넘기지 않는다.
            //특정한 상태 코드와 토큰이 만료되었다는 메시지를 함께 응답을 보낸다.

            // 프론트 측과 약속된 응답을 보내야한다.
            // -> 토큰 만료시 refesh토큰으로 access토큰을 재발급해야하기 때문이다.

            //response Error
            sendErrorResponse(response, ErrorCode.ACCESS_TOKEN_EXPIRED);
            return;
        }

        //카테고리 확인
        //토큰이 access인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(accessToken);

        if (!category.equals("access")) {
            //access 토큰이 아니면 응답 메시지와 상태코드 반환
            log.info("유효하지 않은 Access 토큰입니다. Access 토큰이 아닙니다.");

            //response Error
            sendErrorResponse(response, ErrorCode.INVALID_ACCESS_TOKEN);
            return;
        }

        //토큰 검증 완료
        log.info("Access 토큰 검증 완료");

        //일시적인 세션 만들기
        // 토큰에서 사용자 정보 획득
        String username = jwtUtil.getUsername(accessToken);
        String role = jwtUtil.getRole(accessToken);

        /* 서버 자체 로그인 삭제 - START */
        //account를 생성하여 값 저장 (추후 DTO를 만들어 값 저장)
//        Account account = Account.builder()
//                .username(username)
//                .password("temppassword")
//                .role(role)
//                .build();
        // 같이 초기화를 진행해야하는데 비밀번호 값은 jwt 토큰에 없다.
        // 매번 요청이 올 때 마다 DB를 조회해야하므로 불필요한 상황이 생긴다.
        // 임시적으로 만들어 넣는다. -> SecurityContextHolder에 정확한 비밀번호가 필요없다.

        //UserDetails에 회원 정보 객체 담기
//        CustomUserDetails customUserDetails = new CustomUserDetails(account);
        /* 서버 자체 로그인 삭제 - END */

        // DTO로 사용자 정보 저장
        UserDTO userDTO = UserDTO.builder()
                .username(username)
                .role(role)
                .build();

        //UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(userDTO);

        //스프링 시큐리티 로그인 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        //세션에 사용자 등록 (일시적으로 저장) -> 특정 경로에 접근 가능
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        //response body
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(errorCode.getHttpStatus().value())
//                .name(errorCode.name())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        //response status code
        //401 응답 코드
        PrintWriter writer = response.getWriter();
        //writer.print("invalid access token");
        writer.print(new Gson().toJson(errorResponse));
    }
}