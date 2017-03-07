package net.business.system.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.business.system.entity.TsDictType;
import net.business.system.entity.TsDictionary;
import net.business.system.service.DictTypeService;
import net.business.system.service.DictionaryService;
import net.platform.utils.AjaxJson;
import net.platform.utils.ServletUtils;
import net.platform.utils.StringUtils;
import net.platform.utils.page.PageUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * 
 * 功能描述：字典管理
 * 
 * @author  
 * @Email  
 * 
 *        <p>
 *        修改历史：(修改人，修改时间，修改原因/内容)
 *        </p>
 */
@Controller
@RequestMapping("/dict")
public class DictionaryController extends BaseController {
	
	private static Log log = LogFactory.getLog(DictionaryController.class);
	
    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private DictTypeService dictTypeService;
    /**
     * 
     * 功能描述：跳转到列表页面
     * 
     * @author yiting lin
     * @Email 
     *        <p>
     *        创建日期 ：
     *        </p>
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goList")
    public ModelAndView goList(HttpServletRequest request) {

        return new ModelAndView("system/dictionary/dictionary_list");
    }
    /**
	 * 功能描述：跳转到新增页面
	 * 
	 * @param tsRole
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(HttpServletRequest request){
		return new ModelAndView("system/dictionary/dictionary_edit");
	}
    /**
     * 
     * 功能描述：跳转到编辑页面
     * 
     * @author yiting lin
     * @Email 
     *        <p>
     *        创建日期 ：
     *        </p>
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goEdit")
    public ModelAndView goEdit(TsDictionary dictionary, HttpServletRequest request) {
        if (StringUtils.isNotBlank(dictionary.getId())) {
            dictionary = dictionaryService.get(TsDictionary.class, dictionary.getId());
            request.setAttribute("dictionaryPage", dictionary);
        }
        return new ModelAndView("system/dictionary/dictionary_edit");
    }
    /**
     * 功能描述：跳转到字典类型修改页面
     * @author zhangxin
     * @Email
     * 		<p>
     *        
     *      </p>
     * 		
     * @param request
     * @return
     */
    @RequestMapping(params = "goEditType")
    public ModelAndView goEditType(HttpServletRequest request){
    	return new ModelAndView("system/dictionary/dict_type_edit");
    }
    
    /**
     * 
     * 功能描述：列表请求数据
     * 
     * @author 
     * @Email 
     *        <p>
     *        
     *        </p>
     * 
     * @param dictionary
     * @param pageUtil
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "getList")
    public void getList(PageUtil pageUtil, TsDictionary dictionary, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String jsonStr = dictionaryService.query(pageUtil, dictionary);
        ServletUtils.outPrintJson(response, jsonStr);
        
    }
    /**
     * 
     * 功能描述：保存字典
     * 
     * @author 
     * @Email 
     * 
     * @param dictionary
     * @param request
     */
    @RequestMapping(params = "doSave")
    @ResponseBody
    public void doSave(TsDictionary dictionary, HttpServletRequest request, HttpServletResponse response) {

        AjaxJson json = new AjaxJson();
        String msg = "保存成功";
        try {
            if (StringUtils.isNotBlank(dictionary.getId().toString())) {
                dictionaryService.saveOrUpdate(dictionary);
            } else {
                dictionaryService.save(dictionary);
            }

        } catch (Exception e) {
            msg = "保存失败"+e.getMessage()+"<br>"+"请截图联系管理员。";
        }
        json.setMsg(msg);
        ServletUtils.outPrintObjectToJson(response, json);

    }

    /**
     * 
     * 功能描述：删除字典
     * 
     * @author  
     * @Email  
     *        <p>
     *        创建日期 ：2016-7-18 下午03:09:16
     *        </p>
     * 
     * @param dictionary
     * @param request
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TsDictionary dictionary, HttpServletRequest request) {

        AjaxJson json = new AjaxJson();
        String msg = "删除成功";
        try {
            if (StringUtils.isNotBlank(dictionary.getId())) {
                dictionaryService.deleteAll(dictionary);
            }
        } catch (Exception e) {
            msg = "删除失败"+e.getMessage()+"<br>"+"请截图联系管理员。";
        }        
        json.setMsg(msg);
        return json;
    }
    /**
     * 功能描述:获取字典类型列表
     * @author zhangxin
     * @Email
     * 		<p>
     *       	
     *      </p>
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "getDictType")
    public void getDictType(HttpServletRequest request,HttpServletResponse response){
    	String bNeedAll = request.getParameter("bNeedAll");
    	List<TsDictType> dictTypes = dictTypeService.getDictTypeAll(bNeedAll);
    	
    	ServletUtils.outPrintJson(response, JSON.toJSONString(dictTypes));
    }
    /**
     * 功能描述：保存字典类型修改
     * @author zhangin
     * @Email
     * 		<p>
     *       	
     *      </p>
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "doSaveType")
    public void doSaveType(HttpServletRequest request,HttpServletResponse response){
    	String id = request.getParameter("id");
    	String dictName = request.getParameter("dictName");
    	String isValid = request.getParameter("isValid");
    	log.info("id:"+id+"   dictName:"+dictName+"   isValid:"+isValid);
    	AjaxJson json = new AjaxJson();
    	try {
    		TsDictType dictType = new TsDictType();
    		dictType.setId(id);
    		dictType.setDictName(dictName);
    		dictType.setIsValid(isValid);
			dictTypeService.saveOrUpdate(dictType);
			json.setSuccess(true);
			json.setMsg("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("保存失败"+e.getMessage()+"<br>"+"请截图联系管理员。");
		}
    	
    	ServletUtils.outPrintJson(response, JSON.toJSONString(json));
    }
    
    @RequestMapping(params = "delDictType")
    public void delDictType(HttpServletRequest request,HttpServletResponse response){
    	String id = request.getParameter("id");
    	AjaxJson json = new AjaxJson();
    	try {
			dictTypeService.deleteById(id);
			json.setSuccess(true);
			json.setMsg("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("删除失败"+e.getMessage()+"<br>"+"请截图联系管理员。");
		}
    	ServletUtils.outPrintJson(response, JSON.toJSONString(json));
    }
    
    @RequestMapping(params = "checkCode")
    public void checkCode(HttpServletRequest request,HttpServletResponse response){
    	String dictType = request.getParameter("dictType");
    	String dictCode = request.getParameter("dictCode");
    	
    	ServletUtils.outPrintJson(response, dictionaryService.checkDictCode(dictType, dictCode));
    }
    
}
