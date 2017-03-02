package net.platform.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * 
 * 功能描述：项目参数工具类
 * 
 * @author 
 * 
 * @version 0.1
 *          <p>
 *          修改历史：(修改人，修改时间，修改原因/内容)
 *          </p>
 */
public class ResourceUtil {

   
    /**
     * 
     * 功能描述：获得请求路径
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:34:30
     *        </p>
     * @param request
     * @return
     */
    public static String getRequestPath(HttpServletRequest request) {
        String requestPath = request.getRequestURI() + "?" + request.getQueryString();
        if (requestPath.indexOf("&") > -1) {// 去掉其他参数
            requestPath = requestPath.substring(0, requestPath.indexOf("&"));
        }
        requestPath = requestPath.substring(request.getContextPath().length() + 1);// 去掉项目路径
        return requestPath;
    }

    /**
     * 
     * 功能描述：获得请求的菜单路径路径
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:34:30
     *        </p>
     * @param request
     * @return
     */
    public static String getRequestMenuPath(HttpServletRequest request) {
        String requestPath = request.getRequestURI() + "?" + request.getQueryString();
        requestPath = requestPath.substring(request.getContextPath().length() + 1);// 去掉项目路径
        return requestPath;
    }

}
