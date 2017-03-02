package net.business.test.service;

import net.business.test.entity.TsFunction;
import net.platform.dao.IBaseDao;
import net.platform.utils.page.PageUtil;

public interface TestService extends IBaseDao<TsFunction, String> {

	public String getFuncByPage(PageUtil pageUtil,TsFunction tsFunc);
}
