package org.tender.vo;

/**
* 
*
* @author tender  
* @date 2012-8-14   
* @ClassName: BaseVO    
* @Description: TODO   
* @version    
*
*/
public class BaseVo implements java.io.Serializable {
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