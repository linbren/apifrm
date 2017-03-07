package net.platform.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
/**
 * 
 * 功能描述：Http与Servlet工具类.
 * 
 * @author 
 * 
 * @version 0.1
 *          <p>
 *          修改历史：(修改人，修改时间，修改原因/内容)
 *          </p>
 */
public class ServletUtils {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(ServletUtils.class);

    // -- Content Type 定义 --//
    /**
     * 文本类型
     */
    public static final String TEXT_TYPE = "text/plain";
    /**
     * json类型
     */
    public static final String JSON_TYPE = "application/json";
    /**
     * xml类型
     */
    public static final String XML_TYPE = "text/xml";
    /**
     * html类型
     */
    public static final String HTML_TYPE = "text/html";
    /**
     * js类型
     */
    public static final String JS_TYPE = "text/javascript";
    /**
     * excel类型
     */
    public static final String EXCEL_TYPE = "application/vnd.ms-excel";

    // -- Header 定义 --//
    /**
     * 头部定义作者
     */
    public static final String AUTHENTICATION_HEADER = "Authorization";

    // -- 常用数值定义 --//
    /**
     * 常用数值定义
     */
    public static final long ONE_YEAR_SECONDS = 60 * 60 * 24 * 365;

    /**
     * 
     * 功能描述：如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，
     * 那么真正的用户端的真实IP则是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:35:06
     *        </p>
     * @param request
     * @return String
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarder-For");
        if ((StringUtils.isBlank(ip)) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-Ip");
            if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-Ip");
            }
        } else {
            String[] ipArr = ip.split(",");
            for (String IP : ipArr) {
                if (!"unknown".equalsIgnoreCase(IP)) {
                    ip = IP;
                    break;
                }
            }
        }
        if ((StringUtils.isBlank(ip)) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 
     * 功能描述：设置客户端缓存过期时间 的Header
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:35:20
     *        </p>
     * @param response
     * @param expiresSeconds
     */
    public static void setExpiresHeader(HttpServletResponse response,
            long expiresSeconds) {
        // Http 1.0 header
        response.setDateHeader("Expires", System.currentTimeMillis()
                + expiresSeconds * 1000);
        // Http 1.1 header
        response.setHeader("Cache-Control", "private, max-age="
                + expiresSeconds);
    }

    /**
     * 
     * 功能描述：设置禁止客户端缓存的Header
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:35:29
     *        </p>
     * @param response
     */
    public static void setDisableCacheHeader(HttpServletResponse response) {
        // Http 1.0 header
        response.setDateHeader("Expires", 1L);
        response.addHeader("Pragma", "no-cache");
        // Http 1.1 header
        response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
    }

    /**
     * 
     * 功能描述：设置LastModified Header
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:35:39
     *        </p>
     * @param response
     * @param lastModifiedDate
     */
    public static void setLastModifiedHeader(HttpServletResponse response,
            long lastModifiedDate) {
        response.setDateHeader("Last-Modified", lastModifiedDate);
    }

    /**
     * 
     * 功能描述： 设置Etag Header
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:35:47
     *        </p>
     * @param response
     * @param etag
     */
    public static void setEtag(HttpServletResponse response, String etag) {
        response.setHeader("ETag", etag);
    }

