package cn.com.jcgroup.service.repositories;

import cn.com.jcgroup.service.domain.PbFinanceAgency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

/**
 * @description 金融机构
 *
 * @author sunchangqing on 2017-06-08 11:46:48
 */
@Repository
public interface PbFinanceAgencyRepository extends JpaRepository<PbFinanceAgency, Integer>{

    @Query(value = "SELECT SEQ_FINANCE_AGENCY_ID.nextval FROM dual",nativeQuery = true)
    Integer findSeqId();

    /**
     * 根据机构编码获取金融机构
     */
    @Query("select agency from PbFinanceAgency agency where agency.financeAgencyCode=:financeAgencyCode ")
    PbFinanceAgency findByFinanceAgencyCode(@Param("financeAgencyCode") String financeAgencyCode);


    @Modifying
    @Query("delete from PbFinanceAgency fa where fa.financeAgencyCode=:financeAgencyCode")
    void deleteByFinanceAgencyCode(@Param("financeAgencyCode") String financeAgencyCode);

    /**
     * 根据机构编码获取金融机构名称
     */
    @Query("select agency.financeAgencyName  from PbFinanceAgency agency where  agency.financeAgencyCode=:financeAgencyCode ")
    String findAgencyName(@Param("financeAgencyCode") String financeAgencyCode);

    /**
     * 更新金融机构
     */
    @Modifying
    @Query("update PbFinanceAgency pb set pb.financeAgencyName=:#{#pbFinanceAgency.financeAgencyName}," +
            "pb.capitalCost=:#{#pbFinanceAgency.capitalCost},pb.creditDate=:#{#pbFinanceAgency.creditDate}," +
            "pb.creditAmount=:#{#pbFinanceAgency.creditAmount},pb.usedAmount=:#{#pbFinanceAgency.usedAmount} " +
            "where pb.financeAgencyCode=:#{#pbFinanceAgency.financeAgencyCode}")
    void updateFinanceAgency(@Param("pbFinanceAgency") PbFinanceAgency financeAgency);
    
    
    /**
     * 统计今年累计金额
     * 
     * @author LiuYong  
     */
    @Query("select sum(pb.usedAmount) as totalMoney  from PbFinanceAgency pb where pb.isShow='1' and pb.createTime>=:startTime ")
    Long countTotalFinanceAgencyMoney(@Param("startTime")Date startTime);
}
