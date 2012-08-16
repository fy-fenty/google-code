package org.tender.vo;

import java.util.Collections;
import java.util.List;

/**
 * 鍒嗛〉鍙傛暟鍙婃煡璇㈢粨鏋滃皝瑁�
 * 
 * @author ZXY
 */
@SuppressWarnings("unchecked")
public class Page
{
    /**
     * 褰撳墠椤典负绗嚑椤碉紝榛樿涓虹1椤�
     */
    protected int pageNo = 1;

    /**
     * 姣忛〉璁板綍鏁帮紝榛樿涓�
     */
    protected int pageSize = 1;

    /**
     * 鏌ヨ杩斿洖鐨勭粨鏋滈泦
     */
    protected List result = Collections.emptyList();

    /**
     * 鎬昏褰曟暟
     */
    protected long totalCount = -1;

    /**
     * 鑾峰緱褰撳墠椤电殑椤靛彿,搴忓彿浠�寮�,榛樿涓�.
     */
    public int getPageNo()
    {
        return pageNo;
    }

    /**
     * 璁剧疆褰撳墠椤电殑椤靛彿,搴忓彿浠�寮�,浣庝簬1鏃惰嚜鍔ㄨ皟鏁翠负1.
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
     * 鑾峰緱姣忛〉鐨勮褰曟暟閲�榛樿涓�.
     */
    public int getPageSize()
    {
        return pageSize;
    }

    /**
     * 璁剧疆姣忛〉鐨勮褰曟暟閲�浣庝簬1鏃惰嚜鍔ㄨ皟鏁翠负1.
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
     * 鏍规嵁pageNo鍜宲ageSize璁＄畻褰撳墠椤电涓�潯璁板綍鍦ㄦ�缁撴灉闆嗕腑鐨勪綅缃�搴忓彿浠�寮�.
     */
    public int getFirst()
    {
        return ((pageNo - 1) * pageSize) + 1;
    }

    /**
     * 鍙栧緱椤靛唴鐨勮褰曞垪琛�
     */
    public List getResult()
    {
        return result;
    }

    /**
     * 璁剧疆椤靛唴鐨勮褰曞垪琛�
     */
    public void setResult(final List result)
    {
        this.result = result;
    }

    /**
     * 鍙栧緱鎬昏褰曟暟, 榛樿鍊间负-1.
     */
    public long getTotalCount()
    {
        return totalCount;
    }

    /**
     * 璁剧疆鎬昏褰曟暟.
     */
    public void setTotalCount(final long totalCount)
    {
        this.totalCount = totalCount;
    }

    /**
     * 鏍规嵁pageSize涓巘otalCount璁＄畻鎬婚〉鏁� 榛樿鍊间负-1.
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
     * 鏄惁杩樻湁涓嬩竴椤�
     */
    public boolean isHasNext()
    {
        return (pageNo + 1 <= getTotalPages());
    }

    /**
     * 鍙栧緱涓嬮〉鐨勯〉鍙� 搴忓彿浠�寮�. 褰撳墠椤典负灏鹃〉鏃朵粛杩斿洖灏鹃〉搴忓彿.
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
     * 鏄惁杩樻湁涓婁竴椤�
     */
    public boolean isHasPre()
    {
        return (pageNo - 1 >= 1);
    }

    /**
     * 鍙栧緱涓婇〉鐨勯〉鍙� 搴忓彿浠�寮�. 褰撳墠椤典负棣栭〉鏃惰繑鍥為椤靛簭鍙�
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
