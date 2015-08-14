package cn.focus.search.admin.service.impl;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;
import io.searchbox.core.Update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.elasticsearch.common.netty.util.internal.ConcurrentHashMap;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cn.focus.search.admin.config.Config;
import cn.focus.search.admin.config.Constants;
import cn.focus.search.admin.config.LastTime;
import cn.focus.search.admin.dao.HotWordDao;
import cn.focus.search.admin.dao.ManualPleDao;
import cn.focus.search.admin.dao.ParticipleDao;
import cn.focus.search.admin.dao.StopWordsDao;
import cn.focus.search.admin.model.HotWord;
import cn.focus.search.admin.model.ManualParticiple;
import cn.focus.search.admin.model.Participle;
import cn.focus.search.admin.model.PplResult;
import cn.focus.search.admin.model.ProjInfo;
import cn.focus.search.admin.model.StopWords;
import cn.focus.search.admin.service.ParticipleManagerService;
import cn.focus.search.admin.service.RedisService;
import cn.focus.search.admin.utils.ExcelUtil;
import cn.focus.search.admin.utils.JSONUtils;
import cn.focus.search.admin.utils.StopWordsUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
/**
 * 分词管理
 * @author xuemingtang
 *
 */
@Service
public class ParticipleManagerServiceImpl implements ParticipleManagerService{

	@Autowired
	private JestClient client;
	
	@Value("${ik.url}")
	private String ikurl;
	
	@Value("${ik.urlSmart}")
	private String ikurlSmart;
	
    @Value("${redis.expiredTime}")
    private int expireTime;
	
	@Autowired
	private ManualPleDao manualPleDao;
	
	@Autowired
	private ParticipleDao participleDao;
	
	@Autowired
	private StopWordsDao stopWordsDao;
	
	@Autowired
	private HotWordDao hotWordDao;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private RedisService redisService;
	
	private Logger logger = LoggerFactory.getLogger(ParticipleManagerServiceImpl.class);
	
	private static Map<String,Object> wordMap = new ConcurrentHashMap<String, Object>();
	
	@Autowired
	private StopWordsUtil stopWordsUtil;
	
	public int getWordMapSize(){
		return wordMap.size();
	}
	
	/**
	 * 获取索引和搜索分词
	 * @param words
	 */
	public Map<String,Object> getIkWords(int pageNo,int pageSize,String words){
		
		wordMap.clear();
		String wordArr[] = words.split("\n");
		StringBuilder ikUrl = new StringBuilder(256);
		StringBuilder iksmartUrl = new StringBuilder(256);
		
		List<PplResult> list = new ArrayList<PplResult>();
		Map<String,Object> map = new HashMap<String,Object>();
		
		int start = (pageNo-1)*pageSize;
		int end = pageNo*pageSize-1;
		if(end > wordArr.length-1){
			end  =  wordArr.length-1;
		}
		
		for(int i=start;i<=end;i++){
			
			if(StringUtils.isBlank(wordArr[i])){
				continue;
			}
			list.add(getPplWord(wordArr[i],iksmartUrl,ikUrl));
		}
		
		map.put("list", list);
		map.put("total", wordArr.length);
		
		wordMap.put("wordKey", list);
		
		return map;
	}
	
	/**
	 * 
	 * @param word
	 * @param iksmartUrl
	 * @param ikUrl
	 * @return
	 */
	private PplResult getPplWord(String word,StringBuilder iksmartUrl,StringBuilder ikUrl){
		
		if(StringUtils.isBlank(word)){
			return null;
		}
		
		PplResult ikWords = new PplResult();
		ikWords.setWord(word);
		
		if(ikUrl!=null){
			ikUrl.append(ikurl).append(word);
			String ikWord = restTemplate.getForObject(ikUrl.toString(), String.class);
			ikUrl.delete( 0, ikUrl.length() );
			ikWords.setIndexPplWord(buildIkWords(ikWord));
		}
		if(iksmartUrl!=null){
			iksmartUrl.append(ikurlSmart).append(word);
			String iksmartWord = restTemplate.getForObject(iksmartUrl.toString(), String.class);
			iksmartUrl.delete( 0, iksmartUrl.length() );
			ikWords.setSearchPplWord(buildIkWords(iksmartWord));
		}
		
		return ikWords;
	}
	
