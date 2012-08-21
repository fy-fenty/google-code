/**
 * 
 */
package org.han.vo;

/**
 * @author tender  
 * @date 2012-8-21   
 * @ClassName: LoginVo    
 * @Description: TODO   
 * @version    
 *  
 */
public class LoginVo {
	private String userName;
	private String pwd;
	private String checkCode;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getCheckCode() {
		return checkCode;
	}
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
}
