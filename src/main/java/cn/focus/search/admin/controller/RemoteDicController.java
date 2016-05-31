package cn.focus.search.admin.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.focus.search.admin.config.LastTime;
import cn.focus.search.admin.service.HotWordService;
import cn.focus.search.admin.service.ParticipleManagerService;
import cn.focus.search.admin.service.StopWordsService;
import cn.focus.search.admin.utils.JSONUtils;

@Controller
@RequestMapping("/")
public class RemoteDicController {
	@Autowired
	private ParticipleManagerService participleManagerService;
	@Autowired
	private LastTime lastTime;
	@Autowired
	private HotWordService hotWordService;
	@Autowired
	private StopWordsService stopWordsService;
	
	@RequestMapping("remote_final_house.dic")
	@ResponseBody
	public String getRemoteFinalHouseDic(HttpServletResponse response,HttpServletRequest request){
	    // first time through - set last modified time to now 
		//楼盘词
		Logger logger = LoggerFactory.getLogger(RemoteDicController.class);
		long last=lastTime.getLastModifiedTime();
 		response.setDateHeader("Last-Modified",last );
 		response.setHeader("ETags", "etagSting");
 		response.setContentType("text/plain;charset=UTF-8");
 		response.setCharacterEncoding("UTF-8");
  		long temp=request.getDateHeader("If-Modified-Since");
  		if (temp==last) return "";
  		else {
  			String str="";
  			return str;
  		}
	}
	
	@RequestMapping("remote_stopword.dic")
	@ResponseBody
	public String getRemoteStopwordDic(@RequestParam(value="type", required=false, defaultValue="1") Integer type,HttpServletResponse response,HttpServletRequest request){
		//停用词
		Logger logger = LoggerFactory.getLogger(RemoteDicController.class);
		long last=lastTime.getLastModifiedTime();
 		response.setDateHeader("Last-Modified", last);
 		response.setHeader("ETags", "etagSting");
 		response.setContentType("text/plain;charset=UTF-8");
 		response.setCharacterEncoding("UTF-8");

  		long temp=request.getDateHeader("If-Modified-Since");
  		if (temp==last) return "";
  		else {
  			
  			logger.info("**ES端开始从APP端拉取拓展停用词词典**");
  			logger.info("ES端时间戳为 "+temp);
  			logger.info("APP端时间戳为"+last);
  			String str=stopWordsService.getStopWordToDicByType(type);
  			logger.info("added stop words");
  			return str;
  			
  		}
		
	}
	
	@RequestMapping("remote_hotword.dic")
	@ResponseBody
	public String getRemoteHotwordDic(@RequestParam(value="type", required=false, defaultValue="1") Integer type,HttpServletResponse response,HttpServletRequest request){
		//热词
		Logger logger = LoggerFactory.getLogger(RemoteDicController.class);
		long last=lastTime.getLastModifiedTime();
 		response.setDateHeader("Last-Modified", last);
 		response.setHeader("ETags", "etagSting");
 		response.setContentType("text/plain;charset=UTF-8");
 		response.setCharacterEncoding("UTF-8");

  		long temp=request.getDateHeader("If-Modified-Since");
  		if (temp==last) return "";
  		else {
  			logger.info("**ES端开始从APP端拉取拓展热词词典**");
  			logger.info("ES端时间戳为 "+temp);
  			logger.info("APP端时间戳为 "+last);
  			String str=hotWordService.getHotWordToDicByType(type);
  			logger.info("added hot words");
  			return str;	
  		}
		
	}
	
	@RequestMapping("reloadRemoteDic")
	@ResponseBody
	public String reloadRemoteDic(HttpServletResponse response){
		Logger logger = LoggerFactory.getLogger(RemoteDicController.class);
		logger.info("start to reload total dic");
		if(lastTime.setLastModifiedTime().equals("success")) return JSONUtils.ok();
  		else return JSONUtils.badResult("failed");
	}
	
	
	

	
}
