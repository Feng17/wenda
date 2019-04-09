package com.feng.wenda.controller;

import com.alibaba.fastjson.JSONObject;
import com.feng.wenda.model.EntityType;
import com.feng.wenda.model.HostHolder;
import com.feng.wenda.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LikeController {
    @Autowired
    HostHolder hostHolder;
    @Autowired
    LikeService likeService;

    @RequestMapping(value = "/likeAnswer", method = RequestMethod.POST)
    @ResponseBody
    public Object likeAnswer(@RequestParam("answerId") int answerId) {
        long likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_ANSWER, answerId);
        JSONObject json = new JSONObject();
        json.put("code", 0);
        json.put("msg", String.valueOf(likeCount));
        return json;
    }


    @RequestMapping(value = "/dislikeAnswer", method = RequestMethod.POST)
    @ResponseBody
    public Object dislike(@RequestParam("answerId") int answerId) {
        long likeCount = likeService.disLike(hostHolder.getUser().getId(), EntityType.ENTITY_ANSWER, answerId);
        JSONObject json = new JSONObject();
        json.put("code", 0);
        json.put("msg", String.valueOf(likeCount));
        return json;
    }
}
