package net.platform.utils;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

/***
 * 
 * 功能描述：设置WEB层文件路径
 * 
 * @author 
 * 
 * @version 0.1
 *          <p>
 *          修改历史：(修改人，修改时间，修改原因/内容)
 *          </p>
 */
public class ResourcePathExposer implements ServletContextAware {
    /**
     * 全局类型
     */
    private ServletContext servletContext;

    /**
     * 
     * 功能描述：初始化加载
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午03:32:47
     *        </p>
     */
    public void init() {
        this.getServletContext().setAttribute("webPath", getServletContext().getContextPath());
        this.getServletContext().setAttribute("baidu_map_key", Const.BAIDU_MAP_KEY);
        this.getServletContext().setAttribute("baidu_map_version", Const.BAIDU_MAP_VERSION);
    }

    /**
     * 
     * 功能描述：设置全文对象
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午03:32:47
     *        </p>
     * @param servletContext
     */
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    /**
     * 
     * 功能描述：获取全文对象
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午03:32:47
     *        </p>
     * @return servletContext
     */
    public ServletContext getServletContext() {
        return servletContext;
    }

}