    /**
     * 
     * 功能描述：根据浏览器If-Modified-Since Header, 计算文件是否已被修改.
     * 
     * 如果无修改, checkIfModify返回false ,设置304 not modify status.
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:35:55
     *        </p>
     * @param request
     * @param response
     * @param lastModified
     * @return
     */
    public static boolean checkIfModifiedSince(HttpServletRequest request,
            HttpServletResponse response, long lastModified) {
        long ifModifiedSince = request.getDateHeader("If-Modified-Since");
        if ((ifModifiedSince != -1) && (lastModified < ifModifiedSince + 1000)) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return false;
        }
        return true;
    }

    /**
     * 
     * 功能描述：根据浏览器 If-None-Match Header, 计算Etag是否已无效.
     * 
     * 如果Etag有效, checkIfNoneMatch返回false, 设置304 not modify status.
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:36:07
     *        </p>
     * @param request
     * @param response
     * @param etag
     * @return
     */
    public static boolean checkIfNoneMatchEtag(HttpServletRequest request,
            HttpServletResponse response, String etag) {
        String headerValue = request.getHeader("If-None-Match");
        if (headerValue != null) {
            boolean conditionSatisfied = false;
            if (!"*".equals(headerValue)) {
                StringTokenizer commaTokenizer = new StringTokenizer(
                        headerValue, ",");

                while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
                    String currentToken = commaTokenizer.nextToken();
                    if (currentToken.trim().equals(etag)) {
                        conditionSatisfied = true;
                    }
                }
            } else {
                conditionSatisfied = true;
            }

            if (conditionSatisfied) {
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                response.setHeader("ETag", etag);
                return false;
            }
        }
        return true;
    }

    /**
     * 
     * 功能描述：设置让浏览器弹出下载对话框的Header.
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:36:16
     *        </p>
     * @param response
     * @param fileName
     */
    public static void setFileDownloadHeader(HttpServletResponse response,
            String fileName) {
        try {
            // 中文文件名支持
            String encodedfileName = new String(fileName.getBytes(),
                    "ISO8859-1");
            response.setHeader("Content-Disposition", "attachment; filename=\""
                    + encodedfileName + "\"");
        } catch (UnsupportedEncodingException e) {
        }
    }

    /**
     * 
     * 功能描述：设置让浏览器弹出下载Excel对话框的Header
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:36:25
     *        </p>
     * @param response
     * @param fileName
     */
    public static void setExcelDownloadHeader(HttpServletResponse response,
            String fileName) {
        response.setContentType(EXCEL_TYPE);
        setFileDownloadHeader(response, fileName);
    }

    /**
     * 
     * 功能描述：取得带相同前缀的Request Parameters
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:36:33
     *        </p>
     * @param request
     * @param prefix
     * @return Map<String, Object>
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getParametersStartingWith(
            ServletRequest request, String prefix) {
        Assert.notNull(request, "Request must not be null");
        Enumeration paramNames = request.getParameterNames();
        Map<String, Object> params = new TreeMap<String, Object>();
        if (prefix == null) {
            prefix = "";
        }
        while (paramNames != null && paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            if ("".equals(prefix) || paramName.startsWith(prefix)) {
                String unprefixed = paramName.substring(prefix.length());
                String[] values = request.getParameterValues(paramName);
                if (values == null || values.length == 0) {
                    // Do nothing, no values found at all.
                } else if (values.length > 1) {
                    params.put(unprefixed, values);
                } else {
                    params.put(unprefixed, values[0]);
                }
            }
        }
        return params;
    }

    public static void buildRs(HttpServletRequest request,
            HttpServletResponse response, boolean success, String msg,
            Object obj) {
        flushResult(request, response, buildRs(success, msg, obj));
    }

    /**
     * 
     * 功能描述：返回信息最后JSON格式
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:36:48
     *        </p>
     * @param success
     * @param msg
     * @param obj
     * @return String
     */
    public static String buildRs(boolean success, String msg, Object obj) {
        StringBuilder strb = new StringBuilder("{\"ajaxResult\":");
        strb.append(success ? "1" : "0").append(",\"message\":\"").append(
                null == msg ? "" : msg).append("\",\"datas\":").append(
                null == obj ? "{}" : JSON.toJSONString(obj)).append("}");

        return strb.toString();
    }

    /**
     * 
     * 功能描述：安卓接口
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:36:57
     *        </p>
     * @param request
     * @param response
     * @param success
     * @param msg
     * @param obj
     */
    public static void rsAndroid(HttpServletRequest request,
            HttpServletResponse response, boolean success, String msg,
            Object obj) {
        flushResult(request, response, rsAndroid(success, msg, obj));
    }

    /**
     * 
     * 功能描述：客户端返回信息最后JSON格式
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:37:36
     *        </p>
     * @param success
     * @param msg
     * @param obj
     * @return String
     */
    public static String rsAndroid(boolean success, String msg, Object obj) {
        StringBuilder strb = new StringBuilder("{\"code\":");
        strb.append(success ? "1" : "0").append(",\"result\":\"").append(
                null == msg ? "" : msg).append("\",\"body\":").append(
                null == obj ? "{}" : JSON.toJSONString(obj)).append("}");

        return strb.toString();
    }

    /**
     * 
     * 功能描述：将用户数据返回给相应的客服端请求ajax页面
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:37:45
     *        </p>
     * @param request
     * @param response
     * @param str
     */
    @SuppressWarnings( { "unchecked", "unused" })
    public static void flushResult(HttpServletRequest request,
            HttpServletResponse response, Object str) {
        setDisableCacheHeader(response);
        response.setHeader("Content-type", JSON_TYPE);

        PrintWriter out = null;
        try {
            out = response.getWriter();
            logger.debug("用户数据返回: [ {} ]", str);

            out.print(str);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                out.close();
            }
        }
    }

    /**
     * 
     * 功能描述：返回xml格式数据到客服端页面
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:37:55
     *        </p>
     * @param request
     * @param response
     * @param xml
     */
    public static void outPrintXml(HttpServletRequest request,
            HttpServletResponse response, String xml) {
        setDisableCacheHeader(response);
        response.setHeader("Content-type", XML_TYPE);

        PrintWriter out = null;
        try {
            out = response.getWriter();
            logger.debug("用户数据返回: [ {} ]", xml);

            out.print(xml);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                out.close();
            }
        }
    }

    /**
     * 
     * 功能描述：下载
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:38:03
     *        </p>
     * @param request
     * @param response
     * @param filePath
     */
    @SuppressWarnings("unchecked")
    public static void downLoad(HttpServletRequest request,
            HttpServletResponse response, String filePath) {
        File file = new File(filePath);
        String fileName = file.getName();
        OutputStream os = null;
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            byte b[] = new byte[is.available()];
            is.read(b);
            response.setContentType("application/octet-stream");// 设置为下载application/x-download
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="
                    + new String(fileName.getBytes("UTF-8"), "ISO8859-1")); // 这个很重要

            os = new BufferedOutputStream(response.getOutputStream());
            os.write(b);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 
     * 功能描述：错误消息
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:38:16
     *        </p>
     * @param errors
     * @return String
     */
    public static String convertErrors2Msg(Errors errors) {
        List<FieldError> listErrors = errors.getFieldErrors();
        StringBuffer sb = new StringBuffer("");

        for (FieldError fieldError : listErrors) {
            sb.append(fieldError.getDefaultMessage() + "<br/>");
            if (logger.isDebugEnabled()) {
                logger.debug(fieldError.getDefaultMessage() + " "
                        + fieldError.getRejectedValue() + " "
                        + fieldError.getCode());
            }
        }

        return sb.toString();
    }

    /**
     * 
     * 功能描述：转出json返回给页面
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:38:56
     *        </p>
     * @param response
     * @param jsonStr
     */
    public static void outPrintJson(HttpServletResponse response, String jsonStr) {
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-store");
        System.out.println(jsonStr);
        try {
            PrintWriter pw = response.getWriter();
            jsonStr = jsonStr.replace("\\", "\\\\");
            Pattern p = Pattern.compile("\t|\r|\n");
            Matcher m = p.matcher(jsonStr);
            jsonStr = m.replaceAll("");
            pw.write(jsonStr);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * 功能描述：转出json返回给页面
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午04:39:06
     *        </p>
     * @param response
     * @param list
     */
    public static void outPrintObjectToJson(HttpServletResponse response,
            Object obj) {
        response.setHeader("Cache-Control", "no-store");
        response.setContentType("application/json");
        String json = JSON.toJSONString(obj,SerializerFeature.WriteMapNullValue);
        System.out.println("json:"+json);
        try {
            PrintWriter pw = response.getWriter();
            pw.write(json);
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * 描述 下载ftp文件
     * 
     * @author yiting lin
     * @created 2015-5-18 下午04:03:36
     * @param request
     * @param response
     * @param filePath
     */
    public static void downLoadFile(HttpServletRequest request,
            HttpServletResponse response, String filePath) {
        URL urlfile = null;
        HttpURLConnection httpUrl = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        try {
            urlfile = new URL(filePath);
            httpUrl = (HttpURLConnection) urlfile.openConnection();
            httpUrl.connect();
            bis = new BufferedInputStream(httpUrl.getInputStream());
            response.setContentType("application/octet-stream");// 设置为下载application/x-download
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="
                    + new String(fileName.getBytes("UTF-8"), "ISO8859-1")); // 这个很重要
            bos = new BufferedOutputStream(response.getOutputStream());
            int len = 2048;
            byte[] b = new byte[len];
            while ((len = bis.read(b)) != -1) {
                bos.write(b, 0, len);
            }
            bos.flush();
            bis.close();
            httpUrl.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
