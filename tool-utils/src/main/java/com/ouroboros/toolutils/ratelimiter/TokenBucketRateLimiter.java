package com.ouroboros.toolutils.ratelimiter;

import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.LongCodec;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class TokenBucketRateLimiter {

    private final RScript rScript;

    private static final String KEY_PREFIX = "rateLimiter:";

    public TokenBucketRateLimiter(RedissonClient client) {
        this.rScript = client.getScript(LongCodec.INSTANCE);
    }

    // 从令牌桶中获取令牌
    public Boolean acquireToken(RateLimiterRule rule) {
        List<Object> keys = Arrays.asList(getTokenKey(rule.getKey()), getTimestampKey(rule.getKey()));
        String script = LuaScriptUtil.getLuaScript(LuaScriptEnum.TOKEN_BUCKET_RATE_LIMITER);
        List<Long> results = rScript.eval(RScript.Mode.READ_WRITE, script, RScript.ReturnType.MULTI,
                keys, rule.getRate(), rule.getBucketCapacity(), rule.getRequestedTokens());

        return results.get(0) == 1L;
    }

    // 将令牌放回令牌桶中
    public void giveBackToken(RateLimiterRule rule) {
        List<Object> keys = Collections.singletonList(getTokenKey(rule.getKey()));
        String script = LuaScriptUtil.getLuaScript(LuaScriptEnum.GIVE_BACK_TOKEN_TO_BUCKET);
        rScript.eval(RScript.Mode.READ_WRITE, script, RScript.ReturnType.VALUE,
                keys, rule.getRate(), rule.getBucketCapacity(), rule.getGiveBackTokens());
    }

    private static String getTokenKey(String key) {
        return KEY_PREFIX + key + ":tokens";
    }

    private static String getTimestampKey(String key) {
        return KEY_PREFIX + key + ":timestamp";
    }
}