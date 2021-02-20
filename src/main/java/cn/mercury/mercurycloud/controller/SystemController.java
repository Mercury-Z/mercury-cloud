package cn.mercury.mercurycloud.controller;

import cn.mercury.mercurycloud.pojo.File;
import cn.mercury.mercurycloud.pojo.FileFolder;
import cn.mercury.mercurycloud.pojo.FileStoreInfo;
import cn.mercury.mercurycloud.pojo.User;
import cn.mercury.mercurycloud.utils.FileType;
import cn.mercury.mercurycloud.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
public class SystemController extends BaseController{
    @GetMapping("/")
    public String defaultPage(Map<String,Object> map){
//        map.put("errorMsg", "this is /");
        return "index";
    }


    @GetMapping("/index")
    public String index(Map<String,Object> map){
        User loginUser =(User) session.getAttribute("loginUser");
        FileStoreInfo fileStoreInfo = fileStoreService.countFileStore(loginUser.getFileStoreId());
        fileStoreInfo.setFileStore(fileStoreService.getFileStoreByFileStoreId(loginUser.getFileStoreId()));
        map.put("fileStoreInfo",fileStoreInfo );
        return "u-admin/index";
    }

    @GetMapping("/upload")
    public String upload(Integer fId, Map<String, Object> map){
        User loginUser = (User)session.getAttribute("loginUser");
        //包含的子文件夹
        List<FileFolder> folders = null;
        //当前文件夹信息
        FileFolder nowFolder = null;
        //当前文件夹的相对路径
        List<FileFolder> location = new ArrayList<>();
        if (fId == null || fId <= 0) {
            fId = 0;
            folders = fileFolderService.getFileFolderByStoreIdAndParentFolderId(loginUser.getFileStoreId(), fId);

            nowFolder = FileFolder.builder().fileFolderId(fId).build();
            location.add(nowFolder);
        }else {
            folders = fileFolderService.getFileFolderByStoreIdAndParentFolderId(loginUser.getFileStoreId(), fId);

            nowFolder = fileFolderService.getFileFolderByFolderId(fId);
            FileFolder tempFolder = nowFolder;
            while (tempFolder.getParentFolderId() != 0) {
                tempFolder = fileFolderService.getFileFolderByFolderId(nowFolder.getParentFolderId());
                location.add(tempFolder);
            }
        }
        Collections.reverse(location);
        //根据名字进行排序
        Collections.sort(folders, Comparator.comparing(FileFolder::getFileFolderName));
        //获得统计信息
        FileStoreInfo fileStoreInfo = fileStoreService.countFileStore(loginUser.getFileStoreId());
        map.put("fileStoreInfo", fileStoreInfo);
        map.put("folders", folders);
        map.put("nowFolder", nowFolder);
        map.put("location", location);
        return "u-admin/upload";
    }

    /**
     * 文件列表展示页面
     * @param map
     * @return
     */
    @GetMapping("/files")
    public String files(Integer fId,Map<String,Object> map){
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

        //根据名字进行排序
        Collections.sort(files, (o1,o2)->{
            int diff = o1.getType() -o2.getType();
            if (diff == 0)
                return o1.getMyFileName().compareTo(o2.getMyFileName());
            return diff;
        });
        Collections.sort(folders, Comparator.comparing(FileFolder::getFileFolderName));
        map.put("fileStoreInfo", fileStoreInfo);
        map.put("permission", fileStoreService.getFileStoreByFileStoreId(loginUser.getFileStoreId()).getPermission());
        map.put("folders", folders);
        map.put("files", files);
        map.put("nowFolder", nowFolder);
        map.put("location", location);
       // logger.info("网盘页面域中的数据:" + map);

        return "u-admin/files";
    }

