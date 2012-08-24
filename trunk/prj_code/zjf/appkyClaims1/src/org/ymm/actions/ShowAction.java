package org.ymm.actions;

import org.ymm.services.IShowMenuService;
import org.zjf.exception.MyException;

public class ShowAction {
	
	private IShowMenuService iShowMenuService;
	
	public IShowMenuService getiShowMenuService() {
		return iShowMenuService;
	}

	public void setiShowMenuService(IShowMenuService iShowMenuService) {
		this.iShowMenuService = iShowMenuService;
	}

	public void showMenu() throws MyException{
		
		Long p_id=(long)0;
		iShowMenuService.findEmpShowMenu(p_id);
	}
}
