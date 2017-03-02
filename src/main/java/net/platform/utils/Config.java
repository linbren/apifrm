package net.platform.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;


/*
 * 参考PropertiesUtil
 * 总结一下，可能只是两种写法 
第一：前面有 “   / ” 
“ / ”代表了工程的根目录，例如工程名叫做myproject，“ / ”代表了myproject 
me.class.getResourceAsStream("/com/x/file/myfile.xml"); 
第二：前面没有 “   / ” 
代表当前类的目录 
me.class.getResourceAsStream("myfile.xml"); 
me.class.getResourceAsStream("file/myfile.xml"); 
最后，自己的理解： 
getResourceAsStream读取的文件路径只局限与工程的源文件夹中，包括在工程src根目录下，
以及类包里面任何位置，但是如果配置文件路径是在除了源文件夹之外的其他文件夹中时，
该方法是用不了的。 
 * Config.getInt("server.port")
 */
public class Config {

    private static       Properties properties = new Properties();
//    private static final String     configName = "classpath*:/config/db.properties";
    private static final String     configName = "/config/db.properties";    
    private static Config instance;

    private Config() {
        try {
            properties.load(new InputStreamReader(getClass().getResourceAsStream(configName), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized static Config getInstance() {
        if (null == instance) {
            instance = new Config();
        }
        return instance;
    }

    public static int getInt( String str){
        try {
            if (null == instance) {
                getInstance();
            }
            return Integer.parseInt(properties.getProperty( str ));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long getLong( String str){
        try {
            if (null == instance) {
                getInstance();
            }
            return Long.parseLong( properties.getProperty( str ) );
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static double getDouble( String str){
        try {
            if (null == instance) {
                getInstance();
            }
            return Double.parseDouble(properties.getProperty( str ));

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getString( String str){
        try {
            if (null == instance) {
                getInstance();
            }
            return properties.getProperty( str );
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean getBoolean( String str){
        try {
            if (null == instance) {
                getInstance();
            }
            return Boolean.parseBoolean( properties.getProperty( str ));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void main(String[] args){
    	System.out.println(Config.getString("hibernate.dialect"));
    }
}
