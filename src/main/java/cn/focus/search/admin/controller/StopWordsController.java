package cn.focus.search.admin.controller;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import cn.focus.search.admin.config.Constants;
import cn.focus.search.admin.model.Participle;
import cn.focus.search.admin.model.StopWords;
import cn.focus.search.admin.model.UserInfo;
import cn.focus.search.admin.service.ParticipleManagerService;
import cn.focus.search.admin.service.StopWordsService;
import cn.focus.search.admin.utils.JSONUtils;
import cn.focus.search.admin.utils.StopWordsUtil;

@Controller
@RequestMapping("/stop")
public class StopWordsController {

	private Logger logger = LoggerFactory.getLogger(ParticipleManagerController.class);
	
	@Autowired
	private StopWordsService stopWordsService;
	@Autowired
	private ParticipleManagerService pmService;
	@Autowired
	private StopWordsUtil stopWordsUtil;
	/***
	 * 批量获取停止词的数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="loadStop",method=RequestMethod.POST)
	@ResponseBody
	public String getStopWordsList(HttpServletRequest request){
		try{
			JSONObject json = new JSONObject();
			int pageSize = 10;
			int pageNo = 1;
			
			if(StringUtils.isNotBlank(request.getParameter("page"))){
				pageNo = Integer.valueOf(request.getParameter("page"));
			}
			if(StringUtils.isNotBlank(request.getParameter("rows"))){
				pageSize = Integer.valueOf(request.getParameter("rows"));
			}
			
			RowBounds rowBounds=new RowBounds((pageNo-1)*pageSize, pageSize);
			List<StopWords> list = new LinkedList<StopWords>();
			list = stopWordsService.getStopWordsList(rowBounds);
			int totalNum = stopWordsService.getTotalNum();
			
			JSONArray ja=new JSONArray();
            ja.addAll(list);			
			json.put("total", String.valueOf(totalNum));
			json.put("rows", ja);
			
			System.out.println(JSON.toJSONString(ja,SerializerFeature.WriteDateUseDateFormat));
			return JSON.toJSONString(json,SerializerFeature.WriteDateUseDateFormat);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			return JSONUtils.badResult("failed");
		}
	}
	
	/***
	 * 批量添加停止词的数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="addStop",method=RequestMethod.POST)
	@ResponseBody
	public String InsertStopWords(HttpServletRequest request)
	{
		try{
			
			String stype = request.getParameter("type");
			//System.out.println("！停止词TYPE："+stype);
			if(StringUtils.isBlank(stype)){
				return JSONUtils.badResult("failed");
			}
			UserInfo user = (UserInfo)request.getSession().getAttribute("user");
			String editor = user.getUserName();
			int type = Integer.parseInt(stype);
			String stopWords = request.getParameter("stopWords");
			
			if (stopWords == null || stopWords =="" || type < 1 || type > 2)
				return JSONUtils.badResult("failed");
			List<StopWords> stopList = new LinkedList<StopWords>();
			stopList = stopWordsUtil.getStopList(type, stopWords, editor, Constants.ORI_STATUS);
			for (StopWords sw : stopList)
			{
				//System.out.println("sw: "+ sw.getName()+"  "+sw.getType()+"  "+sw.getEditor()+sw.getCreateTime());
				List<StopWords> list = new LinkedList<StopWords>();
				list = stopWordsService.getStopWordsListByName(sw.getName());
				logger.info("list大小： " + list.size());
				if (list.size() > 0)
					continue;
				int result = stopWordsService.insertStopWords(sw);
				if(result<1){
					logger.info(sw + "插入失败!");
					return JSONUtils.badResult("failed");
				}
			}
			return JSONUtils.ok();
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONUtils.badResult("failed");
		}
	}
	
	/***
	 * 更新停止词词库
	 * @param request
	 * @return
	 */
	@RequestMapping(value="updateStop",method=RequestMethod.POST)
	@ResponseBody
	public String updateStopDick(HttpServletRequest request)
	{
		return pmService.updateStopwordIK();
	}
	
	/***
	 * 删除停止词
	 * @param request
	 * @return
	 */
	@RequestMapping(value="delStop",method=RequestMethod.POST)
	@ResponseBody
	public String deleteHotDic(HttpServletRequest request){
		
		try{
			String sid = request.getParameter("id");
			if(StringUtils.isBlank(sid)){
				return JSONUtils.badResult("failed");
			}
			int id = Integer.parseInt(sid);
			String name = request.getParameter("name");
			//System.out.println("id:"+id +"name:"+name);
			int result = stopWordsService.delStopWordsById(id);
			if(result<1){
				logger.info(id + "删除失败!");
				return JSONUtils.badResult("failed");
			}
			return JSONUtils.ok();
						
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			return JSONUtils.badResult("failed");
		}
	}

	@RequestMapping(value="exportStop",method=RequestMethod.POST)
	@ResponseBody
	public String exportStop(HttpServletResponse response){
		try{
			List<String> stoplist = new LinkedList<String>();
			stoplist = stopWordsService.getStopWordnameByStatus(Constants.ORI_STATUS);
			if (stoplist.size() == 0)
				return JSONUtils.badResult("没有停止词可供导出！");
			logger.info("stoplist: " + stoplist.get(stoplist.size()-1));
			String path = "D:"+File.separator+"dic";
			String fileName = "stop-words.dic";
			stopWordsService.exportStop(path, fileName, stoplist);
			//pmService.exportHot(request, response, fileName);
			stopWordsService.setExported();
			return JSONUtils.ok("停止词库已经导出到"+path+File.separator+fileName);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONUtils.badResult("没有停止词可供导出！");
		}
	}
}
