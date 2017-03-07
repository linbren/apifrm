package net.business.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.business.system.entity.TsFunction;
import net.business.system.entity.TsUser;
import net.business.system.service.LoginService;
import net.platform.dao.impl.BaseDao;
import net.platform.utils.AjaxJson;
import net.platform.utils.Const;
import net.platform.utils.page.PageUtil;
import net.platform.utils.page.Ztree;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

@Service("loginService")
@Transactional
public class LoginServiceImpl extends BaseDao<TsFunction, String> implements LoginService {
    private static Log log = LogFactory.getLog(LoginServiceImpl.class);
	/**
     * 
     * 功能描述：
     * 
     * @author yiting lin
     * @Email 
     *        <p>
     *        创建日期 ：2016-7-18 下午03:20:29
     *        </p>
     * 
     * @param pageUtil
     * @param TsFunction
     * @return String
     * @throws Exception
     */
	@Transactional
    public String query(PageUtil pageUtil, TsFunction TsFunction) throws Exception {
        String sQrySql="select * from TS_DICTIONARY";
        String sCntSql="select count(*) from TS_DICTIONARY";
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
     * @return TsFunction
     * 
     *         <p>
     *         修改历史 ：(修改人，修改时间，修改原因/内容)
     *         </p>
     */
    public TsFunction getById(String id) {
        return get(TsFunction.class, id);
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
     * @param TsFunction
     * 
     *            <p>
     *            修改历史 ：(修改人，修改时间，修改原因/内容)
     *            </p>
     */

    /**
     * 方法saveOrUpdate
     * 
     * @param TsFunction 传入参数
     */
    public void saveOrUpdate(TsFunction TsFunction) {
        super.saveOrUpdate(TsFunction);
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
     * @param TsFunction
     * 
     *            <p>
     *            修改历史 ：(修改人，修改时间，修改原因/内容)
     *            </p>
     */

    /**
     * 方法saveOrUpdate
     * 
     * @param TsFunction 传入参数
     */
    public void save(TsFunction TsFunction) {
        super.save(TsFunction);
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
     * @param TsFunction
     * 
     *            <p>
     *            修改历史 ：(修改人，修改时间，修改原因/内容)
     *            </p>
     */
    /**
     * 方法delAll
     * 
     * @param TsFunction
     *            传入参数
     */

    public void deleteAll(TsFunction TsFunction) {
        super.deleteAll(TsFunction.class, TsFunction.getId());
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
    public List<TsFunction> queryBySql(String sql) {
        return  getListBySQL(sql);
    }
    
    public String queryForZtree(List<TsFunction> functions) {
       //String sQrySql="select * from TS_FUNCTION";
        //List<TsFunction> functions=getListBySQL(sQrySql);
        List<Ztree> lzt=new ArrayList<Ztree>();

        for(TsFunction f:functions){
        	Ztree zt=new Ztree();
        	zt.setId(f.getFuncCode());
        	zt.setpId(f.getParentCode());
        	zt.setName(f.getFuncName());
        	zt.setUrl(f.getFuncUrl());
        	zt.setIcon(f.getFuncIcon());
        	zt.setTarget("mainFrame1");
        	lzt.add(zt);
        }
        return  JSON.toJSONString(lzt);
    }
    
    public String checkUser(TsUser tsUser,TsUser user,HttpServletRequest request){
    	String msg = "";
    	boolean b = false;
		String vCode = request.getParameter("vCode");
		String sCode = request.getSession().getAttribute("_code").toString();		
		log.info("vCode:" + vCode + "   Session:"
				+ sCode);    
		if ("".equals(vCode.trim())){
			msg ="验证码不能为空！";
		}else if (!vCode.equalsIgnoreCase(sCode)){
			msg ="验证码错误！";
		}else if (user == null || user.getId() == null){
    		msg ="该用户不存在";
    	}else{
    		if(!user.getUserPass().equals(tsUser.getUserPass())){
    			msg = "密码错误";
    		}else if(Const.USER_CANCLED.equals(tsUser.getStatus())){
    			msg = "该用户已注销";
    		}else if(Const.USER_LOCKED.equals(tsUser.getStatus())){
    			msg = "该用户以锁定";
    		}else{
    			request.getSession().setAttribute("user", user);
    			msg = "验证通过";
    			b = true;
    		}
    		
    	}
    	AjaxJson aj = new AjaxJson();
    	aj.setSuccess(b);
    	aj.setMsg(msg);
    	return JSON.toJSONString(aj);
    }
    
}
