package org.xuliang.service;

import java.util.Map;

import org.xuliang.exception.MyException;
import org.xuliang.vo.Page;

public interface IEmployeeService {
	public Page findDispathList();
	public Map<String,Object> login(String uname,String upwd) throws MyException;
}
