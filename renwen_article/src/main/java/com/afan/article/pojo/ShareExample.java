package com.afan.article.pojo;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: ShareExample
 * @Description: TODO
 * @Author：afan
 * @Date : 2019/4/24 11:19
 */
public class ShareExample {

	private String id;//shareID

	private String avater;//用户头像
	private String username;//用户名

	private String content;//文章正文
	private List<String> imgs;//相关图片
	private java.util.Date publishtime;//发表日期

    private String userid;
	private Integer visits;//浏览量
	private Integer thumbup;//点赞数
	private Integer comment;//评论数
	private List<CommentExample> comments;

	public List<CommentExample> getComments() {
		return comments;
	}

	public void setComments(List<CommentExample> comments) {
		this.comments = comments;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Override
	public String toString() {
		return "ShareExample{" +
				"id='" + id + '\'' +
				", avater='" + avater + '\'' +
				", username='" + username + '\'' +
				", content='" + content + '\'' +
				", imgs=" + imgs +
				", publishtime=" + publishtime +
				", visits=" + visits +
				", thumbup=" + thumbup +
				", comment=" + comment +
				'}';
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAvater() {
		return avater;
	}

	public void setAvater(String avater) {
		this.avater = avater;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getImgs() {
		return imgs;
	}

	public void setImgs(List<String> imgs) {
		this.imgs = imgs;
	}

	public Date getPublishtime() {
		return publishtime;
	}

	public void setPublishtime(Date publishtime) {
		this.publishtime = publishtime;
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
}
