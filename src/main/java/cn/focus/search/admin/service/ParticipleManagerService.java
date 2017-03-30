package cn.focus.search.admin.service;
import cn.focus.search.admin.model.PplResult;

import java.util.Map;
/**
 * 
 * @author xueqingyuan
 *
 */
public interface ParticipleManagerService {
	
	public int getWordMapSize();
	
	public Map<String,Object> getIkWords(int pageNo,int pageSize,String words,int type);
	public PplResult getPplWord(String word,int type);
	
	public boolean isDuplicate(String word,int type);

}
