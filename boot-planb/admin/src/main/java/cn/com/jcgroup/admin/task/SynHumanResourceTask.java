package cn.com.jcgroup.admin.task;

import cn.com.jcgroup.admin.constant.AdminIdentifier;
import cn.com.jcgroup.admin.manage.HumanResourceManage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 同步人员信息
 * @author LiuYong on 17/6/18 下午2:34.
 */
@Component
public class SynHumanResourceTask extends SynTask {
    
    private static final Logger LOG = LoggerFactory.getLogger(SynHumanResourceTask.class);
    @Autowired
    private HumanResourceManage humanResourceManage;

    @Scheduled(cron = AdminIdentifier.Cron.SYN_HUMAN_RESOUCE)
    @Override
    public void schedule() {
        LOG.info("[人员信息同步定时任务]开始执行...");
        try{
            synTask();
        }catch (Exception e){
            LOG.error("[人员信息同步定时任务]任务异常",e);
        }
        LOG.info("[人员信息同步定时任务]执行完成...");

    }

    @Override
    public void doTask() {
        humanResourceManage.synHumanResource();
    }
}
