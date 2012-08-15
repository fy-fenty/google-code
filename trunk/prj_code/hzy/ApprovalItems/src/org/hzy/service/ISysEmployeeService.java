package org.hzy.service;

import java.util.List;

import org.hzy.exception.MyException;

public interface ISysEmployeeService {
	public abstract List findAllDispatch(Long dlID) throws MyException;
}
