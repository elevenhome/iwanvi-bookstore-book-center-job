package com.iwanvi.bookstore.book.job.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 常用的方法集合
 * @author zzw
 * @since 2018年12月14日16:57:47
 */
public class Utils {

    /**
     * 字符串为空判断
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    public static boolean isEmpty(final String str) {
        if (null == str || str.trim().length() == 0 || "null".equals(str)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 字符串非空判断
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    public static boolean isNotEmpty(final String str) {
        return !isEmpty(str);
    }

    /**
     * StringBuilder为空判断
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    public static boolean isEmpty(final StringBuilder obj) {
        if (null == obj || obj.length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * StringBuilder非空判断
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    public static boolean isNotEmpty(final StringBuilder obj) {
        return !isEmpty(obj);
    }

    /**
     * 对象为空判断
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    public static boolean isEmpty(final Object obj) {
        return null == obj;
    }

    /**
     * 对象非空判断
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    public static boolean isNotEmpty(final Object obj) {
        return null != obj;
    }

    /**
     * Integer不为空且不等于0判断
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    public static boolean isNotEmptyZero(final Integer obj) {
        return !isEmptyZero(obj);
    }

    /**
     * Integer为空或等于0判断
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    public static boolean isEmptyZero(final Integer obj) {
        return isEmpty(obj) || obj == 0;
    }


    /**
     * Long不为空且不等于0判断
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    public static boolean isNotEmptyZero(final Long obj) {
        return !isEmptyZero(obj);
    }

    /**
     * Long为空或等于0判断
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    public static boolean isEmptyZero(final Long obj) {
        return isEmpty(obj) || obj == 0;
    }

    //-----------------collection--------------------



    /**
     * 获取Collection集合长度
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    public static int size(final Collection<?> collection) {
        return isEmpty(collection) ? 0 : collection.size();
    }

    /**
     * 获取Map集合长度
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    public static int size(final Map<?, ?> map) {
        return isEmpty(map) ? 0 : map.size();
    }


    /**
     * 判断Collection集合为空
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    public static boolean isEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty() || collection.size() <= 0;
    }

    /**
     * 判断Collection集合非空
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    public static boolean isNotEmpty(final Collection<?> collection) {
        return !isEmpty(collection);
    }


    /**
     * 判断Map集合为空
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    public static boolean isEmpty(final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }


    /**
     * 判断Map集合非空
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    public static boolean isNotEmpty(final Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * 整形List集合转化为int数组
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    public static int[] listToInArray(final List<Integer> list) {
        int[] intArry = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            intArry[i] = list.get(i);
        }
        return intArry;
    }


    /**
     * 判断是否为数字
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    public static boolean isNumer1(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否为数字 如果不是 = 0
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    public static boolean isNumer(String str) {
        try {
            Integer.parseInt(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


}
