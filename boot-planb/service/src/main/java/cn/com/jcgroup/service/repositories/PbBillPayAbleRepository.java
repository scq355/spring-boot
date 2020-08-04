package cn.com.jcgroup.service.repositories;

import cn.com.jcgroup.service.domain.PbBillPayAble;
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
 * @Description 应收台账
 * @Author SunChangqing
 */
@Repository
public interface PbBillPayAbleRepository extends JpaRepository<PbBillPayAble, Integer> {

    @Query(value = "SELECT SEQ_BILL_PAY_ABLE_ID.nextval FROM dual",nativeQuery = true  )
    Integer findSeqId();

    @Modifying
    @Query("update PbBillPayAble pb set pb.contractCode=:#{#pbBillPayAble.contractCode},pb.contractName=:#{#pbBillPayAble.contractName}," +
            "pb.cooperA=:#{#pbBillPayAble.cooperA},pb.cooperB=:#{#pbBillPayAble.cooperB},pb.signTime=:#{#pbBillPayAble.signTime}," +
            "pb.agreementMoney=:#{#pbBillPayAble.agreementMoney},pb.cooperationPeriod=:#{#pbBillPayAble.cooperationPeriod}," +
            "pb.payAbleItem=:#{#pbBillPayAble.payAbleItem},pb.payableDetails=:#{#pbBillPayAble.payableDetails},pb.payMoney=:#{#pbBillPayAble.payMoney}," +
            "pb.payableWay=:#{#pbBillPayAble.payableWay},pb.payableNode=:#{#pbBillPayAble.payableNode},pb.payTime=:#{#pbBillPayAble.payTime}," +
            "pb.amountPaid=:#{#pbBillPayAble.amountPaid},pb.realAmountPaid=:#{#pbBillPayAble.payTime},pb.invoiceProvide=:#{#pbBillPayAble.invoiceProvide}," +
            "pb.otherInfo=:#{#pbBillPayAble.otherInfo},pb.contractMoney=:#{#pbBillPayAble.agreementMoney},pb.isShow=:#{#pbBillPayAble.isShow} where pb.id=:#{#pbBillPayAble.id}")
    void updateById(@Param("pbBillPayAble") PbBillPayAble billPayAble);

    /**
     * 根据合同编码获取应收台账
     */
    @Query("select bf from PbBillPayAble bf where bf.id=:id")
    PbBillPayAble findById(@Param("id") int id);

    /**
     * 通过工程编码获取应收台账
     */
    @Query("select pa from PbBillPayAble pa where pa.subProCode=:subProCode and pa.isShow='1' ")
    Slice<PbBillPayAble> findAllByProCode(@Param("subProCode") String subProCode, Pageable pageable);

    /**
     * 通过工程编码获取应收台账
     */
    @Query("select pa from PbBillPayAble pa where pa.subProCode=:subProCode")
    List<PbBillPayAble> findAllByProCode(@Param("subProCode") String subProCode);
}
