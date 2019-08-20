package com.example.rainboot.common.util;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 测试工具类, 单元测试中很有用。
 *
 * @Author 小熊
 * @Created 2019-08-20
 */
public class TestUtil {
    private TestUtil() {
    }

    /**
     * 创建随机的bean,实例化一个类（要求该类拥有默认构造函数）,并赋予该类的各个变量以随机值.方便测试(该方法不会给父类中的属性赋值)
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T createRandomBean(Class<T> clazz) {
        return createRandomBean(clazz, false);
    }

    /**
     * 创建随机的bean,实例化一个类（要求该类拥有默认构造函数）,并赋予该类的各个变量以随机值.方便测试
     *
     * @param clazz
     * @param <T>
     * @param includeParents 父类中的变量是否赋值
     * @return
     */
    public static <T> T createRandomBean(Class<T> clazz, boolean includeParents) {
        T t;
        try {
            t = clazz.newInstance();

            setRandomAttributesForBean(t, includeParents);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return t;
    }

    /**
     * 给某个对象的属性设置随机值,方便测试
     *
     * @param t
     * @param includeParents
     * @throws IllegalAccessException
     */
    public static void setRandomAttributesForBean(Object t, boolean includeParents) {
        setRandomAttributesForBean(t, t.getClass(), includeParents, false);
    }

    private static boolean isIgnoreField(Field f) {
        //id不填充,防止导致AUTO_INCREMENT过大
        if ("id".equals(f.getName())) {
            return true;
        }

        if ((f.getModifiers() & Modifier.STATIC) == Modifier.STATIC || (f.getModifiers() & Modifier.FINAL) == Modifier.FINAL) {
            return true;
        }
        return false;
    }

    /**
     * @param t
     * @param clazz
     * @param includeParents
     * @param overwriteValuesNotNull 是否覆盖非null值
     * @throws IllegalAccessException
     */
    private static void setRandomAttributesForBean(Object t, Class<?> clazz, boolean includeParents, boolean overwriteValuesNotNull) {
        try {
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                if (isIgnoreField(f)) {
                    continue;
                }

                f.setAccessible(true);
                Object currentValue = f.get(t);
                if (!overwriteValuesNotNull && currentValue != null) {
                    continue;
                }
                setRandomAttributeForField(f, t);
            }
            if (includeParents) {
                Class parentClass = clazz.getSuperclass();
                if (parentClass != null) {
                    setRandomAttributesForBean(t, parentClass, includeParents, overwriteValuesNotNull);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void setRandomAttributeForField(Field f, Object t) throws IllegalAccessException {
        Class type = f.getType();
        if (type.equals(String.class)) {
            f.set(t, RandomStringUtils.random(3, true, true));
        } else if (type.equals(Integer.class) || type.equals(int.class)) {//防止关联其他表时填充id
            f.set(t, RandomUtils.nextInt(10000));
        } else if (type.equals(Long.class) || type.equals(long.class)) {
            f.set(t, (long) RandomUtils.nextInt(10000));
        } else if (type.equals(Byte.class) || type.equals(byte.class)) {
            f.set(t, (byte) RandomUtils.nextInt(50));
        } else if (type.equals(Short.class) || type.equals(short.class)) {
            f.set(t, (short) RandomUtils.nextInt(10000));
        } else if (type.equals(Date.class)) {
            f.set(t, new Date());
        } else if (type.equals(Boolean.class)) {
            f.set(t, RandomUtils.nextBoolean());
        }
    }

    /**
     * 比较两个对象是否相同（通过反射比较field),如果不相同则抛出异常,用于方便单元测试
     *
     * @param t1
     * @param t2
     * @param exceptFields   例外字段,这些字段不会被比较.
     * @param includeParents 是否比较父类中的属性
     * @param <T>
     * @return
     */
    public static <T> void assertEqualsReflect(T t1, T t2, boolean includeParents, String[] exceptFields) {
        if (t1 == null && t2 == null) {
            return;
        }
        if (t1 == null) {
            throw new Error("left parameter is null");
        }
        if (t2 == null) {
            throw new Error("right parameter is null");
        }
        Class c1 = t1.getClass();
        Class c2 = t2.getClass();
        if (!c1.equals(c2)) {
            throw new Error("class not equal:[" + c1.getName() + "],  [" + c2.getName() + "]");
        }

        Set<String> exceptFieldSet = new HashSet<>();
        if (exceptFields != null) {
            exceptFieldSet.addAll(Arrays.asList(exceptFields));
        }

        assertEqualsReflect(t1, t2, t1.getClass(), includeParents, exceptFieldSet);
    }

    private static <T> void assertEqualsReflect(T t1, T t2, Class<?> clazz, boolean includeParents, Set<String> exceptFieldSet) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            if (exceptFieldSet.contains(f.getName())) {
                continue;
            }

            try {
                f.setAccessible(true);
                Object fv1 = f.get(t1);
                Object fv2 = f.get(t2);
                if (!ObjectUtils.equals(fv1, fv2)) {
                    throw new Error("field:[" + f.getName() + "] not equals,left is:" + fv1 + ", right is:" + fv2);
                }
            } catch (IllegalAccessException e) {
                throw new Error(e);
            }
        }

        if (includeParents) {
            clazz = clazz.getSuperclass();
            if (clazz != null) {
                assertEqualsReflect(t1, t2, clazz, includeParents, exceptFieldSet);
            }
        }
    }

}
