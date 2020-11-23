package cn.mercury.mercurycloud.controller;

import jdk.nashorn.internal.ir.RuntimeNode;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import java.io.*;

@Controller

public class FileController {
    @RequestMapping("/saveFile")
    @ResponseBody
    public String saveFile(HttpServletRequest request,MultipartFile file) throws IOException {
        String fileName = request.getParameter("fileName");
        System.out.println(fileName);
        InputStream in = file.getResource().getInputStream();

     //   RandomAccessFile accessFile = new RandomAccessFile(new File(fileName),"rw");
        FileOutputStream fos = new FileOutputStream(new File(fileName));
        int i=-1;
        byte[] bytes = new byte[1024*1024];
        while ((i = in.read(bytes))!=-1){
            fos.write(bytes,0,i);
         //   accessFile.write(bytes,0,i);
        }
      //  accessFile.close();
        fos.close();

        return"save Complete";
    }
}
