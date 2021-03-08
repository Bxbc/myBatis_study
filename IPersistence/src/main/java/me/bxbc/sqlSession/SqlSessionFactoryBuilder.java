package me.bxbc.sqlSession;

import me.bxbc.config.XMLConfigBuilder;
import me.bxbc.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Author: BI XI
 * Date 2021/3/8
 */
public class SqlSessionFactoryBuilder {
//    使用dom4j解析配置文件，将解析出来的内容封装到configuration中
    public SqlSessionFactory build(InputStream in) throws PropertyVetoException, DocumentException, FileNotFoundException {
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(in);
//        创建sqlSessionFactory对象
//        是一个工厂类：生成sqlSession会话对象
        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        return defaultSqlSessionFactory;
    }
}
