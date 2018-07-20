package com.booksroo.classroom.service;

import com.booksroo.classroom.common.constant.BizConstant;
import com.booksroo.classroom.common.enums.FileEnum;
import com.booksroo.classroom.common.exception.BizException;
import com.booksroo.classroom.common.util.FileUtil;
import com.booksroo.classroom.common.util.POIUtil;
import com.booksroo.classroom.service.interf.FileStoreService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.booksroo.classroom.common.constant.PromptConstant.PPT_CONTENT_INCORRECT;
import static com.booksroo.classroom.common.constant.PromptConstant.PPT_EMPTY;

/**
 * @author liujianjian
 * @date 2018/6/6 14:23
 */
@Service("bizFileHandleService")
public class BizFileStoreService extends BaseService {

    private static final String TEMP_FILE_STORE_PATH = BizConstant.TEMP_FILE_STORE_PATH;

    @Autowired(required = false)
    private FileStoreService aliYunOssService;

    public List<String> handleStore(InputStream is, String fileName, String flagName) throws Exception {
        if (is == null || StringUtils.isBlank(fileName)) return null;

        String suffix = FileUtil.getFileSuffix(fileName);

        if (FileEnum.isPPT(suffix) || FileEnum.isPPTX(suffix)) {
            return handlePPTStore(is, suffix, flagName);
        }
        List<String> urls = new ArrayList<>();
        urls.add(handleFileStore(is, suffix, flagName + "_1_"));
        return urls;
    }

    /**
     * 上传单个文件
     *
     * @param is
     * @param suffix   文件后缀
     * @param flagName
     * @return
     */
    public String handleFileStore(InputStream is, String suffix, String flagName) {
        return aliYunOssService.putInputStream(flagName + suffix, is);
    }

    public String handleFileStore(byte[] bytes, String fileName) {
        return aliYunOssService.putBytes(fileName, bytes);
    }

    public String handleFileStore(byte[] bytes, String suffix, String flagName) {
        return aliYunOssService.putBytes(flagName + suffix, bytes);
    }

    private List<String> handlePPTStore(InputStream is, String suffix, String flagName) throws Exception {

        List<File> files = null;
        long s = System.currentTimeMillis();
        if (FileEnum.isPPT(suffix)) {
            try {
                files = POIUtil.pptToImg(is, TEMP_FILE_STORE_PATH);
            } catch (Exception e) {
                log.error(".ppt转图片失败: " + e.getMessage(), e);
                throw new BizException(PPT_CONTENT_INCORRECT, "该 ppt 可能是手动修改的后缀名, 请打开 ppt 另存为 .ppt 或 .pptx 的格式, 上传另存为的文件");
            }
        }
        if (FileEnum.isPPTX(suffix)) {
            try {
                files = POIUtil.pptxToImg(is, TEMP_FILE_STORE_PATH);
            } catch (Exception e) {
                log.error(".pptx转图片失败: " + e.getMessage(), e);
                throw new BizException(PPT_CONTENT_INCORRECT, "该 ppt 可能是手动修改的后缀名, 请打开 ppt 另存为 .ppt 或 .pptx 的格式, 上传另存为的文件");
            }
        }
        if (CollectionUtils.isEmpty(files)) throw new BizException(PPT_EMPTY, "ppt没有内容");

        long diff = System.currentTimeMillis() - s;
        if (diff > (1000L * 60)) {
            log.info(flagName + ", ppt生成图片耗时：" + (diff / 60) + " 秒, files.size:" + files.size());
        }
//        http://kd-classroom.oss-cn-hangzhou.aliyuncs.com/1-teacher-5-2-1528287284453.jpeg?Expires=4683960884&OSSAccessKeyId=LTAIHR1bcULwhxpg&Signature=GLwCWKggy35%2B%2BHvcjj04M%2BGnvIY%3D
        List<String> urls = new ArrayList<>(files.size());
        s = System.currentTimeMillis();
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            String url = aliYunOssService.putBytes(flagName + "_" + (i + 1) + "_" + FileEnum.JPEG.getSuffix(), FileUtil.genBytesByFile(file));
            urls.add(url);
        }
        diff = System.currentTimeMillis() - s;
        if (diff > (1000L * 60)) {
            log.info(flagName + ", oss上传图片耗时：" + (diff / 60) + " 秒， files.size:" + files.size() + ", urls.size:" + urls.size());
        }

        //删除本地生成的图片
        delFiles(files);
        return urls;
    }

    private void delFiles(List<File> files) {
        for (File file : files) {
            if (!file.exists()) continue;
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        file.delete();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

}
