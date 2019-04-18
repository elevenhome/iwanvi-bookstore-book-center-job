package com.iwanvi.bookstore.book.job.timer;

import com.iwanvi.bookstore.book.job.service.BookIndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * 定时任务（普通图书）
 * @author zzw
 * @since 2018年12月14日16:57:47
 */
@Component
public class BookJobs {
    /**日志**/
    private static final Logger LOGGER = LoggerFactory.getLogger(BookJobs.class);

    /**添加图书服务**/
    @Autowired
    private transient BookIndexService bookIndexService;


    /**每天（几点）点执行**/
    private static final String RUN_TIME_0 = "0 0 6 * * ?";
    /**每天（几点）点执行**/
    private static final String RUN_TIME_1 = "0 0 17 * * ?";

    /**
     * 添加图书数据到ES（上午执行）
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    @Scheduled(cron = RUN_TIME_0)
    public void setBookInfoToES0() {
        LOGGER.info("定时任务开始0......");
        bookIndexService.createIndex();
        LOGGER.info("定时任务结束0......");
    }

    /**
     * 添加图书数据到ES（下午执行）
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    @Scheduled(cron = RUN_TIME_1)
    public void setBookInfoToES1() {
        LOGGER.info("定时任务开始1......");
        bookIndexService.createIndex();
        LOGGER.info("定时任务结束1......");
    }

}





/*
 * cron表达式：* * * * * *（共6位，使用空格隔开，具体如下）
 * cron表达式：*(秒0-59) *(分钟0-59) *(小时0-23) *(日期1-31) *(月份1-12或是JAN-DEC) *(星期1-7或是SUN-SAT)
 * @Scheduled(cron = "0 0/5 * * * ?") //五分钟一次
 * @Scheduled(cron = "0 0 9 * * ?") // 每天9点执行
 * @Scheduled(cron = "0/10 * * * * ?") // 10s处理一次
 */
