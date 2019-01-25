package cn.com.jcgroup.service.service;

import cn.com.jcgroup.service.domain.PbRoles;
import cn.com.jcgroup.service.domain.PbUserRelationProject;
import cn.com.jcgroup.service.repositories.PbRolesRepository;
import cn.com.jcgroup.service.repositories.PbUserRelationProjectRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 权限校验
 *
 * @author LiuYong on 17/5/30 下午10:02.
 */
@Service
public class SecurityService {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityService.class);

    @Autowired
    private PbUserRelationProjectRepository pbUserRelationProjectRepository;
    @Autowired
    private PbRolesRepository pbRolesRepository;

    /**
     * 判断用户是否拥有访问该项目的权限
     *
     * @return string  返回菜单列表
     * @author LiuYong
     */
    public String hasProjectAuth(String proCode, String username) {
        if (StringUtils.isEmpty(proCode) || StringUtils.isEmpty(username)) {
            LOG.error("[权限校验]项目编码和用户名不能为空");
            return null;
        }
        PbUserRelationProject userRelationProject = pbUserRelationProjectRepository.
                findUserRelationProject(proCode, username);
        if(userRelationProject == null){
            LOG.info("[权限校验]权限不足，proCode={},username={}",proCode,username);
            return null;
        }
        PbRoles pbRoles = pbRolesRepository.getOne(userRelationProject.getRoleId());
        return pbRoles == null ? null : pbRoles.getMenuLevel();
    }
}
