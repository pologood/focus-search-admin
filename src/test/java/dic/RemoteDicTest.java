package dic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.focus.search.admin.controller.RemoteDicController;
import cn.focus.search.admin.service.ParticipleManagerService;
import cn.focus.search.admin.service.impl.ParticipleManagerServiceImpl;

 @RunWith(SpringJUnit4ClassRunner.class) 
 @ContextConfiguration(locations={"classpath*:applicationContext.xml"})
 @ActiveProfiles("test_sce")
public class RemoteDicTest {
	
	@Autowired
	public ParticipleManagerServiceImpl participleManagerService;
	
	@Test
	public void testStopword(){
		Logger logger = LoggerFactory.getLogger(RemoteDicTest.class);

/*		logger.info(participleManagerService.getRemoteFinalHouseWord());
		logger.info(participleManagerService.getRemoteHotword());
		logger.info(participleManagerService.getRemoteStopword());*/		
	}

}
