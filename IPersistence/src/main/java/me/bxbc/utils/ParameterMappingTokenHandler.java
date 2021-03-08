package me.bxbc.utils;

import java.util.ArrayList;
import java.util.List;

//来源自mybatis源码
public class ParameterMappingTokenHandler implements TokenHandler{
    private List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();

    // content拿到的是 #{} 中的值
    @Override
    public String handleToken(String content) {
        parameterMappings.add(buildParameterMapping(content));
        return "?";
    }

    private ParameterMapping buildParameterMapping(String content) {
        ParameterMapping parameterMapping = new ParameterMapping(content);
        return parameterMapping;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public void setParameterMappings(List<ParameterMapping> parameterMappings) {
        this.parameterMappings = parameterMappings;
    }
}
