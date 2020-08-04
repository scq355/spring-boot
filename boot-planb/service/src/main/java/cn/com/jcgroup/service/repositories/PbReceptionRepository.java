package cn.com.jcgroup.service.repositories;

import cn.com.jcgroup.service.domain.PbReception;
import cn.com.jcgroup.service.domain.PbTravel;
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
 * @author LiuYong on 17/6/16 上午12:43.
 */
@Repository
public interface PbReceptionRepository extends JpaRepository<PbReception, Integer> {

    /**
     * 查询接待记录
     * @author LiuYong  
     */
    @Query("select pb from PbReception pb where pb.company in :company and pb.startTime>=:startTime and pb.startTime<=:endTime")
    Slice<PbReception> findByCompanyAndDate(@Param("company") List<String> company, Pageable pageable,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 分组统计时间段内各部门接待总费用和总出差次数和总人数
     *
     * @author LiuYong
     */
    @Query("select pb.company, count(pb.id)as totalTimes,sum(pb.fee) as totalFee,sum(pb.personNumber) as totalPerson from PbReception pb where pb.startTime>=:startTime and pb.startTime<=:endTime and pb.company in :company  group by  pb.company ")
    List<Object[]> countTotalFeeAndTimesByCompany(@Param("startTime") Date start,@Param("endTime") Date end,@Param("company") List<String> company);
    
    /**
     * 查询关键词
     * @author LiuYong  
     */
    @Query("select pb.company,pb.keyWords  from PbReception pb where pb.startTime>=:startTime and pb.startTime<=:endTime and pb.company in :company")
    List<Object[]> findKeyWords(@Param("startTime") Date start,@Param("endTime") Date end,@Param("company") List<String> company);

    /**
     * 根据公司分组统计接待数据
     *
     * @author LiuYong
     */
    @Query("select pb.company, count(1) as totalTimes,sum(pb.fee) as totalFee,sum(pb.personNumber) as totalNumbers  from PbReception pb where pb.startTime>=:startTime and pb.startTime<=:endTime and pb.company in :company group by  pb.company ")
    List<Object[]> countReceptionByCompanyGroup(@Param("startTime") Date start, @Param("endTime") Date end, @Param("company") List<String> company);

    /**
     * 更新词频
     *
     * @author LiuYong
     */
    @Modifying
    @Query("update PbReception pb set pb.keyWords=:keyWords where pb.relationId=:relationId ")
    Integer updateKeyWords(@Param("keyWords") String keyWords,@Param("relationId") String relationId);

    /**
     * 分页查询接待信息
     *
     * @author LiuYong
     */
    @Query("select pb from PbReception pb where pb.company in :company ")
    Slice<PbReception> findByCompany(@Param("company") List<String> company, Pageable pageable);

}
