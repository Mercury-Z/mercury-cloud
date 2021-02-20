package cn.mercury.mercurycloud.utils;

import org.apache.commons.codec.binary.Hex;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.security.MessageDigest;
@Configuration
@ConfigurationProperties(prefix = "file-util.config")
public class FileUtil {
    //保存文件的文件夹
    private static String path;
    //缓存的大小 MB
    private static Integer byteSize;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getByteSize() {
        return byteSize;
    }

    public void setByteSize(Integer byteSize) {
        this.byteSize = byteSize;
    }

    public static boolean saveFileAndCheckMD5(InputStream inputStream,String fileName,String postfix) {
        //FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        File file = null;
        try {
            MessageDigest MD5 = MessageDigest.getInstance("MD5");
        //    fileInputStream = new FileInputStream(file);
            file = new File(path,fileName/*+postfix*/);
            if (!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
            fileOutputStream = new FileOutputStream(file);
            byte[] buffer = new byte[byteSize*1024*1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer,0,length);
                MD5.update(buffer, 0, length);
            }
            //比较实际保存文件的MD5值与前端传来的MD5值
            String realMd5 = new String(Hex.encodeHex(MD5.digest()));
            boolean flag = realMd5.equalsIgnoreCase(fileName);
            if (!flag){
                throw new RuntimeException("文件保存出现异常，文件md5校验失败");
            }
            return true;
        } catch (Exception e) {
            //出现错误删除掉正在保存的文件
            try {
                if (inputStream != null){
                    inputStream.close();
                }
                if (fileOutputStream != null){
                    fileOutputStream.close();
                }
                file.delete();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (inputStream != null){
                    inputStream.close();
                }
                if (fileOutputStream != null){
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static boolean downloadFile(OutputStream outputStream,String fileName,String postfix) {
        FileInputStream fileInputStream = null;
        File file = null;
        try {
            MessageDigest MD5 = MessageDigest.getInstance("MD5");
            file = new File(path,fileName/*+postfix*/);
            if (!file.exists()){
                return false;
            }
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[byteSize*1024*1024];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer,0,length);
            }
            return true;
        } catch (Exception e) {
            try {
                if (fileInputStream != null){
                    fileInputStream.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (fileInputStream != null){
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static FileType getFileType(String type){
            if (".chm".equals(type)||".txt".equals(type)||".xmind".equals(type)||".xlsx".equals(type)||".md".equals(type)
                    ||".doc".equals(type)||".docx".equals(type)||".pptx".equals(type)
                    ||".wps".equals(type)||".word".equals(type)||".html".equals(type)||".pdf".equals(type)){
                return  FileType.document;
            }else if (".bmp".equals(type)||".gif".equals(type)||".jpg".equals(type)||".ico".equals(type)||".vsd".equals(type)
                    ||".pic".equals(type)||".png".equals(type)||".jepg".equals(type)||".jpeg".equals(type)||".webp".equals(type)
                    ||".svg".equals(type)){
                return FileType.image;
            } else if (".avi".equals(type)||".mov".equals(type)||".qt".equals(type)
                    ||".asf".equals(type)||".rm".equals(type)||".navi".equals(type)||".wav".equals(type)
                    ||".mp4".equals(type)||".mkv".equals(type)||".webm".equals(type)){
                return FileType.video;
            } else if (".mp3".equals(type)||".wma".equals(type)){
                return FileType.audio;
            } else {
                return FileType.other;
            }
    }

}
