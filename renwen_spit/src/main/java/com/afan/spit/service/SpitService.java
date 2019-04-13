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

import com.afan.spit.dao.SpitDao;
import com.afan.spit.pojo.Spit;

/**
 * spit服务层
 * 
 * @author afan
 *
 */
@Service
public class SpitService {

	@Autowired
	private SpitDao spitDao;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Spit> findAll() {
		return spitDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Spit> findSearch(Map whereMap, int page, int size) {
		Specification<Spit> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return spitDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Spit> findSearch(Map whereMap) {
		Specification<Spit> specification = createSpecification(whereMap);
		return spitDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Spit findById(String id) {
		return spitDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param spit
	 */
	public void add(Spit spit) {
		// spit.setId( idWorker.nextId()+"" ); 雪花分布式ID生成器
		spitDao.save(spit);
	}

	/**
	 * 修改
	 * @param spit
	 */
	public void update(Spit spit) {
		spitDao.save(spit);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		spitDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Spit> createSpecification(Map searchMap) {

		return new Specification<Spit>() {

			@Override
			public Predicate toPredicate(Root<Spit> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // 主键
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 吐槽内容
                if (searchMap.get("content")!=null && !"".equals(searchMap.get("content"))) {
                	predicateList.add(cb.like(root.get("content").as(String.class), "%"+(String)searchMap.get("content")+"%"));
                }
                // 发布人Id
                if (searchMap.get("userid")!=null && !"".equals(searchMap.get("userid"))) {
                	predicateList.add(cb.like(root.get("userid").as(String.class), "%"+(String)searchMap.get("userid")+"%"));
                }
                // 发布人昵称
                if (searchMap.get("nickname")!=null && !"".equals(searchMap.get("nickname"))) {
                	predicateList.add(cb.like(root.get("nickname").as(String.class), "%"+(String)searchMap.get("nickname")+"%"));
                }
                // '1'可见，'0'不可见
                if (searchMap.get("state")!=null && !"".equals(searchMap.get("state"))) {
                	predicateList.add(cb.like(root.get("state").as(String.class), "%"+(String)searchMap.get("state")+"%"));
                }
                // 上级Id
                if (searchMap.get("parentid")!=null && !"".equals(searchMap.get("parentid"))) {
                	predicateList.add(cb.like(root.get("parentid").as(String.class), "%"+(String)searchMap.get("parentid")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

}
