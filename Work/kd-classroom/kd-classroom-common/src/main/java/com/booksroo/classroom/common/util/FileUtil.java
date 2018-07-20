package com.booksroo.classroom.common.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * <pre>
 * Author: liujianjian
 * DateTime: 17/1/3 下午8:46.
 * </pre>
 */
public class FileUtil {

    public static void main(String[] args) throws Exception {
        String s = "fdsa.png";
        System.out.println(getFileType(s));
    }

    //将base64编码字符串转换为图片
    public static boolean genFileByBase64Str(String base64Str, String path) throws Exception {
        if (base64Str == null) return false;

        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = decoder.decodeBuffer(base64Str);
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {
                b[i] += 256;
            }
        }
        OutputStream out = new FileOutputStream(path);
        out.write(b);
        out.flush();
        out.close();
        return true;
    }

    //据图片地址转换为base64编码字符串
    public static String genBase64Str(String imgFile) throws Exception {
        InputStream inputStream = null;
        byte[] data = null;
        inputStream = new FileInputStream(imgFile);
        data = new byte[inputStream.available()];
        inputStream.read(data);
        inputStream.close();
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encodeBuffer(data);
    }

    public static String genBase64Str(byte[] bytes) throws Exception {
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encodeBuffer(bytes);
    }

    //base64字符串转byte[]
    public static byte[] genByteFromBase64Str(String base64Str) throws Exception {
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(base64Str);
    }

    public static String urlEncodeFileName(String name) {
        try {
            return URLEncoder.encode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return name;
    }

    public static String handlePath(String path) {
        if (!path.startsWith("/")) return "/" + path;
        return path;
    }

    public static String getFileSuffix(String fileName) {
        if (StringUtils.isNotBlank(fileName)) {
            return fileName.substring(fileName.lastIndexOf("."), fileName.length());
        }
        return "";
    }

    public static String getFileType(String fileName) {
        if (StringUtils.isNotBlank(fileName)) {
            return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        }
        return "";
    }


    //将文件转换成Byte数组
    public static byte[] genBytesByFile(File file) throws Exception {
        return genBytesByFile(file, 1024);
    }

    public static byte[] genBytesByFile(File file, int byteArrSize) throws Exception {
        ByteArrayOutputStream bos = null;
        FileInputStream fis = null;
        try {
//            File file = new File(pathStr);
            fis = new FileInputStream(file);
            bos = new ByteArrayOutputStream(byteArrSize);
            byte[] b = new byte[byteArrSize];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            return bos.toByteArray();
        } catch (Exception e) {
            throw e;
        } finally {
            if (fis != null)
                fis.close();
            if (bos != null)
                bos.close();
        }
    }

    //将Byte数组转换成文件
    public static void genFileByBytes(byte[] bytes, File file) throws Exception {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
//            if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
//                dir.mkdirs();
//            }
//            file = new File(filePath + "\\" + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
            bos.close();
            fos.close();
        } catch (Exception e) {
            throw e;
        } finally {
            if (bos != null) {
                bos.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

}
