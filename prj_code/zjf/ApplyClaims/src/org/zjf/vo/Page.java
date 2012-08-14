package org.zjf.vo;

import java.util.Collections;
import java.util.List;

/**
 * @project:ApplyClaims
 * @author:zjf
 * @date:2012-8-14 上午9:37:57   
 * @class:Page
 * @extends:Object
 * @description:分页参数以及查询结果的封装对象
 */
public class Page
{
    /**
     * 当前页为第几页，默认为第1页
     */
    protected int pageNo = 1;

    /**
     * 每页记录数，默认为1
     */
    protected int pageSize = 1;

    /**
     * 查询返回的结果集
     */
    @SuppressWarnings("rawtypes")
	protected List result = Collections.emptyList();

    /**
     * 总记录数
     */
    protected long totalCount = -1;

    /**
     * 获得当前页的页号,序号从1开始,默认为1.
     */
    public int getPageNo()
    {
        return pageNo;
    }

    /**
     * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
     */
    public void setPageNo(final int pageNo)
    {
        this.pageNo = pageNo;

        if (pageNo < 1)
        {
            this.pageNo = 1;
        }
    }

    /**
     * 获得每页的记录数量,默认为1.
     */
    public int getPageSize()
    {
        return pageSize;
    }

    /**
     * 设置每页的记录数量,低于1时自动调整为1.
     */
    public void setPageSize(final int pageSize)
    {
        this.pageSize = pageSize;

        if (pageSize < 1)
        {
            this.pageSize = 1;
        }
    }

    /**
     * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
     */
    public int getFirst()
    {
        return ((pageNo - 1) * pageSize) + 1;
    }

    /**
     * 取得页内的记录列表.
     */
    @SuppressWarnings("rawtypes")
	public List getResult()
    {
        return result;
    }

    /**
     * 设置页内的记录列表.
     */
    @SuppressWarnings("rawtypes")
	public void setResult(final List result)
    {
        this.result = result;
    }

    /**
     * 取得总记录数, 默认值为-1.
     */
    public long getTotalCount()
    {
        return totalCount;
    }

    /**
     * 设置总记录数.
     */
    public void setTotalCount(final long totalCount)
    {
        this.totalCount = totalCount;
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
        return (pageNo + 1 <= getTotalPages());
    }

    /**
     * 取得下页的页号, 序号从1开始. 当前页为尾页时仍返回尾页序号.
     */
    public int getNextPage()
    {
        if (isHasNext())
        {
            return pageNo + 1;
        }
        else
        {
            return pageNo;
        }
    }

    /**
     * 是否还有上一页.
     */
    public boolean isHasPre()
    {
        return (pageNo - 1 >= 1);
    }

    /**
     * 取得上页的页号, 序号从1开始. 当前页为首页时返回首页序号.
     */
    public int getPrePage()
    {
        if (isHasPre())
        {
            return pageNo - 1;
        }
        else
        {
            return pageNo;
        }
    }
}