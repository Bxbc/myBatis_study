package me.bxbc.sqlSession;

import me.bxbc.pojo.Configuration;
import me.bxbc.pojo.MappedStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * Author: BI XI
 * Date 2021/3/8
 */
public interface Executor {
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException, IntrospectionException, InstantiationException, InvocationTargetException;
}
