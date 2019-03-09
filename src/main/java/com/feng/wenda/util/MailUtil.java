package com.feng.wenda.util;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailUtil {
    @Autowired
    private Configuration configuration;
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendTemplateMail(String username, String code, String email) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try

        {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(Constant.MAIL_FROM);
            helper.setTo(email);
            helper.setSubject("激活邮件");
            Map<String, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("active", Constant.DOMAIN_NAME.concat("activate?code=").concat(code));
            try {
                Template template = configuration.getTemplate("mail.html");
                String templateString = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
                helper.setText(templateString, true);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TemplateException e) {
                e.printStackTrace();
            }
            javaMailSender.send(mimeMessage);
        } catch (
                MessagingException e
                ) {
            e.printStackTrace();
        }
    }
}
