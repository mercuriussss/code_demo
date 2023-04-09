package com.ouroboros.springbootpractice.config;


import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.ouroboros.springbootpractice.dao.original", sqlSessionTemplateRef = "shardingJDBCSqlSessionTemplate")
public class ShardingOldSourceConfig {

    @Bean(name = "shardingJDBCDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.shardingjdbc")
    public DataSource shardingJDBCDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * sharding的默认数据源
     */
    @Bean(name = "shardingJDBCSqlSessionFactory")
    SqlSessionFactory shardingJDBCSqlSessionFactory(@Qualifier("shardingJDBCDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/original/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "shardingJDBCTransactionManager")
    DataSourceTransactionManager shardingJDBCTransactionManager(@Qualifier("shardingJDBCDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "shardingJDBCSqlSessionTemplate")
    SqlSessionTemplate shardingJDBCSqlSessionTemplate(@Qualifier("shardingJDBCSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
