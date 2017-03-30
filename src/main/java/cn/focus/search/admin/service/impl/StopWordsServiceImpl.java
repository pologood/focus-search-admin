package cn.focus.search.admin.service.impl;

import cn.focus.search.admin.dao.StopWordsDao;
import cn.focus.search.admin.model.StopWords;
import cn.focus.search.admin.service.StopWordsService;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

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
	public List<StopWords> getStopWordsList(RowBounds rowBounds)
	{
		List<StopWords> list=new LinkedList<StopWords>();
        try {
            list = stopWordsDao.getStopWordsList(rowBounds);
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
	public int setExported(int type) throws Exception {
		// TODO Auto-generated method stub
		int s = 0;
        try {
            s = stopWordsDao.setExported(type);
        } catch (Exception e) {
            logger.error("删除停止词数据异常!", e);
        }
        return s;
	}

	@Override
	public boolean exportStop(String path, String fileName, List<String> list)
			throws IOException {
		// TODO Auto-generated method stub
		File file = new File(path);
		if (!file.exists())
		{
			file.mkdir();
		}
		file = new File(path+File.separator+fileName);
		OutputStream os = null;
		os = new FileOutputStream(file);
		
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

	@Override
	public int getTotalNum() throws Exception {
		// TODO Auto-generated method stub
		int s = 0;
        try {
            s = stopWordsDao.getTotalNum();
        } catch (Exception e) {
            logger.error("插入停止词数据异常!", e);
        }
        return s;
	}

	/* (non-Javadoc)
	 * @see cn.focus.search.admin.service.StopWordsService#getStopWordToDicByType(java.lang.Integer)
	 */
	@Override
	public String getStopWordToDicByType(Integer type) {
		StringBuffer str=new StringBuffer();
		String strWords=null;
			
		// read from mysql.
		List<String> list = new LinkedList<String>();
		try {
			
			list=stopWordsDao.getStopWordToDicByType(type);
			logger.info("获取 "+list.size()+"个 type"+type+"的stop word.");
		} catch (Exception e) {
			logger.error("getStopWordsException",e);
		}
		for(int i=0;i<list.size();i++){
			
			String word=list.get(i);
			str.append(word);
			str.append("\n");
			}
		strWords=str.toString();
		return strWords;
	}

}
