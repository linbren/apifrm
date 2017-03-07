package net.business.system.service;

import java.util.List;

import net.business.system.entity.TsRole;
import net.business.system.entity.TsUser;
import net.platform.dao.IBaseDao;
import net.platform.utils.page.PageUtil;

public interface RoleService extends IBaseDao<TsRole, String>{
	
	public TsRole getRoleById(String id);
	
	public List<TsRole> getRoleByUserId(String id);
	
	public String getRoleByPage(PageUtil pageUtil,TsRole role);
	
	public void deleteAll(TsRole tsRole);
	
	public String getRoleTree(String userId);
	
	public List<TsRole> getRoleByIds(String roleIds);
	
	public String roleSetUsers(String roleId,List<TsUser> users);
	
	public String delRole(String roleId);
}
