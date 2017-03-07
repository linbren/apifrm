package net.business.system.servlet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.business.system.entity.TsFunction;
import net.business.system.entity.TsRole;
import net.business.system.entity.TsUser;
import net.business.system.service.FunctionService;
import net.business.system.service.RoleService;
import net.business.system.service.UserService;
import net.platform.utils.AjaxJson;
import net.platform.utils.Const;
import net.platform.utils.ServletUtils;
import net.platform.utils.StringUtils;
import net.platform.utils.page.PageUtil;

import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;


@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
	
	@Autowired
	private RoleService roleService;
	@Autowired
	private FunctionService functionService;
	@Autowired
	private UserService userService;
	
	/**
	 * 功能描述：跳转到角色列表页面
	 * 
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "goList")
	public ModelAndView goList(HttpServletRequest request){
		return new ModelAndView("system/role/role_list");
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
		return new ModelAndView("system/role/role_edit");
	}
	/**
	 * 功能描述：跳转到修改页面
	 * 
	 * @param tsRole
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "goEdit")
	public ModelAndView goEdit(TsRole tsRole,HttpServletRequest request){
		if(StringUtils.isNotBlank(tsRole.getId())){
			TsRole role = roleService.get(TsRole.class,tsRole.getId());
			request.setAttribute("rolePage", role);
		}
		return new ModelAndView("system/role/role_edit");
	}
	
	/**
	 * 功能描述：获取角色列表
	 * 
	 * @param pageUtil
	 * @param tsRole
	 * @param reuqest
	 * @param response
	 */
	@RequestMapping(params = "getList")
	public void getList(PageUtil pageUtil,TsRole tsRole,
			HttpServletRequest reuqest,HttpServletResponse response){
		String json = roleService.getRoleByPage(pageUtil, tsRole);
		ServletUtils.outPrintJson(response, json);
	}
	
	/**
	 * 功能描述：跳转到功能树界面
	 * @param request
	 * @return
	 */
    @RequestMapping(params = "goRoleSet")
    public ModelAndView goRoleSet(HttpServletRequest request) {
        String roleId = request.getParameter("roleId");
        request.setAttribute("roleId", roleId);
        return new ModelAndView("system/role/role_set");
    }
    /**
     * 功能描述：跳转到权限赋给用户界面
     * @param request
     * @return
     */
    @RequestMapping(params = "goRoleSetUser")
    public ModelAndView goRoleSetUser(HttpServletRequest request){
    	String roleId = request.getParameter("roleId");
    	request.setAttribute("roleId", roleId);
    	return new ModelAndView("system/role/role_set_user");
    }
    /**
     * 功能描述：保存角色
     * 
     * @param tsRole
     * @param reuqest
     * @param response
     */
	@RequestMapping(params = "doSave")
	public void doSave(TsRole tsRole,HttpServletRequest reuqest,
			HttpServletResponse response){
		AjaxJson json = new AjaxJson();
        String msg = "保存成功";
        try{
        	if(StringUtils.isNotBlank(tsRole.getId())){
        		//编辑
        		TsRole role = roleService.get(tsRole.getId());
        		tsRole.setFunctions(role.getFunctions());
        		roleService.saveOrUpdate(tsRole);
        	}else{
        		//新增
//        		tsRole.setStatus(Const.ROLE_START);
        		roleService.save(tsRole);
        	}
        }catch(Exception e){
        	e.printStackTrace();
        	msg = "保存失败"+e.getMessage()+"<br>"+"请截图联系管理员。";
        }
        json.setMsg(msg);
        ServletUtils.outPrintObjectToJson(response, json);
	}
	
	/**
	 * 功能描述：删除角色
	 * 
	 * @param tsRole
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "doDel")
	public void doDel(HttpServletRequest request,HttpServletResponse response){
		String roleId = request.getParameter("id");
		System.out.println("roleId:"+roleId);
		String json = roleService.delRole(roleId);
		
		ServletUtils.outPrintJson(response, json);
	}
	
	/**
	 * 功能描述：获取功能树
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "getFuncTree")
	public void getFuncTree(HttpServletRequest request,HttpServletResponse response){
		try{
			String roleId = request.getParameter("roleId");
			String appId = request.getParameter("appId");
			
			ServletUtils.outPrintJson(response, functionService.queryForzTree(roleId,appId));
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 功能描述：修改角色权限
	 * 
	 * @param tsFunc
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "updateAuthority")
	public void updateAuthority(TsFunction tsFunc,HttpServletRequest request
			,HttpServletResponse response){
		String roleId = request.getParameter("roleId");
		String roleFuncs = request.getParameter("roleFuncs");
		AjaxJson json = new AjaxJson();
        String msg = "角色权限修改成功";
        boolean success = true;
		try{
			TsRole role = roleService.getRoleById(roleId);
			List<TsFunction> functions = functionService.getFunctionByCode(roleFuncs);
			Set<TsFunction> funcs = new HashSet<TsFunction>();
			funcs.addAll(functions);
			role.setFunctions(funcs);
			
			roleService.saveOrUpdate(role);
		}catch(Exception e){
			e.printStackTrace();
			success = false;
			msg = "角色权限修改失败"+e.getMessage()+"<br>"+"请截图联系管理员。";
		}
		
		json.setSuccess(success);
		json.setMsg(msg);
		ServletUtils.outPrintJson(response, JSON.toJSONString(json));
	}
	/**
	 * 功能描述：把角色赋给用户
	 * @author zhangxin
	 * @created 2017-01-19 9:02:44
	 * @Email
	 * 		<p>
	 * 
	 * 		</p>
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "roleSetUser")
	public void roleSetUser(HttpServletRequest request,HttpServletResponse response){
		String roleId = request.getParameter("roleId");
		String userIds = request.getParameter("userIds");
		String checkeds = request.getParameter("checkeds");
		
		AjaxJson json = new AjaxJson();
		
		try{
			TsRole role = roleService.get(roleId);
			
			if(!userIds.equals("")){
				String[] userId = userIds.split(",");
				String[] checked = checkeds.split(",");
 				for(int i=0;i<userId.length;i++){
					TsUser user = userService.get(userId[i]);
					if(checked[i].equals("true")){
						user.getRoles().add(role);
					}else{
						user.getRoles().remove(role);
					}
					userService.saveOrUpdate(user);
				}
				
			}
			
			json.setSuccess(true);
			json.setMsg("保存成功");
			
		}catch(Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("角色赋给用户失败"+e.getMessage()+"<br>"+"请截图联系管理员。");
		}
		ServletUtils.outPrintJson(response, JSON.toJSONString(json));
		
	}
	
}
