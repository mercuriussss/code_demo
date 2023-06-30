package com.ouroboros.toolutils.ratelimiter;

public enum ApiEnum {

    AMAZON_CREATE_REPORT("amazonCreateReport", 0.0167, 10);

    // api对应的唯一标识key
    private final String key;

    // 令牌恢复的速率, 每秒恢复多少个令牌
    private final double ratePerSecond;

    // 令牌桶的最大容量
    private final int bucketCapacity;

    ApiEnum(String key, double ratePerSecond, int bucketCapacity) {
        this.key = key;
        this.ratePerSecond = ratePerSecond;
        this.bucketCapacity = bucketCapacity;
    }

    public String getKey() {
        return key;
    }

    public double getRatePerSecond() {
        return ratePerSecond;
    }

    public int getBucketCapacity() {
        return bucketCapacity;
    }
}
