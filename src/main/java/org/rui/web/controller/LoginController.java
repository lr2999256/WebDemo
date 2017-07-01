package org.rui.web.controller;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.rui.model.User;
import org.rui.service.UserService;
import org.rui.util.SecureUtil;
import org.rui.util.WebUtil;
import org.rui.util.security.token.FormToken;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.rui.constant.Constant.HEAD_SESSION_STATUS_KEY;
import static org.rui.constant.Constant.HEAD_SESSION_STATUS_VALUE;

/**
 * 登录
 * Created by JK on 2017/1/24.
 */
@Controller
@RequestMapping("/admin")
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(LoginController.class);
    /**
     * 跳转到登录页面
     *
     * @return
     */
    @RequestMapping(value = "/login")
    public String toLogin(HttpServletRequest request, HttpServletResponse response) {
        log.info("跳转到登录页面！");
        if(WebUtil.isAjaxRequest(request)){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader(HEAD_SESSION_STATUS_KEY, HEAD_SESSION_STATUS_VALUE);
//            response.setHeader("X-Session-Status","{\"code\":408,\"msg\":'Session Timeout'}");
            response.setContentType("text/html;charset=utf-8");
        }
        return "admin/login";
    }

    /**
     * 用户登陆
     * 先根据用户名查询出一条用户记录再对比密码是否正确可以防止sql注入
     * @param username  用户名
     * @param password  密码
     * @return
     */
    @PostMapping(value = "/login")
    public ResponseEntity<Void> login(String username, String password){
        try {
            //获取当前的Subject
            Subject currentUser = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            log.info("对用户进行登录验证..验证开始! username = {}", username);
            currentUser.login(token);
            //验证是否登录成功
            if(currentUser.isAuthenticated()){
                log.info("对用户进行登录验证..验证通过! username = {}", username);
                return ResponseEntity.ok(null);
            }
        }catch (UnknownAccountException e) {  //账号不存在
            log.info("对用户进行登录验证..验证未通过,未知账户! username = {}", username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IncorrectCredentialsException e) {
            log.info("对用户进行登录验证..验证未通过,错误的凭证! username = {}", username);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (LockedAccountException e) {
            log.info("对用户进行登录验证..验证未通过,账户已锁定! username = {}", username);
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(null);
        }catch(ExcessiveAttemptsException eae) {
            log.info("对用户进行登录验证..验证未通过,错误次数过多! username = {}", username);
            return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(null);
        } catch (AuthenticationException e) {
            log.info("对用户进行登录验证..验证未通过,身份验证失败! username = {}" ,username);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (Exception e) {
            log.error("对用户进行登录验证失败! username = {} e = {}", username, e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 跳转到修改密码页面
     * @return
     */
    @FormToken(save = true)
    @GetMapping(value = "/password")
    public String password(){
        log.info("跳转到修改密码页面成功!");
        return "admin/password";
    }

    /**
     * 修改密码
     * @return
     */
    @FormToken(remove = true)
    @ResponseBody
    @PostMapping(value = "/password")
    public ModelMap updatePassword(String oldPassword, String newPassword){
        ModelMap messagesMap = new ModelMap();
        try {
            //获得当前登陆用户
            Subject subject = SecurityUtils.getSubject();
            User user = (User) subject.getPrincipal();

            if(!user.getPassword().equals(SecureUtil.md5(oldPassword))){
                log.info("修改密码失败，原始密码不正确!");
                messagesMap.put("status",FAILURE);
                messagesMap.put("message","原始密码不正确!");
                return messagesMap;
            }

            User newUser = new User();
            newUser.setId(user.getId());
            newUser.setPassword(SecureUtil.md5((newPassword)));
            userService.updateSelective(newUser);

            //清除登录信息
            subject.logout();

            log.info("修改密码成功!");
            messagesMap.put("status",SUCCESS);
            messagesMap.put("message","修改密码成功!");
            return messagesMap;
        } catch (Exception e) {
            log.error("修改密码失败! e = {}", e);
            messagesMap.put("status",FAILURE);
            messagesMap.put("message","修改密码失败!");
            return messagesMap;
        }
    }
}
