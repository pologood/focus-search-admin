/**
 * 
 */
package cn.focus.search.admin.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

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
    public List<HotWord> getHotWordList(RowBounds rowBounds)throws Exception;
    
    public int getTotalNum()throws Exception;
    
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

    
	List<HotWord> getTotalHotWordList();

	/**
     *将status置0
     * @param status
     * @return
     * @throws Exception
     */
	public int setExported(int type);

	/**
	 * @author qingyuanxue@sohu-inc.com  
	 * @date 2016年5月26日下午3:19:22
	 * @description 
	 */
	List<String> getHotWordToDicByType(Integer type);
}
