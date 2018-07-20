package test.service;

import com.aliyun.oss.OSSClient;
import com.booksroo.classroom.common.enums.SubjectEnum;
import com.booksroo.classroom.common.util.FileUtil;
import com.booksroo.classroom.service.BizFileStoreService;
import com.booksroo.classroom.service.interf.FileStoreService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;


/**
 * @author liujianjian
 * @date 2018/6/5 11:30
 */
public class BizFileStoreServiceTest extends BaseJunitTest {

    @Autowired
    private FileStoreService aliYunOssService;

    @Autowired
    private BizFileStoreService bizFileStoreService;

    @Test
    public void putSubject() {

//        OSSClient oss = new OSSClient("oss-cn-hangzhou.aliyuncs.com", "LTAIHR1bcULwhxpg", "rtKv68iaXxqfo0xMEwNFkgzWs4S4eA");
//
//        File path = new File("D:\\work\\BooksrooWork\\xxhdpi");
//
//        Set<String> keys = new HashSet<>();
//        for (File f : path.listFiles()) {
//
//            String name = f.getName().replace(".png", "").trim();
//            if (!(name.equals("化学") || name.equals("物理"))) continue;
//
//            SubjectEnum e = SubjectEnum.getByName(name);
//            String key = "subject_" + e.getSubjectEN() + ".png";
//            oss.putObject("kd-classroom-public", key, f);
//
//            keys.add(key);
//        }
//
//        Calendar c = Calendar.getInstance();
//        c.add(Calendar.YEAR, 100);
//        for (String key : keys) {
//            URL url = oss.generatePresignedUrl("kd-classroom-public", key, c.getTime());
//            String str = url.toExternalForm();
//            System.out.println(str);
//        }

    }

    @Test
    public void putAvatar() {

//        OSSClient oss = new OSSClient("oss-cn-hangzhou.aliyuncs.com", "LTAIHR1bcULwhxpg", "rtKv68iaXxqfo0xMEwNFkgzWs4S4eA");
//        oss.putObject("kd-classroom-public", "default-avatar.png", new File("C:\\Users\\LJ\\Desktop\\Avatar.png"));
//
//        Calendar c = Calendar.getInstance();
//        c.add(Calendar.YEAR, 100);
//        URL url = oss.generatePresignedUrl("kd-classroom-public", "default-avatar.png", c.getTime());
//        String str = url.toExternalForm();
//        System.out.println(str);
    }

    @Test
    public void testBase64() throws Exception {
        String str = FileUtil.genBase64Str("C:\\Users\\LJ\\Desktop\\1.png");
        System.out.println(str);

//        FileUtil.genFileByBase64Str(str, "C:\\Users\\LJ\\Desktop\\3.png");
//        byte[] bytes = FileUtil.genBase64StrByBytes(str);
//        FileUtil.genFileByBytes(bytes, new File("C:\\Users\\LJ\\Desktop\\4.png"));

//        String url = aliYunOssService.putBytes("1.png", bytes);
//        System.out.println(url);
    }

    @Test
    public void test0() throws Exception {
        bizFileStoreService.handleStore(new FileInputStream(new File("C:\\Users\\LJ\\Desktop\\2.pptx")), "2.pptx", 1 + "-teacher");
    }

    @Test
    public void test() throws Exception {
        System.out.println(aliYunOssService.putBytes("kd_classroom_default_head.png", FileUtil.genBytesByFile(new File("C:\\Users\\LJ\\Desktop\\Avatar.png"), 1024)));
    }
}
