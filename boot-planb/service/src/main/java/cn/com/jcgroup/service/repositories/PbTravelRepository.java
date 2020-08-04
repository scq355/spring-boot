package cn.com.jcgroup.service.repositories;

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
 * @author LiuYong on 17/6/16 上午12:38.
 */
@Repository
public interface PbTravelRepository extends JpaRepository<PbTravel, Integer> {
    
    /**
     * 查询出差记录
     * @author LiuYong  
     */
    @Query("select pb from PbTravel pb where pb.company in :company and pb.startTime>=:startTime and pb.startTime<=:endTime")
    Slice<PbTravel> findByCompanyAndDate(@Param("company") List<String> company, Pageable pageable,
                                         @Param("startTime") Date startTime, @Param("endTime") Date endTime);


    /**
     * 分组统计时间段内各部门出差总费用和总出差次数
     *
     * @author LiuYong
     */
    @Query("select pb.company, count(pb.id)as totalTimes,sum(pb.fee) as totalFee from PbTravel pb where pb.startTime>=:startTime and pb.startTime<=:endTime and pb.company in :company  group by  pb.company ")
    List<Object[]> countTotalFeeAndTimesByCompany(@Param("startTime") Date start,@Param("endTime") Date end,@Param("company") List<String> company);


    /**
     * 查询关键词
     * @author LiuYong
     */
    @Query("select pb.company,pb.keyWords  from PbTravel pb where pb.startTime>=:startTime and pb.startTime<=:endTime and pb.company in :company")
    List<Object[]> findKeyWords(@Param("startTime") Date start,@Param("endTime") Date end,@Param("company") List<String> company);

    /**
     * 根据公司分组统计出差数据
     *
     * @author LiuYong
     */
    @Query("select pb.company,count(1) as totalTimes,sum(pb.fee) as totalFee  from PbTravel pb where pb.startTime>=:startTime and pb.startTime<=:endTime and pb.company in :company group by  pb.company ")
    List<Object[]> countTravelByCompanyGroup(@Param("startTime") Date start, @Param("endTime") Date end, @Param("company") List<String> company);

    /**
     * 分页查询出差信息
     *
     * @author LiuYong
     */
    @Query("select pb from PbTravel pb where pb.company in :company ")
    Slice<PbTravel> findByCompany(@Param("company") List<String> company, Pageable pageable);
    
    
    /**
     * 更新词频
     * 
     * @author LiuYong  
     */
    @Modifying
    @Query("update PbTravel pb set pb.keyWords=:keyWords where pb.relationId=:relationId ")
    Integer updateKeyWords(@Param("keyWords") String keyWords,@Param("relationId") String relationId);
    
    
}


