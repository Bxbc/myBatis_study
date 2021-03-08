package me.bxbc.test;

import me.bxbc.dao.IUserDao;
import me.bxbc.io.Resources;
import me.bxbc.pojo.User;
import me.bxbc.sqlSession.SqlSession;
import me.bxbc.sqlSession.SqlSessionFactory;
import me.bxbc.sqlSession.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * Author: BI XI
 * Date 2021/3/8
 */
public class IPersistenceTest {
    @Test
    public void test() throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("/Users/bixi/Documents/myBatis_study/src/main/resources/sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
//        调用
//        测试单例查找
//        User user = new User();
//        user.setId(1);
//        user.setUsername("bixi");
//        User user1 = sqlSession.selectOne("user.selectOne", user);
//        System.out.println(user1);
//        测试多例查找
//        List<User> users = sqlSession.selectList("user.selectList");
//        for(User user : users) {
//            System.out.println(user);
//        }
        User user = new User();
        user.setId(1);
        user.setUsername("bixi");
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        User user1 = userDao.findByCondition(user);
        List<User> all = userDao.findAll();
        System.out.println(user1);
    }
}
