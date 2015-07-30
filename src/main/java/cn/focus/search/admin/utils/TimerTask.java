package cn.focus.search.admin.utils;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.focus.search.admin.service.ParticipleManagerService;

public class TimerTask implements Job{

	private static Logger log = Logger.getLogger(TimerTask.class);
	private ParticipleManagerService participleManagerService;
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		try
		{
			String isSuccess = participleManagerService.updateIK();
			log.info(isSuccess);
			TimerOperation.getDate();
		}catch(Exception ex)
		{
			log.info(ex.getMessage());
		}
	}
}
