package org.han.entity;


/**
 * LoginUser entity. @author MyEclipse Persistence Tools
 */

public class LoginUser implements java.io.Serializable {

	// Fields

	private Long UId;
	private String ESn;
	private String UPwd;

	// Constructors

	/** default constructor */
	public LoginUser() {
	}

	/** minimal constructor */
	public LoginUser(Long UId) {
		this.UId = UId;
	}

	/** full constructor */
	public LoginUser(Long UId, String ESn, String UPwd) {
		this.UId = UId;
		this.ESn = ESn;
		this.UPwd = UPwd;
	}

	// Property accessors

	public Long getUId() {
		return this.UId;
	}

	public void setUId(Long UId) {
		this.UId = UId;
	}

	public String getESn() {
		return this.ESn;
	}

	public void setESn(String ESn) {
		this.ESn = ESn;
	}

	public String getUPwd() {
		return this.UPwd;
	}

	public void setUPwd(String UPwd) {
		this.UPwd = UPwd;
	}

}