package com.feng.wenda.controller;

import com.feng.wenda.model.HostHolder;
import com.feng.wenda.model.Message;
import com.feng.wenda.model.User;
import com.feng.wenda.model.ViewObject;
import com.feng.wenda.service.MessageService;
import com.feng.wenda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
public class MessageController {
    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;
    @Autowired
    HostHolder hostHolder;

    @RequestMapping(value = "/message", method = RequestMethod.GET)
    public String sendMessage() {
        return "message";
    }


    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addMessage(@RequestParam("toUsername") String toUsername,
                                          @RequestParam("content") String content) {
        Map<String, Object> map = messageService.addMessage(toUsername, content);
        return map;
    }


    @RequestMapping(value = "/messageList", method = RequestMethod.GET)
    public String getConversationList(Model model) {
        int localUserId = hostHolder.getUser().getId();
        List<Message> conversationList = messageService.getConversationList(localUserId);
        List<ViewObject> vos = new ArrayList<ViewObject>();
        for (Message message : conversationList) {
            ViewObject vo = new ViewObject();
            vo.set("message", message);
            //列表页显示接收用户信息
            int targetId = message.getFromId() == localUserId ? message.getToId() : message.getFromId();
            vo.set("user", userService.selectUserById(targetId));
            vos.add(vo);
        }
        model.addAttribute("vos", vos);
        return "messageList";
    }

    @RequestMapping(value = "/messageDetail", method = RequestMethod.GET)
    public String getConversationDetail(Model model, @RequestParam("conversationId") String conversationId) {
        List<Message> messageDetail = messageService.getConversationDetail(conversationId);
        List<ViewObject> vos = new ArrayList<ViewObject>();
        for (Message message : messageDetail) {
            ViewObject vo = new ViewObject();
            vo.set("message", message);
            vo.set("user", userService.selectUserById(message.getFromId()));
            vos.add(vo);
        }
        model.addAttribute("vos", vos);
        Message message = messageService.selectOneMessageByConversationId(conversationId);
        int targetId = message.getFromId() == hostHolder.getUser().getId() ? message.getToId() : message.getFromId();
        User targetUser = userService.selectUserById(targetId);
        model.addAttribute("toUsername", targetUser.getName());
        return "messageDetail";
    }
}
