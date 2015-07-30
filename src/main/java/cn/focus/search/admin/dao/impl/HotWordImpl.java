package cn.focus.search.admin.dao.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import cn.focus.search.admin.dao.HotWordDao;
import cn.focus.search.admin.model.HotWord;


public class HotWordImpl implements HotWordDao{
    @Autowired
    private SqlSessionTemplate sqlSession;
    
	@Override
	public List<HotWord> getDayHotWordList() {
		// TODO Auto-generated method stub
		return sqlSession.selectList("HotWordDao.getDayHotWordList");
	}

}
