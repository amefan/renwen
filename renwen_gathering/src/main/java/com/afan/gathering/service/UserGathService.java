package com.afan.gathering.service;

import com.afan.gathering.dao.UserGathDao;
import com.afan.gathering.pojo.UserGath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: UserGathService
 * @Description: TODO
 * @Author：afan
 * @Date : 2019/4/14 11:00
 */
@Service
public class UserGathService {
	@Autowired
	private UserGathDao userGathDao;


	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<UserGath> findAll() {
		return userGathDao.findAll();
	}


	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<UserGath> findSearch(Map whereMap, int page, int size) {
		Specification<UserGath> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return userGathDao.findAll(specification, pageRequest);
	}


	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<UserGath> findSearch(Map whereMap) {
		Specification<UserGath> specification = createSpecification(whereMap);
		return userGathDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public UserGath findById(String id) {
		return userGathDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param UserGath
	 */
	public void add(UserGath UserGath) {
		UserGath.setId(idWorker.nextId()+"");
		UserGath.setExetime(new Date());
		userGathDao.save(UserGath);
	}

	/**
	 * 修改
	 * @param UserGath
	 */
	public void update(UserGath UserGath) {
		userGathDao.save(UserGath);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		userGathDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<UserGath> createSpecification(Map searchMap) {

		return new Specification<UserGath>() {

			@Override
			public Predicate toPredicate(Root<UserGath> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();

				// 文章ID
				if (searchMap.get("gathid")!=null && !"".equals(searchMap.get("gathid"))) {
					predicateList.add(cb.like(root.get("gathid").as(String.class), "%"+(String)searchMap.get("gathid")+"%"));
				}

				// 评论人ID
				if (searchMap.get("userid")!=null && !"".equals(searchMap.get("userid"))) {
					predicateList.add(cb.like(root.get("userid").as(String.class), "%"+(String)searchMap.get("userid")+"%"));
				}
				// 评论人ID
				if (searchMap.get("nickname")!=null && !"".equals(searchMap.get("nickname"))) {
					predicateList.add(cb.like(root.get("nickname").as(String.class), "%"+(String)searchMap.get("nickname")+"%"));
				}


				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}
}
