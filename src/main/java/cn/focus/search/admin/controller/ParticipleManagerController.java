package cn.focus.search.admin.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.focus.search.admin.model.Participle;
import cn.focus.search.admin.model.UserInfo;
import cn.focus.search.admin.service.ParticipleManagerService;
import cn.focus.search.admin.utils.JSONUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

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
	
/*	@RequestMapping(value="modifyP",method =RequestMethod.GET)
	public String modifyP(){
		try{
			String ikurl = participleManagerService.getIkUrl();
			return "modify_proj";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "error";
		}
	}*/
	
	
	@RequestMapping(value="modifyP",method =RequestMethod.GET)
	public ModelAndView modifyP(){ 
		try{
			ModelAndView mv = new ModelAndView("modify_proj"); 
			String ikurl = participleManagerService.getIkUrl();
			System.out.println("ikurl:"+ikurl);
			mv.addObject("ikurl", ikurl);
		    return mv;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			ModelAndView mv = new ModelAndView("error"); 
			return mv;
		}
	}
	
	@RequestMapping(value="backup",method =RequestMethod.GET)
	public ModelAndView dataBackup(){ 
		try{
			ModelAndView mv = new ModelAndView("backup"); 
			String ikurl = participleManagerService.getIkUrl();
			System.out.println("ikurl:"+ikurl);
			mv.addObject("ikurl", ikurl);
		    return mv;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			ModelAndView mv = new ModelAndView("error"); 
			return mv;
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
	 * modify_proj.jsp中修改楼盘分词。。。搜索待修改楼盘列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="proj_query",method=RequestMethod.POST)
	@ResponseBody
	public String queryProjListToMidify(HttpServletRequest request){
		try{
			JSONObject json = new JSONObject();
            String groupId = request.getParameter("groupId");
            String projName = request.getParameter("projName");
			int pageSize = 3;
			int pageNo = 1;
			
            

			if(StringUtils.isNotBlank(request.getParameter("page"))){
				pageNo = Integer.valueOf(request.getParameter("page"));
			}
			if(StringUtils.isNotBlank(request.getParameter("rows"))){
				pageSize = Integer.valueOf(request.getParameter("rows"));
			}
            
			List<Participle> list=null;
			int total=0;
            if(StringUtils.isBlank(projName) && StringUtils.isBlank(groupId)){
            	list = participleManagerService.searchProjToMidify((pageNo-1)*pageSize, pageSize);
            	total= participleManagerService.searchProjToMidifyNum();
            	System.out.println("@@@@@@@@total"+total);
            }else {
            	list = participleManagerService.searchProjToMidify(groupId, projName, (pageNo-1)*pageSize, pageSize);
            	total= participleManagerService.searchProjToMidifyNum(groupId,projName);
            }		
            
            JSONArray ja=new JSONArray();
            ja.addAll(list);
			
			json.put("total", String.valueOf(total));
			json.put("rows", ja);
			
			System.out.println(JSON.toJSONString(json,SerializerFeature.WriteDateUseDateFormat));
			return JSON.toJSONString(json,SerializerFeature.WriteDateUseDateFormat);
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			return JSONUtils.badResult("failed");
		}
	}
	
	/***
	 * 批量获取新加的待分词的数据
	 * @param request
	 * @return
	 */
	@SuppressWarnings("null")
	@RequestMapping(value="select4new",method=RequestMethod.POST)
	@ResponseBody
	public String selectNewPartList(HttpServletRequest request){
		try{
			int pageSize = 10;
			int pageNo = 1;
			
			if(StringUtils.isNotBlank(request.getParameter("page"))){
				pageNo = Integer.valueOf(request.getParameter("page"));
			}
			if(StringUtils.isNotBlank(request.getParameter("rows"))){
				pageSize = Integer.valueOf(request.getParameter("rows"));
			}
			List<Participle> listTotal = participleManagerService.getParticipleList(0);
			List<Participle> list = new LinkedList<Participle>();
			int start = (pageNo-1)*pageSize;
			int end = pageNo*pageSize-1;
			if(end > listTotal.size()-1){
				end  =  listTotal.size()-1;
			}
			for(int i=start;i<=end;i++){
				list.add(listTotal.get(i));
			}
			JSONArray jsArray = new JSONArray();
			jsArray.addAll(list);
			String result = "{"+"\"rows\":"+JSON.toJSONString(jsArray,SerializerFeature.WriteDateUseDateFormat)+","+"\"total\":"+listTotal.size()+"}";
			System.out.println(JSON.toJSONString(jsArray,SerializerFeature.WriteDateUseDateFormat));
			System.out.println(result);
			return result;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			return JSONUtils.badResult("failed");
		}
	}
	
	/***
	 * 更新词库
	 * @param request
	 * @return
	 */
	@RequestMapping(value="updateIK",method=RequestMethod.POST)
	@ResponseBody
	public String updateIK(HttpServletRequest request){
		/*
		 * 处理成功返回"success",失败返回任意字符串(建议返回"failed")
		 */
		return participleManagerService.updateIK();
	}
	
	/**
	 * 完成人工分词后存库
	 * @param request
	 * @return
	 */
	@RequestMapping(value="updatePart",method=RequestMethod.POST)
	@ResponseBody
	public String updateParticiple(HttpServletRequest request){
		try{
			String sId = request.getParameter("Id");
			System.out.println("！分词ID："+sId);
			if(StringUtils.isBlank(sId)){
				return JSONUtils.badResult("failed");
			}
			int Id = Integer.parseInt(sId);
			int pid = Integer.parseInt(request.getParameter("pid"));
			String name = request.getParameter("name");
			String aliasName = request.getParameter("aliasName");
			int type = Integer.parseInt(request.getParameter("type"));
			String participles = request.getParameter("manualWords");
			UserInfo user = (UserInfo)request.getSession().getAttribute("user");
			String editor = user.getUserName();
			String createTime = request.getParameter("createTime");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date cTime = sdf.parse(createTime);
			System.out.println("!!!!!新词Id:"+Id+" participles:"+participles);
				
			Participle participle = new Participle();
			participle.setId(Id);
			participle.setPid(pid);
			participle.setName(name);
			participle.setAliasName(aliasName);
			participle.setParticiples(participles);
			participle.setEditor(editor);
			participle.setType(type);
			participle.setStatus(1);
			participle.setCreateTime(cTime);
			participle.setUpdateTime(new Date());
			
			int result = participleManagerService.updateParticiple(participle);
			System.out.println("！result："+result);
			if(result<1){//更新失败
				System.out.println("更新失败！result："+result);
				return JSONUtils.badResult("failed");
			}
			return JSONUtils.ok();
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
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
	 * 修改人工分词
	 * @param request
	 * @return
	 */
	@RequestMapping(value="modify_proj",method=RequestMethod.POST)
	@ResponseBody
	public String modifyParticiple(HttpServletRequest request){
		try{
			String groupId = request.getParameter("groupId");
			String manualWords = request.getParameter("manualWords");
			UserInfo user = (UserInfo)request.getSession().getAttribute("user");
			
			
			if(StringUtils.isBlank(groupId)){
				return JSONUtils.badResult("failed");
			}
			
			boolean flg = participleManagerService.updateParticiple(groupId, manualWords,user.getUserName());
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
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	@RequestMapping(value="exportParticiple",method=RequestMethod.POST)
	@ResponseBody
	public String exportParticiple(HttpServletResponse response){
		try{
			List<String> parlist = new LinkedList<String>();
			parlist = participleManagerService.getParticiplesByStatus(1);
			if (parlist.size() == 0)
				return JSONUtils.badResult("没有分词可供导出！");
			logger.info("parlist: " + parlist.get(parlist.size()-1));
			String path = "D:"+File.separator+"dic";
			String fileName = "participle-words.dic";
			participleManagerService.exportParticiple(path, fileName, parlist);
			participleManagerService.setExported();
			return JSONUtils.ok("分词库已经导出到"+path+File.separator+fileName);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONUtils.badResult("没有分词可供导出！");
		}
	}
	
	public static void main(String args[]){
		System.out.println("name"+"\n"+"tag"+"\n"+"ddd");
	}

}
