package cn.com.jcgroup.service.repositories;

import cn.com.jcgroup.service.domain.PbBillPayFor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description 应付台账
 * @author sunchangqing
 */
@Repository
public interface PbBillPayForRepository extends JpaRepository<PbBillPayFor, Integer>{

    /**
     * 生成ID
     */
    @Query(value = "SELECT SEQ_BILL_PAY_FOR_ID.Nextval FROM dual",nativeQuery = true)
    Integer findSeqId();

    /**
     * 应付台账修改
     */
    @Modifying
    @Query("update PbBillPayFor pb set pb.contractCode=:#{#pbBillPayFor.contractCode}," +
            "pb.contractName=:#{#pbBillPayFor.contractName},pb.cooperA=:#{#pbBillPayFor.cooperA}," +
            "pb.cooperB=:#{#pbBillPayFor.cooperB},pb.cooperC=:#{#pbBillPayFor.cooperC}," +
            "pb.signTime=:#{#pbBillPayFor.signTime},pb.expectedEndTime=:#{#pbBillPayFor.expectedEndTime}," +
            "pb.pricingType=:#{#pbBillPayFor.pricingType},pb.summaryContract=:#{#pbBillPayFor.summaryContract}," +
            "pb.contractMoney=:#{#pbBillPayFor.contractMoney},pb.settleAccount=:#{#pbBillPayFor.settleAccount}," +
            "pb.accumulatedPayable=:#{#pbBillPayFor.accumulatedPayable},pb.payMoney=:#{#pbBillPayFor.payMoney}," +
            "pb.payTime=:#{#pbBillPayFor.payTime},pb.invoiceAmount=:#{#pbBillPayFor.invoiceAmount}," +
            "pb.unpaidAmount=:#{#pbBillPayFor.unpaidAmount},pb.isShow=:#{#pbBillPayFor.isShow} " +
            "where pb.id=:#{#pbBillPayFor.id}")
    void updateById(@Param("pbBillPayFor") PbBillPayFor billPayFor);

    /**
     * 根据合同编码获取应付台账
     */
    @Query("select bf from PbBillPayFor bf where bf.id=:id ")
    PbBillPayFor findById(@Param("id") int id);

    /**
     * 通过工程编码删除应付台账
     */
    @Modifying
    @Query("delete from PbBillPayFor bf where bf.contractCode=:contractCode")
    void deleteByContractCode(@Param("contractCode") String contractCode);

    /**
     * 根据项目编码获取应付台账
     */
    @Query("select bf from PbBillPayFor bf where bf.subProCode=:subProCode and bf.isShow='1' ")
    Slice<PbBillPayFor> findAllByProCode(@Param("subProCode") String subProCode, Pageable pageable);

    /**
     * 根据项目编码获取应付台账
     */
    @Query("select bf from PbBillPayFor bf where bf.subProCode=:subProCode order by bf.signTime desc ")
    List<PbBillPayFor> findAllByProCode(@Param("subProCode") String subProCode);

}
