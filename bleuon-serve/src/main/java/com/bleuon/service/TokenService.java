package com.bleuon.service;

import com.bleuon.entity.CustomUserDetails;
import com.bleuon.entity.dto.TokenDTO;
import com.bleuon.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @description: 颁发 token，与 redis 交互
 * @package: com.bleuon.service
 * @author: zheng
 * @date: 2023/10/9
 */
@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, String> redisTemplate;

    public TokenDTO grant(CustomUserDetails details) {
        String jwtUuid = UUID.randomUUID().toString();
        Long expire = jwtUtil.getExpire();
        String value = jwtUtil.createJwt(details, jwtUuid, expire);
        redisTemplate.opsForValue().set(jwtUuid, value, expire, TimeUnit.SECONDS);
        return new TokenDTO(expire, value, details.getUsername(), details.getId(), details.getType());
    }

}
