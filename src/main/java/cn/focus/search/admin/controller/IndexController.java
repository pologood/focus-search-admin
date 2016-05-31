package cn.focus.search.admin.controller;

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
import cn.focus.search.admin.model.UserInfo;
import cn.focus.search.admin.service.LoginService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping("/search/admin")
public class IndexController {
	//首页相关，登录相关。
	
	private Logger logger = LoggerFactory.getLogger(IndexController.class);
	
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
			
			System.out.println("userName:"+userName+" password: "+password);
			
			if(StringUtils.isBlank(userName) || StringUtils.isBlank(password)){
				return "login";
			}
			
			UserInfo userInfo = loginService.getUser(userName, password);
			if(userInfo == null||userInfo.getStatus() == 0){
				request.setAttribute("flag","用户名或者密码错误！");
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
	
	
	public static void main(String args[]){
		//admin ,以下是用户的登陆密码
		System.out.println(DigestUtils.md5Hex("Search!@#"));
		System.out.println(DigestUtils.md5Hex("123"));
	}

}
