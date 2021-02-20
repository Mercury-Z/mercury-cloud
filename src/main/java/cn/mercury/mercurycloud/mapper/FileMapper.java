package cn.mercury.mercurycloud.mapper;

import cn.mercury.mercurycloud.pojo.File;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;


import java.util.List;
@Mapper
@Repository
public interface FileMapper extends tk.mybatis.mapper.common.Mapper<File> {
    @Select("select * from file where file_store_id = #{fileStoreId} and parent_folder_id = #{FolderId}")
    List<File> findFileByStoreIdAndParentFolderId(Integer fileStoreId, Integer FolderId);
    @Delete("delete from file where parent_folder_id = #{fId} and file_store_id = #{fileStoreId}")
    Integer deleteFileByParentFolderId(Integer fId,Integer fileStoreId);

    @Select("select count(my_file_id) from file  where my_file_name = #{fileName} and postfix = #{postfix} and parent_folder_id = #{FolderId}")
    Integer findFileByFileNameAndParentFolderId(String fileName,  String postfix, Integer FolderId);

    @Select("select * from file where file_store_id = #{fileStoreId} and type = #{type}")
    List<File> getFilesByType(Integer fileStoreId, Integer type);

    @Update("update file set my_file_name = #{myFileName} where my_file_id = #{myFileId}")
    Integer updateFile(Integer myFileId, String myFileName);

    @Delete("delete from file where file_store_id = #{fileStoreId}")
    void deleteFilesByFileStoreId(Integer fileStoreId);
    @Delete("delete from file where my_file_id = #{fId} and file_store_id = #{fileStoreId}")
    Integer deleteFileByFileIdAndFileStoreId(Integer fId, Integer fileStoreId);

    @Select("select sum(size) from file where file_store_id = #{fileStoreId}")
    Integer countAllFileSizeByFileStoreId(Integer fileStoreId);
}
