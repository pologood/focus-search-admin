package cn.focus.search.admin.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import common.Logger;

public class TimerOperation {

	private static Logger log = Logger.getLogger(TimerOperation.class);
	public static void getDate()
	{
		String nowTime = "";
		Date currentTime = new Date();
		SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		nowTime = form.format(currentTime);
		log.info("定时器启动" + nowTime);
	}
}
