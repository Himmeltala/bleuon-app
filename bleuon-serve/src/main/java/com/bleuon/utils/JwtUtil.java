package com.bleuon.utils;

import com.bleuon.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

public class JwtUtil {

    private static final long EXPIRE_SECONDS = 86400000 * 7; // 24 小时，单位毫秒
    private static final String KEY = "cereshuzhitingnizhenbangcereshuzhitingnizhenbang";

    public static Long getExpire() {
        return new Date(System.currentTimeMillis() + EXPIRE_SECONDS).getTime();
    }

    private static SecretKey generateKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(KEY));
    }

    public static String createJwt(UserDetails details, String jwtUuid, Long expire) {
        JwtBuilder builder = Jwts.builder();
        return builder
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .claim("username", details.getUsername())
                .setSubject("bleuon")
                .setExpiration(new Date(expire))
                .setIssuedAt(new Date())
                .setId(jwtUuid)
                .signWith(generateKey())
                .compact();
    }

    public static String createJwt(User user, String jwtUuid, Long expire) {
        JwtBuilder builder = Jwts.builder();
        return builder
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .claim("username", user.getUsername())
                .setSubject("bleuon")
                .setExpiration(new Date(expire))
                .setIssuedAt(new Date())
                .setId(jwtUuid)
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
            return claimsJws.getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public static UserDetails toUserDetails(Claims claims, List<String> authorities) {
        String username = (String) claims.get("username");

        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password("******")
                // 将数组转换为字符串数组，new String[0]，告诉 toArray 创建大小与 ArrayList 相同大小的字符串数组
                .authorities(authorities.toArray(new String[0]))
                .build();
    }

}
