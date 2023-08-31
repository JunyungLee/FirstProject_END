package dictionary.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CommonJDBC {
	public static final String DRIVER = "oracle.jdbc.OracleDriver";
	public static final String URL = "jdbc:oracle:thin:@192.168.18.14:1521:xe";
	public static final String USER = "PROJECT";
	public static final String PW = "project";
	public static String ID;
	
	public static final String TABLE = "secret";
	public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		try {
			if(rs != null)
				try {rs.close();} catch (Exception e) {}
			if(pstmt != null)
				try {pstmt.close();} catch (SQLException e) {}
			if(conn != null)
				try {conn.close();} catch (SQLException e) {}
		} catch (Exception e) {}
	}
}
