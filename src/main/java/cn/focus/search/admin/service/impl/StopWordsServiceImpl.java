package cn.focus.search.admin.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	@Override
	public List<StopWords> getDayStopWordsList() {
		// TODO Auto-generated method stub
		List<StopWords> list=new LinkedList<StopWords>();
        try {
            list = stopWordsDao.getDayStopWordsList();
        } catch (Exception e) {
            logger.error("获取停止词数据异常!", e);
        }
        return list;
	}

	@Override
	public int delStopWordsById(int id) {
		// TODO Auto-generated method stub
		int s = 0;
        try {
            s = stopWordsDao.delStopWordsById(id);
        } catch (Exception e) {
            logger.error("删除停止词数据异常!", e);
        }
        return s;
	}

	@Override
	public List<String> getStopWordnameByStatus(int status) {
		// TODO Auto-generated method stub
		List<String> list=new LinkedList<String>();
        try {
            list = stopWordsDao.getStopWordnameByStatus(status);
            logger.info("status: "+status);
        } catch (Exception e) {
            logger.error("获取停止词数据异常!", e);
        }
        return list;
	}

	@Override
	public int setExported() throws Exception {
		// TODO Auto-generated method stub
		int s = 0;
        try {
            s = stopWordsDao.setExported();
        } catch (Exception e) {
            logger.error("删除停止词数据异常!", e);
        }
        return s;
	}

	@Override
	public boolean exportStop(HttpServletResponse response, String fileName, List<String> list)
			throws IOException {
		// TODO Auto-generated method stub
		response.reset();
		response.setContentType("application/vnd.ms-txt");
		response.addHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
		OutputStream os = null;
		os = response.getOutputStream();
		
		// 第二步，将文件存到指定位置
		try {
			for (String str : list)
			{
				os.write(str.getBytes());
				os.write('\n'); 
				os.flush();
			}
			return true;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return false;
			}finally{
				os.close();
			}
	}
}
