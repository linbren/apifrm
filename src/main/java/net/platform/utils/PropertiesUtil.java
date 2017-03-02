package net.platform.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * 
 * 描述 读写配置文件工具类
 * 
 * @author yiting lin
 * @created 2015-1-30 上午11:35:33
 */
public class PropertiesUtil {
    /**
     * 配置文件名称
     */
    private String properiesName = "";

    /**
     * 
     * 描述 构造函数：读写配置文件工具类
     * 
     * @author yiting lin
     * @created 2015-2-4 下午04:12:06
     */
    public PropertiesUtil() {

    }

    /**
     * 
     * 描述 构造函数：读写配置文件工具类
     * 
     * @author yiting lin
     * @created 2015-2-4 下午04:12:13
     * @param fileName
     */
    public PropertiesUtil(String fileName) {
        this.properiesName = fileName;
    }

    /**
     * 
     * 描述 读配置文件
     * 
     * @author yiting lin
     * @created 2015-2-4 下午04:11:56
     * @param key
     * @return
     */
    public String readProperty(String key) {
        String value = "";
        InputStream is = null;
        try {
            is = PropertiesUtil.class.getClassLoader().getResourceAsStream(
                    properiesName);
            Properties p = new Properties();
            p.load(is);
            value = p.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    /**
     * 
     * 描述 获取配置文件内容
     * 
     * @author yiting lin
     * @created 2015-2-4 下午04:11:48
     * @return
     */
    public Properties getProperties() {
        Properties p = new Properties();
        InputStream is = null;
        try {
            is = PropertiesUtil.class.getClassLoader().getResourceAsStream(
                    properiesName);
            p.load(is);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return p;
    }

    /**
     * 
     * 描述 写配置文件
     * 
     * @author yiting lin
     * @created 2015-2-4 下午04:11:38
     * @param key
     * @param value
     */
    public void writeProperty(String key, String value) {
        InputStream is = null;
        OutputStream os = null;
        Properties p = new Properties();
        try {
            is = new FileInputStream(properiesName);
            p.load(is);
            os = new FileOutputStream(PropertiesUtil.class.getClassLoader()
                    .getResource(properiesName).getFile());

            p.setProperty(key, value);
            p.store(os, key);
            os.flush();
            os.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (null != is)
                    is.close();
                if (null != os)
                    os.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
}
