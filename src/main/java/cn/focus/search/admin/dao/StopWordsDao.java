package cn.focus.search.admin.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import cn.focus.search.admin.model.StopWords;

public interface StopWordsDao {

    /**
     *插入停止词数据
     * @param stopword
     * @return
     * @throws Exception
     */
    public int insertStopWords(StopWords stopword)throws Exception;

    /**
     *批量获取停止词数据
     * @param 
     * @return
     * @throws Exception
     */
    public List<StopWords> getStopWordsList(RowBounds rowBounds)throws Exception;
    
    public int getTotalNum()throws Exception;
    
    /**
     *根据停止词名获取停止词数据
     * @param wordName
     * @return
     * @throws Exception
     */
    public List<StopWords> getStopWordsListByName(String wordName)throws Exception;

    /**
     *根据日期获取需要添加到词库的停止词数据
     * @param wordName
     * @return
     * @throws Exception
     */
	public List<StopWords> getDayStopWordsList()throws Exception;
	
	/**
     *根据id删除停止词数据
     * @param id
     * @return
     * @throws Exception
     */
	public int delStopWordsById(int id)throws Exception;

	/**
     *根据status获取停止词数据（只要名称）
     * @param status == 1
     * @return
     * @throws Exception
     */
	public List<String> getStopWordnameByStatus(int status)throws Exception;
	
	public List<StopWords> getTotalStopWordList();

	/**
     *将status置0
     * @param status
     * @return
     * @throws Exception
     */
	public int setExported();

	/**
	 * @author qingyuanxue@sohu-inc.com  
	 * @date 2016年5月26日下午3:39:40
	 * @description 
	 */
	public List<String> getStopWordToDicByType(Integer type);
}
