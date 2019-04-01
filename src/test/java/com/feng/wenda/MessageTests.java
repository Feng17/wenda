package com.feng.wenda;

import com.feng.wenda.dao.MessageDao;
import com.feng.wenda.model.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MessageTests {
    @Autowired
    MessageDao messageDao;

    @Test
    public void testAddMessage() {
        Message message = new Message();
        message.setCreatedDate(new Date());
        message.setFromId(15);
        message.setToId(2);
        message.setContent("你好");
        if (message.getFromId() < message.getToId()) {
            message.setConversationId(String.format("%d_%d", message.getFromId(), message.getToId()));
        } else {
            message.setConversationId(String.format("%d_%d", message.getToId(), message.getFromId()));
        }
        messageDao.addMessage(message);
    }
}
