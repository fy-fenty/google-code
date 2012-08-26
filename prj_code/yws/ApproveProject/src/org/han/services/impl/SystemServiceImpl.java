package org.han.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.han.dao.IDetailItemDao;
import org.han.dao.IDispatchDetailDao;
import org.han.dao.IDispatchListDao;
import org.han.dao.IDispatchResultDao;
import org.han.dao.ILoginUserDao;
import org.han.dao.ISysMenuDao;
import org.han.dao.ISysPositionsDao;
import org.han.dao.ISysRoleDao;
import org.han.entity.DispatchList;
import org.han.entity.DispatchResult;
import org.han.entity.LoginUser;
import org.han.entity.SysEmployee;
import org.han.entity.SysMenu;
import org.han.entity.SysPositions;
import org.han.entity.SysRole;
import org.han.services.ISystemServices;
import org.han.util.MD5;
import org.han.vo.BaseVo;
import org.han.vo.Page;
import org.han.vo.TreeBean;

public class SystemServiceImpl implements ISystemServices {
	private IDispatchListDao disListDao;
	private IDispatchResultDao disResultDao;
	private IDispatchDetailDao disDetail;
	private ILoginUserDao logUserDao;
	private ISysPositionsDao positionsDao;
	private ISysRoleDao roleDao;
	private ISysMenuDao menuDao;
	private IDetailItemDao detailItemDao;

	public IDetailItemDao getDetailItemDao() {
		return detailItemDao;
	}

	public void setDetailItemDao(IDetailItemDao detailItemDao) {
		this.detailItemDao = detailItemDao;
	}

	public ISysMenuDao getMenuDao() {
		return menuDao;
	}

	public void setMenuDao(ISysMenuDao menuDao) {
		this.menuDao = menuDao;
	}

	public ISysRoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(ISysRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public ISysPositionsDao getPositionsDao() {
		return positionsDao;
	}

	public void setPositionsDao(ISysPositionsDao positionsDao) {
		this.positionsDao = positionsDao;
	}

	public ILoginUserDao getLogUserDao() {
		return logUserDao;
	}

	public void setLogUserDao(ILoginUserDao logUserDao) {
		this.logUserDao = logUserDao;
	}

	public IDispatchDetailDao getDisDetail() {
		return disDetail;
	}

	public void setDisDetail(IDispatchDetailDao disDetail) {
		this.disDetail = disDetail;
	}

	public IDispatchResultDao getDisResultDao() {
		return disResultDao;
	}

	public void setDisResultDao(IDispatchResultDao disResultDao) {
		this.disResultDao = disResultDao;
	}

	public IDispatchListDao getDisListDao() {
		return disListDao;
	}

	public void setDisListDao(IDispatchListDao disListDao) {
		this.disListDao = disListDao;
	}

	@Override
	public DispatchList getDisListById(Long disId) {
		return disListDao.findUnique(
				"from DispatchList where dl_id=? and flag=1", disId);
	}

	@Override
	public boolean checkEmpPermi(Long disId, String empSN) {
		// TODO Auto-generated method stub
		DispatchResult disResult = this.getDisResultByDisId(disId);
		if (null == disResult) {
			SysPositions pos = getSysPositionsBySN(empSN);
			if (pos.getPId() == 3) {// 是否是雇员
				return true;
			}
		} else if (empSN.equals(disResult.getCheckNext())) {
			return true;
		}
		return false;
	}

	@Override
	public DispatchResult getDisResultByDisId(Long disId) {
		// TODO Auto-generated method stubs
		String sql = "select * from hzy.dispatch_result dr where check_time=(select max(check_time) from hzy.dispatch_result where sheet_id=? and dr.sheet_id=sheet_id)";
		DispatchResult dr = (DispatchResult) disResultDao.createSQLQuery(sql,
				disId).addEntity(DispatchResult.class).uniqueResult();
		return dr;
	}

	@Override
	public List getDispatchDetails(Long disId) {
		// TODO Auto-generated method stub
		// String hql = "from DispatchDetail where sheet_id=? and flag=1";
		// return disDetail.find(hql, disId);

		String hql = "from DispatchDetail where sheet_id=? and flag=1";
		Page page = disDetail
				.findPageBySQL(
						new BaseVo(0, 1000),
						"select dd.*, (select item_name from hzy.DETAIL_ITEM where dd.item_id=de_id) item_name from hzy.DISPATCH_DETAIL dd where sheet_id=? and flag=1",
						disId);
		return page.getData();
	}

	@Override
	public List getDispatchResults(Long disId) {
		// TODO Auto-generated method stub
		String hql = "from DispatchResult where sheet_id=?";
		return disResultDao.find(hql, disId);
	}

	@Override
	public LoginUser getLoginUserBySN(String empSN) {
		// TODO Auto-generated method stub
		return logUserDao.findUnique("from LoginUser where e_sn=?", empSN);
	}

	@Override
	public String getMD5Encryption(String pwd) {
		// TODO Auto-generated method stub
		return MD5.getMD5(pwd);
	}

	@Override
	public SysEmployee getSysEmployeeBySN(String empSN) {
		// TODO Auto-generated method stub
		return logUserDao.findUnique("from SysEmployee where e_sn=?", empSN);
	}

	@Override
	public SysPositions getSysPositionsBySN(String empSN) {
		// TODO Auto-generated method stub
		SysEmployee emp = this.getSysEmployeeBySN(empSN);
		if (null == emp) {
			return null;
		}
		SysPositions obj = positionsDao.findUnique(
				"from SysPositions where p_id=?", emp.getPId());
		return obj;
	}

	public TreeBean getTreeBean(SysPositions pos) {
		TreeBean root = new TreeBean();
		SysRole role = roleDao.findUnique("from SysRole where p_id=?", pos
				.getPId());
		List l = menuDao.find("from SysMenu where m_id in(" + role.getMenu()
				+ ")");
		List<TreeBean> treeList = new ArrayList();
		root.setText(pos.getPNameCn());
		root.setId("pos");
		if (null != l && l.size() > 0) {
			for (int i = 0; i < l.size(); i++) {
				TreeBean tree = new TreeBean();
				SysMenu menu = (SysMenu) l.get(i);
				tree.setFunName(menu.getUrl());
				tree.setText(menu.getMenuName());
				treeList.add(tree);
			}
		}
		if (null != treeList && treeList.size() > 0) {
			root.setChildren(treeList);
		}
		return root;
	}

	public List getDetailItems() {
		return detailItemDao.find("from DetailItem");
	}
}
