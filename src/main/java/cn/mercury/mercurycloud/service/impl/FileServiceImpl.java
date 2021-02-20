package cn.mercury.mercurycloud.service.impl;

import cn.mercury.mercurycloud.mapper.FileMapper;
import cn.mercury.mercurycloud.pojo.File;
import cn.mercury.mercurycloud.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    FileMapper fileMapper;
    @Override
    public List<File> getFileByStoreIdAndParentFolderId(Integer fileStoreId, Integer folderId) {
        return fileMapper.findFileByStoreIdAndParentFolderId(fileStoreId,folderId);
    }

    @Override
    public Integer deleteFileByParentFolderId(Integer fId,Integer fileStoreId) {
        return fileMapper.deleteFileByParentFolderId(fId,fileStoreId);
    }

    @Override
    public boolean existsFileByFileNameAndParentFolderId(String name, String Postfix, Integer FolderId) {
        Integer count = fileMapper.findFileByFileNameAndParentFolderId(name,Postfix,FolderId);
        return count>0?true:false;
    }

    @Override
    public Integer insert(File nowFile) {
        return fileMapper.insert(nowFile);
    }

    @Override
    public List<File> getFilesByType(Integer fileStoreId, Integer type) {
        return fileMapper.getFilesByType(fileStoreId,type);
    }

    @Override
    public Integer updateFileFolderName(Integer myFileId, String myFileName) {
        return fileMapper.updateFile(myFileId,myFileName);
    }

    @Override
    public void deleteFilesByFileStoreId(Integer fileStoreId) {
        fileMapper.deleteFilesByFileStoreId(fileStoreId);
    }

    @Override
    public Integer deleteFileByFileIdAndFileStoreId(Integer fId, Integer fileStoreId) {
        return fileMapper.deleteFileByFileIdAndFileStoreId(fId,fileStoreId);
    }

    @Override
    public File getFileByFileId(Integer fId) {
        return fileMapper.selectByPrimaryKey(fId);
    }

    @Override
    public void updateFile(File file) {
        fileMapper.updateByPrimaryKey(file);
    }

    @Override
    public Integer countAllFileSizeByFileStoreId(Integer fileStoreId) {
        return fileMapper.countAllFileSizeByFileStoreId(fileStoreId);
    }

}
