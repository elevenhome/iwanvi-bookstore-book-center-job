package com.iwanvi.bookstore.book;

import com.alibaba.fastjson.JSONObject;
import com.iwanvi.bookstore.book.job.dao.resourceprocess.ChannelLibBookDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationTests.class);

    @Test
    public void contextLoads() {
    }



    @Autowired
    private ChannelLibBookDao channelLibBookDao;//渠道书籍
    @Test
    public void daoTests() {
        List<Map> list = channelLibBookDao.getBookCnIdsMap("11", 1000);
        LOGGER.info(JSONObject.toJSONString(list));
    }

}
