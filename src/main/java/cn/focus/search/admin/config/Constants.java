package cn.focus.search.admin.config;

public class Constants {
	/**
	 * 登陆用户名
	 */
	public static final String userName ="admin";
	/**
	 * 登陆密码
	 */
	public static final String passWord = "123";
	/**
	 * redis cache 词失效时间
	 */
	public static final int expiredTime=6000;
	
	/**
	 * 楼盘工程secret key
	 */
	public static final String BUILDING_SECRET_KEY = "e0cd45bf9ee7773cc9b72bd824f3b35c";
	/**
	 * ik搜索端分词url
	 */
	public static String ikSmart = "http://es.search.focus.cn:9200/focus_house/_analyze?analyzer=ik_smart&text=";
	/**
	 * ik索引端分词url
	 */
	public static String ik = "http://es.search.focus.cn:9200/focus_combinesearch_v1.1/_analyze?analyzer=ik&text=";

}
