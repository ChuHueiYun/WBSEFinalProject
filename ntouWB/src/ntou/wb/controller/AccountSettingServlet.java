package ntou.wb.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ntou.wb.model.AccountSettingServer;
import ntou.wb.model.DBConnection;
import ntou.wb.model.MemberManagementServer;


public class AccountSettingServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();	
		String memberID = (String) session.getAttribute("memberID");
		
		String option = request.getParameter("option");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String height = request.getParameter("height");
		String position = request.getParameter("position");
		
		//資料庫連線
        DBConnection db = (DBConnection) getServletContext().getAttribute("db");
        Connection conn = db.getConnection();
		
		switch(option){
	    	case "getAccountInfo":	//
	        	response.setContentType("application/json;charset=UTF-8");        	
	        	response.getWriter().write(AccountSettingServer.getAccountInfo(conn, memberID));
	        	break;
	        
	    	case "editAccount":
	    		response.setContentType("text/html;charset=UTF-8");
	    		response.getWriter().write(AccountSettingServer.editAccount(conn, password, name, height, position, memberID));
	        	break;
	        default:
	        	break;
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	

}
