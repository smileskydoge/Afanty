package com.booksroo.classroom.common.enums;

/**
 * @author liujianjian
 * @date 2018/6/3 17:41
 */

public enum FileEnum {

    PPT(1, "ppt", ".ppt"), PPTX(1, "pptx", ".pptx"), IMAGE(2, "image", ".png/.jpeg/.jpg/.gif/.bmp/.webp/.svg./.tiff"), JPEG(3, "jpeg", ".jpeg");
    private Integer type;//1-ppt，2-图片
    private String name;
    private String suffix;

    FileEnum() {
    }

    FileEnum(Integer type, String name, String suffix) {
        this.type = type;
        this.name = name;
        this.suffix = suffix;
    }

    public static boolean isPPT(String suffix) {
        return FileEnum.PPT.getSuffix().equalsIgnoreCase(suffix);
    }

    public static boolean isPPT(int type) {
        return FileEnum.PPT.getType() == type || FileEnum.PPTX.getType() == type;
    }

    public static boolean isPPTX(String suffix) {
        return FileEnum.PPTX.getSuffix().equalsIgnoreCase(suffix);
    }

    public static boolean isImage(String suffix) {
        return FileEnum.IMAGE.getSuffix().contains(suffix);
    }

    public static Integer getType(String suffix) {
        for (FileEnum e : FileEnum.values()) {
            String[] arr = e.getSuffix().split("/");
            for (String s : arr) {
                if (s.equalsIgnoreCase(suffix)) return e.getType();
            }
        }

        return -1;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
