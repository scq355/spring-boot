package cn.com.jcgroup.service.repositories;

import cn.com.jcgroup.service.domain.PbSubProjectMoneySum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 工程汇总流水相关
 * @author LiuYong on 17/6/14 下午5:53.
 */
@Repository
public interface PbSubProjectMoneySumReponsitory extends JpaRepository<PbSubProjectMoneySum, Integer> {

    /**
     * 生成Id
     */
    @Query(value = "SELECT SEQ_SUB_PROJECT_MONEY_SUM_ID.nextval FROM dual",nativeQuery = true)
    Integer findSeqId();

    /**
     * 根据工程编码获取工程量列表
     */
    @Query("select pms from PbSubProjectMoneySum pms where pms.subProCode=:subProCode")
    List<PbSubProjectMoneySum> findAllBySubProCode(@Param("subProCode") String subProCode, Pageable pageable);

    /**
     * 根据工程编码和时间获取工程量
     */
    @Query("select pb  from PbSubProjectMoneySum pb where pb.subProCode=:subProCode and pb.reportTime=:reportTime ")
    PbSubProjectMoneySum findBySubProjectAndTime(@Param("subProCode") String subProCode, @Param("reportTime") Date reportTime);

    /**
     * 修改工程量
     */
    @Modifying
    @Query("update PbSubProjectMoneySum pb set pb.reportTime=:#{#pbSubProjectMoneySum.reportTime}," +
            "pb.checkedMoney=:#{#pbSubProjectMoneySum.checkedMoney}, pb.realPaidMoney=:#{#pbSubProjectMoneySum.realPaidMoney}," +
            "pb.projectProgress=:#{#pbSubProjectMoneySum.projectProgress}, pb.totalMoney=:#{#pbSubProjectMoneySum.totalMoney}," +
            "pb.totalCheckedMoney=:#{#pbSubProjectMoneySum.totalCheckedMoney}, pb.totalRealPaidMoney=:#{#pbSubProjectMoneySum.totalRealPaidMoney}" +
            " where pb.id=:#{#pbSubProjectMoneySum.id}")
    void update(@Param("pbSubProjectMoneySum") PbSubProjectMoneySum subProjectMoneySum);
}
