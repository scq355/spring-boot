package cn.com.jcgroup.service.repositories;

import cn.com.jcgroup.service.domain.PbFile;
import cn.com.jcgroup.service.domain.PbFileField;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文件字段相关
 *
 * @author LiuYong on 17/6/8 下午2:50.
 */
@Repository
public interface PbFileFieldReponsitory extends JpaRepository<PbFileField, Integer> {

    /**
     * 根据文件编号获取文件相关字段
     */
    @Query("select p from PbFileField p where p.fileCode=:fileCode and p.isShow='1' ")
    List<PbFileField> findByFileCode(@Param("fileCode") String fileCode,Sort sort);

    /**
     * 文档详情-查询
     */
    @Query("select pf from PbFileField pf where pf.fileCode=:fileCode")
    List<PbFileField> findAllByFileCode(@Param("fileCode") String fileCode);


    /**
     * 文档详情-查询
     */
    @Query("select pf from PbFileField pf where pf.fileCode=:fileCode")
    List<PbFileField> findAllByFileCodeInPage(@Param("fileCode") String fileCode, Pageable pageable);

}
