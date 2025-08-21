package com.jobspring.jobspring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String TOKEN_KEY_PREFIX = "activation_token:";

    public void save(String token, Long id){
        redisTemplate.opsForValue().set(TOKEN_KEY_PREFIX+token, id, Duration.ofMinutes(2));
    }

    public Long findIdByToken(String token){
        Object id = redisTemplate.opsForValue().get(TOKEN_KEY_PREFIX+token);
        if (id instanceof Integer) {
            return ((Integer) id).longValue();
        }
        return (Long) id;
    }

    public boolean existsByToken(String token){
        return redisTemplate.hasKey(TOKEN_KEY_PREFIX + token);
    }

    public void deleteToken(String token){
        redisTemplate.delete(TOKEN_KEY_PREFIX+token);
    }
}