	/**
	 * 
	 * @param result
	 * @return
	 */
	private String buildIkWords(String result){
		JSONObject json = JSONObject.parseObject(result);
		JSONArray arr = (JSONArray)json.get("tokens");
		StringBuilder words = new StringBuilder(256);
		
		for(int i=0;i<arr.size();i++){
			JSONObject js = (JSONObject)arr.get(i);
			words.append(js.get("token")).append(" ");
		}	
		
//		System.out.println(words.toString());
		return words.toString();
	}
	
	/**
	 * 传入query
	 * @param groupId
	 * @param projName
	 * @param from
	 * @param size
	 * @return
	 */
	public JSONObject searchProj(String groupId,String projName,int from,int size){
		
		BoolQueryBuilder qb = QueryBuilders.boolQuery();
		
		if(StringUtils.isNotBlank(groupId)){
			qb.must(QueryBuilders.termQuery("groupId",groupId));
		}
		
		if(StringUtils.isNotBlank(projName)){
			qb.must(QueryBuilders.matchQuery("projName", projName));
		}
		//es的起始from是0
		return baseSearch(qb,from,size);
	}
	
	/**
	 * 搜索楼盘
	 * @param query
	 * @param from
	 * @param size
	 * @return
	 */
	public JSONObject baseSearch(QueryBuilder query,int from,int size){
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(query)
		.from(from)
		.size(size);
//		.fields(Config.EsConfig.fileds);
//		.fetchSource(FetchSourceContext.FETCH_SOURCE.includes(Config.EsConfig.fileds));
		
		Search search = new Search.Builder(searchSourceBuilder.toString())
	    .setHeader("X-SCE-ES-PASSWORD", "")
	    .addIndex(Config.EsConfig.indexName)
	    .addType(Config.EsConfig.typeName)
	    .build();
		
		try {
			JestResult result = client.execute(search);
			
			System.out.println(result.getJsonString());
			if(!result.isSucceeded()){
				logger.error("es search error ", result.getErrorMessage());
				return JSONUtils.badJSONResult("search exception");
			}
			
			return buildSearchResult(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONUtils.badJSONResult("search exception");
		}
		
	}
	
	/**
	 * 构建返回结果
	 * @param jestResult
	 * @return
	 */
	private JSONObject buildSearchResult(JestResult jestResult){
		
		JSONObject obj = JSON.parseObject(jestResult.getJsonString());
		int total = obj.getJSONObject("hits").getIntValue("total");
		JSONArray array = obj.getJSONObject("hits").getJSONArray("hits");
		List<ProjInfo> list = new ArrayList<ProjInfo>();
		JSONObject js = new JSONObject();
		StringBuilder iksmartUrl = new StringBuilder(256);
		
		for(int i=0;i<array.size();i++){
			
			JSONObject pJson = array.getJSONObject(i).getJSONObject("_source");
			
			list.add(buildProjInfo(pJson,iksmartUrl));
		}
		
		js.put("total", total);
		js.put("projList", list);
		
		return js;
		
	}
	
	/**
	 * 
	 * @param pJson
	 * @param iksmartUrl
	 * @return
	 */
	private ProjInfo buildProjInfo(JSONObject pJson,StringBuilder iksmartUrl){
		ProjInfo p = new ProjInfo();
		
		p.setGroupId(pJson.getLongValue("groupId"));
		
		p.setProjName(pJson.getString("projName"));
		if(StringUtils.isNotBlank(p.getProjName())){
			p.setProjNameWords(getPplWord(p.getProjName(),iksmartUrl,null).getSearchPplWord());
		}
		
		p.setProjNameOther(pJson.getString("projNameOther"));
		if(StringUtils.isNotBlank(p.getProjNameOther())){
			p.setProjNameOtherWords(getPplWord(p.getProjNameOther(),iksmartUrl,null).getSearchPplWord());
		}
		
//		List<String> projNoLink = pJson.getJSONArray("projNoLink").parseArray(pJson.getString("projNoLink"), String.class);
		p.setProjNoLink(pJson.getString("noLinks"));
        if(StringUtils.isNotBlank(p.getProjNoLink())){
        	p.setProjNoLinkWords(getPplWord(pJson.getString("noLinks"),iksmartUrl,null).getSearchPplWord());
        }		
		
		p.setProjAddress(pJson.getString("projAddress"));
		if(StringUtils.isNotBlank(p.getProjAddress())){
			p.setProjAddressWords(getPplWord(p.getProjAddress(),iksmartUrl,null).getSearchPplWord());
		}
		
		p.setKfsName(pJson.getString("kfsName"));
		if(StringUtils.isNotBlank(p.getKfsName())){
			p.setKfsNameWords(getPplWord(p.getKfsName(), iksmartUrl, null).getSearchPplWord());
		}
		
		p.setManualWords(pJson.getString("extField"));
		
		return p;
	}
	
	/**
	 * 更新人工分词字段
	 * @param groupId
	 * @param manualWord
	 * @return
	 */
	public boolean updateManualWords(String groupId,String manualWords,String userName)throws Exception{
		
		String FILE_NAME = "extField";
		
		boolean flg = updateIndexById(Config.EsConfig.indexName,Config.EsConfig.typeName,groupId,FILE_NAME,manualWords,1);
		
		ManualParticiple manualParticiple = new ManualParticiple();
		manualParticiple.setGroupId(Long.valueOf(groupId));
		manualParticiple.setManualWords(manualWords);
		manualParticiple.setEditor(userName);
		
		if(flg){
			manualPleDao.insertManualWords(manualParticiple);
		}
		
		return flg;
	}
	
	
	 /**
     *  修改索引docment中某个field内容，根据docmentId
     * @param typeName
     * @param id
     * @param fieldName
     * @param context
     * @param flag 1:替换原始field 内容，2：向原始field内容后追加
     * @return
     */
    public boolean updateIndexById(String indexName,String typeName,String id,String fieldName,String context,int flag) {
        boolean rs = false;
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put(fieldName, context);
        JSONObject jsonObject = new JSONObject();
        if (flag == 1) {
            //直接替换
            jsonObject.put("script", "ctx._source." + fieldName + "=" + fieldName);
        } else if (2 == flag) {
            //向后追加内容
            jsonObject.put("script", "ctx._source." + fieldName + "+=" + fieldName);
        } else {
            jsonObject.put("script", "ctx._source." + fieldName + "=" + fieldName);
        }
        jsonObject.put("params", map);
        Update.Builder builder = new Update.Builder(jsonObject.toString()).index(indexName).type(typeName).id(id);
        JestResult result;
        try {
            result = client.execute(builder.build());
            if (result == null || !result.isSucceeded()) {
                logger.error("updateIndexById Exception ", result.getJsonString());
            } else {
                rs = true;
            }
        } catch (Exception e) {
            logger.info("更新索引异常!id=" + id, e);
        }
        return rs;
    }
    

    

    


	@Override
	public List<Participle> getParticipleList(int status) {
		List<Participle> list = new LinkedList<Participle>();
		try {
			list = participleDao.getParticipleList(status);
		} catch (Exception e) {
			logger.error("获取未分词数据异常!", e);
		}
		return list;
	}

	@Override
	public int updateParticiple(Participle participle) {
		int result = 0;
		try {
			result = participleDao.updateParticiple(participle);
		} catch (Exception e) {
			logger.error("更新数据分词结果异常!", e);
		}
		return result;
	}

	//更新新房（final_house）词库。
	@Override
	public String updateIK() {
		// TODO Auto-generated method stub
		//System.out.println("！！！updateIK");
		String flag="failed";
		if (LastTime.setlTime()==1) flag="success";
		return flag;
	}
	

	//更新停用词词库。
	@Override
	public String updateStopwordIK() {
		// TODO Auto-generated method stub
		//System.out.println("@@@@@@updateStop");
		String flag="failed";
		if (LastTime.setStopwordlTime()==1) flag="success";
		return flag;
	}
	

	//更新临时热词词库。
	@Override
	public String updateHotwordIK() {
		// TODO Auto-generated method stub
		//System.out.println("######updateHot");
		String flag="failed";
		if (LastTime.setHotwordlTime()==1) flag="success";
		return flag;
	}
	
	public String getRemoteFinalHouseWord(){
		StringBuffer str=new StringBuffer();
		String strWords=null;
		String key="cn.focus.search.admin.RemoteDicController.getRemoteFinalHouseWord"+Long.toString(LastTime.getFinal_house_lTime());
		
		// read from redis firstly.
		strWords=redisService.getRedis(key);
		if(strWords!=null){
			logger.info("readed FinalHouseWord from redis.");
			return strWords;	
		}


		
		// read from mysql.
		List<Participle> list = new LinkedList<Participle>();
		try {
			
			list=participleDao.getTotalFinalHouseParticipleList();
		
			logger.info("readed FinalHouseWord from mysql");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("getFinalHouseParticipleListException",e);
			e.printStackTrace();
		}
		for(int i=0;i<list.size();i++){
			
			String value=list.get(i).getParticiples();
			String[] words = value.split("[, ， ]");//以全角或半角逗号或空格作为分隔符。
			for(int j=0;j<words.length;j++){
					str.append(words[j]);
					str.append("\n");

			}
		}
		strWords=str.toString();
		
		// write to redis.
		
		redisService.setRedis(key, strWords, true, expireTime);
		logger.info("writed FinalHouseWord to redis.");
		
		return strWords;
	}
	
	

	@Override
	public String getRemoteStopword() {
		// TODO Auto-generated method stub
		StringBuffer str=new StringBuffer();
		String strWords=null;
		String key="cn.focus.search.admin.RemoteDicController.getRemoteStopword"+Long.toString(LastTime.getStopword_lTime());
		// read from redis firstly.
		
			
		strWords=redisService.getRedis(key);
		if(strWords!=null){
			logger.info("readed stopword from redis.");
			return strWords;	
		}
		
		
		// read from mysql.
		List<StopWords> list = new LinkedList<StopWords>();
		try {
			
			list=stopWordsDao.getTotalStopWordList();
			logger.info("readed RemoteStopWord from mysql");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("getStopWordsException",e);
		}
		for(int i=0;i<list.size();i++){
			
			String word=list.get(i).getName();
			str.append(word);
			str.append("\n");
			}
		
		
		strWords=str.toString();
		
		// write to redis.
		
		redisService.setRedis(key, strWords, true, expireTime);
		logger.info("writed RemoteStopword to redis");
		
		return strWords;

	}

	@Override
	public String getRemoteHotword() {
		// TODO Auto-generated method stub
		StringBuffer str=new StringBuffer();
		
		// read from redis firstly.
		String key="cn.focus.search.admin.RemoteDicController.getRemoteHotword"+Long.toString(LastTime.getHotword_lTime());
		String strWords=redisService.getRedis(key);
		if(strWords!=null){
			
			logger.info("readed RemoteHotword from redis.");
			return strWords;
		}
		
		// read from mysql.
		List<HotWord> list = new LinkedList<HotWord>();
		try {
			
		    list=hotWordDao.getTotalHotWordList();
			logger.info("readed RemoteHotWord from mysql");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("get HotWordException",e);
			e.printStackTrace();
		}
		for(int i=0;i<list.size();i++){
			
			String word=list.get(i).getName();
					str.append(word);
					str.append("\n");
		}
		strWords=str.toString();
		// write to redis.
		
		redisService.setRedis(key, strWords, true, expireTime);
		logger.info("write remoteHotWord to redis.");
		
		return strWords;
	}

	////判断该词是否已经已经在词库(非停用词)中。
	@Override
	public boolean isDuplicate(String word) {
		// TODO Auto-generated method stub
		boolean flag=false;
		StringBuffer ikUrl=new StringBuffer();
		ikUrl.append(ikurl).append(word);
		String ikWord = restTemplate.getForObject(ikUrl.toString(), String.class);
		ikUrl.delete( 0, ikUrl.length() );
		
		JSONObject json = JSONObject.parseObject(ikWord);
		JSONArray arr = (JSONArray)json.get("tokens");
		
		for(int i=0;i<arr.size();i++){
			JSONObject js = (JSONObject)arr.get(i);
			if (js.getString("token").equals(word)){
				flag=true;
			}
		}	
		
		return flag;
	}
	
	//重新加载所有远程词库。
	public boolean reloadRemoteDic(){
	    if(LastTime.setReloadTime()==1) return true;
	    else return false;
	}
	
	@Override
	public String getIkUrl() {
		// TODO Auto-generated method stub
		return ikurl;
	}

    /**
     * 导出方法1
     */
	
	@Override
    public boolean exportExcel(HttpServletRequest request,
			HttpServletResponse response,String exportName) throws IOException{
    	
    	List<PplResult> list = (List<PplResult>)wordMap.get("wordKey");
    	if(list == null || list.size()==0){
    		return false;
    	}
    	
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-Disposition", "attachment;filename=\""
				+ exportName + "\"");
		OutputStream os = null;
		os = response.getOutputStream();
		 
    	// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("分词效果");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("单词");
		cell.setCellStyle(style);

		cell = row.createCell((short) 1);
		cell.setCellValue("索引分词");
		cell.setCellStyle(style);

		cell = row.createCell((short) 2);
		cell.setCellValue("搜索分词");
		cell.setCellStyle(style);

		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，

		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow((int) i + 1);
			PplResult pplResult =  list.get(i);
			// 第四步，创建单元格，并设置值
			row.createCell((short) 0).setCellValue(pplResult.getWord());
			row.createCell((short) 1).setCellValue(pplResult.getIndexPplWord());
			row.createCell((short) 2).setCellValue(pplResult.getSearchPplWord());
		
		}
		// 第六步，将文件存到指定位置
		try {
//			FileOutputStream fout = new FileOutputStream("D:/data/words.xls");
			wb.write(os);
			os.flush();
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			return false;
		}finally{
			os.close();
		}
    }
	
	@Override
	public boolean exportParticiple(String path, String fileName, List<String> list)throws IOException {
		File file = new File(path);
		if (!file.exists())
		{
			file.mkdir();
		}
		file = new File(path+File.separator+fileName);
		OutputStream os = null;
		os = new FileOutputStream(file);
		
		// 第二步，将文件存到指定位置
		try {
			for (String str : list)
			{
				//System.out.println("!!!!!!!!"+ str);
				String[] parti = stopWordsUtil.getParticiple(str);
				if (parti == null || parti.length == 0)
					continue;
				for (String participle : parti)
				{
					os.write(participle.getBytes());
					os.write('\n'); 
					os.flush();
				}				
			}
			return true;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return false;
			}finally{
				os.close();
			}
	}

	@Override
	public int setExported() {
		// TODO Auto-generated method stub
		int s = 0;
        try {
            s = participleDao.setExported();
        } catch (Exception e) {
            logger.error("删除停止词数据异常!", e);
        }
        return s;
	}

	@Override
	public List<String> getParticiplesByStatus(int status) throws Exception {
		// TODO Auto-generated method stub
		List<String> list=new LinkedList<String>();
        try {
            list = participleDao.getParticiplesByStatus(status);
            logger.info("status: "+status);
        } catch (Exception e) {
            logger.error("获取热词数据异常!", e);
        }
        return list;
	}
}
