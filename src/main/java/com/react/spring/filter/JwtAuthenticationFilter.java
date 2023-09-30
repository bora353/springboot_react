package com.react.spring.filter;

import com.react.spring.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor // 필수 생성자
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String token = parseBearerToken(request);

            if (token == null){
                // 현재 필터가 아무 작업을 하지 않고 요청을 다음 필터로 전달하는 역할 (요청 계속 처리할 수 있도록)
                filterChain.doFilter(request, response);
                return;
            }

            String email = jwtProvider.validate(token);

            if (email == null){
                filterChain.doFilter(request, response);
                return;
            }

            AbstractAuthenticationToken abstractAuthenticationToken =
                    // (아이디, 비밀번호, 권한) 입력
                    new UsernamePasswordAuthenticationToken(email, null, AuthorityUtils.NO_AUTHORITIES);

            // 인증 요청에 대한 사용자 인증 요청에 대한 세부정보 설정
            abstractAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // context에 등록 (사용자의 인증 정보 관리 목적, 인증된 사용자의 컨텍스트 유지)
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext(); // 비어있는 context 만들고
            securityContext.setAuthentication(abstractAuthenticationToken); // 현재 사용자의 인증정보 등록

            // 사용자의 인증 정보 유지, 인증된 사용자로서 역할 수행
            SecurityContextHolder.setContext(securityContext);

        } catch (Exception exception){
            exception.printStackTrace();
        }

        // 다음 필터로 넘기기
        filterChain.doFilter(request, response);

    }


    private String parseBearerToken(HttpServletRequest request){

        String authorization = request.getHeader("Authorization");

        // hasText : null 이거나 길이 0 이거나 공백으로만 채워져 있으면 false
        boolean hasAuthorization = StringUtils.hasText(authorization);
        if(!hasAuthorization) return null;

        boolean isBearer = authorization.startsWith("Bearer ");
        if (!isBearer) return null;

        // Bearer 토큰인 경우에만
        String token = authorization.substring(7);
        return token;
    }
}
