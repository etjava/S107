package com.etjava.bean;

import java.util.Date;

/**
 * 评论实体类
 * @author etjav
 *
 */
public class Comment {

	private Integer id;
	private String userAddr;
	private String content;
	private Date commonDate;
	private Integer state;
	
	private Blog blog;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserAddr() {
		return userAddr;
	}

	public void setUserAddr(String userAddr) {
		this.userAddr = userAddr;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCommonDate() {
		return commonDate;
	}

	public void setCommonDate(Date commonDate) {
		this.commonDate = commonDate;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", userAddr=" + userAddr + ", content=" + content + ", commonDate=" + commonDate
				+ ", state=" + state + ", blog=" + blog + "]";
	}
}
