package cn.com.jcgroup.service.repositories;

import cn.com.jcgroup.service.domain.PbPersonnelData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @description 人才库
 * @author sunchangqing
 */
@Repository
public interface PbPersonnelDataRepository extends JpaRepository<PbPersonnelData, Integer>{
    
    @Query(value = "SELECT seq_personnel_data_id.nextval FROM dual",nativeQuery = true)
    Integer findSeqId();

    /**
     * 根据部门编号/时间区段查询人员信息列表
     */
    @Query("select pd from PbPersonnelData pd where pd.department=:department and pd.entryTime>=:beginDate and pd.entryTime<=:endDate")
    List<PbPersonnelData> findAllByDepartment(@Param("department") String department, @Param("beginDate") Date beginDate,
                                              @Param("endDate") Date endDate, Pageable pageable);

    /**
     * 遍历部门列表
     */
    @Query("select distinct pd.department as department from PbPersonnelData pd")
    List<String> findAllDepartment();

    /**
     * 根据部门编号统计员工性别组成
     */
    @Query("select count(stuff.sex) as num, stuff.sex from PbPersonnelData stuff where stuff.department in:deptCode group by  stuff.sex")
    List<Object[]> findSexByDepartmentCode(@Param("deptCode") List<String> deptCode);

    /**
     * 根据部门编号统计员工总数
     */
    @Query("select count(1) as totalNum from PbPersonnelData stuff where stuff.department in :deptCode")
    Integer countByDepartmentCode(@Param("deptCode") List<String> deptCode);

    /**
     * 根据部门编号统计员工学历组成
     */
    @Query("select count(stuff.academic) as num, stuff.academic from PbPersonnelData stuff where stuff.department in:deptCode group by stuff.academic")
    List<Object[]> findAcademicByDepartmentCode(@Param("deptCode") List<String> deptCode);


    /**
     * 根据部门编号统计员工年龄组成
     */
    @Query("select stuff.birthday from PbPersonnelData as stuff where stuff.department in:deptCode")
    List<Date> findAgeByDepartmentCode(@Param("deptCode") List<String> deptCode);

    /**
     * 根据部门编号统计员工工作年限
     */
    @Query("select stuff.firstWorkTime from PbPersonnelData stuff where stuff.department in:deptCode")
    List<Date> findFirstWorkTimeByDepartmentCode(@Param("deptCode") List<String> deptCode);

    /**
     * 根据部门编号统计员年薪待遇
     */
    @Query("select stuff.annualSalary from PbPersonnelData stuff where stuff.department in:deptCode")
    List<Long> findAnnualSalaryByDepartmentCode(@Param("deptCode") List<String> deptCode);

    /**
     * 根据部门编号统计员本公司工作年限
     */
    @Query("select stuff.entryTime from PbPersonnelData stuff where stuff.department in:deptCode ")
    List<Date> findEntryTimeByDepartmentCode(@Param("deptCode") List<String> deptCode);
    
    /**
     * 查询上一次同步人员信息id
     * @author LiuYong  
     */
    @Query("select  max(pb.relationId) from PbPersonnelData pb")
    Integer findMaxRelationId();
    
    /**
     * 分页查询员工信息
     * 
     * @author LiuYong  
     */
    @Query("select pb  from PbPersonnelData pb where pb.department in :departmentList ")
    Slice<PbPersonnelData> findStaffInfo(@Param("departmentList" )List<String> departmentList, Pageable pageable);

    /**
     * 更新员工信息
     *
     * @author LiuYong
     */
    @Modifying
    @Query("update PbPersonnelData  pb  set pb.positionLevel=:#{#staffData.positionLevel},pb.sex=:#{#staffData.sex},pb.department=:#{#staffData.department}," +
            "pb.staffName=:#{#staffData.staffName},pb.academic=:#{#staffData.academic},pb.birthday=:#{#staffData.birthday},pb.entryTime=:#{#staffData.entryTime}," +
            "pb.firstWorkTime=:#{#staffData.firstWorkTime},pb.positions=:#{#staffData.positions},pb.workSite=:#{#staffData.workSite},pb.label=:#{#staffData.label}," +
            "pb.annualSalary=:#{#staffData.annualSalary} where  pb.stuffCode=:#{#staffData.stuffCode}")
    Integer updateStaffInfoByStaffCode(@Param("staffData") PbPersonnelData pbPersonnelData);
    
}
