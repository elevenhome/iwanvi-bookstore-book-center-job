package com.iwanvi.bookstore.book.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 启动项
 * @author zzw
 * @since 2019年4月10日17:31:32
 */
@SpringBootApplication(scanBasePackages = {"com.iwanvi.bookstore.book.job"})
@EnableSwagger2
@EnableEurekaClient
@EnableScheduling //定时任务
public class Application {
    /**日志**/
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    /**
     * 启动项
     * @param args。
     * @author zzw
     * @since 2019年4月10日17:31:32
     */
    public static void main(String[] args) {
        LOGGER.info("----------------system beg----------------");
        SpringApplication.run(Application.class, args);
        LOGGER.info("----------------system end----------------");
    }

}
