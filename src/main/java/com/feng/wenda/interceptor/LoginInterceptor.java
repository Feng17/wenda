package com.feng.wenda.interceptor;

import com.feng.wenda.dao.LoginTokenDao;
import com.feng.wenda.dao.UserDao;
import com.feng.wenda.model.HostHolder;
import com.feng.wenda.model.LoginToken;
import com.feng.wenda.model.User;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    LoginTokenDao loginTokenDao;

    @Autowired
    UserDao userDao;

    @Autowired
    HostHolder hostHolder;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception {

        String token = null;
        Cookie[] cookies = request.getCookies();
        if (ArrayUtils.isEmpty(cookies)) {
            response.sendRedirect("/login");
            return false;
        } else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token.isEmpty()) {
            response.sendRedirect("/login");
            return false;
        } else {
            LoginToken loginToken = loginTokenDao.selectLoginTokenByToken(token);
            //如果loginToken不存在或者是登出状态或者已经失效就返回到登录页面
            if (loginToken == null || loginToken.getExpired().before(new Date()) || loginToken.getStatus() == 1) {
                response.sendRedirect("/login");
                return false;
            }
            User user = userDao.selectUserById(loginToken.getUserId());
            hostHolder.setUser(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handle, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && hostHolder.getUser() != null) {
            modelAndView.addObject("user", hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handle, Exception e) throws Exception {
        hostHolder.clear();

    }
}

