package com.shuangzh.dao.mybatis;

import com.shuangzh.dao.mybatis.domain.*;
import com.shuangzh.dao.mybatis.mappers.StudentMapper;
import com.shuangzh.dao.mybatis.typehandlers.PhoneTypeHandler;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by admin on 2017/3/8.
 */

@Configuration
@EnableTransactionManagement
public class MybatisConfig implements TransactionManagementConfigurer{
    Logger logger = LoggerFactory.getLogger(MybatisConfig.class);

    //    @Bean
//    public SqlSessionFactory sqlSessionFactory() {
//        logger.info("going to build sqlSessionFacotory");
//        InputStream inputstream;
//        try {
//            inputstream = Resources.getResourceAsStream("mybatis-config.xml");
//            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputstream);
//            return sqlSessionFactory;
//        } catch (IOException e) {
//            logger.error("sqlSessionFactory been builded failed, " + e.getMessage());
//            e.printStackTrace();
//            throw new RuntimeException(e.getCause());
//        }
//    }

    @Bean(name = "mybatis_da")
    public DataSource dataSource() {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@//localhost:1521/ORCL";
        String username = "admin";
        String userpassword = "123456";
        PooledDataSource dataSource = new PooledDataSource(driver, url, username, userpassword);
        return dataSource;
    }

    @Bean(name = "apifactory")
    public SqlSessionFactory apiConfigured() {

//        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        TransactionFactory transactionFactory=new ManagedTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource());

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration(environment);
        configuration.getTypeAliasRegistry().registerAlias("Student", Student.class);
        configuration.getTypeAliasRegistry().registerAlias("Address", Address.class);
        configuration.getTypeAliasRegistry().registerAlias("Tutor", Tutor.class);
        configuration.getTypeAliasRegistry().registerAlias("Course", Course.class);
        configuration.getTypeHandlerRegistry().register(PhoneNumber.class, PhoneTypeHandler.class);
        configuration.addMapper(StudentMapper.class);
        configuration.addMappers("sql");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        return sqlSessionFactory;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("apifactory") SqlSessionFactory sqlSessionFactory) {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sqlSessionTemplate;
    }

    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
