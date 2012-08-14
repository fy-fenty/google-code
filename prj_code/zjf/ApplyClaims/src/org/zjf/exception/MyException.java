package org.zjf.exception;

/**
 * @project:ApplyClaims
 * @author:zjf
 * @date:2012-8-14 下午12:37:34   
 * @class:MyException
 * @extends:Exception
 * @description:自定义异常
 */
public class MyException extends Exception {

	private static final long serialVersionUID = -8062405113888955260L;

	public MyException(String msg){
		super(msg);
	}
}
