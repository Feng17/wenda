package com.feng.wenda.service;

import com.feng.wenda.dao.AnswerDao;
import com.feng.wenda.model.Answer;
import com.feng.wenda.model.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AnswerService {
    @Autowired
    HostHolder hostHolder;
    @Autowired
    AnswerDao answerDao;
    @Autowired
    AnswerService answerService;
    @Autowired
    QuestionService questionService;

    public List<Answer> selectAnswerList(int questionId) {
        return answerDao.selectAnswerList(questionId);
    }


    public void addAnswer(String content, int questionId) {
        Answer answer = new Answer();
        answer.setUserId(hostHolder.getUser().getId());
        answer.setContent(content);
        answer.setQuestionId(questionId);
        answer.setCreatedDate(new Date());
        answerDao.addAnswer(answer);
        int answerCount = answerDao.getAnswerCount(questionId);
        questionService.updateAnswerCount(questionId, answerCount);
    }
}