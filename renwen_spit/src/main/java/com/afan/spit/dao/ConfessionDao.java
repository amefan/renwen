package com.afan.spit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.afan.spit.pojo.Confession;
/**
 * confession数据访问接口
 * @author afan
 *
 */
public interface ConfessionDao extends JpaRepository<Confession,String>,JpaSpecificationExecutor<Confession>{
	
}
