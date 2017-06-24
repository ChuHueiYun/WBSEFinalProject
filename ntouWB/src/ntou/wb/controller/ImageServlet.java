package ntou.wb.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ntou.wb.model.DBConnection;
import ntou.wb.model.ImageServer;

public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String state = request.getParameter("state");
        DBConnection db = (DBConnection) getServletContext().getAttribute("db");
        Connection conn = db.getConnection();
		String result;	
		//HttpSession session = request.getSession();
		//String memberID = (String) session.getAttribute("memberID");
		//String identity = (String) session.getAttribute("identity");
		if(state.equals("getTotalPictureID")){
			result = ImageServer.getTotalPictureID(conn);
			response.getWriter().write(result);
		}else if(state.equals("UploadPicture")){
			String picture = request.getParameter("base64");
			String pictureName = request.getParameter("pictureName");
			String dateTime = request.getParameter("dateTime");
			int rs = ImageServer.UploadPicture(conn,picture,pictureName,dateTime); 
			response.getWriter().println(rs);
		}else if(state.equals("getPicture")){
			String pictureID = request.getParameter("id");
			result = ImageServer.getPicture(conn,pictureID);
			response.getWriter().write(result);
		}else if(state.equals("editPicture")){
			String pictureID = request.getParameter("id");
			String picture = request.getParameter("base64");
			String pictureName = request.getParameter("pictureName");
			String dateTime = request.getParameter("dateTime");
			int rs = ImageServer.editPicture(conn,pictureID,picture,pictureName,dateTime);
			response.getWriter().println(rs);
		}else if(state.equals("deletePicture")){
			String pictureID = request.getParameter("id");
			int rs = ImageServer.deletePicture(conn,pictureID);
			response.getWriter().println(rs);			
		}
	}
}