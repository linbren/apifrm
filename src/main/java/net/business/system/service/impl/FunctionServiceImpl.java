package net.business.system.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import net.business.system.entity.TsFunction;
import net.business.system.entity.TsUser;
import net.business.system.service.FunctionService;
import net.platform.dao.impl.BaseDao;
import net.platform.utils.AjaxJson;
import net.platform.utils.page.ButtonUtil;
import net.platform.utils.page.ComboTree;
import net.platform.utils.page.PageUtil;
import net.platform.utils.page.Ztree;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

@Service("functionService")
@Transactional
public class FunctionServiceImpl extends BaseDao<TsFunction, String> implements
		FunctionService {

	@Override
	public List<TsFunction> getFunctionByUserId(String userId) {
		String sql = "select DISTINCT A.* from  TS_FUNCTION A,TS_ROLE_FUNC B ,TS_ROLE C,TS_USER_ROLE D ,TS_USER E"+
				" WHERE A.ID=B.FUNC_ID AND B.ROLE_ID=C.ID AND C.ID=D.ROLE_ID AND D.USER_ID=E.ID"+
				" AND A.APP_ID='1000' AND A.FUNC_TYPE='1' AND A.IS_VALID='1'"+
				" AND E.ID=? ORDER BY A.SORT_CODE";
		List<TsFunction> functions = null;
		try{
			functions = getListBySQL(sql, new Object[]{userId});
		}catch(Exception e){
			e.printStackTrace();
		}
		return functions;
	}

	@Override
	public List<TsFunction> getButtonByUserId(String userId,String funcUrl) {
		String sql = "select DISTINCT A.* from  TS_FUNCTION A,TS_ROLE_FUNC B ,TS_ROLE C,TS_USER_ROLE D ,TS_USER E"+
				" WHERE A.ID=B.FUNC_ID AND B.ROLE_ID=C.ID AND C.ID=D.ROLE_ID AND D.USER_ID=E.ID"+
				" AND A.FUNC_TYPE='2' AND A.IS_VALID='1'"+
				" AND E.ID=? AND PARENT_CODE = (SELECT FUNC_CODE FROM TS_FUNCTION WHERE FUNC_URL = ?)  ORDER BY A.SORT_CODE";
		List<TsFunction> functions = getListBySQL(sql, new Object[]{userId,funcUrl});
		
		return functions;
	}

	@Override
	public String getButtons(HttpServletRequest request) {
		List<ButtonUtil> list = new ArrayList<ButtonUtil>();
		String menuUrl = request.getParameter("url");
		
		String path = "http://" + request.getServerName() + ":"
                + request.getServerPort() + request.getContextPath();
        if (request.getServerPort() == 80) {
            path = "http://" + request.getServerName();
        }
		if (menuUrl.lastIndexOf("&_=") > 0) {
            menuUrl = menuUrl.substring(path.length() + 1, menuUrl
                    .lastIndexOf("&_="));// 去掉项目路径和时间戳
        } else {
            menuUrl = menuUrl.substring(path.length() + 1, menuUrl.length());// 去掉项目路径和时间戳
        }
		TsUser user = (TsUser) request.getSession().getAttribute("user");
		List<TsFunction> functions = this.getButtonByUserId(user.getId(), menuUrl);
        StringBuffer toolsBuffer = new StringBuffer();
        for (TsFunction m : functions) {
            ButtonUtil buttonUtil = new ButtonUtil();
            buttonUtil.setText(m.getFuncName());
            buttonUtil.setIconCls(m.getFuncIcon());
            buttonUtil.setHandler(m.getFuncMethod());
            list.add(buttonUtil);
        }
		
		return JSON.toJSONString(list);
	}

	@Override
	public List<TsFunction> getFunctionByRoleId(String roleId) {
		String sql = "select * from TS_FUNCTION "
				+ " where ID in(select FUNC_ID from TS_ROLE_FUNC"
				+ " where ROLE_ID = ?)";
		return getListBySQL(sql, new Object[]{roleId});
	}

	@Override
	public String queryForzTree(String roleId,String appId) {
		String sql = "select (case when B.ROLE_ID = '"+roleId+"' then 't' else 'f' end) as REMARK,"
				+ "FUNC_CODE,FUNC_NAME,PARENT_CODE"
				+ " from TS_FUNCTION a LEFT JOIN TS_ROLE_FUNC B ON A.ID=B.FUNC_ID AND B.ROLE_ID='"+roleId+"'"
				+ "where A.APP_ID = '"+appId+"'";
		
		/*String hql = "select (case when B.roleId = ? then 't' else 'f' end) as remark,A.funcCode,A.funcName,A.parentCode from TsFunction A"
				+ " left join TsRoleFunc B where A.id = B.funcId and b.roldId = ?";*/
		List<Object[]> funcs = findListBySql(sql,0,0);
		List<Ztree> tree = new ArrayList<Ztree>();
		for(Object[] func:funcs){
			Ztree t = new Ztree();
			t.setId(func[1].toString());
			t.setpId(func[3].toString());
			t.setName(func[2].toString());
			if(func[0].toString().equals("t")){
				t.setChecked(true);
			}
			tree.add(t);
		}
		
		String json = JSON.toJSONString(tree);
		return json;
	}

	@Override
	public List<TsFunction> getFunctionByCode(String roleFuncs) {
		if(roleFuncs.indexOf(",") > -1)
			roleFuncs = roleFuncs.substring(0,roleFuncs.length()-1);
		roleFuncs = "'" + roleFuncs.replace(",", "','") + "'";
		
		String sql = "select * from TS_FUNCTION where FUNC_CODE in("+roleFuncs+")";
		return getListBySQL(sql);
	}

	@Override
	public List<ComboTree> getComboTreeByParentId(String parentId,String cmdStr) {
		String sql = "select * from TS_FUNCTION where PARENT_CODE = ? "+cmdStr;
		List<ComboTree> tree = new ArrayList<ComboTree>();
		try{
		List<TsFunction> funcs = getListBySQL(sql, parentId);
			if(funcs != null && funcs.size() != 0){
				for(TsFunction func:funcs){
					ComboTree t = new ComboTree();
					t.setId(func.getFuncCode());
					t.setParentId(func.getParentCode());
					t.setText(func.getFuncName());
					t.setChecked(true);
					List<ComboTree> children = this.getComboTreeByParentId(func.getFuncCode(),cmdStr);
					if(children != null){
						t.setChildren(children);
					}
					else{
						t.setChildren(new ArrayList<ComboTree>());
						t.setState("open");
					}
					tree.add(t);
				}
			}else{
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return tree;
	}

	@Override
	public String getFunctionTree(TsFunction tsFunc) {
		String sQrySql = "select * from TS_FUNCTION where 1=1 ";
		String json = "";
		try{
			if(tsFunc.getAppId() != null && !tsFunc.getAppId().equals("")){
				sQrySql += " and APP_ID = '"+tsFunc.getAppId()+"'";
			}
			if(tsFunc.getFuncName() != null && !tsFunc.getFuncName().equals("")){
				/*sQrySql = "with cte_child(ID,PARENT_CODE,FUNC_CODE,FUNC_NAME,FUNC_URL,APP_ID,FUNC_TYPE) as ("
						+ " select ID,PARENT_CODE,FUNC_CODE,FUNC_NAME,FUNC_URL,APP_ID,FUNC_TYPE"
						+ " from TS_FUNCTION where FUNC_NAME like '%"+tsFunc.getFuncName()+"%' "
						+ sqlApp +" union all"
						+ " select a.ID,a.PARENT_CODE,a.FUNC_CODE,a.FUNC_NAME,a.FUNC_URL,a.APP_ID,a.FUNC_TYPE"
						+ " from TS_FUNCTION a inner join cte_child b"
						+ " on ( a.PARENT_CODE=b.FUNC_CODE))"
						+ " select distinct * from cte_child";*/
				sQrySql += " and FUNC_NAME like '%"+tsFunc.getFuncName()+"%' ";
			}else{
				if(tsFunc.getParentCode() != null){
					sQrySql += " and PARENT_CODE = '"+tsFunc.getParentCode()+"'";
				}else{
					sQrySql += " and PARENT_CODE = ''";
				}
			}
			List<ComboTree> tree = new ArrayList<ComboTree>();
			List<TsFunction> funcs = getFunctionByCmd(sQrySql);
			if(funcs != null && funcs.size() != 0){
				for(TsFunction func:funcs){
					ComboTree t = new ComboTree();
					t.setId(func.getFuncCode());
					t.setParentId(func.getParentCode());
					t.setText(func.getFuncName());
					t.setChecked(true);
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("func", func);
					t.setAttributes(map);
					if(tsFunc.getFuncName() != null && !tsFunc.getFuncName().equals("")){
						t.setState("open");
					}else{
						if(func.getFuncType().equals("2")){
							t.setState("open");
						}
					}
					tree.add(t);
				}
			}
			
			json = JSON.toJSONString(tree);
		}catch(Exception e){
			e.printStackTrace();
		}
		return json;
	}

	@Override
	public AjaxJson delFunction(String funcCode) {
		funcCode = "'" + funcCode.replace(",", "','") + "'";
		String sql = "select A.* from TS_FUNCTION A,TS_ROLE_FUNC B"
				+ " where A.ID = B.FUNC_ID and A.FUNC_CODE in("+funcCode+")";
		AjaxJson json = new AjaxJson();
		String msg = "删除成功";
		boolean success = true;
		try{
			List<TsFunction> funcs = getListBySQL(sql);
			if(funcs != null && funcs.size() > 0){
				msg = "此功能还有角色在使用不能删除";
				success = false;
			}else{
				String sql2 = "select * from TS_FUNCTION WHERE FUNC_CODE in("+funcCode+")";
				funcs = getListBySQL(sql2);
				deleteAll(funcs);
				
			}
		}catch(Exception e){
			e.printStackTrace();
			msg = "删除时出现错误"+e.getMessage()+"<br>"+"请截图联系管理员。";
			success = false;
		}
		json.setMsg(msg);
		json.setSuccess(success);
		return json;
	}

	@Override
	public List<TsFunction> getFunctionByCmd(String cmdStr) {
		String sql = cmdStr;
		return getListBySQL(sql);
	}

	

}
