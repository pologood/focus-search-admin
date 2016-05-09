package cn.focus.search.admin.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
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
	public List<Participle> getTotalFinalHouseParticipleList() {
		// TODO Auto-generated method stub
		return sqlSession.selectList("ParticipleDao.getTotalFinalHouseParticipleList");
	}

	@Override
	public int setExported() {
		// TODO Auto-generated method stub
		return sqlSession.update("ParticipleDao.setExported");
		
	}

	@Override
	public List<String> getParticiplesByStatus(int status) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList("ParticipleDao.getParticiplesByStatus", status);
	}

	@Override
	public int delParticipleWordsByPid(int pid) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.delete("ParticipleDao.delParticipleWordsByPid", pid);
	}


	@Override
	public List<Participle> getPorjList(int i, int pageSize) {
		// TODO Auto-generated method stub
		RowBounds rb=new RowBounds(i,pageSize);
		return sqlSession.selectList("ParticipleDao.getPorjList","",rb);
	}

	@Override
	public List<Participle> getPorjListSearch(String groupId, String projName, int i, int pageSize) {
		// TODO Auto-generated method stub
		RowBounds rb=new RowBounds(i,pageSize);
		Participle temp=new Participle();
		if(!groupId.equals("")) temp.setPid(Integer.valueOf(groupId));
		temp.setName(projName);
		return sqlSession.selectList("ParticipleDao.getPorjListSearch", temp,rb);
	}

	@Override
	public int getPorjListNum() {
		// TODO Auto-generated method stub		
		return sqlSession.selectOne("ParticipleDao.getPorjListNum");
	}

	@Override
	public int getPorjListSearchNum(String groupId, String projName) {
		// TODO Auto-generated method stub
		Participle temp=new Participle();
		if(!groupId.equals("")) temp.setPid(Integer.valueOf(groupId));
		temp.setName(projName);
		return sqlSession.selectOne("ParticipleDao.getPorjListSearchNum",temp);
	}

	/* (non-Javadoc)
	 * @see cn.focus.search.admin.dao.ParticipleDao#getProjNew()
	 */
	@Override
	public List<Participle> getProjListNew() {
		// TODO Auto-generated method stub
		return sqlSession.selectList("ParticipleDao.getProjListNew");
	}

}
