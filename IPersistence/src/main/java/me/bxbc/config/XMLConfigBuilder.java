package me.bxbc.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import me.bxbc.io.Resources;
import me.bxbc.pojo.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * Author: BI XI
 * Date 2021/3/8
 */
public class XMLConfigBuilder {
    private Configuration configuration;

    public XMLConfigBuilder() {
        this.configuration = new Configuration();
    }

    //    该方法将配置进行解析，并封装进Configuration对象
    public Configuration parseConfig(InputStream inputStream) throws DocumentException, PropertyVetoException, FileNotFoundException {
        Document document = new SAXReader().read(inputStream);
//        拿到一个根对象
        Element rootElement = document.getRootElement();
//        拿到property list
        List<Element> list = rootElement.selectNodes("//property");
        Properties properties = new Properties();
        for(Element element : list) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name,value);
        }
//        根据xml中的配置信息初始化连接池
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("diverClass"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        comboPooledDataSource.setUser(properties.getProperty("username"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));
        configuration.setDataSource(comboPooledDataSource);
//        针对mapper.xml进行解析
//        首先拿到路径
        List<Element> mapList = rootElement.selectNodes("//mapper");
        for(Element element : mapList) {
            String mapPath = element.attributeValue("resource");
            InputStream resourceAsStream = Resources.getResourceAsStream(mapPath);
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
            xmlMapperBuilder.parse(resourceAsStream);
        }
        return configuration;
    }
}
