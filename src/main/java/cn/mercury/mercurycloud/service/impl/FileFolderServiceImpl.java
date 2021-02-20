package cn.mercury.mercurycloud.service.impl;

import cn.mercury.mercurycloud.mapper.FileFolderMapper;
import cn.mercury.mercurycloud.pojo.FileFolder;
import cn.mercury.mercurycloud.service.FileFolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileFolderServiceImpl implements FileFolderService {
    @Autowired
    FileFolderMapper fileFolderMapper;
    @Override
    public List<FileFolder> getFileFolderByStoreIdAndParentFolderId(Integer fileStoreId, Integer FolderId) {
        return fileFolderMapper.findFileFolderByStoreIdAndParentFolderId(fileStoreId,FolderId);
    }

    @Override
    public FileFolder getFileFolderByFolderId(Integer FolderId) {
        return fileFolderMapper.selectByPrimaryKey(FolderId);
    }

    @Override
    public int addFileFolder(FileFolder fileFolder) {
        return fileFolderMapper.insert(fileFolder);
    }

    @Override
    public Integer deleteFileFolderById(Integer fId,Integer fileStoreId) {
        return fileFolderMapper.deleteByFileFolderIdAndStoreId(fId,fileStoreId);
    }

    @Override
    public Integer updateFileFolderName(Integer fileFolderId, String fileFolderName) {
        return fileFolderMapper.updateFileFolderName(fileFolderId,fileFolderName);
    }

    @Override
    public void deleteFileFoldersByFileStoreId(Integer fileStoreId) {
        fileFolderMapper.deleteFileFoldersByFileStoreId(fileStoreId);
    }

}
