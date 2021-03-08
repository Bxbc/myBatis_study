package me.bxbc.config;

import me.bxbc.utils.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: BI XI
 * Date 2021/3/8
 */
public class BoundSql {
//    解析完成的sql语句
    private String sqlText;

    private List<ParameterMapping> parameterMappings = new ArrayList<>();

    public BoundSql(String sqlText, List<ParameterMapping> parameterMappings) {
        this.sqlText = sqlText;
        this.parameterMappings = parameterMappings;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public void setParameterMappings(List<ParameterMapping> parameterMappings) {
        this.parameterMappings = parameterMappings;
    }
}
