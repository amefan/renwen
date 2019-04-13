package com.afan.spit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.afan.spit.pojo.Spit;
/**
 * spit数据访问接口
 * @author afan
 *
 */
public interface SpitDao extends JpaRepository<Spit,String>,JpaSpecificationExecutor<Spit>{
	
}
