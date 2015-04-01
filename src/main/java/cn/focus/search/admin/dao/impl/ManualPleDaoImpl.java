package cn.focus.search.admin.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.focus.search.admin.dao.ManualPleDao;
import cn.focus.search.admin.model.ManualParticiple;
@Repository
public class ManualPleDaoImpl implements ManualPleDao{
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public int insertManualWords(ManualParticiple manualParticiple)throws Exception{
		return sqlSessionTemplate.insert("ManualPleDao.insertManualWords", manualParticiple);
	}

}
