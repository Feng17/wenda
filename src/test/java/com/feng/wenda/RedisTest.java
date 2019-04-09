package com.feng.wenda;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisTest {
    @Autowired
    JedisPool jedisPool;


    private boolean set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return "OK".equals(jedis.set(key, value));
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }


    private String get(String key) {
        Jedis jedis = null;
        String value;
        try {
            jedis = jedisPool.getResource();
            value = jedis.get(key);
        } finally {
            if (jedis != null)
                jedis.close();
        }
        return value;
    }


    @Test
    public void testGet() {
        // test set
        boolean status = set("name2", "jim");
        Assert.assertTrue(status);

        // test get
        String str = get("name2");
        Assert.assertEquals("jim", str);
    }

}
