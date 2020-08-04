package cn.com.jcgroup.service.repositories;

import cn.com.jcgroup.service.domain.PbRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 角色表
 *
 * @author LiuYong on 17/5/30 下午9:40.
 */
@Repository
public interface PbRolesRepository extends JpaRepository<PbRoles, Integer> {

}
