package com.booksroo.classroom.service;

import com.booksroo.classroom.common.domain.*;
import com.booksroo.classroom.common.exception.BizException;
import com.booksroo.classroom.common.query.TeacherQuery;
import com.booksroo.classroom.common.util.BeanUtilsExt;
import com.booksroo.classroom.common.util.BizUtil;
import com.booksroo.classroom.common.util.ExcelUtil;
import com.booksroo.classroom.common.vo.TeacherAddVo;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service("bizTeacherExcelService")
public class BizTeacherExcelService extends BaseService{


    @Autowired
    private BizTeacherService bizTeacherService;

    @Autowired
    private BizSubjectService bizSubjectService;

    @Autowired
    private BizSchoolService bizSchoolService;

    @Autowired
    private BizClassService bizClassService;

    @Autowired
    private BizCheckWeightService bizCheckWeightService;



    public Boolean initTeacherExcel(File file) throws Exception{
        XSSFWorkbook workBook = new XSSFWorkbook();

        writeSheet1(workBook.createSheet("sheet1"));
        writeSheet2(workBook.createSheet("sheet2"));
        writeSheet3(workBook.createSheet("sheet3"));
        workBook.write(new FileOutputStream(file));
        return true;
    }



    private void writeSheet1(XSSFSheet sh1) {
        XSSFRow row1=sh1.createRow(0);
        row1.createCell(0).setCellValue("学校ID");
        row1.createCell(1).setCellValue("班级ID(多班级以“,”连接)");
        row1.createCell(2).setCellValue("学科ID");
        row1.createCell(3).setCellValue("姓名");
        row1.createCell(4).setCellValue("账号");
        row1.createCell(5).setCellValue("年龄");
    }

    private void writeSheet2(XSSFSheet sheet) throws Exception {

        sheet.createRow(0).createCell(0).setCellValue("ID 映射关系表");
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,2));

        List<School> schools = bizSchoolService.getAllSchoolInfo();
        if(schools==null)throw new BizException("查询学校列表错误");
        int rowNum = 1;

        for(int i=0;i<schools.size();i++){
            School school = schools.get(i);

            rowNum = rowNum + i;

            XSSFRow row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue("学校ID");
            row.createCell(1).setCellValue("学校名称");
            rowNum++;

            XSSFRow row2 = sheet.createRow(rowNum);
            row2.createCell(0).setCellValue(school.getId());
            row2.createCell(1).setCellValue(school.getSchoolName());
            rowNum++;

            XSSFRow row3 = sheet.createRow(rowNum);
            row3.createCell(0).setCellValue("班级ID");
            row3.createCell(1).setCellValue("班级名称");
            rowNum++;

            List<ClassDomain> classDomains = bizClassService.getClassInfoBySchoolId(school.getId());
            if(classDomains==null)continue;

            for(int j=0;j<classDomains.size();j++){
                ClassDomain classDomain = classDomains.get(j);
                XSSFRow row4 = sheet.createRow(rowNum);
                row4.createCell(0).setCellValue(classDomain.getId());
                row4.createCell(1).setCellValue(classDomain.getGrade()+"年级"+classDomain.getClassNo()+"班");
                rowNum++;
            }
        }
    }

    private void writeSheet3(XSSFSheet sheet) throws Exception{

        sheet.createRow(0).createCell(0).setCellValue("学科映射关系表");
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,2));

        List<Subject> subjects = bizSubjectService.getAllSubjectList();
        if (subjects==null)throw new BizException("查询学科列表错误");

        XSSFRow row1 = sheet.createRow(1);

        row1.createCell(0).setCellValue("学科名");
        row1.createCell(1).setCellValue("学科ID");

        for (int i=0;i<subjects.size();i++){
            Subject subject = subjects.get(i);
            XSSFRow row = sheet.createRow(i+2);
            row.createCell(0).setCellValue(subject.getSubjectName());
            row.createCell(1).setCellValue(subject.getId());
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    public Boolean insertTeacherInfo(File file) throws Exception{
        List<TeacherAddVo> teacherAddVos = insertExcelTeacherInfos(file);
        for(int i=0;i<teacherAddVos.size();i++){
            TeacherAddVo teacherAddVo = teacherAddVos.get(i);
            Teacher teacher = new Teacher();
            BeanUtilsExt.copyProperties(teacher,teacherAddVo);

            if (teacherAddVo==null) throw new BizException("Excel解析异常");
            if(teacherAddVo.getClassIds()==null||teacherAddVo.getSubjectId()==null)throw new BizException("第"+(i+2)+"行参数不合法!");
            TeacherQuery teacherQuery = getTeacherQueryInfo(teacherAddVo);

            if(!bizCheckWeightService.checkTeacherByUnique(teacher))throw new BizException("第"+(i+2)+"行账号已存在,请查证后重新导入!");
            if(!bizCheckWeightService.checkTeacherClassByUnique(teacherQuery))throw new BizException("第"+(i+2)+"行教师所在班级科目已有老师,请查证后重新导入!");
            bizTeacherService.addTeacherInfoService(teacherAddVo);
        }
        return true;
    }

    private TeacherQuery getTeacherQueryInfo(TeacherAddVo teacherAddVo) throws BizException {
        TeacherQuery teacherQuery ;
        try{
            teacherQuery = new TeacherQuery();
            teacherQuery.setClassIds(BizUtil.strToLongs(teacherAddVo.getClassIds()));
            teacherQuery.setSubjectId(Long.parseLong(teacherAddVo.getSubjectId()));
        }catch (Exception e){
            throw new BizException(teacherAddVo.getTeacherName()+"信息填写有误，请更正后再输入");
        }
        return teacherQuery;
    }


    @Transactional(rollbackFor = Throwable.class)
    public List<TeacherAddVo> insertExcelTeacherInfos(File file) throws Exception{

        if(!file.isFile())throw new BizException("文件上传失败，请联系管理员");
        InputStream is = new FileInputStream(file);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);

        XSSFSheet teacherSheet = xssfWorkbook.getSheet("sheet1");

        List<TeacherAddVo> teacherAddVos = new ArrayList<>();

        for(int rowNum=1;rowNum<=teacherSheet.getLastRowNum();rowNum++){
            XSSFRow row= teacherSheet.getRow(rowNum);
            if(row!=null){
                TeacherAddVo teacherAddVo = new TeacherAddVo();

                XSSFCell schoolId = row.getCell(0);
                XSSFCell classId = row.getCell(1);
                XSSFCell subjectId = row.getCell(2);
                XSSFCell teacherName = row.getCell(3);
                XSSFCell mobileNo = row.getCell(4);
                XSSFCell age = row.getCell(5);

                try {
                    teacherAddVo.setSchoolId(Long.parseLong(ExcelUtil.getValueByCell(schoolId)));
                    teacherAddVo.setClassIds(ExcelUtil.getValueByCell(classId));
                    teacherAddVo.setSubjectId(ExcelUtil.getValueByCell(subjectId));
                    teacherAddVo.setTeacherName(ExcelUtil.getValueByCell(teacherName));
                    teacherAddVo.setMobileNo(ExcelUtil.getValueByCell(mobileNo));
                    if(age !=null)teacherAddVo.setAge(Byte.parseByte(ExcelUtil.getValueByCell(age)));
                }catch (Exception e){
                    throw new BizException("表格中第" + (rowNum+1) + "行参数不合法");
                }
                teacherAddVos.add(teacherAddVo);
            }
        }
        return teacherAddVos;
    }
}
