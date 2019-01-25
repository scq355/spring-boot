package cn.com.jcgroup.service.repositories;

import cn.com.jcgroup.service.domain.PbUserRelationProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @description 用户项目关系
 *
 * @author LiuYong on 17/5/30 下午9:30.
 */
@Repository
public interface PbUserRelationProjectRepository extends JpaRepository<PbUserRelationProject, Integer> {


    @Query("select a from PbUserRelationProject a where a.userName=:username and a.proCode=:proCode ")
    PbUserRelationProject findUserRelationProject(@Param("proCode") String proCode,
                                                  @Param("username") String username);

}
