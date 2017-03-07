package net.business.system.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.api.RetMsg;
import net.platform.utils.ServletUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/except")
public class ExceptionHandler extends BaseController {
	@RequestMapping(value="/404")
	public void Handler404(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		resp.setRet(RetMsg.API_NOT_FOUND);
		resp.setMsg(RetMsg.retMsg.get(RetMsg.API_NOT_FOUND));
		ServletUtils.outPrintObjectToJson(response, resp);
	}
	@RequestMapping(value="/500")
	public void Handler500(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		resp.setRet(RetMsg.UNKNOWN_ERROR);
		resp.setMsg(RetMsg.retMsg.get(RetMsg.UNKNOWN_ERROR));
		ServletUtils.outPrintObjectToJson(response, resp);
	}

}