package cn.focus.search.admin.config;

import java.util.Calendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.focus.search.admin.service.RedisService;

@Component
public class LastTime {

	Logger logger=LoggerFactory.getLogger(LastTime.class);
	
	@Autowired
	public RedisService redisService;
	public String key="focus-search-admin.Last-Modified";
	
	public long getNow(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime().getTime();
	}
	
	//将所有远程词库最后修改时间设置为当前时间.
	public String setLastModifiedTime(){
	
			try {
				long now=getNow();
				redisService.setRedis(key, String.valueOf(now));
				logger.info("Last-Modified is setted : "+now);
				return "success";
			} catch (Exception e) {
				logger.error(e.getMessage());
				return "failed";			
			}
 			
	}
	public long getLastModifiedTime(){
		//key存在则返回value值，否则将当前时间写入该key，并且返回当前时间。
		if(redisService.exists(key)==0){
			setLastModifiedTime();
			return getNow();
		}else {
			return Long.parseLong(redisService.getRedis(key));
		}
	}
}
