package me.bxbc.sqlSession;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * Author: BI XI
 * Date 2021/3/8
 */
public interface SqlSession {
//    查询所有
    public <E> List<E> selectList(String statementId, Object... params) throws SQLException, IllegalAccessException, IntrospectionException, InstantiationException, NoSuchFieldException, InvocationTargetException, ClassNotFoundException;

//    查询单个
    public <T> T selectOne(String statementId, Object... params) throws SQLException, IllegalAccessException, IntrospectionException, InstantiationException, ClassNotFoundException, InvocationTargetException, NoSuchFieldException;

//    在这里为dao接口生成代理实现类
    public <T> T getMapper(Class<?> mapperClass);
}

