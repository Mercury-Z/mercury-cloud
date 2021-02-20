package cn.mercury.mercurycloud.service.impl;

import cn.mercury.mercurycloud.mapper.UserMapper;
import cn.mercury.mercurycloud.pojo.User;
import cn.mercury.mercurycloud.pojo.UserToShow;
import cn.mercury.mercurycloud.service.UserService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findUserByEmail(String email) {
        return userMapper.findUserByEmail(email);
    }

    @Override
    public int insert(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public int updateByUserId(Integer userId, Integer fileStoreId) {
        return userMapper.updateByUserId(userId,fileStoreId);
    }

    @Override
    public Integer getUsersCount() {
        return userMapper.UsersCount();
    }

    @Override
    public List<UserToShow> getUsers() {
        return userMapper.getUsers();
    }

    @Override
    public User findUserById(Integer uId) {
        return userMapper.selectByPrimaryKey(uId);
    }

    @Override
    public void deleteById(Integer uId) {
        userMapper.deleteByPrimaryKey(uId);
    }

}
