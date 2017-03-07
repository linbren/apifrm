package net.business.system.service;

import java.util.List;

import net.business.system.entity.TsApp;
import net.platform.dao.IBaseDao;
import net.platform.utils.page.PageUtil;

public interface AppService extends IBaseDao<TsApp, String> {

	public List<TsApp> getAppAll(boolean bNeedAll);
	
	public String getAppByPage(PageUtil pageUtil,TsApp app);
}
