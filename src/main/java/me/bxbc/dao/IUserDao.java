package me.bxbc.dao;

import me.bxbc.pojo.User;
import org.dom4j.DocumentException;

import java.beans.IntrospectionException;
import java.beans.PropertyVetoException;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * Author: BI XI
 * Date 2021/3/8
 */
public interface IUserDao {
    // 查询所有用户
    public List<User> findAll() throws IllegalAccessException, IntrospectionException, InstantiationException, NoSuchFieldException, SQLException, InvocationTargetException, ClassNotFoundException, FileNotFoundException, PropertyVetoException, DocumentException;

    // 根据条件进行查询
    public User findByCondition(User user) throws FileNotFoundException, PropertyVetoException, DocumentException, IllegalAccessException, IntrospectionException, InstantiationException, NoSuchFieldException, SQLException, InvocationTargetException, ClassNotFoundException;
}
