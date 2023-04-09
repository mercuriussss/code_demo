package com.ouroboros.springbootpractice.sharding.algorithm;


import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

@Configuration
public class ShardingSchemaHashModAlgorithm implements PreciseShardingAlgorithm<String> {

    public static final SortedMap<Integer, String> virtualToRealMap = new TreeMap<>();

    public static SortedMap<Integer, String> shardingMap = new TreeMap<>();

    /**
     * 初始化一致性，虚拟节点。据说Collection<String> availableTargetNames 这个获取的节点有初始化慢的问题，会导致数据有问题
     */
    static {
        ShardingConsistentHashAlgorithm.init(virtualToRealMap, List.of("sharding_jdbc_1", "sharding_jdbc_2", "sharding_jdbc_3"));
        shardingMap = new TreeMap<>(virtualToRealMap);
        // 定位节点，用真实数据源，然后shardng不支持下划线，必须转换为中线
        shardingMap.entrySet().forEach( e -> e.setValue(e.getValue().replaceAll("_", "-")));
    }


    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        String routeKey = shardingValue.getValue();
        if (availableTargetNames.size() > 1 && !StringUtils.hasText(routeKey)) {
            throw new RuntimeException("无主键");
        }
        return ShardingConsistentHashAlgorithm.getShardingDatasource(shardingMap, routeKey);
    }

    public static String getRouteSchemaName(String routeKey) {
        return ShardingConsistentHashAlgorithm.getShardingDatasource(virtualToRealMap, routeKey);
    }

    public static void main(String[] args) {
        System.out.println(ShardingConsistentHashAlgorithm.getShardingDatasource(virtualToRealMap, "user_table2222"));
    }
}