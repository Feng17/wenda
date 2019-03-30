package com.feng.wenda.service;

import com.feng.wenda.dao.QuestionDao;
import com.feng.wenda.model.HostHolder;
import com.feng.wenda.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;
    @Autowired
    HostHolder hostHolder;

    public List<Question> selectQuestionList() {
        return questionDao.selectQuestionList();
    }

    public void addQuestion(String title, String content, int topicId) {
        Question question = new Question();
        question.setTitle(title);
        question.setContent(content);
        question.setTopicId(topicId);
        question.setUserId(hostHolder.getUser().getId());
        question.setCreatedDate(new Date());
        question.setAnswerCount(0);
        questionDao.addQuestion(question);
    }


    public Question selectQuestionById(int questionId) {
        return questionDao.selectQuestionById(questionId);
    }


    public void updateAnswerCount(int questionId, int answerCount) {
        questionDao.updateAnswerCount(questionId, answerCount);
    }

    public List<Question> getUserQuestions(int userId){
        return questionDao.selectUserQuestions(userId);
    }
}
