package com.xm.shiro.admin.dao;


import com.xm.shiro.admin.entity.Role;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;
import java.util.Map;
@Mapper
public interface RoleDao {

	List<Role> getByMap(Map<String, Object> map);
	Role getById(Integer id);
	Integer create(Role role);
	int update(Role role);
	int delete(Integer id);
}