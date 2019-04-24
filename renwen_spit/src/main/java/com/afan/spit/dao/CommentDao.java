package com.afan.spit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.afan.spit.pojo.Comment;

import java.util.List;

/**
 * comment数据访问接口
 * @author afan
 *
 */
public interface CommentDao extends JpaRepository<Comment,String>,JpaSpecificationExecutor<Comment>{
	List<Comment> findCommentsBySpitid(String spitid);
}
