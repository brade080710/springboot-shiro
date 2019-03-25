package com.xm.shiro.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import com.xm.shiro.entity.ServiceResult;
import com.xm.shiro.utils.RedisUtil;

/**
 * 基于redis限流
 * @author gaoxinda
 *
 */
@Controller
@CrossOrigin
public class LimitRateController {
	
	@Autowired
	RedisUtil redisUtil;
	
	@GetMapping(value = "/limitRate")
    public ServiceResult limitRate() {
        ServiceResult serviceResult = null;
        if(redisUtil.get("LimitCount")!=null) {
            Integer countExist = (Integer) redisUtil.get("LimitCount");
            // -2, 键不存在   -1,如果key到期超时
            Long expireTimes = redisUtil.getExpire("LimitCount");
            if(expireTimes>-1) {
                if(countExist>10) {
                    serviceResult = new ServiceResult(-102,"LimitCount没秒超过10次访问，返回错误");
                    serviceResult.setData(countExist);
                    return serviceResult;
                }else {
                    int count = countExist+1;
                    redisUtil.increValue("LimitCount");
                    serviceResult = new ServiceResult("SUCCESS");
                    serviceResult.setData(count);
                    return serviceResult;
                }
            }else {
                redisUtil.remove("LimitCount");
                redisUtil.set("LimitCount",1,10L);
                serviceResult = new ServiceResult(100,"LimitCount超时，删除后，创建LimitCount=1");
                serviceResult.setData(1);
                return serviceResult;
            }
        }else {
        	redisUtil.set("LimitCount",1,10L);
            serviceResult = new ServiceResult(100,"LimitCount不存在，创建LimitCount=1");
            serviceResult.setData(1);
            return serviceResult;
        }
    }
	

}
