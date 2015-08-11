package cn.focus.search.admin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.focus.search.admin.model.UserInfo;
import cn.focus.search.admin.service.LoginService;
import cn.focus.search.admin.service.UserManagerService;
import cn.focus.search.admin.utils.JSONUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;

@Controller
@RequestMapping("/user/um")
public class UserManagerController {

	private Logger logger = LoggerFactory.getLogger(ParticipleManagerController.class);
	
	@Autowired
	private UserManagerService userManagerService;
	
	@Autowired
	private LoginService loginService;
	 
	@RequestMapping(value="userList",method =RequestMethod.GET)
	public ModelAndView showUserListPage(HttpServletRequest request){
		try{
			ModelAndView mv = new ModelAndView("user_list"); 
			UserInfo User = (UserInfo)request.getSession().getAttribute("user");
			String cUserName = User.getUserName();
			mv.addObject("cUserName", cUserName);
			return mv;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			ModelAndView mv = new ModelAndView("error"); 
			return mv;
		}
	}
	
	/***
	 * 获取全部用户信息列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="showAllUsers",method=RequestMethod.POST)
	@ResponseBody
	public String showAllUsers(HttpServletRequest request){
		try{
			List<UserInfo> list = userManagerService.getUserList();
			int size = list.size();			
			System.out.println("UserList的大小是"+size);
			JSONArray jsArray = new JSONArray();
			jsArray.addAll(list);
			System.out.println(JSON.toJSONString(jsArray,SerializerFeature.WriteDateUseDateFormat));
			return JSON.toJSONString(jsArray,SerializerFeature.WriteDateUseDateFormat);
						
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			return JSONUtils.badResult("failed");
		}
	}
	
	/**
	 * 修改用户信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="updateUser",method=RequestMethod.POST)
	@ResponseBody
	public String updateUserInfo(HttpServletRequest request){
		try{
			String sId = request.getParameter("id");
			System.out.println("！用户ID："+sId);
			if(StringUtils.isBlank(sId)){
				return JSONUtils.badResult("failed");
			}
			int id = Integer.parseInt(sId);
			String userName = request.getParameter("name");
			String accessToken = request.getParameter("accessToken");
			int type = Integer.parseInt(request.getParameter("type"));
			int status = 1;
			String pw = new String();
//			String passwordMd5 = DigestUtils.md5Hex(password);
			if(type == 0){//逻辑删除
				status = 0;
				pw = request.getParameter("pw");
			}
			else if(type == 1){//重置
				pw = DigestUtils.md5Hex("123");
			}
			else{//修改
				pw=DigestUtils.md5Hex(request.getParameter("pw"));
			}

			String createTime = request.getParameter("createTime");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 	Date cTime = sdf.parse(createTime);
			UserInfo userInfo = new UserInfo();
			userInfo.setId(id);
			userInfo.setUserName(userName);
			userInfo.setPassword(pw);
			userInfo.setAccessToken(accessToken);
			userInfo.setStatus(status);
			userInfo.setCreateTime(cTime);
			
			int result = userManagerService.updateUserInfo(userInfo);
			System.out.println("！result："+result);
			if(result<1){//更新失败
				System.out.println("更新失败！result："+result);
				return JSONUtils.badResult("failed");
			}
			return JSONUtils.ok();
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONUtils.badResult("failed");
		}
	}
	
	/**
	 * 用户自行修改密码
	 * @param request
	 * @return
	 */
	@RequestMapping(value="updatePw",method=RequestMethod.POST)
	@ResponseBody
	public String updateUserPw(HttpServletRequest request){
		try{
			String userName = request.getParameter("name");
			String oPw = request.getParameter("oPw");
			String nPw = request.getParameter("nPw");
			System.out.println("userName:"+userName+" nPw: "+nPw);
			
			if(StringUtils.isBlank(userName) || StringUtils.isBlank(oPw) || StringUtils.isBlank(nPw)){
				return JSONUtils.badResult("failed");
			}
			String nPwMd5 = DigestUtils.md5Hex(nPw);
			UserInfo cUserInfo = loginService.getUser(userName, oPw);
			if(cUserInfo == null){
				return JSONUtils.badResult("failed");
			}
			
			UserInfo userInfo = new UserInfo();
			userInfo.setId(cUserInfo.getId());
			userInfo.setUserName(cUserInfo.getUserName());
			userInfo.setPassword(nPwMd5);
			userInfo.setAccessToken(cUserInfo.getAccessToken());
			userInfo.setStatus(cUserInfo.getStatus());
			userInfo.setCreateTime(cUserInfo.getCreateTime());
			
			int result = userManagerService.updateUserInfo(userInfo);
			System.out.println("！result："+result);
			if(result<1){//更新失败
				System.out.println("更新失败！result："+result);
				return JSONUtils.badResult("failed");
			}
			return JSONUtils.ok();			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONUtils.badResult("failed");
		}
	}
}
