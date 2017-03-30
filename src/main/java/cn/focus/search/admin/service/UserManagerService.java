package cn.focus.search.admin.service;

import cn.focus.search.admin.model.UserInfo;

import java.util.List;

public interface UserManagerService {

	public List<UserInfo> getUserList();
	
	public int updateUserInfo(UserInfo userInfo);
	
	public int addNewUser(UserInfo userInfo);
}
