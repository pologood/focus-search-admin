package cn.focus.search.admin.dao.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.focus.search.admin.dao.HotWordDao;
import cn.focus.search.admin.model.HotWord;

@Repository
public class HotWordDaoImpl implements HotWordDao{
    @Autowired
    private SqlSessionTemplate sqlSession;
    
	@Override
	public List<HotWord> getDayHotWordList() {
		// TODO Auto-generated method stub
		return sqlSession.selectList("HotWordDao.getDayHotWordList");
	}
	
	@Override
    public int insertHotWord(HotWord hotWord)throws Exception
    {
		return sqlSession.insert("HotWordDao.insertHotWord", hotWord);
    }

    @Override
    public List<HotWord> getHotWordList()throws Exception
    {
    	return sqlSession.selectList("HotWordDao.getHotWordList");
    }
    
    @Override
    public List<HotWord> getHotWordListByName(String wordName)throws Exception
    {
    	return sqlSession.selectList("HotWordDao.getHotWordListByName", wordName);
    }

	@Override
	public int delHotWordById(int id) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.delete("HotWordDao.delHotWordById", id);
	}
}
