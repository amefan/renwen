package com.afan.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.afan.user.pojo.User;
/**
 * user数据访问接口
 * @author afan
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{
	// 根据手机号查询用户
	public User findByMobile(String mobile);

	public User findUserByNickname(String name);
}
