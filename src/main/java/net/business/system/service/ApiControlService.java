package net.business.system.service;

import java.util.List;

import net.business.system.entity.TsApiControl;
import net.platform.dao.IBaseDao;

public interface ApiControlService extends IBaseDao<TsApiControl, String> {

	public List<String> getApiList(String apiStatus);
	
}
