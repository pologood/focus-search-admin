package cn.focus.search.admin.dao;

import java.util.List;

import cn.focus.search.admin.model.UserInfo;


public interface UserInfoDao {
	
	public int updateUserToken(String userName,String accessToken)throws Exception;
	
	public UserInfo getUserInfo(String userName,String password)throws Exception;
	
	public List<UserInfo> getUserList()throws Exception;
	
	public int updateUserInfo(UserInfo userInfo)throws Exception;
	
    public int addNewUser(UserInfo userInfo)throws Exception;
}
