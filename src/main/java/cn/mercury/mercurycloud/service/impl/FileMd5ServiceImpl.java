package cn.mercury.mercurycloud.service.impl;

import cn.mercury.mercurycloud.mapper.FileMd5Mapper;
import cn.mercury.mercurycloud.pojo.File;
import cn.mercury.mercurycloud.pojo.FileMd5;
import cn.mercury.mercurycloud.service.FileMd5Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Mercury-Z
 */
@Service
public class FileMd5ServiceImpl implements FileMd5Service {

    @Autowired
    private FileMd5Mapper fileMd5Mapper;
    @Override
    public Integer insert(FileMd5 fileMd5) {
        return fileMd5Mapper.insert(fileMd5);
    }

    @Override
    public FileMd5 findByMd5(String md5) {
        return fileMd5Mapper.findByMd5(md5);
    }

    @Override
    public FileMd5 getFileMd5ById(Integer md5Id) {
        return fileMd5Mapper.selectByPrimaryKey(md5Id);
    }
}
