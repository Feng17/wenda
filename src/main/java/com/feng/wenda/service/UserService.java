package com.feng.wenda.service;


import com.feng.wenda.dao.LoginTokenDao;
import com.feng.wenda.dao.UserDao;
import com.feng.wenda.model.LoginToken;
import com.feng.wenda.model.User;
import com.feng.wenda.util.MailUtil;
import com.feng.wenda.util.WendaUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private LoginTokenDao loginTokenDao;
    @Autowired
    private MailUtil mailUtil;


    public Map<String, Object> register(String username, String email, String password) {
        Map<String, Object> map = new HashMap<String, Object>();

        Pattern p = Pattern.compile("^\\w{1,10}$");
        Matcher m = p.matcher(username);
        if (!m.matches()) {
            map.put("msg", "用户名长度为1-10位字符");
            return map;
        }

        User user = userDao.selectUserByName(username);
        if (user != null) {
            map.put("msg", "用户名已存在");
            return map;
        }

        p = Pattern.compile("^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$");
        m = p.matcher(email);
        if (!m.matches()) {
            map.put("msg", "请输入正确的邮箱");
            return map;
        }

        int emailCount = userDao.selectEmailCount(email);
        if (emailCount > 0) {
            map.put("msg", "该邮箱已被注册");
            return map;
        }

        p = Pattern.compile("\\w{6,16}$");
        m = p.matcher(password);
        if (!m.matches()) {
            map.put("msg", "密码长度为6-16字符");
            return map;
        }

        user = new User();
        user.setName(username);
        user.setImage("");
        user.setDescription("");
        user.setEmail(email);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        user.setPassword(WendaUtil.MD5(password + user.getSalt()));
        String code = UUID.randomUUID().toString().replace("-", "");
        user.setCode(code);
        user.setState(0);
        mailUtil.sendTemplateMail(username, code, email);
        userDao.addUser(user);
        map.put("msg", "ok");
        return map;

    }

    public Map<String, Object> activate(String code) {
        Map<String, Object> map = new HashMap<>();

        User user = userDao.selectUserByCode(code);
        if (user == null) {
            map.put("msg", "激活码无效请重新注册");
            return map;
        } else if (user.getState() == 1) {
            map.put("msg", "激活码失效请重新注册");
            return map;
        } else {
            userDao.updateStateByCode(code);
            map.put("msg", "激活成功！可以登录了");
            return map;
        }
    }

    public Map<String, Object> login(String username, String password, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            map.put("msg", "用户名和密码不能为空");
            return map;
        }
        User user = userDao.selectUserByName(username);
        if (user == null) {
            map.put("msg", "用户名不存在");
            return map;
        }

        int state = userDao.selectStateByUsername(username);
        if (state != 1) {
            map.put("msg", "帐号还未激活，请先前往邮箱激活");
            return map;
        }

        if (!WendaUtil.MD5(password + user.getSalt()).equals(user.getPassword())) {
            map.put("msg", "密码错误");
            return map;
        }

        //登录成功 添加一个token
        String token = addLoginToken(user.getId());
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        response.addCookie(cookie);
        return map;
    }

    private String addLoginToken(Integer userId) {
        LoginToken token = new LoginToken();
        token.setUserId(userId);
        Date date = new Date();
        date.setTime(System.currentTimeMillis() + 1000 * 3600 * 24);
        token.setExpired(date);
        token.setStatus(0);
        token.setToken(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTokenDao.addLoginToken(token);
        return token.getToken();
    }
    public User selectUserById(int id) {
        return userDao.selectUserById(id);
    }


    public void logout(int id,int status){
    loginTokenDao.updateStatusByUserId(id,status);
    }

}
