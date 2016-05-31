package cn.focus.search.admin.scheduled.impl;

import cn.focus.search.admin.config.LastTime;
import cn.focus.search.admin.scheduled.IkScheduledTask;
import cn.focus.search.admin.service.HotWordService;
import cn.focus.search.admin.service.ParticipleManagerService;
import common.Logger;
import net.rubyeye.xmemcached.MemcachedClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 */
@Service
public class IkScheduledTaskImpl  implements IkScheduledTask {

	
	private static Logger log = Logger.getLogger(IkScheduledTaskImpl.class);
	
	//分布式锁，保证只有一个实例运行定时任务。
    @Autowired
    @Qualifier("myMemcachedClient")
    private MemcachedClient cacheClient;
    
	@Autowired
	private LastTime lastTime;
	
	@Autowired
	private HotWordService hotWordService;
	
    @Scheduled(cron="0 0 2 * * *")
    @Override
    public void doUpdateDic() {
    	if(isExecutor()){
        	lastTime.setLastModifiedTime();
        	log.info("执行每日定时任务：更新词库。");
    	}
    }
    //每日，将新增楼盘名字扫入数据库。
    @Scheduled(cron="0 0 23 * * *")
    public void getNewProj() {
    	if(isExecutor()){
        	hotWordService.importNewProjName();
        	log.info("执行每日定时任务：新增楼盘名入库。");
    	}
    }
    
    //根据分布式锁来判断是否应该由该实例来执行定时任务。
    public boolean isExecutor(){
    	boolean getLock=false;
    	String key = "focus-search-admin-lock";
        
        try {
        	//时效时间单位是毫秒，如果已经存在对应的key，就会添加失败，返回false。
            getLock = cacheClient.add(key, 60, "ok");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        
    	return getLock;
    }
}
