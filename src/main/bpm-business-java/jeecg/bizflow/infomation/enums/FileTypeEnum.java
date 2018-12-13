package jeecg.bizflow.infomation.enums;

import org.jeecgframework.core.util.oConvertUtils;

public enum FileTypeEnum {
	dir("0","文件夹"),
	txt("txt","文本"),
	doc("doc","Word"),
	docx("docx","Word"),
	xls("xls","Excel"),
	xlsx("xlsx","Excel"),
	pdf("pdf","PDF"),
	ppt("ppt","PPT"),
	zip("zip","压缩文件"),
	mp4("mp4","视频"),
	mp3("mp3","音频"),
	cad("cad","工业图纸"),
	bmp("bmp","图片"),
	jpg("jpg","图片"),
	png("png","图片"),
	tiff("tiff","图片"),
	gif("gif","图片"),
	pcx("pcx","图片"),
	tga("tga","图片"),
	exif("exif","图片"),
	fpx("fpx","图片"),
	svg("svg","图片"),
	psd("psd","图片"),
	cdr("cdr","图片"),
	pcd("pcd","图片"),
	dxf("dxf","图片"),
	eps("eps","图片"),
	ai("ai","图片"),
	raw("raw","图片"),
	WMF("wmf","图片"),
	webp("webp","图片"),
	ufo("ufo","图片"),
	unkown("unkown","未知类型");
	
	String ext;
	String type;

	FileTypeEnum(String ext,String type){
		this.ext = ext;
		this.type = type;
	}
	
	
	public static String getTypeByext(String ext){
		if(oConvertUtils.isNotEmpty(ext)){
			for (FileTypeEnum e : FileTypeEnum.values()) {
				if(e.ext.equals(ext.toLowerCase())){
					return e.type;
				}
			}
		}
		return unkown.type;
	}
}
