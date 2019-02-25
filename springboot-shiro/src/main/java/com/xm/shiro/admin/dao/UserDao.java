package com.xm.shiro.admin.dao;

import com.xm.shiro.admin.entity.User;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface UserDao {

	List<User> getByMap(Map<String, Object> map);
	User getById(Integer id);
	Integer create(User user);
	int update(User user);
	int delete(Integer id);
	User getByUserName(String userName);
}