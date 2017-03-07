package net.business.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.business.system.entity.TsDictType;
import net.business.system.service.DictTypeService;
import net.platform.dao.impl.BaseDao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("dictTypeService")
@Transactional
public class DictTypeServiceImpl extends BaseDao<TsDictType,String> implements DictTypeService{

	@Override
	public List<TsDictType> getDictTypeAll(String bNeedAll) {
		String sql = "select * from TS_DICTTYPE";
		List<TsDictType> dictType = new ArrayList<TsDictType>();
		if(bNeedAll.equals("true")){
			TsDictType dict = new TsDictType();
			dict.setId("");
			dict.setIsValid("1");
			dict.setDictName("全部");
			dictType.add(dict);
		}
		dictType.addAll(getListBySQL(sql));
		return dictType;
	}
	
	
}
