package cn.mercury.mercurycloud.controller;

import cn.mercury.mercurycloud.pojo.*;
import cn.mercury.mercurycloud.pojo.File;
import cn.mercury.mercurycloud.utils.FileUtil;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class FileController extends BaseController{
    @RequestMapping("/saveFile")
    @ResponseBody
    @Transactional
    public Map<String, Object> saveFile(MultipartFile file , Integer Fid , String fileName ,String MD5) {
        Map<String,Object> map = new HashMap<>();
        User loginUser = (User) session.getAttribute("loginUser");
        boolean saveFlag = false;
        FileMd5 fileMd5 = null;
        if (file != null){
            //从接受的文件中获取文件名
            fileName = file.getOriginalFilename();
            //保存文件
            try {
                InputStream in = file.getResource().getInputStream();
                // FileOutputStream fos = new FileOutputStream(name);
                saveFlag = FileUtil.saveFileAndCheckMD5(in,MD5,"");//文件保存名改为前端传来的MD5值
           //     System.out.println(MD5);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!saveFlag){
                //保存后的文件实际MD5值与前端传来的不一致
                throw new RuntimeException("保存文件发生异常");
            }
            //文件保存成功，将文件md5值记录到md5表
            fileMd5 = FileMd5.builder().fileMd5(MD5).build();
            fileMd5Service.insert(fileMd5);
            if (fileMd5.getFileMd5Id()==null){
                throw new RuntimeException("保存md5值出现错误");
            }
        }
        //获取文件后缀
        int index = fileName.lastIndexOf(".");
        String postfix = fileName.substring(index);
        //获取文件类型
        Integer type = FileUtil.getFileType(postfix).getValue();
        //获取文件名
        String name = fileName.substring(0,index);
        //查找是否有重名文件存在于该文件夹下
        boolean  exists = fileService. existsFileByFileNameAndParentFolderId(name, name,Fid);
        //同名文件已存在
        if (exists){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String date;
            while (exists) {
                date = simpleDateFormat.format(new Date());
                name = name + "_" + date;
                exists = fileService.existsFileByFileNameAndParentFolderId(name, name, Fid);
            }
        }
        //获取文件大小getSize获得的大小单位为b
        String size_str = null;
        if (file != null){
            size_str = String.valueOf(file.getSize()/1024.0);
        }else {
            size_str = String.valueOf(file.getSize()/1024.0);
        }
        Integer size = Integer.parseInt(size_str.substring(0,size_str.lastIndexOf(".")));

        //构造文件实体
        File nowFile = File.builder().myFileName(name).fileStoreId(loginUser.getFileStoreId()).myFileMd5Id(fileMd5.getFileMd5Id())
                .downloadTime(0).uploadTime(new Date()).parentFolderId(Fid).size(size).type(type).postfix(postfix).build();
        //将文件记录进数据库
        fileService.insert(nowFile);
        if (nowFile.getMyFileId()==null){
            throw new RuntimeException("记录文件进入数据库失败，回滚数据库");
        }

//        InputStream in = file.getResource().getInputStream();
//        System.out.println();
//        //   RandomAccessFile accessFile = new RandomAccessFile(new File(fileName),"rw");
//        FileOutputStream fos = new FileOutputStream(new File(fileName));
//        int i=-1;
//        byte[] bytes = new byte[1024*1024];
//        while ((i = in.read(bytes))!=-1){
//            fos.write(bytes,0,i);
//         //   accessFile.write(bytes,0,i);
//        }
//      //  accessFile.close();
//        fos.close();
        map.put("code",200);
        return map;
    }
    @RequestMapping("/uploadSaveFile")
    @ResponseBody
    @Transactional
    public Map<String, Object> uploadSaveFile(MultipartFile file , Integer Fid , String MD5) {
        Map<String,Object> map = new HashMap<>();
        User loginUser = (User) session.getAttribute("loginUser");

        FileStore fileStore = fileStoreService.getFileStoreByFileStoreId(loginUser.getFileStoreId());
        if (fileStore.getPermission()!=0){
            map.put("code",499);
            return  map;
        }

        boolean saveFlag = false;
        FileMd5 fileMd5 = null;

        //从接受的文件中获取文件名
        String fileName = file.getOriginalFilename();
        //获取文件后缀
        int index = fileName.lastIndexOf(".");
        String postfix = fileName.substring(index);
            //保存文件
            try {
                InputStream in = file.getResource().getInputStream();
                // FileOutputStream fos = new FileOutputStream(name);
                saveFlag = FileUtil.saveFileAndCheckMD5(in,MD5,postfix);//文件保存名改为前端传来的MD5值
                //     System.out.println(MD5);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!saveFlag){
                //保存后的文件实际MD5值与前端传来的不一致
                throw new RuntimeException("保存文件发生异常");
            }
            //文件保存成功，将文件md5值记录到md5表
            fileMd5 = FileMd5.builder().fileMd5(MD5).uploadCount(0).build();
            fileMd5Service.insert(fileMd5);
            if (fileMd5.getFileMd5Id()==null){
                throw new RuntimeException("保存md5值出现错误");
            }
        //获取文件类型
        Integer type = FileUtil.getFileType(postfix).getValue();
        //获取文件名
        String name = fileName.substring(0,index);
        //查找是否有重名文件存在于该文件夹下  加上文件后缀一起判断
        boolean  exists = fileService. existsFileByFileNameAndParentFolderId(name,postfix,Fid);
        //同名文件已存在
        if (exists){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String date;
            while (exists) {
                date = simpleDateFormat.format(new Date());
                name = name + "_" + date;
                exists = fileService.existsFileByFileNameAndParentFolderId(name,postfix, Fid);
            }
        }
        //获取文件大小getSize获得的大小单位为b
        String size_str = String.valueOf(file.getSize()/1024.0);
        Integer size = Integer.parseInt(size_str.substring(0,size_str.lastIndexOf(".")));

        //构造文件实体
        File nowFile = File.builder().myFileName(name).fileStoreId(loginUser.getFileStoreId()).myFileMd5Id(fileMd5.getFileMd5Id())
                .downloadTime(0).uploadTime(new Date()).parentFolderId(Fid).size(size).type(type).postfix(postfix).build();
        //将文件记录进数据库
        fileService.insert(nowFile);
        if (nowFile.getMyFileId()==null){
            throw new RuntimeException("记录文件进入数据库失败，回滚数据库");
        }
        //更新仓库信息
        fileStore.setCurrentSize(fileService.countAllFileSizeByFileStoreId(loginUser.getFileStoreId()));
        if (fileStore.getCurrentSize()>=fileStore.getMaxSize()){
            fileStore.setPermission(1);
        }
        fileStoreService.updateFileStore(fileStore);
        map.put("code",200);
        return map;
    }

    @RequestMapping("/instantSaveFile")
    @ResponseBody
    @Transactional
    public Map<String, Object> instantSaveFile(Integer Fid ,String fileName ,String MD5 ,Integer size) {
        Map<String,Object> map = new HashMap<>();
        User loginUser = (User) session.getAttribute("loginUser");

        FileStore fileStore = fileStoreService.getFileStoreByFileStoreId(loginUser.getFileStoreId());
        if (fileStore.getPermission()!=0){
            map.put("code","499");
            return  map;
        }

        //获取文件后缀
        int index = fileName.lastIndexOf(".");
        String postfix = fileName.substring(index);
        //获取文件类型
        Integer type = FileUtil.getFileType(postfix).getValue();
        //获取文件名
        String name = fileName.substring(0,index);
        //查找是否有重名文件存在于该文件夹下  加上文件后缀一起判断
        boolean  exists = fileService. existsFileByFileNameAndParentFolderId(name,postfix,Fid);
        //同名文件已存在
        if (exists){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String date;
            while (exists) {
                date = simpleDateFormat.format(new Date());
                name = name + "_" + date;
                exists = fileService.existsFileByFileNameAndParentFolderId(name,postfix, Fid);
            }
        }
        //获取文件大小getSize获得的大小单位为b
        String size_str = String.valueOf(size/1024.0);
        size = Integer.parseInt(size_str.substring(0,size_str.lastIndexOf(".")));
        //获取文件MD5ID
        FileMd5 fileMd5 = fileMd5Service.findByMd5(MD5);
        //构造文件实体
        File nowFile = File.builder().myFileName(name).fileStoreId(loginUser.getFileStoreId()).myFileMd5Id(fileMd5.getFileMd5Id())
                .downloadTime(0).uploadTime(new Date()).parentFolderId(Fid).size(size).type(type).postfix(postfix).build();
        //将文件记录进数据库
        fileService.insert(nowFile);
        if (nowFile.getMyFileId()==null){
            throw new RuntimeException("记录文件进入数据库失败，回滚数据库");
        }
        //更新仓库信息
        fileStore.setCurrentSize(fileService.countAllFileSizeByFileStoreId(loginUser.getFileStoreId()));
        if (fileStore.getCurrentSize()>=fileStore.getMaxSize()){
            fileStore.setPermission(1);
        }
        fileStoreService.updateFileStore(fileStore);
        map.put("code",200);
        return map;
    }


    @RequestMapping("/checkMD5")
    @ResponseBody
    public Map checkMD5(String MD5){
        Map map = new HashMap<String,Object>();
        FileMd5 fileMd5 = fileMd5Service.findByMd5(MD5);
        if (fileMd5 == null){
            map.put("exitsMD5",false);
        }else{
            map.put("exitsMD5",true);
        }
        return map;
    }

    @RequestMapping("/addFolder")
    @ResponseBody
    public Map addFileFolder(FileFolder fileFolder){
        User loginUser = (User)session.getAttribute("loginUser");
        List<FileFolder> folders = null;
        fileFolder.setFileStoreId(loginUser.getFileStoreId());
        fileFolder.setTime(new Date());
        Map map = new HashMap<String,Object>();
        folders = fileFolderService.getFileFolderByStoreIdAndParentFolderId(loginUser.getFileStoreId(), fileFolder.getParentFolderId());
        for (FileFolder folder : folders) {
            if (folder.getFileFolderName().equals(fileFolder.getFileFolderName())){
                map.put("status",0);
                map.put("error","操作失败，已存在同名文件夹");
                return map;
               // return "操作失败，已存在同名文件夹";
            }
        }
        fileFolderService.addFileFolder(fileFolder);
        if (fileFolder.getFileFolderId()==null){
            map.put("status",0);
            map.put("error","操作失败，服务器错误");
        }
        map.put("status",1);
        map.put("folder",fileFolder);
        //添加文件夹
        return map;
    }

    @RequestMapping("/deleteFolder")
    @ResponseBody
    @Transactional
    public Map deleteFileFolder(Integer fId){
        User loginUser = (User)session.getAttribute("loginUser");
        Map map = new HashMap<String,Object>();

        //递归删除子文件夹
        deleteChildFolder(loginUser.getFileStoreId(),fId);
        //删除文件夹
        Integer status = fileFolderService.deleteFileFolderById(fId,loginUser.getFileStoreId());
        if (status == null){
            map.put("status",0);
            map.put("error","操作失败，服务器错误");
            return map;
        }
        FileStore fileStore = fileStoreService.getFileStoreByFileStoreId(loginUser.getFileStoreId());
        System.out.println(fileStore);
        //更新仓库信息
        fileStore.setCurrentSize(fileService.countAllFileSizeByFileStoreId(loginUser.getFileStoreId()));
        if (fileStore.getCurrentSize()==null){
            fileStore.setCurrentSize(0);
        }
        if (fileStore.getCurrentSize()<fileStore.getMaxSize()){
            fileStore.setPermission(0);
        }
        fileStoreService.updateFileStore(fileStore);
        map.put("status",1);
        map.put("msg","成功删除文件夹及其文件");
        return map;
    }
    @Transactional
    public void deleteChildFolder(Integer sId,Integer fId){
        //删除文件夹下的文件
        fileService.deleteFileByParentFolderId(fId,sId);
        //获取当前目录下的子文件夹
        List<FileFolder> folders = fileFolderService.getFileFolderByStoreIdAndParentFolderId(sId, fId);
        if (folders == null||folders.size() == 0)
            return;
        //遍历删除子文件夹
        for (FileFolder folder : folders) {
            //删除当前文件夹下的文件
            fileService.deleteFileByParentFolderId(folder.getFileFolderId(),sId);
            //删除子文件夹
            deleteChildFolder(sId,folder.getFileFolderId());
            //删除文件夹
            fileFolderService.deleteFileFolderById(folder.getFileFolderId(),sId);
        }
    }

    @RequestMapping("/updateFolder")
    @ResponseBody
    public Map updateFolder(FileFolder fileFolder) {
        User loginUser = (User)session.getAttribute("loginUser");
        Map<String,Object> map = new HashMap<>();
        List<FileFolder> folders;
//        System.out.println(fileFolder);
        folders = fileFolderService.getFileFolderByStoreIdAndParentFolderId(loginUser.getFileStoreId(), fileFolder.getParentFolderId());
        if (fileFolder.getFileFolderName() == null){
            map.put("status",0);
            map.put("error","操作失败，出现错误");
            return  map;
        }
        for (FileFolder folder : folders) {
            if (folder.getFileFolderName().equals(fileFolder.getFileFolderName())){
                map.put("status",0);
                map.put("error","操作失败，已存在同名文件夹");
                return map;
                // return "操作失败，已存在同名文件夹";
            }
        }
        Integer count = fileFolderService.updateFileFolderName(fileFolder.getFileFolderId(),fileFolder.getFileFolderName());
        if (count==null||count<1){
            map.put("status",0);
            map.put("error","操作失败，出现错误");
            return map;
        }
        map.put("status",1);
        return map;
    }

    @RequestMapping("/updateFile")
    @ResponseBody
    public Map updateFile(File file) {
        User loginUser = (User)session.getAttribute("loginUser");
        Map<String,Object> map = new HashMap<>();
        List<File> files;
        System.out.println(file);
        //folders = fileFolderService.getFileFolderByStoreIdAndParentFolderId(loginUser.getFileStoreId(), fileFolder.getParentFolderId());
        files = fileService.getFileByStoreIdAndParentFolderId(loginUser.getFileStoreId(), file.getParentFolderId());
        if (file.getMyFileName() == null){
            map.put("status",0);
            map.put("error","操作失败，系统错误");
            return  map;
        }
        for (File f : files) {
            if (f.getMyFileName().equals(file.getMyFileName())){
                map.put("status",0);
                map.put("error","操作失败，已存在同名文件");
                return map;
            }
        }
        Integer count = fileService.updateFileFolderName(file.getMyFileId(),file.getMyFileName());
        if (count==null||count<1){
            map.put("status",0);
            map.put("error","操作失败，系统错误");
            return map;
        }
        map.put("status",1);
        return map;
    }
    @RequestMapping("/deleteFile")
    @ResponseBody
    public Map deleteFile(Integer fId){
       // Integer cnt= fileService.deleteFilesByFileId(fId);
        User loginUser = (User)session.getAttribute("loginUser");
        Map map = new HashMap<String,Object>();
        Integer status = fileService.deleteFileByFileIdAndFileStoreId(fId,loginUser.getFileStoreId());
        if (status == null){
            map.put("status",0);
            map.put("error","操作失败，服务器错误");
            return map;
        }
        //更新仓库信息
        FileStore fileStore = fileStoreService.getFileStoreByFileStoreId(loginUser.getFileStoreId());
        fileStore.setCurrentSize(fileService.countAllFileSizeByFileStoreId(loginUser.getFileStoreId()));
        if (fileStore.getCurrentSize()==null){
            fileStore.setCurrentSize(0);
        }
        if (fileStore.getCurrentSize()<fileStore.getMaxSize()){
            fileStore.setPermission(0);
        }
        fileStoreService.updateFileStore(fileStore);
        map.put("status",1);
        map.put("msg","成功删除文件");
        return map;
    }

    @RequestMapping("/downloadFile")
    @ResponseBody
    public String downloadFile(Integer fId, HttpServletResponse response){
        User loginUser = (User)session.getAttribute("loginUser");
        if (fileStoreService.getFileStoreByFileStoreId(loginUser.getFileStoreId()).getPermission() == 2){
            return "redirect:/error401Page";
        }
        //获取文件信息
        File file = fileService.getFileByFileId(fId);
        Integer md5Id = file.getMyFileMd5Id();
        FileMd5 fileMd5 = fileMd5Service.getFileMd5ById(md5Id);
        String fileName = file.getMyFileName()+file.getPostfix();
        OutputStream os = null;
        try {
            //获取文件流
            os = new BufferedOutputStream(response.getOutputStream());
            response.setCharacterEncoding("utf-8");
            // 设置返回类型
            response.setContentType("multipart/form-data");
            // 文件名转码一下，以防中文乱码
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));

            boolean flag = FileUtil.downloadFile(os,fileMd5.getFileMd5(),file.getPostfix());
            file.setDownloadTime(file.getDownloadTime()+1);
            if (flag) {
                fileService.updateFile(file);
                os.flush();
                os.close();
             //   logger.info("文件下载成功!" + myFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (os!=null) {
                try {
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

}
