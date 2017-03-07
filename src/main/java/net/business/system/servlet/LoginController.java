package net.business.system.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.api.ApiResp;
import net.api.RetMsg;
import net.business.system.entity.TsFunction;
import net.business.system.entity.TsUser;
import net.business.system.service.FunctionService;
import net.business.system.service.LoginService;
import net.business.system.service.RoleService;
import net.business.system.service.UserService;
import net.platform.jwt.Jwt;
import net.platform.utils.ContextHolderUtils;
import net.platform.utils.ServletUtils;
import net.platform.utils.VerifyCodeUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {
	private static Log log = LogFactory.getLog(LoginController.class);

	@Autowired
	private LoginService loginService;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private FunctionService functionService;

	@RequestMapping(params = "getToken")
	public void getToken(TsUser tsUser, HttpServletRequest req,
			HttpServletResponse response) {
		String userCode=req.getParameter("userCode");
		String userPass=req.getParameter("userPass");
		log.info("userCode:" + tsUser.getUserCode() + "   userPass:"
				+ tsUser.getUserPass());
		ApiResp resp = new ApiResp();
		TsUser user = userService.getUserByCode(tsUser);
		if (null != user) {
			if (tsUser.getUserPass().equals(user.getUserPass())) {
				Date date = new Date();
				String token = null;
				Long iat = date.getTime();
				Long ext = iat + 2000 * 60 * 60;

				Map<String, Object> payload = new HashMap<String, Object>();
				payload.put("uid", user.getUserCode());// 用户id
				payload.put("iat", iat);// 生成时间:当前
				payload.put("ext", ext);// 过期时间2小时2000 * 60 * 60
				payload.put("roles", JSON.toJSONString(roleService
						.getRoleByUserId(user.getId())));//
				token = Jwt.createToken(payload);
				Map<String, String> tk = new HashMap<String, String>();
				tk.put("ext", ext.toString());
				tk.put("token", token);
				resp.setItems(tk);
				log.info("新生成的token是：" + token
						+ "\n请求受保护资源需要在Header带上token进行校验");
			} else {
				resp.setRet(RetMsg.ACCOUNT_PWD_ERROR);
				resp.setMsg(RetMsg.getMsg(RetMsg.ACCOUNT_PWD_ERROR));
			}
		} else {
			resp.setRet(RetMsg.ACCOUNT_NOT_EXIST);
			resp.setMsg(RetMsg.getMsg(RetMsg.ACCOUNT_NOT_EXIST));
		}

		ServletUtils.outPrintObjectToJson(response, resp);
	}

	@RequestMapping(params = "toHome")
	public ModelAndView thome(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return new ModelAndView("toHome");
	}

	@RequestMapping(params = "toLogin")
	public ModelAndView toLogin(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String url = request.getParameter("url");
		request.setAttribute("url", url);
		return new ModelAndView("system/login/login");
	}

	@RequestMapping(params = "getVcode", method = RequestMethod.GET)
	public void getVcode(HttpServletResponse response,
			HttpServletRequest request) {
		try {
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/jpeg");

			// 生成随机字串
			String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
			// 存入会话session
			HttpSession session = request.getSession(true);
			session.setAttribute("_code", verifyCode.toLowerCase());
			// 生成图片
			int w = 100, h = 32;
			VerifyCodeUtils.outputImage(w, h, response.getOutputStream(),
					verifyCode);
		} catch (Exception e) {
			log.info("获取验证码异常： " + e.getMessage());
		}
	}

	@RequestMapping(params = "home")
	public ModelAndView home(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String url = request.getParameter("url");
		request.setAttribute("url", url);
		return new ModelAndView("system/main/main");
	}

	@RequestMapping(params = "doLogin")
	public ModelAndView doLogin(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return new ModelAndView("system/main/main");
	}

	@RequestMapping(params = "checkUser")
	@ResponseBody
	public void checkUser(TsUser tsUser, HttpServletRequest req,
			HttpServletResponse response) {
		log.info("userName:" + tsUser.getUserCode() + "   password:"
				+ tsUser.getUserPass());
		TsUser user = userService.getUserByCode(tsUser);

		ServletUtils.outPrintObjectToJson(response,
				loginService.checkUser(tsUser, user, req));
	}

	@RequestMapping(params = "logout")
	public ModelAndView logout(HttpServletRequest request) {
		HttpSession session = ContextHolderUtils.getSession();
		session.invalidate();
		// ClientManager.getInstance().removeClinet(session.getId());
		return new ModelAndView("system/login/login");
	}

	@RequestMapping(params = "getTree")
	public void getTree(HttpServletRequest req, HttpServletResponse response) {
		TsUser user = (TsUser) req.getSession().getAttribute("user");
		List<TsFunction> functions = functionService.getFunctionByUserId(user
				.getId());
		ServletUtils.outPrintObjectToJson(response,
				loginService.queryForZtree(functions));

	}

}
