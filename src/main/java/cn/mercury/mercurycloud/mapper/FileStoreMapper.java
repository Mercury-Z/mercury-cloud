package cn.mercury.mercurycloud.mapper;

import cn.mercury.mercurycloud.pojo.FileStore;
import cn.mercury.mercurycloud.pojo.FileStoreInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FileStoreMapper extends tk.mybatis.mapper.common.Mapper<FileStore> {
    @Select(" select sum(type = 1) doc,sum(type = 2) image,sum(type = 3) video,\n" +
            "      sum(type = 4) music,sum(type = 5) other,count(*) fileCount from file\n" +
            "      where file_store_id = #{fileStoreId}")
    FileStoreInfo countFileStore(Integer fileStoreId);
    @Select(" select count(*)  from file_folder  where file_store_id = #{fileStoreId}")
    Integer countFileFolderById(Integer fileStoreId);
    @Select("select * from file_store where file_store_id = #{fileStoreId}")
    FileStore findByFileStoreId(Integer fileStoreId);

    @Update("update file_store set permission = #{pre} ,max_size = #{size} where user_id = #{uId}")
    Integer updatePermission(Integer uId, Integer pre, Integer size);
}
