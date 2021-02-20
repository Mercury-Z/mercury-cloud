package cn.mercury.mercurycloud.mapper;

import cn.mercury.mercurycloud.pojo.FileFolder;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FileFolderMapper extends tk.mybatis.mapper.common.Mapper<FileFolder> {
    @Select("select * from file_folder where file_store_id = #{fileStoreId} and parent_folder_id = #{FolderId}")
    List<FileFolder> findFileFolderByStoreIdAndParentFolderId(Integer fileStoreId, Integer FolderId);
    @Delete("delete from file_folder where file_folder_id = #{fId} and file_store_id = #{fileStoreId}")
    Integer deleteByFileFolderIdAndStoreId(Integer fId, Integer fileStoreId);

    @Update("update file_folder set file_folder_name = #{fileFolderName} where file_folder_id = #{fileFolderId}")
    Integer updateFileFolderName(Integer fileFolderId, String fileFolderName);

    @Delete("delete from file_folder where file_store_id = #{fileStoreId}")
    void deleteFileFoldersByFileStoreId(Integer fileStoreId);
}
