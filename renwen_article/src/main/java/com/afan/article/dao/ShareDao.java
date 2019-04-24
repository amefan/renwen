package com.afan.article.dao;

import com.afan.article.pojo.Article;
import com.afan.article.pojo.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * article数据访问接口
 * @author afan
 *
 */
public interface ShareDao extends JpaRepository<Share,String>,JpaSpecificationExecutor<Share>{

}
