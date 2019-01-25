package cn.com.jcgroup.service.repositories;

import cn.com.jcgroup.service.domain.PbPrivateFund;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 私募基金
 *
 * @author LiuYong on 17/6/2 上午11:39.
 */
@Repository
public interface PbPrivateFundRepository extends JpaRepository<PbPrivateFund, Integer> {

    /**
     * 私募基金列表
     */
    @Query("select pf from PbPrivateFund pf where pf.fundCode=:fundCode ")
    PbPrivateFund findAllBypFundCode(@Param("fundCode") String fundCode);

    /**
     * 私募基金更新
     */
    @Modifying
    @Query("update PbPrivateFund  pf set pf.fundManager=:#{#pbf.fundManager} ,pf.fundName=:#{#pbf.fundName}," +
            "pf.raiseAmount=:#{#pbf.raiseAmount},pf.fundDuration=:#{#pbf.fundDuration},pf.currentTotalMoney=:#{#pbf.currentTotalMoney}," +
            "pf.riskLevel=:#{#pbf.riskLevel},pf.riskControl=:#{#pbf.riskControl},pf.apr=:#{#pbf.apr}," +
            "pf.capitalUse=:#{#pbf.capitalUse},pf.realAmount=:#{#pbf.realAmount},pf.periodInfo=:#{#pbf.periodInfo},pf.updateTime=sysdate" +
            "  where pf.fundCode=:#{#pbf.fundCode}")
    int updatePrivateFundInfo(@Param("pbf") PbPrivateFund pbPrivateFund);

    /**
     * 私募基金更新 除 int updatePrivateFundInfo（）方法之外
     */
    @Modifying
    @Query("update PbPrivateFund pb set pb.custodyFee=:#{#pbPrivateFund.custodyFee}," +
            "pb.investFee=:#{#pbPrivateFund.investFee},pb.manageFee=:#{#pbPrivateFund.manageFee}," +
            "pb.consignFee=:#{#pbPrivateFund.consignFee},pb.financeSide=:#{#pbPrivateFund.financeSide}," +
            "pb.guarantor=:#{#pbPrivateFund.guarantor},pb.fundManager=:#{#pbPrivateFund.fundManager}," +
            "pb.isShow=:#{#pbPrivateFund.isShow} where pb.fundCode=:#{#pbPrivateFund.fundCode}")
    int updatePrivateFund(@Param("pbPrivateFund") PbPrivateFund privateFund);


    /**
     * 查询私募基金编号列表
     * @author LiuYong  
     */
    @Query("select pf.fundCode from PbPrivateFund pf ")
    List<String> selectPrivateFundIds(Pageable pageable);

    /**
     * 获取私募数据通过基金编号
     * and pf.isShow = '1'
     */
    @Query("select pf from PbPrivateFund pf where pf.fundCode=:fundCode")
    PbPrivateFund findByFundCode(@Param("fundCode") String fundCode);

    /**
     * 根据基金编号查询基金名称
     */
    @Query("select a.fundName from PbPrivateFund a where a.fundCode=:fundCode")
    String findFundName(@Param("fundCode") String fundCode);
    
    /**
     * 统计今年累计金额
     * @author LiuYong  
     */
    @Query("select sum(pb.currentTotalMoney) as totalMoney from PbPrivateFund pb  where pb.isShow='1' ")
    Long countTotalPrivateMoney();

}
