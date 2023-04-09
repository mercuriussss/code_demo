package com.ouroboros.springbootpractice.sharding.algorithm;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import java.util.SortedMap;

public class ShardingConsistentHashAlgorithm {
    public ShardingConsistentHashAlgorithm() {
    }

    public static String getShardingDatasource(SortedMap<Integer, String> virtualToRealMap, String shardingKey) {
        int hashValue = hash(shardingKey);
        SortedMap<Integer, String> subVirtualToRealMap = virtualToRealMap.tailMap(hashValue);
        return subVirtualToRealMap.isEmpty() ? (String)virtualToRealMap.get(virtualToRealMap.firstKey()) : (String)subVirtualToRealMap.get(subVirtualToRealMap.firstKey());
    }

    public static Long getShardingTable(Integer shardingTotal, String shardingKey) {
        return Math.abs(hashStringToLong(shardingKey)) % (long)shardingTotal;
    }

    public static Long hashStringToLong(String key) {
        ByteBuffer buf = ByteBuffer.wrap(key.getBytes());
        int seed = 305441741;
        ByteOrder byteOrder = buf.order();
        buf.order(ByteOrder.LITTLE_ENDIAN);
        long m = -4132994306676758123L;
        int r = 47;

        long h;
        for(h = (long)seed ^ (long)buf.remaining() * m; buf.remaining() >= 8; h *= m) {
            long k = buf.getLong();
            k *= m;
            k ^= k >>> r;
            k *= m;
            h ^= k;
        }

        if (buf.remaining() > 0) {
            ByteBuffer finish = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
            finish.put(buf).rewind();
            h ^= finish.getLong();
            h *= m;
        }

        h ^= h >>> r;
        h *= m;
        h ^= h >>> r;
        buf.order(byteOrder);
        return h;
    }

    public static int hash(String key) {
        ByteBuffer buf = ByteBuffer.wrap(key.getBytes());
        int seed = 305441741;
        ByteOrder byteOrder = buf.order();
        buf.order(ByteOrder.LITTLE_ENDIAN);
        int m = 1540483477;
        byte r = 24;

        int h;
        int k;
        for(h = seed ^ buf.remaining(); buf.remaining() >= 4; h ^= k) {
            k = buf.getInt();
            k *= m;
            k ^= k >>> r;
            k *= m;
            h *= m;
        }

        if (buf.remaining() > 0) {
            ByteBuffer finish = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
            finish.put(buf).rewind();
            h ^= finish.getInt();
            h *= m;
        }

        h ^= h >>> 13;
        h *= m;
        h ^= h >>> 15;
        buf.order(byteOrder);
        if (h < 0) {
            h = Math.abs(h);
        }

        return h;
    }

    public static void init(SortedMap<Integer, String> virtualToRealMap, List<String> realNodes) {
        realNodes.forEach((node) -> {
            virtualToRealMap.put(hash(node), node);
            int count = 0;
            int i = 0;
            int virtualNum = realNodes.size();

            while(count < virtualNum) {
                ++i;
                String virtualNode = node + "#" + i;
                int hashValue = hash(virtualNode);
                if (!virtualToRealMap.containsKey(hashValue)) {
                    virtualToRealMap.put(hashValue, node);
                    ++count;
                }
            }

        });
    }
}
