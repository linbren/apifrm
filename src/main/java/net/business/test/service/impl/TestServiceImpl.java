package net.business.test.service.impl;

import net.business.test.entity.TsFunction;
import net.business.test.service.TestService;
import net.platform.dao.impl.BaseDao;
import net.platform.utils.page.PageUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

@Service("testService")
@Transactional
public class TestServiceImpl extends BaseDao<TsFunction, String> implements TestService {
	@Override
	public String getFuncByPage(PageUtil pageUtil, TsFunction tsFunc) {
		String sQrySql = "select func_code,func_name,parent_code from TS_FUNCTION where FUNC_TYPE='1'";
		String sCntSql = "select count(*) from TS_FUNCTION where FUNC_TYPE='1'";
		
		if(tsFunc.getFuncName() != null && !tsFunc.getFuncName().equals("")){
			sQrySql += " and FUNC_NAME like '%" + tsFunc.getFuncName() + "%'";
			sCntSql += " and FUNC_NAME like '%" + tsFunc.getFuncName() + "%'";
		}
		return JSON.toJSONString(findPageBySql(sQrySql,sCntSql,pageUtil.getPage(),pageUtil.getRows()));
	}

}
