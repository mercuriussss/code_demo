package com.ouroboros.springbootpractice.sharding.config;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.ouroboros.springbootpractice.dao.sharding", sqlSessionTemplateRef = "shardingSqlSessionTemplate")
public class ShardingDataSourceConfig {

    /**
     * sharding的默认数据源
     */

    @Bean(name = "shardingSqlSessionFactory")
    @Primary
    SqlSessionFactory shardingSqlSessionFactory(@Qualifier("shardingDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/sharding/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "shardingSqlSessionTemplate")
    @Primary
    SqlSessionTemplate shardingSqlSessionTemplate(@Qualifier("shardingSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
