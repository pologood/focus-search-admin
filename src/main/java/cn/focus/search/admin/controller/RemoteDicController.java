package cn.focus.search.admin.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.focus.search.admin.config.LastTime;
import cn.focus.search.admin.service.ParticipleManagerService;
import cn.focus.search.admin.utils.JSONUtils;

@Controller
@RequestMapping("/")
public class RemoteDicController {
	@Autowired
	private ParticipleManagerService participleManagerService;
	
	@RequestMapping("remote_final_house.dic")
	@ResponseBody
	public String getRemoteFinalHouseDic(HttpServletResponse response,HttpServletRequest request){
	    // first time through - set last modified time to now 
		Logger logger = LoggerFactory.getLogger(RemoteDicController.class);
 		response.setDateHeader("Last-Modified", LastTime.final_house_lTime);
 		response.setHeader("ETags", "etagSting");
 		response.setContentType("text/plain;charset=UTF-8");
 		response.setCharacterEncoding("UTF-8");
 		
  		long temp=request.getDateHeader("If-Modified-Since");
  		if (temp==LastTime.final_house_lTime) return "";
  		else {
  			String str=participleManagerService.getRemoteFinalHouseWord();
  			logger.info("adding houseword words: "+"\n"+str);
  			logger.info("es server house word time: "+temp);
  			logger.info("client house word time: "+LastTime.final_house_lTime);
  			return str;
  		}
  		//return participleManagerService.getRemoteFinalHouseWord();
		
	}
	
	@RequestMapping("remote_stopword.dic")
	@ResponseBody
	public String getRemoteStopwordDic(HttpServletResponse response,HttpServletRequest request){
		Logger logger = LoggerFactory.getLogger(RemoteDicController.class);
 		response.setDateHeader("Last-Modified", LastTime.stopword_lTime);
 		response.setHeader("ETags", "etagSting");
 		response.setContentType("text/plain;charset=UTF-8");
 		response.setCharacterEncoding("UTF-8");

  		long temp=request.getDateHeader("If-Modified-Since");
  		if (temp==LastTime.stopword_lTime) return "";
  		else {
  			String str=participleManagerService.getRemoteStopword();
  			logger.info("adding stopword words: "+"\n"+str);
  			logger.info("es server stop word time: "+temp);
  			logger.info("client stop word time: "+LastTime.stopword_lTime);
  			return str;
  		}
		
	}
	
	@RequestMapping("remote_hotword.dic")
	@ResponseBody
	public String getRemoteHotwordDic(HttpServletResponse response,HttpServletRequest request){
		Logger logger = LoggerFactory.getLogger(RemoteDicController.class);
 		response.setDateHeader("Last-Modified", LastTime.hotword_lTime);
 		response.setHeader("ETags", "etagSting");
 		response.setContentType("text/plain;charset=UTF-8");
 		response.setCharacterEncoding("UTF-8");

  		long temp=request.getDateHeader("If-Modified-Since");
  		if (temp==LastTime.hotword_lTime) return "";
  		else {
  			String str=participleManagerService.getRemoteHotword();
  			logger.info("adding hot words: "+str);
  			logger.info("es server hot word time: "+"\n"+temp);
  			logger.info("client hot word time: "+LastTime.hotword_lTime);
  			return str;	
  		}
		
	}
	
	@RequestMapping("reloadRemoteDic")
	@ResponseBody
	public String reloadRemoteDic(HttpServletResponse response){
		Logger logger = LoggerFactory.getLogger(RemoteDicController.class);
		logger.info("start to reload total dic");
		if(participleManagerService.reloadRemoteDic()) return JSONUtils.ok();
  		else return JSONUtils.badResult("failed");
	}
	
	
	

	
}
