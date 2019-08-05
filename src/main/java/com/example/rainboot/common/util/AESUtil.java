package com.example.rainboot.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @version 1.0
 * @Author 小熊
 * @Created 14:46 2018/5/29
 * @Remark
 */
@Slf4j
public class AESUtil {

    /**
     * 加密
     *
     * @param content 需要加密的明文
     * @return 加密后的密文
     */
    public static String encode(String content) {
        Cipher cipher = getCipher(false);
        byte[] result = new byte[0];
        try {
            if (cipher != null) {
                result = cipher.doFinal(content.getBytes());
            }
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            log.error("加密失败：", e);
            return null;
        }
        return new Base64().encodeAsString(result);
    }

    /**
     * 解密
     *
     * @param content 需要解密的密文
     * @return 解密后的明文
     */
    public static String decode(String content) {
        Cipher cipher = getCipher(true);
        byte[] result = new byte[0];
        try {
            if (cipher != null) {
                result = cipher.doFinal(new Base64().decode(content));
            }
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            log.error("解密失败：", e);
            return null;
        }
        return new String(result);
    }

    /**
     * 加密初始化
     *
     * @param flag 加密(true) 解密(false)
     * @return 加密器初始化
     */
    private static Cipher getCipher(boolean flag) {
        // 生成密钥
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.getProvider();
            keyGenerator.init(256);
            IvParameterSpec iv = new IvParameterSpec("dzg86swqe51zcx8l".getBytes());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            // key转换
            SecretKeySpec key = new SecretKeySpec("b4417f99-f18c-4069-b708-78184565".getBytes(), "AES");
            if (flag) {
                cipher.init(Cipher.DECRYPT_MODE, key, iv);
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            }
            return cipher;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            log.error("加密初始化失败：", e);
        }
        return null;
    }

    /*public static void main(String[] args) {
        String en = encode("我是谁");
        String result = decode(

        en);
        System.err.println(en);
        System.err.println(result);
    }*/
}
