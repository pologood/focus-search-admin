package cn.focus.search.admin.utils;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.focus.search.admin.service.ParticipleManagerService;

public class TimerTask implements Job{

	private static Logger log = LoggerFactory.getLogger(TimerTask.class);
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		try
		{
			TimerOperation.doTasks();
		}catch(Exception ex)
		{
			log.info(ex.getMessage());
		}
	}
}
