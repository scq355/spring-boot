package jcgroup.service;

import cn.com.jcgroup.PlanbApplication;
import cn.com.jcgroup.planb.manage.PersonnelManage;
import cn.com.jcgroup.service.service.PersonnelFlowService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Description: service 测试
 * User: sunchangqing
 * Date: 2017-06-12
 * Time: 下午3:19
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlanbApplication.class)
public class TestService {

    private static final Logger log = LoggerFactory.getLogger(TestService.class);

    @Autowired
    PersonnelFlowService personnelFlowService;
    @Autowired
    PersonnelManage personnelManage;

    @Test
    public void testStuff() {
//        log.info(personnelFlowService.querySexByDeptCode("1").toString());
    }

}
