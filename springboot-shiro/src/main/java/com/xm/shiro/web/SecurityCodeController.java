package com.xm.shiro.web;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xm.shiro.utils.SecurityImage;

public class SecurityCodeController {
	private final static Logger logger = LoggerFactory.getLogger(SecurityCodeController.class);
	 // 图片验证码
    @RequestMapping("/image")
    public void genImage(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        try {
            String code = SecurityImage.generateVerifyCode(4);
            HttpSession session = request.getSession();
            session.setAttribute("imageCode", code);
            session.setAttribute("imageCodeExpire", new Date().getTime());
            SecurityImage.outputImage(100, 30, response.getOutputStream(), code);
        } catch (IOException e) {
        	logger.error(e.getMessage());
        }
    }
}
