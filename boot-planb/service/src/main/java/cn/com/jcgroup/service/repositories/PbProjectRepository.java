package cn.com.jcgroup.service.repositories;

import cn.com.jcgroup.service.domain.PbProject;
import cn.com.jcgroup.service.util.SqlHelperUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * 项目相关
 *
 * @author LiuYong on 17/6/4 下午11:06.
 */
@Repository
public interface PbProjectRepository extends JpaRepository<PbProject, Integer> {

    /**
     * 生成主键
     */
    @Query(value = "SELECT SEQ_PROJECT_ID.nextval FROM dual",nativeQuery = true)
    Integer findSeqId();

    @Modifying
    @Query("delete from PbProject pj where pj.proCode=:proCode")
    void deleteByProCode(@Param("proCode") String proCode);

    /**
     * 根据名称查询项目
     */
    @Query("select pc from PbProject pc where pc.proName like CONCAT('%',:proName,'%') or pc.proNameAbbr like CONCAT('%',:proName,'%')")
    List<PbProject> findAllByProNameLike(@Param("proName") String proName);

    /**
     * 项目复审评级总金额更新
     */
    @Modifying
    @Query("update PbProject pj set pj.encourageMoney=:encourageMoney, pj.levels=:level where pj.proCode=:proCode")
    void updateEncourageMoneyByProCode(@Param("proCode") String proCode, @Param("level") String level, @Param("encourageMoney") long encourageMoney);

    /**
     * 查询激励金额相关
     * 
     * @author LiuYong  
     */
    @Query("select pb.encourageMoney,pb.levels  from PbProject pb where pb.proCode=:proCode ")
    List<Object[]> findEncourageMoneyByProCode(@Param("proCode") String proCode);
    
    /**
     * 项目更新
     */
    @Modifying
    @Query(value = "update PbProject pb set pb.updateTime=:#{#pbProject.updateTime},pb.department=:#{#pbProject.department},\n" +
            "pb.projectManager=:#{#pbProject.projectManager},pb.storageLevel=:#{#pbProject.storageLevel},pb.isCore=:#{#pbProject.isCore},\n" +
            "pb.region=:#{#pbProject.region},pb.affiCity=:#{#pbProject.affiCity},pb.affiProvince=:#{#pbProject.affiProvince},\n" +
            "pb.projectAddress=:#{#pbProject.projectAddress},pb.projectNature=:#{#pbProject.projectNature},pb.collectCapital=:#{#pbProject.collectCapital},\n" +
            "pb.scales=:#{#pbProject.scales},pb.specialProportion=:#{#pbProject.specialProportion},pb.projectPeriod=:#{#pbProject.projectPeriod},\n" +
            "pb.constructionPeriod=:#{#pbProject.constructionPeriod},pb.operationPeriod=:#{#pbProject.operationPeriod},\n" +
            "pb.repurchasePeriod=:#{#pbProject.repurchasePeriod},pb.expectProfit=:#{#pbProject.expectProfit},pb.bankPercent=:#{#pbProject.bankPercent},\n" +
            "pb.fundCapital=:#{#pbProject.fundCapital},pb.investPercent=:#{#pbProject.investPercent},pb.buildingArea=:#{#pbProject.buildingArea},\n" +
            "pb.landArea=:#{#pbProject.landArea},pb.planArea=:#{#pbProject.planArea},pb.goverPointDown=:#{#pbProject.goverPointDown},\n" +
            "pb.constrPointDown=:#{#pbProject.constrPointDown},pb.repurchaseMethod=:#{#pbProject.repurchaseMethod},\n" +
            "pb.repurchaseBase=:#{#pbProject.repurchaseBase},pb.repurchasePercent=:#{#pbProject.repurchasePercent},\n" +
            "pb.cooperContent=:#{#pbProject.cooperContent},pb.projectStatus=:#{#pbProject.projectStatus},pb.innerLevel=:#{#pbProject.innerLevel} where pb.proCode=:#{#pbProject.proCode}")
    void updateProjectInfoByProCode(@Param("pbProject") PbProject pbProject);

