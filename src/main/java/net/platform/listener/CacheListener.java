package net.platform.listener;

import java.util.List;

import net.business.system.service.ApiControlService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/*
 * 监听器，用于项目启动的时候初始化信息
 */
@Service
public class CacheListener implements
		ApplicationListener<ContextRefreshedEvent> {
	// 日志
	private final Logger log = Logger.getLogger(CacheListener.class);
	@Autowired
	private ApiControlService apiControlService;
	public static List<String> whiteApis;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// spring 启动的时候缓存 信息
		System.out.println("\nevent\n"+event.getApplicationContext().getDisplayName());
		if (event.getApplicationContext().getDisplayName()
				.equals("WebApplicationContext for namespace 'DispatcherServlet-servlet'")) {
			log.info("\n\n\n_________\n\n缓存数据开始");
	        whiteApis=apiControlService.getApiList("1");
			log.info("\n\n\n_________\n\n缓存数据");
		}
	}



}