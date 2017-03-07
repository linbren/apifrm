package net.business.system.servlet;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.business.system.entity.TsArea;
import net.business.system.entity.TsFunction;
import net.business.system.service.AreaService;
import net.platform.utils.AjaxJson;
import net.platform.utils.ServletUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/area")
public class AreaController extends BaseController {
	public static Log log = LogFactory.getLog(AreaController.class);
	
	@Autowired
	private AreaService areaService;
	
	
	/**
	 * 功能描述：跳转到列表页面
	 * @author zhangxin
	 * @created 2017-01-03 16:44:23
	 * @Email
	 *  	<p>
	 *        修改历史：(修改人，修改时间，修改原因/内容)
	 *      </p>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "goList")
	public ModelAndView goList(HttpServletRequest request){
		return new ModelAndView("system/area/area_list");
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
		String parentName = request.getParameter("name");
		TsArea area = new TsArea();
		area.setParentId(id);
		request.setAttribute("areaPage", area);
		request.setAttribute("act", "add");
		request.setAttribute("parentName", parentName);
		return new ModelAndView("system/area/area_edit");
	}
	/**
	 * 功能描述：跳转到新增编辑页面
	 * @author zhangxin
	 * @created 2017-01-03 16:44:23
	 * @Email
	 *  	<p>
	 *        修改历史：(修改人，修改时间，修改原因/内容)
	 *      </p>
	 *      
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "goEdit")
	public ModelAndView goEdit(HttpServletRequest request){
		String id = request.getParameter("id");
		String parentName = request.getParameter("name");
		TsArea area = new TsArea();
		area = areaService.get(id);
		request.setAttribute("areaPage", area);
		request.setAttribute("act", "edit");
		request.setAttribute("parentName", parentName);
		return new ModelAndView("system/area/area_edit");
	}
	/**
	 * 功能描述：获取列表
	 * @author zhnangxin
	 * @created 2017-01-03 16:44:23
	 * @Email
	 *  	<p>
	 *        修改历史：(修改人，修改时间，修改原因/内容)
	 *      </p>
	 *      
	 * @param tsArea
	 * @param pageUtile
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "getList")
	public void getList(TsArea tsArea,HttpServletRequest request,
			HttpServletResponse response){
		/*String parentId = request.getParameter("parentId");
		log.info("parenId:"+parentId);
		TsArea area = new TsArea();
		area.setParentId(parentId);*/
		System.out.println("areaName:"+tsArea.getAreaName());
		String json = areaService.getAreaTree(tsArea);
		ServletUtils.outPrintJson(response, json);
	}
	/**
	 * 功能描述：保存修改
	 * @author zhangxin
	 * @category 2017-01-10 14:55:23
	 * @Email
	 * 		<p>
	 *        修改历史：(修改人，修改时间，修改原因/内容)
	 *      </p>
	 * 
	 * @param area
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "doSave")
	public void doSave(TsArea area,HttpServletRequest request,
			HttpServletResponse response){
		log.info("-----保存修改-----");
		String act = request.getParameter("act");
		AjaxJson json = new AjaxJson();
		try{
			if(act.equals("add")){
				area.setIsLeaf("1");
				areaService.save(area);
				TsArea parentArea = areaService.get(area.getParentId());
				//上一级如果是叶子节点则修改
				if(parentArea != null && parentArea.getIsLeaf().equals("1")){
					parentArea.setIsLeaf("0");
					areaService.saveOrUpdate(parentArea);
				}
			}else{
				areaService.saveOrUpdate(area);
			}
			json.setMsg("保存成功");
			json.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			json.setMsg("保存失败"+e.getMessage()+"<br>"+"请截图联系管理员。");
			json.setSuccess(false);
		}
		ServletUtils.outPrintJson(response, JSON.toJSONString(json));
	}
	
	@RequestMapping(params = "checkId")
	public void checkId(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		AjaxJson json = new AjaxJson();
		try{
			TsArea area = areaService.get(id);
			if(area != null){
				json.setMsg("该地区编号已存在，不可使用");
				json.setSuccess(false);
			}else{
				json.setMsg("该地区编号不存在，可以使用");
				json.setSuccess(true);
			}
		}catch(Exception e){
			e.printStackTrace();
			json.setMsg("验证地区编号时发生错误");
			json.setSuccess(false);
		}
		ServletUtils.outPrintJson(response, JSON.toJSONString(json));
	}
	
	@RequestMapping(params = "doDel")
	public void doDel(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		String json = areaService.delArea(id);
		ServletUtils.outPrintJson(response, json);
	}
	
}
