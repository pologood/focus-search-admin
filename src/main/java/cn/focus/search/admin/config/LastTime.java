package cn.focus.search.admin.config;

import java.util.Calendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LastTime {
	public static long final_house_lTime;
	public static long stop_lTime;
	
	public static int setlTime(){
		int flag=0;
		Logger logger=LoggerFactory.getLogger(LastTime.class);
		
 		try {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MILLISECOND, 0);
			long now=cal.getTime().getTime();
			final_house_lTime = now;
			stop_lTime=now;
			flag=1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("set lastTime failed!", e);
			e.printStackTrace();
		}
 		
 		return flag;
	} 

}
