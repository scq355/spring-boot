package cn.com.jcgroup.service.repositories;

import cn.com.jcgroup.service.domain.PbCashFlow;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 资金流相关
 *
 * @author LiuYong on 17/6/9 上午10:41.
 */
@Repository
public interface PbCashFlowReponsitory extends JpaRepository<PbCashFlow, Integer> {

    /**
     * 根据时间段和资金类别统计预期资金和实际资金
     *
     * @author LiuYong
     */
    @Query("select  pb.reportTime,sum(pb.realMoney) as totalRealMoney,sum(pb.expectMoney) as totalExpectMoney  from PbCashFlow pb " +
            " where pb.companyCode=:companyCode and pb.type=:cashType and pb.reportTime >=:startTime and pb.reportTime <=:endTime group by pb.reportTime")
    List<Object[]> countByCompanyCodeAndReportTimeBetween(@Param("companyCode") String companyCode, @Param("cashType") String type,
                                                          @Param("startTime") Date start, @Param("endTime") Date end, Sort sort);

    /**
     * 根据资金类别按照时间查询各财务条目预期资金和实际资金
     *
     * @author LiuYong
     */
    @Query("select  pb.itemId,pb.realMoney,pb.expectMoney,pb.reportTime  from PbCashFlow pb where pb.companyCode=:companyCode and pb.type=:cashType and pb.reportTime >=:startTime" +
            "  and pb.reportTime <=:endTime and pb.itemId in :items ")
    List<Object[]> findCashFlowByItemIdAndTypeAndCompanyCode(@Param("companyCode") String companyCode, @Param("cashType") String type,
                                                             @Param("startTime") Date start, @Param("endTime") Date end, @Param("items") List<Integer> items);

    /**
     * 根据资金类别按照时间查询单个财务条目预期资金和实际资金
     *
     * @author LiuYong
     */
    @Query("select pb.realMoney,pb.expectMoney  from PbCashFlow pb where pb.companyCode=:companyCode and pb.type=:cashType " +
            " and pb.reportTime=:reportTime and pb.itemId=:itemId ")
    List<Object[]> findSingleCashFlow(@Param("companyCode") String companyCode, @Param("cashType") String type,
                                      @Param("reportTime") Date reportTime, @Param("itemId") int itemId);

    /**
     * 根据资金类别按照时间统计合营公司实际资金
     *
     * @author LiuYong
     */
    @Query("select sum(pb.realMoney) as totalRealMoney  from PbCashFlow pb where pb.companyCode=:companyCode and pb.type=:cashType " +
            " and pb.reportTime>=:startTime and pb.reportTime<=:endTime")
    Long countSumCashFlowByCompanyCodeAndTypeAndMonth(@Param("companyCode") String companyCode, @Param("cashType") String type,
                                                      @Param("startTime") Date startTime,@Param("endTime") Date endTime);

}
