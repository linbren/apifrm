package net.business.system.service;

import java.util.List;

import net.business.system.entity.TsDictType;
import net.platform.dao.IBaseDao;

public interface DictTypeService extends IBaseDao<TsDictType,String>{
	
	public List<TsDictType> getDictTypeAll(String bNeetAll);
	
	
}
