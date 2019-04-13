package com.afan.spit.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
/**
 * confession实体类
 * @author afan
 *
 */
@Entity
@Table(name="tb_confession")
public class Confession implements Serializable{

	@Id
	private String id;//主键


	
	private String content;//内容
	private java.util.Date publishtime;//发布时间
	private String userid;//发布人Id
	private String state;//1 可见，0不可见

	
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

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}


	
}
