package org.han.vo;

import org.han.constant.AppConstant;

/**
* @author HanZhou
* @className: Result 
* @date 2012-8-14 
* @extends Object
* @description: 增、删、改操作返回的结果提示消息
 */
public class Result {
    /**
     * 操作是否成功
     */
    private Boolean success = false;
    
    /**
     * 提示消息,默认为操作成功
     */
    private String msg =AppConstant.A000;

    /**
     * 异常信息
     */
    private String exception;

    public Result()
    {
    }

    public Result(Boolean success, String msg)
    {
        this.success = success;
        this.msg = msg;
    }

    public Result(Boolean success, String msg, String exception)
    {
        this.success = success;
        this.msg = msg;
        this.exception = exception;
    }

    public Boolean getSuccess()
    {
        return success;
    }

    public void setSuccess(Boolean success)
    {
        this.success = success;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public String getException()
    {
        return exception;
    }

    public void setException(String exception)
    {
        this.exception = exception;
    }
}
