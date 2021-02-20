package cn.mercury.mercurycloud.controller;

import cn.mercury.mercurycloud.mapper.FileStoreMapper;
import cn.mercury.mercurycloud.pojo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.Folder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("test")
public class TestController extends BaseController{

    @Autowired
    FileStoreMapper fileStoreMapper;

    @RequestMapping("reg_success")
    public String regSuccess(){
        return "reg_success.html";
    }
    @RequestMapping("users")
    @ResponseBody
    public List<UserToShow> users(){
        List<UserToShow> users = userService.getUsers();
        return users;
    }

    @RequestMapping("/store/{sid}")
    @ResponseBody
    public FileStore getStore(@PathVariable Integer sid){
        return fileStoreMapper.findByFileStoreId(sid);
    }

    @RequestMapping("/test")
    public String test(Map<String,Object> map){
        String test = "123";
        map.put("test",test);
        return "test";
    }
    @RequestMapping("/upload")
    public String upload(Map<String,Object> map){
        String test = "123";
        map.put("test",test);
        return "test";
    }
    @RequestMapping("folder/{Sid}/{Fid}")
    @ResponseBody
    public List<FileFolder> Folder(@PathVariable Integer Sid, @PathVariable Integer Fid){
        return fileFolderService.getFileFolderByStoreIdAndParentFolderId(Sid,Fid);
    }

    @RequestMapping("file/{Sid}/{Fid}")
    @ResponseBody
    public List<File> File(@PathVariable Integer Sid, @PathVariable Integer Fid){
        return fileService.getFileByStoreIdAndParentFolderId(Sid,Fid);
    }
    @RequestMapping("nowFolder/{Fid}")
    @ResponseBody
    public FileFolder nowFolder( @PathVariable Integer Fid){
        return fileFolderService.getFileFolderByFolderId(Fid);
    }

    @RequestMapping("loadRow")
    public String loadRow(Integer fId,Map<String,Object> map) {
        //当前目录下的文件夹
        List<FileFolder> folders = null;
        //当前目录下的文件
        List<File> files = null;
        //当前文件夹的信息
        FileFolder nowFolder = null;
        //当前文件夹的路径
        List<FileFolder> location = new ArrayList<>();

        User loginUser =(User) session.getAttribute("loginUser");

        FileStoreInfo fileStoreInfo = null;
        fileStoreInfo = fileStoreService.countFileStore(loginUser.getFileStoreId());
        Logger logger = LoggerFactory.getLogger(SystemController.class);
        logger.info("fid = "+fId);
        if (fId == null || fId <= 0) {
            fId = 0;
            folders = fileFolderService.getFileFolderByStoreIdAndParentFolderId(loginUser.getFileStoreId(), fId);
            files = fileService.getFileByStoreIdAndParentFolderId(loginUser.getFileStoreId(), fId);
            nowFolder = FileFolder.builder().fileFolderId(fId).build();
            location.add(nowFolder);
        }else {
            folders = fileFolderService.getFileFolderByStoreIdAndParentFolderId(loginUser.getFileStoreId(), fId);
            files = fileService.getFileByStoreIdAndParentFolderId(loginUser.getFileStoreId(), fId);
            nowFolder = fileFolderService.getFileFolderByFolderId(fId);
            FileFolder tempFolder = nowFolder;
            while (tempFolder.getParentFolderId() != 0) {
                tempFolder = fileFolderService.getFileFolderByFolderId(nowFolder.getParentFolderId());
                location.add(tempFolder);
            }
        }
        map.put("folders", folders);
        map.put("files", files);
        map.put("nowFolder", nowFolder);
        map.put("location", location);
        return "commons/row::#file-row(files = ${files},folders=${folders},nowFolder = ${nowFolder})";
    }

}
