package com.magicalcoder.youyamvc.app.model;

import java.io.Serializable;

public class AdminUser implements Serializable{
	private Long id;// 
	private String userName;// 用户名
	private String password;// `password` varchar DEFAULT '密码'
	private String realName;// 真名
	private String email;// 邮箱
	private String telephone;// 座机号
	private String mobilePhone;// 手机号
	private String address;// 手机号
	private Integer superAdmin;// 超级管理员
	private int createTimeYmd;
	private int createTimeHms;
	private int modifiedTimeYmd;
	private int modifiedTimeHms;
	public Long getId(){
			return id;
	}
	public void  setId(Long id){
			this.id = id;
	}
	public String getUserName(){
			return userName;
	}
	public void  setUserName(String userName){
			this.userName = userName;
	}
	public String getPassword(){
			return password;
	}
	public void  setPassword(String password){
			this.password = password;
	}
	public String getRealName(){
			return realName;
	}
	public void  setRealName(String realName){
			this.realName = realName;
	}
	public String getEmail(){
			return email;
	}
	public void  setEmail(String email){
			this.email = email;
	}
	public String getTelephone(){
			return telephone;
	}
	public void  setTelephone(String telephone){
			this.telephone = telephone;
	}
	public String getMobilePhone(){
			return mobilePhone;
	}
	public void  setMobilePhone(String mobilePhone){
			this.mobilePhone = mobilePhone;
	}
	public String getAddress(){
			return address;
	}
	public void  setAddress(String address){
			this.address = address;
	}
	public Integer getSuperAdmin(){
			return superAdmin;
	}
	public void  setSuperAdmin(Integer superAdmin){
			this.superAdmin = superAdmin;
	}

	public int getCreateTimeYmd() {
		return createTimeYmd;
	}

	public void setCreateTimeYmd(int createTimeYmd) {
		this.createTimeYmd = createTimeYmd;
	}

	public int getCreateTimeHms() {
		return createTimeHms;
	}

	public void setCreateTimeHms(int createTimeHms) {
		this.createTimeHms = createTimeHms;
	}

	public int getModifiedTimeYmd() {
		return modifiedTimeYmd;
	}

	public void setModifiedTimeYmd(int modifiedTimeYmd) {
		this.modifiedTimeYmd = modifiedTimeYmd;
	}

	public int getModifiedTimeHms() {
		return modifiedTimeHms;
	}

	public void setModifiedTimeHms(int modifiedTimeHms) {
		this.modifiedTimeHms = modifiedTimeHms;
	}
}
