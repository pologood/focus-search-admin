package cn.focus.search.admin.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.focus.search.admin.dao.StopWordsDao;
import cn.focus.search.admin.model.StopWords;
import cn.focus.search.admin.service.StopWordsService;

@Service
public class StopWordsServiceImpl implements StopWordsService{

	private Logger logger = LoggerFactory.getLogger(StopWordsServiceImpl.class);
    @Autowired
    private StopWordsDao stopWordsDao;
    
	@Override
	public int insertStopWords(StopWords stopword)
	{
        int s = 0;
        try {
            s = stopWordsDao.insertStopWords(stopword);
        } catch (Exception e) {
            logger.error("插入停止词数据异常!", e);
        }
        return s;
	}
	
	@Override
	public List<StopWords> getStopWordsList()
	{
		List<StopWords> list=new LinkedList<StopWords>();
        try {
            list = stopWordsDao.getStopWordsList();
        } catch (Exception e) {
            logger.error("获取停止词数据异常!", e);
        }
        return list;
	}
	
	@Override
	public List<StopWords> getStopWordsListByName(String wordName)
	{
		List<StopWords> list=new LinkedList<StopWords>();
        try {
            list = stopWordsDao.getStopWordsListByName(wordName);
            logger.info("wordname: "+wordName);
        } catch (Exception e) {
            logger.error("获取停止词数据异常!", e);
        }
        return list;
	}
}
