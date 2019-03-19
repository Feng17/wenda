package com.feng.wenda.dao;

import com.feng.wenda.model.Question;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuestionDao {

    List<Question> selectQuestionList();
}
