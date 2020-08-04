package cn.com.jcgroup.service.repositories;

import cn.com.jcgroup.service.domain.PbCompany;
import cn.com.jcgroup.service.util.SqlHelperUtil;
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
 * @description 合营公司
 *
 * @author sunchangqing on 2017-06-07 17:24:04
 */
@Repository
public interface PbCompanyRepository extends JpaRepository<PbCompany, Integer>{

    /**
     * 根据合营公司名字查询合营公司信息
     */
    @Query("select pc from PbCompany pc where pc.companyName like CONCAT('%',:companyName,'%')")
    List<PbCompany> findByCompanyNameLike(@Param("companyName") String companyName);

    /**
     * 删除合营公司
     */
    @Modifying
    @Query("delete from PbCompany company where company.companyCode=:companyCode")
    void deleteByCompanyCode(@Param("companyCode") String companyCode);

    /**
     * 获取某个项目下的所有合营公司列表
     */
    @Query("select company from PbCompany company where company.proCode=:proCode")
    List<PbCompany> findAllByProCodeBack(@Param("proCode") String proCode);

    /**
     * 获取某个项目下的所有合营公司列表
     */
    @Query("select company from PbCompany company where company.proCode=:proCode and company.isShow='1'")
    List<PbCompany> findAllByProCode(@Param("proCode") String proCode);

    /**
     * 获取某个项目下的所有合营公司列表（带分页）
     */
    @Query("select company from PbCompany company where company.proCode=:proCode")
    List<PbCompany> findAllByProCode(@Param("proCode") String proCode, Pageable pageable);

    /**
     * 根据合营公司编码修改合营公司信息
     */
    @Query("select company from PbCompany company where company.companyCode=:companyCode")
    PbCompany findByCompanyCode(@Param("companyCode") String companyCode);

    @Modifying
    @Query("update PbCompany as pc set pc.companyName=:companyName, pc.updateTime=:updateDate ," +
            "pc.isShow=:isShow where pc.companyCode=:companyCode")
    int updateCompany(@Param("companyCode") String companyCode,
                      @Param("companyName") String companyName,
                      @Param("updateDate") Date updateTime,
                      @Param("isShow") String isShow);

    @Modifying
    @Query("update PbCompany pb set pb.updateTime=:#{#pbCompany.updateTime}," +
            "pb.officeAddress=:#{#pbCompany.officeAddress},pb.officeRent=:#{#pbCompany.officeRent}," +
            "pb.officeSize=:#{#pbCompany.officeSize},pb.director=:#{#pbCompany.director}," +
            "pb.seniorManager=:#{#pbCompany.seniorManager},pb.supervisors=:#{#pbCompany.supervisors}," +
            "pb.stockHolder=:#{#pbCompany.stockHolder},pb.projectManager=:#{#pbCompany.projectManager}," +
            "pb.legalPerson=:#{#pbCompany.legalPerson},pb.registerCapital=:#{#pbCompany.registerCapital}," +
            "pb.equityTransferAgree=:#{#pbCompany.equityTransferAgree},pb.rulePromise=:#{#pbCompany.rulePromise}," +
            "pb.profitDistrPlan=:#{#pbCompany.profitDistrPlan} where pb.companyCode=:#{#pbCompany.companyCode}")
    void updateCompanyInfo(@Param("pbCompany") PbCompany company);

}
