package cn.focus.search.admin.dao.impl;

import cn.focus.search.admin.dao.HotWordDao;
import cn.focus.search.admin.model.HotWord;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public List<HotWord> getHotWordList(RowBounds rowBounds)throws Exception
    {
    	return sqlSession.selectList("HotWordDao.getHotWordList", "", rowBounds);
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

	@Override
	public List<HotWord> getTotalHotWordList() {
		// TODO Auto-generated method stub
		return sqlSession.selectList("HotWordDao.getTotalHotWordList");
	}

	@Override
	public int setExported(int type) {
		// TODO Auto-generated method stub
		return sqlSession.update("HotWordDao.setExported",type);
		
	}

	@Override
	public int getTotalNum() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("HotWordDao.getTotalNum");
	}

	/* (non-Javadoc)
	 * @see cn.focus.search.admin.dao.HotWordDao#getHotWordToDicByType(java.lang.Integer)
	 */
	@Override
	public List<String> getHotWordToDicByType(Integer type) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("HotWordDao.getHotWordToDicByType",type);
	}
}
