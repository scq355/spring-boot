package cn.com.jcgroup.service.repositories;

import cn.com.jcgroup.service.domain.PbPersonFlowInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 员工异动流水相关
 * @author LiuYong on 17/6/19 下午5:07.
 */
@Repository
public interface PbPersonFlowInfoRepository extends JpaRepository<PbPersonFlowInfo,Integer>{

    @Query(value = "SELECT seq_person_flow_info_id.nextval FROM dual",nativeQuery = true)
    Integer findSeqId();
    
    /**
     * 查询上一次同步时间
     * @author LiuYong  
     */
    @Query("select max(pb.modifyTime)  from PbPersonFlowInfo pb")
    Date findLastModifyTime(); 
    
    /**
     * 查询用户异动流水是否存在
     * @author LiuYong  
     */
    @Query("select count(pb.id) from PbPersonFlowInfo pb where pb.relationId=:relationId ")
    Long findRelationIdExist(@Param("relationId") String relationId);


    /**
     * 更新用户异动流水
     * @author LiuYong
     */
    @Modifying
    @Query("update PbPersonFlowInfo pb set pb.endTime=:#{#pbPersonFlowInfo.endTime}, pb.modifyTime=:#{#pbPersonFlowInfo.modifyTime}," +
            " pb.isEnd=:#{#pbPersonFlowInfo.isEnd} where pb.relationId=:#{#pbPersonFlowInfo.relationId} ")
    Long updatePersonFlowInfo(@Param("pbPersonFlowInfo")PbPersonFlowInfo pbPersonFlowInfo);
    
    /**
     * 分页查询员工入职离职流水
     * @author LiuYong  
     */
    @Query(value = "select  resp.*  from(select result.*, rownum as rn from (select pb.type,pb.begin_time,pd.staff_name,pd.sex,pd.position_level,pd.department,pd.birthday,pd.academic,pd.positions,pd.label,pd.work_site,pd.stuff_code  from pb_person_flow_info pb join pb_personnel_data pd on pb.stuff_code=pd.stuff_code" +
            " where pb.type='1' and pd.department in :department and pb.begin_time>=:startTime and pb.begin_time<=:endTime union all" +
            "  select pc.type,pc.begin_time,pe.staff_name,pe.sex,pe.position_level,pe.department,pe.birthday,pe.academic,pe.positions,pe.label,pe.work_site,pe.stuff_code  from pb_person_flow_info pc join pb_personnel_data pe on pc.stuff_code=pe.stuff_code" +
            "   where pc.type='4' and pc.is_end='Y' and pe.department in :department and pc.begin_time>=:startTime and pc.begin_time<=:endTime)  result where rownum<=:endNum order by result.begin_time desc) resp where resp.rn>:startNum ",nativeQuery = true)
    List<Object[]> findPersonFlowInfo(@Param("startTime") Date startTime,@Param("endTime") Date endTime,
                                      @Param("department")List<String> department,@Param("startNum") int startNum,@Param("endNum") int endNum);
    
    /**
     * 统计入职员工  
     * @author LiuYong  
     */
    @Query(value = "select count(pc.id) as totalNumbers  from pb_person_flow_info pc join pb_personnel_data pd on pc.stuff_code=pd.stuff_code   where pc.type='1' " +
            " and pd.department in :department and pc.begin_time>=:startTime and pc.begin_time<=:endTime",nativeQuery = true)
    Integer countEntryStaffNumbers(@Param("startTime") Date startTime,@Param("endTime") Date endTime,@Param("department")List<String> department);

    /**
     * 统计离职员工
     * @author LiuYong
     */
    @Query(value = "select count(pc.id) as totalNumbers  from pb_person_flow_info pc join pb_personnel_data pd on pc.stuff_code=pd.stuff_code   where pc.type='4' and pc.is_end='Y'  " +
            " and pd.department in :department and pc.begin_time>=:startTime and pc.begin_time<=:endTime",nativeQuery = true)
    Integer countLeaveStaffNumbers(@Param("startTime") Date startTime,@Param("endTime") Date endTime,@Param("department")List<String> department);
    
    
}
