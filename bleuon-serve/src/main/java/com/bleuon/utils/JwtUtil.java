package com.bleuon.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.*;

public class JwtUtil {

    private static final long EXPIRATION_TIME = 86400000 * 7; // 24 小时，单位毫秒
    private static final String KEY = "cereshuzhitingnizhenbangcereshuzhitingnizhenbang";

    public static Long getExpire() {
        return new Date(System.currentTimeMillis() + EXPIRATION_TIME).getTime();
    }

    private static SecretKey generateKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(KEY));
    }

    public static String createJwt(UserDetails details, String jwtUuid, Long expire) {
        JwtBuilder builder = Jwts.builder();
        return builder
                // header
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                // payload
                .claim("username", details.getUsername())
                // jwt 主题
                .setSubject("bleuon")
                // 过期时间
                .setExpiration(new Date(expire))
                // 颁发时间
                .setIssuedAt(new Date())
                // jwt id
                .setId(jwtUuid)
                // signature
                .signWith(generateKey())
                .compact();
    }

    public static Claims parseJwt(String header) {
        if (header == null || !header.startsWith("Bearer ")) return null;
        String token = header.substring(7);
        try {
            JwtParser parser = Jwts
                    .parserBuilder()
                    .setSigningKey(generateKey())
                    .build();
            Jws<Claims> claimsJws = parser.parseClaimsJws(token);
            // Claims claims = claimsJws.getBody();
            // Date issuedAt = claims.getIssuedAt();
            // return new Date().after(issuedAt) ? null : claims;
            return claimsJws.getBody();
        } catch (ExpiredJwtException e) {
            return null;
        }
    }

    public static UserDetails toUserDetails(Claims claims, List<String> authorities) {
        String username = (String) claims.get("username");

        return User
                .withUsername(username)
                .password("******")
                // 将数组转换为字符串数组，new String[0]，告诉 toArray 创建大小与 ArrayList 相同大小的字符串数组
                .authorities(authorities.toArray(new String[0]))
                .build();
    }

}
