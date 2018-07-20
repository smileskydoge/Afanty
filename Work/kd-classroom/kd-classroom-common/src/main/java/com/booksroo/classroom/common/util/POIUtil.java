package com.booksroo.classroom.common.util;

import com.booksroo.classroom.common.enums.FileEnum;
import org.apache.poi.hslf.usermodel.*;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.sl.usermodel.Slide;
import org.apache.poi.xslf.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liujianjian
 * @date 2018/5/29 16:26
 */
public class POIUtil {

    public static void main(String[] args) throws Exception {

    }

    /**
     * ppt2003 文档的转换 后缀名为.ppt
     *
     * @param pptFile
     * @return
     */
    public static List<File> pptToImg(InputStream pptFile, String tempPath) throws Exception {
        HSLFSlideShow ppt = new HSLFSlideShow(new HSLFSlideShowImpl(new POIFSFileSystem(POIFSFileSystem.createNonClosingInputStream(pptFile))));
        Dimension pgsize = ppt.getPageSize();
        List<HSLFSlide> slides = ppt.getSlides();
        List<File> list = new ArrayList<>();

        for (int i = 0; i < slides.size(); i++) {
            HSLFSlide slide = slides.get(i);
            // 解决乱码问题
            List<HSLFShape> shapes = slide.getShapes();
            for (HSLFShape shape : shapes) {
                if (shape instanceof HSLFTextShape) {
                    HSLFTextShape sh = (HSLFTextShape) shape;
                    List<HSLFTextParagraph> textParagraphs = sh.getTextParagraphs();
                    for (HSLFTextParagraph hslfTextParagraph : textParagraphs) {
                        List<HSLFTextRun> textRuns = hslfTextParagraph.getTextRuns();
                        for (HSLFTextRun hslfTextRun : textRuns) {
                            hslfTextRun.setFontFamily("宋体");
                        }
                    }
                }
            }
            list.add(genImageFile(pgsize, slide, tempPath));
        }
        closeStream(pptFile);
        return list;
    }

    /**
     * ppt2007 文档的转换 后缀名为.pptx
     *
     * @param pptFile
     * @return
     */
    public static List<File> pptxToImg(InputStream pptFile, String tempPath) throws Exception {

        // read the .pptx file
        XMLSlideShow ppt = new XMLSlideShow(POIFSFileSystem.createNonClosingInputStream(pptFile));
        Dimension pgsize = ppt.getPageSize();

        List<XSLFSlide> slides = ppt.getSlides();
        List<File> list = new ArrayList<>();

        for (int i = 0; i < slides.size(); i++) {

            XSLFSlide slide = slides.get(i);
            // 解决乱码问题
            List<XSLFShape> shapes = slide.getShapes();
            for (XSLFShape shape : shapes) {
                if (shape instanceof XSLFTextShape) {
                    XSLFTextShape sh = (XSLFTextShape) shape;
                    List<XSLFTextParagraph> textParagraphs = sh.getTextParagraphs();
                    for (XSLFTextParagraph xslfTextParagraph : textParagraphs) {
                        List<XSLFTextRun> textRuns = xslfTextParagraph.getTextRuns();
                        for (XSLFTextRun xslfTextRun : textRuns) {
                            xslfTextRun.setFontFamily("宋体");
                        }
                    }
                }
            }

            list.add(genImageFile(pgsize, slide, tempPath));
        }
        closeStream(pptFile);
        return list;
    }

    private static File genImageFile(Dimension pgsize, Slide slide, String tempPath) throws Exception {

        BufferedImage img = genBufferedImage(pgsize, slide);

        File imgFile = createTempFile(tempPath);
        // 这里设置图片的存放路径和图片的格式(jpeg,png,bmp等等),注意生成文件路径
        FileOutputStream out = new FileOutputStream(imgFile);
        // 写入到图片中去
        ImageIO.write(img, FileEnum.JPEG.getName(), out);
        out.close();
        return imgFile;
    }


    private static byte[] genImageBytes(Dimension pgsize, Slide slide, String tempPath) throws Exception {
        FileOutputStream os = genImageOS(pgsize, slide, tempPath);
        byte[] b = new byte[1024];
        os.write(b);
        os.close();
        return b;
    }

    private static FileOutputStream genImageOS(Dimension pgsize, Slide slide, String tempPath) throws Exception {
        //根据幻灯片大小生成图片
        BufferedImage img = genBufferedImage(pgsize, slide);
        File imgFile = createTempFile(tempPath);

        // 这里设置图片的存放路径和图片的格式(jpeg,png,bmp等等),注意生成文件路径
        FileOutputStream out = new FileOutputStream(imgFile);
        // 写入到图片中去
        ImageIO.write(img, FileEnum.JPEG.getName(), out);
        return out;
    }

    private static BufferedImage genBufferedImage(Dimension pgsize, Slide slide) {
        //根据幻灯片大小生成图片
        BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = img.createGraphics();

        graphics.setPaint(Color.white);
        graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
        // 最核心的代码
        slide.draw(graphics);
        return img;
    }

    private static File createTempFile(String tempPath) {
        //图片将要存放的路径
        File folder = new File(tempPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String absolutePath = tempPath + "/" + System.currentTimeMillis() + FileEnum.JPEG.getSuffix();
        File imgFile = new File(absolutePath);
//        if (imgFile.exists()) {
//                    continue;
//        }
        return imgFile;
    }

    private static void closeStream(InputStream is) throws Exception {
        is.close();
    }

}
