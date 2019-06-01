package com.afan.article.pojo;

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
	private String id;//主键


	
	private String articleid;//文章ID
	private String content;//评论内容
	private String userid;//评论人ID
	private String parentid;//为0测表示顶级评论
	private java.util.Date publishdate;//发布时间
	private String nickname;//昵称

	private String avatar;

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

	public String getArticleid() {
		return articleid;
	}
	public void setArticleid(String articleid) {
		this.articleid = articleid;
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

	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	
}
