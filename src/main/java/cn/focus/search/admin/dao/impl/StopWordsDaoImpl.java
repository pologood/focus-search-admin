package cn.focus.search.admin.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
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
    public List<StopWords> getStopWordsList(RowBounds rowBounds)throws Exception
    {
    	return sqlSession.selectList("StopWordsDao.getStopWordsList", "", rowBounds);
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

	@Override
	public List<StopWords> getTotalStopWordList() {
		// TODO Auto-generated method stub
		return sqlSession.selectList("StopWordsDao.getTotalStopWordsList");
	}

	@Override
	public int setExported() {
		// TODO Auto-generated method stub
		return sqlSession.update("StopWordsDao.setExported");
	}

	@Override
	public List<String> getStopWordnameByStatus(int status) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList("StopWordsDao.getStopWordnameByStatus", status);
	}

	@Override
	public int getTotalNum() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("StopWordsDao.getTotalNum");
	}
}
