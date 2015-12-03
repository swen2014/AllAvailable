package com.cmu.qiuoffer.Servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cmu.qiuoffer.DAO.AADAO;
import com.cmu.qiuoffer.DAO.CommentDAO;
import com.cmu.qiuoffer.Entities.CommentBean;
import com.cmu.qiuoffer.Util.JsonHelper;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * Servlet implementation class Comment
 */
public class Comment extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";
	private ServletContext sc;
	private CommentDAO commentDao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Comment() {
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
		commentDao = new AADAO();
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
		String action = request.getParameter("action");
		if (action != null) {
			if (action.equals("new")) {
				makeComment(request, response);
			} else if (action.equals("check")) {
				String roomId = request.getParameter("roomid");
			} else if (action.equals("upload")) {
				uploadImage(request, response);
			} else if (action.equals("download")) {
				dispatchImage(request, response);
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	private void makeComment(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();

		String userId = request.getParameter("user");
		int roomId = Integer.parseInt(request.getParameter("rId"));
		String content = request.getParameter("content");
		String picName = request.getParameter("pic");

		String pic = sc.getRealPath("/ImageResources/" + picName);

		boolean success = commentDao.makeComment(userId, roomId, content, pic);
		if (success) {
			response.setStatus(200);
		} else {
			response.setStatus(500);
		}

		out.flush();
		out.close();
	}

	private void getComments(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();

		int roomId = Integer.parseInt(request.getParameter("rId"));

		List<CommentBean> comments = commentDao.getComments(roomId);

		response.setStatus(200);
		out.println(JsonHelper.createJsonString(comments));
		out.flush();
		out.close();
	}

	private void uploadImage(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		System.out.println("upload");
		String photo = request.getParameter("photo");
		String name = request.getParameter("name");
		try {
			byte[] decode = Base64.decode(photo);
			String dir = sc.getRealPath("/ImageResources/");
			System.out.println(dir);
			File file = new File(dir + name + ".jpg");
			System.out.println(file.getName());
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(file);
			out.write(decode);
			out.flush();
			out.close();
		} catch (Base64DecodingException e) {
			e.printStackTrace();
		}
	}

	private void dispatchImage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// ServletContext cntx = getServletContext();

		String path = request.getParameter("path");

		// retrieve mimeType dynamically
		String mime = sc.getMimeType(path);
		if (mime == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		response.setContentType(mime);

		File file = new File(path);
		response.setContentLength((int) file.length());

		FileInputStream in = new FileInputStream(file);
		OutputStream out = response.getOutputStream();

		// Copy the contents of the file to the output stream
		byte[] buf = new byte[1024];
		int count = 0;
		while ((count = in.read(buf)) >= 0) {
			out.write(buf, 0, count);
		}
		out.close();
		in.close();
	}

}
