package com.feng.wenda.controller;

import com.feng.wenda.model.HostHolder;
import com.feng.wenda.model.Question;
import com.feng.wenda.model.ViewObject;
import com.feng.wenda.service.QuestionService;
import com.feng.wenda.service.TopicService;
import com.feng.wenda.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    QuestionService questionService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    UserService userService;
    @Autowired
    TopicService topicService;

    @RequestMapping(value = {"/index"}, method = RequestMethod.GET)
    public String home(Model model, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "8") int pageSize) {
        model.addAttribute("vos", selectQuestions(pageNum, pageSize));
        return "index";
    }

    private PageInfo selectQuestions(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Question> questionList = questionService.selectQuestionList();
        List<ViewObject> vos = new ArrayList<ViewObject>();
        for (Question question : questionList) {
            ViewObject vo = new ViewObject();
            vo.set("question", question);
            vo.set("user", userService.selectUserById(question.getUserId()));
            vo.set("topic", topicService.selectTopicById(question.getTopicId()));
            vos.add(vo);
        }
        PageInfo pageResult = new PageInfo(questionList);
        pageResult.setList(vos);
        return pageResult;
    }

}
