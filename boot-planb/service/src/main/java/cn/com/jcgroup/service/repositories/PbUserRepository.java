package cn.com.jcgroup.service.repositories;

import cn.com.jcgroup.service.domain.PbUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @description 用户
 *
 * @author LiuYong on 17/5/24 上午10:45.
 */
@Repository
public interface PbUserRepository extends JpaRepository<PbUser,Integer> {

    @Query(value = "select  u from PbUser u where u.userName=:username")
    PbUser findUserInfoByUserName(@Param("username") String userName);

}
