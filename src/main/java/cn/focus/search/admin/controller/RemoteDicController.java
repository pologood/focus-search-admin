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
  			logger.info("*************"+request.getRemoteAddr()+"*****************");
  			logger.info("es server house If-Modified-Since: "+temp);
  			logger.info("sce client house Last-Modified: "+LastTime.final_house_lTime);
  			logger.info("adding houseword words: "+"\n"+str);
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
  			logger.info("*************"+request.getRemoteAddr()+"*****************");
  			logger.info("es server stop If-Modified-Since time: "+temp);
  			logger.info("sce client stop Last-Modified: "+LastTime.stopword_lTime);
  			logger.info("adding stopword words: "+"\n"+str);
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
  			logger.info("*************"+request.getRemoteAddr()+"*****************");
  			logger.info("es server hot If-Modified-Since: "+temp);
  			logger.info("sce client hot Last-Modified: "+LastTime.hotword_lTime);
  			logger.info("adding hot words: "+"\n"+str);
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
