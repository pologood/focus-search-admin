package cn.focus.search.admin.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.focus.search.admin.model.Participle;

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
	 * @param groupId
	 * @param projName
	 * @param from
	 * @param size
	 * @return
	 */
	public JSONObject searchProj(String groupId,String projName,int from,int size);
	/**
	 * 
	 * @param groupId
	 * @param manualWord
	 * @return
	 */
	public boolean updateManualWords(String groupId,String manualWords,String userName)throws Exception;
	

	/**
	 * 
	 * @return
	 */
	public int getWordMapSize();
	
	

	/***
	 * 批量获取未分词数据
	 * @param participles
	 * @return
	 */
	public List<Participle> getParticipleList(int status);
	
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
	public boolean exportHouse(HttpServletRequest request, HttpServletResponse response, String string)throws IOException;
	public boolean exportStop(HttpServletRequest request, HttpServletResponse response, String string)throws IOException;
	public boolean exportHot(HttpServletRequest request, HttpServletResponse response, String string)throws IOException;
	
}
