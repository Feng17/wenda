package com.feng.wenda.dao;

import com.feng.wenda.model.Answer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AnswerDao {

    void addAnswer(Answer answer);

    int getAnswerCount(int questionId);

    List<Answer> selectAnswerList(int questionId);

}
