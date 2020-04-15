package util;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;

public class FileUtils {
	/**
     * ���շָ�������ַ�ַ����и,Ȼ��ƴװ��FIle����
     * @param files �ַ���
     * @param split �ָ���,����Ϊ����
     * @return
     */
	 public File[] getFileArrayByString(String files, String split){
	        String[] aa = files.split(split);
	        File[] attachments = new File[aa.length];
	        for(int i=0;i<aa.length;i++){
	            attachments[i] = new File(aa[i]);
	        }
	        return attachments;
	    }

	    public String getFilePrefix(MultipartFile file){
	        String fileName=file.getOriginalFilename();
	        String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
	        return prefix;
	    }


}
