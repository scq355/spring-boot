package cn.com.jcgroup.service.repositories;

import cn.com.jcgroup.service.domain.PbImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description: 图片信息
 * User: sunchangqing
 * Date: 2017-06-13
 * Time: 下午6:19
 */
@Repository
public interface PbImageRepository extends JpaRepository<PbImage, Integer>{

    @Query("select im from PbImage as im where im.proCode=:proCode")
    List<PbImage> findAllByProCode(@Param("proCode") String proCode);
}
