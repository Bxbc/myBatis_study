package me.bxbc.sqlSession;

import me.bxbc.pojo.Configuration;
import me.bxbc.pojo.MappedStatement;

import java.beans.IntrospectionException;
import java.io.ObjectInputFilter;
import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.List;

/**
 * Author: BI XI
 * Date 2021/3/8
 */
public class DefaultSqlSession implements SqlSession{
    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws SQLException, IllegalAccessException, IntrospectionException, InstantiationException, NoSuchFieldException, InvocationTargetException, ClassNotFoundException {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        List<Object> list = simpleExecutor.query(configuration,mappedStatement,params);
        return (List<E>) list;
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws SQLException, IllegalAccessException, IntrospectionException, InstantiationException, ClassNotFoundException, InvocationTargetException, NoSuchFieldException {
        List<Object> objects = selectList(statementId,params);
        if(objects.size() == 1) {
            return (T) objects.get(0);
        } else{
            throw new RuntimeException("查询结果为空或返回结果过多");
        }
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
//        使用jdk动态代理来为Dao接口生成代理对象并返回
        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
//            首先需要一个类加载器，其次需要一个class数组，第三需要实现一个接口
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                重写invoke方法
//                proxy：当前代理对象的引用
//                method：当前被调用方法的引用
//                args：传递的参数
//                底层实际上还是去执行jdbc方法
//                准备参数：statementid=namespace.id
//                拿到方法名
                String methodName = method.getName();
//                拿到接口权限类名
                String className = method.getDeclaringClass().getName();
                String statementId = className + "." + methodName;
//                准备参数：params，即实际传入的参数 args
//                获取被调用方法返回值的类型
                Type genericReturnType = method.getGenericReturnType();
//                判断是否进行了范型类型参数化，简单判断返回类型是否有范型，如有就认为返回的是一个集合
                if(genericReturnType instanceof ParameterizedType) {
                    List<Object> objects = selectList(statementId, args);
                    return objects;
                }

                return selectOne(statementId, args);
            }
        });
        return (T) proxyInstance;
    }
}
