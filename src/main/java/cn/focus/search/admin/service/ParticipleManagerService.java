package cn.focus.search.admin.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import cn.focus.search.admin.model.Participle;
import cn.focus.search.admin.model.PplResult;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author xuemingtang
 *
 */
public interface ParticipleManagerService {
	/**
	 * 
	 * @param words
	 * @return
	 */
	public Map<String,Object> getIkWords(int pageNo,int pageSize,String words);


	/**
	 * 
	 * @return
	 */
	public int getWordMapSize();
	public int updateParticiple(Participle participle);
	
	public String updateIK();//更新final_house词库。
	public String updateStopwordIK();//更新停用词词库。
	public String updateHotwordIK();//更新临时词词库。
	
	public String getRemoteFinalHouseWord();
	public String getRemoteStopword();
	public String getRemoteHotword();
	public String getIkUrl();

	public boolean isDuplicate(String word);

	public boolean reloadRemoteDic();
	
	/**
	 * 导出数据
	 * @param pathName
	 * @return
	 * @throws IOException
	 */
    public boolean exportExcel(HttpServletRequest request,HttpServletResponse response,String exportName) throws IOException;
	
    /**
	 * 导出分词数据到文本文件
	 * @param pathName
	 * @return
	 * @throws IOException
	 */
    public boolean exportParticiple(String path, String fileName, List<String> list)throws IOException;	

    /**
     *将status置0
     * @param status
     * @return
     * @throws Exception
     */
    public int setExported();
    
    /**
     *根据status获取分词数据（只要名称）
     * @param status == 1
     * @return
     * @throws Exception
     */
    public List<String> getParticiplesByStatus(int status)throws Exception;
    
    /**
     *根据id删除分词
     * @param id
     * @return
     * @throws Exception
     */
    public int delParticipleWordsByPid(int pid)throws Exception;

	public boolean updateParticiple(String groupId, String manualWords, String userName);
	public List<Participle> searchProjToMidify(int i, int pageSize);
	public List<Participle> searchProjToMidify(String groupId, String projName, int i, int pageSize);
	public int searchProjToMidifyNum();
	public int searchProjToMidifyNum(String groupId, String projName);


	/**
	 * @author qingyuanxue@sohu-inc.com  
	 * @date 2016年5月9日下午8:33:23
	 * @description 对新加入数据库的楼盘进行初步分词。
	 */
	public void updateParticiple();
	public PplResult getPplWord(String word);

}
