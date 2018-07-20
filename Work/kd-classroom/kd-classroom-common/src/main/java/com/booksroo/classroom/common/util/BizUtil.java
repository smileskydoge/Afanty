package com.booksroo.classroom.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.booksroo.classroom.common.constant.BizConstant;
import com.booksroo.classroom.common.domain.FileResourceChild;
import com.booksroo.classroom.common.domain.FileResources;
import com.booksroo.classroom.common.exception.BizException;
import com.booksroo.classroom.common.vo.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.*;

import static com.booksroo.classroom.common.constant.BizConstant.APP_REQUEST_URI_PREFIX;
import static com.booksroo.classroom.common.constant.BizConstant.COMMON_REQUEST_URI_PREFIX;
import static com.booksroo.classroom.common.constant.BizConstant.PC_REQUEST_URI_PREFIX;
import static com.booksroo.classroom.common.constant.PromptConstant.PARAMS_LENGTH_INCORRECT;
import static com.booksroo.classroom.common.constant.PromptConstant.PARAMS_MUST_BE_NOT_NULL;

/**
 * @author liujianjian
 * @date 2018/6/6 13:43
 */
public class BizUtil {

    public static String removeBase64Tag(String str) {
        return str.replaceAll("data:image/png;base64,", "");
    }

    //判断选项是否是正确答案
    public static boolean isOptionRight(String answer, String op) {
        if (StringUtils.isBlank(answer) || StringUtils.isBlank(op)) return false;
        answer = answer.trim();
        op = op.trim();
        if (!answer.startsWith("[") && !op.startsWith("[")) return answer.equals(op);
        int count = 0;
        JSONArray arr = JSON.parseArray(answer);
        if (!op.startsWith("[")) {
            for (int i = 0; i < arr.size(); i++) {
                if (arr.getString(i).equals(op)) return true;
            }
            return false;
        }
        JSONArray opArr = JSON.parseArray(op);
        if (arr.size() != opArr.size()) return false;
        for (int i = 0; i < arr.size(); i++) {
            String a = arr.getString(i);
            String o = opArr.getString(i);
            if (a.equals(o)) count++;
        }
        return count == arr.size();
    }

    public static FileResourceVo bindResourceVo(FileResourceVo vo, FileResources fr) {
        if (fr == null || vo == null) return null;

        vo.setResourceId(fr.getId());
        vo.setResourceName(fr.getResourceName());
        vo.setResourceType(fr.getResourceType().intValue());
        vo.setCreateTime(fr.getCreateTime());
        vo.setUpdateTime(fr.getUpdateTime());

        return vo;
    }

    public static List<FileResourceVo> replaceResourceToNoteContent(List<FileResourceVo> list, Map<String, String> map) {
        if (CollectionUtils.isEmpty(list) || MapUtils.isEmpty(map)) return list;

        for (FileResourceVo vo : list) {
            if (CollectionUtils.isEmpty(vo.getChildResourceList())) continue;
            for (FileResourceChild c : vo.getChildResourceList()) {
                String con = map.get(vo.getPackageClassId() + "_" + vo.getResourceId() + "_" + c.getId());
                if (StringUtils.isBlank(con)) continue;
                c.setContent(con);
            }
        }
        return list;
    }

    public static List<FileResourceChild> replaceResourceToNoteContent(long packageClassId, List<FileResourceChild> list, Map<String, String> map) {
        if (CollectionUtils.isEmpty(list) || MapUtils.isEmpty(map)) return list;

        for (FileResourceChild frc : list) {
            String con = map.get(packageClassId + "_" + frc.getResourceId() + "_" + frc.getId());
            if (StringUtils.isBlank(con)) continue;
            frc.setContent(con);
        }
        return list;
    }

    public static List<TeacherClassVo> orderClassList(List<TeacherClassVo> list) {
        if (CollectionUtils.isEmpty(list)) return list;

        list.sort(new Comparator<TeacherClassVo>() {
            @Override
            public int compare(TeacherClassVo o1, TeacherClassVo o2) {
                if (o1.getGrade() < o2.getGrade()) return -1;

                if (o1.getGrade() > o2.getGrade()) return 1;

                return o1.getClassNo() - o2.getClassNo();
            }
        });

        return list;
    }

    public static String decodeUrlJsonArr(String arr) throws Exception {
        if (StringUtils.isBlank(arr)) return arr;
        if (arr.startsWith("[") && !arr.contains("%")) return arr;

        int i = 0;
        while (!arr.startsWith("[")) {
            if (i > 10) break;
            arr = URLDecoder.decode(arr, "UTF-8");
            i++;
        }

        i = 0;
        while (arr.contains("%")) {
            if (i > 10) break;
            arr = URLDecoder.decode(arr, "UTF-8");
            i++;
        }
        return arr;
    }

