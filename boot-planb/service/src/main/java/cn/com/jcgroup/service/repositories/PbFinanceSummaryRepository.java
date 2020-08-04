package cn.com.jcgroup.service.repositories;

import cn.com.jcgroup.service.domain.PbFinanceSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description 汇总
 * @author sunchangqing
 */
@Repository
public interface PbFinanceSummaryRepository extends JpaRepository<PbFinanceSummary, Integer>{

    @Query(value = "SELECT seq_finance_summary_id.nextval FROM dual",nativeQuery = true)
    Integer findSeqId();

    /**
     * 根据公司编码获取汇总信息列表
     */
    @Query("select fs from PbFinanceSummary fs where fs.companyCode=:companyCode")
    List<PbFinanceSummary> findAllByCompanyCode(@Param("companyCode") String companyCode);
    
    @Modifying
    @Query("delete  from PbFinanceSummary pb where pb.id=:id")
    Integer deleteFinanceSummary(@Param("id")int id);
    
    @Modifying
    @Query("update PbFinanceSummary  pb  set pb.updateTime=:#{#financeSummary.updateTime} ," +
            "pb.type=:#{#financeSummary.type},pb.privateFund=:#{#financeSummary.privateFund}," +
            "pb.agency=:#{#financeSummary.agency},pb.financialAgency=:#{#financeSummary.financialAgency}," +
            "pb.remain=:#{#financeSummary.remain},pb.financialCostAvg=:#{#financeSummary.financialCostAvg}," +
            "pb.amountRaised=:#{#financeSummary.amountRaised} where  pb.id=:#{#financeSummary.id}")
    Integer updateFinanceSummary(@Param("financeSummary")PbFinanceSummary pbFinanceSummary);
    
}
