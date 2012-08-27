/**
 * 
 */
package org.han.dao;

import org.han.entity.DispatchList;
import org.han.support.IBaseDAO;

/**
 * @author tender
 * @date 2012-8-14
 * @ClassName: IDispatchListDao
 * @Description: TODO
 * @extends IBaseDAO
 * @version
 * 
 */
public interface IDispatchListDao extends IBaseDAO<DispatchList, Long> {
	public Long saveDispatch(DispatchList dis);
}
