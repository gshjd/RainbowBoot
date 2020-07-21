package com.example.rainboot.system.util;

import lombok.extern.slf4j.Slf4j;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

/**
 * 获取权重随机数工具类
 *
 * @author 小熊
 * @version 2.0
 */
@Slf4j
public class WeightedRandomUtil {

    /**
     * 获取权重数据随机索引值
     *
     * @param weighted 权重数组（权重从小到大）
     * @return index 传参的权重数组的索引坐标
     */
    public static int getWeightedRandom(int[] weighted) {
        // 权重总数
        int sum = 0;
        // 权重分配数
        int[] randomNum = new int[weighted.length];
        for (int i = 0; i < weighted.length; i++) {
            // 获取权重总数
            sum += weighted[i];
            // 获取权重分配
            randomNum[i] = sum - 1;
        }
        // 权重随机数
        int rand = 0;
        try {
            rand = SecureRandom.getInstance("SHA1PRNG", "SUN").nextInt(sum);
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            log.error("随机数生成失败：", e);
            return 0;
        }
        // 权重数组索引
        int index = 0;
        for (int i = 0; i < randomNum.length; i++) {
            if (randomNum[i] >= rand) {
                index = i;
                break;
            }
        }
        return index;
    }
	
/*	public static void main(String[] args) {
		int[] k = {3,97};
		int h=0,l=0;
		for(int i=0; i<100; i++) {
			if((h=getWeightedRandom(k)) == 1) {
				l++;
//				System.err.println(h);
			}
		}
		System.out.println("1号位置共出现：" + l + "次");
	}*/
}
