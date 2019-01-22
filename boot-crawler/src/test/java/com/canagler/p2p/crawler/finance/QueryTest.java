package com.canagler.p2p.crawler.finance;

import com.alibaba.fastjson.JSON;
import com.canagler.p2p.crawler.domain.FinanceStatic;
import com.canagler.p2p.crawler.service.FinanceCreditorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QueryTest {

    @Autowired
    FinanceCreditorService financeCreditorService;

    @Test
    public void testQueryInGroup() {
        List<FinanceStatic> result = financeCreditorService.findInGroupByName();
        System.out.println(JSON.toJSONString(result));
    }

}
