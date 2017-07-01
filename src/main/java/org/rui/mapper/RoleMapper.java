package org.rui.mapper;

import org.rui.model.Role;
import org.rui.util.MyMapper;


public interface RoleMapper extends MyMapper<Role> {

    /**
     * 根据用户ID查询角色对象信息
     * @param userId
     * @return
     */
    Role findByUserId(Long userId);
}