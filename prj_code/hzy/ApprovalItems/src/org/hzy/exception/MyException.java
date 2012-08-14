package org.hzy.exception;

/**
 * @author hzy
 * @date 2012-8-14
 *	@extends Exception 
 * @class MyException
 * @description  自定义异常
 */
public class MyException extends Exception{
	private static final long serialVersionUID = 1L;

	public MyException(String msg){
		super(msg);
	}
}
