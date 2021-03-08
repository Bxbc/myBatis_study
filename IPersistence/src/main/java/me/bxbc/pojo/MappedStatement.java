package me.bxbc.pojo;

/**
 * Author: BI XI
 * Date 2021/3/8
 */

// 创建自己的第一个容器对象
//    解析sqlMappConfig.xml
public class MappedStatement {
    private String id;
    private String resultType;
    private String paramterType;
    private String sql;

    public MappedStatement() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getParamterType() {
        return paramterType;
    }

    public void setParamterType(String paramterType) {
        this.paramterType = paramterType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @Override
    public String toString() {
        return "MappedStatement{" +
                "id='" + id + '\'' +
                ", resultType='" + resultType + '\'' +
                ", paramterType='" + paramterType + '\'' +
                ", sql='" + sql + '\'' +
                '}';
    }
}
