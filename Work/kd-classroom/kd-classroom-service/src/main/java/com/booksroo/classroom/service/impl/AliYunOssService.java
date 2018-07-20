package com.booksroo.classroom.service.impl;

import com.aliyun.oss.OSSClient;
import com.booksroo.classroom.service.interf.FileStoreService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;

/**
 * @author liujianjian
 * @date 2018/6/5 10:10
 */
public class AliYunOssService implements FileStoreService {

    private String bucketName;

    private OSSClient ossClient;

    //上传字节数组
    @Override
    public String putBytes(String key, byte[] bytes) {
        if (bytes.length <= 0) return "bytes is empty";
        ossClient.putObject(bucketName, key, new ByteArrayInputStream(bytes));
        return genUrl(key);
    }

    @Override
    public String putInputStream(String key, InputStream is) {
        ossClient.putObject(bucketName, key, is);
        return genUrl(key);
    }

    // 生成URL。设置URL过期时间为100年
    private String genUrl(String key) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, 100);
        URL url = ossClient.generatePresignedUrl(bucketName, key, c.getTime());
//        ossClient.shutdown();
        return url.toExternalForm();
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public OSSClient getOssClient() {
        return ossClient;
    }

    public void setOssClient(OSSClient ossClient) {
        this.ossClient = ossClient;
    }

    public static void main(String[] args) throws Exception {

//        https://koudairoo.oss-cn-hangzhou.aliyuncs.com/201712261545401213337266.png?Expires=1528171394&OSSAccessKeyId=TMP.AQER-sEU-aJ3D6I9eGqTgz9H_0XehkYBjmg8JDaj74HPJer7Nrbd-kdlxYHkAAAwLAIUZEv6b3IzwjGZQVJDQLE3shMMDSQCFGqPqhM1g2n2UIeNZWsaFMXubx-S&Signature=PTNANVeai0sJxapf4mFFBIcex2g%3D
        URL url = new URL("https", "koudairoo.oss-cn-hangzhou.aliyuncs.com", "/201712261545401213337266.png");

        System.out.println(url.toExternalForm());
    }
}
