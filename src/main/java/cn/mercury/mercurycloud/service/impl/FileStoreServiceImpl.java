package cn.mercury.mercurycloud.service.impl;

import cn.mercury.mercurycloud.mapper.FileStoreMapper;
import cn.mercury.mercurycloud.pojo.FileStore;
import cn.mercury.mercurycloud.pojo.FileStoreInfo;
import cn.mercury.mercurycloud.service.FileStoreService;
import com.sun.glass.ui.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileStoreServiceImpl implements FileStoreService {
    @Autowired
    private FileStoreMapper fileStoreMapper;
    @Override
    public Integer insert(FileStore fileStore) {
        return fileStoreMapper.insert(fileStore);
    }

    @Override
    public FileStore getFileStoreByFileStoreId(Integer fileStoreId) {
        return fileStoreMapper.selectByPrimaryKey(fileStoreId);
    }

    @Override
    public FileStoreInfo countFileStore(Integer fileStoreId) {
        FileStoreInfo fileStoreInfo = fileStoreMapper.countFileStore(fileStoreId);
        fileStoreInfo.setFolderCount(fileStoreMapper.countFileFolderById(fileStoreId));
        return fileStoreInfo;
    }

    @Override
    public Integer updatePermission(Integer uId, Integer pre, Integer Size) {
        return fileStoreMapper.updatePermission(uId,pre, Size);
    }

    @Override
    public void deleteById(Integer fileStoreId) {
        fileStoreMapper.deleteByPrimaryKey(fileStoreId);
    }

    @Override
    public Integer updateFileStore(FileStore fileStore) {
        return fileStoreMapper.updateByPrimaryKey(fileStore);
    }
}
