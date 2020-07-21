package com.example.rainboot.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 数组模糊查询工具类
 *
 * @author 小熊
 * @version 1.0
 */
public class FuzzyQueryUtil {

    /**
     * 整数数组对值模糊查询
     *
     * @param array
     * @param query
     * @return
     */
    public static int[] arrayFuzzyQuery(int[] array, String query) {
        List<Integer> intList = new ArrayList<Integer>();
        for (int anArray : array) {
            String str = Integer.toString(anArray);
            if (str.contains(query)) {
                intList.add(Integer.parseInt(str));
            }
        }
        int[] intArray = new int[intList.size()];
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = intList.get(i);
        }
        return intArray;
    }

    /**
     * 双精度数组对值模糊查询
     *
     * @param array
     * @param query
     * @return
     */
    public static double[] arrayFuzzyQuery(double[] array, String query) {
        List<Double> doubleList = new ArrayList<Double>();
        for (double anArray : array) {
            String str = Double.toString(anArray);
            if (str.contains(query)) {
                doubleList.add(Double.parseDouble(str));
            }
        }
        double[] doubleArray = new double[doubleList.size()];
        for (int i = 0; i < doubleArray.length; i++) {
            doubleArray[i] = doubleList.get(i);
        }
        return doubleArray;
    }

    /**
     * 单精度数组对值模糊查询
     *
     * @param array
     * @param query
     * @return
     */
    public static float[] arrayFuzzyQuery(float[] array, String query) {
        List<Float> floatList = new ArrayList<Float>();
        for (float anArray : array) {
            String str = Float.toString(anArray);
            if (str.contains(query)) {
                floatList.add(Float.parseFloat(str));
            }
        }
        float[] floatArray = new float[floatList.size()];
        for (int i = 0; i < floatArray.length; i++) {
            floatArray[i] = floatList.get(i);
        }
        return floatArray;
    }

    /**
     * 字符串数组对值模糊查询
     *
     * @param array
     * @param query
     * @return
     */
    public static String[] arrayFuzzyQuery(String[] array, String query) {
        List<String> stringList = new ArrayList<String>();
        for (String str : array) {
            if (str.contains(query)) {
                stringList.add(str);
            }
        }
        String[] stringArray = new String[stringList.size()];
        for (int i = 0; i < stringArray.length; i++) {
            stringArray[i] = stringList.get(i);
        }
        return stringArray;
    }

    /**
     * 短整型数组值模糊查询
     *
     * @param array
     * @param query
     * @return
     */
    public static short[] arrayFuzzyQuery(short[] array, String query) {
        List<Short> shortList = new ArrayList<Short>();
        for (short anArray : array) {
            String str = Short.toString(anArray);
            if (str.contains(query)) {
                shortList.add(Short.parseShort(str));
            }
        }
        short[] shortArray = new short[shortList.size()];
        for (int i = 0; i < shortArray.length; i++) {
            shortArray[i] = shortList.get(i);
        }
        return shortArray;
    }

    /**
     * 长整型数组值模糊查询
     *
     * @param array
     * @param query
     * @return
     */
    public static long[] arrayFuzzyQuery(long[] array, String query) {
        List<Long> longList = new ArrayList<Long>();
        for (long anArray : array) {
            String str = Long.toString(anArray);
            if (str.contains(query)) {
                longList.add(Long.parseLong(str));
            }
        }
        long[] longArray = new long[longList.size()];
        for (int i = 0; i < longArray.length; i++) {
            longArray[i] = longList.get(i);
        }
        return longArray;
    }

    /**
     * 字节型数组值模糊查询
     *
     * @param array
     * @param query
     * @return
     */
    public static byte[] arrayFuzzyQuery(byte[] array, String query) {
        List<Byte> byteList = new ArrayList<Byte>();
        for (byte anArray : array) {
            String str = Long.toString(anArray);
            if (str.indexOf(query) > -1) {
                byteList.add(Byte.parseByte(str));
            }
        }
        byte[] byteArrya = new byte[byteList.size()];
        for (int i = 0; i < byteArrya.length; i++) {
            byteArrya[i] = byteList.get(i);
        }
        return byteArrya;
    }
}
