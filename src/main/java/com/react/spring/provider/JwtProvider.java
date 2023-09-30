package com.react.spring.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${secret-key}")
    private String secretKey;

    // jwt 생성
    public String create(String email){

        Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS)); // 1시간

        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.ES256, secretKey)
                .setSubject(email) // email을 (id를 email로 설정함)
                .setIssuedAt(new Date()) // 생성시간
                .setExpiration(expiredDate) // 만료시간
                .compact(); //압축

        return jwt;
    }

    // jwt 검증 (반환되는 것은 email)
    public String validate(String jwt){

        Claims claims = null;

        try {
            claims = Jwts.parser().setSigningKey(secretKey)
                    .parseClaimsJws(jwt).getBody();

        } catch (Exception exception){
            exception.printStackTrace();
            return null;
        }
        return claims.getSubject();
    }
}
