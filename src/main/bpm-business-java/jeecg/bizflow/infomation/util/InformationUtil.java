package jeecg.bizflow.infomation.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.jeecgframework.core.util.ResourceUtil;
import jeecg.bizflow.infomation.entity.JoaFileInfoEntity;

/**
 * 信息管理相关工具类
 * @author taoYan
 * @since 2018年8月8日
 */
public class InformationUtil {
	
	/**
	 * 根据传进来的文件信息集合和压缩文件路径进行 文件压缩
	 * @author taoYan
	 * @since 2018年8月8日
	 */
	 public static void zipMultiFile(List<JoaFileInfoEntity> list,String destFilepath) throws IOException{
		   ZipOutputStream out = new ZipOutputStream(new File(destFilepath));
		   String root =  ResourceUtil.getConfigByName("webUploadpath");
		   byte[] buffer = new byte[1024]; 
		   for (JoaFileInfoEntity info : list) {
			   File file = new File(root+File.separator+info.getPath());
			   FileInputStream fis = new FileInputStream(file);
			   out.putNextEntry(new ZipEntry(info.getName()+"."+info.getExt()));
			   //设置压缩文件内的字符编码，不然会变成乱码    
			   out.setEncoding("GBK");
			   int len;
			   while ((len = fis.read(buffer)) > 0) {    
	               out.write(buffer, 0, len);    
	           }
			   out.closeEntry();    
	           fis.close();   
		   }
		   out.close();    
	   }

}
