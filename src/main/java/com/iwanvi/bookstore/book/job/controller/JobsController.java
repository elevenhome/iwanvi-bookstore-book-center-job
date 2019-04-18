package com.iwanvi.bookstore.book.job.controller;

import com.iwanvi.bookstore.book.job.service.AudioBookIndexService;
import com.iwanvi.bookstore.book.job.service.BookIndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 任务手动运行
 * @author zzw
 * @since 2018年12月14日16:57:47
 */
@RestController
@Api(value = "任务手动运行")
public class JobsController {
    /**日志**/
    private static final Logger LOGGER = LoggerFactory.getLogger(JobsController.class);

    @Autowired
    private transient BookIndexService bookIndexService;
    @Autowired
    private transient AudioBookIndexService audioBookIndexService;

    @ApiOperation(value = "添加Book索引-普通图书")
    @GetMapping("jobs/setBookInfoToES")
    public String setBookInfoToES() {
        LOGGER.info("setBookInfoToES 请求......");
        new Thread() {
            public void run() {
                bookIndexService.createIndex();
            }
        }.start();
        return "success";
    }

    @ApiOperation(value = "添加Book索引-音频图书")
    @GetMapping("jobs/setAudioBookInfoToES")
    public String setAudioBookInfoToES() {
        LOGGER.info("setAudioBookInfoToES 请求......");
        new Thread() {
            public void run() {
                audioBookIndexService.createIndex();
            }
        }.start();
        return "success";
    }

    @ApiOperation(value = "测试", httpMethod = "GET")
    @GetMapping("jobs/test")
    String test(String param) {
        return "param=" + param;
    }
}
