package cn.com.jcgroup.service.repositories;

import cn.com.jcgroup.service.domain.PbCompanyAgencyRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description: 合营公司-机构 关系
 * User: sunchangqing
 * Date: 2017-06-19
 * Time: 下午7:15
 */
@Repository
public interface PbCompanyAgencyRelationRepository extends JpaRepository<PbCompanyAgencyRelation, Integer>{

    @Query(value = "SELECT SEQ_COMPANY_AGENCY_ID.nextval FROM dual", nativeQuery = true)
    Integer findSeqId();

    /**
     * 根据机构编码删除记录
     */
    @Modifying
    @Query("delete from PbCompanyAgencyRelation car where car.comAgencyRelationCode=:agencyCode ")
    void deleteAllByAgencyCode(@Param("agencyCode") String agencyCode);

    /**
     * 根据合营公司编码获取机构编码
     */
    @Query("select car.comAgencyRelationCode from PbCompanyAgencyRelation car where car.companyCode=:companyCode and car.type=:type")
    List<String> findAllByCompanyCodeAndType(@Param("companyCode") String companyCode, @Param("type") String type);

}
