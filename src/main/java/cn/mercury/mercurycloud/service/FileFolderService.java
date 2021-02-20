package cn.mercury.mercurycloud.service;


import cn.mercury.mercurycloud.pojo.FileFolder;

import java.util.List;

public interface FileFolderService {
    List<FileFolder> getFileFolderByStoreIdAndParentFolderId(Integer fileStoreId, Integer FolderId);

    FileFolder getFileFolderByFolderId(Integer FolderId);

    int addFileFolder(FileFolder fileFolder);

    Integer deleteFileFolderById(Integer fId, Integer fileStoreId);

    Integer updateFileFolderName(Integer fileFolderId, String fileFolderName);

    void deleteFileFoldersByFileStoreId(Integer fileStoreId);
}
