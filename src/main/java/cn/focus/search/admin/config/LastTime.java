package cn.focus.search.admin.config;

import java.util.Calendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LastTime {
	public static long final_house_lTime;
	public static long hotword_lTime;
	public static long stopword_lTime;
	
	//修改finalhouse时间，成功返回1，失败返回0.
	public static int setlTime(){
		int flag=0;
		Logger logger=LoggerFactory.getLogger(LastTime.class);
		
 		try {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MILLISECOND, 0);
			long now=cal.getTime().getTime();
			final_house_lTime = now;
			flag=1;
			logger.info("final_house_lTime is setted to "+Long.toString(now));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("set lastTime failed!", e);
			e.printStackTrace();
		}
 		
 		return flag;
	} 
	
	
	//修改停用词最后修改时间，成功返回1，失败返回0.
	public static int setStopwordlTime(){
		int flag=0;
		Logger logger=LoggerFactory.getLogger(LastTime.class);
		
 		try {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MILLISECOND, 0);
			long now=cal.getTime().getTime();
			stopword_lTime = now;
			flag=1;
			logger.info("stopword_lTime is setted to "+Long.toString(now));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("set stopword lastTime failed!", e);
			e.printStackTrace();
		}
 		
 		return flag;
	} 
	//修改临时加的热词最后修改时间，成功返回1，失败返回0.
	public static int setHotwordlTime(){
		int flag=0;
		Logger logger=LoggerFactory.getLogger(LastTime.class);
		
 		try {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MILLISECOND, 0);
			long now=cal.getTime().getTime();
			hotword_lTime = now;
			flag=1;
			logger.info("hotword_lTime is setted to "+Long.toString(now));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("set hot word lastTime failed!", e);
			e.printStackTrace();
		}
 		
 		return flag;
	}


	public static long getFinal_house_lTime() {
		return final_house_lTime;
	}


	public static long getHotword_lTime() {
		return hotword_lTime;
	}


	public static long getStopword_lTime() {
		return stopword_lTime;
	} 
	

}
