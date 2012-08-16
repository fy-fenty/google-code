package org.fy.vo;

import java.util.Collections;
import java.util.List;

/**
 * @author fy
 * @date 2012-8-14
 * @class Page
 * @extends
 * @description 分页参数及所过封装
 */
public class Page {

	/**
	 * 当前所在的页码，默认为第一页
	 */
	protected int pageNo = 1;

	/**
	 * 每页显示的总记录数，默认为一条
	 */
	protected int pageSize = 1;

	/**
	 * 查询返回的结果集合
	 */
	protected List result = Collections.emptyList();

	/**
	 * 查询得到的总记录数
	 */
	protected long totalCount = -1;

	/**
	 * @return 当前页码，从一开始，默认为第一页
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * 设置当前页码，若页码小于1，则自动调整为第一页
	 * @param pageNo
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
		if (pageNo < 1) {
			this.pageNo = 1;
		}
	}

	/**
	 * @return 每页显示的总记录数，默认为一条
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页显示的总记录数，若数量小于1，则自动调整为1
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		if (pageSize < 1) {
			this.pageSize = 1;
		}
	}

	/**
	 * @return 查询返回的结果集合
	 */
	public List getResult() {
		return result;
	}

	/**
	 * 设置当前页的内容
	 * @param result
	 */
	public void setResult(List result) {
		this.result = result;
	}

	/**
	 * @return 当前结果总记录数，默认为-1
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**
	 * 设置总记录数
	 * @param totalCount
	 */
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 得到当前页的第一条在全部记录中所在记录的索引，索引从1开始
	 * @return
	 */
	public int getFirst() {
		return (getPageNo() - 1) * pageSize + 1;
	}

	/**
	 * @return 总页数，默认为-1
	 */
	public long getTotalPages() {
		if (totalCount < 0) {
			return -1;
		}
		Long count = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			count++;
		}
		return count;
	}

	/**
	 * @return 是否有上一页
	 */
	public boolean hasPre() {
		return pageNo - 1 >= 1;
	}

	/**
	 * @return 是否有下一页
	 */
	public boolean hasNext() {
		return pageNo + 1 <= getTotalPages();
	}

	/**
	 * @return 当前所在的下一页，索引从1开始，当前页为末尾时返回末尾
	 */
	public int getNextPage() {
		if (hasNext()) {
			return pageNo + 1;
		}
		return pageNo;
	}

	/**
	 * @return 当前所在的上一页，索引从1开始，当前页为首页时返回首页
	 */
	public int getPrePage() {
		if (hasPre()) {
			return pageNo - 1;
		}
		return pageNo;
	}

}
