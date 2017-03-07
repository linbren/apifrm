package net.business.system.servlet;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.business.system.entity.TsApp;
import net.business.system.entity.TsOrgan;
import net.business.system.entity.TsRole;
import net.business.system.entity.TsUser;
import net.business.system.service.AppService;
import net.business.system.service.OrganService;
import net.business.system.service.RoleService;
import net.business.system.service.UserService;
import net.platform.utils.AjaxJson;
import net.platform.utils.Const;
import net.platform.utils.ServletUtils;
import net.platform.utils.StringUtils;
import net.platform.utils.page.ComboTree;
import net.platform.utils.page.PageUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;


@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private OrganService organService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private AppService appService;
	
	/**
	 * 功能描述：跳转到用户列表页面
	 * @author zhangxin
	 * @created 2016-12-16 14:44:12
	 * @Email
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "goList")
	public ModelAndView goList(HttpServletRequest request){
		return new ModelAndView("system/user/user_list");
	}
	/**
	 * 功能描述：跳转到添加页面
	 * @author zhangxin
	 * @created 2016-12-16 15:45:43
	 * @Email
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "goSave")
	public ModelAndView goSave(HttpServletRequest request){
		return new ModelAndView("system/user/user_save");
	}
	/**
	 * 功能描述：跳转到goAdd页面
	 * @author zhangxin
	 * @created 2016-12-16 15:45:43
	 * @Email
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(HttpServletRequest request){
		return new ModelAndView("system/user/user_edit");
	}	
	/**
	 * 功能描述：跳转到编辑页面
	 * @author zhangxin
	 * @created 2016-12-16 15:45:43
	 * @Email
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "goEdit")
	public ModelAndView goEdit(TsUser tsUser,HttpServletRequest request){
		if(StringUtils.isNotBlank(tsUser.getId())){
			try{
				TsUser user = new TsUser();			
				user = userService.get(tsUser.getId());
				request.setAttribute("userPage", user);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return new ModelAndView("system/user/user_edit");
	}
	
	/**
     * 
     * 功能描述：列表请求数据
     * 
     * @author 
     * @Email 
     *        <p>
     *        
     *        </p>
     * 
     * @param dictionary
     * @param pageUtil
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "getList")
    public void getList(PageUtil pageUtil, TsUser tsUser, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	String json = userService.getUserByPage(pageUtil, tsUser);
    	ServletUtils.outPrintJson(response, json);
    }
    /**
     * 
     * 功能描述：添加或编辑数据
     * 
     * @author zhangxin
     * @Email 
     *        <p>
     *        
     *        </p>
     * 
     * @param tsUser
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "doSave")
    public void doSave(TsUser tsUser,HttpServletRequest request,
    		HttpServletResponse response){
    	AjaxJson json = new AjaxJson();
    	try{
    		if(StringUtils.isNotBlank(tsUser.getId())){
    			List<TsRole> list = roleService.getRoleByUserId(tsUser.getId());
    			Set<TsRole> roles = new HashSet<TsRole>();
    			roles.addAll(list);
    			tsUser.setRoles(roles);
    		}
    		TsOrgan organ = organService.get(tsUser.getOrgan().getId());
    		tsUser.setOrgan(organ);
    		json = userService.saveOrEdit(tsUser, request);
    	}catch(Exception e){
    		e.printStackTrace();
            
    	}
    	ServletUtils.outPrintObjectToJson(response, JSON.toJSON(json));
    }
    
    
    @RequestMapping(params = "doDel")
    public void doDel(TsUser tsUser,HttpServletRequest request,
    		HttpServletResponse response){
    	AjaxJson json = new AjaxJson();
    	String msg = "删除成功";
    	try{
    		if (StringUtils.isNotBlank(tsUser.getId())) {
    			userService.deleteById(tsUser.getId());
    		}
    		json.setSuccess(true);
    	}catch(Exception e){
    		e.printStackTrace();
    		msg = "删除失败"+e.getMessage()+"请联系管理员";
    		json.setSuccess(false);
    	}
    	json.setMsg(msg);
    	ServletUtils.outPrintJson(response, JSON.toJSONString(json));
    	
    }
    
    /**
     * 功能描述：检查登录账号是否重复
     * 
     * @author zhangxin
     * @Email 
     *        <p>
     *        
     *        </p>
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "checkCode")
    public void checkCode(HttpServletRequest request,HttpServletResponse response){
    	String userCode = request.getParameter("userCode");
    	AjaxJson json = new AjaxJson();
    	try{
	    	TsUser user = new TsUser();
	    	user.setUserCode(userCode);
	    	TsUser tsUser = userService.getUserByCode(user);
	    	if(tsUser == null){
	    		json.setSuccess(true);
	    		json.setMsg(userCode+"该账号不存在，可以使用");
	    	}else{
	    		json.setSuccess(false);
	    		json.setMsg(userCode+"该账号已存在，不可以使用");
	    	}
    	}catch(Exception e){
    		e.printStackTrace();
    		json.setMsg("验证账号是发生错误");
    		json.setSuccess(false);
    	}
    	ServletUtils.outPrintJson(response, JSON.toJSONString(json));
    }
    
    /**
     * 功能描述：获取机构列表
     * @author zhangxin
     * @created 2016-12-21 15:24:41
     * @Email
     * 		<p>
     *        
     *      </p>
     *        
     * @param request
     * @param response
     */
    @RequestMapping(params = "getOrganTree")
    public void getOrganTree(HttpServletRequest request,HttpServletResponse response){
    	try{
	    	List<ComboTree> tree = organService.getOrganComboTree("0","");
	    	ServletUtils.outPrintJson(response, JSON.toJSONString(tree));
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    /**
     * 功能描述：获取用户头像
     * @author zhangxin
     * @created 2016-12-23 9:47:23
     * @Email
     * 		<p>
     *        
     *      </p>
     * @param file
     * @param request
     * @param response
     */
    @RequestMapping(params = "getFile")
    public void getFile(@RequestParam("file") MultipartFile file,
    		HttpServletRequest request,HttpServletResponse response){
    	//文件保存地址
    	String path = request.getSession().getServletContext().getRealPath("/");
        path = path + Const.USER_PHOTO;
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
        	json.setMsg(Const.USER_PHOTO+time);
        	ServletUtils.outPrintJson(response, JSON.toJSONString(json));
        }catch(Exception e){
        	e.printStackTrace();
        }
        
    }
	
    /**
     * 功能描述：跳转到指定角色页面
     * @author zhangxin
     * @created 2016-12-23 11:09:32
     * @Email
     * 		<p>
     *        
     *      </p>
     * @param request
     * @param response
     */
    @RequestMapping(params = "goRole")
    public ModelAndView goRole(HttpServletRequest request, HttpServletResponse response){
    	String userId = request.getParameter("userId");
    	request.setAttribute("userId", userId);
    	return new ModelAndView("system/user/user_role");
    }
    
    /**
     * 功能描述：获取角色列表
     * @author zhangxin
     * @created 2016-12-23 14:58:34
     * @Email
     * 		<p>
     *        
     *      </p>
     * @param request
     * @param response
     */
    
    @RequestMapping(params = "getRole")
    public void getRole(HttpServletRequest request, HttpServletResponse response){
    	String userId = request.getParameter("userId");
    	String json = roleService.getRoleTree(userId);
    	ServletUtils.outPrintJson(response, json);
    }
    
    /**
     * 功能描述：保存指定角色
     * @author zhangxin
     * @created 2016-12-23 14:58:34
     * @Email
     * 		<p>
     *        
     *      </p>
     * @param request
     * @param response
     */
    @RequestMapping(params = "doSaveRole")
    public void doSaveRole(HttpServletRequest request,HttpServletResponse response){
    	String userId = request.getParameter("userId");
    	String roleIds = request.getParameter("roleIds");
    	
    	AjaxJson json = new AjaxJson();
    	String msg = "角色设置成功";
    	boolean success = true;
    	
    	try{
    		TsUser user = userService.get(userId);
    		List<TsRole> roles = roleService.getRoleByIds(roleIds);
    		Set<TsRole> role = new HashSet<TsRole>();
    		if(roles != null && roles.size() != 0){
    			role.addAll(roles);
    		}
    		
    		user.setRoles(role);
    		userService.saveOrUpdate(user);
    	}catch(Exception e){
    		e.printStackTrace();
    		success = false;
    		msg = "角色设置失败"+e.getMessage()+"<br>"+"请截图联系管理员。";
    	}
    	
    	json.setMsg(msg);
    	json.setSuccess(success);
    	
    	ServletUtils.outPrintJson(response, JSON.toJSONString(json));
    }
    
    /**
     * 功能描述：重置密码
     * @author zhangxin
     * @Email
     * 		<p>
     *        
     *      </p>
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "resetPass")
    public void resetPass(HttpServletRequest request, HttpServletResponse response){
    	String userIds = request.getParameter("userIds");
    	AjaxJson json = userService.resetPass(userIds);
    	ServletUtils.outPrintJson(response, JSON.toJSONString(json));
    }
    /**
     * 功能描述：根据用户树
     * @author zhangxin
     * @created 2017-01-18 9:51:23
     * @Email
     * 		<p>
     *        
     *      </p>
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "getUserTree")
    public void getUserTree(HttpServletRequest request,HttpServletResponse response){
    	String appId = request.getParameter("appId");
    	String roleId = request.getParameter("roleId");
    	String organId = request.getParameter("organId");
    	log.info("roleId:"+roleId+"   appId:"+appId+"   organId:"+organId);
//    	List<TsOrgan> organs = organService.getOrganByCmd("order by PARENT_ID");
    	if(organId.equals("") || appId.equals("")){
    		ServletUtils.outPrintJson(response, "");
    	}else{
    		String json = userService.getUserTree(appId, roleId,organId);
    		ServletUtils.outPrintJson(response, json);
    	}
    	
    	
    }
    
}
