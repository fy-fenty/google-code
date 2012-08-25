package org.xulaing.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyServlet1 extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public MyServlet1() {
		super();
	}

	/** 
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(request.getParameter("x"));
//		this.doPost(request, response);
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		System.out.println("GET"+new Date()+": "+request.getParameter("job"));
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try {
			Thread.sleep(1*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.print("{'msg':'操作成功!'}");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		System.out.println("POST "+new Date()+": "+request.getParameter("start"));
		StringBuilder sb = new StringBuilder(128);

		try {
			Thread.sleep(1*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("我们是害虫.......");
		System.out.println(request.getParameter("start")+" "+request.getParameter("dno"));
		sb.append("{");
		sb.append("'total_num': 10,"); // Reader's configured totalProperty
		sb.append("'rows': ["); // Reader's configured root
		sb.append("{'id': 1, 'firstname': 'Bill', 'occupation': 'Gardener'},");
		sb.append("{'id': 2, 'firstname': '文子980' , 'occupation': '北方2'},");
		sb.append("{'id': 3, 'firstname': '文子3123' , 'occupation': '北方3'},");
		sb.append("{'id': 4, 'firstname': '文子4331' , 'occupation': '北方4'},");
		sb.append("{'id': 5, 'firstname': '文子5' , 'occupation': '北方5'}");
		sb.append("]");
		sb.append("}");
		
		out.print(sb.toString().replaceAll("'", "\""));
//		out.print("{\"total_num\" : \"50\",\"rows\" : [{},{}]}");
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
