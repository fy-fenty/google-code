package org.ymm.entity;
// default package

/**
 * SysMenu entity. @author MyEclipse Persistence Tools
 */

public class SysMenu implements java.io.Serializable {

	// Fields

	private Long MId;
	private String menuName;
	private String url;

	// Constructors

	/** default constructor */
	public SysMenu() {
	}

	/** minimal constructor */
	public SysMenu(Long MId) {
		this.MId = MId;
	}

	/** full constructor */
	public SysMenu(Long MId, String menuName, String url) {
		this.MId = MId;
		this.menuName = menuName;
		this.url = url;
	}

	// Property accessors

	public Long getMId() {
		return this.MId;
	}

	public void setMId(Long MId) {
		this.MId = MId;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}