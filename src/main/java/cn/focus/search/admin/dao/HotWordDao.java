/**
 * 
 */
package cn.focus.search.admin.dao;

import java.util.List;

import cn.focus.search.admin.model.HotWord;

/**
 * @author Administrator
 *
 */
public interface HotWordDao {

	/**
     *根据日期获取需要更新到词库的热词数据
     * @param
     * @return
     * @throws Exception
     */
	List<HotWord> getDayHotWordList()throws Exception;

    /**
     *插入热词数据
     * @param hotWord
     * @return
     * @throws Exception
     */
    public int insertHotWord(HotWord hotWord)throws Exception;

    /**
     *批量获取热词数据
     * @param 
     * @return
     * @throws Exception
     */
    public List<HotWord> getHotWordList()throws Exception;
    
    /**
     *根据日期获取需要添加到热词词库的热词数据
     * @param wordName
     * @return
     * @throws Exception
     */
    public List<HotWord> getHotWordListByName(String wordName)throws Exception;
    
    /**
     *根据id删除热词数据
     * @param id
     * @return
     * @throws Exception
     */
    public int delHotWordById(int id)throws Exception;

    /**
     *根据status获取热词数据（只要名称）
     * @param status == 1
     * @return
     * @throws Exception
     */
    public List<String> getHotWordnameByStatus(int status)throws Exception;
    
	List<HotWord> getTotalHotWordList();

	/**
     *将status置0
     * @param status
     * @return
     * @throws Exception
     */
	public int setExported();
}
