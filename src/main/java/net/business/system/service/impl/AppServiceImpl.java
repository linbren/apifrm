package net.business.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import net.business.system.entity.TsApp;
import net.business.system.service.AppService;
import net.platform.dao.impl.BaseDao;
import net.platform.utils.page.PageUtil;

@Service("appService")
@Transactional
public class AppServiceImpl extends BaseDao<TsApp, String> implements AppService {

	@Override
	public List<TsApp> getAppAll(boolean bNeedAll) {
		String sql = "select * from TS_APP where IS_VALID = 1";
		List<TsApp> apps = new ArrayList<TsApp>();
		if(bNeedAll){
			TsApp app = new TsApp();
			app.setAppName("全部");
			app.setId("");
			app.setIsValid("1");
			apps.add(app);
		}
    	apps.addAll(getListBySQL(sql));
		return apps;
	}

	@Override
	public String getAppByPage(PageUtil pageUtil, TsApp app) {
		String sQrySql = "select * from TS_APP where 1=1";
		String sCntSql = "select count(*) from TS_APP where 1=1";
		
		if(app.getAppName() != null && !app.getAppName().equals("")){
			sQrySql += " and APP_NAME like '%" + app.getAppName() + "%'";
			sCntSql += " and APP_NAME like '%" + app.getAppName() + "%'";
		}
		if(app.getAppType() != null){
			sQrySql += " and APP_TYPE = " + app.getAppType();
			sCntSql += " and APP_TYPE = " + app.getAppType();
		}
		return JSON.toJSONString(findPageBySql(sQrySql,sCntSql,pageUtil.getPage(),pageUtil.getRows()));
	}

}
