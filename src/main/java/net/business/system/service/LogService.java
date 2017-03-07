package net.business.system.service;

import net.business.system.entity.TsLog;
import net.platform.dao.IBaseDao;

public interface LogService extends IBaseDao<TsLog, String> {

	public boolean add(TsLog tsLog);
	
}
