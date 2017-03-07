package net.business.system.service;

import java.util.List;

import net.business.system.entity.TsDictionary;
import net.platform.dao.IBaseDao;
import net.platform.utils.page.PageUtil;

/**
 * 
 * 功能描述：字典服务
 * 
 * @author 
 * 
 * 
 *        <p>
 *        修改历史：(修改人，修改时间，修改原因/内容)
 *        </p>
 */
public interface DictionaryService extends IBaseDao<TsDictionary, String> {

    /**
     * 
     * 功能描述：查询字典
     * 
     * @author 
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
    public String query(PageUtil pageUtil, TsDictionary TsDictionary) throws Exception;
    //public String query() throws Exception;
    public List<Object[]> getListDictType();
    /**
     * 
     * 功能描述：根据id获取字典
     * 
     * @author 
     * @Email 
     *        <p>
     *        创建日期 ：2016-7-18 下午03:20:41
     *        </p>
     * 
     * @param id
     * @return TsDictionary
     */
    public TsDictionary getById(String id);
    public List<TsDictionary> getListByType(String dictType);
    public List<TsDictionary> getListByType(String dictType,String bNeedAll);
    /**
     * 
     * 功能描述：更新字典
     * 
     * @author
     * @Email 
     *        <p>
     *        创建日期 ：2016-7-18 下午03:20:50
     *        </p>
     * 
     * @param TsDictionary
     */
    public void saveOrUpdate(TsDictionary TsDictionary);

    /**
     * 
     * 功能描述：增加字典
     * 
     * @author 
     * @Email 
     *        <p>
     *        创建日期 ：2016-7-18 下午03:20:50
     *        </p>
     * 
     * @param TsDictionary
     */
    public void save(TsDictionary TsDictionary);

    /**
     * 
     * 功能描述：删除字典
     * 
     * @author  
     * @Email 
     *        <p>
     *        创建日期 ：2014-6-18 下午03:21:00
     *        </p>
     * 
     * @param TsDictionary
     * 
     */
    public void deleteAll(TsDictionary TsDictionary);

    /**
     * 
     * 功能描述：sql查询字典
     * 
     * @author 
     * 
     *        <p>
     *        创建日期 ：2016-7-18 下午03:21:11
     *        </p>
     * 
     * @param sql
     * @return List
     */
    public List queryBySql(String sql);
    
    /**
     * 功能描述：验证字典编号
     * @author zhangxin
     * @created 2017-01-20 9:28:32
     * @Email
     * 		<p>
     * 
     * 		</p>
     * 
     * @param dictType
     * @param dictCode
     * @return
     */
    public String checkDictCode(String dictType,String dictCode);
}
