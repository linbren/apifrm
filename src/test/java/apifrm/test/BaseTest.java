package apifrm.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 配置spring和junit整合，junit启动时加载springIOC容器 spring-test,junit
 */
/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml",
		"classpath:springMVC-servlet.xml" })*/
public class BaseTest {
	 protected String getMethodName() { 
	        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();  
	        StackTraceElement e = stacktrace[2];  
	        String methodName = e.getMethodName();  
	        return methodName;  
	    }  
}