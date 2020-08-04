package cn.com.jcgroup.service.repositories;

import cn.com.jcgroup.service.domain.PbSubProjectPaidItem;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @description 项目资金流水
 * @author sunchangqing
 */
@Repository
public interface PbSubProjectPaidItemRepository extends JpaRepository<PbSubProjectPaidItem, Integer> {

    @Query(value = "SELECT SEQ_SUB_PROJECT_PAY_ID.nextval FROM dual",nativeQuery = true)
    Integer findSeqId();

    @Modifying
    @Query("update PbSubProjectPaidItem pb set pb.fundInfo=:#{#pbSubProjectPaidItem.fundInfo}," +
            "pb.payTime=:#{#pbSubProjectPaidItem.payTime},pb.paidMoney=:#{#pbSubProjectPaidItem.paidMoney}, " +
            "pb.amountOfSubPro=:#{#pbSubProjectPaidItem.amountOfSubPro}" +
            " where pb.id=:#{#pbSubProjectPaidItem.id}")
    void update(@Param("pbSubProjectPaidItem") PbSubProjectPaidItem subProjectPaidItem);

    /**
     * 根据工程编码&&支付时间区段获取工程流水
     */
    @Query("select p from PbSubProjectPaidItem p where p.subProCode=:subProCode and p.payTime>=:startTime and p.payTime<=:endTime")
    List<PbSubProjectPaidItem> findAllBySubProCodeAndPayTime(@Param("subProCode") String subProCode,
                                                             @Param("startTime") Date startTime,
                                                             @Param("endTime") Date endTime, Sort sort);

    /**
     * 根据工程编码和月份统计工程产值和累计付款
     */
    @Query("select sum(pb.paidMoney) as totalPaidMoney,sum(pb.amountOfSubPro) as totalPay  from PbSubProjectPaidItem pb where pb.subProCode=:subProCode and  pb.payTime<=:endTime")
    List<Object[]> countTotalMoneyByCodeAndTime(@Param("subProCode") String subProCode, @Param("endTime") Date endTime);

    /**
     * 根据工程编码查询资金流水
     */
    @Query("select p from PbSubProjectPaidItem p where p.subProCode=:subProCode")
    List<PbSubProjectPaidItem> findAllBySubProCode(@Param("subProCode") String subProCode, Pageable pageable);
}
