package org.zjf.vo;

/**
 * 
 * @project:ApplyClaims
 * @author:zjf
 * @date:2012-8-14 上午9:42:31   
 * @class:BaseVO
 * @extends:Object
 * @description:基础VO，用于跟页面数据交互.提供分页参数
 */
public class BaseVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 当前页的第一条记录编号
	 */
	protected int start = 0;

	/**
	 * 每页记录数，默认为20
	 */
	protected int limit = 20;

	public int getStart() {
		return start <= 0 ? 0 : start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
}