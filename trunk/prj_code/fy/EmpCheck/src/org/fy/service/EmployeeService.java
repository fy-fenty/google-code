package org.fy.service;

import java.util.List;
import java.util.Map;

import org.fy.dao.MyUserDao;

@SuppressWarnings("unchecked")
public class EmployeeService implements IEmployeeService{
	public MyUserDao mud;
	
	public List<Map> findDispathList(){
		String sql = "";
		this.mud.countSqlResult(sql,(Map)null);
		return null;
	}
}
