package cn.com.jcgroup.service.repositories;

import cn.com.jcgroup.service.domain.PbCompanyStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 合营公司员工信息
 *
 * @author LiuYong on 17/6/26 上午9:29.
 */
@Repository
public interface PbCompanyStaffRepository extends JpaRepository<PbCompanyStaff, Integer> {

    @Query(value = "SELECT seq_company_staff_id.nextval FROM dual",nativeQuery = true)
    Integer findSeqId();
    
    @Query(value = "select pb from PbCompanyStaff pb where pb.id in :ids ")
    List<PbCompanyStaff> findStaffInfoByIds(@Param("ids") List<Integer> ids); 
    
    @Modifying
    @Query(value = "update PbCompanyStaff  pb  set pb.name=:#{#staff.name},pb.birthday=:#{#staff.birthday}," +
            "pb.annualSalary=:#{#staff.annualSalary},pb.sex=:#{#staff.sex},pb.birthplace=:#{#staff.birthplace}," +
            "pb.company=:#{#staff.company},pb.degree=:#{#staff.degree},pb.department=:#{#staff.department}," +
            "pb.laborRelations=:#{#staff.laborRelations},pb.post=:#{#staff.post},pb.qualification=:#{#staff.qualification}," +
            "pb.workExperience=:#{#staff.workExperience},pb.telephone=:#{#staff.telephone} where pb.id=:#{#staff.id}")
    Integer updateStaffInfo(@Param("staff") PbCompanyStaff pbCompanyStaff);
}
