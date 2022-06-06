package com.example.web;


import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


@RestController
public class UploaderController {

    @PostMapping("/uploader")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {
        // 获取上传的文件名称
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        //文件上传的地址
        String path = ResourceUtils.getURL("classpath:").getPath()+"static/assets/images/";
        //修改路径

        String realPath = path.replace('/', '\\').substring(1,path.length());
        File dest = new File(realPath + fileName);
        System.out.println(dest);
        String a="D:\\Java\\Project\\campus\\user\\src\\main\\resources\\static\\assets\\images\\";
//        System.out.println(dest);
        String str2="";
        try {
            // 上传的文件被保存了
            file.transferTo(dest);
            FileInputStream fis=new FileInputStream(dest);
            FileOutputStream fos=new FileOutputStream(a+fileName);
            int by;
            while((by=fis.read())!=-1){
                fos.write(by);
            }
            fis.close();
            fos.close();
            // 打印日志
            System.out.println("上传成功，当前上传的文件保存在 "+realPath + fileName);
            // 自定义返回的统一的 JSON 格式的数据，可以直接返回这个字符串也是可以的。
            String da =realPath + fileName;
            //截取assets字符串前面的
            String str1 = da.substring(0, da.indexOf("assets"));
            //截取assets字符串后面的
            str2=da.substring(str1.length());

        } catch (IOException e) {
            System.out.println("上传错误");
            return null;
        }
        // 待完成 —— 文件类型校验工作
        return str2;
    }

}
