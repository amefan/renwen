package com.afan.spit.service;

import java.util.*;

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

import com.afan.spit.dao.ConfessionDao;
import com.afan.spit.pojo.Confession;

/**
 * confession服务层
 * 
 * @author afan
 *
 */
@Service
public class ConfessionService {

	@Autowired
	private ConfessionDao confessionDao;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Confession> findAll() {
		return confessionDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Confession> findSearch(Map whereMap, int page, int size) {
		Specification<Confession> specification = createSpecification(whereMap);
		Sort sort = new Sort(Sort.Direction.DESC,"publishtime");
		PageRequest pageRequest =  PageRequest.of(page-1, size,sort);
		return confessionDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Confession> findSearch(Map whereMap) {
		Specification<Confession> specification = createSpecification(whereMap);
		return confessionDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Confession findById(String id) {
		return confessionDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param confession
	 */
	public void add(Confession confession) {
		if(confession.getFromuser()==null||"".equals(confession.getFromuser())){
			confession.setFromuser("匿名用户");
		}
		confession.setId( idWorker.nextId()+"" );
		confession.setPublishtime(new Date());

		confessionDao.save(confession);
	}

	/**
	 * 修改
	 * @param confession
	 */
	public void update(Confession confession) {
		confessionDao.save(confession);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		confessionDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Confession> createSpecification(Map searchMap) {

		return new Specification<Confession>() {

			@Override
			public Predicate toPredicate(Root<Confession> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // 主键
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 内容
                if (searchMap.get("content")!=null && !"".equals(searchMap.get("content"))) {
                	predicateList.add(cb.like(root.get("content").as(String.class), "%"+(String)searchMap.get("content")+"%"));
                }
                // 发布人Id
                if (searchMap.get("userid")!=null && !"".equals(searchMap.get("userid"))) {
                	predicateList.add(cb.like(root.get("userid").as(String.class), "%"+(String)searchMap.get("userid")+"%"));
                }
                // 1 可见，0不可见
                if (searchMap.get("state")!=null && !"".equals(searchMap.get("state"))) {
                	predicateList.add(cb.like(root.get("state").as(String.class), "%"+(String)searchMap.get("state")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

}
