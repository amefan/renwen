package com.afan.gathering.dao;

import com.afan.gathering.pojo.UserGath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
/**
 * userGath数据访问接口
 * @author afan
 *
 */
public interface UserGathDao extends JpaRepository<UserGath,String>,JpaSpecificationExecutor<UserGath> {
}
