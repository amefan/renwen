package com.afan.user.pojo;

/**
 * @ClassName: SocketMessage
 * @Description: TODO
 * @Author：afan
 * @Date : 2019/4/27 12:46
 */
public class SocketMessage {
	private String fromUser;
	private String toUser;
	private String message;

	private String date;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
