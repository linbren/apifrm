package net.business.system.service;

import net.platform.dao.IBaseDao;

public interface CacheService extends IBaseDao<Object[], String> {

	public boolean loadAllDict();	
	public String getDictList(String sDictType);
	public void refreshDict(String sDictType);
	
}
