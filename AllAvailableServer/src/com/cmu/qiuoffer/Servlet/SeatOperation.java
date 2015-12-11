package com.cmu.qiuoffer.Servlet;

import java.io.File;
import java.io.FileInputStream;
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
import com.cmu.qiuoffer.DAO.BuildingDAO;
import com.cmu.qiuoffer.DAO.RoomDAO;
import com.cmu.qiuoffer.DAO.SeatDAO;
import com.cmu.qiuoffer.DAO.UserDAO;
import com.cmu.qiuoffer.Entities.BuildingBean;
import com.cmu.qiuoffer.Entities.RoomBean;
import com.cmu.qiuoffer.Entities.SeatBean;
import com.cmu.qiuoffer.Util.JsonHelper;

/**
 * Servlet implementation class SeatOperation
 */
public class SeatOperation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";
	private ServletContext sc;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SeatOperation() {
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
		super.init();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);

		String action = request.getParameter("action");
		if (action != null) {
			if (action.equals("buildings")) {
				getBuildings(request, response);
			} else if (action.equals("rooms")) {
				getRooms(request, response);
			} else if (action.equals("lock")) {
				changeLock(request, response);
			} else if (action.equals("seats")) {
				getSeats(request, response);
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
		doGet(request, response);
	}

	/**
	 * Get the building list
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void getBuildings(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		BuildingDAO buildingDao = new AADAO();

		List<BuildingBean> results = buildingDao.getBuildings();
		if (results.size() > 0) {
			response.setStatus(200);
			out.println(JsonHelper.createJsonString(results));
		} else {
			response.setStatus(404);
		}
		out.flush();
		out.close();
	}

	/**
	 * Get the rooms list
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void getRooms(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		RoomDAO roomDao = new AADAO();

		int buildingId = Integer.parseInt(request.getParameter("bId"));

		List<RoomBean> results = roomDao.getRooms(buildingId);
		response.setStatus(200);
		out.println(JsonHelper.createJsonString(results));
		out.flush();
		out.close();
	}

	/**
	 * Change the lock status
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void changeLock(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		RoomDAO roomDao = new AADAO();

		int roomId = Integer.parseInt(request.getParameter("rId"));
		boolean lock = Boolean.parseBoolean(request.getParameter("lock"));

		boolean success = roomDao.changeLock(roomId, lock);
		if (success) {
			response.setStatus(200);
		} else {
			response.setStatus(500);
		}
		out.flush();
		out.close();
	}

	/**
	 * Get the seats list
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void getSeats(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		SeatDAO seatDao = new AADAO();

		int roomId = Integer.parseInt(request.getParameter("rId"));

		List<SeatBean> results = seatDao.getSeats(roomId);
		response.setStatus(200);
		out.println(JsonHelper.createJsonString(results));
		out.flush();
		out.close();
	}

	/**
	 * Dispatch the image
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void dispatchImage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// ServletContext cntx = getServletContext();

		// Get the absolute path of the image
		String filename = sc.getRealPath("/ImageResources/seat.jpg");
		System.out.println(filename);

		// retrieve mimeType dynamically
		String mime = sc.getMimeType(filename);
		if (mime == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		response.setContentType(mime);

		File file = new File(filename);
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
