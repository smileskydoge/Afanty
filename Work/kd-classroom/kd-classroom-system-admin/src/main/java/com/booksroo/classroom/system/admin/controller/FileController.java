package com.booksroo.classroom.system.admin.controller;


import com.booksroo.classroom.common.exception.BizException;
import com.booksroo.classroom.service.BizClassExcelService;
import com.booksroo.classroom.service.BizStudentExcelService;
import com.booksroo.classroom.service.BizTeacherExcelService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping(value = "/admin/file")
public class FileController extends BaseController {

    @Autowired
    private BizStudentExcelService bizStudentExcelService;

    @Autowired
    private BizClassExcelService bizClassExcelService;

    @Autowired
    private BizTeacherExcelService bizTeacherExcelService;


    @RequestMapping(value = "/student/excel")
    public ResponseEntity<byte[]> downloadStudentExc(HttpServletRequest request) throws Exception{

        //下载文件路径
        String path = request.getServletContext().getRealPath("/TempletFile/");
        File file = new File(path + File.separator + "学生模板.xlsx");
        //每次下载模板时更新
        if(file.exists()) file.delete();
        File newFile = new File(path + File.separator + "学生模板.xlsx");

        bizStudentExcelService.initStudentExcel(newFile);
        HttpHeaders headers = new HttpHeaders();
        //下载显示的文件名，解决中文名称乱码问题
        String downloadFielName = new String("学生模板.xlsx".getBytes("UTF-8"),"iso-8859-1");
        //通知浏览器以attachment（下载方式）
        headers.setContentDispositionFormData("attachment", downloadFielName);
        //application/octet-stream ： 二进制流数据（最常见的文件下载）。
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(newFile), headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/teacher/excel")
    public ResponseEntity<byte[]> downloadTeacherExc(HttpServletRequest request) throws Exception{
        //下载文件路径
        String path = request.getServletContext().getRealPath("/TempletFile/");
        File file = new File(path + File.separator + "教师模板.xlsx");
        //每次下载模板时更新
        if(file.exists()) file.delete();
        File newFile = new File(path + File.separator + "教师模板.xlsx");
        bizTeacherExcelService.initTeacherExcel(newFile);

        HttpHeaders headers = new HttpHeaders();
        String downloadFielName = new String("教师模板.xlsx".getBytes("UTF-8"),"iso-8859-1");
        headers.setContentDispositionFormData("attachment", downloadFielName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/class/excel")
    public ResponseEntity<byte[]> downloadClassExc(HttpServletRequest request) throws Exception{
        //获得模板地址
        String path = request.getServletContext().getRealPath("/TempletFile/");
        File file = new File(path + File.separator + "班级模板.xlsx");
        if(file.exists()) file.delete();
        File newFile = new File(path + File.separator + "班级模板.xlsx");

        //初始化模板
        bizClassExcelService.initClassExcel(file);
        //封装发送格式
        HttpHeaders headers = new HttpHeaders();
        String downloadFielName = new String("班级模板.xlsx".getBytes("UTF-8"),"iso-8859-1");
        headers.setContentDispositionFormData("attachment", downloadFielName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/uploadStudentInfo")
    public Object uploadStudentExcel(MultipartFile file,HttpServletRequest request) throws Exception{
        String desFilePath = "";
        String oriName = "";
        // 1.获取原文件名
        oriName = file.getOriginalFilename();
        if(!oriName.startsWith("学生")) throw new BizException(" 请选择学生模板上传");
        // 2.获取原文件图片后缀，以最后的.作为截取
        String extName = oriName.substring(oriName.lastIndexOf("."));
        // 3.生成自定义的新文件名，这里以UUID.jpg|png|xxx作为格式（可选操作，也可以不自定义新文件名）
        String uuid = UUID.randomUUID().toString();
        String newName = uuid + extName;
        // 4.获取要保存的路径文件夹
        String path = request.getServletContext().getRealPath("/DataFile/");
        // 5.保存图片
        desFilePath = path + "\\" + newName;
        File desFile = new File(desFilePath);
        file.transferTo(desFile);
        if(bizStudentExcelService.insertStudentsInfo(desFile))return success();

        return fail("-1","批量增加失败");
    }

    @RequestMapping(value = "/uploadTeacherInfo")
    public Object uploadTeacherExcel(MultipartFile file,HttpServletRequest request) throws Exception{
        String desFilePath = "";
        String oriName = "";
        // 1.获取原文件名
        oriName = file.getOriginalFilename();
        if(!oriName.startsWith("教师")) throw new BizException(" 请选择教师模板上传");
        // 2.获取原文件图片后缀，以最后的.作为截取
        String extName = oriName.substring(oriName.lastIndexOf("."));
        // 3.生成自定义的新文件名，这里以UUID.jpg|png|xxx作为格式（可选操作，也可以不自定义新文件名）
        String uuid = UUID.randomUUID().toString();
        String newName = uuid + extName;
        // 4.获取要保存的路径文件夹
        String path = request.getServletContext().getRealPath("/DataFile/");
        // 5.保存图片
        desFilePath = path + "\\" + newName;
        File desFile = new File(desFilePath);
        file.transferTo(desFile);
        if(bizTeacherExcelService.insertTeacherInfo(desFile))return success();

        return fail("-1","批量增加失败");
    }

    @RequestMapping(value = "/uploadClassInfo")
    public Object uploadClassExcel(MultipartFile file,HttpServletRequest request) throws Exception{
        String desFilePath = "";
        String oriName = "";
        // 1.获取原文件名
        oriName = file.getOriginalFilename();
        if(!oriName.startsWith("班级")) throw new BizException(" 请选择班级模板上传");
        // 2.获取原文件图片后缀，以最后的.作为截取
        String extName = oriName.substring(oriName.lastIndexOf("."));
        // 3.生成自定义的新文件名，这里以UUID.jpg|png|xxx作为格式（可选操作，也可以不自定义新文件名）
        String uuid = UUID.randomUUID().toString();
        String newName = uuid + extName;
        // 4.获取要保存的路径文件夹
        String path = request.getServletContext().getRealPath("/DataFile/");
        // 5.保存图片
        desFilePath = path + "\\" + newName;
        File desFile = new File(desFilePath);
        file.transferTo(desFile);
        if (bizClassExcelService.insertClassInfo(desFile))return success();
        return fail("-1","批量增加失败");
    }
}
