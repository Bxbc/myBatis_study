package me.bxbc.sqlSession;

import me.bxbc.pojo.Configuration;

/**
 * Author: BI XI
 * Date 2021/3/8
 */


public class DefaultSqlSessionFactory implements SqlSessionFactory{
    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
