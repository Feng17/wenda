package com.feng.wenda.dao;

import com.feng.wenda.model.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionDao {

    List<Question> selectQuestionList();

    void addQuestion(Question question);

    Question selectQuestionById(int questionId);

    void updateAnswerCount(@Param("questionId") int questionId,@Param("answerCount") int answerCount);
}
