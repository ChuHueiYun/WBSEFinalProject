package ntou.wb.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ntou.wb.model.DBConnection;
import ntou.wb.model.Login;

public class LoginServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		String option = request.getParameter("option");
		
		HttpSession session = request.getSession();	
		session.setMaxInactiveInterval(60*10);	//	10分鐘

		//資料庫連線
        DBConnection db = (DBConnection) getServletContext().getAttribute("db");
        Connection conn = db.getConnection();
		
        String result[] = null;
		
		switch(option){			
			case "login":	//登入
				//System.out.println("帳號："+account);
				//System.out.println("密碼："+password);
				
				if(Login.searchAccount(conn, account).equals("notFound")){	//搜尋帳號		
					response.getWriter().print("帳號不存在");
					//System.out.println("帳號不存在");
				}
				else if(Login.searchAccount(conn, account).equals("SQLException")){
					response.getWriter().print("SQLException");
				}
				else {
					result = Login.loginVerification(conn, account, password);
					
					if(result[0].equals("")){
						response.getWriter().print("密碼錯誤");
						//System.out.println("密碼錯誤");
					}
					else if(result[0].equals("SQLException")){
						response.getWriter().print("SQLException");
						//System.out.println("SQLException");
					}
					else{	//登入成功
						response.getWriter().print("登入成功");
						//System.out.println("登入成功");						
						
						session.setAttribute("memberID", result[0]);
						//System.out.println("memberID(session內容) : " + (String) session.getAttribute("memberID"));
						
						session.setAttribute("identity", result[1]);
						//System.out.println("身份(session內容) : " + (String) session.getAttribute("identity"));
					}			
				}		
				break;
			case "logout":
				session.invalidate();
				//System.out.println("登出成功");	
				response.getWriter().print("登出成功");
				break;
			case "getIdentity":
				response.getWriter().print((String) session.getAttribute("identity"));
				break;
			case "getMemberID":
				//System.out.println("memberID(session內容) : " + (String) session.getAttribute("memberID"));
				response.getWriter().print((String) session.getAttribute("memberID"));
				break;
			default:
				break;
		}
   
	}

}
