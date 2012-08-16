package org.fy.entity;

import java.lang.Long;
import java.util.Date;

/**
 * SysEmployee entity. @author MyEclipse Persistence Tools
 */

public class SysEmployee implements java.io.Serializable {

	// Fields

	private Long EId;
	private String ESn;
	private String EName;
	private String EIdentity;
	private Boolean sex;
	private Date birthday;
	private String address;
	private String tel;
	private Long departmentId;
	private Long PId;
	private Boolean status;
	private Date inTime;
	private Date outTime;

	// Constructors

	/** default constructor */
	public SysEmployee() {
	}

	/** minimal constructor */
	public SysEmployee(Long EId) {
		this.EId = EId;
	}

	/** full constructor */
	public SysEmployee(Long EId, String ESn, String EName,
			String EIdentity, Boolean sex, Date birthday, String address,
			String tel, Long departmentId, Long PId,
			Boolean status, Date inTime, Date outTime) {
		this.EId = EId;
		this.ESn = ESn;
		this.EName = EName;
		this.EIdentity = EIdentity;
		this.sex = sex;
		this.birthday = birthday;
		this.address = address;
		this.tel = tel;
		this.departmentId = departmentId;
		this.PId = PId;
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

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
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

	public Long getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long getPId() {
		return this.PId;
	}

	public void setPId(Long PId) {
		this.PId = PId;
	}

	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Date getInTime() {
		return this.inTime;
	}

	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}

	public Date getOutTime() {
		return this.outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

}