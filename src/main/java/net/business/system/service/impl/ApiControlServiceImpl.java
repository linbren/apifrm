package net.business.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.business.system.entity.TsApiControl;
import net.business.system.service.ApiControlService;
import net.platform.dao.impl.BaseDao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("apiService")
@Transactional
public class ApiControlServiceImpl extends BaseDao<TsApiControl, String> implements ApiControlService {

	@Override
	public List<String> getApiList(String apiStatus){
		String sql = "select API_NAME from TS_API_CONTROL where API_STATUS = ?";
		List<String> wapis = new ArrayList<String>();
		List<Object[]> objs = findListBySql(sql,0,0,apiStatus);
		for(int i=0;i<objs.size();i++)
		{
			String sTemp=""+objs.get(i);
			wapis.add(sTemp);
			log.info("getApiList==="+sTemp);
		}
		return wapis;
	}


}
