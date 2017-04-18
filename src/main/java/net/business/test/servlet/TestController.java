package net.business.test.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.business.system.entity.TsFunction;
import net.business.test.service.TestService;
import net.platform.servlet.BaseController;
import net.platform.utils.ServletUtils;
import net.platform.utils.page.PageUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test")
public class TestController extends BaseController{
	@Autowired
    private TestService testService;
	@RequestMapping(params = "gos")
	public ModelAndView goList(HttpServletRequest request){
		return new ModelAndView("test/select2");
	}

    @RequestMapping(params = "getFuncList")
    public void getList(PageUtil pageUtil, TsFunction tsFunc, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	String term=request.getAttribute("data").toString();
    	log.info(term);
    	//tsFunc.setFuncName(term);
    	resp.setItems(testService.getFuncByPage(pageUtil, tsFunc));
    	ServletUtils.outPrintObjectToJson(response, resp);
    }
}
