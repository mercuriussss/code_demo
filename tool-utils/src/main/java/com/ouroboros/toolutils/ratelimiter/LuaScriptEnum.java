package com.ouroboros.toolutils.ratelimiter;


public enum LuaScriptEnum {
    TOKEN_BUCKET_RATE_LIMITER("script/tokenBucketRateLimiter.lua"),
    GIVE_BACK_TOKEN_TO_BUCKET("script/giveBackTokenToBucket.lua");

    private final String scriptRef;

    LuaScriptEnum(String scriptRef) {
        this.scriptRef = scriptRef;
    }

    public String getScriptRef() {
        return scriptRef;
    }
}