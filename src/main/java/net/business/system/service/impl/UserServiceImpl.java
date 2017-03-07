package net.business.system.service.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.business.system.entity.TsOrgan;
import net.business.system.entity.TsUser;
import net.business.system.service.UserService;
import net.platform.dao.impl.BaseDao;
import net.platform.utils.AjaxJson;
import net.platform.utils.Const;
import net.platform.utils.StringUtils;
import net.platform.utils.page.PageUtil;
import net.platform.utils.page.Ztree;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;


@Service("userService")
@Transactional
public class UserServiceImpl extends BaseDao<TsUser, String> implements UserService {

	@Override
	public TsUser getUserByCode(TsUser user) {
		String sql = "select * from TS_USER where USER_CODE = ?";
		TsUser tsUser = new TsUser();
		try{
			List<TsUser> users = getListBySQL(sql, new Object[]{user.getUserCode()});
			if(users == null || users.size() == 0){
				return null;
			}
			tsUser = users.get(0);
		}catch(Exception e){
			e.printStackTrace();
		}
		return tsUser;
	}

	@Override
	public String getUserByPage(PageUtil pageUtil, TsUser tsUser)
			throws Exception {
		String sQrySql = "select * from TS_USER where 1=1";
		String sCntSql = "select count(*) from TS_USER where 1=1";
		String json = "";
		try{
			if(tsUser.getUserCode() != null && !tsUser.getUserCode().equals("")){
				sQrySql += " and (USER_CODE like '%"+tsUser.getUserCode()+"%' "
						+ " or USER_NAME like '%"+tsUser.getUserCode()+"%' "
						+ " or TELPHONE like '%"+tsUser.getUserCode()+"%')";
				sCntSql += " and (USER_CODE like '%"+tsUser.getUserCode()+"%' "
						+ " or USER_NAME like '%"+tsUser.getUserCode()+"%' "
						+ " or TELPHONE like '%"+tsUser.getUserCode()+"%')";
			}
			if(tsUser.getOrgan().getId() != null && !tsUser.getOrgan().getId() .equals("")){
				String orgId = tsUser.getOrgan().getId() ;
				orgId = "'" + orgId.replace(",", "','") + "'";
				sQrySql += " and ORG_ID IN("+orgId+")";
				sCntSql += " and ORG_ID IN("+orgId+")";
			}
			if(tsUser.getAppId() != null && !tsUser.getAppId().equals("")){
				sQrySql += " and APP_ID = '"+tsUser.getAppId()+"' ";
				sCntSql += " and APP_ID = '"+tsUser.getAppId()+"' ";
			}
			json = JSON.toJSONString(findPageBySql(sQrySql,sCntSql,pageUtil.getPage(),pageUtil.getRows()),
					SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty);
		}catch(Exception e){
			e.printStackTrace();
		}
		return json;
	}

	@Override
	public AjaxJson saveOrEdit(TsUser tsUser, HttpServletRequest request) {
		AjaxJson json = new AjaxJson();
        String msg = "保存成功";
        try{
	    	if(StringUtils.isNotBlank(tsUser.getId())){
	    		String time = request.getParameter("time");
	    		tsUser.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss").parse(time));
	    		saveOrUpdate(tsUser);
	    	}else{
//	    		tsUser.setAppId(Const.APP_ID);
//	    		tsUser.setStatus(Const.USER_START);
	    		tsUser.setSortCode(0);
	    		tsUser.setUserPass(Const.DEFAULT_PASSWORD);
	    		tsUser.setCreateTime(new Date());
	    		
	    		save(tsUser);
	    	}
	    	json.setSuccess(true);
        }catch(Exception e){
        	e.printStackTrace();
        	msg = "保存失败"+e.getMessage()+"<br>"+"请截图联系管理员。";
        	json.setSuccess(false);
        }
        json.setMsg(msg);
		return json;
	}

	@Override
	public AjaxJson resetPass(String userIds) {
		if(userIds.indexOf(",") > -1){
			userIds = userIds.substring(0,userIds.length()-1);
		}
		userIds = "'" + userIds.replace(",", "','") + "'";
		String sql = "from TsUser where id in("+userIds+")";
		AjaxJson json = new AjaxJson();
		try{
			List<TsUser> users = getListByHQL(sql);
			for(TsUser user:users){
				user.setUserPass(Const.DEFAULT_PASSWORD);
				saveOrUpdate(user);
			}
			json.setMsg("密码重置成功");
			json.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			json.setMsg("密码重置失败"+e.getMessage()+"<br>"+"请截图联系管理员。");
			json.setSuccess(false);
		}
		
		return json;
	}

	@Override
	public String getUserTree(String appId,String roleId,String organId) {
		organId = "'"+organId.replace(",", "','") + "'";
		String UserSql = "select (case when b.ROLE_ID = '"+roleId+"' then 't' else 'f' end) as REMARK,"
				+ "a.ID,ORG_ID,USER_NAME from TS_USER a"
				+ " left join TS_USER_ROLE b on a.ID = b.USER_ID and b.ROLE_ID = '"+roleId+"' "
				+ "where a.STATUS = 1 and a.APP_ID = '"+appId+"' and ORG_ID in("+organId+")";
		
		/*String OrganSql = "select (select count(*) from TS_USER"
				+ " where ID in( select USER_ID from TS_USER_ROLE"
				+ " where ROLE_ID = "+roleId+") and STATUS = 1 and r.ID = ORG_ID"
				+ " ) as a,ID,ORG_NAME,PARENT_ID from TS_ORGAN r";*/
		
//		String OrganSql = "select REMARK,ID,ORG_NAME,PARENT_ID from TS_ORGAN";
		
		List<Ztree> tree = new ArrayList<Ztree>();
		try{
			List<Object[]> users = findListBySql(UserSql,0,0);
//			List<Object[]> organs = findListBySql(OrganSql,0,0);
			/*for(int i = 0;i < organs.size();i++){
				Ztree t = new Ztree();
				t.setId(organs.get(i)[1].toString());
				t.setpId(organs.get(i)[3].toString());
				t.setName(organs.get(i)[2].toString());
				if(!organs.get(i)[0].toString().equals("0")){
					t.setChecked(true);
				}
				t.setNocheck(true);
				tree.add(t);
			}*/
			
			for(int i = 0;i < users.size();i++){
				Ztree t = new Ztree();
				t.setId(users.get(i)[1].toString());
				t.setpId(users.get(i)[2].toString());
				t.setName(users.get(i)[3].toString());
				if(users.get(i)[0].toString().equals("t")){
					t.setChecked(true);
				}
				tree.add(t);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return JSON.toJSONString(tree);
	}

	@Override
	public List<TsUser> getUserByCmd(String cmdStr) {
		String sql = "select * from TS_USER where 1=1 "+cmdStr;
		List<TsUser> users = getListBySQL(sql);
		return users;
	}

	@Override
	public Integer getUserCount(String cmdStr) {
		String sql = "select count(*) from TS_USER where STATUS = 1 " + cmdStr;
		List<Object[]> objs = findListBySql(sql,0,0);
		Object[] obj = new Object[1];
		obj[0] = objs.get(0);
		Integer userCount = Integer.parseInt(obj[0]+"");
		return userCount;
	}


}
