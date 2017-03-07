package net.business.system.service;

import net.business.system.entity.TsUser;
import net.platform.dao.IBaseDao;

/**
 * 功能描述：个人面板
 * @author zhangxin
 * @created 2017-02-23 15:11:42
 * @Email
 * 		<p>
 * 		修改历史：(修改人，修改时间，修改原因/内容)
 * 		</p>
 *
 */
public interface DashboardService extends IBaseDao<TsUser,String>{
	/**
	 * 功能描述：根据时间获取用户和新闻数据
	 * @author zhangxin
	 * @created 2017-02-23 15:12:21
	 * @Email
	 * 		<p>
	 * 		修改历史：(修改人，修改时间，修改原因/内容)
	 * 		</p>
	 * @return
	 */
	public String getInfoByTime(String time);
	/**
	 * 功能描述：获取机构经纬度信息
	 * @author zhangxin
	 * @created 2017-02-23 15:12:21
	 * @Email
	 * 		<p>
	 * 		修改历史：(修改人，修改时间，修改原因/内容)
	 * 		</p>
	 * @return
	 */
	public String getOrganData();
	
	public String getWeather(String addr);
	
}
