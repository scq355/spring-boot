package cn.com.jcgroup.service.repositories;

import cn.com.jcgroup.service.domain.PbMonthPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @description 月度台账
 * @author sunchangqing
 */
@Repository
public interface PbMonthPaperRepository extends JpaRepository<PbMonthPaper, Integer>{

    /**
     * 跟新月度台账信息
     */
    @Modifying
    @Query("update PbMonthPaper mp set mp.isShow=:isShow, mp.paperName=:paperName, mp.createTime=:yearMonth where mp.id=:id")
    void updateById(@Param("id") Integer id,
                    @Param("isShow") String isShow,
                    @Param("paperName") String paperName,
                    @Param("yearMonth") Date yearMonth);

    /**
     * 通过工程编码获取月度台账信息
     */
    @Query("select mp from PbMonthPaper mp where mp.subProCode=:subProCode order by mp.createTime desc")
    List<PbMonthPaper> findAllByProCode(@Param("subProCode") String subProCode);
}
