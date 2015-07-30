/**
 * 
 */
package cn.focus.search.admin.dao;

import java.util.List;

import cn.focus.search.admin.model.HotWord;

/**
 * @author Administrator
 *
 */
public interface HotWordDao {

	List<HotWord> getDayHotWordList()throws Exception;
	

}
