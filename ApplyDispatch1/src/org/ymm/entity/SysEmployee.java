package org.ymm.entity;

import java.lang.Long;
import java.sql.Timestamp;

/**
 * SysEmployee entity. @author MyEclipse Persistence Tools
 */

public class SysEmployee implements java.io.Serializable {

	// Fields

	private Long EId;
	private Long sysPositions;
	private Long sysDepartment;
	private String ESn;
	private String EName;
	private String EIdentity;
	private Boolean sex;
	private Timestamp birthday;
	private String address;
	private String tel;
	private Boolean status;
	private Timestamp inTime;
	private Timestamp outTime;

	// Constructors

	/** default constructor */
	public SysEmployee() {
	}

	/** minimal constructor */
	public SysEmployee(Long EId) {
		this.EId = EId;
	}

	/** full constructor */
	public SysEmployee(Long EId, Long sysPositions,
			Long sysDepartment, String ESn, String EName,
			String EIdentity, Boolean sex, Timestamp birthday, String address,
			String tel, Boolean status, Timestamp inTime, Timestamp outTime) {
		this.EId = EId;
		this.sysPositions = sysPositions;
		this.sysDepartment = sysDepartment;
		this.ESn = ESn;
		this.EName = EName;
		this.EIdentity = EIdentity;
		this.sex = sex;
		this.birthday = birthday;
		this.address = address;
		this.tel = tel;
		this.status = status;
		this.inTime = inTime;
		this.outTime = outTime;
	}

	// Property accessors

	public Long getEId() {
		return this.EId;
	}

	public void setEId(Long EId) {
		this.EId = EId;
	}

	public Long getSysPositions() {
		return this.sysPositions;
	}

	public void setSysPositions(Long sysPositions) {
		this.sysPositions = sysPositions;
	}

	public Long getSysDepartment() {
		return this.sysDepartment;
	}

	public void setSysDepartment(Long sysDepartment) {
		this.sysDepartment = sysDepartment;
	}

	public String getESn() {
		return this.ESn;
	}

	public void setESn(String ESn) {
		this.ESn = ESn;
	}

	public String getEName() {
		return this.EName;
	}

	public void setEName(String EName) {
		this.EName = EName;
	}

	public String getEIdentity() {
		return this.EIdentity;
	}

	public void setEIdentity(String EIdentity) {
		this.EIdentity = EIdentity;
	}

	public Boolean getSex() {
		return this.sex;
	}

	public void setSex(Boolean sex) {
		this.sex = sex;
	}

	public Timestamp getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Timestamp getInTime() {
		return this.inTime;
	}

	public void setInTime(Timestamp inTime) {
		this.inTime = inTime;
	}

	public Timestamp getOutTime() {
		return this.outTime;
	}

	public void setOutTime(Timestamp outTime) {
		this.outTime = outTime;
	}

}