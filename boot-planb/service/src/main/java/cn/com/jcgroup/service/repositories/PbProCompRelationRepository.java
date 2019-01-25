package cn.com.jcgroup.service.repositories;

import cn.com.jcgroup.service.domain.PbProjectCompanyRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description 项目公司关系
 * @author sunchangqing
 */
@Repository
public interface PbProCompRelationRepository extends JpaRepository<PbProjectCompanyRelation, Integer> {

    /**
     * 根据公司编码获取所有关系信息
     */
    @Query("select distinct pc.proCode from PbProjectCompanyRelation pc where pc.companyCode=:companyCode")
    List<String> findAllByCompanyCode(@Param("companyCode") String companyCode);
}
