package cn.com.jcgroup.admin.manage;

import cn.com.jcgroup.service.constant.ServiceIdentifier;
import cn.com.jcgroup.service.service.PrivateFundSyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 私募基金
 * @author LiuYong on 17/6/19 下午2:37.
 */
@Service
public class PrivateFundManage {

    private static final Logger LOG = LoggerFactory.getLogger(PrivateFundManage.class);

    @Autowired
    private PrivateFundSyncService privateFundSyncService;

    /**
     * 同步私募基金
     *
     * @author LiuYong
     */
    public void synPrivateFund() {
        int page = 0;
        while (true){
            List<String> fundList = privateFundSyncService.findPrivateFundIdsWithPage(page);
            if(fundList == null || fundList.isEmpty()){
                break;
            }else{
                int size = fundList.size();
                LOG.info("[私募基金同步定时任务]当前同步页数为page={},共{}条记录",page+1,size);
                for (String code:fundList) {
                    //同步任务
                    privateFundSyncService.synPrivateFund(code);
                }
                if(size < ServiceIdentifier.PAGE_SIZE){
                    break;
                }else{
                    page ++ ;
                }
            }
        }
    }
}
