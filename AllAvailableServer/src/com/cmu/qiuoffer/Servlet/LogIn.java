package com.cmu.qiuoffer.Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cmu.qiuoffer.DAO.AADAO;
import com.cmu.qiuoffer.DAO.UserDAO;
import com.cmu.qiuoffer.DB.SQLHelper;
import com.cmu.qiuoffer.Entities.UserBean;

/**
 * Servlet implementation class LogIn
 */
public class LogIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";
	private ServletContext sc;
	private UserDAO userDao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LogIn() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	@Override
	public void init() throws ServletException {
		// Put your code here
		sc = this.getServletContext();
		userDao = new AADAO();
		super.init();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType(CONTENT_TYPE);

		if (request.getParameter("action").equals("login")) {

			login(request, response);
		}
	}

	/**
	 * Login the client account
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void login(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		PrintWriter out = response.getWriter();

		String email = request.getParameter("uid");
		String pwdmd5 = request.getParameter("pwd");

		String userEmail = userDao.loginCheck(email, pwdmd5);

		if (userEmail == null) {
			UserBean user = new UserBean();
			user.setEmail(email);
			user.setPassword(pwdmd5);
			userDao.createUser(user);
			response.setStatus(200);
			out.println(email);
		} else if (userEmail.equals(SQLHelper.AUTHENTICATION_FAILED)) {
			response.setStatus(404);
		} else {
			response.setStatus(200);
			out.println(userEmail);
		}
		out.flush();
		out.close();
	}

}
