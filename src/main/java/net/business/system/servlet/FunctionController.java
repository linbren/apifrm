package net.business.system.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.business.system.entity.TsFunction;
import net.business.system.service.FunctionService;
import net.platform.utils.AjaxJson;
import net.platform.utils.ServletUtils;
import net.platform.utils.StringUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;


@Controller
@RequestMapping("/func")
public class FunctionController extends BaseController {
	@Autowired
	private FunctionService functionService;
	
	public static Log log = LogFactory.getLog(FunctionController.class);
	/**
	 * 功能描述：跳转到列表页面
	 * @author zhangxin
	 * @created 2016-12-26 15:21:23
	 * @Email
	 * 
	 * @param rquest
	 * @return ModelAndView
	 */
	@RequestMapping(params = "goList")
	public ModelAndView goList(HttpServletRequest request){
		return new ModelAndView("system/function/func_list");
	}
	/**
	 * 功能描述：跳转到新增页面
	 * 
	 * @param tsRole
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(HttpServletRequest request){
		String id = request.getParameter("id");
		TsFunction func = new TsFunction();
		func.setParentCode(id);
		request.setAttribute("funcPage", func);
		return new ModelAndView("system/function/func_edit");
	}
	/**
	 * 功能描述：跳转到新增修改页面
	 * @author zhangxin
	 * @created 2016-12-26 15:21:23
	 * @Email
	 * 
	 * @param rquest
	 * @return ModelAndView
	 */
	@RequestMapping(params = "goEdit")
	public ModelAndView goEdit(HttpServletRequest request){
		String id = request.getParameter("id");
		TsFunction func = new TsFunction();
		func = functionService.getFunctionByCode(id).get(0);
		request.setAttribute("funcPage", func);
		return new ModelAndView("system/function/func_edit");
	}
	
	@RequestMapping(params = "getButtons")
	public void getButton(HttpServletRequest request,HttpServletResponse response){
		String json = functionService.getButtons(request);
		
		ServletUtils.outPrintJson(response, json);
	}
	
	@RequestMapping(params = "getList")
	public void getList(TsFunction tsFunc,
			HttpServletRequest request,HttpServletResponse response){
		String json = functionService.getFunctionTree(tsFunc);
		ServletUtils.outPrintJson(response, json);
	}
	
	@RequestMapping(params = "doSave")
	public void doSave(TsFunction tsFunc,HttpServletRequest request,
			HttpServletResponse response){
		AjaxJson json = new AjaxJson();
		String msg = "保存成功";
		try{
			if(tsFunc.getParentCode() == null){
				tsFunc.setParentCode("");
			}
			if(StringUtils.isNotBlank(tsFunc.getId())){
				functionService.saveOrUpdate(tsFunc);
			}else{
				functionService.save(tsFunc);
			}
			json.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			msg = "保存失败"+e.getMessage()+"<br>"+"请截图联系管理员。";
			json.setSuccess(false);
		}
		json.setMsg(msg);
		ServletUtils.outPrintJson(response, JSON.toJSONString(json));
	}
	
	@RequestMapping(params = "checkCode")
	public void checkCode(HttpServletRequest request,HttpServletResponse response){
		String funcCode = request.getParameter("funcCode");
		AjaxJson json = new AjaxJson();
		String msg = "此功能编号不存在,可以使用";
		try{
			List<TsFunction> funcs = functionService.getFunctionByCode(funcCode);
			if(funcs == null || funcs.size() == 0){
				json.setSuccess(true);
				json.setMsg(msg);
			}else{
				msg = "此功能编号已存在，不能使用";
				json.setMsg(msg);
				json.setSuccess(false);
			}
		}catch(Exception e){
			e.printStackTrace();
			msg = "出现错误";
			json.setMsg(msg);
			json.setSuccess(false);
		}
		ServletUtils.outPrintJson(response, JSON.toJSONString(json));
	}
	
	@RequestMapping(params = "doDel")
	public void doDel(HttpServletRequest request,
			HttpServletResponse response){
		String delCode = request.getParameter("delCode");
		AjaxJson json = functionService.delFunction(delCode);
		ServletUtils.outPrintJson(response, JSON.toJSONString(json));
		
	}
			
	
}
