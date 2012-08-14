package org.ymm.support;

import java.io.Serializable;
import java.util.Map;
import org.hibernate.Query;
import org.ymm.vo.BaseVo;
import org.ymm.vo.Page;
/**
 * @author yingmingming 
 * @date 2012-8-14 下午2:16:03
 * @ClassName: IBaseDao 
 * @extends ISimpleDao	 
 * @Description: 基础DAO接口,扩展功能包括分页查询,按属性过滤条件列表查询.
 * @param <T>
 * @param <PK>
 */
public interface IBaseDao<T,PK extends Serializable> extends ISimpleDao<T,PK> {
	/**
	 * @Title: setPageParameter 
	 * @param @param query query对象
	 * @param @param fisrt 开始
	 * @param @param limit 显示数量
	 * @param @return
	 * @return Query   	   Query对象
	 * @Description:  设置分页参数到Query对象
	 * @throws
	 */
	public abstract Query setPageParameter(final Query query,final int fisrt,final int limit);
	
	/**
	 * @Title: countSqlResult 
	 * @param @param sql    hql语句 
	 * @param @param values 参数
	 * @param @return  
	 * @return long         条数   
	 * @Description: 执行count查询获得本次Sql查询所能获得的对象总数.注： 本函数只能自动处理简单的sql语句,复杂的sql查询请另行编写count语句查询.
	 * @throws
	 */
	public abstract long countSqlResult(final String sql,final Object...values);
	
	/**
	 * @Title: countSqlResult 
	 * @param @param sql    hql语句 
	 * @param @param values 参数
	 * @param @return  
	 * @return long         条数   
	 * @Description: 执行count查询获得本次Sql查询所能获得的对象总数.注： 本函数只能自动处理简单的sql语句,复杂的sql查询请另行编写count语句查询.
	 * @throws
	 */
	public abstract long countSqlResult(final String sql,
			final Map<String, Object> values);
	
	/**
	 * @Title: findPageBySQL 
	 * @param @param vo    	分页参数
	 * @param @param sql   	sql语句.
	 * @param @param values 命名参数，按名称绑定
	 * @param @return
	 * @return Page     	分页查询结果.result里面是map对象
	 * @Description: 按SQL分页查询
	 * @throws
	 */
	public abstract Page findPageBySQL(final BaseVo vo,final String sql,
			final Map<String,Object> values);
	/**
	 * @Title: findPageBySQL 
	 * @param @param vo 	分页参数
	 * @param @param sql	sql语句.
	 * @param @param values 数量可变的查询参数,按顺序绑定.
	 * @param @return
	 * @return Page     	分页查询结果.result里面是Map对象.	
	 * @Description: 按SQL分页查询 
	 * @throws
	 */
	public abstract Page findPageBySQL(final BaseVo vo, final String sql,
			final Object...values);
	
	/**
	 * @Title: findUniqueBySQL 
	 * @param @param sql		sql语句
	 * @param @param values		命名参数,按名称绑定.
	 * @param @return
	 * @return Map<String,Object>    Map对象 
	 * @Description: 根据sql查询唯一结果，返回Map对象
	 * @throws
	 */
	public Map<String,Object> findUniqueBySQL(String sql,Map<String,Object> values);
	
	/**
	 * @Title: findUniqueBySQL 
	 * @param @param sql   		sql语句.
	 * @param @param values		数量可变的查询参数,按顺序绑定.
	 * @param @return
	 * @return Map<String,Object>  map对象   
	 * @Description: 根据sql查询唯一结果，返回Map对象
	 * @throws
	 */
	public abstract Map<String,Object> findUniqueBySQL(final String sql,final Object...values);
}
