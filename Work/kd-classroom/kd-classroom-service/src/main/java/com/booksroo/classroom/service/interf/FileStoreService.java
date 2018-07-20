package com.booksroo.classroom.service.interf;

import java.io.InputStream;

/**
 * @author liujianjian
 * @date 2018/6/5 10:07
 */
public interface FileStoreService {

    String putBytes(String key, byte[] bytes);

    String putInputStream(String key, InputStream is);
}
