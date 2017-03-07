package net.business.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.business.system.entity.TsOrgan;
import net.business.system.service.OrganService;
import net.platform.dao.impl.BaseDao;
import net.platform.utils.AjaxJson;
import net.platform.utils.page.ComboTree;
import net.platform.utils.page.Ztree;

import org.hibernate.mapping.Array;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

@Service("organService")
@Transactional
public class OrganServiceImpl extends BaseDao<TsOrgan, String> implements OrganService {

	@Override
	public List<ComboTree> getOrganComboTree(String parentId,String cmdStr) {
		String sql = "select * from TS_ORGAN where PARENT_ID=? "+cmdStr;
		List<ComboTree> tree = new ArrayList<ComboTree>();
		try{
			List<TsOrgan> organs = getListBySQL(sql, parentId); 
			if(organs != null && organs.size() != 0){
				for(TsOrgan organ:organs){
					ComboTree t = new ComboTree();
					t.setId(organ.getId());
					t.setParentId(organ.getParentId());
					t.setText(organ.getOrgName());
					List<ComboTree> children = this.getOrganComboTree(organ.getId(),cmdStr);
					if(children != null){
						t.setChildren(children);
					}
					else{
						t.setChildren(null);
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
	public String getOrganTree(TsOrgan tsOrgan) {
		String sql = "select a.ID,a.ORG_NAME,a.PARENT_ID,a.LONGITUDE,a.LATITUDE,a.AREA_ID,a.SORT_CODE,b.AREA_NAME as REMARK from TS_ORGAN as a,TS_AREA as b where a.AREA_ID = b.ID ";
		String areaSql = "WITH NODES AS ( SELECT * FROM TS_AREA"
				+ " par WHERE par.ID='"+tsOrgan.getArea().getId()+"'"
				+ " UNION ALL SELECT child.* FROM TS_AREA AS child"
				+ " INNER JOIN NODES  AS RC ON child.PARENT_ID = RC.ID"
				+ " WHERE child.AREA_LEVEL<5)";
		String json = "";
		try{
			boolean b = true;
			if(tsOrgan.getArea().getId() != null && 
					!tsOrgan.getArea().getId().equals("") && !tsOrgan.getArea().getId().equals("0")){
				sql = areaSql + sql + " and a.AREA_ID in(SELECT id FROM NODES where AREA_LEVEL<5 ) ";
				b = false;
			}
			if(tsOrgan.getOrgName() != null && !tsOrgan.getOrgName().equals("")){
				sql += " and a.ORG_NAME like '%"+tsOrgan.getOrgName()+"%'";
				b = false;
			}
			
			if(b){
				if(tsOrgan.getParentId() != null && !tsOrgan.getParentId().equals("")){
					sql += " and a.PARENT_ID = '"+tsOrgan.getParentId()+"'";
				}else{
					sql += " and a.PARENT_ID = ''";
				}
			}
			
			List<TsOrgan> organs = getListBySQL(sql);
			List<ComboTree> tree = new ArrayList<ComboTree>();
			if(organs != null && organs.size() != 0){
				for(TsOrgan organ:organs){
					ComboTree t = new ComboTree();
					t.setId(organ.getId());
					t.setParentId(organ.getParentId());
					t.setText(organ.getOrgName());
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("organ",organ);
					t.setAttributes(map);
					if(b == false){
						t.setState("open");
					}
					tree.add(t);
				}
				
			}
			json = JSON.toJSONString(tree,
					SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty);
		}catch(Exception e){
			e.printStackTrace();
		}
		return json;
	}

	@Override
	public List<TsOrgan> getOrganByCmd(String cmd) {
		String sql = "select * from TS_ORGAN where 1=1 "+cmd;
		return getListBySQL(sql);
	}

	@Override
	public String getOrganZtree() {
		List<TsOrgan> organs = getOrganByCmd("order by PARENT_ID");
		List<Ztree> tree = new ArrayList<Ztree>();
		for(int i=0;i<organs.size();i++){
			Ztree t = new Ztree();
			t.setId(organs.get(i).getId());
			t.setpId(organs.get(i).getParentId());
			t.setName(organs.get(i).getOrgName());
			tree.add(t);
		}
		
		return JSON.toJSONString(tree);
	}

	@Override
	public String delOrgan(String id) {
		//获取此机构的所有下级
		String sql = "WITH ORGAN_TREE AS (SELECT * FROM TS_ORGAN A"
				+ " WHERE A.ID = ? UNION ALL  SELECT B.* FROM TS_ORGAN B"
				+ "	INNER JOIN ORGAN_TREE AS C ON B.PARENT_ID = C.ID)"
				+ " SELECT * FROM TS_ORGAN WHERE ID IN(SELECT ID FROM ORGAN_TREE) ";
		
		AjaxJson json = new AjaxJson();
		
		try{
			List<TsOrgan> organs = getListBySQL(sql, new Object[]{id});
			List<TsOrgan> orgList = new ArrayList<TsOrgan>();
			String ids = "";
			for(TsOrgan organ:organs){
				orgList.add(get(organ.getId()));
				ids += "'" + organ.getId() + "',"; 
			}
			ids = ids.substring(0,ids.length()-1);
			
			String organSql = "select * from TS_ORGAN where ID in("+ids+")";
			String userSql = "select * from TS_USER where ORG_ID in("+ids+")";
			
			List<Object[]> objs = findListBySql(userSql,0,0);
			if(objs == null || objs.size() == 0){
				deleteAll(orgList);
				json.setSuccess(true);
				json.setMsg("删除成功");
			}else{
				json.setSuccess(false);
				json.setMsg("此机构及其下属机构上还用用户，不能删除");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("删除失败"+e.getMessage()+"请截图与管理员联系");
		}
		
		return JSON.toJSONString(json);
	}

}
