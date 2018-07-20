package com.booksroo.classroom.service;

import com.booksroo.classroom.common.domain.ClassDomain;
import com.booksroo.classroom.common.domain.School;
import com.booksroo.classroom.common.domain.Student;
import com.booksroo.classroom.common.exception.BizException;
import com.booksroo.classroom.common.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.DateUtil;
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

@Service("bizStudentExcelService")
public class BizStudentExcelService extends BaseService{

    @Autowired
    private BizStudentService bizStudentService;

    @Autowired
    private BizSchoolService bizSchoolService;

    @Autowired
    private BizClassService bizClassService;

    @Autowired
    private BizCheckWeightService bizCheckWeightService;


    public Boolean initStudentExcel(File file) throws Exception{
        //读取学生模板
        XSSFWorkbook workBook = new XSSFWorkbook();
        XSSFSheet sheet1 = workBook.createSheet("sheet1");
        //在sheet2上创建映射关系表
        XSSFSheet sheet2 = workBook.createSheet("sheet2");

        writeInfoExcel(sheet1);
        writeMappingExcel(sheet2);
        workBook.write(new FileOutputStream(file));
        return true;
    }

    private void writeInfoExcel(XSSFSheet sh1) {
        XSSFRow row1=sh1.createRow(0);
        row1.createCell(0).setCellValue("学校ID");
        row1.createCell(1).setCellValue("班级ID");
        row1.createCell(2).setCellValue("学号");
        row1.createCell(3).setCellValue("姓名");
        row1.createCell(4).setCellValue("账号");
        row1.createCell(5).setCellValue("年龄");
        row1.createCell(6).setCellValue("入学时间(yyyy.MM.dd)");
    }

    public void writeMappingExcel(XSSFSheet sheet) throws Exception{
        XSSFRow row1=sheet.createRow(0);
        XSSFCell cell1 = row1.createCell(0);
        cell1.setCellValue("ID 映射关系表");
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,2));

        List<School> schools = bizSchoolService.getAllSchoolInfo();
        if(schools==null)throw new BizException("查询学校列表错误");
        int rowNum = 0;
        for(int i=0;i<schools.size();i++){
            School school = schools.get(i);

            rowNum = rowNum + i + 1;
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

            List<ClassDomain> classDomains = bizClassService.getClassInfoBySchoolId(school.getId());
            if(classDomains==null)continue;

           for(int j=0;j<classDomains.size();j++){
               rowNum++;
               ClassDomain classDomain = classDomains.get(j);
               XSSFRow row4 = sheet.createRow(rowNum);
               row4.createCell(0).setCellValue(classDomain.getId());
               row4.createCell(1).setCellValue(classDomain.getGrade()+"年级"+classDomain.getClassNo()+"班");
           }
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    public Boolean insertStudentsInfo(File file) throws Exception{
        List<Student> students = insertExcelStudentInfos(file);
        for(int i=0;i<students.size();i++){
            Student s = students.get(i);
            if (s==null) throw new BizException("Excel解析异常");
            if(bizCheckWeightService.checkStudentByUnique(s)){
                bizStudentService.addStudentService(s);
            }else{
                throw new BizException("第"+(i+2)+"行学生账号已存在,请查证后重新导入!");
            }
        }
        return true;
    }


    @Transactional(rollbackFor = Throwable.class)
    public List<Student> insertExcelStudentInfos(File file) throws Exception{
        if(!file.isFile())throw new BizException("文件上传失败，请联系管理员");
        InputStream is = new FileInputStream(file);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);

        XSSFSheet StudentSheet = xssfWorkbook.getSheet("sheet1");

        List<Student> students = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");

        for(int rowNum=1;rowNum<=StudentSheet.getLastRowNum();rowNum++){
            XSSFRow row= StudentSheet.getRow(rowNum);
            if(row!=null){
                Student student = new Student();
                XSSFCell schoolId = row.getCell(0);
                XSSFCell classId = row.getCell(1);
                XSSFCell studentNo = row.getCell(2);
                XSSFCell studentName = row.getCell(3);
                XSSFCell account = row.getCell(4);
                XSSFCell age = row.getCell(5);
                XSSFCell beginTime = row.getCell(6);

                try {
                    if (studentNo != null) student.setStudentNo(ExcelUtil.getValueByCell(studentNo));
                    if (age != null) student.setAge(Byte.parseByte(ExcelUtil.getValueByCell(age)));
                    if (beginTime != null)student.setBeginTime(sdf.parse(ExcelUtil.getValueByCell(beginTime)));
                    student.setSchoolId(Long.parseLong(ExcelUtil.getValueByCell(schoolId)));
                    student.setClassId(Long.parseLong(ExcelUtil.getValueByCell(classId)));
                    student.setStudentName(ExcelUtil.getValueByCell(studentName));
                    if("".equals(ExcelUtil.getValueByCell(studentName))) throw new Exception();
                    student.setParentPhone(ExcelUtil.getValueByCell(account));
                    if("".equals(ExcelUtil.getValueByCell(account))) throw new Exception();
                } catch (Exception e) {
                    throw new BizException("表格中第" + (rowNum+1) + "行参数不合法");
                }
                students.add(student);
            }
        }
        return students;
    }
}
