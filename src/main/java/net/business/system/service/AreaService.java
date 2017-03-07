package net.business.system.service;

import java.util.List;

import net.business.system.entity.TsArea;
import net.platform.dao.IBaseDao;
import net.platform.utils.page.ComboTree;
import net.platform.utils.page.PageUtil;

/**
 * 功能描述：地区管理
 * @author zhangxin
 * @created 2017-01-03 16:57:14
 * @Email 
 * 
 *        <p>
 *        修改历史：(修改人，修改时间，修改原因/内容)
 *        </p>
 *
 */
public interface AreaService extends IBaseDao<TsArea,String>{
	
	/**
	 * 功能描述：获取地区列表
	 * @author zhangxin
	 * @created 2017-01-03 16:35:32
	 * 
	 * @param area
	 * @param pageUtil
	 * @return
	 */
	public String getAreaTree(TsArea area);
	/**
	 * 功能描述:把地区表生成数
	 * @author zhangxin
	 * @created 2017-01-03 16:56:44
	 * @Email
	 * 		<p>
	 *        修改历史：(修改人，修改时间，修改原因/内容)
	 *      </p>
	 *      
	 * @param parentId
	 * @param cmdStr
	 * @return
	 */
	public List<ComboTree> getComboTreeByParentId(String parentId,String cmdStr);
	/**
	 * 功能描述：根据条件获取地区列表
	 * @author zhangxin
	 * @created 2017-01-10 15:01:43
	 * @Email
	 * 		<p>
	 *        修改历史：(修改人，修改时间，修改原因/内容)
	 *      </p>
	 *      
	 * @param cmdStr
	 * @return
	 */
	public List<TsArea> getAreaAll(String cmdStr);
	/**
	 * 功能描述：删除地区
	 * @author zhangxin
	 * @created 2017-01-20 15:25:21
	 * @Email
	 * 		<p>
	 *        修改历史：(修改人，修改时间，修改原因/内容)
	 *      </p>
	 *      
	 * @param id
	 * @return
	 */
	public String delArea(String id);
	
}
