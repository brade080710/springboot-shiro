package com.xm.shiro.config;

import com.xm.shiro.admin.dao.PermissionDao;
import com.xm.shiro.admin.dao.UserDao;
import com.xm.shiro.admin.entity.Permission;
import com.xm.shiro.admin.entity.Role;
import com.xm.shiro.admin.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



public class ShiroRealm extends AuthorizingRealm {
    private Logger logger =  LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDao userService;
    @Autowired
    private PermissionDao permissionService;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("doGetAuthorizationInfo+ "+principalCollection.toString());
        User user = userService.getByUserName((String) principalCollection.getPrimaryPrincipal());


        //把principals放session中 key=userId value=principals
        SecurityUtils.getSubject().getSession().setAttribute(String.valueOf(user.getId()),SecurityUtils.getSubject().getPrincipals());

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //赋予角色
        for(Role userRole:user.getRoles()){
            info.addRole(userRole.getName());
        }
        //赋予权限
        for(Permission permission:permissionService.getByUserId(user.getId())){
//            if(StringUtils.isNotBlank(permission.getPermCode()))
                info.addStringPermission(permission.getName());
        }

        //设置登录次数、时间
//        userService.updateUserLogin(user);
        return info;
    }
    //认证.登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("doGetAuthenticationInfo +"  + authenticationToken.toString());

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userName=token.getUsername();
        logger.info(userName+token.getPassword());

        User user = userService.getByUserName(token.getUsername());
        if (user != null) {
//            byte[] salt = Encodes.decodeHex(user.getSalt());
//            ShiroUser shiroUser=new ShiroUser(user.getId(), user.getLoginName(), user.getName());
            //设置用户session
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("user", user);
            return new SimpleAuthenticationInfo(user, user.getPassword(),getName());
        } else {
            return null;
        }
//        return null;
    }

}
