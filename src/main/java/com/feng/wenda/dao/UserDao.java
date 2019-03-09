package com.feng.wenda.dao;

import com.feng.wenda.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

    User selectUserByName(String name);

    void addUser(User user);

    User selectUserById(Integer id);

    void updateStateByCode(String code);

    int selectEmailCount(String email);

    int selectStateByUsername(String name);

    User selectUserByCode(String code);
}
