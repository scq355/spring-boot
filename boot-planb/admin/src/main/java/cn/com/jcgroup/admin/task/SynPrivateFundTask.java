package cn.com.jcgroup.admin.task;

import cn.com.jcgroup.admin.constant.AdminIdentifier;
import cn.com.jcgroup.admin.manage.PrivateFundManage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 私募基金同步定时任务
 * @author LiuYong on 17/6/3 下午3:12.
 */
@Component
public class SynPrivateFundTask extends SynTask {

    private static final Logger LOG = LoggerFactory.getLogger(SynPrivateFundTask.class);

    @Autowired
    private PrivateFundManage privateFundManage;

    @Scheduled(cron = AdminIdentifier.Cron.SYN_PRIVATE_FUND)
    @Override
    public void schedule() {
        LOG.info("[私募基金同步定时任务]开始执行...");
        try{
            synTask();
        }catch (Exception e){
            LOG.error("[私募基金同步定时任务]任务异常",e);
        }
        LOG.info("[私募基金同步定时任务]执行完成...");
        
    }

    @Override
    public void doTask() {
        privateFundManage.synPrivateFund();
    }
}
