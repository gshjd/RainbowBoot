package com.example.rainboot.common.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

/**
 * bean 工具类
 *
 * @Author 小熊
 * @Created 2019-07-31
 */
public class BeanUtils {

    private static final BeanCopier FAIL_BACK = new BeanCopier() {
        @Override
        public void copy(Object o, Object o1, Converter converter) {
            throw new UnsupportedOperationException();
        }
    };

    private static ConcurrentMap<Class, ConcurrentMap<Class, BeanCopier>> beanCopierMap = new ConcurrentHashMap<>();

    /**
     * 复制属性, 会自动缓存以加快速度,建议使用传入Class的方法
     *
     * @param src
     * @param dest
     * @return 复制的目标对象,注入如果src为null,则这里会返回null
     */
    public static Object copy(Object src, Object dest) {
        if (src == null) {
            return null;
        }
        if (dest == null) {
            throw new NullPointerException("dest is null");
        }
        ConcurrentMap<Class, BeanCopier> innerMap = beanCopierMap.get(src.getClass());
        if (innerMap == null) {
            innerMap = new ConcurrentHashMap<>(16);
            ConcurrentMap<Class, BeanCopier> temp = beanCopierMap.putIfAbsent(src.getClass(), innerMap);
            if (temp != null) {
                innerMap = temp;
            }
        }
        BeanCopier beanCopier = innerMap.get(dest.getClass());
        if (beanCopier == null) {
            if(src.getClass().getClassLoader() != dest.getClass().getClassLoader()){
                //cglib的复制器不支持复制两个classloader不同的类（其实如果src的classloader是dest的classloader的父亲，是可以支持的，这里简化处理，只判断是不是相同）
                beanCopier = FAIL_BACK;
            }else {
                beanCopier = BeanCopier.create(src.getClass(), dest.getClass(), false);
            }
            BeanCopier temp = innerMap.putIfAbsent(dest.getClass(), beanCopier);
            if (temp != null) {
                beanCopier = temp;
            }
        }
        if(beanCopier == FAIL_BACK){
            //当两个类的类加载器不同时，降级为使用spring BeanUtils来复制属性，这个的性能会差很多。
            org.springframework.beans.BeanUtils.copyProperties(src, dest);
        }else {
            beanCopier.copy(src, dest, null);
        }

        return dest;
    }

    /**
     * 复制属性, 会自动缓存以加快速度
     *
     * @param src
     * @param destClass 目标类,要求该类必须有无参构造函数
     * @param <T>
     * @return
     */
    private static <T> T copy(Object src, Class<T> destClass) {
        if (src == null) {
            return null;
        }
        try {
            T dest = destClass.newInstance();
            copy(src, dest);
            return dest;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 把list中的每个对象都转换为目标类的对象
     *
     * @param srcList
     * @param destClass
     * @param <T>
     * @return
     */
    public static <T> List<T> copyList(List<?> srcList, Class<T> destClass) {
        if (srcList == null) {
            return Collections.emptyList();
        }
        List<T> retList = new ArrayList<>();
        for (Object src : srcList) {
            T destInstance = copy(src, destClass);
            retList.add(destInstance);
        }
        return retList;
    }

    public static Map<String, Object> transBeanToMap(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!"class".equals(key)) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return map;
    }

}
