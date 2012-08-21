package org.han.vo;

public class ApprovalVo extends BaseVo {
	private String empSN;
	private Long disId;
	private Integer disResul;
	private String comment;

	public String getEmpSN() {
		return empSN;
	}

	public void setEmpSN(String empSN) {
		this.empSN = empSN;
	}

	public Long getDisId() {
		return disId;
	}

	public void setDisId(Long disId) {
		this.disId = disId;
	}

	public Integer getDisResul() {
		return disResul;
	}

	public void setDisResul(Integer disResul) {
		this.disResul = disResul;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public ApprovalVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ApprovalVo(int start, int limit) {
		super(start, limit);
		// TODO Auto-generated constructor stub
	}

	public ApprovalVo(String empSN, Long disId, String comment) {
		super();
		this.empSN = empSN;
		this.disId = disId;
		this.comment = comment;
	}

	public ApprovalVo(String empSN, Long disId, Integer disResul, String comment) {
		super();
		this.empSN = empSN;
		this.disId = disId;
		this.disResul = disResul;
		this.comment = comment;
	}

}
