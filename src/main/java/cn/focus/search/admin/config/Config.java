package cn.focus.search.admin.config;

public class Config {
	
	public interface EsConfig{
		/**
		 * 索引库名字
		 */
		public final static String indexName = "focus_house";
		/**
		 * type名字
		 */
		public final static String typeName = "focus-newProj-index";
		/**
		 * 索引字段
		 */
		public final static String[] fileds = {"groupId","projName","projNameOther","noLinks","projAddress","kfsName","extField"};
				
	}
	

}
