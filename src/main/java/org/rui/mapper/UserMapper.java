package org.rui.mapper;

import org.rui.model.User;
import org.rui.util.MyMapper;

public interface UserMapper extends MyMapper<User>{

    /**
     * 根据用户ID查询用户信息
     * @param id
     * @return
     */
    User findById(Long id);

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    User findByUserName(String username);
}