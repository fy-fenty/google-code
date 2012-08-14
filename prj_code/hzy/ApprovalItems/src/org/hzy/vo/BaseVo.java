package org.hzy.vo;

import java.io.Serializable;

/**
 * @author hzy
 * @date 2012-8-14
 *	@extends Object
 * @class BaseVo
 * @description 基础VO,用于跟页面数据交互,提供分页参数
 */
public class BaseVo implements Serializable{
	
	/**
	 * 当前页的第一条记录编号
	 */
	protected int start=0;
	
	/**
	 * 每页记录数，默认为20
	 */
	protected int limit=20;
	
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
