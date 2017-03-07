package net.business.system.service.impl;

import net.business.system.entity.TsLog;
import net.business.system.service.LogService;
import net.platform.dao.impl.BaseDao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("logService")
@Transactional
public class LogServiceImpl extends BaseDao<TsLog, String> implements
		LogService {
	@Override
	public boolean add(TsLog tsLog) {
		boolean bResult = false;
		try {
			save(tsLog);
			bResult = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bResult;
	}
}
