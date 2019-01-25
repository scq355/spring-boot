package cn.com.jcgroup.service.repositories;

import cn.com.jcgroup.service.domain.PbFile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文件相关
 *
 * @author LiuYong on 17/6/7 上午10:08.
 */
@Repository
public interface PbFileReponsitory extends JpaRepository<PbFile, Integer> {

    /**
     * 里程碑时间轴
     */
    @Query("select pf from PbFile pf where pf.proCode=:projectCode and pf.isShow='1'  ")
    Slice<PbFile> findMileStoneByProjectCode(@Param("projectCode") String projectCode, Pageable pageable);

    /**
     * 根据文件编码
     */
    @Query("select pf from PbFile pf where pf.fileCode=:fileCode")
    PbFile findByFileCode(@Param("fileCode") String fileCode);

    /**
     * 根据文件编码-分页
     */
    @Query("select pf from PbFile pf where pf.proCode=:proCode")
    List<PbFile> findByFileCodeInPage(@Param("proCode") String proCode, Pageable pageable);

    @Query("select count(pf) as num from PbFile pf where pf.proCode=:proCode")
    Integer countAllByProCode(@Param("proCode") String proCode);

}
