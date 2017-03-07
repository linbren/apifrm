package net.business.test.service;

import net.business.system.entity.TsFunction;
import net.platform.dao.IBaseDao;
import net.platform.utils.page.PageResults;
import net.platform.utils.page.PageUtil;

public interface TestService extends IBaseDao<TsFunction, String> {

	public PageResults<TsFunction> getFuncByPage(PageUtil pageUtil,TsFunction tsFunc);
}
