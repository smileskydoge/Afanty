package com.booksroo.classroom.common.util;

import java.security.MessageDigest;

/**
 * @author liujianjian
 * @date 2018/6/3 16:44
 */
public class EncoderUtil {

    public static void main(String[] args) throws Exception {
        System.out.println(md5("111111"));
        System.out.println(md5("111111").length());
    }

//    public static String md5(String str) throws Exception {
//
//        if (str == null || str.trim().equals("")) return "";
//
//        // 得到一个信息摘要器
//        MessageDigest digest = MessageDigest.getInstance("md5");
//        byte[] result = digest.digest(str.getBytes());
//        StringBuilder sb = new StringBuilder();
//        // 把每一个byte 做一个与运算 0xff;
//        for (byte b : result) {
//            // 与运算
//            int number = b & 0xff;// 加盐
//            String s = Integer.toHexString(number);
//            if (s.length() == 1) {
//                sb.append("0");
//            }
//            sb.append(str);
//        }
//
//        // 标准的md5加密后的结果
//        return sb.toString();
//    }

    public static String md5(String key) throws Exception {
        if (key == null || key.trim().equals("")) return "";

        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
        };
        byte[] btInput = key.getBytes();
        // 获得MD5摘要算法的 MessageDigest 对象
        MessageDigest mdInst = MessageDigest.getInstance("MD5");
        // 使用指定的字节更新摘要
        mdInst.update(btInput);
        // 获得密文
        byte[] md = mdInst.digest();
        // 把密文转换成十六进制的字符串形式
        int j = md.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = md[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);

    }

}
