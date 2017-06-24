package ntou.wb.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AccountSettingServer {
	//儲存帳戶資訊
	static public String editAccount(Connection conn, String password, String name, String height, String position, String memberID){
		String sql = "UPDATE member SET password = ?, name = ?, height = ?, position = ? WHERE memberID = ?";
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, password);
			statement.setString(2, name);
			statement.setString(3, height);
			statement.setString(4, position);
			statement.setString(5, memberID);
			
			statement.executeUpdate(); 
		} catch (SQLException e) {
			System.out.println("SQLException : " + e);
			return "儲存帳戶資訊失敗";
		}
		finally {
            if (statement != null) 
            	try {
            		statement.close();
            	}
            	catch (SQLException ignore) {}
        }
		return "儲存帳戶資訊成功";
	}
	//取得帳戶資訊
	static public String getAccountInfo(Connection conn, String memberID){
		String jsonString = "";
		JSONArray accountInfo = new JSONArray();
		PreparedStatement statement = null;
		
		try {
			//account password name height position
			String sql = "SELECT * FROM member WHERE memberID = ?";
			statement = conn.prepareStatement(sql);
			statement.setString(1, memberID);  
			            
			ResultSet resultSet = statement.executeQuery();
	          
			while (resultSet.next()) {
				JSONObject accountItem = new JSONObject();

				accountItem.put("account", resultSet.getString("account"));	
				accountItem.put("password", resultSet.getString("password"));
				accountItem.put("name", resultSet.getString("name"));
				accountItem.put("height", resultSet.getString("height"));
				accountItem.put("position", resultSet.getString("position"));
							
				accountInfo.put(accountItem);
			}
			
			if (resultSet != null) try { resultSet.close(); } catch (SQLException ignore) {}
	  
			jsonString = accountInfo.toString();
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
}
