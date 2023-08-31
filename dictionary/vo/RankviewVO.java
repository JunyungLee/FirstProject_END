package dictionary.vo;

public class RankviewVO {

	private String id;
	private int c_score;
	private int s_score;
	private int count;
	
	
	public RankviewVO() {
		super();
	}

	public RankviewVO(String id) {
		super();
		this.id = id;
	}
	
	public RankviewVO(String id, int c_score, int s_score, int count) {
		super();
		this.id = id;
		this.c_score = c_score;
		this.s_score = s_score;
		this.count = count;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getC_score() {
		return c_score;
	}

	public void setC_score(int c_score) {
		this.c_score = c_score;
	}

	public int getS_score() {
		return s_score;
	}

	public void setS_score(int s_score) {
		this.s_score = s_score;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "멤버 아이디 : " + id + " 마지막 시험점수 : " + c_score + " 누적점수 : " + s_score
					+ "시험 응시 횟수 : " + count;
	}
	
	
}
