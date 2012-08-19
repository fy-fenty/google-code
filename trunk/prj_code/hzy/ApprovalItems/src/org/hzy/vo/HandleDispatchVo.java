package org.hzy.vo;

public class HandleDispatchVo extends BaseVo {

	private static final long serialVersionUID = 1L;
	private String esn;
	private Long dlId;
	private String checkComment;
	private Long status;
	private Double dispatchMoney;

	public HandleDispatchVo() {
		super();
	}

	/**
	 * @param dsn
	 *            雇员编号
	 * @param dlId
	 *            订单 ID
	 */
	public HandleDispatchVo(String esn, Long dlId) {
		super();
		this.esn = esn;
		this.dlId = dlId;
	}

	/**
	 * @param esn
	 *            雇员编号
	 * @param dlId
	 *            订单 ID
	 * @param checkComment
	 *            审批注释
	 */
	public HandleDispatchVo(String esn, Long dlId, String checkComment) {
		super();
		this.esn = esn;
		this.dlId = dlId;
		this.checkComment = checkComment;
	}

	/**
	 * @param esn
	 *            雇员编号
	 * @param dlId
	 *            订单 ID
	 * @param checkComment
	 *            审批注释
	 * @param status
	 *            审批状态
	 */
	public HandleDispatchVo(String esn, Long dlId, String checkComment, Long status) {
		super();
		this.esn = esn;
		this.dlId = dlId;
		this.checkComment = checkComment;
		this.status = status;
	}

	/**
	 * @param esn
	 *            雇员编号
	 * @param dlId
	 *            订单 ID
	 * @param checkComment
	 *            审批注释
	 * @param status
	 *            审批状态
	 * @param dispatchMoney
	 *            报销单明细总金额
	 */
	public HandleDispatchVo(String esn, Long dlId, String checkComment, Long status, Double dispatchMoney) {
		super();
		this.esn = esn;
		this.dlId = dlId;
		this.checkComment = checkComment;
		this.status = status;
		this.dispatchMoney = dispatchMoney;
	}

	public String getEsn() {
		return esn;
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

	public String getCheckComment() {
		return checkComment;
	}

	public void setCheckComment(String checkComment) {
		this.checkComment = checkComment;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Double getDispatchMoney() {
		return dispatchMoney;
	}

	public void setDispatchMoney(Double dispatchMoney) {
		this.dispatchMoney = dispatchMoney;
	}

}
