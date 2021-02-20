package cn.mercury.mercurycloud.mapper;

import cn.mercury.mercurycloud.pojo.FileStore;
import cn.mercury.mercurycloud.pojo.User;
import cn.mercury.mercurycloud.pojo.UserToShow;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface UserMapper extends tk.mybatis.mapper.common.Mapper<User> {

    User findUserByEmail(String email);

    @Update("UPDATE `user` SET `file_store_id` = #{fileStoreId} WHERE `user_id` = #{userId} ")
    int updateByUserId(Integer userId,Integer fileStoreId);

    @Select("select count(user_id) from user")
    Integer UsersCount();

    @Select("select user_id ,user_name ,email,register_time ,image_path ,file_store_id from user")
    @Results(value = {
            @Result(property = "fileStore", column = "file_store_id",
            one = @One(select = "cn.mercury.mercurycloud.mapper.FileStoreMapper.findByFileStoreId", fetchType = FetchType.EAGER))
            })
    List<UserToShow> getUsers();
}


