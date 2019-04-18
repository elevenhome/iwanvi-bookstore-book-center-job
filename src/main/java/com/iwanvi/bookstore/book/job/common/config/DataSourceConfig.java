package com.iwanvi.bookstore.book.job.common.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


/**
 * 数据源配置
 * @author zzw
 * @since 2018年7月7日08:50:16
 */
@Configuration
public class DataSourceConfig {

    @Primary
    @Bean(name = "resourceProcessDataSource")
    @Qualifier("resourceProcessDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.resourceprocess")
    public DataSource resourceProcessDaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "resourceProcessJdbcTemplate")
    public JdbcTemplate resourceProcessJdbcTemplate(
            @Qualifier("resourceProcessDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "zwscCenterDataSource")
    @Qualifier("zwscCenterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.zwsccenter")
    public DataSource zwscCenterDaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "zwscCenterJdbcTemplate")
    public JdbcTemplate zwscCenterJdbcTemplate(
            @Qualifier("zwscCenterDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


}