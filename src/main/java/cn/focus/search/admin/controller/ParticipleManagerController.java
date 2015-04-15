package cn.focus.search.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.focus.search.admin.model.UserInfo;
import cn.focus.search.admin.service.ParticipleManagerService;
import cn.focus.search.admin.utils.JSONUtils;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/admin/pm")
public class ParticipleManagerController {
	
	private Logger logger = LoggerFactory.getLogger(ParticipleManagerController.class);
	
	@Autowired
	private ParticipleManagerService participleManagerService;
	
	
	@RequestMapping(value="ptindex",method =RequestMethod.GET)
	public String ptCheckIndex(){
		try{
			return "ptcheck";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "error";
		}
	}
	
	@RequestMapping(value="mindex",method =RequestMethod.GET)
	public String manualPtIndex(){
		try{
			return "manualpt";
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
			
//			System.out.println("words: "+words+" page:"+request.getParameter("page")+" rows"+ request.getParameter("rows"));

			
			Map<String,Object> map = participleManagerService.getIkWords(pageNo,pageSize,words);
			
			
			json.put("total", (int)map.get("total"));
			json.put("rows", JSONObject.toJSON((List)map.get("list")));
			
			System.out.println(json.toJSONString());
			
			return json.toJSONString();
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONUtils.badResult("failed");
		}
	}
	
	/**
	 * 搜索的楼盘列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="query",method=RequestMethod.POST)
	@ResponseBody
	public String queryProjList(HttpServletRequest request){
		try{
			JSONObject json = new JSONObject();
            String groupId = request.getParameter("groupId");
            String projName = request.getParameter("projName");
			int pageSize = 10;
			int pageNo = 1;
			
            
            if(StringUtils.isBlank(projName) && StringUtils.isBlank(groupId)){
            	return JSONUtils.badResult("groupId or projName can not be empty");
            }
			if(StringUtils.isNotBlank(request.getParameter("page"))){
				pageNo = Integer.valueOf(request.getParameter("page"));
			}
			if(StringUtils.isNotBlank(request.getParameter("rows"))){
				pageSize = Integer.valueOf(request.getParameter("rows"));
			}
            
            JSONObject js = participleManagerService.searchProj(groupId, projName, pageNo-1, pageSize);
			
			
			json.put("total", js.getIntValue("total"));
			json.put("rows", JSONObject.toJSON(js.get("projList")));
			
			System.out.println(json.toJSONString());
			return json.toJSONString();
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			return JSONUtils.badResult("failed");
		}
	}
	
	/**
	 * 修改人工分词
	 * @param request
	 * @return
	 */
	@RequestMapping(value="manual",method=RequestMethod.POST)
	@ResponseBody
	public String modifyManualWords(HttpServletRequest request){
		try{
			String groupId = request.getParameter("groupId");
			String manualWords = request.getParameter("manualWords");
			UserInfo user = (UserInfo)request.getSession().getAttribute("user");
			
			System.out.println("groupId:"+groupId+" manualWords:"+manualWords);
			
			if(StringUtils.isBlank(groupId)){
				return JSONUtils.badResult("failed");
			}
			
			boolean flg = participleManagerService.updateManualWords(groupId, manualWords,user.getUserName());
			if(!flg){
				return JSONUtils.badResult("failed");
			}
			return JSONUtils.ok();
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONUtils.badResult("failed");
		}
	}
	
	/**
	 * 导出数据
	 * @return
	 */
	@RequestMapping(value="export",method=RequestMethod.GET)
	@ResponseBody
	public String exportData(HttpServletRequest request,
			HttpServletResponse response){
		try{
			
			participleManagerService.exportExcel(request, response, "words-data.xls");
//			participleManagerService.exportExcel(request, response, "words-data.xls", "word.xls", map);
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
//			return JSONUtils.badResult("failed");
		}
		return null;
	}
	
	public static void main(String args[]){
		System.out.println("name"+"\n"+"tag"+"\n"+"ddd");
	}

}
