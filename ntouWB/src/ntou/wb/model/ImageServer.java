package ntou.wb.model;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.JSONArray;


public class ImageServer {
	public static String getTotalPictureID(Connection conn) {
		ArrayList searchResult = new ArrayList();
		JSONArray jsonArray = new JSONArray();		
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement("select * from picture");            
			ResultSet resultSet = statement.executeQuery();		
			while (resultSet.next()) {
				searchResult.add(resultSet.getString("pictureID"));
				searchResult.add(resultSet.getString("picture"));
			}
			//關閉ResultSet
			if (resultSet != null) {
				try { 
					resultSet.close(); 
				} 
				catch (SQLException ignore) {}
			}
			
		} catch (SQLException e) {
			System.out.println("SQLException : " + e);
		}
		for (int i=0; i < searchResult.size(); i++){
	        jsonArray.put(searchResult.get(i));
		}
		return jsonArray.toString();
	}
	
	public static String getPicture(Connection conn, String pictureID) {
		ArrayList searchResult = new ArrayList();
		JSONArray jsonArray = new JSONArray();		
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement("select * from picture where pictureID = '"+pictureID+"'");            
			ResultSet resultSet = statement.executeQuery();		
			while (resultSet.next()) {
				searchResult.add(resultSet.getString("pictureName"));
				searchResult.add(resultSet.getString("date"));
				searchResult.add(resultSet.getString("picture"));
			}
			//關閉ResultSet
			if (resultSet != null) {
				try { 
					resultSet.close(); 
				} 
				catch (SQLException ignore) {}
			}
		} catch (SQLException e) {
			System.out.println("SQLException : " + e);
		}
		for (int i=0; i < searchResult.size(); i++) {
	        jsonArray.put(searchResult.get(i));
		}
		return jsonArray.toString();
	}
	
	public static int UploadPicture(Connection conn, String picture, String pictureName, String date ) {
		int rs = 0;
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement("INSERT INTO picture(pictureID, pictureName, date, picture) SELECT ifNULL(max(pictureID + 0), 0) + 1, '" + pictureName + "', '" + date + "', '" + picture + "' FROM picture");
			statement.executeUpdate();
			rs = 1;
		} catch (SQLException e) {
			System.out.println("SQLException : " + e);
		}
		finally {
            if (statement != null) 
            	try { 
            		statement.close();
            	}
            	catch (SQLException ignore) {}
        }
		return rs;
	}
	
	public static int editPicture(Connection conn, String pictureID, String picture, String pictureName, String date ) {
		int rs = 0;
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement("UPDATE picture set pictureName = '"+pictureName+"', date = '"+date+"', picture = '"+picture+"' where pictureID = '"+pictureID+"'");
			statement.executeUpdate();
			rs = 1;
		} catch (SQLException e) {
			System.out.println("SQLException : " + e);
		}
		finally {
            if (statement != null) 
            	try { 
            		statement.close();
            	}
            	catch (SQLException ignore) {}
        }
		return rs;
	}
	
	public static int deletePicture(Connection conn, String pictureID) {
		int rs = 0;
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement("DELETE FROM picture where pictureID = '"+pictureID+"'");
			statement.executeUpdate();
			rs = 1;
		} catch (SQLException e) {
			System.out.println("SQLException : " + e);
		}
		finally {
            if (statement != null) 
            	try { 
            		statement.close();
            	}
            	catch (SQLException ignore) {}
        }
		return rs;
	}

}
