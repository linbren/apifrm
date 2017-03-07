package net.business.system.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.business.system.entity.TsFunction;
import net.business.system.entity.TsUser;
import net.platform.dao.IBaseDao;
import net.platform.utils.page.PageUtil;

public interface LoginService extends IBaseDao<TsFunction, String> {

	/**
	 * 
	 * 
	 * @author
	 * @Email <p>
	 *        创建日期 ：2016-7-18 下午03:20:29
	 *        </p>
	 * 
	 * @param pageUtil
	 * @param TsFunction
	 * @return String
	 * @throws Exception
	 */
	public String query(PageUtil pageUtil, TsFunction TsFunction)
			throws Exception;


	/**
	 * 
	 * 功能描述： 
	 * 
	 * @author
	 * 
	 * @param id
	 * @return TsFunction
	 */
	public TsFunction getById(String id);

	/**
	 * 
	 * 功能描述：
	 * 
	 * @author

	 * 
	 * @param TsFunction
	 */
	public void saveOrUpdate(TsFunction TsFunction);

	/**
	 * 
	 * 功能描述： 
	 * 
	 * 
	 * @param TsFunction
	 */
	public void save(TsFunction TsFunction);

	/**
	 * 
	 * 功能描述：删除字典
	 * 
	 * @author
	 * @Email <p>
	 *        创建日期 ：2014-6-18 下午03:21:00
	 *        </p>
	 * 
	 * @param TsFunction
	 * 
	 */
	public void deleteAll(TsFunction TsFunction);

	/**
	 * 
	 * 功能描述： 
	 * 
	 * @author  
	 * @Email  

	 * 
	 * @param sql
	 * @return List
	 */
	public List queryBySql(String sql);
	public String queryForZtree(List<TsFunction> functions);
	
	public String checkUser(TsUser tsUser,TsUser user,HttpServletRequest request);
	
}