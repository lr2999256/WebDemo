package org.rui.mapper;

import org.rui.model.Permission;
import org.rui.util.MyMapper;
import org.rui.vo.TreeNode;

import java.util.List;

public interface PermissionMapper extends MyMapper<Permission> {

    /**
     * 根据用户ID查询该用户所拥有的权限列表
     * @param userId
     * @return
     */
    List<Permission> findListPermissionByUserId(Long userId);

    /**
     * 根据用户ID查询用户菜单列表
     * @param userId
     * @return
     */
    List<Permission> findMenuListByUserId(Long userId);

    /**
     * 返回树列表
     * @return
     */
    List<TreeNode> findTreeList();
}