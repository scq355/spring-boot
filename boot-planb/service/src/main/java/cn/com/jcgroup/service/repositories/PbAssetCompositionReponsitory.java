package cn.com.jcgroup.service.repositories;

import cn.com.jcgroup.service.domain.PbAssetComposition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 资产形成相关
 * @author LiuYong on 17/6/10 下午3:53.
 */
@Repository
public interface PbAssetCompositionReponsitory extends JpaRepository<PbAssetComposition, Integer> {

    /**
     * 根据项目编号和月份查看资产各个组成部分
     */
    @Query("select pb from PbAssetComposition pb where pb.recordTime=:currentMonth and pb.proCode=:proCode ")
    List<PbAssetComposition> findByProCodeAndRecordTime(@Param("currentMonth") Date date,@Param("proCode") String proCode);

    /**
     * 根据项目编号和月份查看资产各个组成部分
     * @author LiuYong  ƒ
     */
    @Query("select pb.assetType,pb.assetValue  from PbAssetComposition pb where pb.recordTime>=:currentMonth and pb.proCode=:proCode ")
    List<Object[]> findByProCode(@Param("currentMonth") Date date,@Param("proCode") String proCode);

    /**
     * 根据项目编号按照类别分组统计资产
     * @author LiuYong
     */
    @Query("select pb.assetType,sum(pb.assetValue) as totalAsset  from PbAssetComposition pb where pb.proCode=:proCode group by pb.assetType ")
    List<Object[]> countTotalByProCode(@Param("proCode") String proCode);

    /**
     * 根据项目编号统计资产
     * @author LiuYong
     */
    @Query("select sum(pb.assetValue) as totalAsset from PbAssetComposition pb where pb.proCode in :proCodeList ")
    Long countTotalByProCodeList(@Param("proCodeList") List<String> proCodeList);

    /**
     * 统计所有资产
     * @author LiuYong
     */
    @Query("select sum(pb.assetValue) as totalAsset from PbAssetComposition pb")
    Long countTotalAsset();

    /**
     * 统计时间段内所有资产
     * @author LiuYong
     */
    @Query("select sum(pb.assetValue) as totalAsset  from PbAssetComposition pb where pb.recordTime>=:startTime and pb.recordTime<=:endTime ")
    Long countTotalAssetByYear(@Param("startTime") Date start,@Param("endTime") Date end);
    
}
