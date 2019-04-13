package com.afan.spit.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import util.IdWorker;

import com.afan.spit.dao.CommentDao;
import com.afan.spit.pojo.Comment;

/**
 * comment服务层
 * 
 * @author afan
 *
 */
@Service
public class CommentService {

	@Autowired
	private CommentDao commentDao;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Comment> findAll() {
		return commentDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Comment> findSearch(Map whereMap, int page, int size) {
		Specification<Comment> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return commentDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Comment> findSearch(Map whereMap) {
		Specification<Comment> specification = createSpecification(whereMap);
		return commentDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Comment findById(String id) {
		return commentDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param comment
	 */
	public void add(Comment comment) {
		// comment.setId( idWorker.nextId()+"" ); 雪花分布式ID生成器
		commentDao.save(comment);
	}

	/**
	 * 修改
	 * @param comment
	 */
	public void update(Comment comment) {
		commentDao.save(comment);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		commentDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Comment> createSpecification(Map searchMap) {

		return new Specification<Comment>() {

			@Override
			public Predicate toPredicate(Root<Comment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // id
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 吐槽Id
                if (searchMap.get("spitid")!=null && !"".equals(searchMap.get("spitid"))) {
                	predicateList.add(cb.like(root.get("spitid").as(String.class), "%"+(String)searchMap.get("spitid")+"%"));
                }
                // 评论内容
                if (searchMap.get("content")!=null && !"".equals(searchMap.get("content"))) {
                	predicateList.add(cb.like(root.get("content").as(String.class), "%"+(String)searchMap.get("content")+"%"));
                }
                // 用户id
                if (searchMap.get("userid")!=null && !"".equals(searchMap.get("userid"))) {
                	predicateList.add(cb.like(root.get("userid").as(String.class), "%"+(String)searchMap.get("userid")+"%"));
                }
                // 昵称
                if (searchMap.get("nickname")!=null && !"".equals(searchMap.get("nickname"))) {
                	predicateList.add(cb.like(root.get("nickname").as(String.class), "%"+(String)searchMap.get("nickname")+"%"));
                }
                // 0为顶级评论
                if (searchMap.get("parentid")!=null && !"".equals(searchMap.get("parentid"))) {
                	predicateList.add(cb.like(root.get("parentid").as(String.class), "%"+(String)searchMap.get("parentid")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

}
