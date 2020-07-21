package com.example.rainboot.common.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 缓存
 *
 * @author 小熊
 * @version 1.0
 * @Created 2018-12-24
 */
@Component
public class SingleCacheUtil {
    /**
     * 缓存
     */
    private static final Map<String, Object> CACHE = new HashMap<>();

    private SingleCacheUtil() {
    }

    /**
     * 设置缓存
     *
     * @param key   缓存key
     * @param value 缓存值
     */
    public static void setCache(String key, Object value) {
        CACHE.put(key, value);
    }

    /**
     * 取出缓存
     *
     * @param key 缓存key
     * @return 缓存值
     */
    public static Object getCache(String key) {
        return CACHE.get(key);
    }

    /**
     * 清空缓存
     */
    public static void clearCache() {
        CACHE.clear();
    }

    /**
     * 删除指定缓存
     *
     * @param key 缓存key
     */
    public static void removeCache(String key) {
        CACHE.remove(key);
    }
}