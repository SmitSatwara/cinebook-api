package com.smitsatwara.cinebook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String,String> redisTemplate;
    public void lockSeat(Long showId,Long seatId){
        String key = getRedisKey(showId, seatId);
        redisTemplate.opsForValue().set(key,"LOCKED",10, TimeUnit.MINUTES);
    }
    public void unlockSeat(Long showId,Long seatId){
        String key = getRedisKey(showId, seatId);
        redisTemplate.delete(key);
    }
    public boolean isSeatLocked(Long showId,Long seatId){
        String key = getRedisKey(showId, seatId);
        Boolean result =  redisTemplate.hasKey(key);
        return result!=null && result;
    }


    private String getRedisKey(Long showId, Long seatId) {
        return "seat:lock:" + showId + ":" + seatId;
    }
}


