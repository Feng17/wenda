package com.feng.wenda.controller;

import com.feng.wenda.model.HostHolder;
import com.feng.wenda.service.AnswerService;
import com.feng.wenda.service.FollowService;
import com.feng.wenda.service.QuestionService;
import com.feng.wenda.service.UserService;
import com.feng.wenda.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class FollowController {
    @Autowired
    FollowService followService;

    @Autowired
    AnswerService answerService;

    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;



    @RequestMapping(path = {"/followUser"}, method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String followUser(@RequestParam("entityType") int entityType,@RequestParam("entityId") int entityId) {

        boolean ret = followService.follow(hostHolder.getUser().getId(), entityType, entityId);

        // 返回关注的人数
        return WendaUtil.getJSONString(ret ? 0 : 1, String.valueOf(followService.getFolloweeCount(hostHolder.getUser().getId(), entityType)));
    }

    @RequestMapping(path = {"/unfollowUser"}, method = {RequestMethod.POST})
    @ResponseBody
    public String unfollowUser(@RequestParam("entityType") int entityType,@RequestParam("entityId") int entityId) {

        boolean ret = followService.unfollow(hostHolder.getUser().getId(), entityType, entityId);

        return WendaUtil.getJSONString(ret ? 0 : 1, String.valueOf(followService.getFolloweeCount(hostHolder.getUser().getId(), entityType)));
    }


}
