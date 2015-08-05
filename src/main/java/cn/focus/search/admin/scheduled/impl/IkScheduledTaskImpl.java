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
	
    @Scheduled(cron="0 0 23 * * ?")
    @Override
    public void doTasks() {
        //System.out.println("==================333");
    	String isUpdateIk;
    	String isUpdateHot;
    	String isUpdateStop;
    	isUpdateIk = pmService.updateIK();
    	isUpdateHot = pmService.updateHotwordIK();
        isUpdateStop = pmService.updateStopwordIK();
        log.info("IK" + isUpdateIk);
        log.info("HotWordIk" + isUpdateHot);
        log.info("StopWordIk" + isUpdateStop);
        //System.out.println("==================1111");
    }
}
