package cn.focus.search.admin.service;

import cn.focus.search.admin.model.UserInfo;

public interface LoginService {
	
	public String login(String userName,String password)throws Exception;
	
	
	public UserInfo getUser(String userName, String password) throws Exception;

}
