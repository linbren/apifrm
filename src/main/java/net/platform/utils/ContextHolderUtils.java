package net.platform.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 
 * 功能描述：上下文工具类
 * 
 * @author 
 * 
 * @version 0.1
 *          <p>
 *          修改历史：(修改人，修改时间，修改原因/内容)
 *          </p>
 */
public class ContextHolderUtils {

    /**
     * 
     * 功能描述：SpringMvc下获取request
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:02:17
     *        </p>
     * @return
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        return request;

    }

    /**
     * 
     * 功能描述：SpringMvc下获取session
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:02:27
     *        </p>
     * @return
     */
    public static HttpSession getSession() {
        HttpSession session = getRequest().getSession();
        return session;

    }

}
