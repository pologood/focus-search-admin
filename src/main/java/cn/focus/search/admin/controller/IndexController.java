package cn.focus.search.admin.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.focus.search.admin.model.UserInfo;
import cn.focus.search.admin.service.LoginService;
import cn.focus.search.admin.service.ParticipleManagerService;
import cn.focus.search.admin.utils.JSONUtils;

import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping("/search/admin")
public class IndexController {
	
	private Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	private ParticipleManagerService participleManagerService;
	
	@Autowired
	private LoginService loginService;
	
	@RequestMapping("home")
	public String home(Model model,HttpServletRequest request){
		try{
			UserInfo user = (UserInfo)request.getSession().getAttribute("user");
			if(user == null){
				return "error";
			}
			
			model.addAttribute("userName", user.getUserName());
			return "home";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "error";
		}
	}
	
	
	@RequestMapping(value="login" , method = {RequestMethod.POST,RequestMethod.GET })
	public String login(HttpServletRequest request,HttpServletResponse response)throws JsonProcessingException{
		try{
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");
			
//			logger.info("userName:"+userInfo.getUserName()+" passWord:"+userInfo.getPassword());
			System.out.println("userName:"+userName+" password: "+password);
			
			if(StringUtils.isBlank(userName) || StringUtils.isBlank(password)){
				return "login";
			}
			
			UserInfo userInfo = loginService.getUser(userName, password);
			if(userInfo == null||userInfo.getStatus() == 0){
				return "login";
			}
			
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(3600); //一个小时过期
			session.setAttribute("user", userInfo);
			
			System.out.println(userInfo);
			System.out.println("redirect:/search/admin/home");
			
			return "redirect:/search/admin/home";
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "error";
		}
	}
	
	
	@RequestMapping("tologin")
	public String toLogin(){
		try{
			return "login";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "error";
		}
	}
	
	
	@RequestMapping("logout")
	public String logOut(HttpServletRequest request){
		try{
		   request.getSession().removeAttribute("user");
			
           return "redirect:/search/admin/login";

		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "error";
		}
	}
	
	
	private HttpServletResponse genCookie(HttpServletResponse response,
			String userName, String accessToken) {

		Cookie cookie = new Cookie("userName", userName);
		cookie.setMaxAge(-1); // 设置为负值，关闭浏览器就失效

		Cookie tokenCookie = new Cookie("token", accessToken);
		tokenCookie.setMaxAge(-1); // 设置为负值，关闭浏览器就失效

		response.addCookie(cookie);
		response.addCookie(tokenCookie);

		return response;
	}
	
	@RequestMapping("test")
	@ResponseBody
	public String test(){
		try{
			participleManagerService.getIkWords(1,10,"北京城建·世华泊郡"+"\n"+"首创派尚国际");
			return JSONUtils.ok();
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONUtils.badResult("failed");
		}
	}
	
	public static void main(String args[]){
		//admin ,以下是用户的登陆密码
		System.out.println(DigestUtils.md5Hex("Search!@#"));
		System.out.println(DigestUtils.md5Hex("123"));
	}

}
