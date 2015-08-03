package cn.focus.search.admin.service;

import java.util.List;

import cn.focus.search.admin.model.StopWords;

public interface StopWordsService {

	/**
     *插入停止词数据
     * @param StopWords
     * @return
     * @throws Exception
     */
	public int insertStopWords(StopWords stopword);
	
	/**
     *获取停止词数据
     * @param StopWords
     * @return
     * @throws Exception
     */
	public List<StopWords> getStopWordsList();
	
    /**
     *根据停止词名获取停止词数据
     * @param wordName
     * @return
     * @throws Exception
     */
    public List<StopWords> getStopWordsListByName(String wordName);
    
    /**
     *根据日期获取需要添加到词库的停止词数据
     * @param wordName
     * @return
     * @throws Exception
     */
	public List<StopWords> getDayStopWordsList();
	
	/**
     *根据id删除停止词数据
     * @param wordName
     * @return
     * @throws Exception
     */
	public int delStopWordsById(int id);
}