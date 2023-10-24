package com.bleuon.utils;

import com.bleuon.entity.CustomUserDetails;
import com.bleuon.entity.TokenModel;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Data
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final RedisTemplate<String, String> redisTemplate;
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

    public TokenModel grant(CustomUserDetails details) {
        String jwtUuid = UUID.randomUUID().toString();
        Long expire = getExpire();
        String value = createJwt(details, jwtUuid, expire);
        redisTemplate.opsForValue().set(jwtUuid, value, expire, TimeUnit.SECONDS);
        return new TokenModel(expire, value, details.getUsername(), details.getId(), details.getType());
    }

}
