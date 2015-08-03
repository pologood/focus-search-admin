package cn.focus.search.admin.dao;

import java.util.List;

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
    public List<StopWords> getStopWordsList()throws Exception;
    
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
     * @param wordName
     * @return
     * @throws Exception
     */
	public int delStopWordsById(int id)throws Exception;
}
