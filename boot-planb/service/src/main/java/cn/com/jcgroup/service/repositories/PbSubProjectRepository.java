package cn.com.jcgroup.service.repositories;

import cn.com.jcgroup.service.domain.PbSubProject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.ServiceMode;
import java.util.List;

/**
 * @description 工程
 * @author sunchangqing
 */
@Repository
public interface PbSubProjectRepository extends JpaRepository<PbSubProject, Integer> {

    @Query(value = "SELECT SEQ_SUB_PROJECT_ID.nextval FROM dual",nativeQuery = true)
    Integer findSeqId();

    @Query("select sp from PbSubProject sp where sp.subProjectName like CONCAT('%',:subProName,'%')" )
    List<PbSubProject> findAllBySubProjectNameLike(@Param("subProName") String subProName);

    /**
     * 工程删除
     */
    @Modifying
    @Query("delete from PbSubProject sp where sp.subProCode=:subProCode")
    void deleteBySubProCode(@Param("subProCode") String subProCode);

    /**
     * 工程修改
     */
    @Modifying
    @Query("update PbSubProject pb set pb.totalMoney=:#{#pbSubProject.totalMoney},pb.isShow=:#{#pbSubProject.isShow}," +
            "pb.subProjectName=:#{#pbSubProject.subProjectName}," +
            "pb.fileCode=:#{#pbSubProject.fileCode} where pb.subProCode=:#{#pbSubProject.subProCode}")
    int updateSubProject( @Param("pbSubProject") PbSubProject pbSubProject);

    /**
     * 根据工程编码获取工程列表
     */
    @Query("select sub from PbSubProject sub where sub.proCode=:proCode")
    List<PbSubProject> findAllByCodeInPage(@Param("proCode") String proCode, Pageable pageable);

    /**
     * 根据项目编码获取工程列表
     */
    @Query("select sub from PbSubProject sub where sub.proCode=:proCode and sub.isShow='1' ")
    List<PbSubProject> findAllByProCode(@Param("proCode") String proCode);
}
