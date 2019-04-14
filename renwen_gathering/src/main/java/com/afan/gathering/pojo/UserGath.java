package com.afan.gathering.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_usergath")
public class UserGath implements Serializable{
  @Id
  private String id;

  private String userid;
  private String gathid;
  private String nickname;
  private Date exetime;
  private String phone;
  private String qq;

  public String getPhone() {
    return phone;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getQq() {
    return qq;
  }

  public void setQq(String qq) {
    this.qq = qq;
  }

  public String getUserid() {
    return userid;
  }

  public void setUserid(String userid) {
    this.userid = userid;
  }

  public String getGathid() {
    return gathid;
  }

  public void setGathid(String gathid) {
    this.gathid = gathid;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public Date getExetime() {
    return exetime;
  }

  public void setExetime(Date exetime) {
    this.exetime = exetime;
  }
}
