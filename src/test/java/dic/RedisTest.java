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
import cn.focus.search.admin.service.RedisService;
import cn.focus.search.admin.service.impl.ParticipleManagerServiceImpl;

 @RunWith(SpringJUnit4ClassRunner.class) 
 @ContextConfiguration(locations={"classpath*:applicationContext.xml"})
 @ActiveProfiles("test_sce")
public class RedisTest {
	
	@Autowired
	public ParticipleManagerService participleManagerService;
	
	@Autowired
	public RedisService redisService;
	
	@Test
	public void testStopword() throws Exception{
		Logger logger = LoggerFactory.getLogger(RedisTest.class);
		redisService.setRedis("xueqyqingyuan", "ranran");
		logger.info(redisService.getRedis("xueqyqingyuan"));
		redisService.setRedis("xueqyqingyuan", "ranren");
		logger.info(redisService.getRedis("xueqyqingyuan"));

	}

}


