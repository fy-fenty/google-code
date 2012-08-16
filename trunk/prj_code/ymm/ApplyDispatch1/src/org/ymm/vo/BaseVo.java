package org.ymm.vo;

/**
 * @author yingmingming 
 * @date 2012-8-14 下午1:30:30
 * @ClassName: BaseVo 
 * @extends Object	 
 * @Description: 基础VO，用于跟页面数据交互.提供分页参数
 */
public class BaseVo implements java.io.Serializable{
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
		if(start>0)
			this.start = start;
	}
	
	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		if(limit>0)
			this.limit = limit;
	}
}
