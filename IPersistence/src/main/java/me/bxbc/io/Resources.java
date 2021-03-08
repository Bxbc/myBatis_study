package me.bxbc.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Author: BI XI
 * Date 2021/3/8
 */

//将配置文件xml加载成字节流输入到内存中
public class Resources {
    public static InputStream getResourceAsStream (String path) throws FileNotFoundException {
        System.out.println(path);
        File file = new File(path);
        InputStream resourceAsStream = new FileInputStream(file);
        return resourceAsStream;
    }
}
