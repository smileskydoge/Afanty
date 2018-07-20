package com.booksroo.classroom.service;

import com.booksroo.classroom.common.domain.ClassDomain;
import com.booksroo.classroom.common.domain.School;
import com.booksroo.classroom.common.exception.BizException;
import com.booksroo.classroom.common.util.ExcelUtil;
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

@Service("bizClassExcelService")
public class BizClassExcelService extends BaseService{

    @Autowired
    private BizSchoolService bizSchoolService;

    @Autowired
    private BizClassService bizClassService;

    @Autowired
    private BizCheckWeightService bizCheckWeightService;


    public Boolean initClassExcel(File file) throws Exception{

        XSSFWorkbook workBook = new XSSFWorkbook();
        XSSFSheet sheet1 = workBook.createSheet("sheet1");
        XSSFSheet sheet2 = workBook.createSheet("sheet2");

        writeSheet1(sheet1);
        writeSHeet2(sheet2);
        workBook.write(new FileOutputStream(file));
        return true;
    }

    private void writeSheet1(XSSFSheet sh1) {
        XSSFRow row1=sh1.createRow(0);
        row1.createCell(0).setCellValue("学校ID");
        row1.createCell(1).setCellValue("年级");
        row1.createCell(2).setCellValue("班级");
        row1.createCell(3).setCellValue("开始时间(yyyy.MM.dd)");
        row1.createCell(4).setCellValue("结束时间(yyyy.MM.dd)");
    }

    private void writeSHeet2(XSSFSheet sheet) throws Exception{

        sheet.createRow(0).createCell(0).setCellValue("ID 映射关系表");
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,2));

        List<School> schools = bizSchoolService.getAllSchoolInfo();
        if(schools==null)throw new BizException("查询学校列表错误");
        XSSFRow row = sheet.createRow(1);
        row.createCell(0).setCellValue("学校ID");
        row.createCell(1).setCellValue("学校名称");
        for(int i=0;i<schools.size();i++){
            School school = schools.get(i);
            XSSFRow row2 = sheet.createRow(i+2);
            row2.createCell(0).setCellValue(school.getId());;
            row2.createCell(1).setCellValue(school.getSchoolName());
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    public Boolean insertClassInfo(File file) throws Exception{
        List<ClassDomain> classDomains = insertExcelClassInfos(file);
        for(int i=0;i<classDomains.size();i++) {
            ClassDomain classDomain = classDomains.get(i);
            if (classDomain == null) throw new BizException("Excel解析异常");
            if(bizCheckWeightService.checkClassNo(classDomain)){
                bizClassService.addClassService(classDomain);
            }else{
                throw new BizException("第"+(i+2)+"行班级已存在,请查证后重新导入!");
            }
        }
        return true;
    }


    @Transactional(rollbackFor = Throwable.class)
    public List<ClassDomain> insertExcelClassInfos(File file) throws Exception{
        if(!file.isFile()){
            throw new BizException("文件上传失败，请联系管理员");
        }

        InputStream is = new FileInputStream(file);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        XSSFSheet ClassSheet = xssfWorkbook.getSheet("sheet1");

        List<ClassDomain> classDomains = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");


        for(int rowNum=1;rowNum<=ClassSheet.getLastRowNum();rowNum++){
            XSSFRow row= ClassSheet.getRow(rowNum);
            if(row!=null){
                ClassDomain classDomain = new ClassDomain();
                XSSFCell schoolId = row.getCell(0);
                XSSFCell grade = row.getCell(1);
                XSSFCell classNo = row.getCell(2);
                XSSFCell termStartTime = row.getCell(3);
                XSSFCell termEndTime = row.getCell(4);

                try {
                    classDomain.setSchoolId(Long.parseLong(ExcelUtil.getValueByCell(schoolId)));
                    classDomain.setClassNo(Byte.parseByte(ExcelUtil.getValueByCell(classNo)));
                    classDomain.setGrade(Byte.parseByte(ExcelUtil.getValueByCell(grade)));
                    if(termStartTime != null)classDomain.setTermStartTime(sdf.parse(ExcelUtil.getValueByCell(termStartTime)));
                    if(termEndTime != null)classDomain.setTermEndTime(sdf.parse(ExcelUtil.getValueByCell(termEndTime)));

                    classDomains.add(classDomain);
                }catch (Exception e){
                    throw new BizException("表格中第" + (rowNum+1) + "行参数不合法");
                }
            }
        }
        return classDomains;
    }
}
