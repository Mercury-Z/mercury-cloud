package cn.mercury.mercurycloud.service;

import cn.mercury.mercurycloud.pojo.File;
import cn.mercury.mercurycloud.pojo.FileMd5;

/**
 * @Author: Mercury-Z
 */
public interface FileMd5Service {
    Integer insert(FileMd5 fileMd5);

    FileMd5 findByMd5(String md5);

    FileMd5 getFileMd5ById(Integer md5Id);
}
