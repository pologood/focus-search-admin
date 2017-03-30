package cn.focus.search.admin.controller;

import cn.focus.search.admin.config.Constants;
import cn.focus.search.admin.config.LastTime;
import cn.focus.search.admin.model.HotWord;
import cn.focus.search.admin.model.UserInfo;
import cn.focus.search.admin.service.HotWordService;
import cn.focus.search.admin.service.ParticipleManagerService;
import cn.focus.search.admin.utils.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/hot")
public class HotWordsController {

	private Logger logger = LoggerFactory.getLogger(HotWordsController.class);
	
	@Autowired
	private HotWordService hotWordService;
	
	@Autowired
	private ParticipleManagerService pmService;
		
	@Autowired
	private LastTime lastTime;
	/***
	 * 获取所有热词的数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="loadHot",method=RequestMethod.POST)
	@ResponseBody
	public String getHotWordsList(HttpServletRequest request){
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
			List<HotWord> list = new LinkedList<HotWord>();
			list = hotWordService.getHotWordList(rowBounds);
			int totalNum = hotWordService.getTotalNum();
			
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
	 * 批量添加热词的数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="addHot",method=RequestMethod.POST)
	@ResponseBody
	public String InsertHotWords(HttpServletRequest request)
	{
		try{
			String existWord = "";
			String stype = request.getParameter("type");
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
			hotList = hotWordService.getHotList(type, hotWords, editor, Constants.ORI_STATUS);
			for (HotWord hw : hotList)
			{
				List<HotWord> list = new LinkedList<HotWord>();
				list = hotWordService.getHotWordListByName(hw.getName());
				if (list.size() > 0)
					continue;
				if (pmService.isDuplicate(hw.getName(),type))
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
			if (len < 2)
				existWord = "添加成功！";
			else
			{
				existWord = existWord.substring(0, len-1);
				existWord += "等词词库中已经存在，其它词已经添加成功";
			}
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
		
		return lastTime.setLastModifiedTime();
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
	
	/***
	 * 删除热词
	 * @param request
	 * @return
	 */
	@RequestMapping(value="exportHot",method=RequestMethod.GET)
	@ResponseBody
	public String exportHotWords(@RequestParam(value="type", required=false, defaultValue="1") Integer type,HttpServletRequest request,HttpServletResponse response)
	{
		try{
			String words = hotWordService.getHotWordToDicByType(type);
			if (words.length() == 0) logger.info("没有热词可供导出！");
			
/*			//字节流方式下载文件，并且先在服务器端生成文件。
 			String path = "D:"+File.separator+"dic";			
			String fileName = "hot-words.dic";
			logger.info("fileName: "+fileName);
			hotWordService.exportHot(path, fileName, hotlist);
			hotWordService.setExported();
			
			File downloadFile=new File(path+File.separator+fileName);
			FileInputStream inStream = new FileInputStream(downloadFile);
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setContentLength((int) downloadFile.length());
			
	        // forces download
	        String headerKey = "Content-Disposition";
	        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
	        response.setHeader(headerKey, headerValue);
	        
	        // obtains response's output stream
	        OutputStream outStream = response.getOutputStream();
	         
	        byte[] buffer = new byte[4096];
	        int bytesRead = -1;
	         
	        while ((bytesRead = inStream.read(buffer)) != -1) {
	            outStream.write(buffer, 0, bytesRead);
	        }
	         
	        inStream.close();
	        outStream.close(); */
			
			//直接写入流文件，下载文件。
			String fileName = "hot-words.dic";
			response.setContentType("APPLICATION/OCTET-STREAM");
			
	        // forces download
	        String headerKey = "Content-Disposition";
	        String headerValue = String.format("attachment; filename=\"%s\"", fileName);
	        response.setHeader(headerKey, headerValue);
	        
			PrintWriter pw=response.getWriter();
			pw.append(words);
			pw.close();
			
			hotWordService.setExported(type);
			return JSONUtils.ok("词库已经导出到"+fileName);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONUtils.badResult("没有热词可供导出！");
		}
	}
}
