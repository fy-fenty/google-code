package org.xuliang.service;

import java.util.Map;

import org.xuliang.dao.MyUserDao;
import org.xuliang.exception.MyException;
import org.xuliang.vo.BaseVO;
import org.xuliang.vo.Page;

@SuppressWarnings("unchecked")
public class EmployeeService implements IEmployeeService{
	public MyUserDao mud;
	
	public Map<String,Object> login(String uname,String upwd) throws MyException{
		if("xy".equals(uname)){
			throw new MyException("A001");
		}
		return null;
	}
	
	public Page findDispathList(){
		String sql = "";
		this.mud.countSqlResult(sql,(Map)null);

		return null;
	}
}
