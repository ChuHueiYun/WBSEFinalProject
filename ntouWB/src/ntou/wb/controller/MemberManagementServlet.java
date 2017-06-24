package ntou.wb.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ntou.wb.model.DBConnection;
import ntou.wb.model.MemberManagementServer;


public class MemberManagementServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String option = request.getParameter("option");
		String name = request.getParameter("name");
		String number = request.getParameter("number");
		String identity = request.getParameter("identity");
		String account = request.getParameter("account");
		//String memberID = request.getParameter("memberID");
		String newIdentity = request.getParameter("newIdentity");
		String message = "";
		
		//System.out.println("option:"+option);
		//System.out.println("number:"+number);
		//System.out.println("identity:"+identity);
		//System.out.println("account:"+account);
		//System.out.println(String.format("%04d", 1));
		
		//資料庫連線
        DBConnection db = (DBConnection) getServletContext().getAttribute("db");
        Connection conn = db.getConnection();
        
        switch(option){
        	case "getMemberList"://顯示成員列表
	        	response.setContentType("application/json;charset=UTF-8");
	        	String memberList = MemberManagementServer.getMemberList(conn);
	        	response.getWriter().write(memberList);
	        	break;
	        case "addMember": //加入成員        	
	        	MemberManagementServer.addMember(conn, name, Integer.parseInt(number),identity);
	        	break;
	        case "deleteMember"://刪除成員
	        	message = MemberManagementServer.deleteMember(conn, account);
	        	response.getWriter().write(message);
	        	break;
	        case "changeIdentity"://
	        	message = MemberManagementServer.changeIdentity(conn, account, newIdentity);
	        	response.getWriter().write(message);
	        	break;
	        default:
	        	break;
        }
    	
	}

}
