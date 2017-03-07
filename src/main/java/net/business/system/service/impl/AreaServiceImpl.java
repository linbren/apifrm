package net.business.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.business.system.entity.TsArea;
import net.business.system.service.AreaService;
import net.platform.dao.impl.BaseDao;
import net.platform.utils.AjaxJson;
import net.platform.utils.page.ComboTree;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

@Service("areaService")
@Transactional
public class AreaServiceImpl extends BaseDao<TsArea, String> implements AreaService{

	@Override
	public String getAreaTree(TsArea area) {
		String sql = "";
		int b = 1;
		if(area.getAreaName() != null && !area.getAreaName().equals("")){
			sql += " and AREA_NAME like '%"+area.getAreaName()+"%'";
			b=0;
		}
		if(area.getSortCode() != null){
			sql += " and SORT_CODE like '%"+area.getSortCode()+"%'";
			b = 0;
		}
		if(b == 1){
			if(area.getParentId() != null){
				sql = " and PARENT_ID = '"+area.getParentId()+"' ";
			}else{
				sql = " and PARENT_ID = ''";
			}
		}
		List<TsArea> areas = getAreaAll(sql);
		List<ComboTree> tree = new ArrayList<ComboTree>();
		if(areas != null && areas.size() != 0){
			for(TsArea tsArea:areas){
				ComboTree t = new ComboTree();
				t.setId(tsArea.getId());
				t.setParentId(tsArea.getParentId());
				t.setText(tsArea.getAreaName());
				t.setChecked(true);
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("area", tsArea);
				t.setAttributes(map);
				if(b == 0){
					t.setState("open");
				}else{
					if(tsArea.getIsLeaf().equals("1")){
						t.setChildren(new ArrayList<ComboTree>());
						t.setState("open");
					}
				}
				tree.add(t);
			}
		}
		return JSON.toJSONString(tree,
				SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty);
	}

	@Override
	public List<ComboTree> getComboTreeByParentId(String parentId, String cmdStr) {
		String sql = "select * from TS_AREA where PARENT_ID = ? "+cmdStr;
		List<ComboTree> tree = new ArrayList<ComboTree>();
		try{
		List<TsArea> areas = getListBySQL(sql, parentId);
			if(areas != null && areas.size() != 0){
				for(TsArea area:areas){
					ComboTree t = new ComboTree();
					t.setId(area.getId());
					t.setParentId(area.getParentId());
					t.setText(area.getAreaName());
					t.setChecked(true);
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("area", area);
					t.setAttributes(map);
					List<ComboTree> children = this.getComboTreeByParentId(area.getId(),cmdStr);
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
	public List<TsArea> getAreaAll(String cmdStr) {
		String sql = "select distinct * from TS_AREA where 1=1 " + cmdStr+" order by AREA_LEVEL,ID";
		return this.getListBySQL(sql);
	}

	@Override
	public String delArea(String id) {
		String sql = "WITH AREA_TREE AS (SELECT A.* FROM TS_AREA A"
				+ " WHERE A.ID = ? UNION ALL SELECT B.* FROM TS_AREA B"
				+ " INNER JOIN AREA_TREE AS C ON B.PARENT_ID = C.ID)"
				+ " SELECT * FROM TS_AREA WHERE ID IN(SELECT ID FROM AREA_TREE)";
		
		AjaxJson json = new AjaxJson();
		
		try{
			List<TsArea> areas = getListBySQL(sql, new Object[]{id});
			String ids = "";
			for(TsArea area:areas){
				ids += "'" + area.getId() + "',";
			}
			ids = ids.substring(0,ids.length()-1);
			
			String organSql = "select * from TS_ORGAN where AREA_ID in("+ids+")";
			List<Object[]> objs = findListBySql(organSql, 0, 0);
			if(objs == null || objs.size() == 0){
				deleteAll(areas);
				json.setSuccess(true);
				json.setMsg("删除成功");
			}else{
				json.setSuccess(false);
				json.setMsg("此地区及其下属地区上还有机构存在，不能删除");
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("删除失败"+e.getMessage()+"请截图与管理员联系");
		}
		
		return JSON.toJSONString(json);
	}

	public String getAreaList(){
		String sql = "select * from TS_AREA where AREA_LEVEL < 5 order by PARENT_ID desc";
		
		List<TsArea> areas = getListBySQL(sql);
		ComboTree tree = new ComboTree();
		if(areas != null && areas.size() != 0){
			JSONObject json = new JSONObject();
			List<ComboTree> tree2 = new ArrayList<ComboTree>();
			String parentId = areas.get(0).getParentId();
			for(int i=0;i<areas.size();i++){
				ComboTree t = new ComboTree();
				if(areas.get(i).getParentId().equals(parentId)){
					
					t.setId(areas.get(i).getId());
					t.setParentId(areas.get(i).getParentId());
					t.setText(areas.get(i).getAreaName());
					t.setChecked(true);
					
					if(areas.get(i).getIsLeaf().equals("0")){
						t.setChildren((List<ComboTree>) json.get(areas.get(i).getId()));
						
					}else{
						t.setState("option");
					}
					tree2.add(t);
				}else{
					if(areas.get(i).getParentId().equals("")){
						tree.setId(areas.get(i).getId());
						tree.setParentId(areas.get(i).getParentId());
						tree.setText(areas.get(i).getAreaName());
						tree.setChildren(tree2);
						t.setChecked(true);
					}else{
						json.put(parentId, tree2);
						parentId = areas.get(i).getParentId();
						tree2 = new ArrayList<ComboTree>();
						i--;
					}
				}
			}
			
		}
		
		
		return JSON.toJSONString(tree,
				SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty);
	}

}
