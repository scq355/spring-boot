package jcgroup.service;

import cn.com.jcgroup.PlanbApplication;
import cn.com.jcgroup.service.service.FinanceDetailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Description: 机构测试
 * User: sunchangqing
 * Date: 2017-06-19
 * Time: 下午12:10
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlanbApplication.class)
public class AgencyServiceTest {

    private static final Logger log = LoggerFactory.getLogger(AgencyServiceTest.class);

    @Autowired
    FinanceDetailService financeDetailService;

    @Test
    public void testAgency() {
        String companyCode = "HZYXTZGL";
        String type = "2";
//         JSONArray jsonArray =financeDetailService.findAngecyByComCodeAndType(companyCode, type);
//         log.info("=================" + jsonArray.toString());
    }
}
