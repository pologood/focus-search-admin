package cn.focus.search.admin.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	 * 导出数据
	 * @param pathName
	 * @return
	 * @throws IOException
	 */
    public boolean exportExcel(HttpServletRequest request,
			HttpServletResponse response,String exportName) throws IOException;
	/**
	 * 
	 * @return
	 */
	public int getWordMapSize();
	
	
	public boolean exportExcel(HttpServletRequest request,
				HttpServletResponse response, String exportName,
				String templateName, Map<String, Object> dataMap) throws IOException;

}
