package cn.focus.search.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoginController {
	
	private Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping("login")
	public String toLogin(){
		try{
			return "login";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "error";
		}
	}
}
