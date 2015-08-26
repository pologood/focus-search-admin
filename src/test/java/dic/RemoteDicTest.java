package dic;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import cn.focus.search.admin.controller.RemoteDicController;
import cn.focus.search.admin.model.Participle;
import cn.focus.search.admin.service.ParticipleManagerService;
import cn.focus.search.admin.service.impl.ParticipleManagerServiceImpl;

 @RunWith(SpringJUnit4ClassRunner.class) 
 @ContextConfiguration(locations={"classpath*:applicationContext.xml"})
 @ActiveProfiles("test_sce")
public class RemoteDicTest {
	
	@Autowired
	public ParticipleManagerService participleManagerService;
	
	@Test
	public void testStopword() throws Exception{
		Logger logger = LoggerFactory.getLogger(RemoteDicTest.class);
		logger.info(""+participleManagerService.getRemoteFinalHouseWord());
	/*	logger.info(""+participleManagerService.searchProjToMidifyNum("","王"));*/
/*		JSONObject json = new JSONObject();
		List<Participle> list=null;*/
		//logger.info(participleManagerService.searchProjToMidify(0,10));
/*		list=participleManagerService.searchProjToMidify(0, 10);
		JSONArray ja=new JSONArray();
		ja.addAll(list);
		json.put("total", String.valueOf(list.size()));
		json.put("rows", ja);
		
		System.out.println(JSON.toJSONString(json,SerializerFeature.WriteDateUseDateFormat));*/
		/*		logger.info(participleManagerService.getRemoteHotword());
		logger.info(participleManagerService.getRemoteStopword());*/	
		//logger.info(String.valueOf(participleManagerService.getParticipleList(0).size()));
	}

}

