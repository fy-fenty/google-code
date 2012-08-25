package org.ymm.entity;
// default package

/**
 * SysRole entity. @author MyEclipse Persistence Tools
 */

public class SysRole implements java.io.Serializable {

	// Fields

	private Long RId;
	private Long PId;
	private String menu;

	// Constructors

	/** default constructor */
	public SysRole() {
	}

	/** minimal constructor */
	public SysRole(Long RId, Long PId) {
		this.RId = RId;
		this.PId = PId;
	}

	/** full constructor */
	public SysRole(Long RId, Long PId, String menu) {
		this.RId = RId;
		this.PId = PId;
		this.menu = menu;
	}

	// Property accessors

	public Long getRId() {
		return this.RId;
	}

	public void setRId(Long RId) {
		this.RId = RId;
	}
	public Long getPId() {
		return PId;
	}

	public void setPId(Long pId) {
		PId = pId;
	}

	public String getMenu() {
		return this.menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

}