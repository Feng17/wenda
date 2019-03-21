package com.feng.wenda.dao;

import com.feng.wenda.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {

    User selectUserByName(String name);

    void addUser(User user);

    User selectUserById(Integer id);

    void updateStateByCode(String code);

    int selectEmailCount(String email);

    int selectStateByUsername(String name);

    User selectUserByCode(String code);

    void updateImage(@Param("image") String image, @Param("id") Integer id);

    void updateUserProfile(User user);
}
