package com.iwanvi.bookstore.book.job.common.config;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;


/**
 * 配置jest客户端
 *
 * @author zzw
 * @since 2018年12月17日20:39:15
 */
@Configuration
public class JestSpringConfiguration {
    /**
     * 日志
     **/
    private static final Logger LOGGER = LoggerFactory.getLogger(JestSpringConfiguration.class);
    /**
     * ES服务器地址
     **/
    @Value("${es.servers}")
    private String es_servers;
    /**
     * 连接超时时间
     **/
    private static final int CONN_TIME_OUT = 20000;
    /**
     * 读取超时时间
     **/
    private static final int READ_TIME_OUT = 60000;
    /**
     * 最大连接数
     **/
    private static final int MAX_TOTAL_CONN = 200;
    /**
     * 默认每个路由最大连接数
     **/
    private static final int DEF_MAX_TOTAL_CONN_PER_ROUTE = 200;


    /**
     * Jest客户端初始化
     *
     * @return
     */
    public @Bean
    JestClient jestClient() {
        LOGGER.info("jestClient初始化 es_servers:" + es_servers);
        String[] sers = es_servers.split(",");
        HttpClientConfig httpClientConfig =
                new HttpClientConfig
                        .Builder(Arrays.asList(sers))
                        .multiThreaded(true)
//                        .connTimeout(5000)
////                        .readTimeout(10000)
////                        .maxTotalConnection(100)
////                        .defaultMaxTotalConnectionPerRoute(50)
                        .connTimeout(CONN_TIME_OUT)
                        .readTimeout(READ_TIME_OUT)
                        .maxTotalConnection(MAX_TOTAL_CONN)
                        .defaultMaxTotalConnectionPerRoute(DEF_MAX_TOTAL_CONN_PER_ROUTE)
                        .build();
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(httpClientConfig);
        return factory.getObject();
    }

}
