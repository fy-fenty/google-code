package org.tender.exception;

import java.util.Date;

import org.tender.entity.DispatchResult;

/**
 * 
 * @author tender
 * @date 2012-8-14
 * @ClassName: MyException
 * @Description: 自定义异常
 * @version
 * 
 */
public class MyException extends Exception {

	private static final long serialVersionUID = 1L;

	public MyException(String msg) {
		// TODO Auto-generated constructor stub
		super(msg);
	}
}
