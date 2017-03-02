package net.platform.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * 功能描述：ip工具类
 * 
 * @author 
 * 
 * @version 0.1
 *          <p>
 *          修改历史：(修改人，修改时间，修改原因/内容)
 *          </p>
 */
public class IpUtil {

    /**
     * 
     * 功能描述：获取登录用户IP地址
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:08:12
     *        </p>
     * @param request
     * @return String
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        	ip = request.getHeader("X-Real-IP");  
        }  
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            ip = "127.0.0.1";
        }        
        if (ip != null && ip.trim().length() > 0) {  
            int index = ip.indexOf(',');  
            return (index != -1) ? ip.substring(0, index) : ip;  
        }
        return ip;
    }

}
