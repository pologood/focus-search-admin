package cn.focus.search.admin.controller;

import cn.focus.search.admin.service.ParticipleManagerService;
import cn.focus.search.admin.utils.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/pm")
public class ParticipleManagerController {
	
	private Logger logger = LoggerFactory.getLogger(ParticipleManagerController.class);
	
	@Autowired
	private ParticipleManagerService participleManagerService;
	
	
	//分词效果。
	@RequestMapping(value="ptindex",method =RequestMethod.GET)
	public String ptCheckIndex(){
		try{
			return "ptcheck";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "error";
		}
	}

	@RequestMapping(value="stop",method =RequestMethod.GET)
	public String stopWords(){
		try{
			return "stop_words";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "error";
		}
	}
		
	@RequestMapping(value="hot",method =RequestMethod.GET)
	public String hotWord(){
		try{
			return "hot_word";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "error";
		}
	}
	
	/**
	 * 查看分词效果
	 * @param request
	 * @return
	 */
	@RequestMapping(value="check",method=RequestMethod.POST)
	@ResponseBody
	public String getPplResult(HttpServletRequest request){
		try{
			JSONObject json = new JSONObject();
			String words = request.getParameter("words");
			//集群类型。
			int type=Integer.valueOf(request.getParameter("type"));
			int pageSize = 10;
			int pageNo = 1;
			
			if(StringUtils.isBlank(words)){
				return JSONUtils.badResult("words can not be empty");
			}
			if(StringUtils.isNotBlank(request.getParameter("page"))){
				pageNo = Integer.valueOf(request.getParameter("page"));
			}
			if(StringUtils.isNotBlank(request.getParameter("rows"))){
				pageSize = Integer.valueOf(request.getParameter("rows"));
			}
			
			Map<String,Object> map = participleManagerService.getIkWords(pageNo,pageSize,words,type);
			json.put("total", (int)map.get("total"));
			json.put("rows", JSONObject.toJSON((List)map.get("list")));
			return json.toJSONString();
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONUtils.badResult("failed");
		}
	}

	public static void main(String args[]){
		System.out.println("name"+"\n"+"tag"+"\n"+"ddd");
	}

}
