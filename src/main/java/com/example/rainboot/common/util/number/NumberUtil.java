package com.example.rainboot.common.util.number;

import java.math.BigDecimal;

/**
 * 数据操作
 *
 * @Author 小熊
 * @Created 2019-08-05
 */
public class NumberUtil {

    /**
     * int类型转long类型
     *
     * @param value 数据
     * @return long类型数据
     */
    public static Long intToLong(Integer value) {
        if (isNull(value)) {
            return null;
        }
        return ((Number) value).longValue();
    }

    /**
     * long类型转int类型
     *
     * @param value 数据
     * @return int类型数据
     */
    public static Integer longToInt(Long value) {
        if (isNull(value)) {
            return null;
        }
        return ((Number) value).intValue();
    }

    /**
     * float转double
     *
     * @param value 数据
     * @return Float类型数据
     */
    public static Double floatToDouble(Float value) {
        if (isNull(value)) {
            return null;
        }
        return new BigDecimal(String.valueOf(value)).doubleValue();
    }

    /**
     * double转float
     *
     * @param value 数据
     * @return Float类型数据
     */
    public static Float doubleToFloat(Double value) {
        if (isNull(value)) {
            return null;
        }
        return new BigDecimal(String.valueOf(value)).floatValue();
    }

    /**
     * 数据转string
     *
     * @param value 数据
     * @return String类型数据
     */
    public static String numberToString(Number value) {
        return String.valueOf(value);
    }

    /**
     * double数据计算，除法只保存6位
     *
     * @param val         前置数据
     * @param val2        后置数据
     * @param computeEnum 计算类型
     * @return 计算结果
     */
    public static Double compute(Double val, Double val2, ComputeEnum computeEnum) {
        if (val == null || val2 == null) {
            return null;
        }
        BigDecimal value = new BigDecimal(String.valueOf(val));
        BigDecimal value2 = new BigDecimal(String.valueOf(val2));
        switch (computeEnum) {
            case ADD:
                return value.add(value2).doubleValue();
            case DIVIDE:
                return value.divide(value2, 6).doubleValue();
            case MULTIPLY:
                return value.multiply(value2).doubleValue();
            case SUBTRACT:
                return value.subtract(value2).doubleValue();
            default:
                return null;
        }
    }

    /**
     * float数据计算，除法只保存6位
     *
     * @param val         前置数据
     * @param val2        后置数据
     * @param computeEnum 计算类型
     * @return 计算结果
     */
    public static Float compute(Float val, Float val2, ComputeEnum computeEnum) {
        if (val == null || val2 == null) {
            return null;
        }
        BigDecimal value = new BigDecimal(String.valueOf(val));
        BigDecimal value2 = new BigDecimal(String.valueOf(val2));
        switch (computeEnum) {
            case ADD:
                return value.add(value2).floatValue();
            case DIVIDE:
                return value.divide(value2, 6).floatValue();
            case MULTIPLY:
                return value.multiply(value2).floatValue();
            case SUBTRACT:
                return value.subtract(value2).floatValue();
            default:
                return null;
        }
    }

    /**
     * 判读数字是否为空, 是否等于0
     *
     * @return 数字是否为空, 是否等于0
     */
    public static boolean isNotNullAndNotZero(Long value) {
        return value != null && 0 != value;
    }

    public static boolean isNotNull(Number number) {
        return number != null;
    }

    public static boolean isNull(Number number) {
        return number == null;
    }

}
