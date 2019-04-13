package com.afan.spit.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
/**
 * spit实体类
 * @author afan
 *
 */
@Entity
@Table(name="tb_spit")
public class Spit implements Serializable{

	@Id
	private String id;//主键


	
	private String content;//吐槽内容
	private java.util.Date publishtime;//发布时间
	private String userid;//发布人Id
	private String nickname;//发布人昵称
	private Integer visits;//浏览数
	private Integer thumbup;//点赞数
	private Integer comment;//评论数
	private String state;//'1'可见，'0'不可见
	private String parentid;//上级Id

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public java.util.Date getPublishtime() {
		return publishtime;
	}
	public void setPublishtime(java.util.Date publishtime) {
		this.publishtime = publishtime;
	}

	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}


	
}
