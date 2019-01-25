package jcgroup.repository;

import cn.com.jcgroup.PlanbApplication;
import cn.com.jcgroup.service.domain.PbCompany;
import cn.com.jcgroup.service.domain.PbProject;
import cn.com.jcgroup.service.repositories.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlanbApplication.class)
public class TestRepository {

    private Logger logger = LoggerFactory.getLogger(TestRepository.class);

    @Autowired
    private PbProjectRepository pbProjectRepository;
    @Autowired
    private PbEncourageRepository encourageRepository;
    @Autowired
    private PbCompanyRepository companyRepository;
    @Autowired
    private PbSubProjectRepository subProjectRepository;
    @Autowired
    private PbEncourageFileRepository encourageFileRepository;
    @Autowired
    private PbBillPayForRepository billPayForRepository;
    @Autowired
    private PbBillPayAbleRepository billPayAbleRepository;
    @Autowired
    private PbProCompRelationRepository pbProCompRelationRepository;


    @Test
    public void testGet() {
        PbProject project = pbProjectRepository.findByProCode("XCZ2-LMH");
        List<PbCompany> company = companyRepository.findAllByProCode("GWC-HZGWCBG");
        for (PbCompany com : company) {
            logger.info("========================" + com.getCompanyCode());
        }
    }
}
