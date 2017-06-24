package ntou.wb.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ntou.wb.model.DBConnection;
import ntou.wb.model.TrainingLogServer;

public class TrainingLogServlet extends HttpServlet {
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
		} else if (option.equals("getTrainingLog") && !identity.equals("coach")) {		//取得訓練日誌(graduated, leader, general)
			response.getWriter().print(TrainingLogServer.getTrainingLog(db, memberID));
		} else if (option.equals("newTrainingLog") && (identity.equals("general") || identity.equals("leader"))) {		//新增訓練日誌(leader, general)
			String newTrainingLogContent = request.getParameter("newTrainingLogContent");
			TrainingLogServer.newTrainingLog(db, memberID, newTrainingLogContent);
		} else if (option.equals("getMemberList") && identity.equals("coach")) {		//取得隊員列表(coach)
			response.getWriter().print(TrainingLogServer.getMemberList(db));
		} else if (option.equals("clickMemberTrainingLog") && identity.equals("coach")) {		//點擊隊員列表(coach)
			String clickedMemberID = request.getParameter("clickedMemberID");
			session.setAttribute("clickedMemberID", clickedMemberID);
		} else if (option.equals("getMemberTrainingLog") && identity.equals("coach")) {		//取得某隊員訓練日誌(coach)
			String clickedMemberID = (String) session.getAttribute("clickedMemberID");
			response.getWriter().print(TrainingLogServer.getMemberTrainingLog(db, clickedMemberID));
		} else if (option.equals("replyTrainingLog") && identity.equals("coach")) {		//回覆訓練日誌(coach)
			String diaryID = request.getParameter("diaryID");
			String replyTrainingLogContent = request.getParameter("replyTrainingLogContent");
			TrainingLogServer.replyTrainingLog(db, memberID, diaryID, replyTrainingLogContent);
		}
	}
}