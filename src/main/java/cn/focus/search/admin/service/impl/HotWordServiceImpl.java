package cn.focus.search.admin.service.impl;

import java.io.File;
import java.io.FileOutputStream;
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
	
	@Override
	public List<String> getHotWordnameByStatus(int status) throws Exception {
		// TODO Auto-generated method stub
		List<String> list=new LinkedList<String>();
        try {
            list = hotWordDao.getHotWordnameByStatus(status);
            logger.info("status: "+status);
        } catch (Exception e) {
            logger.error("获取热词数据异常!", e);
        }
        return list;
	}
	
	@Override
	public boolean exportHot(String path, String fileName, List<String> list) throws IOException {
		
		/*response.reset();
		response.setContentType("application/vnd.ms-txt");
		response.addHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");*/
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
				//os.write('\r');
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
	public int setExported() throws Exception {
		// TODO Auto-generated method stub
		int s = 0;
        try {
            s = hotWordDao.setExported();
        } catch (Exception e) {
            logger.error("插入热词数据异常!", e);
        }
        return s;
	}

	
}
