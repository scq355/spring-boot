package cn.com.jcgroup.service.repositories;

import cn.com.jcgroup.service.domain.PbCashReportItem;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 财务条目
 *
 * @author LiuYong on 17/6/9 下午2:11.
 */
@Repository
public interface PbCashReportItemReponsitory extends JpaRepository<PbCashReportItem, Integer> {

    /**
     * 根据资金类型查询第一层级财务条目
     * @author LiuYong
     */
    @Query("select  pb.id,pb.itemName from PbCashReportItem pb where pb.type=:cashType and pb.pid=0 and pb.isShow='1' ")
    List<Object[]> findFirstLevelCashFlowByType(@Param("cashType") String type,Sort sort);
    
    
    /**
     * 根据资金类型查询第二层级财务条目
     * @author LiuYong  
     */
    @Query(value = "SELECT  a.id,a.item_name as itemName,a.pid  from pb_cash_report_item a  join pb_cash_report_item b on a.pid=b.id" +
            " WHERE b.pid=0 and a.type=:cashType and a.is_show='1' and b.is_show='1'  ORDER BY b.sort_order,a.sort_order " ,nativeQuery = true)
    List<Object[]> findSecondLevelCashFlowByType(@Param("cashType") String type);

    /**
     * 根据资金类型查询第三层级财务条目
     * @author LiuYong
     */
    @Query("select pb.id,pb.itemName,pb.pid from PbCashReportItem pb where pb.pid in :items and  pb.type=:cashType and pb.isShow='1'")
    List<Object[]> findThirdLevelCashFlowByType(@Param("cashType") String type,@Param("items") List<Integer> items,Sort sort);
    
    
}
