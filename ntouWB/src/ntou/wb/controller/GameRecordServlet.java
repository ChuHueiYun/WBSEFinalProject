package ntou.wb.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ntou.wb.model.DBConnection;
import ntou.wb.model.GameRecordServer;

public class GameRecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String option = request.getParameter("option");
		DBConnection db = (DBConnection) getServletContext().getAttribute("db");
		HttpSession session = request.getSession();
		String memberID = (String) session.getAttribute("memberID");
		String identity = (String) session.getAttribute("identity");
		
		if (option.equals("checkIdentity")) {		//確認身份
			response.getWriter().print(identity);
		} else if (option.equals("getMemberList")) {		//取得隊員列表
			System.out.println(GameRecordServer.getMemberList(db));
			response.getWriter().print(GameRecordServer.getMemberList(db));
		} else if (option.equals("clickMemberGameRecord")) {		//點擊隊員列表
			String clickedMemberID = request.getParameter("clickedMemberID");
			session.setAttribute("clickedMemberID", clickedMemberID);
			System.out.println("clickMemberGameRecord: " + clickedMemberID);
		} else if (option.equals("getMemberGameRecord")) {		//取得某隊員比賽數據
			String clickedMemberID = (String) session.getAttribute("clickedMemberID");
			System.out.println(GameRecordServer.getMemberGameRecord(db, clickedMemberID));
			response.getWriter().print(GameRecordServer.getMemberGameRecord(db, clickedMemberID));
		} else if (option.equals("getMemberName")) {		//取得某隊員姓名
			String clickedMemberID = (String) session.getAttribute("clickedMemberID");
			response.getWriter().print(GameRecordServer.getMemberName(db, clickedMemberID));
		} else if (option.equals("getTeamGameRecordList")) {		//取得比賽列表
			System.out.println(GameRecordServer.getTeamGameRecordList(db));
			response.getWriter().print(GameRecordServer.getTeamGameRecordList(db));
		} else if (option.equals("clickTeamGameRecord")) {		//點擊比賽列表
			String clickedGameID = request.getParameter("clickedGameID");
			session.setAttribute("clickedGameID", clickedGameID);
			System.out.println("clickTeamGameRecord: " + clickedGameID);
		} else if (option.equals("getTeamGameRecord")) {		//取得某場次比賽數據
			String clickedGameID = (String) session.getAttribute("clickedGameID");
			System.out.println(GameRecordServer.getTeamGameRecord(db, clickedGameID));
			response.getWriter().print(GameRecordServer.getTeamGameRecord(db, clickedGameID));
		} else if (option.equals("deleteAllGameRecord")) {		//刪除某場次全部比賽數據
			String clickedGameID = (String) session.getAttribute("clickedGameID");
			GameRecordServer.deleteAllGameRecord(db, clickedGameID);
		} else if (option.equals("saveAllGameRecord")) {		//儲存某場次全部比賽數據
			String clickedGameID = (String) session.getAttribute("clickedGameID");
			String gameRecordData = request.getParameter("gameRecordData");
			GameRecordServer.saveAllGameRecord(db, clickedGameID, gameRecordData);
		} else if (option.equals("addGameRecord")) {		//新增比賽數據
			String gameRecordData = request.getParameter("gameRecordData");
			GameRecordServer.addGameRecord(db, gameRecordData);
		}
		
		
	}
}