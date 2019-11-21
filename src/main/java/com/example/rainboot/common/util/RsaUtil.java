package com.example.rainboot.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA加密解密工具类
 *
 * @author wzh
 * @version 2019-11-19
 **/
@Slf4j
public class RsaUtil {
    /**
     * 块加密大小
     */
    private static final int CACHE_SIZE = 4096;

    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 签名算法
     * RSA2	SHA256WithRSA	强制要求 RSA 密钥的长度至少为 2048
     * RSA	SHA1WithRSA	对 RSA 密钥的长度不限制，推荐使用 2048 位以上
     */
    public static final String SIGNATURE_ALGORITHM = "SHA512WithRSA";

    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RsaPublicKey";

    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RsaPrivateKey";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 501;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = CACHE_SIZE / 8;

    /**
     * Base64字符串解码为二进制数据
     *
     * @param base64
     * @return 二进制数据
     */
    public static byte[] decodeBase64(String base64) {
        return Base64.decodeBase64(base64.getBytes());
    }

    /**
     * 二进制数据编码为Base64字符串
     *
     * @param bytes
     * @return Base64字符串
     */
    public static String encodeBase64(byte[] bytes) {
        return new String(Base64.encodeBase64(bytes));
    }

    /**
     * 生成秘钥对
     *
     * @return 返回公钥和私钥的Map集合
     * @throws Exception
     */
    public static Map<String, Object> initKeyPair()
            throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(CACHE_SIZE);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String, Object> keyMap = new HashMap<>(2);
        // 公钥
        keyMap.put(PUBLIC_KEY, publicKey);
        // 私钥
        keyMap.put(PRIVATE_KEY, privateKey);

        return keyMap;
    }

    /**
     * 获取私钥
     *
     * @param keyMap 秘钥对Map
     * @return 私钥字符串
     */
    public static String getPrivateKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return encodeBase64(key.getEncoded());
    }

    /**
     * 获取公钥字符串
     *
     * @param keyMap 秘钥对Map
     * @return 公钥字符串
     */
    public static String getPublicKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return encodeBase64(key.getEncoded());
    }

    /**
     * 使用私钥生成数字签名
     *
     * @param data       使用私钥加密的数据
     * @param privateKey 私钥
     * @return 数字签名
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        // 获取byte数组
        byte[] keyBytes = decodeBase64(privateKey);
        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        // 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取私钥匙对象
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return encodeBase64(signature.sign());
    }

    /**
     * 校验数字签名
     *
     * @param data      私钥加密的数据
     * @param publicKey 公钥字符串
     * @param sign      私钥生成的签名
     * @return 校验成功返回true 失败返回false
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        // 获取byte数组
        byte[] keyBytes = decodeBase64(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        // 构造X509EncodedKeySpec对象
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 指定的加密算法
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        // 取公钥匙对象
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        signature.initVerify(publicK);
        signature.update(data);

        // 验证签名是否正常
        return signature.verify(decodeBase64(sign));
    }

    /**
     * 私钥加密
     *
     * @param data       需要加密的数据
     * @param privateKey 私钥
     * @return 加密后的数据
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * 公钥加密
     *
     * @param data      需要加密的数据
     * @param publicKey 公钥字符串
     * @return 加密后的数据
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
        byte[] keyBytes = decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * 私钥解密
     *
     * @param encryptedData 公钥加密的数据
     * @param privateKey    私钥字符串
     * @return 私钥解密的数据
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
        byte[] keyBytes = decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * 公钥解密
     *
     * @param encryptedData 私钥加密的数据
     * @param publicKey     公钥字符串
     * @return 公钥解密的数据
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws Exception {
        byte[] keyBytes = decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * 公钥加密方法
     *
     * @param data      需加密的字符串
     * @param PUBLICKEY 公钥字符串
     * @return 加密后的字符串
     */
    public static String encryptedDataByPublic(String data, String PUBLICKEY) {
        try {
            data = encodeBase64(encryptByPublicKey(data.getBytes(), PUBLICKEY));
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return data;
    }

    /**
     * 私钥解密方法
     *
     * @param data       公钥加密的字符串
     * @param PRIVATEKEY 私钥字符串
     * @return 私钥解密的字符串
     */
    public static String decryptDataByPrivate(String data, String PRIVATEKEY) {
        String temp = "";
        try {
            byte[] rs = decodeBase64(data);
            //以utf-8的方式生成字符串
            temp = new String(decryptByPrivateKey(rs, PRIVATEKEY), StandardCharsets.UTF_8);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    public static void main(String[] args) {
        try {
            Map<String, Object> keyMap = RsaUtil.initKeyPair();
            String publicKey = RsaUtil.getPublicKey(keyMap);
            String privateKey = RsaUtil.getPrivateKey(keyMap);
            System.out.println("公钥：" + publicKey);
            System.out.println("私钥：" + privateKey);
            String source = "我是需要私钥加密的字符串！";
            System.out.println("签名验证逻辑，私钥加密--公钥解密，需要加密的字符串：" + source);
            byte[] data = source.getBytes();
            byte[] encodedData = RsaUtil.encryptByPrivateKey(data, privateKey);
            System.out.println("私钥加密后：" + new String(encodedData));
            String sign = RsaUtil.sign(encodedData, privateKey);
            System.out.println("签名:" + sign);
            boolean status = RsaUtil.verify(encodedData, publicKey, sign);
            System.out.println("验证结果:" + status);
            byte[] decodedData = RsaUtil.decryptByPublicKey(encodedData, publicKey);
            String target = new String(decodedData);
            System.out.println("公钥解密私钥加密的数据:" + target);

            System.out.println("---------公钥加密----私钥解密----------");
            // 这里尽量长一点，复制了一段歌词
            String msg = "月溅星河，长路漫漫，风烟残尽，独影阑珊；谁叫我身手不凡，谁让我爱恨两难，到后来，" +
                    "肝肠寸断。幻世当空，恩怨休怀，舍悟离迷，六尘不改；且怒且悲且狂哉，是人是鬼是妖怪，不过是，" +
                    "心有魔债。叫一声佛祖，回头无岸，跪一人为师，生死无关；善恶浮世真假界,尘缘散聚不分明，难断！" +
                    "我要这铁棒有何用，我有这变化又如何；还是不安，还是氐惆，金箍当头，欲说还休。我要这铁棒醉舞魔，" +
                    "我有这变化乱迷浊；踏碎灵霄，放肆桀骜，世恶道险，终究难逃。";
            String ecodeMsg = RsaUtil.encryptedDataByPublic(msg, publicKey);
            System.out.println("加密后的歌词：" + ecodeMsg);
            String decodeMsg = RsaUtil.decryptDataByPrivate(ecodeMsg, privateKey);
            System.out.println("解密后的歌词：" + decodeMsg);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
