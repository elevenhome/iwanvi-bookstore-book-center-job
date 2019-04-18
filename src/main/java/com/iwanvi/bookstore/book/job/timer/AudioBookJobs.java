package com.iwanvi.bookstore.book.job.timer;

import com.iwanvi.bookstore.book.job.service.AudioBookIndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * 定时任务(音频图书)
 * @author zzw
 * @since 2018年12月14日16:57:47
 */
@Component
public class AudioBookJobs {

    /**日志**/
    private static final Logger LOGGER = LoggerFactory.getLogger(AudioBookJobs.class);

    /**添加图书服务**/
    @Autowired
    private transient AudioBookIndexService bookIndexService;


    /**每天（几点）点执行**/
    private static final String RUN_TIME_0 = "0 0 8 * * ?";
    /**每天（几点）点执行**/
    private static final String RUN_TIME_1 = "0 0 19 * * ?";

    /**
     * 添加图书数据到ES（上午执行）
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    @Scheduled(cron = RUN_TIME_0)
    public void setBookInfoToES0() {
        LOGGER.info("音频图书定时任务开始0......");
        bookIndexService.createIndex();
        LOGGER.info("音频图书定时任务结束0......");
    }

    /**
     * 添加图书数据到ES（下午执行）
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    @Scheduled(cron = RUN_TIME_1)
    public void setBookInfoToES1() {
        LOGGER.info("音频图书定时任务开始1......");
        bookIndexService.createIndex();
        LOGGER.info("音频图书定时任务结束1......");
    }



}
