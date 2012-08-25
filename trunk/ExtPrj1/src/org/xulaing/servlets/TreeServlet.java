package org.xulaing.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TreeServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public TreeServlet() {
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
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		StringBuilder sb = new StringBuilder();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String s = "[{"+
		    "'text': 'adapter',"+
		    "'cls': 'folder',"+
		    "children: [{"+
	            "text: 'A child Node',"+
	            "leaf: true"+
	        "}]"+
		"}, {"+
		    "'text': 'dd',"+
		    "'cls': 'folder',"+
		    "children: [{"+
            "text: 'A child Nodeaaaa',"+
            "leaf: true"+
        "}]"+
		"}, {"+
		    "'text': 'debug.js',"+
		    "'leaf': true,"+
		    "'cls': 'file'"+
		"}]";
		
		System.out.println("=============================");
		sb.append(s);
		out.write(sb.toString());
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}
}
