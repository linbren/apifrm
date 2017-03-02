package net.platform.utils;



import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.*;
import javax.servlet.http.*;
/*
 * SQL注入过虑器
 *
<filter>
<filter-name>filter</filter-name>
<filter-class>com.sql_inj.filter</filter-class>
<init-param>
<param-name>excludeURL</param-name>
<param-value>admin</param-value>
</init-param>
<init-param>
<param-name>badStr</param-name>
<param-value>
classloader|allowstaticmethodaccess|fileinputstream|runtime|processbuilder|fileoutputstream|getrealpath|redirectaction:|action:|redirect:|\b(and|or)\b.+?(=|>|in|is|like)|union[\s\S]+?select\b|(select|delete)[\s\S]+?(from|load_file|into)[\s\S]+?|insert[\s\S]+?into[\s\S]+?values|(create|alter|drop|truncate|backup)[\s\S]+?(table|database|view)|update[\s\S]+?set[\s\S]+?\=|\;[\s\S]*?\b(exec|declare|sp_configure)\b|\bon(click|mouse|key|load|error|blur|focus|touch)\=.+?|(alert|confirm|prompt|expression)\s*[\(\`]|waitfor.+?delay
</param-value>
</init-param>
<init-param>
<param-name>includeUpload</param-name>
<param-value>images/ db/</param-value>
</init-param>
</filter>
<filter-mapping>
<filter-name>filter</filter-name>
<url-pattern>/*</url-pattern>
<dispatcher>REQUEST</dispatcher>
</filter-mapping>
 * 
 */
public class SqlInjectFilter extends HttpServlet
    implements Filter
{
	private static final long serialVersionUID = 1L;
    private String badStr;
    private String excludeURL;
    private String includeUpload;	

	public SqlInjectFilter()
    {
        badStr = "";
        excludeURL = "";
        includeUpload = "";
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws ServletException, IOException
    {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;
        String querys = "";
        String method = req.getMethod();
        boolean isPostMethod = "POST".equalsIgnoreCase(method);
        String url = new String(req.getRequestURI());
        //JavaServer 漏洞http://xx:yy/javax.faces.resource.../WEB-INF/web.xml.jsf
        String filter_url_value = url.substring(url.lastIndexOf("/"), url.length());
        if("web.xml.jsf".equals(filter_url_value))
        {
            res.sendRedirect((new StringBuilder(String.valueOf(req.getContextPath()))).append("/err.htm").toString());
            return;
        }
        if(!isPostMethod)
        {
            if(req.getQueryString() != null)
                querys = req.getQueryString();
        } else
        {
            Map para = req.getParameterMap();
            for(Iterator it = para.keySet().iterator(); it.hasNext();)
            {
                String key = (String)it.next();
                String values[] = (String[])para.get(key);
                if(values != null && values.length > 0)
                {
                    for(int i = 0; i < values.length; i++)
                        if(values[i] != null)
                            querys = (new StringBuilder(String.valueOf(querys))).append(key).append(" ").append(values[i]).append(" ").toString();

                }
            }

        }
        String cookiestring = "";
        Cookie cookies[] = req.getCookies();
        Cookie sCookie = null;
        String cname = null;
        String cvalue = null;
        if(cookies != null)
        {
            for(int i = 0; i < cookies.length; i++)
            {
                sCookie = cookies[i];
                cname = sCookie.getName();
                cvalue = sCookie.getValue();
                cookiestring = (new StringBuilder(String.valueOf(cookiestring))).append(cname).append(" ").append(cvalue).append(" ").toString();
            }

        }
        if(!sql_inj(querys) && !sql_inj(cookiestring) && checkupload(url) || checkurl(url))
            chain.doFilter(request, response);
        else
            res.sendRedirect((new StringBuilder(String.valueOf(req.getContextPath()))).append("/err.htm").toString());
    }

    public boolean checkurl(String str)
    {
        if(excludeURL == null)
            return false;
        String inj_stra[] = excludeURL.split(" ");
        for(int i = 0; i < inj_stra.length; i++)
            if(str.toLowerCase().indexOf(inj_stra[i]) >= 0)
                return true;

        return false;
    }

    public boolean checkupload(String str)
    {
        if(includeUpload == null)
            return true;
        String inj_stra[] = includeUpload.split(" ");
        for(int i = 0; i < inj_stra.length; i++)
            if(str.toLowerCase().indexOf(inj_stra[i]) >= 0 && str.toLowerCase().indexOf(".jsp") >= 0)
                return false;

        return true;
    }

    public boolean sql_inj(String str)
    {
        boolean returnValue = false;
        str = str.toLowerCase();
        String regEx = badStr;
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        returnValue = m.find();
        return returnValue;
    }

    public void init(FilterConfig config)
        throws ServletException
    {
        badStr = config.getInitParameter("badStr");
        excludeURL = config.getInitParameter("excludeURL");
        includeUpload = config.getInitParameter("includeUpload");
    }

    public void destroy()
    {
    }
}