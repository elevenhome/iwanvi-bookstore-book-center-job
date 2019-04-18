package com.iwanvi.bookstore.book.job.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 业务订单生成
 * @author zzw
 * @since 2018年12月14日16:57:47
 */
public class OrderUtils {


    /**微信支付**/
    public static final String WX_PAY = "1001";
    /**支付宝支付**/
    public static final String ALI_PAY = "1002";
    /**苹果支付（内购）**/
    public static final String APPLE_PAY = "1003";


    /**
     * 订单号
     * 订单规则:4位业务代码+20181009191423999+5位随机数+4位随机数=30位
     * 重复的概率极小可忽略集群和并发（考虑redis累加数）
     * @param busCode。
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    public static String getOrderNo(String busCode) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String nowTime = sdf.format(new Date());
        StringBuffer orderNo = new StringBuffer();
        orderNo.append(busCode);
        orderNo.append(nowTime);
        orderNo.append(getRandomNum(5));
        orderNo.append(getRandomNum(4));
        return orderNo.toString();
    }

    /**
     * 获取随机数
     * @Para 生成随机数的位数
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    public static Integer getRandomNum(int num) {
        int m = 10;
        for (int i = 1; i < num; i++) {
            m = m * 10;
        }
        double random = Math.random() + 1;
        return (int) (random * m );
    }




}
