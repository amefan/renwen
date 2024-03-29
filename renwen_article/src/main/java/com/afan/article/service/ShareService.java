package com.afan.article.service;



import com.afan.article.dao.CommentDao;
import com.afan.article.dao.ShareDao;
import com.afan.article.pojo.Comment;
import com.afan.article.pojo.CommentExample;
import com.afan.article.pojo.Share;
import com.afan.article.pojo.ShareExample;
import com.afan.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * share服务层
 * 
 * @author afan
 *
 */
@Service
public class ShareService {

	@Autowired
	private ShareDao shareDao;

	@Autowired
	private IdWorker idWorker;

	@Autowired
	private CommentDao commentDao;

//	@Autowired
//	private UserService userService;
	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Share> findAll() {
		return shareDao.findAll();
	}


	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public List<ShareExample> findSearch(Map whereMap, int page, int size) {
		Specification<Share> specification = createSpecification(whereMap);
		Sort sort = new Sort(Sort.Direction.DESC,"publishtime");
		PageRequest pageRequest =  PageRequest.of(page-1, size,sort); // 构造分页
		Page<Share> pageList = shareDao.findAll(specification, pageRequest); //查询分页内容

		List<ShareExample> sharelist = new ArrayList<>();
		List<Share> list = pageList.getContent();// 获取分页内容
		for (Share share : list) {
			ShareExample shareExample = new ShareExample();
			shareExample.setComment(share.getComment());
			shareExample.setContent(share.getContent());
			shareExample.setId(share.getId());
			shareExample.setUserid(share.getUserid());
			shareExample.setVisits(share.getVisits());
			shareExample.setThumbup(share.getThumbup());
			shareExample.setPublishtime(share.getPublishtime());
			shareExample.setImgs(Arrays.asList(share.getImgs().split(",")));
			shareExample.setAvater(share.getAvatar());
			shareExample.setUsername(share.getUsername());
			// 封装评论
			List<Comment> comentList = commentDao.findCommentsByArticleidAndParentid(share.getId(),
					"0");
			List<CommentExample> cxList = new ArrayList<>();
			for (Comment comment : comentList) {
				CommentExample cex = new CommentExample();
				cex.setId(comment.getId());
				cex.setAvatar(comment.getAvatar());
				cex.setContent(comment.getContent());
				cex.setNickname(comment.getNickname());
				cex.setPublishdate(comment.getPublishdate());

				List<Comment> childlist = commentDao.findCommentsByArticleidAndParentid(share
								.getId(),
						comment.getId());

				cex.setChildcomment(childlist);
				cxList.add(cex);
			}
            shareExample.setComments(cxList);

			sharelist.add(shareExample);
		}

		return sharelist;
	}


	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Share> findSearch(Map whereMap) {
		Specification<Share> specification = createSpecification(whereMap);
		return shareDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Share findById(String id) {
		return shareDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param share
	 */
	public void add(Share share) {
		share.setId( idWorker.nextId()+"" );
		share.setComment(0);
		share.setState("1");
		share.setVisits(0);
		share.setThumbup(0);
		share.setPublishtime(new Date());
		shareDao.save(share);
	}

	/**
	 * 修改
	 * @param share
	 */
	public void update(Share share) {
		shareDao.save(share);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		shareDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Share> createSpecification(Map searchMap) {
		return new Specification<Share>() {
			/**
			 * @Description:
			 * @author: afan
			 * @param: [root:根对象，封装着要根据查询的条件 ，相当于 where 列名 = label.getId
			 *         criteriaQuery: 封装查询关键字 order by group by
			 *         criteriaBuilder: 封装条件对象,返回null 就是无条件查询
			 *         ]
			 * @return: javax.persistence.criteria.Predicate
			 */
			@Override
			public Predicate toPredicate(Root<Share> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>(); //创建条件来存放多个条件
                //判断是否传入此条件
                if(!"".equals(searchMap.get("starttime_1"))&&!"".equals(searchMap.get
						("starttime_2"))&&searchMap.get("starttime_1")!=null &&searchMap.get
						("starttime_2")!=null){
					SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date starttime_1 = sdf.parse(String.valueOf(searchMap.get
								("starttime_1"))); //将前端转来的字符串时间格式变为date类型
						Date starttime_2= sdf.parse(String.valueOf(searchMap.get
								("starttime_2")));
						predicateList.add(cb.between(root.get("createtime").as(Date.class),
								starttime_1,starttime_2));
					}catch (Exception e){
						e.printStackTrace();
					}

				}
                // 用户ID
                if (searchMap.get("userid")!=null && !"".equals(searchMap.get("userid"))) {
                	predicateList.add(cb.like(root.get("userid").as(String.class), "%"+(String)searchMap.get("userid")+"%"));
                }
                // 标题
                if (searchMap.get("title")!=null && !"".equals(searchMap.get("title"))) {
                	predicateList.add(cb.like(root.get("title").as(String.class), "%"+(String)searchMap.get("title")+"%"));
                }

                // 审核状态
                if (searchMap.get("state")!=null && !"".equals(searchMap.get("state"))) {
                	predicateList.add(cb.like(root.get("state").as(String.class), "%"+(String)searchMap.get("state")+"%"));
                }

				// 审核状态
				if (searchMap.get("content")!=null && !"".equals(searchMap.get("content"))) {
					predicateList.add(cb.like(root.get("content").as(String.class), "%"+(String)searchMap.get("content")+"%"));
				}

				// 审核状态
				if (searchMap.get("username")!=null && !"".equals(searchMap.get("username"))) {
					predicateList.add(cb.like(root.get("username").as(String.class), "%"+(String)searchMap.get("username")+"%"));
				}
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}


	public void updateThumbup(String id) {
		Share share = shareDao.findById(id).get();
		share.setVisits(share.getVisits()+1);
		share.setThumbup(share.getThumbup()+1);
		shareDao.save(share);
	}
}
