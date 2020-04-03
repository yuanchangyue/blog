package com.changyue.blogserver.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author : 袁阊越
 * @description : 数据源配置
 * @date : 2020-03-27 12:23
 */
@Slf4j
@Configuration
@MapperScan(basePackages = DataSourceConfig.MAPPER_PACKAGE, sqlSessionFactoryRef = DataSourceConfig.SESSION_FACTORY)
public class DataSourceConfig {

    static final String SESSION_FACTORY = "dbSqlSessionFactory";

    /**
     * mapper 扫描包路径
     */
    public static final String MAPPER_PACKAGE = "com.changyue.blogserver.dao";

    /**
     * mapper 扫描实体类
     */
    public static final String MODEL_PACKAGE = "com.changyue.blogserver.model";

    /**
     * mapper xml 路径
     */
    public static final String MAPPER_XML_PATH = "classpath:mapper/*.xml";

    /**
     * 主业务数据源
     */
    public static final String BUSINESS_DATASOURCE = "spring.datasource.druid.business";

    /**
     * quartz数据源
     */
    public static final String QUARTZ_DATASOURCE = "spring.datasource.druid.quartz";


    private static final String DATASOURCE_NAME = "dbDataSource";

    @Primary
    @Bean(name = DATASOURCE_NAME)
    @ConfigurationProperties(prefix = BUSINESS_DATASOURCE)
    public DruidDataSource druidDataSource() {
        return new DruidDataSource();
    }

    @Bean
    @QuartzDataSource
    @ConfigurationProperties(prefix = QUARTZ_DATASOURCE)
    public DataSource quartzDataSource() {
        return new DruidDataSource();
    }

    /**
     * 配置Mybatis环境
     */
    @Primary
    @Bean(name = SESSION_FACTORY)
    public SqlSessionFactory sqlSessionFactory() {
        final SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(druidDataSource());
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            sessionFactoryBean.setMapperLocations(resolver.getResources(MAPPER_XML_PATH));
            org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
            configuration.setMapUnderscoreToCamelCase(true);
            configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
            sessionFactoryBean.setConfiguration(configuration);
            sessionFactoryBean.setTypeAliasesPackage(MODEL_PACKAGE);
            return sessionFactoryBean.getObject();
        } catch (Exception e) {
            log.error("配置SqlSessionFactory失败，error:{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

}
