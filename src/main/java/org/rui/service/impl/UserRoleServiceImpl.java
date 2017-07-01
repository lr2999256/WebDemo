package org.rui.service.impl;

import org.rui.model.UserRole;
import org.rui.service.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author cuiP
 * Created by JK on 2017/2/16.
 */
@Transactional
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole> implements UserRoleService {
    @Transactional(readOnly = true)
    @Override
    public UserRole findByUserIdAndRoleId(Long userId, Long roleId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        return super.findOne(userRole);
    }
}
