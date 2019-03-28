package com.feng.wenda.controller;

import com.feng.wenda.model.Answer;
import com.feng.wenda.model.Question;
import com.feng.wenda.model.Topic;
import com.feng.wenda.model.ViewObject;
import com.feng.wenda.service.AnswerService;
import com.feng.wenda.service.QuestionService;
import com.feng.wenda.service.TopicService;
import com.feng.wenda.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    TopicService topicService;
    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;
    @Autowired
    AnswerService answerService;

    @RequestMapping(value = "/askQuestion", method = RequestMethod.GET)
    public String askQuestion(Model model) {
        List<Topic> topicList = topicService.selectTopicList();
        model.addAttribute("topicList", topicList);
        return "askQuestion";
    }


    @RequestMapping(value = "/addQuestion", method = RequestMethod.POST)
    public String add(@RequestParam("title") String title,
                      @RequestParam("content") String content,
                      @RequestParam("topicId") int topicId) {

        questionService.addQuestion(title, content, topicId);
        return "redirect:/index";
    }


    @RequestMapping(value = "/question/{questionId}")
    public String questionDetail(Model model, @PathVariable("questionId") int questionId, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "8") int pageSize) {
        Question question = questionService.selectQuestionById(questionId);
        if (question == null) {
            return "redirect:/index";
        }
        Topic topic = topicService.selectTopicById(question.getTopicId());
        model.addAttribute("question", question);
        model.addAttribute("vos", selectAnswers(pageNum, pageSize, questionId));
        model.addAttribute("topic", topic);
        return "questionDetail";
    }


    private PageInfo selectAnswers(int pageNum, int pageSize, int questionId) {
        PageHelper.startPage(pageNum, pageSize);
        List<Answer> answerList = answerService.selectAnswerList(questionId);
        List<ViewObject> vos = new ArrayList<ViewObject>();
        for (Answer answer : answerList) {
            ViewObject vo = new ViewObject();
            vo.set("answer", answer);
            vo.set("user", userService.selectUserById(answer.getUserId()));
            vos.add(vo);
        }
        PageInfo pageResult = new PageInfo(answerList);
        pageResult.setList(vos);
        return pageResult;
    }

}
