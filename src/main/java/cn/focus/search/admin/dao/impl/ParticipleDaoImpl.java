package cn.focus.search.admin.dao.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.focus.search.admin.dao.ParticipleDao;
import cn.focus.search.admin.model.Participle;

@Repository
public class ParticipleDaoImpl implements ParticipleDao {

	@Autowired
    private SqlSessionTemplate sqlSession;
	
	@Override
	public int updateParticiple(Participle participle) throws Exception {
		return sqlSession.update("ParticipleDao.updateParticiple",participle);	
	}

	@Override
	public List<Participle> getParticipleList(String participles,int status) throws Exception {
		return sqlSession.selectList("ParticipleDao.getParticipleList",participles);
	}

}
