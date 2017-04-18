package net.business.system.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import net.business.system.service.ApiControlService;

public class CacheInit {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
	@Autowired
	private ApiControlService apiControlService;
	public static List<String> whiteApis;
    
    public void init(){
        logger.info("正在初始化缓存");

        whiteApis=apiControlService.getApiList("1");
        
        logger.info("缓存初始化结束");
    }

    
}