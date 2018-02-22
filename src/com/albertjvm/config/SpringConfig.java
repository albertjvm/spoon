package com.albertjvm.config;

import com.albertjvm.data.*;
import com.albertjvm.service.square.SquareOauthService;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.albertjvm"})
public class SpringConfig extends WebMvcConfigurerAdapter {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/spn");
        dataSource.setUsername("spn");
        dataSource.setPassword("spn");
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());

        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
        sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
        sqlSessionFactory.getConfiguration().addMapper(SquareAccessTokenMapper.class);
        sqlSessionFactory.getConfiguration().addMapper(ShopifyAccessTokenMapper.class);
        sqlSessionFactory.getConfiguration().addMapper(ShopifyVarientMapper.class);
        sqlSessionFactory.getConfiguration().addMapper(UserMapper.class);
        sqlSessionFactory.getConfiguration().addMapper(OauthStateMapper.class);
        return sqlSessionFactory;
    }

    @Bean
    public SquareAccessTokenMapper squareAccessTokenMapper() throws Exception {
        SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sessionTemplate.getMapper(SquareAccessTokenMapper.class);
    }

    @Bean
    public ShopifyAccessTokenMapper shopifyAccessTokenMapper() throws Exception {
        SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sessionTemplate.getMapper(ShopifyAccessTokenMapper.class);
    }

    @Bean
    public ShopifyVarientMapper shopifyVarientMapper() throws Exception {
        SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sessionTemplate.getMapper(ShopifyVarientMapper.class);
    }

    @Bean
    public UserMapper userMapper() throws Exception {
        SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sessionTemplate.getMapper(UserMapper.class);
    }

    @Bean
    public OauthStateMapper oauthStateMapper() throws Exception {
        SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sessionTemplate.getMapper(OauthStateMapper.class);
    }

    @Bean
    public SquareOauthService squareOauthService() throws Exception {
        return new SquareOauthService(squareAccessTokenMapper());
    }
}
