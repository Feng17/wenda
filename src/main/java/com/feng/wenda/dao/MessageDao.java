package com.feng.wenda.dao;

import com.feng.wenda.model.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageDao {

    void addMessage(Message message);

    List<Message> getConversationList(int userId);

    List<Message> getConversationDetail(String conversationId);

    Message selectOneMessageByConversationId(String conversationId);
}
