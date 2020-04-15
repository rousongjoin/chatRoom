package util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class UploadUtils {
	
	public static String upload(HttpServletRequest request, String folder, String name){
        FileUtils fileUtil = new FileUtils();
        String file_url = "";
        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if(multipartResolver.isMultipart(request)){
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
            //取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while(iter.hasNext()){
                //取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                String prefix = fileUtil.getFilePrefix(file);
                if(file != null){
                    //取得当前上传文件的文件名称
                    String myFileName = file.getOriginalFilename();
                    //如果名称不为"",说明该文件存在，否则说明该文件不存在
                    if(myFileName.trim() !=""){
                        System.out.println(myFileName);
                        //重命名上传后的文件名
                        String fileName =  name + "." + prefix;
                        //定义上传路径,格式为 upload/Amayadream/Amayadream.jpg
                        String path = request.getServletContext().getRealPath("/") + folder + "/" + name;
                        File localFile = new File(path, fileName);
                        if(!localFile.exists()){
                            localFile.mkdirs();
                        }
                        try {
                            file.transferTo(localFile);
                            file_url = folder + "/" + name + "/" + fileName;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return file_url;
    }
	
	public Map<String,String> File_upload(HttpServletRequest request,String filepath) {
        //判断上传的表单是否为multipart/form-data类型
        if (ServletFileUpload.isMultipartContent(request)) {

            try {
                //1.创建DiskFileItemFactory对象,设置缓冲区大小和临时目录文件
                DiskFileItemFactory factory = new DiskFileItemFactory();
                //2.创建ServletFileUpload对象，并设置上传文件的大小限制
                ServletFileUpload sfu = new ServletFileUpload(factory);
                sfu.setSizeMax(10 * 1024 * 1024);//以byte为单位 1024byte->1KB*1024=1M->1M*10=10M
                sfu.setHeaderEncoding("utf-8");

                //3.调用ServletFileUpload.parseRequest方法来解析对象，得到一个保存了所有上传内容的List对象
                List<FileItem> fileItemList = sfu.parseRequest(request);
                Iterator<FileItem> fileItems = fileItemList.iterator();

                //创建一个Map集合，用于添加表单元素
                Map<String, String> map = new TreeMap<String, String>();

                //4.遍历fileItems，每迭代一个对象，调用其isFormField方法判断是否是上传文件
                while ((fileItems.hasNext())) {
                    FileItem fileItem = fileItems.next();
                    try{
                        //普通的表单元素
                        if (fileItem.isFormField()) {
                            String name = fileItem.getFieldName();//name的属性值
                            String value = fileItem.getString("utf-8");//name对应的value值
                            //添加进Map集合中
                            map.put(name, value);
                        } else {//否则即为<input type="file">上传的文件
                            if(fileItem.getName()==null||fileItem.getFieldName()==null){
                                map.put("profilehead","empty");
                            }else {
                                String fileName = fileItem.getName();// 文件名称
                                System.out.println("原文件名：" + fileName);// Koala.jpg

                                String suffix = fileName.substring(fileName.lastIndexOf('.'));
                                System.out.println("扩展名：" + suffix);// .jpg

                                // 新文件名（唯一）
                                String newFileName = new Date().getTime() + suffix;
                                System.out.println("新文件名：" + newFileName);// image\1478509873038.jpg

                                //将文件名存入到数组中
                                map.put("profilehead", newFileName);

                                // 5. 调用FileItem的write()方法，写入文件
                                String context = filepath+newFileName ;
                                System.out.println("图片的路径为"+context);
                                File file = new File(context);
                                System.out.println(file.getAbsolutePath());
                                fileItem.write(file);

                                //判断该文件是否为head_img下默认的头像，如果不是才执行删除
                                if(!fileName.contains("empty")|| !newFileName.contains("empty")){
                                    // 6. 调用FileItem的delete()方法，删除临时文件
                                    fileItem.delete();
                                }

                            }

                        }
                    }catch (StringIndexOutOfBoundsException e ){
                        //若为空指指针
                        //未上传图片则按原来的图片显示
                        //设置为false,在进行数据库操作时不对图片进行操作
                        System.out.println("出现异常");
                        map.put("profilehead","empty");
                        e.printStackTrace();
                    }
                }
                return map;
            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return  null;
    }

}
