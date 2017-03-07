package net.business.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.business.system.entity.TsRole;
import net.business.system.entity.TsUser;
import net.business.system.service.RoleService;
import net.platform.dao.impl.BaseDao;
import net.platform.utils.AjaxJson;
import net.platform.utils.Const;
import net.platform.utils.StringUtils;
import net.platform.utils.page.PageUtil;
import net.platform.utils.page.Ztree;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;


@Service("roleService")
@Transactional
public class RoleServiceImpl extends BaseDao<TsRole, String> implements RoleService{

	@Override
	public TsRole getRoleById(String id){
		return get(TsRole.class,id);
	}
	
	@Override
	public List<TsRole> getRoleByUserId(String id) {
		String sql = "select * from TS_ROLE where ID in("
				+ "select ROLE_ID from TS_USER_ROLE where USER_ID = ?)";
		List<TsRole> roles = getListBySQL(sql, id);
		return roles;
	}

	@Override
	public String getRoleByPage(PageUtil pageUtil, TsRole role) {
		String sQrySql = "select * from TS_ROLE where 1=1 ";
		String sCntSql = "select count(*) from TS_ROLE where 1=1 ";
		String json = "";
		try{
			if(role.getRoleName() != null && !role.getRoleName().equals("")){
				sQrySql += " and ROLE_NAME like '%"+role.getRoleName()+"%'";
				sCntSql += " and ROLE_NAME like '%"+role.getRoleName()+"%'";
			}
			if(role.getAppId() != null && !role.getAppId().equals("")){
				sQrySql += " and APP_ID = '"+role.getAppId()+"'";
				sCntSql += " and APP_ID = '"+role.getAppId()+"'";
			}
			json = JSON.toJSONString(findPageBySql(sQrySql,sCntSql,pageUtil.getPage(),pageUtil.getRows()));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return json;
	}

	@Override
	public void deleteAll(TsRole tsRole) {
		// TODO Auto-generated method stub
		super.deleteAll(TsRole.class,tsRole.getId());
	}

	@Override
	public String getRoleTree(String userId) {
		List<Ztree> zTree = new ArrayList<Ztree>();
		String sql1 = "select ID,APP_NAME from TS_APP";
		
		
		String sql2 = "select (case when B.USER_ID = '"+userId+"' then 't' else 'f' end) as remark,"
				+ "A.ID,A.ROLE_NAME,A.APP_ID from TS_ROLE A left join TS_USER_ROLE B"
				+ " on A.ID = B.ROLE_ID and B.USER_ID = '"+userId+"' and A.STATUS = 1"
				+ " where A.STATUS = 1";
		try{
			List<Object[]> objs1 = findListBySql(sql1, 0, 0);
			for(Object[] obj:objs1){
				Ztree tree = new Ztree();
				tree.setId(obj[0].toString());
				tree.setName(obj[1].toString());
				tree.setNocheck(true);
				zTree.add(tree);
			}
			
			List<Object[]> objs2 = findListBySql(sql2,0,0);
			for(Object[] obj:objs2){
				Ztree tree = new Ztree();
				tree.setId(obj[1].toString());
				tree.setName(obj[2].toString());
				tree.setpId(obj[3].toString());
				if(obj[0].toString().equals("t")){
					tree.setChecked(true);
				}
				zTree.add(tree);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return JSON.toJSONString(zTree);
	}

	@Override
	public List<TsRole> getRoleByIds(String roleIds) {
		if(roleIds == null || roleIds.equals("")){
			return null;
		}
		roleIds = roleIds.substring(0,roleIds.length()-1);
		roleIds = "'" + roleIds.replace(",", "','") + "'";
		
		String sql = "select * from TS_ROLE where ID in("+roleIds+")";
		return getListBySQL(sql);
	}

	@Override
	public String roleSetUsers(String roleId, List<TsUser> users) {
		try{
			TsRole role = get(roleId);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public String delRole(String roleId) {
		roleId = "'" + roleId.replace(",", "','") + "'";
		
		String sql = "select * from TS_ROLE where id in("+roleId+")";
		
		AjaxJson json = new AjaxJson();
        String msg = "删除成功";
		try {
			List<TsRole> roles = getListBySQL(sql);
			List<TsRole> not_roles = new ArrayList<TsRole>();
			
			if(roles != null && roles.size() != 0){
				for(TsRole role:roles){
					String userSql = "select * from TS_USER_ROLE where ROLE_ID = '"+role.getId()+"' ";
					List<Object[]> users = findListBySql(userSql,0,0);
					if(roleId.equals(Const.ADMIN_ROLE)){
						msg = "不允许删除管理员角色";
						roles.remove(role);
						
						json.setSuccess(false);
					}else if(users.size() > 0){
						
						not_roles.add(role);
						json.setSuccess(false);
					}
				}
				if(not_roles.size() != 0){
					for(int i=0;i<not_roles.size();i++){
						msg = not_roles.get(i).getRoleName() + "、";
						roles.remove(not_roles.get(i));
					}
					msg = msg.substring(0,msg.length()-1) + "角色下还有用户不能被删除";
				}
				if(roles.size() != 0){
					deleteAll(roles);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.setSuccess(false);
			msg = "删除失败"+e.getMessage()+"<br>"+"请截图联系管理员。";
		}
		json.setMsg(msg);
		
		return JSON.toJSONString(json);
	}
}
