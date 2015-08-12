package cn.focus.search.admin.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.focus.search.admin.dao.UserInfoDao;
import cn.focus.search.admin.model.UserInfo;
import cn.focus.search.admin.service.UserManagerService;

@Service
public class UserManagerServiceImpl implements UserManagerService{

	private Logger logger = LoggerFactory.getLogger(ParticipleManagerServiceImpl.class);
	
	@Autowired
	private UserInfoDao userInfoDao;
	
	@Override
	public List<UserInfo> getUserList() {
		List<UserInfo>list = null;
		try {
			list = userInfoDao.getUserList();
		} catch (Exception e) {
			logger.error("获取用户列表数据异常!", e);
		}
		return list;
	}

	@Override
	public int updateUserInfo(UserInfo userInfo) {
		int result = 0;
		try {
			result = userInfoDao.updateUserInfo(userInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("更新用户信息异常!", e);
		}
		return result;
	}

	@Override
	public int addNewUser(UserInfo userInfo) {
		int r = 0;
        try {
            r = userInfoDao.addNewUser(userInfo);
        } catch (Exception e) {
            logger.error("插入新用户数据异常!", e);
        }
        return r;
	}

}
