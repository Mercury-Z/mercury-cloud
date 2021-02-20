package cn.mercury.mercurycloud.service;

import cn.mercury.mercurycloud.pojo.FileStore;
import cn.mercury.mercurycloud.pojo.FileStoreInfo;

public interface FileStoreService {
    Integer insert(FileStore fileStore);
    FileStore getFileStoreByFileStoreId(Integer fileStoreId);
    FileStoreInfo countFileStore(Integer fileStoreId);

    Integer updatePermission(Integer uId, Integer pre, Integer Size);

    void deleteById(Integer fileStoreId);

    Integer updateFileStore(FileStore fileStore);
}
