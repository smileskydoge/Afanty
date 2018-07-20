package com.booksroo.classroom.common.util;

import com.booksroo.classroom.common.domain.Student;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class StudentExcelUtil {

    /**
     * excel 2003
     */
    public static final String OFFICE_EXCEL_V2003_SUFFIX = "xls";
    /**
     * excel 2007
     */
    public static final String OFFICE_EXCEL_V2007_SUFFIX = "xlsx";
    /**
     * excel 2010
     */
    public static final String OFFICE_EXCEL_V2010_SUFFIX = "xlsx";

    public static final String EMPTY = "";

    public static final String DOT = ".";

    public static final String NOT_EXCEL_FILE = " is Not a Excel file!";

    public static final String PROCESSING = "Processing...";

    /**
     * Check which version of The Excel file is. Throw exception if Excel file path is illegal.
     *
     * @param path the Excel file
     * @return a list that contains Students from Excel.
     * @throws IOException
     */
    public static List<Student> readExcel(String path) throws IOException, IllegalArgumentException {


        String suffiex = getSuffiex(path);

        if (OFFICE_EXCEL_V2003_SUFFIX.equals(suffiex)) {
            return readXls(path);
        } else if (OFFICE_EXCEL_V2007_SUFFIX.equals(suffiex)) {
            return readXlsx(path);
        } else if (OFFICE_EXCEL_V2010_SUFFIX.equals(suffiex)) {
            return readXlsx(path);
        } else {
            throw new IllegalArgumentException(path + NOT_EXCEL_FILE);
        }
    }

    /**
     * Read the Excel 2017 or 2010
     *
     * @param path the path of the excel file
     * @return
     * @throws IOException
     */
    public static List<Student> readXlsx(String path) throws IOException {
        System.out.println(PROCESSING + path);
        InputStream is = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        Student student = null;
        List<Student> list = new ArrayList<Student>();
        // Read the Sheet
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
//                    student = new Student();
//                    XSSFCell no = xssfRow.getCell(0);
//                    XSSFCell name = xssfRow.getCell(1);
//                    XSSFCell age = xssfRow.getCell(2);
//                    XSSFCell score = xssfRow.getCell(3);
//                    student.setNo(getValue(no));
//                    student.setName(getValue(name));
//                    student.setAge(getValue(age));
//                    student.setScore(Float.valueOf(getValue(score)));
//                    list.add(student);
                }
            }
        }
        return list;
    }

    /**
     * Read the Excel 2003
     *
     * @param path the path of the Excel
     * @return
     * @throws IOException
     */
    public static List<Student> readXls(String path) throws IOException {
        System.out.println(PROCESSING + path);
        InputStream is = new FileInputStream(path);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        Student student = null;
        List<Student> list = new ArrayList<Student>();
        // Read the Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
//                    student = new Student();
//                    HSSFCell no = hssfRow.getCell(0);
//                    HSSFCell name = hssfRow.getCell(1);
//                    HSSFCell age = hssfRow.getCell(2);
//                    HSSFCell score = hssfRow.getCell(3);
//                    student.setNo(getValue(no));
//                    student.setName(getValue(name));
//                    student.setAge(getValue(age));
//                    student.setScore(Float.valueOf(getValue(score)));
//                    list.add(student);
                }
            }
        }
        return list;
    }

    /**
     * @param xssfRow
     * @return
     */
    @SuppressWarnings("static-access")
    private static String getValue(XSSFCell xssfRow) {
//        if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
//            return String.valueOf(xssfRow.getBooleanCellValue());
//        } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
//            return String.valueOf(xssfRow.getNumericCellValue());
//        } else {
//            return String.valueOf(xssfRow.getStringCellValue());
//        }
        String value ;

        switch (xssfRow.getCellType()) {
            case XSSFCell.CELL_TYPE_NUMERIC: // 数字
                //如果为时间格式的内容
                if (HSSFDateUtil.isCellDateFormatted(xssfRow)) {
                    //注：format格式 yyyy-MM-dd hh:mm:ss 中小时为12小时制，若要24小时制，则把小h变为H即可，yyyy-MM-dd HH:mm:ss
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    value=sdf.format(HSSFDateUtil.getJavaDate(xssfRow.
                            getNumericCellValue())).toString();
                    break;
                } else {
                    value = new DecimalFormat("0").format(xssfRow.getNumericCellValue());
                }
                break;
            case XSSFCell.CELL_TYPE_STRING: // 字符串
                value = xssfRow.getStringCellValue();
                break;
            case XSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                value = xssfRow.getBooleanCellValue() + "";
                break;
            case XSSFCell.CELL_TYPE_FORMULA: // 公式
                value = xssfRow.getCellFormula() + "";
                break;
            case XSSFCell.CELL_TYPE_BLANK: // 空值
                value = "";
                break;
            case XSSFCell.CELL_TYPE_ERROR: // 故障
                value = "非法字符";
                break;
            default:
                value = "未知类型";
                break;
        }
        return value;

    }

    @SuppressWarnings("static-access")
    private static String getValue(HSSFCell hssfCell) {
//        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
//            return String.valueOf(hssfCell.getBooleanCellValue());
//        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
//            return String.valueOf(hssfCell.getNumericCellValue());
//        } else {
//            return String.valueOf(hssfCell.getStringCellValue());
//        }

        String value ;

        switch (hssfCell.getCellType()) {
            case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                //如果为时间格式的内容
                if (HSSFDateUtil.isCellDateFormatted(hssfCell)) {
                    //注：format格式 yyyy-MM-dd hh:mm:ss 中小时为12小时制，若要24小时制，则把小h变为H即可，yyyy-MM-dd HH:mm:ss
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    value=sdf.format(HSSFDateUtil.getJavaDate(hssfCell.
                            getNumericCellValue())).toString();
                    break;
                } else {
                    value = new DecimalFormat("0").format(hssfCell.getNumericCellValue());
                }
                break;
            case HSSFCell.CELL_TYPE_STRING: // 字符串
                value = hssfCell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                value = hssfCell.getBooleanCellValue() + "";
                break;
            case HSSFCell.CELL_TYPE_FORMULA: // 公式
                value = hssfCell.getCellFormula() + "";
                break;
            case HSSFCell.CELL_TYPE_BLANK: // 空值
                value = "";
                break;
            case HSSFCell.CELL_TYPE_ERROR: // 故障
                value = "非法字符";
                break;
            default:
                value = "未知类型";
                break;
        }
        return value;
    }

    public static String getSuffiex(String path) {

        int index = path.lastIndexOf(DOT);
        if (index == -1) {
            return EMPTY;
        }
        return path.substring(index + 1, path.length());
    }
}
