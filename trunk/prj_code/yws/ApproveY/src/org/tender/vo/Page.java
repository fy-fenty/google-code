package org.tender.vo;

import java.util.Collections;
import java.util.List;

/**
 * @author HanZhou
 * @className: Page
 * @date 2012-8-13
 * @extends Object
 * @description: 分页查询工具类
 */
public class Page {

	/**
	 * 当前页，默认1
	 */
	protected int pageIndex = 1;
	/**
	 * 每页记录数
	 */
	protected int pageSize;
	/**
	 * 总记录数，默认0
	 */
	protected long totalCount = 0;
	/**
	 * 数据结果集
	 */
	protected List data = Collections.emptyList();

	/**
	 * 获得当前页的页号,序号从1开始,默认为1.
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * 设置当前页的页号,序号从1开始,低于1时设为1.
	 */
	public void setPageIndex(final int pageIndex) {
		this.pageIndex = pageIndex;
		if (pageIndex < 1) {
			this.pageIndex = 1;
		}
	}

	/**
	 * 获得每页记录数
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页的记录数量,低于1时设为1.
	 */
	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;
		if (pageSize < 1) {
			this.pageSize = 1;
		}
	}

	/**
	 * 取得总记录数, 默认值为-1.
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**
	 * 设置总记录数.
	 */
	public void setTotalCount(final long totalCount) {
		this.totalCount = totalCount;
	}
	
	/**
     * 获得页内的记录列表.
     */
	public List getData() {
		return data;
	}
	
	/**
     * 设置页内的记录列表.
     */
	public void setData(List data) {
		this.data = data;
	}
	
    /**
     * 根据pageSize与totalCount计算总页数, 默认值为-1.
     */
    public long getTotalPages()
    {
        if (totalCount < 0)
        {
            return -1;
        }
        long count = totalCount / pageSize;
        if (totalCount % pageSize > 0)
        {
            count++;
        }
        return count;
    }
    
    /**
     * 是否还有下一页.
     */
    public boolean isHasNext()
    {
        return (pageIndex + 1 <= getTotalPages());
    }
    
    /**
     * 取得下页的页号, 序号从1开始. 当前页为尾页时仍返回尾页序号.
     */
    public int getNextPage()
    {
        if (isHasNext())
        {
            return pageIndex + 1;
        }
        else
        {
            return pageIndex;
        }
    }
    
    /**
     * 是否还有上一页.
     */
    public boolean isHasPre()
    {
        return (pageIndex - 1 >= 1);
    }
    
    /**
     * 取得上页的页号, 序号从1开始. 当前页为首页时返回首页序号.
     */
    public int getPrePage()
    {
        if (isHasPre())
        {
            return pageIndex - 1;
        }
        else
        {
            return pageIndex;
        }
    }
}
