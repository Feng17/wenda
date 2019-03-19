package com.feng.wenda.dao;

import com.feng.wenda.model.Topic;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TopicDao {

    Topic selectTopicById(int topicId);
}
