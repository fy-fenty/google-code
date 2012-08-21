package org.ymm.actions;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.zjf.util.SessionListener;

import com.sun.net.httpserver.HttpServer;


public class LoginAction {
	
	

	public void login(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String uname= request.getParameter("uname");
		String passwprd=request.getParameter("passwprd");
		String vcode=request.getParameter("vcode");
		System.out.println(request.getSession());
		String imgValue=(String) request.getSession().getAttribute("rand");
		String sessionId = request.getSession().getId();
		System.out.println(uname+"-"+passwprd+"-"+vcode+"="+imgValue+" "+sessionId);
		
		
		
		SessionListener.removeSession(uname);
		SessionListener.addSession(uname, request.getSession());
		
		
		
	}
	
	
}
