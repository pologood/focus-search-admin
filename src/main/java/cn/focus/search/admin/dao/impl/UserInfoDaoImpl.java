package cn.focus.search.admin.dao.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.focus.search.admin.dao.UserInfoDao;
import cn.focus.search.admin.model.UserInfo;
@Repository
public class UserInfoDaoImpl implements UserInfoDao{
	 
	@Autowired
	private SqlSessionTemplate sqlSession;
	 
	@Override
	public int updateUserToken(String userName,String accessToken)throws Exception{
		
		UserInfo userInfo = new UserInfo();
		userInfo.setAccessToken(accessToken);
		userInfo.setUserName(userName);
		
		return sqlSession.update("UserInfoDao.updateUserToken", userInfo);
	}
	
	
	@Override
	public UserInfo getUserInfo(String userName,String password)throws Exception{
		
		UserInfo userInfo = new UserInfo();
		userInfo.setUserName(userName);
		userInfo.setPassword(password);
		
		return sqlSession.selectOne("UserInfoDao.getUserInfo", userInfo);
	}


	@Override
	public List<UserInfo> getUserList() throws Exception{
		return sqlSession.selectList("UserInfoDao.getUserList");
	}


	@Override
	public int updateUserInfo(UserInfo userInfo) throws Exception {
		return sqlSession.update("UserInfoDao.updateUserInfo",userInfo);
	}

}
