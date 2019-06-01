package com.afan.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.afan.article.pojo.Comment;

import java.util.List;

/**
 * comment数据访问接口
 * @author afan
 *
 */
public interface CommentDao extends JpaRepository<Comment,String>,JpaSpecificationExecutor<Comment>{
	// 根据文章Id和父id查询评论
	List<Comment> findCommentsByArticleidAndParentid(String artId, String paretid);
}
