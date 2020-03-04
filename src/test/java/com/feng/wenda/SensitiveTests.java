package com.feng.wenda;

import com.feng.wenda.util.SensitiveFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = WendaApplication.class)
public class SensitiveTests {

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void testSensitiveFilter() {
        String text = "qaaq";
        text = sensitiveFilter.filter(text);
        System.out.println(text);

        text = "q☆a☆a☆q";
        text = sensitiveFilter.filter(text);
        System.out.println(text);
    }

}
