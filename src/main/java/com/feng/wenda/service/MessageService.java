package com.feng.wenda.service;

import com.feng.wenda.dao.MessageDao;
import com.feng.wenda.model.HostHolder;
import com.feng.wenda.model.Message;
import com.feng.wenda.model.User;
import com.feng.wenda.util.SensitiveFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {
    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    MessageDao messageDao;

    @Autowired
    SensitiveFilter sensitiveFilter;

    public Map<String, Object> addMessage(String toUsername, String content) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(toUsername) || StringUtils.isBlank(content)) {
            map.put("msg", "用户名和内容不能为空");
            return map;
        }

        User user = userService.selectUserByName(toUsername);
        if (user == null) {
            map.put("msg", "用户名不存在");
            return map;
        }

        Message message = new Message();
        message.setCreatedDate(new Date());
        message.setFromId(hostHolder.getUser().getId());
        message.setToId(user.getId());
        message.setContent(HtmlUtils.htmlEscape(content));
        message.setContent(sensitiveFilter.filter(message.getContent()));
        if (hostHolder.getUser().getId() < user.getId()) {
            message.setConversationId(String.format("%d_%d", hostHolder.getUser().getId(), user.getId()));
        } else {
            message.setConversationId(String.format("%d_%d", user.getId(), hostHolder.getUser().getId()));
        }
        messageDao.addMessage(message);
        map.put("ok", message.getConversationId());
        return map;
    }


    public List<Message> getConversationList(int userId) {
        return messageDao.getConversationList(userId);
    }

    public List<Message> getConversationDetail(String conversationId) {
        return messageDao.getConversationDetail(conversationId);
    }

    public Message selectOneMessageByConversationId(String conversationId) {
        return messageDao.selectOneMessageByConversationId(conversationId);
    }

}
