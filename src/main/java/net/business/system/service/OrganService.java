package net.business.system.service;

import java.util.List;

import net.business.system.entity.TsOrgan;
import net.platform.dao.IBaseDao;
import net.platform.utils.page.ComboTree;

public interface OrganService extends IBaseDao<TsOrgan, String>{
	
	/**
	 * 功能描述：把机构列表转换成数型
	 * @author zhangxin
	 * @created 2016-12-21 15:08:21
	 * @Email
	 * 
	 * @return List<ComboTree>
	 */
	public List<ComboTree> getOrganComboTree(String parentId,String cmdStr);
	/**
	 * 功能描述：获取机构列表
	 * @author zhangxin
	 * @created 2017-01-03 16:59:32
	 * @Email
	 * 		<p>
	 *        修改历史：(修改人，修改时间，修改原因/内容)
	 *      </p>
	 * 
	 * @param tsOrgan
	 * @return
	 */
	public String getOrganTree(TsOrgan tsOrgan);
	/**
	 * 功能描述：根据条件获取列表
	 * @author zhangxin
	 * @created 2017-01-09 15:43:23
	 * @Email
	 * 		<p>
	 *        修改历史：(修改人，修改时间，修改原因/内容)
	 *      </p>
	 *       
	 * @param cmd
	 * @return
	 */
	public List<TsOrgan> getOrganByCmd(String cmd);
	/**
	 * 功能描述：获取机构树
	 * @author zhangxin
	 * @created 2017-01-18 14:17:34
	 * @Email
	 * 		<p>
	 *        修改历史：(修改人，修改时间，修改原因/内容)
	 *      </p>
	 * @return
	 */
	public String getOrganZtree();
	/**
	 * 功能描述：删除机构
	 * @author zhangxin
	 * @created 2017-01-20 14:26:43
	 * @Email
	 * 		<p>
	 *        修改历史：(修改人，修改时间，修改原因/内容)
	 *      </p>
	 *      
	 * @return
	 */
	public String delOrgan(String id);
	
}
