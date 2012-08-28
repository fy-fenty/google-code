package org.hzy.vo;

import org.apache.struts2.ServletActionContext;
import org.hzy.constant.AppConstant;
import org.hzy.entity.DispatchDetail;

public class HandleDispatchVo extends BaseVo {

	private static final long serialVersionUID = 1L;
	/**
	 * 操作报销单的员工编号
	 */
	private String esn;
	/**
	 * 操作报销单编号
	 */
	private Long dlId;
	/**
	 * 操作备注
	 */
	private String comment;
	/**
	 * 审批结果
	 */
	private Long approvalStatus;
	/**
	 * 操作报销单明细
	 */
	private DispatchDetail dd;
	/**
	 * 保存报销单的时候是否直接提交
	 */
	private Boolean isCommit;

	public HandleDispatchVo() {
		super();
	}

	/**
	 * 获取操作人，若当前为空时，返回当前会话中的用户名
	 * 
	 * @return
	 */
	public String getEsn() {
		return esn == null ? (String) ServletActionContext.getRequest().getSession().getAttribute(AppConstant.CURRENT_UNAME) : esn;
	}

	public void setEsn(String esn) {
		this.esn = esn;
	}

	public Long getDlId() {
		return dlId;
	}

	public void setDlId(Long dlId) {
		this.dlId = dlId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(Long approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public DispatchDetail getDd() {
		return dd;
	}

	public void setDd(DispatchDetail dd) {
		this.dd = dd;
	}

	public Boolean getIsCommit() {
		return isCommit;
	}

	public void setIsCommit(Boolean isCommit) {
		this.isCommit = isCommit;
	}

}
