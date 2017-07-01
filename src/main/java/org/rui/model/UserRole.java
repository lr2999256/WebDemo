package org.rui.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户角色中间表
 * Created by Rui on 2017/2/13.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRole extends BaseEntity{
    private Long userId;
    private Long roleId;
}
