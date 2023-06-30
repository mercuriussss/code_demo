package com.ouroboros.toolutils.ratelimiter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RateLimiterRule {

    private String key;

    // 每秒令牌恢复的个数, 即多少个/每秒
    private double rate;

    // 令牌桶的容量
    private int bucketCapacity;

    // 此次请求需要获取的令牌数
    private int requestedTokens;

    // 此次请求需要获取的令牌数
    private int giveBackTokens;
}
