package net.platform.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.api.ApiResp;
import net.api.RetMsg;
import net.business.system.service.ApiControlService;
import net.business.system.service.impl.ApiControlServiceImpl;
import net.business.system.service.impl.CacheInit;
import net.platform.jwt.Jwt;
import net.platform.jwt.TokenState;
import net.platform.listener.CacheListener;
import net.platform.utils.ResourceUtil;
import net.platform.utils.ServletUtils;

import org.apache.log4j.Logger;

@WebFilter(urlPatterns = "/*")
public class Filter1_CheckToken implements Filter {
	protected Logger log = Logger.getLogger(this.getClass());
	private ApiResp resp = new ApiResp();
	@Override
	public void doFilter(ServletRequest argo, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) argo;
		HttpServletResponse response = (HttpServletResponse) arg1;
		String requestpath=ResourceUtil.getRequestPath(request);
		//APIS的获取要改到系统启动时初始化
		//1白名单API不拦截"login.do?getToken".equalsIgnoreCase(requestpath)
		//if (CacheListener.whiteApis.contains(requestpath)) {
		if ("login.do?getToken".equalsIgnoreCase(requestpath)) {
			// 登陆接口不校验token，直接放行
			chain.doFilter(request, response);
			return;
		}
		// 其他API接口一律校验token
		// 从请求头中获取token
		String token = request.getHeader("token");
		log.info("token:"+token);
		log.info("version:"+request.getHeader("version"));
		log.info("timestamp:"+request.getHeader("timestamp"));
		
		Map<String, Object> resultMap = Jwt.validToken(token);
		TokenState state = TokenState.getTokenState((String) resultMap
				.get("state"));
		switch (state) {
		case VALID:
			// 取出payload中数据,放入到request作用域中
			request.setAttribute("data", resultMap.get("data"));
			// 放行
			chain.doFilter(request, response);
			break;
		case EXPIRED:
			// token过期，则输出错误信息返回给ajax
			resp.setRet(RetMsg.TOKEN_EXPIRED);
			resp.setMsg(RetMsg.retMsg.get(RetMsg.TOKEN_EXPIRED));
			ServletUtils.outPrintObjectToJson(response, resp);
			break;
		case INVALID:
			// token过期或者无效，则输出错误信息返回给ajax
			resp.setRet(RetMsg.TOKEN_INVALID);
			resp.setMsg(RetMsg.retMsg.get(RetMsg.TOKEN_INVALID));
			ServletUtils.outPrintObjectToJson(response, resp);
			break;
		}
	}

	public void output(String jsonStr, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html;charset=UTF-8;");
		PrintWriter out = response.getWriter();
		out.write(jsonStr);
		out.flush();
		out.close();

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		log.debug("token过滤器初始化了");
		
	}

	@Override
	public void destroy() {

	}

}
