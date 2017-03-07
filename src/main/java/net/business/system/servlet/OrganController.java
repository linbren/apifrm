package net.business.system.servlet;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.business.system.entity.TsArea;
import net.business.system.entity.TsOrgan;
import net.business.system.entity.TsOrganExt;
import net.business.system.service.AreaService;
import net.business.system.service.OrganExtService;
import net.business.system.service.OrganService;
import net.platform.utils.AjaxJson;
import net.platform.utils.Const;
import net.platform.utils.ServletUtils;
import net.platform.utils.StringUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/organ")
public class OrganController extends BaseController{
	private static Log log = LogFactory.getLog(OrganController.class);
	@Autowired
	private OrganService organService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private OrganExtService organExtService;
	
	/**
	 * 功能描述：跳转到列表页面
	 * @author zhangxin
	 * @created 2017-01-03 17:08:32
	 * @Emain
	 *  	<p>
	 *        修改历史：(修改人，修改时间，修改原因/内容)
	 *      </p>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "goList")
	public ModelAndView goList(HttpServletRequest request){
		return new ModelAndView("system/organ/organ_list");
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
		TsOrgan organ = new TsOrgan();
		organ.setParentId(id);
		
		request.setAttribute("organPage", organ);
		request.setAttribute("act", "add");
		request.setAttribute("parentName", parentName);
		return new ModelAndView("system/organ/organ_edit");
	}
	/**
	 * 功能描述：跳转到新增编辑界面
	 * @author zhangxin
	 * @created 2017-01-03 17:11:23
	  * @Emain
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
		TsOrgan organ = new TsOrgan();
		organ = organService.get(id);
		TsOrganExt organExt = organExtService.get(id);
		request.setAttribute("orgExtPage", organExt);
		request.setAttribute("organPage", organ);
		request.setAttribute("act", "edit");
		request.setAttribute("parentName", parentName);
		return new ModelAndView("system/organ/organ_edit");
	}
	/**
	 * 功能描述：获取机构列表
	 * @author zhangxin
	 * @created 2017-01-10 15:52:23
	 * @Email
	 * 		<p>
	 *        修改历史：(修改人，修改时间，修改原因/内容)
	 *      </p>
	 *      
	 * @param tsOrgan
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "getList")
	public void getList(TsOrgan tsOrgan,HttpServletRequest request,
			HttpServletResponse response){
		String json = organService.getOrganTree(tsOrgan);
		ServletUtils.outPrintJson(response, json);
	}
	/**
	 * 功能描述：保存机构修改
	 * @author zhangxin
	 * @created 2017-01-10 16:47:12
	 * @Email
	 * 		<p>
	 *        修改历史：(修改人，修改时间，修改原因/内容)
	 *      </p>
	 *      
	 * @param tsOrgan
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "doSave")
	public void doSave(TsOrgan tsOrgan, HttpServletRequest request,	
			HttpServletResponse response){
		String act = request.getParameter("act");
		AjaxJson json = new AjaxJson();
		try{
			TsArea area = areaService.get(tsOrgan.getArea().getId());
			tsOrgan.setArea(area);
			if(act.equals("add")){
				tsOrgan.getOrganExt().setId(tsOrgan.getId());
				TsOrganExt organExt = tsOrgan.getOrganExt();
				tsOrgan.setOrganExt(null);
				organService.save(tsOrgan);
				organExtService.save(organExt);
				
			}else{
				if(tsOrgan.getOrganExt().getId() != null && tsOrgan.getOrganExt().getId().equals("")){
					tsOrgan.getOrganExt().setId(tsOrgan.getId());
					organExtService.save(tsOrgan.getOrganExt());
				}
				organService.saveOrUpdate(tsOrgan);
			}
			
			System.out.println("area_name:"+tsOrgan.getArea().getAreaName());
			json.setMsg("保存成功");
			json.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			json.setMsg("保存失败"+e.getMessage()+"<br>"+"请截图联系管理员。");
			json.setSuccess(false);
		}
		ServletUtils.outPrintJson(response, JSON.toJSONString(json));
	}
	/**
	 * 功能描述：验证机构编号
	 * @author zhangxin
	 * @created 2017-01-10 16:46:23
	 * @Email
	 * 		<p>
	 *        修改历史：(修改人，修改时间，修改原因/内容)
	 *      </p>
	 *      
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "checkId")
	public void checkId(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		AjaxJson json = new AjaxJson();
		try {
			TsOrgan organ = organService.get(id);
			if(organ != null){
				json.setMsg("该机构编号已存在，不可使用");
				json.setSuccess(false);
			}else{
				json.setMsg("该机构编号不存在，可以使用");
				json.setSuccess(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("验证机构编号时出现错误");
			json.setSuccess(false);
		}
		ServletUtils.outPrintJson(response, JSON.toJSONString(json));
	}
	/**
	 * 功能描述：获取上传图片
	 * @author zhangxin
	 * @created 2017-01-12 15:09:23
	 * @Email
	 * 		<p>
	 *        修改历史：(修改人，修改时间，修改原因/内容)
	 *      </p>
	 * 
	 * @param file
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "getFile")
    public void getFile(@RequestParam("file") MultipartFile file,
    		HttpServletRequest request,HttpServletResponse response){
    	String type = request.getParameter("type");
		//文件保存地址
    	String path = request.getSession().getServletContext().getRealPath("/");
        String url = "";
        if(type.equals("logo")){
        	url = Const.ORGAN_LOGO;
        }else if(type.equals("map")){
        	url = Const.ORGAN_MAP;
        }else{
        	url = Const.ORGAN_IMAGES;
        }
    	path = path + url;
        //获取文件类型
    	String fileName = file.getOriginalFilename();
        String imgType = fileName.substring(fileName.indexOf("."),fileName.length());
        
        String time = new Date().getTime() + imgType;
        File targetFile  = new File(path,time);
        if(!targetFile.exists()){
        	targetFile.mkdirs();
        }
        
        try{
        	//保存后返回新的文件名称
        	file.transferTo(targetFile);
        	AjaxJson json = new AjaxJson();
        	json.setMsg(url+time);
        	ServletUtils.outPrintJson(response, JSON.toJSONString(json));
        }catch(Exception e){
        	e.printStackTrace();
        }
        
    }
	/**
	 * 功能描述：获取机构树
	 * @author zhangxin
	 * @created 2017-01-18 14:25:12
	 * @Email
	 * 		<p>
	 *        修改历史：(修改人，修改时间，修改原因/内容)
	 *      </p>
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "getOrganTree")
	public void getOrganTree(HttpServletRequest request,HttpServletResponse response){
		String json = organService.getOrganZtree();
		ServletUtils.outPrintJson(response, json);
	}
	
	@RequestMapping(params = "doDel")
	public void doDel(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("delCode");
		String json = organService.delOrgan(id);
		ServletUtils.outPrintJson(response, json);
	}
	
}
