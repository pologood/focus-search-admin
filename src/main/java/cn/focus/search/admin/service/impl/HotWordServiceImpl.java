package cn.focus.search.admin.service.impl;

import cn.focus.search.admin.config.Constants;
import cn.focus.search.admin.dao.HotWordDao;
import cn.focus.search.admin.model.HotWord;
import cn.focus.search.admin.service.HotWordService;
import cn.focus.search.admin.service.ParticipleManagerService;
import cn.focus.search.admin.service.RedisService;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class HotWordServiceImpl implements HotWordService{

	private Logger logger = LoggerFactory.getLogger(HotWordServiceImpl.class);

	@Autowired
	private HotWordDao hotWordDao;
	
	@Autowired
	private RedisService redisService;
	@Autowired
	private ParticipleManagerService pmService;
	
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
            redisService.popRedisSet("projNameForPartitio","helloworld");
        } catch (Exception e) {
            logger.error("插入热词数据异常!", e);
        }
        return s;
	}

	@Override
	public List<HotWord> getHotWordList(RowBounds rowBounds) throws Exception {
		// TODO Auto-generated method stub
		List<HotWord> list=new LinkedList<HotWord>();
        try {
            list = hotWordDao.getHotWordList(rowBounds);
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
	public int setExported(int type) throws Exception {
		// TODO Auto-generated method stub
		int s = 0;
        try {
            s = hotWordDao.setExported(type);
        } catch (Exception e) {
            logger.error("插入热词数据异常!", e);
        }
        return s;
	}

	@Override
	public int getTotalNum() throws Exception {
		// TODO Auto-generated method stub
		int s = 0;
        try {
            s = hotWordDao.getTotalNum();
        } catch (Exception e) {
            logger.error("获取数据异常!", e);
        }
        return s;
	}

	/* (non-Javadoc)
	 * @see cn.focus.search.admin.service.HotWordService#importNewProjName()
	 * 通过对比redis中数据，得出新增楼盘名称，并将其作为新热词加入数据库。
	 */
	@Override
	public void importNewProjName() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String keyT=dateFormat.format(new Date());
		Calendar c=Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -1);
		String keyY=dateFormat.format(c.getTime());
		Set<String> projNameSetYesterday=redisService.popRedisSet("projNameForPartition", keyY);
		Set<String> projNameSetToday=redisService.popRedisSet("projNameForPartition", keyT);
		Set<String> projNameSet=null;
		
		if(projNameSetYesterday==null) {
			logger.info("projNameSetYesterday"+keyY+"为空。");
			return;
		}
		if(projNameSetToday==null) {
			logger.info("projNameSetToday"+keyT+"为空。");
			return;
		}
		projNameSet=redisService.sdiff("projNameForPartition"+keyT, "projNameForPartition"+keyY);
		

		logger.info("projNameSetYesterday，当前数量为"+projNameSetYesterday.size());
		logger.info("projNameSetToday，当前数量为"+projNameSetToday.size());
		logger.info("projNameSet，当前数量为"+projNameSet.size());

		Iterator<String> it = projNameSet.iterator();
		int count=0;
		while (it.hasNext()) {
			String token=it.next();
			if(isExist(token,1)) continue;
			HotWord hw = new HotWord();
			hw.setName(token);
			hw.setType(1);
			hw.setStatus(Constants.ORI_STATUS);
			hw.setEditor("system");
			Date now=new Date();
			hw.setCreateTime(now);
			hw.setUpdateTime(now);
			try {
				hotWordDao.insertHotWord(hw);
				count++;
				logger.info("find "+count+"new hot words,and insert them to db.");
			} catch (Exception e) {
				logger.error("涉及插入词"+hw.getName(),e.getMessage());
			}
		}
	}	
	
	
	/**
	 * @author qingyuanxue@sohu-inc.com  
	 * @date 2016年5月26日上午11:26:10
	 * @description 
	 */
	public boolean  isExist(String token,int type) {
		boolean flag=false;
		try {
			List<HotWord> list=hotWordDao.getHotWordListByName(token);
			//如果该词汇已经在数据库或者es端词库。
			if((list.size()>0&&list!=null)||pmService.isDuplicate(token,type)) flag=true;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return flag;
	}

	public List<HotWord> getHotList(int type, String hots, String editor, int status)
	{
		List<HotWord> hotList = new LinkedList<HotWord>();
		try{
			String[] stop = hots.split("[, ， ]");
			for (int i = 0; i < stop.length; i++)
			{
				HotWord hw = new HotWord();
				hw.setName(stop[i]);
				hw.setType(type);
				hw.setStatus(status);
				hw.setEditor(editor);
				Date now=new Date();
				hw.setCreateTime(now);
				hw.setUpdateTime(now);
				hotList.add(hw);
			}
			
			return hotList;
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return hotList;		
	}

	/* (non-Javadoc)
	 * @see cn.focus.search.admin.service.HotWordService#getHotWordToDicByType()
	 */
	@Override
	public String getHotWordToDicByType(Integer type) {
		StringBuffer str=new StringBuffer();
		
		// read from mysql.
		List<String> list = new LinkedList<String>();
		try {
		    list=hotWordDao.getHotWordToDicByType(type);
			logger.info("getDicFromDB-获取 "+list.size()+"个 type"+type+"的hot word.");
		} catch (Exception e) {
			logger.error("get HotWordException",e);
		}
		for(int i=0;i<list.size();i++){
			
			String word=list.get(i);
					str.append(word);
					str.append("\n");
		}
		return str.toString();
	}


}
