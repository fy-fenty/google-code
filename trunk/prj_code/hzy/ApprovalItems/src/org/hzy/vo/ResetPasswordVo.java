package org.hzy.vo;


public class ResetPasswordVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	private String esn;
	private String dsn;

	public ResetPasswordVo() {
		super();
	}

	/**
	 * @param esn
	 *            员工编号
	 * @param dsn
	 *            部门经理编号
	 */
	public ResetPasswordVo(String esn, String dsn) {
		super();
		this.esn = esn;
		this.dsn = dsn;
	}

	public String getEsn() {
		return esn;
	}

	public void setEsn(String esn) {
		this.esn = esn;
	}

	public String getDsn() {
		return dsn;
	}

	public void setDsn(String dsn) {
		this.dsn = dsn;
	}

}
