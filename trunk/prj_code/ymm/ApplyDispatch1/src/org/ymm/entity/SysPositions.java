package org.ymm.entity;


/**
 * SysPositions entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class SysPositions implements java.io.Serializable {

	// Fields

	private Long PId;
	private String PNameCn;
	private String PNameEn;

	// Constructors

	/** default constructor */
	public SysPositions() {
	}

	/** minimal constructor */
	public SysPositions(Long PId) {
		this.PId = PId;
	}

	/** full constructor */
	public SysPositions(Long PId, String PNameCn, String PNameEn) {
		this.PId = PId;
		this.PNameCn = PNameCn;
		this.PNameEn = PNameEn;
	}

	// Property accessors

	public Long getPId() {
		return this.PId;
	}

	public void setPId(Long PId) {
		this.PId = PId;
	}

	public String getPNameCn() {
		return this.PNameCn;
	}

	public void setPNameCn(String PNameCn) {
		this.PNameCn = PNameCn;
	}

	public String getPNameEn() {
		return this.PNameEn;
	}

	public void setPNameEn(String PNameEn) {
		this.PNameEn = PNameEn;
	}

}