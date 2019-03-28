package com.feng.wenda.controller;

import com.feng.wenda.model.Question;
import com.feng.wenda.model.Topic;
import com.feng.wenda.service.AnswerService;
import com.feng.wenda.service.QuestionService;
import com.feng.wenda.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AnswerController {
    @Autowired
    AnswerService answerService;
    @Autowired
    TopicService topicService;
    @Autowired
    QuestionService questionService;

    @RequestMapping(path = "/addAnswer", method = RequestMethod.POST)
    public String addComment(@RequestParam("content") String content,
                             @RequestParam("questionId") int questionId) {
        answerService.addAnswer(content, questionId);
        return "redirect:/question/" + String.valueOf(questionId);

    }


    @RequestMapping(value = "/question/{questionId}/answerQuestion", method = RequestMethod.GET)
    public String answerQuestion(Model model, @PathVariable("questionId") int questionId) {
        Question question = questionService.selectQuestionById(questionId);
        if (question == null) {
            return "redirect:/index";
        }
        Topic topic = topicService.selectTopicById(question.getTopicId());
        model.addAttribute("question", question);
        model.addAttribute("topic", topic);
        return "answerQuestion";
    }
}
