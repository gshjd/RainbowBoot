package com.example.rainboot.system.util;

import lombok.extern.slf4j.Slf4j;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

/**
 * 获取分布式主键
 *
 * @version 1.0
 * @Author 小熊
 * @Created 2018-12-24
 */
@Slf4j
public class KeyUtil {

    private KeyUtil() {
    }

    public static Long getKey() {
        StringBuilder id = new StringBuilder(18);
        try {
            // 取当前服务器时间纳秒数
            id.append(System.nanoTime());
            // id所需长度
            int length = 18;
            // 已生成id现有长度
            int idLength = id.length();
            // 补足18位
            for (int i = 0; i < (length - idLength); i++) {
                id.append(SecureRandom.getInstance("SHA1PRNG", "SUN").nextInt(9));
            }
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            log.error("生成主键出错！", e);
            return -1L;
        }

        return Long.parseLong(id.toString());
    }
}