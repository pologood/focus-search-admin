package cn.focus.search.admin.service;

import java.util.List;
import cn.focus.search.admin.model.UserInfo;

public interface UserManagerService {

	public List<UserInfo> getUserList();
	
	public int updateUserInfo(UserInfo userInfo);
	
	public int addNewUser(UserInfo userInfo);
}
