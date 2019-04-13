package com.afan.spit.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
/**
 * comment实体类
 * @author afan
 *
 */
@Entity
@Table(name="tb_comment")
public class Comment implements Serializable{

	@Id
	private String id;//id


	
	private String spitid;//吐槽Id
	private String content;//评论内容
	private String userid;//用户id
	private String nickname;//昵称
	private String parentid;//0为顶级评论
	private java.util.Date publishdate;//发布日期

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getSpitid() {
		return spitid;
	}
	public void setSpitid(String spitid) {
		this.spitid = spitid;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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

	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public java.util.Date getPublishdate() {
		return publishdate;
	}
	public void setPublishdate(java.util.Date publishdate) {
		this.publishdate = publishdate;
	}


	
}
