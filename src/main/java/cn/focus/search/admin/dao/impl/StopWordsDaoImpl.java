package cn.focus.search.admin.dao.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.focus.search.admin.dao.StopWordsDao;
import cn.focus.search.admin.model.StopWords;

@Repository
public class StopWordsDaoImpl implements StopWordsDao{

    @Autowired
    private SqlSessionTemplate sqlSession;
    
    @Override
    public int insertStopWords(StopWords stopword)throws Exception
    {
    	return sqlSession.insert("StopWordsDao.insertStopWords", stopword);
    }

    @Override
    public List<StopWords> getStopWordsList()throws Exception
    {
    	return sqlSession.selectList("StopWordsDao.getStopWordsList");
    }
    
    @Override
    public List<StopWords> getStopWordsListByName(String wordName)throws Exception
    {
    	return sqlSession.selectList("StopWordsDao.getStopWordsListByName",wordName);
    }

	@Override
	public List<StopWords> getDayStopWordsList() {
		// TODO Auto-generated method stub
		return sqlSession.selectList("StopWordsDao.getDayStopWordsList");
	}

	@Override
	public int delStopWordsById(int id) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.delete("StopWordsDao.delStopWordsById", id);
	}
}
