package cn.focus.search.admin.scheduled.impl;

import cn.focus.search.admin.scheduled.IkScheduledTask;
import cn.focus.search.admin.service.ParticipleManagerService;
import common.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by zhaozhaozhang on 2015/8/5.
 */
@Service
public class IkScheduledTaskImpl  implements IkScheduledTask {

	
	private static Logger log = Logger.getLogger(IkScheduledTaskImpl.class);
	@Autowired
	private ParticipleManagerService pmService;
	
    @Scheduled(cron="0 0 23 ? * FRI")
    @Override
    public void doUpdateDic() {
    	
    	log.info("更新词库。");
    	pmService.updateIK();
  
    }
    
    @Scheduled(cron="0 0 10 ? * *")
    @Override
    public void doUpdateParticiple(){
    	log.info("对新进入数据库的楼盘进行初步分词。");
    	pmService.updateParticiple();
    }
}
