package org.hzy.service;

import java.util.List;
import java.util.Map;

import org.hzy.entity.DispatchDetail;
import org.hzy.entity.DispatchList;
import org.hzy.exception.MyException;
import org.hzy.vo.HandleDispatchVo;
import org.hzy.vo.LoginVo;
import org.hzy.vo.Page;
import org.hzy.vo.Result;

/**
 * @author fy
 * @date 2012-8-16
 * @class ISysEmployeeService
 * @extends Object
 * @description 员工的业务接口
 */
public interface IEmployeeService {
	/**
	 * 雇员查找所有当前状态的全部报销单，字段包括 报销单号 创建人 创建时间 总金额 状态
	 * 
	 * @param eSn
	 *            雇员编号
	 * @return {@link List}<<{@link Map}>>
	 *         <ul>
	 *         <li>DLID：报销单编号</li>
	 *         <li>CREATETIME：报销单创建时间</li>
	 *         <li>ESN：创建人编号</li>
	 *         <li>CURRENTSTATUS：报销单当前状态</li>
	 *         <li>TOTALMONEY：报销单明细总金额</li>
	 *         </ul>
	 */
	public abstract Page findAllDispatchListByESn(HandleDispatchVo hdVo) throws MyException;

	/**
	 * 用户保存报销单
	 * 
	 * @param uId
	 *            用户 ID
	 * @param dl
	 *            保存的报销单
	 * @return {@link Result}
	 * @throws MyException
	 */
	public abstract Result saveDispatchList(HandleDispatchVo hdVo);

	/**
	 * 用户修改报销单
	 * 
	 * @param dl
	 *            修改的报销单
	 * @return Result
	 */
	public abstract Result updateDispatchList(HandleDispatchVo hdVo);

	/**
	 * 用户修改报销单明细
	 * 
	 * @param eSn
	 *            用户编号
	 * @param dd
	 *            修改的报销单明细
	 * @return Result
	 * @throws MyException
	 */
	public abstract Result updateDispatchDetail(HandleDispatchVo hdVo);

	/**
	 * 删除报销单
	 * 
	 * @param eSn
	 *            用户编号
	 * @param dl
	 *            删除的报销单
	 * @return Result
	 */
	public abstract Result deleteDispatchList(HandleDispatchVo hdVo);

	/**
	 * 删除报销单明细
	 * 
	 * @param eSn
	 *            用户编号
	 * @param dd
	 *            删除的报销单明细
	 * @return Result
	 */
	public abstract Result deleteDispatchDetail(HandleDispatchVo hdVo);

	/**
	 * 添加报销单明细
	 * 
	 * @param eSn
	 *            用户编号
	 * @param dd
	 *            添加的报销单明细
	 * @return Result
	 */
	public abstract Result addDispatchDetail(HandleDispatchVo hdVo, DispatchDetail[] dds);

	/**
	 * 提交报销单
	 * 
	 * @param eSn
	 *            用户编号
	 * @param dl
	 *            提交的报销单
	 * @return Result
	 */
	public abstract Result submitDispatchList(HandleDispatchVo hdVo);

	/**
	 * 用户登录
	 * 
	 * @param lu
	 *            用户
	 * @return Result
	 */
	public abstract String login(LoginVo lgVo) throws MyException;
}