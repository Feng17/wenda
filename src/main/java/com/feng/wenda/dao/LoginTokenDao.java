package com.feng.wenda.dao;

import com.feng.wenda.model.LoginToken;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginTokenDao {

    void addLoginToken(LoginToken token);

    LoginToken selectLoginTokenByToken(String token);

    void updateStatusByUserId(@Param("userId") Integer userId, @Param("status") Integer status);

}
