package net.business.system.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.business.system.entity.TsFunction;
import net.platform.dao.IBaseDao;
import net.platform.utils.AjaxJson;
import net.platform.utils.page.ComboTree;

public interface FunctionService extends IBaseDao<TsFunction, String> {
	/**
	 * 功能描述：根据用户ID查找功能
	 * @author zhangxin
	 * @Email
	 * 		<p>
	 *        修改历史：(修改人，修改时间，修改原因/内容)
	 *      </p>
	 * @param userId
	 * @return
	 */
	public List<TsFunction> getFunctionByUserId(String userId);
	/**
	 * 功能描述：根据用户ID获取按钮权限
	 * @author zhangxin
	 * @Email
	 * 		<p>
	 *        修改历史：(修改人，修改时间，修改原因/内容)
	 *      </p>
	 *      
	 * @param userId
	 * @param funcUrl
	 * @return
	 */
	public List<TsFunction> getButtonByUserId(String userId,String funcUrl);
	/**
	 * 功能描述：调用getButtonByUserId方法，获取按钮权限
	 * @author zhangxin
	 * @Email
	 * 		<p>
	 *        修改历史：(修改人，修改时间，修改原因/内容)
	 *      </p>
	 *      
	 * @param request
	 * @return
	 */
	public String getButtons(HttpServletRequest request);
	/**
	 * 功能描述：根据角色ID获取功能
	 * @author zhangxin
	 * @Email
	 * 		<p>
	 *        修改历史：(修改人，修改时间，修改原因/内容)
	 *      </p>
	 *	
	 * @param roleId
	 * @return
	 */
	public List<TsFunction> getFunctionByRoleId(String roleId);
	/**
	 * 功能描述：获取功能树
	 * @author zhangxin
	 * @Email
	 * 		<p>
	 *        修改历史：(修改人，修改时间，修改原因/内容)
	 *      </p>
	 *      
	 * @param roleId
	 * @param appId
	 * @return
	 */
	public String queryForzTree(String roleId,String appId);
	/**
	 * 功能描述：根据功能编号查找功能列表
	 * @author zhangxin
	 * @Email
	 * 		<p>
	 *        修改历史：(修改人，修改时间，修改原因/内容)
	 *      </p>
	 *      
	 * @param roleFuncs
	 * @return
	 */
	public List<TsFunction> getFunctionByCode(String roleFuncs);

	public List<ComboTree> getComboTreeByParentId(String parentId,String cmdStr);
	/**
	 * 功能描述：获取功能数
	 * @author zhangxin
	 * @Email
	 * 		<p>
	 *        修改历史：(修改人，修改时间，修改原因/内容)
	 *      </p>
	 *      
	 * @param tsFunc
	 * @return
	 */
	public String getFunctionTree(TsFunction tsFunc);
	/**
	 * 功能描述：删除功能
	 * @author zhangxin
	 * @Email 
	 * 		<p>
	 *        修改历史：(修改人，修改时间，修改原因/内容)
	 *      </p>
	 * @param funcCode
	 * @return
	 */
	public AjaxJson delFunction(String funcCode);
	/**
	 * 功能描述：根据条件查找功能
	 * @author zhangxin
	 * @Email
	 * 		<p>
	 *        修改历史：(修改人，修改时间，修改原因/内容)
	 *      </p>
	 * @param cmd
	 * @return
	 */
	public List<TsFunction> getFunctionByCmd(String cmdStr);
	
}
