package net.business.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.business.system.entity.TsDictionary;
import net.business.system.service.DictionaryService;
import net.platform.dao.impl.BaseDao;
import net.platform.utils.AjaxJson;
import net.platform.utils.page.PageUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

/**
 * 
 * 功能描述：字典服务
 * 
 * @author yiting lin
 * @Email 
 * 
 *        <p>
 *        修改历史：(修改人，修改时间，修改原因/内容)
 *        </p>
 */
@Service("dictionaryService")
@Transactional
public class DictionaryServiceImpl extends BaseDao<TsDictionary, String> implements DictionaryService {
	public List<Object[]> getListDictType(){
    	String sQrySql = "select ID from TS_DICTTYPE where IS_VALID='1'";
    	return findListBySql(sQrySql,0,0);
		 
	}
	
    /**
     * 
     * 功能描述：查询字典
     * 
     * @author yiting lin
     * @Email 
     *        <p>
     *        创建日期 ：2016-7-18 下午03:20:29
     *        </p>
     * 
     * @param pageUtil
     * @param TsDictionary
     * @return String
     * @throws Exception
     */
	
    public String query(PageUtil pageUtil, TsDictionary TsDictionary) throws Exception {
        String sQrySql="select * from TS_DICTIONARY where 1=1 ";
        String sCntSql="select count(*) from TS_DICTIONARY where 1=1 ";
        
        if(TsDictionary.getDictType() != null && !TsDictionary.getDictType().equals("")){
        	sQrySql += " and DICT_TYPE = '" + TsDictionary.getDictType() + "'";
        	sCntSql += " and DICT_TYPE = '" + TsDictionary.getDictType() + "'";
        }
        
        if(TsDictionary.getItemValue() != null && !TsDictionary.getItemValue().equals("")){
        	sQrySql += " and ITEM_NAME like '%" + TsDictionary.getItemValue() + "%'";
        	sCntSql += " and ITEM_NAME like '%" + TsDictionary.getItemValue() + "%'";
        }
        
        if(TsDictionary.getItemName() != null && !TsDictionary.getItemName().equals("")){
        	sQrySql += " and ITEM_NAME like '%" + TsDictionary.getItemName() + "%'";
        	sCntSql += " and ITEM_NAME like '%" + TsDictionary.getItemName() + "%'";
        }
        
    	return JSON.toJSONString(findPageBySql(sQrySql,sCntSql,pageUtil.getPage(),pageUtil.getRows()));
    	
    }


    /**
     * 
     * 功能描述：根据id获取字典
     * 
     * @author yiting lin
     * @Email 
     *        <p>
     *        创建日期 ：2016-7-18 下午03:20:41
     *        </p>
     * 
     * @param id
     * @return TsDictionary
     * 
     *         <p>
     *         修改历史 ：(修改人，修改时间，修改原因/内容)
     *         </p>
     */
    public TsDictionary getById(String id) {
        return get(TsDictionary.class, id);
    }
    // @RedisCache(type=TsDictionary.class,fieldKey="#dictType") 
    public List<TsDictionary> getListByType(String dictType) {
    	String sQrySql = "select * from TS_DICTIONARY where DICT_TYPE=?";
    	List<TsDictionary> tsDict=new ArrayList<TsDictionary>();
		try{
			tsDict.addAll(getListBySQL(sQrySql, new Object[]{dictType}));
		}catch(Exception e){
			e.printStackTrace();
		}
		return tsDict;
	}    
   // @RedisCache(type=TsDictionary.class,fieldKey="#dictType") 
    public List<TsDictionary> getListByType(String dictType,String bNeedAll) {
    	String sQrySql = "select * from TS_DICTIONARY where DICT_TYPE=?";
    	List<TsDictionary> tsDict=new ArrayList<TsDictionary>();
		try{
			if(bNeedAll != null && bNeedAll.equals("true")){
				TsDictionary dict = new TsDictionary();
				dict.setId("");
				dict.setDictType("");
				dict.setItemCode("");
				dict.setItemName("全部");
				dict.setItemValue("全部");
				tsDict.add(dict);
			}
			tsDict.addAll(getListBySQL(sQrySql, new Object[]{dictType}));
		}catch(Exception e){
			e.printStackTrace();
		}
		return tsDict;
	}
    /**
     * 
     * 功能描述：更新字典
     * 
     * @author yiting lin
     * @Email 
     *        <p>
     *        创建日期 ：2016-7-18 下午03:20:50
     *        </p>
     * 
     * @param TsDictionary
     * 
     *            <p>
     *            修改历史 ：(修改人，修改时间，修改原因/内容)
     *            </p>
     */

    /**
     * 方法saveOrUpdate
     * 
     * @param TsDictionary 传入参数
     */
    public void saveOrUpdate(TsDictionary TsDictionary) {
        super.saveOrUpdate(TsDictionary);
    }

