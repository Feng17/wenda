package com.feng.wenda.controller;


import com.feng.wenda.model.HostHolder;
import com.feng.wenda.model.User;
import com.feng.wenda.service.UserService;
import com.feng.wenda.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> register(String username, String email, String password) {
        Map<String, Object> map = userService.register(username, email, password);
        return map;
    }

    @RequestMapping(value = "/activate", method = RequestMethod.GET)
    public String activate(@RequestParam("code") String code, Model model) {
        Map<String, Object> map = userService.activate(code);
        model.addAttribute("msg", map.get("msg"));
        return "login";
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> signin(Model model, String username, String password, HttpServletResponse response) {
        Map<String, Object> map = userService.login(username, password, response);
        return map;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        userService.logout(hostHolder.getUser().getId(), 1);
        return "redirect:/login";
    }


    @RequestMapping(value = "/user/editProfile")
    public String userProfile(Model model) {
        User user = userService.selectUserById(hostHolder.getUser().getId());
        model.addAttribute("user", user);
        return "editProfile";
    }

    @RequestMapping("/editProfile")
    public String editProfile(@RequestParam("name") String name,@RequestParam("description") String description ) {
        User user = new User();
        user.setName(name);
        user.setDescription(description);
        user.setId(hostHolder.getUser().getId());
        userService.updateUserProfile(user);
        return "redirect:/user/editProfile";
    }


    @RequestMapping(path = {"/uploadImage"}, method = {RequestMethod.POST})
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        userService.upload(file,hostHolder.getUser().getId());
        return "redirect:/user/editProfile";

    }

    @RequestMapping(value = "/image", method = RequestMethod.GET)
    public void getImage(@RequestParam("name") String imageName, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");
        response.setCharacterEncoding("UTF-8");
        FileInputStream in = new FileInputStream(Constant.IMAGE_DIR + imageName);
        int i = in.available();
        byte[] data = new byte[i];
        in.read(data);
        in.close();

        OutputStream out = response.getOutputStream();
        out.write(data);
        out.flush();
        out.close();
    }
}
