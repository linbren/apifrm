package net.business.system.service.impl;

import net.business.system.entity.TsOrganExt;
import net.business.system.service.OrganExtService;
import net.platform.dao.impl.BaseDao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("organExtService")
@Transactional
public class OrganExtServiceImpl extends BaseDao<TsOrganExt,String> 
		implements OrganExtService {
	
}
