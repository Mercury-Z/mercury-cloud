package cn.mercury.mercurycloud.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Controller

public class FileController {
    @RequestMapping("/saveFile")
    @ResponseBody
    public String saveFile(MultipartFile file) throws IOException {
        InputStream in = file.getResource().getInputStream();
        RandomAccessFile accessFile = new RandomAccessFile(new File("test"),"rw");
        FileOutputStream fos = new FileOutputStream(new File("test2"));
        int i=-1;

        while ((i = in.read())!=-1){
            fos.write(i);
            accessFile.write(i);
        }
        accessFile.close();
        fos.close();
        return"save Complete";
    }
}
