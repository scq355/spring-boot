package cn.com.jcgroup.service.repositories;

import cn.com.jcgroup.service.domain.PbEncourageFile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description 激励文件
 * @author sunchangqing
 */
@Repository
public interface PbEncourageFileRepository extends JpaRepository<PbEncourageFile, Integer>{

    /**
     * 查询激励文件名
     */
    @Query("select ef from PbEncourageFile ef where ef.fileName like concat('%', :fileName, '%') and ef.isShow='1' ")
    List<PbEncourageFile> findAllByFileNameLike(@Param("fileName") String fileName);

    /**
     * 根据项目编码获取激励文件信息
     */
    @Query("select ef from PbEncourageFile ef where ef.proCode=:proCode and ef.isShow='1' ")
    List<PbEncourageFile> findByProCode(@Param("proCode") String proCode);

    /**
     * 根据项目编码获取激励文件信息
     */
    @Query("select en from PbEncourageFile en where en.proCode=:proCode")
    List<PbEncourageFile> findByProjectCodeInPage(@Param("proCode") String proCode, Pageable pageable);

    @Modifying
    @Query("update PbEncourageFile pb set pb.fileName=:#{#pbEncourageFile.fileName},pb.fileExt=:#{#pbEncourageFile.fileExt}," +
            "pb.updateTime=:#{#pbEncourageFile.updateTime},pb.isShow=:#{#pbEncourageFile.isShow} " +
            "where pb.fileCode=:#{#pbEncourageFile.fileCode}")
    void updateEncourageFile(@Param("pbEncourageFile") PbEncourageFile encourageFile);
}
