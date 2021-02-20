package cn.mercury.mercurycloud.service;



import cn.mercury.mercurycloud.pojo.File;

import java.util.List;

public interface FileService {
    List<File> getFileByStoreIdAndParentFolderId(Integer fileStoreId, Integer folderId);

    Integer deleteFileByParentFolderId(Integer fId,Integer fileStoreId);

    boolean existsFileByFileNameAndParentFolderId(String name, String Postfix, Integer FolderId);

    Integer insert(File nowFile);

    List<File> getFilesByType(Integer fileStoreId, Integer type);

    Integer updateFileFolderName(Integer myFileId, String myFileName);

    void deleteFilesByFileStoreId(Integer fileStoreId);

    Integer deleteFileByFileIdAndFileStoreId(Integer fId, Integer fileStoreId);

    File getFileByFileId(Integer fId);

    void updateFile(File file);

    Integer countAllFileSizeByFileStoreId(Integer fileStoreId);
}
