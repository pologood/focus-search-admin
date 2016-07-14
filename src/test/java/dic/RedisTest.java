package dic;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
import cn.focus.search.admin.scheduled.impl.IkScheduledTaskImpl;
import cn.focus.search.admin.service.HotWordService;
import cn.focus.search.admin.service.ParticipleManagerService;
import cn.focus.search.admin.service.RedisService;
import cn.focus.search.admin.service.StopWordsService;
import cn.focus.search.admin.service.impl.HotWordServiceImpl;
import cn.focus.search.admin.service.impl.ParticipleManagerServiceImpl;

 @RunWith(SpringJUnit4ClassRunner.class) 
 @ContextConfiguration(locations={"classpath*:applicationContext.xml"})
 @ActiveProfiles("test")
public class RedisTest {
	
	@Autowired
	public ParticipleManagerService participleManagerService;
	
	@Autowired
	public RedisService redisService;
	@Autowired
	public HotWordServiceImpl hotWordService;
	@Autowired
	public IkScheduledTaskImpl schedule;

	@Autowired
	private StopWordsService stopWordsService;

	
	@Test
	public void testStopword() throws Exception{
		Logger logger = LoggerFactory.getLogger(RedisTest.class);
		System.out.println("########");
		hotWordService.importNewProjName();
	}
	 @Test
	 public void testRedis() throws Exception{
		 Logger logger = LoggerFactory.getLogger(RedisTest.class);
		 Set<String> projNameSetYesterday=redisService.popRedisSet("projNameForPartition", "2016-07-08");
		 System.out.println("xxx");
	 }

	 @Test
	 public void testRedisTime() throws Exception{
		 Logger logger = LoggerFactory.getLogger(RedisTest.class);
		 redisService.setRedis("name","yuan",true,40);
		 Thread.sleep(10000);
		 System.out.println(redisService.getRedis("name"));
	 }

}


