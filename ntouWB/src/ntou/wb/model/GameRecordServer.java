package ntou.wb.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GameRecordServer {
	//取得隊員列表
	public static String getMemberList(DBConnection conn) {
		String result = "";
		JSONArray ML = new JSONArray();
		try {
			ResultSet resultSet = conn.runSql("SELECT memberID, name, height, number, position, identity FROM member ORDER BY number ASC");
			while (resultSet.next()) {
				if(!resultSet.getString("identity").equals("coach")) {
					JSONObject MLItem = new JSONObject();
					MLItem.put("memberID", resultSet.getString("memberID"));
					MLItem.put("name", resultSet.getString("name"));
					MLItem.put("height", resultSet.getString("height"));
					MLItem.put("number", resultSet.getString("number"));
					MLItem.put("position", resultSet.getString("position"));
					MLItem.put("identity", resultSet.getString("identity"));
					ML.put(MLItem);
				}
			}
			result = ML.toString();
			if(result.equals("")) {
				System.out.println("getMemberList empty");
				result = "getMemberListFail";
			}
			if (resultSet != null) resultSet.close();
			return result;
		} catch (SQLException e) {
			System.out.println("getMemberList SQLException: " + e);
			result = "getMemberListFail";
			return result;
		} catch (JSONException e) {
			System.out.println("getMemberList JSONException: " + e);
			result = "getMemberListFail";
			return result;
		}
	}
	//取得某隊員比賽數據
	public static String getMemberGameRecord(DBConnection conn, String memberID) {
		String result = "";
		JSONArray MGR = new JSONArray();
		try {
			ResultSet resultSet = conn.runSql("SELECT * FROM game_record WHERE memberID = '" + memberID + "' ORDER BY gameID ASC");
			while (resultSet.next()) {
				ResultSet resultSet2 = conn.runSql("SELECT * FROM game WHERE gameID = '" + resultSet.getString("gameID") + "'");
				JSONObject MGRItem = new JSONObject();
				MGRItem.put("gameID", resultSet.getString("gameID"));
				while (resultSet2.next()) {
					MGRItem.put("opponent", resultSet2.getString("opponent"));
					MGRItem.put("date", resultSet2.getString("date"));
				}
				MGRItem.put("game_2PM", resultSet.getString("game_2PM"));
				MGRItem.put("game_2PA", resultSet.getString("game_2PA"));
				MGRItem.put("game_3PM", resultSet.getString("game_3PM"));
				MGRItem.put("game_3PA", resultSet.getString("game_3PA"));
				MGRItem.put("game_FTM", resultSet.getString("game_FTM"));
				MGRItem.put("game_FTA", resultSet.getString("game_FTA"));
				MGRItem.put("game_STL", resultSet.getString("game_STL"));
				MGRItem.put("game_RB", resultSet.getString("game_RB"));
				MGRItem.put("game_AST", resultSet.getString("game_AST"));
				MGRItem.put("game_TO", resultSet.getString("game_TO"));
				MGRItem.put("game_FOUL", resultSet.getString("game_FOUL"));
				MGRItem.put("game_point", Integer.valueOf(resultSet.getString("game_2PM")) * 2 + Integer.valueOf(resultSet.getString("game_3PM")) * 3 + Integer.valueOf(resultSet.getString("game_FTM")));
				MGR.put(MGRItem);
			}
			result = MGR.toString();
			if (result.equals("")) {
				System.out.println("getMemberGameRecord empty");
				result = "getMemberGameRecordFail";
			}
			if (resultSet != null) resultSet.close();
			return result;
		} catch (SQLException e) {
			System.out.println("getMemberGameRecord SQLException: " + e);
			result = "getMemberGameRecordFail";
			return result;
		} catch (JSONException e) {
			System.out.println("getMemberGameRecord JSONException: " + e);
			result = "getMemberGameRecordFail";
			return result;
		}
	}
	//取得某隊員姓名
	public static String getMemberName(DBConnection conn, String memberID) {
		String result = "";
		try {
			ResultSet resultSet = conn.runSql("SELECT name FROM member WHERE memberID = '" + memberID + "'");
			while (resultSet.next()) {
				result = resultSet.getString("name");
			}
			if (result.equals("")) {
				System.out.println("getMemberName empty");
				result = "getMemberNameFail";
			}
			if (resultSet != null) resultSet.close();
			return result;
		} catch (SQLException e) {
			System.out.println("getMemberName SQLException: " + e);
			result = "getMemberNameFail";
			return result;
		}
	}
	//取得比賽列表
	public static String getTeamGameRecordList(DBConnection conn) {
			String result = "";
			JSONArray TGRL = new JSONArray();
			try {
				ResultSet resultSet = conn.runSql("SELECT * FROM game ORDER BY gameID ASC");
				while (resultSet.next()) {
					JSONObject TGRLItem = new JSONObject();
					TGRLItem.put("gameID", resultSet.getString("gameID"));
					TGRLItem.put("date", resultSet.getString("date"));
					TGRLItem.put("opponent", resultSet.getString("opponent"));
					String[] score = resultSet.getString("score").split(":");
					TGRLItem.put("ourPoint", Integer.valueOf(score[0]));
					TGRLItem.put("opponentPoint", Integer.valueOf(score[1]));
					TGRLItem.put("result", resultSet.getString("result"));
					TGRL.put(TGRLItem);
				}
				result = TGRL.toString();
				if(result.equals("")) {
					System.out.println("getMemgetTeamGameRecordListberList empty");
					result = "getTeamGameRecordListFail";
				}
				if (resultSet != null) resultSet.close();
				return result;
			} catch (SQLException e) {
				System.out.println("getTeamGameRecordList SQLException: " + e);
				result = "getTeamGameRecordListFail";
				return result;
			} catch (JSONException e) {
				System.out.println("getTeamGameRecordList JSONException: " + e);
				result = "getTeamGameRecordListFail";
				return result;
			}
		}
	//取得某隊員比賽數據
	public static String getTeamGameRecord(DBConnection conn, String gameID) {
		String result = "";
		JSONObject TGR = new JSONObject();
		JSONArray MGR = new JSONArray();
		try {
			ResultSet resultSet = conn.runSql("SELECT * FROM game WHERE gameID = '" + gameID + "' ORDER BY gameID ASC");
			while (resultSet.next()) {
				TGR.put("gameID", resultSet.getString("gameID"));
				TGR.put("date", resultSet.getString("date"));
				TGR.put("opponent", resultSet.getString("opponent"));
				String[] score = resultSet.getString("score").split(":");
				TGR.put("ourPoint", Integer.valueOf(score[0]));
				TGR.put("opponentPoint", Integer.valueOf(score[1]));
				TGR.put("result", resultSet.getString("result"));
			}
			resultSet = conn.runSql("SELECT * FROM game_record WHERE gameID = '" + gameID + "' ORDER BY gameID ASC");
			while (resultSet.next()) {
				JSONObject MGRItem = new JSONObject();
				MGRItem.put("memberID", resultSet.getString("memberID"));
				ResultSet resultSet2 = conn.runSql("SELECT memberID, name, number, position FROM member WHERE memberID = '" + resultSet.getString("memberID") + "'");
				while (resultSet2.next()) {
					MGRItem.put("number", resultSet2.getString("number"));
					MGRItem.put("name", resultSet2.getString("name"));
					MGRItem.put("position", resultSet2.getString("position"));
				}
				MGRItem.put("game_2PM", resultSet.getString("game_2PM"));
				MGRItem.put("game_2PA", resultSet.getString("game_2PA"));
				MGRItem.put("game_3PM", resultSet.getString("game_3PM"));
				MGRItem.put("game_3PA", resultSet.getString("game_3PA"));
				MGRItem.put("game_FTM", resultSet.getString("game_FTM"));
				MGRItem.put("game_FTA", resultSet.getString("game_FTA"));
				MGRItem.put("game_STL", resultSet.getString("game_STL"));
				MGRItem.put("game_RB", resultSet.getString("game_RB"));
				MGRItem.put("game_AST", resultSet.getString("game_AST"));
				MGRItem.put("game_TO", resultSet.getString("game_TO"));
				MGRItem.put("game_FOUL", resultSet.getString("game_FOUL"));
				MGRItem.put("game_point", Integer.valueOf(resultSet.getString("game_2PM")) * 2 + Integer.valueOf(resultSet.getString("game_3PM")) * 3 + Integer.valueOf(resultSet.getString("game_FTM")));
				MGR.put(MGRItem);
			}
			TGR.put("MGR", MGR);
			result = TGR.toString();
			if (result.equals("")) {
				System.out.println("getTeamGameRecord empty");
				result = "getTeamGameRecordFail";
			}
			if (resultSet != null) resultSet.close();
			return result;
		} catch (SQLException e) {
			System.out.println("getTeamGameRecord SQLException: " + e);
			result = "getTeamGameRecordFail";
			return result;
		} catch (JSONException e) {
			System.out.println("getTeamGameRecord JSONException: " + e);
			result = "getTeamGameRecordFail";
			return result;
		}
	}
	//刪除某場次全部比賽數據
	public static void deleteAllGameRecord(DBConnection conn, String gameID) {
		try {
			conn.updateSql("DELETE FROM game_record WHERE gameID = '" + gameID + "'");
			conn.updateSql("DELETE FROM game WHERE gameID = '" + gameID + "'");
		} catch (SQLException e) {
			System.out.println("deleteAllGameRecord SQLException: " + e);
		}
	}
	//儲存某場次全部比賽數據
	public static void saveAllGameRecord(DBConnection conn, String gameID, String gameRecordData) {
		try {
			JSONObject GRD = new JSONObject(gameRecordData);
			conn.updateSql("UPDATE game SET gameID = '" + gameID + "', date = '" + GRD.getString("date") + "', opponent = '" + GRD.getString("opponent") + "', score = '" + GRD.getString("score") + "', result = '" + GRD.getString("result") + "' WHERE gameID = '" + gameID + "'");
			for (int i=0; i<GRD.getJSONArray("MGR").length(); i++) {
				ResultSet resultSet = conn.runSql("SELECT memberID FROM member WHERE name = '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("name") + "'");
				while (resultSet.next()) {
					ResultSet resultSet2 = conn.runSql("SELECT * FROM game_record WHERE memberID = '" + resultSet.getString("memberID") + "'");
					if (resultSet2.next()) {
						conn.updateSql("UPDATE game_record SET game_2PM = '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("game_2PM") + "', game_2PA = '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("game_2PA") + "' WHERE gameID = '" + gameID + "' AND memberID = '" + resultSet.getString("memberID") + "'");
						conn.updateSql("UPDATE game_record SET game_3PM = '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("game_3PM") + "', game_3PA = '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("game_3PA") + "' WHERE gameID = '" + gameID + "' AND memberID = '" + resultSet.getString("memberID") + "'");
						conn.updateSql("UPDATE game_record SET game_FTM = '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("game_FTM") + "', game_FTA = '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("game_FTA") + "' WHERE gameID = '" + gameID + "' AND memberID = '" + resultSet.getString("memberID") + "'");
						conn.updateSql("UPDATE game_record SET game_STL = '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("game_STL") + "', game_RB = '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("game_RB") + "' WHERE gameID = '" + gameID + "' AND memberID = '" + resultSet.getString("memberID") + "'");
						conn.updateSql("UPDATE game_record SET game_AST = '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("game_AST") + "', game_TO = '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("game_TO") + "' WHERE gameID = '" + gameID + "' AND memberID = '" + resultSet.getString("memberID") + "'");
						conn.updateSql("UPDATE game_record SET game_FOUL = '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("game_FOUL") + "' WHERE gameID = '" + gameID + "' AND memberID = '" + resultSet.getString("memberID") + "'");
					} else {
						conn.updateSql("INSERT INTO game_record(gameID, memberID, game_2PA, game_2PM, game_3PA, game_3PM, game_FTA, game_FTM, game_STL, game_RB, game_AST, game_TO, game_FOUL) VALUES ('" + gameID + "', '" + resultSet.getString("memberID") + "', '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("game_2PA") + "', '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("game_2PM") + "', '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("game_3PA") + "', '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("game_3PM") + "', '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("game_FTA") + "', '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("game_FTM") + "', '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("game_STL") + "', '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("game_RB") + "', '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("game_AST") + "', '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("game_TO") + "', '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("game_FOUL") + "')");
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("saveAllGameRecord SQLException: " + e);
		} catch (JSONException e) {
			System.out.println("saveAllGameRecord JSONException: " + e);
		}
	}
	//新增比賽數據
	public static void addGameRecord(DBConnection conn, String gameRecordData) {
		try {
			JSONObject GRD = new JSONObject(gameRecordData);
			conn.updateSql("INSERT INTO game(gameID, date, opponent, score, result) SELECT ifNULL(max(gameID + 0), 0) + 1, '" + GRD.getString("date") + "', '" + GRD.getString("opponent") + "', '" + GRD.getString("score") + "', '" + GRD.getString("result") + "' FROM game");
			String gameID = "";
			ResultSet resultSet = conn.runSql("SELECT MAX(gameID) as maxGameID FROM game");
			while (resultSet.next()) {
				gameID = resultSet.getString("maxGameID");
			}
			for (int i=0; i<GRD.getJSONArray("MGR").length(); i++) {
				resultSet = conn.runSql("SELECT memberID FROM member WHERE name = '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("name") + "'");
				while (resultSet.next()) {
					conn.updateSql("INSERT INTO game_record(gameID, memberID, game_2PA, game_2PM, game_3PA, game_3PM, game_FTA, game_FTM, game_STL, game_RB, game_AST, game_TO, game_FOUL) VALUES ('" + gameID + "', '" + resultSet.getString("memberID") + "', '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("game_2PA") + "', '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("game_2PM") + "', '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("game_3PA") + "', '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("game_3PM") + "', '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("game_FTA") + "', '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("game_FTM") + "', '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("game_STL") + "', '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("game_RB") + "', '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("game_AST") + "', '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("game_TO") + "', '" + GRD.getJSONArray("MGR").getJSONObject(i).getString("game_FOUL") + "')");
				}
			}
		} catch (SQLException e) {
			System.out.println("saveAllGameRecord SQLException: " + e);
		} catch (JSONException e) {
			System.out.println("saveAllGameRecord JSONException: " + e);
		}
	}
}