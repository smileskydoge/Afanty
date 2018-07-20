package com.booksroo.classroom.sdk.oss;

//import com.aliyun.oss.OSSClient;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Calendar;

import static com.booksroo.classroom.sdk.constant.AliyunConstant.*;

/**
 * @author liujianjian
 * @date 2018/6/22 16:37
 */
public class OssService {

//    private OSSClient ossClient;
//
//    private static final OssService ossService = new OssService();
//
//    private OssService() {
//        ossClient = new OSSClient(OSS_ENDPOINT, OSS_ACCESS_KEY_ID, OSS_SECRET_ACCESS_KEY);
//    }
//
//    public static OssService getInstance() {
//        return ossService;
//    }
//
//    public String upload(String key, byte[] bytes) {
//        ossClient.putObject(OSS_BUKET_NAME, key, new ByteArrayInputStream(bytes));
//        return genUrl(key);
//    }
//
//    // 生成URL。设置URL过期时间为100年
//    private String genUrl(String key) {
//        Calendar c = Calendar.getInstance();
//        c.add(Calendar.YEAR, 100);
//        URL url = ossClient.generatePresignedUrl(OSS_BUKET_NAME, key, c.getTime());
//        return url.toExternalForm();
//    }

}
