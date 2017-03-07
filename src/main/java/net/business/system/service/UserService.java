package net.business.system.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.business.system.entity.TsOrgan;
import net.business.system.entity.TsUser;
import net.platform.dao.IBaseDao;
import net.platform.utils.AjaxJson;
import net.platform.utils.page.PageUtil;

/**
 * 功能描述：用户管理
 * @author zhangxin
 * @created 2016-12-15 3:57:14
 * @Email 
 * 
 *        <p>
 *        修改历史：(修改人，修改时间，修改原因/内容)
 *        </p>
 *
 */
public interface UserService extends IBaseDao<TsUser, String>{
	
	/**
	 * 功能描述：根据编号查找用户
	 * @author zhangxin
	 * @created 2016-12-15 3:57:14
	 * @param user
	 * @return TsUser
	 */
	public TsUser getUserByCode(TsUser user);
	
	/**
	 * 功能描述：获取列表
	 * @author zhangxin
	 * @created 2016-12-15 14:16:22
	 * @Email
	 * 
	 * @param pageUtil
	 * @param tsUser
	 * @return String
	 * @throws Exception
	 */
	public String getUserByPage(PageUtil pageUtil,TsUser tsUser) throws Exception;
	
	/**
	 * 功能描述：添加或修改数据
	 * @author zhangxin
	 * @created 2016-12-22 10:32:12
	 * @Email
	 * 
	 * @param tsUser
	 * @param request
	 * @return
	 */
	public AjaxJson saveOrEdit(TsUser tsUser,HttpServletRequest request);
	
	/**
	 * 功能描述：重置密码
	 * @author zhangxin
	 * @created 2016-12-29 16:33:23
	 * @Email
	 * 
	 * @param userIds
	 * @return
	 */
	public AjaxJson resetPass(String userIds);
	/**
	 * 功能描述：根据用户树
     * @author zhangxin
     * @created 2017-01-18 9:51:23
     * @Email
     * 		<p>
     *        
     *      </p>
	 * @param organs
	 * @param appId
	 * @return
	 */
	public String getUserTree(String appId,String roleId,String organId);
	/**
	 * 功能描述：根据条件查找用户
	 * @author zhangxin
     * @created 2017-01-18 9:51:23
     * @Email
     * 		<p>
     *        
     *      </p>
     *      
	 * @param cmdStr
	 * @return
	 */
	public List<TsUser> getUserByCmd(String cmdStr);
	/**
	 * 功能描述：根据条件统计用户
	 * @author zhangxin
     * @created 2017-01-18 9:51:23
     * @Email
     * 		<p>
     *        
     *      </p>
     *      
	 * @param cmdStr
	 * @return
	 */
	public Integer getUserCount(String cmdStr);
}
