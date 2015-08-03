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

	List<HotWord> getDayHotWordList()throws Exception;

    /**
     *插入热词数据
     * @param stopword
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
     * @param wordName
     * @return
     * @throws Exception
     */
    public int delHotWordById(int id)throws Exception;
}