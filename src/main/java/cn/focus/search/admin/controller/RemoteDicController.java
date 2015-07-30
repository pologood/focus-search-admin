package cn.focus.search.admin.controller;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.focus.search.admin.config.LastTime;
import cn.focus.search.admin.service.ParticipleManagerService;

@Controller
@RequestMapping("/")
public class RemoteDicController {
	@Autowired
	private ParticipleManagerService participleManagerService;
	
	@RequestMapping("remote_final_house.dic")
	@ResponseBody
	public String getRemoteFinalHouseDic(HttpServletResponse response){
	    // first time through - set last modified time to now 
		Logger logger = LoggerFactory.getLogger(RemoteDicController.class);
 		response.setDateHeader("Last-Modified", LastTime.final_house_lTime);
 		response.setHeader("ETags", "etagSting");
 		response.setContentType("text/plain;charset=UTF-8");
 		response.setCharacterEncoding("UTF-8");

  		logger.info("Writing dic to  remote_final_house.dic");
		return participleManagerService.getRemoteFinalHouseWord();
		
	}

}