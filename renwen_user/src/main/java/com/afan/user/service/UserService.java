package com.afan.user.service;

import java.util.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.afan.user.UserApplication;
import com.afan.user.utils.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import util.IdWorker;

import com.afan.user.dao.UserDao;
import com.afan.user.pojo.User;

/**
 * user服务层
 * 
 * @author afan
 *
 */
@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private IdWorker idWorker;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private BCryptPasswordEncoder encoder;
	/**
	 * @Description: 发送短信
	 * @author: afan
	 * @param: [mobile]
	 * @return: void
	 */
	public void sendSms(String mobile)  {
		// 随机生成6位验证码
		Random random = new Random();
		int max = 999999;
		int min = 100000; // 验证码最大最小值
		int smsCode = random.nextInt(max);
		if (smsCode<min){
			smsCode = min+smsCode;
		}
		try {
			// 存到缓存中
			redisTemplate.opsForValue().set("smscode_"+mobile,smsCode);
			SmsUtil.sendSms(mobile,String.valueOf(smsCode));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("验证码："+ smsCode);




	}
	/**
	 * 查询全部列表
	 * @return
	 */
	public List<User> findAll() {
		return userDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<User> findSearch(Map whereMap, int page, int size) {
		Specification<User> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return userDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<User> findSearch(Map whereMap) {
		Specification<User> specification = createSpecification(whereMap);
		return userDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public User findById(String id) {
		return userDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param user
	 */
	public void add(User user) {
		user.setId( idWorker.nextId()+"" );
		user.setFanscount(0);
		user.setFollowcount(0);
		userDao.save(user);
	}

	/**
	 * 修改
	 * @param user
	 */
	public void update(User user) {
		userDao.save(user);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		userDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<User> createSpecification(Map searchMap) {

		return new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();

                // 手机号码
                if (searchMap.get("mobile")!=null && !"".equals(searchMap.get("mobile"))) {
                	predicateList.add(cb.like(root.get("mobile").as(String.class), "%"+(String)searchMap.get("mobile")+"%"));
                }
                // 密码
                if (searchMap.get("password")!=null && !"".equals(searchMap.get("password"))) {
                	predicateList.add(cb.like(root.get("password").as(String.class), "%"+(String)searchMap.get("password")+"%"));
                }
                // 昵称
                if (searchMap.get("nickname")!=null && !"".equals(searchMap.get("nickname"))) {
                	predicateList.add(cb.like(root.get("nickname").as(String.class), "%"+(String)searchMap.get("nickname")+"%"));
                }
                // 性别
                if (searchMap.get("sex")!=null && !"".equals(searchMap.get("sex"))) {
                	predicateList.add(cb.like(root.get("sex").as(String.class), "%"+(String)searchMap.get("sex")+"%"));
                }
                // 头像
                if (searchMap.get("avatar")!=null && !"".equals(searchMap.get("avatar"))) {
                	predicateList.add(cb.like(root.get("avatar").as(String.class), "%"+(String)searchMap.get("avatar")+"%"));
                }
                // E-Mail
                if (searchMap.get("email")!=null && !"".equals(searchMap.get("email"))) {
                	predicateList.add(cb.like(root.get("email").as(String.class), "%"+(String)searchMap.get("email")+"%"));
                }
                // 兴趣
                if (searchMap.get("interest")!=null && !"".equals(searchMap.get("interest"))) {
                	predicateList.add(cb.like(root.get("interest").as(String.class), "%"+(String)searchMap.get("interest")+"%"));
                }
                // 个性
                if (searchMap.get("personality")!=null && !"".equals(searchMap.get("personality"))) {
                	predicateList.add(cb.like(root.get("personality").as(String.class), "%"+(String)searchMap.get("personality")+"%"));
                }
                // 专业
                if (searchMap.get("major")!=null && !"".equals(searchMap.get("major"))) {
                	predicateList.add(cb.like(root.get("major").as(String.class), "%"+(String)searchMap.get("major")+"%"));
                }
                // QQ
                if (searchMap.get("city")!=null && !"".equals(searchMap.get("city"))) {
                	predicateList.add(cb.like(root.get("city").as(String.class), "%"+(String)searchMap.get("city")+"%"));
                }
                // 社团
                if (searchMap.get("org")!=null && !"".equals(searchMap.get("org"))) {
                	predicateList.add(cb.like(root.get("org").as(String.class), "%"+(String)searchMap.get("org")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}
	/**
	 * @Description: 注册
	 * @author: afan
	 * @param: [user, code]
	 * @return: void
	 */
	public void register(User user, String code) {
		System.out.println(user.toString());
		String syscode = String.valueOf(redisTemplate.opsForValue().get("smscode_" + user
				.getMobile()));
		if(syscode==null){
			throw new  RuntimeException("请获取短信验证码");
		}

		if(!syscode.equals(code)){
			throw new  RuntimeException("输入的验证码有误，重新输入");
		}
		String newPass = encoder.encode(user.getPassword()); // 加密密码
		user.setPassword(newPass);
		user.setId(idWorker.nextId()+"");
		user.setFollowcount(0);//关注数        
		user.setFanscount(0);//粉丝数        
		user.setAvatar("http://localhost/logo/logo_1555158843139.jpg");
		user.setRegdate(new Date());//注册日期        

		userDao.save(user);
		redisTemplate.delete("smscode_" + user.getMobile());
	}

	public User checkLogin(String mobile,String password){
		User user = userDao.findByMobile(mobile);
		System.out.println(mobile+" "+password);
		if (user != null&& encoder.matches(password,user.getPassword())) {
			return user;
		}
		return null;
	}

	public List<User> GetOnlineUser(){
		Set<String> sets = UserApplication.sessionMap.keySet();
		List<User> list = new ArrayList<>();
		for (String set : sets) {

			User user = userDao.findUserByNickname(set);
			System.out.println(user);
			list.add(user);

		}
		return list;
	}

	public boolean checkPhone(String phone) {
		User us = userDao.findByMobile(phone);
		if(us==null){
			return false;
		}
		return true;
	}
}
