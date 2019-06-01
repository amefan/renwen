package com.afan.article.pojo;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: CommentExample
 * @Description: TODO
 * @Author：afan
 * @Date : 2019/4/24 16:42
 */
public class CommentExample {

	private String id;//主键
	private String content;//评论内容
	private java.util.Date publishdate;//发布时间
	private String nickname;//昵称
	private List<Comment> childcomment;
	private String avatar;//评论内容

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPublishdate() {
		return publishdate;
	}

	public void setPublishdate(Date publishdate) {
		this.publishdate = publishdate;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public List<Comment> getChildcomment() {
		return childcomment;
	}

	public void setChildcomment(List<Comment> childcomment) {
		this.childcomment = childcomment;
	}
}
