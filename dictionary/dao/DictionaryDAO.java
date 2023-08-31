package dictionary.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dictionary.vo.DatafileVO;
import dictionary.vo.DictionVO;
import dictionary.vo.MemberVO;
import dictionary.vo.RankviewVO;
import dictionary.vo.ScoreVO;
import dictionary.vo.TestLogVO;
import dictionary.vo.TestVO;

public class DictionaryDAO {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	static {
		try {
			Class.forName(CommonJDBC.DRIVER);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//--------------------Member----------------------------
	public int memberInsert(MemberVO vo) {
		int result = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO MEMBER VALUES (?,?,?,?)");
		
		try {
			connect(sql.toString());
			
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getName());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getPhone());
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJDBC.close(conn, pstmt, rs);
		}
		return result;
	}
	
	public MemberVO memberSelectByPhone(String phone) {
		MemberVO vo = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ID, NAME, PASSWORD, PHONE FROM MEMBER WHERE PHONE = ?");
		
		try {
			connect(sql.toString());
			
			pstmt.setString(1, phone);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo = new MemberVO(rs.getString("id"), rs.getString("name"), rs.getString("password"), rs.getString("phone"));
			}
			return vo;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJDBC.close(conn, pstmt, rs);
		}
		return null;
	}
	
	public MemberVO memberSelect(String id) {
		MemberVO vo = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ID, NAME, PASSWORD, PHONE FROM MEMBER WHERE ID = ?");
		
		try {
			connect(sql.toString());
			
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo = new MemberVO(rs.getString("id"), rs.getString("name"), rs.getString("password"), rs.getString("phone"));
				
			}
			return vo;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJDBC.close(conn, pstmt, rs);
		}
		return null;
	}
	
	public List<MemberVO> memberSelectByName(String name) {
		List<MemberVO> list = new ArrayList<MemberVO>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ID, NAME, PASSWORD, PHONE FROM MEMBER WHERE NAME = ?");
		
		try {
			connect(sql.toString());
			
			pstmt.setString(1, name);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				list.add(new MemberVO(rs.getString("id"), rs.getString("name"), rs.getString("password"), rs.getString("phone")));
				
			}
			if(list.size() != 0)
				return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJDBC.close(conn, pstmt, rs);
		}
		return null;
	}
	
	public MemberVO memberSelect(MemberVO vo) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ID, NAME, PASSWORD FROM MEMBER WHERE ID = ?");
		
		try {
			connect(sql.toString());
			
			pstmt.setString(1, vo.getId());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo = new MemberVO(rs.getString("id"), rs.getString("name"), rs.getString("password"), rs.getString("phone"));
			}
			return vo;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJDBC.close(conn, pstmt, rs);
		}
		return null;
	}
	
	public int memberUpdate(MemberVO vo) {
		int result = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE MEMBER SET ID=?, NAME=?, PASSWORD=?, PHONE = ? WHERE ID=?");
		
		try {
			connect(sql.toString());
			
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getName());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getPhone());
			pstmt.setString(5, vo.getId());
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJDBC.close(conn, pstmt, rs);
		}
		return result;
	}
	
	public int memberDelete(MemberVO vo) {
		int result = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM MEMBER WHERE ID = ?");
		
		try {
			connect(sql.toString());
			
			pstmt.setString(1, vo.getId());
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJDBC.close(conn, pstmt, rs);
		}
		return result;
	}
	//--------------------Member----------------------------
	//--------------------Diction----------------------------
	public int dictionInsert(DictionVO vo) {
		int result = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO DICTION VALUES (?,?,?)");
		
		try {
			connect(sql.toString());
			
			pstmt.setInt(1, vo.getD_id());
			pstmt.setString(2, vo.getKeyword());
			pstmt.setString(3, vo.getGrade());
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJDBC.close(conn, pstmt, rs);
		}
		return result;
	}
	
	public DictionVO dictionSelect(DictionVO vo) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT D_ID, KETWORD, R_GRADE FROM DICTION WHERE D_ID = ?");
		
		try {
			connect(sql.toString());
			
			pstmt.setInt(1, vo.getD_id());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo = new DictionVO(rs.getInt("d_id"), rs.getString("keyword"), rs.getString("r_grade"));
			}
			return vo;
		} catch (SQLException e) {
		} finally {
			CommonJDBC.close(conn, pstmt, rs);
		}
		return null;
	}
	
	public DictionVO dictionSelect(int id) {
		StringBuilder sql = new StringBuilder();
		DictionVO vo = null;
		sql.append("SELECT D_ID, KEYWORD, R_GRADE FROM DICTION WHERE D_ID = ?");
		
		try {
			connect(sql.toString());
			
			pstmt.setInt(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo = new DictionVO(rs.getInt("d_id"), rs.getString("keyword"), rs.getString("r_grade"));
			}
			return vo;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJDBC.close(conn, pstmt, rs);
		}
		return null;
	}
	
	public DictionVO dictionSelectKeyword(String keyword) {
		DictionVO vo = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT D_ID, KEYWORD, R_GRADE FROM DICTION WHERE KEYWORD = ?");
		
		try {
			connect(sql.toString());
			
			pstmt.setString(1, keyword);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo = new DictionVO(rs.getInt("d_id"), rs.getString("keyword"), rs.getString("r_grade"));
			}
			return vo;
		} catch (SQLException e) {
		} finally {
			CommonJDBC.close(conn, pstmt, rs);
		}
		return null;
	}
	
	public List<DictionVO> dictionSelectLike(String keyword) {
		List<DictionVO> list = new ArrayList<>();
		DictionVO vo = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT D_ID, KEYWORD, R_GRADE FROM DICTION WHERE KEYWORD LIKE ?");
		
		try {
			connect(sql.toString());
			
			pstmt.setString(1, "%" + keyword + "%");
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				vo = new DictionVO(rs.getInt("d_id"), rs.getString("keyword"), rs.getString("r_grade"));
				list.add(vo);
			}
			return list;
			
		} catch (SQLException e) {
		} finally {
			CommonJDBC.close(conn, pstmt, rs);
		}
		return null;
	}


	
	public List<DictionVO> dictionSelectAll() {
		List<DictionVO> list = new ArrayList<DictionVO>();
		DictionVO vo = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT D_ID, KEYWORD, R_GRADE FROM DICTION");
		
		try {
			connect(sql.toString());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				vo = new DictionVO(rs.getInt("d_id"), rs.getString("keyword"), rs.getString("r_grade"));
				list.add(vo);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJDBC.close(conn, pstmt, rs);
		}
		return null;
	}
	
	public int dictionUpdate(DictionVO vo) {
		int result = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE DICTION SET D_ID=?, KEYWORD=?, R_GRADE=? WHERE D_ID=?");
		
		try {
			connect(sql.toString());
			
			pstmt.setInt(1, vo.getD_id());
			pstmt.setString(2, vo.getKeyword());
			pstmt.setString(3, vo.getGrade());
			pstmt.setInt(4, vo.getD_id());
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJDBC.close(conn, pstmt, rs);
		}
		return result;
	}
	
	public int dictionDelete(DictionVO vo) {
		int result = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM DICTION WHERE D_ID = ?");
		
		try {
			connect(sql.toString());
			
			pstmt.setInt(1, vo.getD_id());
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJDBC.close(conn, pstmt, rs);
		}
		return result;
	}
	
	//--------------------Diction----------------------------
	//--------------------DataFile----------------------------
	public int dataFileInsert(DatafileVO vo) {
		int result = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO DATAFILE VALUES (?,?)");
		
		try {
			connect(sql.toString());
			
			pstmt.setInt(1, vo.getD_id());
			pstmt.setString(2, vo.getFileName());
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJDBC.close(conn, pstmt, rs);
		}
		return result;
	}
	
	public DatafileVO dataFileSelect(DatafileVO vo) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT D_ID, FILENAME FROM DATAFILE WHERE D_ID = ?");
		
		try {
			connect(sql.toString());
			
			pstmt.setInt(1, vo.getD_id());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo = new DatafileVO(rs.getInt("d_id"), rs.getString("filename"));
			}
			return vo;
		} catch (SQLException e) {
		} finally {
			CommonJDBC.close(conn, pstmt, rs);
		}
		return null;
	}
	
	public DatafileVO dataFileSelect(int id) {
		StringBuilder sql = new StringBuilder();
		DatafileVO vo = null;
		sql.append("SELECT D_ID, FILENAME FROM DATAFILE WHERE D_ID = ?");
		
		try {
			connect(sql.toString());
			
			pstmt.setInt(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo = new DatafileVO(rs.getInt("d_id"), rs.getString("filename"));
			}
			return vo;
		} catch (SQLException e) {
		} finally {
			CommonJDBC.close(conn, pstmt, rs);
		}
		return null;
	}
	
	public List<DatafileVO> dataFileSelectAll() {
		List<DatafileVO> list = new ArrayList<DatafileVO>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT D_ID, FILENAME FROM DATAFILE");
		
		try {
			connect(sql.toString());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				list.add(new DatafileVO(rs.getInt("d_id"), rs.getString("filename")));
			}
			return list;
		} catch (SQLException e) {
		} finally {
			CommonJDBC.close(conn, pstmt, rs);
		}
		return null;
	}
	
	public int dataFileUpdate(DatafileVO vo) {
		int result = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE DATAFILE SET D_ID=?, FILENAME=? WHERE D_ID=?");
		
		try {
			connect(sql.toString());
			
			pstmt.setInt(1, vo.getD_id());
			pstmt.setString(2, vo.getFileName());
			pstmt.setInt(3, vo.getD_id());
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJDBC.close(conn, pstmt, rs);
		}
		return result;
	}
	
	public int dataFileDelete(DatafileVO vo) {
		int result = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM DATAFILE WHERE D_ID = ?");
		
		try {
			connect(sql.toString());
			
			pstmt.setInt(1, vo.getD_id());
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJDBC.close(conn, pstmt, rs);
		}
		return result;
	}
	
	//--------------------DataFile----------------------------
	//--------------------TEST----------------------------
	
	
	
	public int testInsert(TestVO vo) {
		int result = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO TEST VALUES (?,?,?,?)");
		
		try {
			connect(sql.toString());
			
			pstmt.setInt(1, vo.getT_id());
			pstmt.setString(2, vo.getQuest());
			pstmt.setInt(3, vo.getAnswer());
			pstmt.setString(4, vo.getGrade());
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJDBC.close(conn, pstmt, rs);
		}
		return result;
	}
	
	public TestVO testSelect(TestVO vo) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T_ID, QUEST, ANSWER, GRADE FROM TEST WHERE T_ID = ?");
		
		try {
			connect(sql.toString());
			
			pstmt.setInt(1, vo.getT_id());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo = new TestVO(rs.getInt("t_id"), rs.getString("quest"), rs.getInt("answer"), rs.getString("grade"));
			}
			return vo;
		} catch (SQLException e) {
		} finally {
			CommonJDBC.close(conn, pstmt, rs);
		}
		return null;
	}
	
	public List<TestVO> testSelectByGrade(String grade) {
		List<TestVO> list = new ArrayList<TestVO>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T_ID, QUEST, ANSWER, GRADE FROM TEST WHERE GRADE >= ?");
		
		try {
			connect(sql.toString());
			
			pstmt.setString(1, grade);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				list.add(new TestVO(rs.getInt("t_id"), rs.getString("quest"), rs.getInt("answer"), rs.getString("grade")));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJDBC.close(conn, pstmt, rs);
		}
		return null;
	}
	
	public TestVO testSelect(int id) {
		TestVO vo = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T_ID, QUEST, ANSWER, GRADE FROM TEST WHERE T_ID = ?");
		
		try {
			connect(sql.toString());
			
			pstmt.setInt(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				vo = new TestVO(rs.getInt("t_id"), rs.getString("quest"), rs.getInt("answer"), rs.getString("grade"));
			}
			return vo;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJDBC.close(conn, pstmt, rs);
		}
		return null;
	}
	
	public List<TestVO> testSelectAll() {
		List<TestVO> list = new ArrayList<TestVO>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T_ID, QUEST, ANSWER, GRADE FROM TEST");
		
		try {
			connect(sql.toString());
			
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				list.add(new TestVO(rs.getInt("t_id"), rs.getString("quest"), rs.getInt("answer"), rs.getString("grade")));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJDBC.close(conn, pstmt, rs);
		}
		return null;
	}
	
	public int testUpdate(TestVO vo) {
		int result = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE TEST SET T_ID=?, QUEST=?, ANSWER=? WHERE T_ID=?");
		
		try {
			connect(sql.toString());
			
			pstmt.setInt(1, vo.getT_id());
			pstmt.setString(2, vo.getQuest());
			pstmt.setInt(3, vo.getAnswer());
			pstmt.setInt(4, vo.getT_id());
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJDBC.close(conn, pstmt, rs);
		}
		return result;
	}
	
	public int testDelete(TestVO vo) {
		int result = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM TEST WHERE T_ID = ?");
		
		try {
			connect(sql.toString());
			
			pstmt.setInt(1, vo.getT_id());
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonJDBC.close(conn, pstmt, rs);
		}
		return result;
	}
	
	//--------------------TEST----------------------------
	//------------------score------------------------
		//---select--
		public ScoreVO scoreSelect(ScoreVO vo) {
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ID, C_SCORE, S_SCORE, GRADE FROM SCORE WHERE ID = ?");
			
			try {
				connect(sql.toString());
				
				pstmt.setString(1, vo.getId());
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					vo = new ScoreVO(rs.getString("id"), rs.getInt("c_score"), rs.getInt("s_score"), rs.getString("grade"));
				}
				return vo;
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CommonJDBC.close(conn, pstmt, rs);
			}
			return null;
		}
		
		public ScoreVO scoreSelect(String id) {
			ScoreVO vo = null;
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ID, C_SCORE, S_SCORE, GRADE FROM SCORE WHERE ID = ?");
			
			try {
				connect(sql.toString());
				
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					vo = new ScoreVO(rs.getString("id"), rs.getInt("c_score"), rs.getInt("s_score"), rs.getString("grade"));
				}
				return vo;
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CommonJDBC.close(conn, pstmt, rs);
			}
			return null;
		}
		
		public int scoreSelectS_SCORE(String id) {
			StringBuilder sql = new StringBuilder();
			int s_score = 0;
			sql.append("SELECT S_SCORE FROM SCORE WHERE ID = ?");
			
			try {
				connect(sql.toString());
				
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				if(rs.next())
					s_score = rs.getInt("s_score");
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CommonJDBC.close(conn, pstmt, rs);
			}
			return s_score;
		}
		//--insert--
		public int scoreInsert(ScoreVO vo) {
			int result = 0;
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO SCORE (ID, C_SCORE, S_SCORE, GRADE) "
					+ "VALUES (?,?,?,?)");
			
			try {
				connect(sql.toString());
				
				pstmt.setString(1, vo.getId());
				pstmt.setInt(2, vo.getC_score());
				pstmt.setInt(3, vo.getS_socre());
				pstmt.setString(4, vo.getGrade());
				
				result = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CommonJDBC.close(conn, pstmt, rs);
			}
			return result;
		}
		
		public int scoreDefault(MemberVO vo) {
			int result = 0;
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO SCORE (ID) VALUES (?)");
			
			try {
				connect(sql.toString());
				
				pstmt.setString(1, vo.getId());
				
				result = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CommonJDBC.close(conn, pstmt, rs);
			}
			return result;
		}
		//--update--
		public int scoreUpdate(ScoreVO vo) {
			int result = 0;
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE SCORE SET C_SCORE = ?, S_SCORE =? , GRADE = ?  WHERE ID = ?");
			
			try {
				connect(sql.toString());
				
				pstmt.setInt(1, vo.getC_score());
				pstmt.setInt(2, vo.getS_socre());
				pstmt.setString(3, vo.getGrade());
				pstmt.setString(4, vo.getId());
				
				result = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CommonJDBC.close(conn, pstmt, rs);
			}
			return result;
		}
		//--delete--
		public int scoreDelete(ScoreVO vo) {
			int result = 0;
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM SCORE WHERE ID = ?");
			try {
				connect(sql.toString());
				
				pstmt.setString(1, vo.getId());
				
				result = pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CommonJDBC.close(conn, pstmt, rs);
			}
			return result;
			
		}
		//-----------------------score-----------------------------
		//--------------------RankviewVO DML 시작----------------------------
		
		
		
		public List<RankviewVO> rank_viewSelectAll() {
			List<RankviewVO> list = null;
			RankviewVO vo = null;
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ID, C_SCORE, S_SCORE, COUNT FROM RANK_VIEW ORDER BY S_SCORE DESC, C_SCORE DESC");
			
			try {
				connect(sql.toString());
				
				rs = pstmt.executeQuery();
				
				list = new ArrayList<>();
				while(rs.next()){
					
					vo = new RankviewVO(rs.getString("id"), rs.getInt("c_score"), rs.getInt("s_score"), rs.getInt("count"));
					list.add(vo);
					
				}
				return list;
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CommonJDBC.close(conn, pstmt, rs);
			}
			
			return null;
		}

		
		//--------------------RankviewVO DML 끝----------------------------
		
		//--------------------TestLogVO DML 시작----------------------------
		public TestLogVO testLogSelect(TestLogVO vo) {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ID, T_ID, OX, COUNT FROM TESTLOG WHERE ID=? AND T_ID = ?");
			
			try {
				connect(sql.toString());
				
				pstmt.setString(1, vo.getId());
				pstmt.setInt(2, vo.getT_id());
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					vo = new TestLogVO(rs.getString("id"), rs.getInt("t_id"), rs.getString("ox"), rs.getInt("count"));
				}
				return vo;
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CommonJDBC.close(conn, pstmt, rs);
			}
			return null;
		}
		
		public TestLogVO testLogSelect(String id, int t_id) {
			TestLogVO vo = null;
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ID, T_ID, OX, COUNT FROM TESTLOG WHERE ID=? AND T_ID = ?");
			
			try {
				connect(sql.toString());
				
				pstmt.setString(1, id);
				pstmt.setInt(2, t_id);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					vo = new TestLogVO(rs.getString("id"), rs.getInt("t_id"), rs.getString("ox"), rs.getInt("count"));
				}
				return vo;
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CommonJDBC.close(conn, pstmt, rs);
			}
			return null;
		}
		
		public int testLogSelectMaxCount(String id) {
			StringBuilder sql = new StringBuilder();
			int maxCount = 0;
			sql.append("SELECT MAX(COUNT) FROM TESTLOG WHERE ID=?");
			
			try {
				connect(sql.toString());
				
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				if(rs.next())
					maxCount = rs.getInt("MAX(COUNT)");
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CommonJDBC.close(conn, pstmt, rs);
			}
			return maxCount;
		}
		
		public List<Integer> testLogSelectIncorrect(String id) {
			List<Integer> list = new ArrayList<Integer>();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT T_ID FROM TESTLOG T "
					+ "WHERE "
					+ "ID = ? "
					+ "AND OX='X' "
					+ "AND COUNT = (SELECT MAX(COUNT) FROM TESTLOG WHERE ID=T.ID AND T_ID = T.T_ID GROUP BY T_ID)");
			
			try {
				connect(sql.toString());
				
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				
				while(rs.next())
					list.add(rs.getInt("t_id"));
				
				return list;
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CommonJDBC.close(conn, pstmt, rs);
			}
			return null;
		}
		
		public int testLogInsert(TestLogVO vo) {
			int result = 0;
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO TESTLOG VALUES(?, ?, ?, ?)");
			
			try {
				connect(sql.toString());
				
				pstmt.setString(1, vo.getId());
				pstmt.setInt(2, vo.getT_id());
				pstmt.setString(3, vo.getOx());
				pstmt.setInt(4, vo.getCount());
				
				result = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CommonJDBC.close(conn, pstmt, rs);
			}
			return result;
		}
		
		public int testLogUpdate(TestLogVO vo) {
			int result = 0;
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE TESTLOG SET T_ID = ?, ID = ?, OX = ?, COUNT = ? WHERE ID = ? AND T_ID = ?");
			
			try {
				connect(sql.toString());
				
				pstmt.setInt(1, vo.getT_id());
				pstmt.setString(2, vo.getId());
				pstmt.setString(3, vo.getOx());
				pstmt.setInt(4, vo.getCount());
				pstmt.setString(5, vo.getId());
				pstmt.setInt(6, vo.getT_id());
				
				result = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CommonJDBC.close(conn, pstmt, rs);
			}
			
			return result;
		}
		
		public int testLogDelete(TestLogVO vo) {
			int result = 0;
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM TESTLOG WHERE ID = ? AND T_ID = ? ");
			try {
				connect(sql.toString());
				
				pstmt.setString(1, vo.getId());
				pstmt.setInt(2, vo.getT_id());
				
				result = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		}

		//--------------------TestLogVO DML 끝----------------------------

	
	private void connect(String sql) throws SQLException {
		conn = DriverManager.getConnection(CommonJDBC.URL, CommonJDBC.USER, CommonJDBC.PW);
		pstmt = conn.prepareStatement(sql);
	}
	
	
}
