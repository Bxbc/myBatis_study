package me.bxbc.sqlSession;

import me.bxbc.config.BoundSql;
import me.bxbc.pojo.Configuration;
import me.bxbc.pojo.MappedStatement;
import me.bxbc.utils.GenericTokenParser;
import me.bxbc.utils.ParameterMapping;
import me.bxbc.utils.ParameterMappingTokenHandler;
import me.bxbc.utils.TokenHandler;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: BI XI
 * Date 2021/3/8
 */
public class SimpleExecutor implements Executor{
    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException, IntrospectionException, InstantiationException, InvocationTargetException {
//        1. 注册驱动，获取连接
        Connection connection = configuration.getDataSource().getConnection();
//        2. 获取sql语句
        String sql = mappedStatement.getSql();
//        System.out.println(sql);
//        3. 转换sql语句，将占位符进行替换
        BoundSql boundSql = getBoundSql(sql);
//        4. 获取预处理对象，preparedStatement
//        System.out.println(boundSql.getSqlText());
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());
//        5. 设置参数
        String paramterType = mappedStatement.getParamterType();
        Class<?> paramtertypeClass = getClassType(paramterType);

        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        for(int i=0;i<parameterMappings.size();i++) {
            ParameterMapping parameterMapping = parameterMappings.get(i);
            String content = parameterMapping.getContent();
//            使用反射，根据参数名称获取实体对象的属性值
            Field declaredField = paramtertypeClass.getDeclaredField(content);
//            防止是私有对象，使用暴力访问
//            再根据属性值匹配返回结果中的字段
//            首先获取实体的全路径
            declaredField.setAccessible((true));
            Object o = declaredField.get(params[0]);
            preparedStatement.setObject(i+1,o);
        }
//        6. 执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        String resultType = mappedStatement.getResultType();
        Class<?> resultTypeClass = getClassType(resultType);

        ArrayList<Object> objects = new ArrayList<>();
//        7. 封装返回结果
        while (resultSet.next()) {
            Object o = resultTypeClass.newInstance();
//            取出resultSet的元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            for(int i=1;i<= metaData.getColumnCount();i++) {
//                获取字段名
                String columnName = metaData.getColumnName(i);
//                获取字段值
                Object value = resultSet.getObject(columnName);
//                使用反射或内省，根据数据库表和实体对象的对应关系完成封装
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName,resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o,value);
            }
            objects.add(o);
        }
        return (List<E>)objects;
    }

    private Class<?> getClassType(String paramterType) throws ClassNotFoundException {
//        根据类的全路径返回对应的对象
        if(paramterType != null) {
            Class<?> aclass = Class.forName(paramterType);
            return aclass;
        }
        return null;
    }

    /**
     * 将占位符 #{} 用问好代替
     * 解析 #{} 中的内容进行存储
     *
     * @param sql
     * @return
     */
    private BoundSql getBoundSql(String sql) {
//        标记处理类：配合标记解析器来完成对占位符的处理工作
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
//        解析出来的sql语句
        String parseSql = genericTokenParser.parse(sql);
//        #{} 里面解析出来的参数名称
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        BoundSql boundSql = new BoundSql(parseSql,parameterMappings);
        return boundSql;
    }
}
