package com.feng.wenda.service;

import com.feng.wenda.dao.TopicDao;
import com.feng.wenda.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    @Autowired
    TopicDao topicDao;

    public Topic selectTopicById(int topicId){
        return topicDao.selectTopicById(topicId);
    }


    public List<Topic> selectTopicList(){
        return topicDao.selectTopicList();
    }
}
