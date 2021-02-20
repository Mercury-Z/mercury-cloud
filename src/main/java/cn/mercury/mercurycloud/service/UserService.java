package cn.mercury.mercurycloud.service;

import cn.mercury.mercurycloud.pojo.User;
import cn.mercury.mercurycloud.pojo.UserToShow;

import java.util.List;

public interface UserService {
    User findUserByEmail(String email);

    int insert(User user);

    int updateByUserId(Integer userId, Integer fileStoreId);

    Integer getUsersCount();

    List<UserToShow> getUsers();

    User findUserById(Integer uId);

    void deleteById(Integer uId);
}
