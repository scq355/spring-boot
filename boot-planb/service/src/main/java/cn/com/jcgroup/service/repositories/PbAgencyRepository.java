package cn.com.jcgroup.service.repositories;

import cn.com.jcgroup.service.domain.PbAgency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @description 代理机构
 * @author sunchangqing
 */
@Repository
public interface PbAgencyRepository extends JpaRepository<PbAgency, Integer>{

    /**
     * 获取主键
     */
    @Query(value = "SELECT SEQ_AGENCY_ID.nextval FROM dual",nativeQuery = true)
    Integer findSeqId();

    /**
     * 根据机构编码查询机构信息
     */
    @Query("select ag from PbAgency ag where ag.agencyCode=:agencyCode")
    PbAgency findByAgencyCode(@Param("agencyCode") String agencyCode);

    /**
     * 机构更新
     */
    @Modifying
    @Query("update PbAgency pb set pb.agencyName=:#{#pbAgency.agencyName}," +
            "pb.amount=:#{#pbAgency.amount},pb.capitalCost=:#{#pbAgency.capitalCost}," +
            "pb.timeLimit=:#{#pbAgency.timeLimit},pb.capitalUse=:#{#pbAgency.capitalUse}," +
            "pb.businessItem=:#{#pbAgency.businessItem}," +
            "pb.updateTime=:#{#pbAgency.updateTime} " +
            "where pb.agencyCode=:#{#pbAgency.agencyCode}")
    void updateAgencyByAgencyCode(@Param("pbAgency") PbAgency pbAgency);

    /**
     * 根据机构编码删除机构
     */
    @Modifying
    @Query("delete from PbAgency pa where pa.agencyCode=:agencyCode")
    void deleteByAgencyCode(@Param("agencyCode") String agencyCode);

    /**
     * 根据机构编号&&类型查询机构信息
     */
    @Query("select a from PbAgency a where a.agencyCode=:agencyCode and a.type=:type")
    PbAgency findByAgencyCodeAndType(@Param("agencyCode") String agencyCode, @Param("type") String type);

    /**
     * 根据机构编号查询机构名称
     */
    @Query("select a.agencyName from PbAgency a where a.agencyCode=:agencyCode")
    String findAgencyName(@Param("agencyCode") String agencyCode);

    /**
     * 统计今年累计金额
     *
     * @author LiuYong
     */
    @Query("select sum(pb.amount) as totalMoney  from PbAgency pb where pb.isShow='1' and pb.createTime>=:startTime and pb.type=:agencyType ")
    Long countTotalAgencyMoney(@Param("startTime")Date startTime,@Param("agencyType") String type);

}
