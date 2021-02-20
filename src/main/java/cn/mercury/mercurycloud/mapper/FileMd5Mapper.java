package cn.mercury.mercurycloud.mapper;

import cn.mercury.mercurycloud.pojo.FileMd5;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Author: Mercury-Z
 */
@Mapper
@Repository
public interface FileMd5Mapper extends tk.mybatis.mapper.common.Mapper<FileMd5> {
    @Select("select * from file_md5 where file_md5 = #{md5}")
    FileMd5 findByMd5(String md5);
}
