package net.platform.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * 功能描述：以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候中取出ApplicaitonContext.
 * 
 * @author 
 * 
 * @version 0.1
 *          <p>
 *          修改历史：(修改人，修改时间，修改原因/内容)
 *          </p>
 */
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

    /**
     * 上下文对象
     */
    private static ApplicationContext applicationContext = null;

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(SpringContextHolder.class);

    /**
     * 
     * 功能描述：实现ApplicationContextAware接口, 注入Context到静态变量
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:42:48
     *        </p>
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        logger.debug("注入ApplicationContext到SpringContextHolder:" + applicationContext);

        if (SpringContextHolder.applicationContext != null) {
            logger.warn("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:"
                    + SpringContextHolder.applicationContext);
        }

        SpringContextHolder.applicationContext = applicationContext; // NOSONAR
    }

    /**
     * 
     * 功能描述：实现DisposableBean接口,在Context关闭时清理静态变量
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:42:48
     *        </p>
     */
    public void destroy() throws Exception {
        SpringContextHolder.clear();
    }

    /**
     * 
     * 功能描述：取得存储在静态变量中的ApplicationContext.
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:42:48
     *        </p>
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        assertContextInjected();
        return applicationContext;
    }

    /**
     * 
     * 功能描述：从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:46:20
     *        </p>
     * @param <T>
     * @param name
     * @return <T> T
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }

    /**
     * 
     * 功能描述：从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:46:33
     *        </p>
     * @param <T>
     * @param requiredType
     * @return <T> T
     */
    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }

    /**
     * 
     * 功能描述：从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:46:44
     *        </p>
     * @param <T>
     * @param name
     * @param requiredType
     * @return
     */
    public static <T> T getBean(String name, Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBean(name, requiredType);
    }

    /**
     * 
     * 功能描述：清除SpringContextHolder中的ApplicationContext为Null
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:46:52
     *        </p>
     */
    public static void clear() {
        logger.debug("清除SpringContextHolder中的ApplicationContext:" + applicationContext);
        applicationContext = null;
    }

    /**
     * 
     * 功能描述：检查ApplicationContext不为空
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:47:06
     *        </p>
     */
    private static void assertContextInjected() {
        if (applicationContext == null) {
            throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
        }
    }
}
