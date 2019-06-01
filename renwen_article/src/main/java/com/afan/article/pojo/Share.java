package com.afan.article.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * article实体类
 * @author afan
 *
 */
@Entity
@Table(name="tb_share")
public class Share implements Serializable{

	@Id
	private String id;//ID


	

	private String userid;//用户ID

	private String content;//文章正文
	private String imgs;//文章封面
	private java.util.Date publishtime;//发表日期


	private Integer visits;//浏览量
	private Integer thumbup;//点赞数
	private Integer comment;//评论数
	private String state;//审核状态
	private String username;//审核状态
	private String avatar;//审核状态

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}


	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}



	public Integer getVisits() {
		return visits;
	}
	public void setVisits(Integer visits) {
		this.visits = visits;
	}

	public Integer getThumbup() {
		return thumbup;
	}
	public void setThumbup(Integer thumbup) {
		this.thumbup = thumbup;
	}

	public Integer getComment() {
		return comment;
	}
	public void setComment(Integer comment) {
		this.comment = comment;
	}

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}


	public String getImgs() {
		return imgs;
	}

	public void setImgs(String imgs) {
		this.imgs = imgs;
	}

	public Date getPublishtime() {
		return publishtime;
	}

	public void setPublishtime(Date publishtime) {
		this.publishtime = publishtime;
	}
}
