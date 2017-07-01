package org.rui.web.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.rui.model.Permission;
import org.rui.model.User;
import org.rui.service.PermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Rui on 2017/6/26.
 */
@Controller
public class IndexController {
    private static final String BASE_PATH = "admin/";

    @Resource
    private PermissionService permissionService;

    /**
     * 首页
     * @return
     */
    @RequestMapping(value = {"","/index"})
    public String index(ModelMap modelMap){
        //从shiro的session中取user
        Subject subject = SecurityUtils.getSubject();
        //取身份信息
        User user = (User) subject.getPrincipal();

        List<Permission> menuList = permissionService.findMenuListByUserId(user.getId());
        //通过model传到页面
        modelMap.addAttribute("menuList", menuList);
//        log.info("------进入首页-------");
        return BASE_PATH+"index";
    }

    /**
     * 欢迎页
     * @return
     */
    @RequestMapping(value = "/welcome")
    public String welcome(){
//        log.info("------进入欢迎页-------");
        return BASE_PATH+"welcome";
    }

    /**
     * 未授权页面
     * @return
     */
    @RequestMapping(value = "/403")
    public String unauthorized(){
//        log.info("------没有权限-------");
        return BASE_PATH+"common/403";
    }
}
