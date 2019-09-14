package com.nekosighed.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 存放一些常用工具方法
 * @author lyl
 */
public class CommonUtils {
    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);
    /**
     * 获取指定文件内容键值对的指定键的值
 *      要求格式为
     *      key: value
     * @param fileUrl
     * @param key
     * @return
     */
    public static String getTargetFileValueByKey(String fileUrl, String key) {
        // 配置文件对象
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(fileUrl)) {
            // 加载输入流到配置文件对象中
            properties.load(fileInputStream);
            // 返回指定key对应的value
            return properties.getProperty(key);
        } catch (IOException e) {
            logger.warn("读取配置内容出现问题: " + e.toString());
        }
        return null;
    }
}