    /**
     * 
     * 功能描述：更新字典
     * 
     * @author yiting lin
     * @Email 
     *        <p>
     *        创建日期 ：2016-7-18 下午03:20:50
     *        </p>
     * 
     * @param TsDictionary
     * 
     *            <p>
     *            修改历史 ：(修改人，修改时间，修改原因/内容)
     *            </p>
     */

    /**
     * 方法saveOrUpdate
     * 
     * @param TsDictionary 传入参数
     */
    public void save(TsDictionary TsDictionary) {
        super.save(TsDictionary);
    }

    /**
     * 
     * 功能描述：删除字典
     * 
     * @author yiting lin
     * @Email 
     *        <p>
     *        创建日期 ：2016-7-18 下午03:21:00
     *        </p>
     * 
     * @param TsDictionary
     * 
     *            <p>
     *            修改历史 ：(修改人，修改时间，修改原因/内容)
     *            </p>
     */
    /**
     * 方法delAll
     * 
     * @param TsDictionary
     *            传入参数
     */

    public void deleteAll(TsDictionary TsDictionary) {
        super.deleteAll(TsDictionary.class, TsDictionary.getId());
    }

    /**
     * 
     * 功能描述：sql查询字典
     * 
     * @author yiting lin
     * @Email 
     *        <p>
     *        创建日期 ：2016-7-18 下午03:21:11
     *        </p>
     * 
     * @param sql
     * @return List
     * 
     *         <p>
     *         修改历史 ：(修改人，修改时间，修改原因/内容)
     *         </p>
     */
    public List<TsDictionary> queryBySql(String sql) {
        return  getListBySQL(sql);
    }

    /**
     * 
     * 功能描述： 拼装删除日志信息
     * 
     * @author yiting lin
     * @Email 
     *        <p>
     *        创建日期 ：2016-9-4 上午08:52:24
     *        </p>
     * 
     * @param <T>
     * @param entity
     * @return
     * 
     *         <p>
     *         修改历史 ：(修改人，修改时间，修改原因/内容)
     *         </p>
     */
    public String getdelLog(TsDictionary entity) {

        StringBuffer sb = new StringBuffer();
        String id = entity.getId().toString();
        String[] ids = id.split(",");
        for (int i = 0; i < ids.length; i++) {
            // entity = get(TSlpccgzs.class, ids[i]);
            // sb.append("类型:").append(Type.getDictionaryValue(entity.getType(),
            // "cglx")).append(",");
            // sb.append("标题:").append(entity.getTitle()).append(" ");
            sb.append("ID:").append(ids[i]).append(" ");
        }
        return sb.toString();
    }

    /**
     * 
     * 功能描述： 拼装增加日志信息
     * 
     * @author yiting lin
     * @Email 
     *        <p>
     *        创建日期 ：2016-9-4 上午08:52:24
     *        </p>
     * 
     * @param <T>
     * @param entity
     * @return
     * 
     *         <p>
     *         修改历史 ：(修改人，修改时间，修改原因/内容)
     *         </p>
     */
    public String getaddLog(TsDictionary entity) {

        StringBuffer sb = new StringBuffer();
//        sb.append("字典类别:").append(entity.getItemType()).append(",");
//        sb.append("字典编码:").append(entity.getItemCode()).append(" ");
        return sb.toString();
    }

    /**
     * 
     * 功能描述： 拼装更新日志信息
     * 
     * @author yiting lin
     * @Email 
     *        <p>
     *        创建日期 ：2016-9-4 上午08:52:24
     *        </p>
     * 
     * @param <T>
     * @param entity
     * @return
     * 
     *         <p>
     *         修改历史 ：(修改人，修改时间，修改原因/内容)
     *         </p>
     */
    public String getupdateLog(TsDictionary entity) {

        StringBuffer sb = new StringBuffer();
//        sb.append("字典类别:").append(entity.getItemType()).append(",");
//        sb.append("字典编码:").append(entity.getItemCode()).append(" ");
        return sb.toString();
    }

	@Override
	public String checkDictCode(String dictType, String dictCode) {
		String sql = "select * from TS_DICTIONARY where DICT_TYPE = ?"
    			+ " and ITEM_CODE = ?";
		
		AjaxJson json = new AjaxJson();
    	try{
	    	List<TsDictionary> dicts = getListBySQL(sql, new Object[]{dictType,dictCode});
	    	if(dicts != null && dicts.size() > 0 ){
	    		json.setSuccess(false);
	    		json.setMsg("该字典编号已存在，不可使用");
	    	}else{
	    		json.setSuccess(true);
	    		json.setMsg("该字典编号不存在，可以使用");
	    	}
    	}catch(Exception e){
    		e.printStackTrace();
    		json.setSuccess(false);
    		json.setMsg("检测字典编号失败"+e.getMessage()+"<br>"+"请截图联系管理员。");
    	}
		
		
		return JSON.toJSONString(json);
	}
}
