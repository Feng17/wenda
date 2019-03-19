package com.feng.wenda.service;

import com.feng.wenda.dao.QuestionDao;
import com.feng.wenda.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;

    public List<Question> selectQuestionList(){
        return questionDao.selectQuestionList();
    }

}
