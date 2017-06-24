package ntou.wb.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TrainingLogServer {
	//取得訓練日誌(graduated, leader, general)
	public static String getTrainingLog(DBConnection conn, String memberID) {
		String result = "";
		JSONArray TL = new JSONArray();
		try {
			ResultSet resultSet = conn.runSql("SELECT * FROM diary WHERE memberID = '" + memberID + "' ORDER BY date DESC");
			while (resultSet.next()) {
				JSONObject TLItem = new JSONObject();
				TLItem.put("diaryID", resultSet.getString("diaryID"));
				TLItem.put("memberID", resultSet.getString("memberID"));
				TLItem.put("date", resultSet.getString("date"));
				TLItem.put("content", resultSet.getString("content"));
				if(resultSet.getString("replyer") != null) {
					ResultSet resultSet2 = conn.runSql("SELECT name FROM member WHERE memberID = '" + resultSet.getString("replyer") + "'");
					while (resultSet2.next()) {
						TLItem.put("replyerName", resultSet2.getString("name"));
					}
					TLItem.put("replyer", resultSet.getString("replyer"));
					TLItem.put("replyDate", resultSet.getString("replyDate"));
					TLItem.put("reply", resultSet.getString("reply"));
				}
				TL.put(TLItem);
			}
			result = TL.toString();
			if(result.equals("")) {
				System.out.println("getTrainingLog empty");
				result = "getTrainingLogFail";
			}
			if (resultSet != null) resultSet.close();
			return result;
		} catch (SQLException e) {
			System.out.println("getTrainingLog SQLException: " + e);
			result = "getTrainingLogFail";
			return result;
		} catch (JSONException e) {
			System.out.println("newTrainingLog JSONException: " + e);
			result = "getTrainingLogFail";
			return result;
		}
	}
	//新增訓練日誌(leader, general)
	public static void newTrainingLog(DBConnection conn, String memberID, String content) {
		//取得現在時間
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentDate = simpleDateFormat.format(timestamp).substring(0, 10);
		try {
			conn.updateSql("INSERT INTO diary(diaryID, memberID, date, content, replyer, replyDate, reply) SELECT ifNULL(max(diaryID + 0), 0) + 1, '" + memberID + "', '" + currentDate + "', '" + content + "', NULL, NULL, NULL FROM diary");
		} catch (SQLException e) {
			System.out.println("newTrainingLog SQLException: " + e);
		}
	}
	//取得隊員列表(coach)
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
	//取得某隊員訓練日誌(coach)
	public static String getMemberTrainingLog(DBConnection conn, String memberID) {
		String result = "";
		JSONArray ML = new JSONArray();
		try {
			ResultSet resultSet = conn.runSql("SELECT memberID, name, number, identity FROM member ORDER BY number ASC");
			while (resultSet.next()) {
				if(!resultSet.getString("identity").equals("coach")) {
					JSONObject MLItem = new JSONObject();
					MLItem.put("memberID", resultSet.getString("memberID"));
					MLItem.put("name", resultSet.getString("name"));
					MLItem.put("number", resultSet.getString("number"));
					ML.put(MLItem);
				}
			}
			result = "{\"ML\":" + ML.toString() + ",\"MTL\":" + getTrainingLog(conn, memberID) + ",\"MName\":\"" + getMemberName(conn, memberID) + "\"}";
			if(result.equals("")) {
				System.out.println("getMemberList empty");
				result = "getMemberListFail";
			}
			if (resultSet != null) resultSet.close();
			return result;
		} catch (SQLException e) {
			System.out.println("getMemberTrainingLog SQLException: " + e);
			result = "getMemberTrainingLogFail";
			return result;
		} catch (JSONException e) {
			System.out.println("getMemberTrainingLog JSONException: " + e);
			result = "getMemberTrainingLogFail";
			return result;
		}
	}
	//取得隊員姓名
	public static String getMemberName(DBConnection conn, String memberID) {
		String result = "";
		try {
			ResultSet resultSet = conn.runSql("SELECT name FROM member WHERE memberID = '" + memberID + "'");
			while (resultSet.next()) {
				result = resultSet.getString("name");
			}
			if(result.equals("")) {
				System.out.println("getMemberList empty");
				result = "無姓名";
			}
			if (resultSet != null) resultSet.close();
			return result;
		} catch (SQLException e) {
			System.out.println("getMemberName SQLException: " + e);
			result = "無姓名";
			return result;
		}
	}
	//回覆訓練日誌(coach)
	public static void replyTrainingLog(DBConnection conn, String memberID, String diaryID, String replyContent) {
		//取得現在時間
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentDate = simpleDateFormat.format(timestamp).substring(0, 10);
		try {
			conn.updateSql("UPDATE diary SET replyer = '" + memberID + "', replyDate = '" + currentDate + "', reply = '" + replyContent + "' WHERE diaryID = '" + diaryID + "'");
		} catch (SQLException e) {
			System.out.println("replyTrainingLog SQLException: " + e);
		}
	}
}