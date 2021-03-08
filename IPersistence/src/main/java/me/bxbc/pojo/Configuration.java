package me.bxbc.pojo;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: BI XI
 * Date 2021/3/8
 */
//第二个自己的容器对象
public class Configuration {

    private DataSource dataSource;
    /*
     * key: statementId = namespace + id
     * value: MappedStatement 对象
     */
    Map<String, MappedStatement> mappedStatementMap = new HashMap<>();

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MappedStatement> getMappedStatementMap() {
        return mappedStatementMap;
    }

    public void setMappedStatementMap(Map<String, MappedStatement> mappedStatementMap) {
        this.mappedStatementMap = mappedStatementMap;
    }
}
