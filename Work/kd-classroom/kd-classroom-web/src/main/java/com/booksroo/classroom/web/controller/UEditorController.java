package com.booksroo.classroom.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.booksroo.classroom.common.enums.FileEnum;
import com.booksroo.classroom.common.util.BizUtil;
import com.booksroo.classroom.common.util.FileUtil;
import com.booksroo.classroom.service.BizFileStoreService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * @author liujianjian
 * @date 2018/7/16 13:53
 */
@RestController
@RequestMapping("/common/ueditor")
public class UEditorController extends BaseController {

    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    private BizFileStoreService bizFileStoreService;

    private static final Set<String> TEMP_FILE_PATH = new HashSet<>();

    private static final String FOLDER_NAME_FORMAT = "yyyyMMdd";

    //单个文件上传
    @RequestMapping("/upload")
    public Object upload(HttpServletRequest request, String action) {
        try {
            if ("config".equals(action)) {
                return getConfig(request);
            }
            List<MultipartFile> files = getUploadFiles(request);
            if (CollectionUtils.isEmpty(files)) return uploadFail("未选择文件");
            MultipartFile file = files.get(0);
            String fileName = file.getOriginalFilename();
            String suffix = FileUtil.getFileSuffix(fileName);
            if (!FileEnum.isImage(suffix)) throw new Exception("仅支持图片上传");

            String url = FileUtil.genBase64Str(file.getBytes());
            url = "data:image/" + (FileUtil.getFileType(fileName)) + ";base64," + url;
//            String date = DateFormatUtils.format(new Date(), FOLDER_NAME_FORMAT);
//            String savedFileName = UUID.randomUUID().toString().replace("-", "").concat(suffix);       //文件存取名
//
//            String url = bizFileStoreService.handleFileStore(file.getBytes(), "tempFile/" + date + "/" + savedFileName);
//            String realPath = request.getSession().getServletContext().getRealPath("views/upload");
//            String savedDir = realPath + "/" + date;
//
//            File path = new File(savedDir);
//            if (!path.exists()) {
//                if (!path.mkdirs()) throw new Exception("文件保存路径创建失败");
//            }
//
//            file.transferTo(new File(savedDir + "/" + savedFileName));

//            taskExecutor.execute(new Runnable() {
//                @Override
//                public void run() {
//                    synchronized (this) {
//                        TEMP_FILE_PATH.add(realPath);
//                    }
//                }
//            });
//            return uploadSuccess(fileName, suffix, BizUtil.getRootPath(request) + "/views/upload/" + date + "/" + savedFileName);
            return uploadSuccess(fileName, suffix, url);
        } catch (Exception e) {
            log.error("/common/ueditor/upload error: " + e.getMessage(), e);
            return uploadFail("上传失败: " + e.getMessage());
        }
    }

    private Object getConfig(HttpServletRequest request) throws Exception {

        String realPath = request.getServletContext().getRealPath("/");
        File file = new File(realPath + "ueditor/jsp/config.json");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return JSON.parseObject(sb.toString());
    }

    private Map<String, Object> uploadSuccess(String originalName, String suffix, String url) {
//        {
//            "state": "SUCCESS",
//                "original": "1.png",
//                "size": "67295",
//                "title": "1531723810372049009.png",
//                "type": ".png",
//                "url": "/ueditor/jsp/upload/image/20180716/1531723810372049009.png"
//        }

        Map<String, Object> map = new HashMap<>();
        map.put("state", "SUCCESS");
        map.put("original", originalName);
        map.put("type", suffix);
        map.put("url", url);
        return map;
    }

    private Map<String, Object> uploadFail(String msg) {
        Map<String, Object> map = new HashMap<>();
        map.put("state", msg);
        return map;
    }

    //每天凌晨2点删掉前一天生成的临时文件
//    @Scheduled(cron = "0 0 2 * * ?")
//    public void delTempFiles() {
//        try {
//            log.info("start delTempFiles ...");
//
//            Calendar c = Calendar.getInstance();
//            c.add(Calendar.DAY_OF_MONTH, -1);
//
//            String yesterday = DateFormatUtils.format(c.getTimeInMillis(), FOLDER_NAME_FORMAT);
//            TEMP_FILE_PATH.forEach(p -> {
//                File path = new File(p);
//                if (!path.exists()) return;
//
//                path = new File(p + "/" + yesterday);
//                if (!path.exists()) return;
//
//                boolean del = path.delete();
//                if (del) log.info(" delTempFiles success, path: " + (p + "/" + yesterday));
//            });
//
//            log.info("end delTempFiles ...");
//        } catch (Exception e) {
//            log.error("delTempFiles error: " + e.getMessage(), e);
//        }
//    }
}
