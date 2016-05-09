package cn.focus.search.admin.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import cn.focus.search.admin.model.Participle;


public interface ParticipleDao {

	/**
     * 更新分词数据
     * @param participle
     * @return
     * @throws Exception
     */
    public int updateParticiple(Participle participle)throws Exception;




    /**
     *根据status获取分词数据（只要名称）
     * @param status == 1
     * @return
     * @throws Exception
     */
    public List<String> getParticiplesByStatus(int status)throws Exception;

	public List<Participle> getTotalFinalHouseParticipleList();

	/**
     *将status置0
     * @param status
     * @return
     * @throws Exception
     */
	public int setExported();

    /**
     *根据id删除分词
     * @param id
     * @return
     * @throws Exception
     */
    public int delParticipleWordsByPid(int pid)throws Exception;

	public List<Participle> getPorjList(int i, int pageSize); 

	public List<Participle> getPorjListSearch(String groupId, String projName, int i, int pageSize);

	public int getPorjListNum();

	public int getPorjListSearchNum(String groupId, String projName);


	/**
	 * @author qingyuanxue@sohu-inc.com  
	 * @date 2016年5月9日下午8:37:54
	 * @description 
	 */
	public List<Participle> getProjListNew();

}