    /**
     * 根据项目编号修改项目
     */
    @Modifying
    @Query("update PbProject as pj set pj.proNameAbbr=:abbr, pj.proName=:proName, " +
            "pj.updateTime=:updateTime, pj.isShow=:isShow where pj.proCode=:proCode")
    int updateProjectByProCode(@Param("abbr") String abbr,
                               @Param("proCode") String proCode,
                               @Param("proName") String proName,
                               @Param("updateTime") Date updateTime,
                               @Param("isShow") String isShow);


    @Query("select pj from PbProject pj")
    List<PbProject> findAllInPage(Pageable pageable);

    /**
     * 根据项目编号查询项目信息
     * @author LiuYong  
     */
    @Query("select p from PbProject p where p.proCode=:proCode ")
    PbProject findByProCode(@Param("proCode") String proCode);
    
    /**
     * 根据项目状态分组进行项目个数统计
     * @author LiuYong  
     */
    @Query("select p.projectStatus, count(p.id) as number  from PbProject p where  p.isShow='1' group by p.projectStatus  ")
    List<Object[]> countProjectNumberByStatusGroup();
    
    
   /**
    * 根据项目状态分组进行项目规模统计
    * @author LiuYong  
    */
   @Query("select p.projectStatus, sum(p.scales) as totalScale  from PbProject p where p.isShow='1' group by p.projectStatus ")
    List<Object[]> countProjectScaleByStatusGroup();
    
   /**
    * 根据项目所在省份分组进行项目个数统计
    * @author LiuYong  
    */
    @Query("select count(id) as number, p.affiProvince  from PbProject p where p.isShow='1' group by p.affiProvince")
   List<Object[]> countProjectNumberByProvinceGroup();
    
    
    /**
     * 根据项目所在省份查询项目简略信息
     * @author LiuYong  
     */
    @Query("select p.proCode,p.scales,p.proName,p.locations,p.projectStatus,p.department,p.abbr,p.proNameAbbr  from PbProject p where p.affiProvince=:affiProvince and p.isShow='1' ")
    List<Object[]> findProjectByProvince(@Param("affiProvince") String affiProvince);
    
    /**
     * 根据项目编号查询banner图
     * @author LiuYong  
     */
    @Query("select p.bannerImgs from PbProject p where p.proCode=:proCode ")
    List<String> findBannerImgsByProCode(@Param("proCode") String proCode);

    /**
     * 根据公司、项目类别分组进行规模、项目个数统计
     * @author LiuYong
     */
    @Query("select p.department , p.projectStatus, sum(p.scales) as sumScale, count(p.id) as totalNumbers from PbProject p where p.isShow='1' group by p.department , p.projectStatus ")
    List<Object[]> countProjectInfoByCompanyAndStatusGroup();


    /**
     * 根据文件级别进行分组统计激励文件个数
     * @author LiuYong
     */
    @Query("select p.levels,count(p.id) as number from PbProject p where p.isShow='1' group by p.levels ")
    List<Object[]> countEncourageNumberByLevelGroup();

    /**
     * 根据公司、评级分组进行项目个数统计
     * @author LiuYong
     */
    @Query("select p.department,p.levels,count(p.id) as number  from PbProject p where p.isShow= '1' group by  p.department,p.levels")
    List<Object[]> countEncourageNumberByCompanyAndLevelGroup();
    
    /**
     * 查询各部门项目列表
     * @author LiuYong  
     */
    @Query("select  p.department,p.proCode from PbProject p where p.isShow='1' group by p.department,p.proCode ")
    List<Object[]> findPojectCodeByDepartmentGroup();


//    public static final String updateProjectByProCode = SqlHelperUtil.convertToSqlWithSpel(PbProject.class,"pbProject",new String[]{},new String[]{});

//    @Query(value = updateProjectByProCode)
//    int updateProjectByProCode(@Param("pbProject") PbProject pbProject);
    
}
