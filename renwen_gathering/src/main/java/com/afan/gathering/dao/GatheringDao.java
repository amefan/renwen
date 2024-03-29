package com.afan.gathering.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.afan.gathering.pojo.Gathering;
/**
 * gathering数据访问接口
 * @author afan
 *
 */
public interface GatheringDao extends JpaRepository<Gathering,String>,JpaSpecificationExecutor<Gathering>{
	
}
