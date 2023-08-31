package dictionary.dao.secret;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import dictionary.dao.CommonJDBC;

public class SecretCodeGenerator {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private FileManager fm = new FileManager();

	static {
		try {
			Class.forName(CommonJDBC.DRIVER);
		} catch (Exception e) {
			System.out.println("[경고] - 접속 실패");
		}
	}

	public boolean encodeFile(String path) {
		return fm.write(encode(fm.read(path)), path);
	}

	public String decodeFile(String path) {
		return decode(fm.read(path));
	}

	/**
	 * **처음 한번만 사용할 것** 암호 코드 배열 만드는 메소드. (성공하면 true, 실패히면 false 리턴.
	 * 
	 * @return
	 */
	public boolean makeCode() {

		Map<Integer, String> map = new HashMap<Integer, String>(); // 문자와 코드를 임시 저장해놓을 맵
		Random r = new Random();

		// 조합가능한 한글에 코드 부여
		char c = '가';
		for (int i = 0; i < 11172; i++) {
			int code = r.nextInt(20000);
			if (map.containsKey(code)) {
				i--;
				continue;
			}
			map.put(code, String.valueOf(c++));

		}

		// 특수문자, 숫자, 알파벳에 코드 부여
		c = ' ';
		for (int i = 0; i < 95; i++) {

			int code = r.nextInt(20000);
			if (map.containsKey(code)) {
				i--;
				continue;
			}
			map.put(code, String.valueOf(c++));

		}


		// DB에 저장
		try {
			conn = DriverManager.getConnection(CommonJDBC.URL, CommonJDBC.USER, CommonJDBC.PW);

			StringBuilder sb = new StringBuilder();
			sb.setLength(0);
			sb.append("INSERT INTO SECRET (WORD, CODE) ");
			sb.append("VALUES (?,?)");
			pstmt = conn.prepareStatement(sb.toString());
			for (int key : map.keySet()) {
				pstmt.setString(1, map.get(key));
				pstmt.setInt(2, key);
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			CommonJDBC.close(conn, pstmt, rs);
		}

		return true;
	}

	/**
	 * 문장을 암호화 하는 메소드. 파라미터로 문장을 받는다. 성공하면 암호화된 문장이 리턴되고, 실패하면 null이 리턴된다.
	 * 
	 * @param input
	 * @return
	 */
	public String encode(String input) {

		String[] arr = input.split("");
		StringBuilder result = new StringBuilder();
		StringBuilder sql = new StringBuilder();

		try {
			conn = DriverManager.getConnection(CommonJDBC.URL, CommonJDBC.USER, CommonJDBC.PW);
			sql.append("SELECT CODE FROM SECRET WHERE WORD = ?");
			pstmt = conn.prepareStatement(sql.toString());

			for (String temp : arr) {
				if (temp.equals("\n")) {
					result.append("\n");
					result.append("/");
					continue;
				} else if (temp.equals("\r")) {
					result.append("\r");
					result.append("/");
					continue;
				}

				pstmt.setString(1, temp);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					result.append(rs.getString("code"));
					result.append("/");
				} else
					result.append(temp);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			CommonJDBC.close(conn, pstmt, rs);
		}

		return result.toString();
	}

	/**
	 * 암호를 복호화 하는 메소드. 파라미터로 문장을 받는다. 성공하면 암호화된 문장이 리턴되고, 실패하면 null이 리턴된다.
	 * 
	 * @param input
	 * @return
	 */
	public String decode(String input) {
		String[] arr = input.split("/");

		StringBuilder result = new StringBuilder();
		try {
			conn = DriverManager.getConnection(CommonJDBC.URL, CommonJDBC.USER, CommonJDBC.PW);
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT WORD FROM SECRET WHERE CODE = ?");
			pstmt = conn.prepareStatement(sql.toString());

			for (String temp : arr) {
				if (temp.equals("\n")) {
					result.append("\n");
					System.out.print("\n");
					continue;
				} else if (temp.equals("\r")) {
					result.append("\r");
					System.out.print("\r");
					continue;
				}
				pstmt.setString(1, temp);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					result.append(rs.getString("word"));
					Thread.sleep(5);
					System.out.print(rs.getString("word"));
				}
			}

		} catch (SQLException e) {
			System.out.println("[경고] - 오류 발생");
			return null;
		} catch (InterruptedException e) {
			System.out.println("[경고] - 오류 발생");
		} finally {
			CommonJDBC.close(conn, pstmt, rs);
		}

		return result.toString();
	}

}
