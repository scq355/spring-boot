package jcgroup.enums;

import cn.com.jcgroup.PlanbApplication;
import cn.com.jcgroup.service.enums.CompanyEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: sunchangqing
 * Date: 2017-06-12
 * Time: 下午3:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlanbApplication.class)
public class TestEnum {

    private static final Logger logger = LoggerFactory.getLogger(TestEnum.class);

    @Test
    public void testEnums() {
        List<String> departList = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        departList.add("1");
        departList.add("2");
        departList.add("3");
        logger.info(CompanyEnum.FIFTH.getName() + CompanyEnum.FIFTH.getCode());

    }
}
