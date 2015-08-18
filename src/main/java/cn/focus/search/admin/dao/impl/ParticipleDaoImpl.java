package cn.focus.search.admin.dao.impl;

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
	public List<Participle> getParticipleList(int status, RowBounds rowBounds) throws Exception {
		return sqlSession.selectList("ParticipleDao.getParticipleList",status,rowBounds);
	}
	@Override
	public List<Participle> getDayFinalHouseParticipleList() throws Exception {
		return sqlSession.selectList("ParticipleDao.getDayFinalHouseParticipleList");
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
	public int getTotalNum(int status) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("ParticipleDao.getTotalNum", status);
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
	public int updateParticiples(Integer pid, String manualWords, String userName) {
		// TODO Auto-generated method stub
		Participle temp=new Participle();
		temp.setPid(pid);
		temp.setParticiples(manualWords);
		temp.setEditor(userName);
		temp.setUpdateTime(new Date());
		
		return sqlSession.update("ParticipleDao.updateParticiples", temp);
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

}
