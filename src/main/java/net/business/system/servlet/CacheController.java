package net.business.system.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.business.system.entity.TsDictionary;
import net.business.system.service.CacheService;
import net.business.system.service.DictionaryService;
import net.platform.utils.ServletUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cache")
public class CacheController extends BaseController {
	//要去掉
    @Autowired
    protected DictionaryService dictionaryService;
    
	//@Autowired
	private CacheService cacheService;      
	/*
	 * 临时实现从数据库取字典，将改为缓存处理
	 */
	@RequestMapping(params = "getList")
    public void getList(HttpServletRequest request, HttpServletResponse response) {
		String key = request.getParameter("key");
        String bNeedAll = request.getParameter("bNeedAll");
        List<TsDictionary> list = dictionaryService.getListByType(key,bNeedAll);
        ServletUtils.outPrintObjectToJson(response, list);
    }


	@RequestMapping(params = "getDictList")
    public void getDictList(HttpServletRequest request, HttpServletResponse response) {
		String sDictType = request.getParameter("sDictType");
        ServletUtils.outPrintJson(response, cacheService.getDictList(sDictType));

    }
	@RequestMapping(params = "refresh")
    public void refresh(HttpServletRequest request, HttpServletResponse response) {

     //todo refresh all cache data here;

    }
	@RequestMapping(params = "refreshDict")
    public void refreshDict(HttpServletRequest request, HttpServletResponse response) {
        String sDictType = request.getParameter("sDictType");
        cacheService.refreshDict(sDictType);
    }	
}
