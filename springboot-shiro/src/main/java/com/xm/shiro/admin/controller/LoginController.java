package com.xm.shiro.admin.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import com.xm.shiro.admin.entity.User;
import com.xm.shiro.utils.BaseError;
import com.xm.shiro.utils.RedisUtil;
import com.xm.shiro.utils.WebUtil;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cdyoue on 2016/10/21.
 * 登陆控制器
 */
@RestController
public class LoginController {
	
	@Autowired
    Environment env;
	
	@Autowired
	RedisUtil redisUtil;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(
            @RequestParam(value = "username", required = true) String userName,
            @RequestParam(value = "password", required = true) String password,
            @RequestParam(value = "rememberMe", required = true, defaultValue = "false") boolean rememberMe, HttpServletResponse response
    ) {
        logger.info("==========" + userName + password + rememberMe);
        logger.trace("日志输出 trace");
        logger.debug("日志输出 debug");
        logger.info("日志输出 info");
        logger.warn("日志输出 warn");
        logger.error("日志输出 error");
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        token.setRememberMe(rememberMe);
//        User user = (User)redisUtil.get("user");
//        if (redisUtil.get("user") == null) {
        	
        	try {
        		subject.login(token);
        		
        		 //获得用户对象
                User u=(User)subject.getPrincipal();
        		System.out.println(u.getUsername());
//        		redisUtil.set("user", (User)subject.getPrincipal());
        	} catch(IncorrectCredentialsException e) {
        		try {
					WebUtil.writeError(response, new BaseError("密码输入错误"));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		return ;
        		
        	} catch (AuthenticationException e) {
        		e.printStackTrace();
//            rediect.addFlashAttribute("errorText", "您的账号或密码输入错误!");
        		try {
					WebUtil.writeError(response, new BaseError("您的账号或密码输入错误"));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		return ;
        	}
//        }
         try {
			WebUtil.writeOK(response,"Msg","登录成功");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }

    @RequestMapping("/logOut")
    public String logOut(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
//        session.removeAttribute("user");
        return "login";
    }

    @RequestMapping("/")
    @ResponseBody
    public String index() {
        return "no permission";
    }
}
