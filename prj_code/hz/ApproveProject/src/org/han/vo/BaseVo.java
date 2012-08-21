package org.han.vo;

import java.io.Serializable;

/**
 * @author HanZhou
 * @className: BaseVo
 * @date 2012-8-13
 * @extends Object
 * @description: 基础VO，分页数据交互
 */
public class BaseVo implements Serializable {

	private static final long serialVersionUID = 9041363112687015196L;

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
