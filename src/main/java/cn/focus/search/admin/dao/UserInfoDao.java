package cn.focus.search.admin.dao;

import cn.focus.search.admin.model.UserInfo;


public interface UserInfoDao {
	
	public int updateUserToken(String userName,String accessToken)throws Exception;
	
	
	public UserInfo getUserInfo(String userName,String password)throws Exception;
	
	
}
