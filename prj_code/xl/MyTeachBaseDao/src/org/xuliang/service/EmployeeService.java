package org.xuliang.service;

import java.util.List;
import java.util.Map;

import org.xuliang.dao.MyUserDao;

@SuppressWarnings("unchecked")
public class EmployeeService implements IEmployeeService{
	public MyUserDao mud;
	
	public List<Map> findDispathList(){
		String sql = "";
		this.mud.countSqlResult(sql,(Map)null);
		return null;
	}
}
