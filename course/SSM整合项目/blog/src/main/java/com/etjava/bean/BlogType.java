package com.etjava.bean;

/**
 * 博客类别
 * @author etjav
 *
 */
public class BlogType {

	private Integer id;
	private String typeName;
	private Integer orderNo;
	
	// 扩展属性
	private Integer blogCount;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	
	public Integer getBlogCount() {
		return blogCount;
	}
	public void setBlogCount(Integer blogCount) {
		this.blogCount = blogCount;
	}
	@Override
	public String toString() {
		return "BlogType [id=" + id + ", typeName=" + typeName + ", orderNo=" + orderNo + ", blogCount=" + blogCount
				+ "]";
	}
}
