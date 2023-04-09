package com.ouroboros.springbootpractice.sharding.algorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.Collection;

@Configuration
public class ShardingTableHashModAlgorithm implements PreciseShardingAlgorithm<String> {


    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        String routeKey = shardingValue.getValue();
        if (availableTargetNames.size() > 1 && !StringUtils.hasText(routeKey)) {
            throw new RuntimeException("无主键");
        }
        String logicTableName = shardingValue.getLogicTableName();
        return getRouteTableName(routeKey, logicTableName, availableTargetNames.size());
    }

    public static String getRouteTableName(String routeKey, String logicTableName, int availableSize) {
        Integer shardingIndex = Math.toIntExact((Math.abs(ShardingConsistentHashAlgorithm.hashStringToLong(routeKey)) % availableSize));
        return String.format("%s_%s", logicTableName, shardingIndex);
    }
}