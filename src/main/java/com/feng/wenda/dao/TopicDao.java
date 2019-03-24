package com.feng.wenda.dao;

import com.feng.wenda.model.Topic;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TopicDao {

    Topic selectTopicById(int topicId);

    List<Topic> selectTopicList();
}
