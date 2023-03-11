package com.yusa.acgnbbs.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yusa
 * @version 1.0
 */
public class BeanCopyUtils {
    private BeanCopyUtils(){}
    public static <V>V copyBean(Object source,Class<V> clazz){
        // 创建目标对象
        V o = null;
        try {
            o = clazz.newInstance();
            // 实现属性拷贝
            BeanUtils.copyProperties(source,o);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        // 返回结果
        return o;
    }
    public static <O,V> List<V> copyBeanList(List<O> list, Class<V> clazz) {
        return list.stream()
                .map(o -> copyBean(o,clazz))
                .collect(Collectors.toList());
    }
}
