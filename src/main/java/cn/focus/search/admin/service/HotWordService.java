package cn.focus.search.admin.service;

import java.util.List;

import cn.focus.search.admin.model.HotWord;

public interface HotWordService {

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
     *根据停止词名获取热词数据
     * @param wordName
     * @return
     * @throws Exception
     */
    public List<HotWord> getHotWordListByName(String wordName)throws Exception;
    
    public int delHotWordById(int id) throws Exception;
}
