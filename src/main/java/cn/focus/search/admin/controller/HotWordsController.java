package cn.focus.search.admin.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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

import cn.focus.search.admin.model.HotWord;
import cn.focus.search.admin.model.UserInfo;
import cn.focus.search.admin.service.HotWordService;
import cn.focus.search.admin.service.ParticipleManagerService;
import cn.focus.search.admin.utils.JSONUtils;
import cn.focus.search.admin.utils.StopWordsUtil;

@Controller
@RequestMapping("/hot")
public class HotWordsController {

	private Logger logger = LoggerFactory.getLogger(HotWordsController.class);
	
	@Autowired
	private HotWordService hotWordService;
	
	@Autowired
	private ParticipleManagerService pmService;
	
	@Autowired
	private StopWordsUtil stopWordsUtil;
	/***
	 * 批量获取停止词的数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="loadHot",method=RequestMethod.POST)
	@ResponseBody
	public String getStopWordsList(HttpServletRequest request){
		try{
			JSONObject json = new JSONObject();
			List<HotWord> list = new LinkedList<HotWord>();
			list = hotWordService.getHotWordList();
			int size = list.size();
			
			System.out.println("list的大小是"+size);
			JSONArray jsArray = new JSONArray();
			jsArray.addAll(list);
			System.out.println(JSON.toJSONString(jsArray,SerializerFeature.WriteDateUseDateFormat));
			return JSON.toJSONString(jsArray,SerializerFeature.WriteDateUseDateFormat);
						
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
	@RequestMapping(value="addHot",method=RequestMethod.POST)
	@ResponseBody
	public String InsertStopWords(HttpServletRequest request)
	{
		try{
			String existWord = "";
			String stype = request.getParameter("type");
			//System.out.println("！停止词TYPE："+stype);
			if(StringUtils.isBlank(stype)){
				return JSONUtils.badResult("failed");
			}
			UserInfo user = (UserInfo)request.getSession().getAttribute("user");
			String editor = user.getUserName();
			int type = Integer.parseInt(stype);
			String hotWords = request.getParameter("hotWords");
			if (hotWords == null || hotWords =="" || type < 1 || type > 2)
				return JSONUtils.badResult("failed");
			List<HotWord> hotList = new LinkedList<HotWord>();
			hotList = stopWordsUtil.getHotList(type, hotWords, editor, 1);
			for (HotWord hw : hotList)
			{
				//System.out.println("sw: "+ hw.getName()+"  "+hw.getType()+"  "+hw.getEditor()+hw.getCreateTime());
				List<HotWord> list = new LinkedList<HotWord>();
				list = hotWordService.getHotWordListByName(hw.getName());
				logger.info("list大小： " + list.size());
				if (list.size() > 0)
					continue;
				if (pmService.isDuplicate(hw.getName()))
				{
					existWord += hw.getName()+",";
					continue;
				}
				int result = hotWordService.insertHotWord(hw);
				if(result<1){
					logger.info(hw + "插入失败!");
					return JSONUtils.badResult("failed");
				}
			}
			int len = existWord.length();
			existWord = existWord.substring(0, len-1);
			existWord += "等词词库中已经存在，其它词已经添加成功";
			return JSONUtils.ok(existWord);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONUtils.badResult("failed");
		}
	}
	
	/***
	 * 更新热词词库
	 * @param request
	 * @return
	 */
	@RequestMapping(value="updateHot",method=RequestMethod.POST)
	@ResponseBody
	public String updateHotDic(HttpServletRequest request){
		
		return pmService.updateHotwordIK();
	}
	
	/***
	 * 删除热词
	 * @param request
	 * @return
	 */
	@RequestMapping(value="delHot",method=RequestMethod.POST)
	@ResponseBody
	public String deleteHotDic(HttpServletRequest request){
		
		try{
			String sid = request.getParameter("id");
			if(StringUtils.isBlank(sid)){
				return JSONUtils.badResult("failed");
			}
			int id = Integer.parseInt(sid);
			String name = request.getParameter("name");
			//System.out.println("!!!!!!@@@@@id:"+id +"  name:"+name);
			int result = hotWordService.delHotWordById(id);
			if(result<1){
				logger.info("wordId: " + id + "删除失败!");
				return JSONUtils.badResult("failed");
			}
			return JSONUtils.ok();
						
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			return JSONUtils.badResult("failed");
		}
	}
}
