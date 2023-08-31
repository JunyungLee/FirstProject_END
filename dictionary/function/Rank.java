package dictionary.function;

import java.util.ArrayList;
import java.util.List;

import dictionary.dao.DictionaryDAO;
import dictionary.vo.RankviewVO;

public class Rank {
	
	public void viewRank(String id) {
		List<RankviewVO> list = new ArrayList<>();
		DictionaryDAO dao = new DictionaryDAO();
		
		list = dao.rank_viewSelectAll();
		
		System.out.println(
				  "=========================================================================\r\n"
				+ "   순위\t아이디\t\t최근 시험점수\t누적점수\t\t응시 횟수   \r\n"
				+ "=========================================================================");
		int i = 1;
		for(RankviewVO v : list) {
			boolean isMe = v.getId().equals(id);
			
			System.out.print("  ");
			if(isMe) System.out.print("★");
			else System.out.print(" ");
			
			System.out.print(i++ + "위\t" + v.getId() + "\t\t " + v.getC_score() + "\t\t" + v.getS_score() + "\t\t "   + v.getCount());
			
			System.out.println();
		}
		
	}
}
