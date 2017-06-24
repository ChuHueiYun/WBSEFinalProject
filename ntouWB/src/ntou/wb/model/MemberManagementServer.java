package ntou.wb.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MemberManagementServer {
	//修改身份
	static public String changeIdentity(Connection conn, String account, String newIdentity){
		String sql = "UPDATE member SET identity = ? WHERE account = ?";
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, newIdentity);
			statement.setString(2, account);
			statement.executeUpdate(); 
		} 
		catch (SQLException e) {
			System.out.println("SQLException:" + e);
			return "修改失敗";
		}
		finally {
            if (statement != null) 
            	try {
            		statement.close();
            	}
            	catch (SQLException ignore) {}
        }
		return "修改成功";
		
	}
	
	//刪除成員
	static public String deleteMember(Connection conn, String account){
		PreparedStatement statement = null;
		String memberID = "";
		
		try {
			//取得memberID
			statement = conn.prepareStatement("SELECT memberID FROM member WHERE account = ?");
			statement.setString(1, account);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				memberID = resultSet.getString("memberID");
			}
			
			//刪除diary表格裡的資料
			statement = conn.prepareStatement("DELETE FROM diary WHERE memberID = ?");
			statement.setString(1, memberID);
			statement.executeUpdate(); 
			
			//刪除game_record表格裡的資料
			statement = conn.prepareStatement("DELETE FROM game_record WHERE memberID = ?");
			statement.setString(1, memberID);
			statement.executeUpdate(); 
			
			//刪除member表格裡的資料
			statement = conn.prepareStatement("DELETE FROM member WHERE memberID = ?");
			statement.setString(1, memberID);
			statement.executeUpdate(); 
		} catch (SQLException e) {
			System.out.println("SQLException : " + e);
			return "SQLException";
		}
		finally {
            if (statement != null) 
            	try { 
            		statement.close();
            	}
            	catch (SQLException ignore) {}
        }
		return "刪除成功";
	}
	
	
	//取得成員名單
	static public String getMemberList(Connection conn){
		String jsonString = "";
		JSONArray memberList = new JSONArray();
		PreparedStatement statement = null;
		
		try {
			//account name height number position identity
			String sql = "SELECT account, name, height, number, position, identity FROM member WHERE identity <> 'coach'";
			statement = conn.prepareStatement(sql);           
			            
			ResultSet resultSet = statement.executeQuery();
	          
			while (resultSet.next()) {
				JSONObject memberItem = new JSONObject();

				memberItem.put("account", resultSet.getString("account"));
				memberItem.put("name", resultSet.getString("name"));
				memberItem.put("height", resultSet.getString("height"));
				memberItem.put("number", resultSet.getString("number"));
				memberItem.put("position", resultSet.getString("position"));
				memberItem.put("identity", resultSet.getString("identity"));
				
				memberList.put(memberItem);
			}
			
			if (resultSet != null) try { resultSet.close(); } catch (SQLException ignore) {}
	  
			jsonString = memberList.toString();
			//System.out.println(jsonString);

			return jsonString;
		}
		catch (SQLException e) {
			System.out.println("SQLException : " + e);
		}
		catch (JSONException e) {
			System.out.println("JSONException : " + e);
		}
		finally {
			if (statement != null) 
				try { statement.close(); }
	            catch (SQLException ignore) {}
		}
		
		return jsonString;
	}
	
	
	static public void addMember(Connection conn, String name, int number, String identity){
		int maxMemberID = 0;
		int newMemberID = 0;
		PreparedStatement ps = null;
		try {
			String sql = "SELECT MAX(memberID) FROM member";
			ps = conn.prepareStatement(sql);
			
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				maxMemberID = resultSet.getInt(1);
			}
			newMemberID = maxMemberID+1;
			
			ps = conn.prepareStatement("INSERT INTO member VALUES (?, ?, ?, ?, ?, ?, ?, ?)");			
			ps.setInt(1, newMemberID);		//memberID
			ps.setString(2, "NTOUWB"+String.format("%04d", newMemberID));			//account
			ps.setString(3, "123456");			//password
			ps.setString(4, name);				//name
			ps.setInt(5, 0);					//height
			ps.setInt(6, number);				//number
			ps.setString(7, "");				//position
			ps.setString(8, identity);			//identity
			ps.execute();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
            if (ps != null) 
            	try {
            		ps.close();
            	}
            	catch (SQLException ignore) {}
        }
	}
}
