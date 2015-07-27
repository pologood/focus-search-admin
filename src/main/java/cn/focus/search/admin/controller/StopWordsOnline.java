package cn.focus.search.admin.controller;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.focus.search.admin.service.impl.ParticipleManagerServiceImpl;

@Controller
@RequestMapping("/")
public class StopWordsOnline {
	
	@RequestMapping("test.dic")
	@ResponseBody
	public String getWords(HttpServletResponse response){
	    // first time through - set last modified time to now 
		Logger logger = LoggerFactory.getLogger(StopWordsOnline.class);
 		Calendar cal = Calendar.getInstance();
 		cal.set(Calendar.MILLISECOND, 0);
 		cal.add(Calendar.DATE, -1);
 		Date lastModified = cal.getTime();
 		response.setDateHeader("Last-Modified", lastModified.getTime());
 		response.setHeader("ETags", "etagSting");
 		response.setContentType("text/plain;charset=UTF-8");
 		response.setCharacterEncoding("UTF-8");

  		logger.info("Writing response body content");
		return "够";
		
	}
	

}
