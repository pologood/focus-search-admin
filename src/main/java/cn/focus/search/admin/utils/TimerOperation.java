package cn.focus.search.admin.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.focus.search.admin.config.LastTime;
import common.Logger;

public class TimerOperation {

	private static Logger log = Logger.getLogger(TimerOperation.class);
//	private static ParticipleManagerService participleManagerService;
	
	
	public static void doTasks()
	{
		String nowTime = "";
		Date currentTime = new Date();
		SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		nowTime = form.format(currentTime);		
		log.info("定时器启动" + nowTime);
		int num = LastTime.setlTime();
		LastTime.setStopwordlTime();
		LastTime.setHotwordlTime();
/*		String isSuccess = participleManagerService.updateIK();
		String isStopSuccess = participleManagerService.updateStopwordIK();
		String isHotSuccess = participleManagerService.updateHotwordIK();*/
		log.info("!!!!!!!!!!!!!!"+num);
		/*log.info(isSuccess);
		log.info(isStopSuccess);
		log.info(isHotSuccess);*/
	}
}
