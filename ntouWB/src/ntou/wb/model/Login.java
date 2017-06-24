package ntou.wb.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
	public static String searchAccount(Connection conn, String account){
		String result = "notFound";
		PreparedStatement statement = null;
		
		String sql = "SELECT account FROM member WHERE BINARY account = ?";
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, account);		            
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				result = "found";
			}
					
			//關閉ResultSet
			if (resultSet != null) {
				try { 
					resultSet.close(); 
				} 
				catch (SQLException ignore) {}
			}
			
			return result;
			
		} catch (SQLException e) {
			System.out.println("SQLException : " + e);
			return "SQLException";
		}
	}
	
	public static String[] loginVerification(Connection conn, String account, String password) {
		String result[] = new String[2];
		result[0] = "";
		result[1] = "";
		
		PreparedStatement statement = null;
		
		String sql = "SELECT memberID ,identity FROM member WHERE BINARY account = ? AND BINARY password = ?";
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, account);
			statement.setString(2, password);		            
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				result[0] = resultSet.getString("memberID");
				result[1] = resultSet.getString("identity");
			}
			//關閉ResultSet
			if (resultSet != null) {
				try { 
					resultSet.close(); 
				} 
				catch (SQLException ignore) {}
			}
			
			return result;
			
		} catch (SQLException e) {
			System.out.println("SQLException : " + e);
			result[0] = "SQLException";
			return result;
		}
	}
}