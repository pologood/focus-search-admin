package cn.focus.search.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.netty.util.internal.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cn.focus.search.admin.config.LastTime;
import cn.focus.search.admin.dao.HotWordDao;
import cn.focus.search.admin.dao.StopWordsDao;
import cn.focus.search.admin.model.HotWord;
import cn.focus.search.admin.model.PplResult;
import cn.focus.search.admin.model.StopWords;
import cn.focus.search.admin.service.ParticipleManagerService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
/**
 * 分词管理
 * @author xuemingtang
 *
 */
@Service
public class ParticipleManagerServiceImpl implements ParticipleManagerService{

	@Value("${ik.url1}")
	private String ikurl1;
	
	@Value("${ik.urlSmart1}")
	private String ikurlSmart1;
	
	@Value("${ik.url2}")
	private String ikurl2;
	
	@Value("${ik.urlSmart2}")
	private String ikurlSmart2;
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	private Logger logger = LoggerFactory.getLogger(ParticipleManagerServiceImpl.class);
	
	private static Map<String,Object> wordMap = new ConcurrentHashMap<String, Object>();
	
	
	public int getWordMapSize(){
		return wordMap.size();
	}
	
	/**
	 * 获取索引和搜索分词
	 * @param words
	 */
	public Map<String,Object> getIkWords(int pageNo,int pageSize,String words,int type){
		
		wordMap.clear();
		String wordArr[] = words.split("\n");
		
		List<PplResult> list = new ArrayList<PplResult>();
		Map<String,Object> map = new HashMap<String,Object>();
		
		int start = (pageNo-1)*pageSize;
		int end = pageNo*pageSize-1;
		if(end > wordArr.length-1){
			end  =  wordArr.length-1;
		}
		
		for(int i=start;i<=end;i++){
			
			if(StringUtils.isBlank(wordArr[i])){
				continue;
			}
			list.add(getPplWord(wordArr[i],type));
		}
		
		map.put("list", list);
		map.put("total", wordArr.length);
		
		wordMap.put("wordKey", list);
		
		return map;
	}
	
	/**
	 * 
	 * @param word
	 * @param iksmartUrl
	 * @param ikUrl
	 * @return
	 */
	@Override
	public PplResult getPplWord(String word,int type){
		
		StringBuffer ikUrl=new StringBuffer();
		StringBuffer iksmartUrl=new StringBuffer();
		String ikurlTemp=null;
		String ikurlSmartTemp=null;
		
		//根据集群类型，确定url。
		if(type==1){
			ikurlTemp=ikurl1;
			ikurlSmartTemp=ikurlSmart1;
		}else if(type==2){
			ikurlTemp=ikurl2;
			ikurlSmartTemp=ikurlSmart2;
		}
		
		if(StringUtils.isBlank(word)){
			return null;
		}
		
		PplResult ikWords = new PplResult();
		ikWords.setWord(word);
		
		ikUrl.append(ikurlTemp).append(word);
		String ikWord = restTemplate.getForObject(ikUrl.toString(), String.class);
		ikUrl.delete( 0, ikUrl.length() );
		ikWords.setIndexPplWord(buildIkWords(ikWord));
		
		iksmartUrl.append(ikurlSmartTemp).append(word);
		String iksmartWord = restTemplate.getForObject(iksmartUrl.toString(), String.class);
		iksmartUrl.delete( 0, iksmartUrl.length() );
		ikWords.setSearchPplWord(buildIkWords(iksmartWord));

		
		return ikWords;
	}
	
	/**
	 * 
	 * @param result
	 * @return
	 */
	public String buildIkWords(String result){
		JSONObject json = JSONObject.parseObject(result);
		JSONArray arr = (JSONArray)json.get("tokens");
		StringBuilder words = new StringBuilder(256);
		
		for(int i=0;i<arr.size();i++){
			JSONObject js = (JSONObject)arr.get(i);
			words.append(js.get("token")).append(" ");
		}	
		
		return words.toString();
	}
	

	//判断该词是否已经已经在词库(非停用词)中。
	@Override
	public boolean isDuplicate(String word,int type) {
		boolean flag=false;
		//根据集群类型，确定url。
		String ikurlTemp=null;
		if(type==1){
			ikurlTemp=ikurl1;
		}else if(type==2){
			ikurlTemp=ikurl2;
		}
		StringBuffer ikUrl=new StringBuffer();
		ikUrl.append(ikurlTemp).append(word);
		String ikWord = restTemplate.getForObject(ikUrl.toString(), String.class);
		ikUrl.delete( 0, ikUrl.length() );
		
		JSONObject json = JSONObject.parseObject(ikWord);
		JSONArray arr = (JSONArray)json.get("tokens");
		
		for(int i=0;i<arr.size();i++){
			JSONObject js = (JSONObject)arr.get(i);
			if (js.getString("token").equals(word)){
				flag=true;
			}
		}	
		
		return flag;
	}
}
