package com.bleuon.utils;

import com.bleuon.entity.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Data
@Component
public class JwtUtil {

    @Value("${spring.security.token-expire}")
    private Long expire; // 24 小时，单位毫秒
    @Value("${spring.security.token-key}")
    private String key;

    public Long getExpire() {
        return new Date(System.currentTimeMillis() + expire).getTime();
    }

    private SecretKey generateKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
    }

    public String createJwt(CustomUserDetails details, String jwtUuid, Long expire) {
        JwtBuilder builder = Jwts.builder();
        return builder
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .claim("id", details.getId())
                .claim("username", details.getUsername())
                .claim("type", details.getType())
                .setExpiration(new Date(expire))
                .setIssuedAt(new Date())
                .setId(jwtUuid)
                .signWith(generateKey())
                .compact();
    }

    public Claims parseJwt(String header) {
        if (header == null || !header.startsWith("Bearer ")) return null;
        String token = header.substring(7);
        try {
            JwtParser parser = Jwts.parserBuilder().setSigningKey(generateKey()).build();
            Jws<Claims> claimsJws = parser.parseClaimsJws(token);
            return claimsJws.getBody();
        } catch (Exception e) {
            return null;
        }
    }

}
