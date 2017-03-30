package cn.focus.search.admin.service.impl;

import cn.focus.search.admin.dao.UserInfoDao;
import cn.focus.search.admin.model.UserInfo;
import cn.focus.search.admin.service.LoginService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserInfoDao userInfoDao;

	public String login(String userName, String password) throws Exception {
		System.out.println("userName:" + userName + " password:" + password);
		String passwordMd5 = DigestUtils.md5Hex(password);

		UserInfo userInfo = userInfoDao.getUserInfo(userName, passwordMd5);
		if (userInfo == null) {
			return null;
		}

		UUID uuid = UUID.randomUUID();
		String uuidMd5 = DigestUtils.md5Hex(userName+uuid.toString());

		userInfoDao.updateUserToken(userName, uuidMd5);

		return uuidMd5;
	}
	
	public UserInfo getUser(String userName, String password) throws Exception {
		System.out.println("userName:" + userName + " password:" + password);
		String passwordMd5 = DigestUtils.md5Hex(password);

		UserInfo userInfo = userInfoDao.getUserInfo(userName, passwordMd5);
		if (userInfo == null) {
			return null;
		}
		return userInfo;
	}


}
