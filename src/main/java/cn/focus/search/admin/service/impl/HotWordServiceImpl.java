package cn.focus.search.admin.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.focus.search.admin.dao.HotWordDao;
import cn.focus.search.admin.model.HotWord;
import cn.focus.search.admin.service.HotWordService;

@Service
public class HotWordServiceImpl implements HotWordService{

	private Logger logger = LoggerFactory.getLogger(HotWordServiceImpl.class);

	@Autowired
	private HotWordDao hotWordDao;
	
	@Override
	public List<HotWord> getDayHotWordList()throws Exception
	{
		List<HotWord> list=new LinkedList<HotWord>();
        try {
            list = hotWordDao.getDayHotWordList();
        } catch (Exception e) {
            logger.error("获取热词数据异常!", e);
        }
        return list;
	}

	@Override
	public int insertHotWord(HotWord hotWord) throws Exception {
		// TODO Auto-generated method stub
		int s = 0;
        try {
            s = hotWordDao.insertHotWord(hotWord);
        } catch (Exception e) {
            logger.error("插入热词数据异常!", e);
        }
        return s;
	}

	@Override
	public List<HotWord> getHotWordList() throws Exception {
		// TODO Auto-generated method stub
		List<HotWord> list=new LinkedList<HotWord>();
        try {
            list = hotWordDao.getHotWordList();
        } catch (Exception e) {
            logger.error("获取热词数据异常!", e);
        }
        return list;
    }

	@Override
	public List<HotWord> getHotWordListByName(String wordName) throws Exception {
		// TODO Auto-generated method stub
		List<HotWord> list=new LinkedList<HotWord>();
        try {
            list = hotWordDao.getHotWordListByName(wordName);
            logger.info("wordname: "+wordName);
        } catch (Exception e) {
            logger.error("获取热词数据异常!", e);
        }
        return list;
	}

	@Override
	public int delHotWordById(int id) throws Exception {
		// TODO Auto-generated method stub
		int s = 0;
        try {
            s = hotWordDao.delHotWordById(id);
        } catch (Exception e) {
            logger.error("删除热词数据异常!", e);
        }
        return s;
	}
		
}
