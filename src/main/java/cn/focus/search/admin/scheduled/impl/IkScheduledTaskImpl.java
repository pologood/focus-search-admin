package cn.focus.search.admin.scheduled.impl;

import cn.focus.search.admin.scheduled.IkScheduledTask;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by zhaozhaozhang on 2015/8/5.
 */
@Service
public class IkScheduledTaskImpl  implements IkScheduledTask {

    @Scheduled(cron="0/2 * * * * ?")
    @Override
    public void doTasks() {
        System.out.println("==================333");
    }
}
