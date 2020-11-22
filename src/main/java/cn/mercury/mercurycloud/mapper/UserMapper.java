package cn.mercury.mercurycloud.mapper;

import cn.mercury.mercurycloud.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface UserMapper {

    public List<User> findAll();
}
