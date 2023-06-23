package com.etjava.bean;

/**
 *博主信息实体类
 * @author etjav
 *
 */
public class Users {

	private Integer id;
	private String userName;
	private String password;
	private String profile; // 简介
	private String nickName; // 昵称
	private String sign; // 个性签名
	private String imageName; // 头像名称
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	@Override
	public String toString() {
		return "Users [id=" + id + ", userName=" + userName + ", password=" + password + ", profile=" + profile
				+ ", nickName=" + nickName + ", sign=" + sign + ", imageName=" + imageName + "]";
	}
	
}
