package cn.com.jcgroup.service.repositories;

import cn.com.jcgroup.service.domain.PbTravelReceptionDetail;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author LiuYong on 17/6/16 上午1:07.
 */
@Repository
public interface PbTravelReceptionRepository extends JpaRepository<PbTravelReceptionDetail, Integer> {


    /**
     * 根据关联id查询出差出发地和目的地
     *
     * @author LiuYong
     */
    @Query("select pb.departureLocation,pb.arriveLocation  from PbTravelReceptionDetail pb where pb.relationId =:relationId and pb.type=:businessType ")
    List<Object[]> findByRelationId(@Param("relationId") String relationId,@Param("businessType") String type);


    /**
     * 根据城市取前10条费用最高的数据
     *
     * @author LiuYong
     */
    @Query(value = "select result.*  from (" +
            "select pb.start_time,pb.company,pb.type,pb.fee as fee  from pb_travel_reception_detail pb where pb.departure_map_location=:cityName" +
            "  and pb.start_time>=:startTime and pb.start_time<=:endTime and pb.company in :company and pb.type='1'  union all  " +
            " select pt.start_time,pt.company,pt.type,pt.fee as fee  from pb_travel_reception_detail pt " +
            " where pt.arrive_map_location=:cityName and  pt.type='2' and pt.start_time>=:startTime and pt.start_time<=:endTime and pt.company in :company) result where rownum<11 order by result.fee desc ", nativeQuery = true)
    List<Object[]> findTopTenByCity(@Param("cityName") String cityName, @Param("startTime") Date start,@Param("endTime") Date end,@Param("company") List<String> company);

    /**
     * 根据城市统计出差费用
     *
     * @author LiuYong
     */
    @Query("select sum(pb.fee) as totalFee from PbTravelReceptionDetail pb where pb.startTime>=:startTime and pb.startTime<=:endTime and pb.company in :company and pb.departureMapLocation=:cityName and pb.type='1' ")
    Long countTravelTotalFeeByCity(@Param("cityName") String cityName, @Param("startTime") Date start,@Param("endTime") Date end,@Param("company") List<String> company);


    /**
     * 根据城市统计接待费用
     *
     * @author LiuYong
     */
    @Query("select sum(pb.fee) as totalFee from PbTravelReceptionDetail pb where pb.startTime>=:startTime and pb.startTime<=:endTime and pb.company in :company and pb.arriveMapLocation=:cityName and pb.type='2' ")
    Long countReceptionTotalFeeByCity(@Param("cityName") String cityName, @Param("startTime") Date start,@Param("endTime") Date end,@Param("company") List<String> company);

    /**
     * 根据城市统计出差次数
     *
     * @author LiuYong
     */
    @Query("select count(pb.id) as totalTimes from PbTravelReceptionDetail pb where pb.startTime>=:startTime and pb.startTime<=:endTime and pb.company in :company and pb.departureMapLocation=:cityName and pb.type='1' ")
    List<Object[]> countTravelTotalTimesByCity(@Param("cityName") String cityName, @Param("startTime") Date start,@Param("endTime") Date end,@Param("company") List<String> company);


    /**
     * 根据城市统计接待次数
     *
     * @author LiuYong
     */
    @Query("select count(pb.id) as totalTimes from PbTravelReceptionDetail pb where pb.startTime>=:startTime and pb.startTime<=:endTime and pb.company in :company and pb.arriveMapLocation=:cityName and pb.type='2' ")
    Long countReceptionTotalTimesByCity(@Param("cityName") String cityName, @Param("startTime") Date start,@Param("endTime") Date end,@Param("company") List<String> company);

    /**
     * 根据时间段和部门查询飞行线路
     *
     * @author LiuYong
     */
    @Query(value = "select  pb.departure_map_location,pb.arrive_map_location,pb.type from PB_TRAVEL_RECEPTION_DETAIL pb where pb.start_time>=:startTime and pb.start_time<=:endTime and pb.company in :company and pb.type='1' group by pb.type,pb.departure_map_location,pb.arrive_map_location " +
            " union all  select  pc.departure_map_location,pc.arrive_map_location,pc.type from PB_TRAVEL_RECEPTION_DETAIL pc where pc.start_time>=:startTime and pc.start_time<=:endTime and pc.company in :company and pc.type='2' group by pc.type,pc.departure_map_location,pc.arrive_map_location ",nativeQuery = true)
    List<Object[]> findTravelRecepionLineByTime(@Param("startTime") Date start,@Param("endTime") Date end,@Param("company") List<String> company);


    /**
     * 按城市分组统计出差接待次数
     *
     * @author LiuYong
     */
    @Query(value = "SELECT pb.DEPARTURE_MAP_LOCATION as locations,count(1) as totalTimes  FROM PB_TRAVEL_RECEPTION_DETAIL pb WHERE  pb.start_time>=:startTime and pb.start_time<=:endTime and pb.company in :company and pb.type='1'  GROUP BY pb.DEPARTURE_MAP_LOCATION " +
            " union all SELECT pc.ARRIVE_MAP_LOCATION as locations,count(1) as totalTimes  FROM PB_TRAVEL_RECEPTION_DETAIL pc WHERE  pc.start_time>=:startTime and pc.start_time<=:endTime and pc.company in :company and pc.type='2'  GROUP BY pc.arrive_map_location ",nativeQuery = true)
    List<Object[]> countTravelTotalTimes(@Param("startTime") Date start,@Param("endTime") Date end,@Param("company") List<String> company);

    /**
     * 更新出差明细
     *
     * @author LiuYong
     */
    @Modifying
    @Query("update PbTravelReceptionDetail pb set pb.arriveMapLocation=:arriveMapLocation,pb.departureMapLocation=:departureMapLocation where pb.id=:id")
    Integer updateTravelLocation(@Param("arriveMapLocation") String arriveMapLocation,@Param("departureMapLocation") String departureMapLocation,@Param("id") int id);

    /**
     * 查询出差/接待明细
     *
     * @author LiuYong
     */
    @Query("select pb  from PbTravelReceptionDetail pb where pb.relationId=:relationId and pb.type=:busyType ")
    List<PbTravelReceptionDetail> findByRelationId(@Param("relationId") String relationId,@Param("busyType") String type,Sort sort);
    
}
