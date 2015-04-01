package cn.focus.search.admin.utils;

import com.alibaba.fastjson.JSONObject;

public class JSONUtils {
	
	public static String badResult(String errMsg){
		JSONObject json = new JSONObject();
		json.put("errorCode", 1);
		json.put("errorMsg", errMsg);
		return json.toJSONString();
	}
	
	public static String badResult(int errorCode,String errMsg){
		JSONObject json = new JSONObject();
		json.put("errorCode", errorCode);
		json.put("errorMsg", errMsg);
		return json.toJSONString();
	}
	
	
	public static JSONObject badJSONResult(String errMsg){
		JSONObject json = new JSONObject();
		json.put("errorCode", 1);
		json.put("errorMsg", errMsg);
		return json;
	}
	
	public static String ok(){
		JSONObject json = new JSONObject();
		json.put("errorCode", 0);
		json.put("errorMsg", "success");
		return json.toJSONString();
	}

}
