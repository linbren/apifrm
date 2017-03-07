package net.business.system.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.business.system.entity.TsApp;
import net.business.system.service.AppService;
import net.platform.utils.AjaxJson;
import net.platform.utils.ServletUtils;
import net.platform.utils.StringUtils;
import net.platform.utils.page.PageUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;


/**
 * 功能描述：应用管理
 * @author  
 * @Email  
 * 
 *        <p>
 *        修改历史：(修改人，修改时间，修改原因/内容)
 *        </p>
 *        
 */
@Controller
@RequestMapping("/app")
public class AppController extends BaseController{
	
	@Autowired
	private AppService appService;
	
	/**
     * 
     * 功能描述：跳转到列表页面
     * 
     * @author zhangxin
     * @Email 
     *        <p>
     *        创建日期 ：
     *        </p>
     * 
     * @param request
     * @return
     */
	@RequestMapping(params = "goList")
	public ModelAndView goList(HttpServletRequest request){
		return new ModelAndView("system/app/app_list");
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
		return new ModelAndView("system/app/app_edit");
	}
	/**
	 * 
     * 功能描述：跳转到新增编辑页面
     * 
     * @author zhangxin
     * @Email 
     *        <p>
     *        创建日期 ：
     *        </p>
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "goEdit")
	public ModelAndView goEdit(TsApp tsApp, HttpServletRequest request){
		TsApp app = new TsApp();
		if(StringUtils.isNotBlank(tsApp.getId())){
			app = appService.get(tsApp.getId());
		}
		request.setAttribute("appPage", app);
		return new ModelAndView("system/app/app_edit");
	}
	/**
	 * 功能描述：获取列表
	 * @author zhangxin
     * @Email 
     *        <p>
     *        创建日期 ：
     *        </p>
     *        
	 * @param pageUtil
	 * @param tsApp
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "getList")
	public void getList(PageUtil pageUtil,TsApp tsApp,
			HttpServletRequest request, HttpServletResponse response){
		String json = appService.getAppByPage(pageUtil, tsApp);
		ServletUtils.outPrintJson(response, json);
	}
	/**
	 * 功能描述：新增或修改应用
	 * @author zhangxin
     * @Email 
     *        <p>
     *        创建日期 ：
     *        </p>
     *        
	 * @param tsApp
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "doSave")
	public void doSave(TsApp tsApp, HttpServletRequest request, 
			HttpServletResponse response){
		AjaxJson json = new AjaxJson();
		String act = request.getParameter("act");
		try{
			if(act.equals("add")){
				appService.save(tsApp);
			}else{
				appService.saveOrUpdate(tsApp);
			}
			json.setMsg("保存成功");
			json.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			json.setMsg("保存失败"+e.getMessage()+"<br/>请截图联系管理员");
			json.setSuccess(false);
		}
		ServletUtils.outPrintJson(response, JSON.toJSONString(json));
	}
	
	/**
	 * 功能描述：验证应用编号
	 * @author zhangxin
     * @Email 
     *        <p>
     *        创建日期 ：
     *        </p>
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "checkId")
	public void checkId(HttpServletRequest request, HttpServletResponse response){
		String id = request.getParameter("id");
		AjaxJson json = new AjaxJson();
		try{
			TsApp app = appService.get(id);
			if(app == null){
				json.setMsg("该编号不存在，可以使用");
				json.setSuccess(true);
			}else{
				json.setMsg("该编号已存在，不能使用");
				json.setSuccess(false);
			}
		}catch(Exception e){
			e.printStackTrace();
			json.setMsg("编号验证出现错误");
			json.setSuccess(false);
		}
		
		ServletUtils.outPrintJson(response, JSON.toJSONString(json));
	}
	
	/**
     * 功能描述：获取应用列表
     * @author zhangxin
     * @created 2016-12-23 14:58:34
     * @Emain
     * 
     * @param response
     */
    @RequestMapping(params = "getApp")
    public void getApp(HttpServletResponse response,boolean bNeedAll){
    	List<TsApp> apps = appService.getAppAll(bNeedAll);
    	ServletUtils.outPrintJson(response, JSON.toJSONString(apps));
    }
	
}
