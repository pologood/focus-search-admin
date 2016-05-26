package cn.focus.search.admin.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;

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
	public List<StopWords> getStopWordsList(RowBounds rowBounds);
	
	public int getTotalNum()throws Exception;
	
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
	
	/**
     *根据status获取停止词数据（只要名称）
     * @param status == 1
     * @return
     * @throws Exception
     */
	public List<String> getStopWordnameByStatus(int status);
	
	/**
     *将status=1停止词（名称）导出到文本
     * @param status == 1
     * @return
     * @throws Exception
     */
	public boolean exportStop(String path, String fileName, List<String> list)throws IOException;
	
	public int setExported()throws Exception;

	/**
	 * @author qingyuanxue@sohu-inc.com  
	 * @date 2016年5月26日下午3:36:51
	 * @description 
	 */
	public String getStopWordToDicByType(Integer type);
}
