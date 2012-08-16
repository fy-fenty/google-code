package org.fy.vo;

import java.io.Serializable;

public class BaseVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 总记录数的条目索引
	 */
	protected int start;

	/**
	 * 显示的总记录数量，默认为 20
	 */
	protected int limit = 20;

	public int getStart() {
		return start < 0 ? 0 : start;
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
