package org.rui.service;

import org.rui.model.UserRole;

/**
 * @author cuiP
 * Created by JK on 2017/2/16.
 */
public interface UserRoleService extends BaseService<UserRole>{
    /**
     * 根据用户ID和角色ID查询用户和角色绑定信息
     * @param userId
     * @param roleId
     * @return
     */
    UserRole findByUserIdAndRoleId(Long userId, Long roleId);
}