    public static void checkPwdLength(String str, int minLength, int maxLength) throws Exception {
        if (str == null) throw new BizException(PARAMS_MUST_BE_NOT_NULL, "密码不能为空");

        if (str.length() < minLength || str.length() > maxLength)
            throw new BizException(PARAMS_LENGTH_INCORRECT, "密码长度必须在 " + minLength + " 到 " + maxLength + " 之间");

    }

    //去掉双引号
    public static String removeQuotes(String val) {
        return StringUtils.isNotBlank(val) ? val.replaceAll("\"", "") : "";
    }

    public static <T> List<T> jsonStrToList(String val, Class<T> clazz) {
        if (StringUtils.isBlank(val)) return null;

        JSONArray arr = JSON.parseArray(val);
        if (arr.size() <= 0) return null;

        List<T> list = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            list.add(JSON.parseObject(arr.getString(i), clazz));
        }
        return list;
    }

    public static List<String> jsonStrToList(String val) {
        if (StringUtils.isBlank(val)) return null;

        if (!val.startsWith("[")) {
            List<String> list = new ArrayList<>();
            list.add(val);
            return list;
        }

        JSONArray arr = JSON.parseArray(val);
        if (arr.size() <= 0) return null;

        List<String> list = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            list.add(arr.getString(i));
        }
        return list;
    }

    public static Set<String> jsonStrToSet(String val) {
        return jsonStrToSet(val, true);
    }

    public static Set<String> jsonStrToSet(String val, boolean includeEmpty) {
        if (StringUtils.isBlank(val)) return null;

        if (!val.startsWith("[")) {
            Set<String> set = new HashSet<>();
            set.add(val);
            return set;
        }

        JSONArray arr = JSON.parseArray(val);
        if (arr.size() <= 0) return null;

        Set<String> list = new HashSet<>();
        for (int i = 0; i < arr.size(); i++) {
            list.add(arr.getString(i));
        }
        return list;
    }

    public static String genToken(UserVo vo) {
        String tokenPre = vo.getUserType() + "-" + vo.getId() + "-" + System.currentTimeMillis();
        return tokenPre + "-" + genToken(tokenPre);
    }

    public static String genToken(String str) {
        if (str == null) str = "";
        try {
            return EncoderUtil.md5(BizConstant.PRIVATE_SECRET_KEY + str);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return str;
    }

    public static long getUserIdByToken(String token) {
        if (StringUtils.isBlank(token)) return 0;
        return Long.parseLong(token.split("-")[1]);
    }

    public static void print(PrintWriter pw, Object obj) {
        pw.write(JSON.toJSONString(obj));
        pw.close();
    }

    public static void orderPackageList(List<PackageVo> list) {
        if (CollectionUtils.isEmpty(list)) return;

        list.sort(new Comparator<PackageVo>() {
            @Override
            public int compare(PackageVo o1, PackageVo o2) {
                try {
                    if (o1.getCreateTime().before(o2.getCreateTime()))
                        return 1;
                    if (o1.getCreateTime().after(o2.getCreateTime()))
                        return -1;
                    return 0;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }

    public static void replaceOSSHost(List<FileResourceVo> list) {
        if (CollectionUtils.isEmpty(list)) return;

        for (FileResourceVo vo : list) {
            if (CollectionUtils.isEmpty(vo.getChildResourceList())) continue;
            for (FileResourceChild o : vo.getChildResourceList()) {
                if (StringUtils.isBlank(o.getContent())) continue;
                o.setContent(replaceOSSHost(o.getContent()));
            }
        }
    }

    public static void sortOssFile(List<FileResourceVo> list) {
//        if (CollectionUtils.isEmpty(list)) return;
//
//        list.sort(new Comparator<FileResourceVo>() {
//            @Override
//            public int compare(FileResourceVo o1, FileResourceVo o2) {
//
//                if (FileEnum.isPPT(o1.getResourceType()) || FileEnum.isPPT(o2.getResourceType())) return 0;
//
//                try {
//                    String o1Name = o1.getResourceName();
//                    String o2Name = o2.getResourceName();
//                    if (o1Name.contains("oss") && !o2Name.contains("oss")) {
//                        return -1;
//                    }
//                    if (!o1Name.contains("oss") && o2Name.contains("oss")) {
//                        return 1;
//                    }
//                    if (!o1Name.contains("oss") && !o2Name.contains("oss")) {
//                        if (o1.getCreateTime().before(o2.getCreateTime()))
//                            return 1;
//                        if (o1.getCreateTime().after(o2.getCreateTime()))
//                            return -1;
//                        return 0;
//                    }
//
//                    String[] arr1 = o1Name.split("_");
//                    String[] arr2 = o2Name.split("_");
//
////                    10000094_teacher_1530275766467_oss.jpg
//                    long time1 = Long.parseLong(arr1[2]);
//                    long time2 = Long.parseLong(arr2[2]);
//
//                    return (time1 - time2 > 0) ? 1 : -1;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return 0;
//            }
//        });
    }

    public static String replaceOSSHost(String str) {
        if (StringUtils.isBlank(str)) return "";
        return str.replace(BizConstant.ALIYUN_OSS_ADDRESS_HOST, "");
    }

    public static String getUserLogPre(Object obj) {
        if (obj instanceof TeacherVo) {
            TeacherVo vo = (TeacherVo) obj;
            return vo.getId() + ", " + vo.getTeacherName();
        }
        if (obj instanceof StudentVo) {
            StudentVo vo = (StudentVo) obj;
            return vo.getId() + ", " + vo.getStudentName();
        }

        return "";
    }

    public static String arrToStr(List<String> set) {
        if (CollectionUtils.isEmpty(set)) return "";
        StringBuilder sb = new StringBuilder();
        for (String str : set) {
            sb.append(",").append(str);
        }
        return sb.replace(0, 1, "").toString();
    }

    public static String longsToStr(Set<Long> set) {
        if (CollectionUtils.isEmpty(set)) return "";
        StringBuilder sb = new StringBuilder();
        for (long id : set) {
            sb.append(",").append(id);
        }
        return sb.replace(0, 1, "").toString();
    }

    public static Set<Long> strToLongs(String str) {
        return strToLongs(str, ",");
    }

    public static Set<Long> strToLongs(String str, String reg) {
        if (StringUtils.isBlank(str)) return null;

        if (StringUtils.isBlank(reg)) reg = ",";

        Set<Long> set = new HashSet<>();
        for (String s : str.split(reg)) {
            if (StringUtils.isBlank(s)) continue;
            set.add(Long.parseLong(s));
        }
        return set;
    }

    public static List<Long> strToLongList(String str) {
        return strToLongList(str, null);
    }

    public static List<Long> strToLongList(String str, String reg) {
        if (StringUtils.isBlank(str)) return null;
        if (StringUtils.isBlank(reg)) reg = ",";

        List<Long> list = new ArrayList<>();
        for (String s : str.split(reg)) {
            if (StringUtils.isBlank(s)) continue;
            list.add(Long.parseLong(s));
        }
        return list;
    }

    public static Set<String> strToArr(String str) {
        return strToArr(str, ",");
    }

    public static Set<String> strToArr(String str, String reg) {
        if (StringUtils.isBlank(str)) return null;

        if (StringUtils.isBlank(reg)) reg = ",";

        Set<String> set = new HashSet<>();
        for (String s : str.split(reg)) {
            if (StringUtils.isBlank(s)) continue;
            set.add(s);
        }
        return set;
    }

    public static String getAdminLoginUrl() {
        return "/common/login";
    }

    public static String getLoginUrl(HttpServletRequest request, boolean html) {

        String uri = request.getRequestURI();
        if (isTeacherRequest(uri)) {
            return html ? "/views/html/teacher/login.html" : "redirect:" + getRootPath(request) + "/views/html/teacher/login.html";
        }
        return "/views/html/teacher/login.html";
    }

    public static String getRootPath(HttpServletRequest request) {
//        String path = request.getContextPath();

        String serverName = request.getServerName();
        if (serverName.contains(".com")) {
            return request.getScheme() + "://" + request.getServerName();
        }
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    }

    public static String getWebRootPath() {
        return "/kd-classroom-web";
    }

    public static boolean isPCRequest(String uri) {
        return uri.contains(PC_REQUEST_URI_PREFIX);
    }

    public static boolean isAPPRequest(String uri) {
        return uri.contains(APP_REQUEST_URI_PREFIX);
    }

    public static boolean isCommonRequest(String uri) {
        return uri.contains(COMMON_REQUEST_URI_PREFIX);
    }

    public static boolean isTeacherRequest(String uri) {
        return uri.contains(BizConstant.PC_TEACHER_REQUEST_URI_PREFIX) || uri.contains(BizConstant.APP_TEACHER_REQUEST_URI_PREFIX);
    }

    public static boolean isStudentRequest(String uri) {
        return uri.contains(BizConstant.APP_STUDENT_REQUEST_URI_PREFIX);
    }
}
