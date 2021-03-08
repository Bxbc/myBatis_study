package me.bxbc.dao;

import me.bxbc.io.Resources;
import me.bxbc.pojo.User;
import me.bxbc.sqlSession.SqlSession;
import me.bxbc.sqlSession.SqlSessionFactory;
import me.bxbc.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;

import java.beans.IntrospectionException;
import java.beans.PropertyVetoException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * Author: BI XI
 * Date 2021/3/8
 */

//    在持久化层以这种方法实现对数据库的操作，存在冗余操作
//    思路是为Dao接口生成代理实现类
@Deprecated
public class UserDaoImp implements IUserDao{
    @Override
    public List<User> findAll() throws IllegalAccessException, IntrospectionException, InstantiationException, NoSuchFieldException, SQLException, InvocationTargetException, ClassNotFoundException, FileNotFoundException, PropertyVetoException, DocumentException {
        InputStream resourceAsStream = Resources.getResourceAsStream("/Users/bixi/Documents/myBatis_study/src/main/resources/sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<User> users = sqlSession.selectList("user.selectList");
        return users;
    }

    @Override
    public User findByCondition(User user) throws FileNotFoundException, PropertyVetoException, DocumentException, IllegalAccessException, IntrospectionException, InstantiationException, NoSuchFieldException, SQLException, InvocationTargetException, ClassNotFoundException {
        InputStream resourceAsStream = Resources.getResourceAsStream("/Users/bixi/Documents/myBatis_study/src/main/resources/sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
//        调用
//        测试单例查找
        User user1 = sqlSession.selectOne("user.selectOne", user);
        return user1;
    }
}
