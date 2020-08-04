package cn.com.jcgroup.service.repositories;

import cn.com.jcgroup.service.domain.PbCompanyStaffRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author LiuYong on 17/6/26 上午9:37.
 */
@Repository
public interface PbCompanyStaffRelationRepository  extends JpaRepository<PbCompanyStaffRelation, Integer> {


    @Query(value = "SELECT seq_company_staff_relation.nextval FROM dual",nativeQuery = true)
    Integer findSeqId();
    
    @Query("select pb.companyStaffId  from PbCompanyStaffRelation pb where pb.companyCode=:companyCode ")
    List<Integer> findIdsByCompanyCode(@Param("companyCode")String companyCode);
    
    @Modifying
    @Query("delete  from PbCompanyStaffRelation pb where pb.companyCode=:companyCode and pb.companyStaffId=:staffId ")
    Integer deleteRelation(@Param("companyCode")String companyCode,@Param("staffId") int  companyStaffId);
}
