package cn.com.jcgroup.service.repositories;

import cn.com.jcgroup.service.domain.PbEncourage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 激励相关
 *
 * @author LiuYong on 17/6/5 下午7:14.
 */
@Repository
public interface PbEncourageRepository extends JpaRepository<PbEncourage, Integer> {

    @Query(value = "SELECT SEQ_ENCOURAGE_ID.nextval FROM dual",nativeQuery = true)
    Integer findSeqId();

    /**
     * 更新激励信息
     */
    @Modifying
    @Query("update PbEncourage pb set pb.stage=:#{#pbEncourage.stage}," +
            "pb.prePercent=:#{#pbEncourage.prePercent},pb.preDate=:#{#pbEncourage.preDate}," +
            "pb.realPercent=:#{#pbEncourage.realPercent},pb.realMoney=:#{#pbEncourage.realMoney}," +
            "pb.moneyFlag=:#{#pbEncourage.moneyFlag}, pb.realStatus=:#{#pbEncourage.realStatus} " +
            " where pb.id=:#{#pbEncourage.id}")
    void updateEncourage(@Param("pbEncourage") PbEncourage encourage);

    /**
     * 根据项目编码获取激励
     */
    @Query("select en from PbEncourage en where en.proCode=:proCode")
    List<PbEncourage> findByProjectCode(@Param("proCode") String proCode, Sort sort);

    /**
     * 根据项目编码获取激励-分页
     */
    @Query("select en from PbEncourage en where en.proCode=:proCode")
    List<PbEncourage> findByProjectCodeInPage(@Param("proCode") String proCode, Pageable pageable);
}