    /**
     * 文档类文件
     * @param map
     * @return
     */
    @GetMapping("/doc-files")
    public String docFiles(Map<String,Object> map){
        User loginUser = (User)session.getAttribute("loginUser");
        List<File> files = fileService.getFilesByType(loginUser.getFileStoreId(), FileType.document.getValue());
        //获得统计信息
        FileStoreInfo fileStoreInfo = fileStoreService.countFileStore(loginUser.getFileStoreId());
        //根据名字进行排序
        Collections.sort(files, Comparator.comparing(File::getMyFileName));
        map.put("fileStoreInfo", fileStoreInfo);
        map.put("files", files);
        map.put("permission", fileStoreService.getFileStoreByFileStoreId(loginUser.getFileStoreId()).getPermission());
        return "u-admin/doc-files";
    }
    /**
     * 图片类文件
     * @param map
     * @return
     */
    @GetMapping("/image-files")
    public String imageFiles( Map<String, Object> map) {
        User loginUser = (User)session.getAttribute("loginUser");
        List<File> files = fileService.getFilesByType(loginUser.getFileStoreId(), FileType.image.getValue());
        //获得统计信息
        FileStoreInfo fileStoreInfo = fileStoreService.countFileStore(loginUser.getFileStoreId());
        //根据名字进行排序
        Collections.sort(files, Comparator.comparing(File::getMyFileName));
        map.put("fileStoreInfo", fileStoreInfo);
        map.put("files", files);
        map.put("permission", fileStoreService.getFileStoreByFileStoreId(loginUser.getFileStoreId()).getPermission());
        return "u-admin/image-files";
    }

    /**
     * 视屏类文件
     * @param map
     * @return
     */
    @GetMapping("/video-files")
    public String videoFiles( Map<String, Object> map) {
        User loginUser = (User)session.getAttribute("loginUser");
        List<File> files = fileService.getFilesByType(loginUser.getFileStoreId(), FileType.video.getValue());
        //获得统计信息
        FileStoreInfo fileStoreInfo = fileStoreService.countFileStore(loginUser.getFileStoreId());
        //根据名字进行排序
        Collections.sort(files, Comparator.comparing(File::getMyFileName));
        map.put("fileStoreInfo", fileStoreInfo);
        map.put("files", files);
        map.put("permission", fileStoreService.getFileStoreByFileStoreId(loginUser.getFileStoreId()).getPermission());
        return "u-admin/video-files";
    }

    /**
     * 音乐类文件
     * @param map
     * @return
     */
    @GetMapping("/music-files")
    public String musicFiles( Map<String, Object> map) {
        User loginUser = (User)session.getAttribute("loginUser");
        List<File> files = fileService.getFilesByType(loginUser.getFileStoreId(), FileType.audio.getValue());
        //获得统计信息
        FileStoreInfo fileStoreInfo = fileStoreService.countFileStore(loginUser.getFileStoreId());
        //根据名字进行排序
        Collections.sort(files, Comparator.comparing(File::getMyFileName));
        map.put("fileStoreInfo", fileStoreInfo);
        map.put("files", files);
        map.put("permission", fileStoreService.getFileStoreByFileStoreId(loginUser.getFileStoreId()).getPermission());
        return "u-admin/music-files";
    }

    /**
     * 其他类型文件
     * @param map
     * @return
     */
    @GetMapping("/other-files")
    public String otherFiles( Map<String, Object> map) {
        User loginUser = (User)session.getAttribute("loginUser");
        List<File> files = fileService.getFilesByType(loginUser.getFileStoreId(), FileType.other.getValue());
        //获得统计信息
        FileStoreInfo fileStoreInfo = fileStoreService.countFileStore(loginUser.getFileStoreId());
        //根据名字进行排序
        Collections.sort(files, Comparator.comparing(File::getMyFileName));
        map.put("fileStoreInfo", fileStoreInfo);
        map.put("files", files);
        map.put("permission", fileStoreService.getFileStoreByFileStoreId(loginUser.getFileStoreId()).getPermission());
        return "u-admin/other-files";
    }

    @GetMapping("/help")
    public String help(Map<String,Object> map){
        //获得统计信息
        User loginUser = (User)session.getAttribute("loginUser");
        FileStoreInfo fileStoreInfo = fileStoreService.countFileStore(loginUser.getFileStoreId());
        map.put("fileStoreInfo", fileStoreInfo);
        return "u-admin/help";
    }

}
