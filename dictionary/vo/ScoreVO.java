package dictionary.vo;

public class ScoreVO {

	private String id;
	private int c_score;
	private int s_score;
	private String grade;
	
	
	public ScoreVO() {
		super();
	}

	public ScoreVO(String id, int c_score, int s_score, String grade) {
		super();
		this.id = id;
		this.c_score = c_score;
		this.s_score = s_score;
		this.grade = grade;
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

	public int getS_socre() {
		return s_score;
	}

	public void setS_socre(int s_score) {
		this.s_score = s_score;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "회원 아이디 : " + id + " 마지막 시험 점수 : " + c_score + " 누적 점수 : " + s_score + " 최종등급 : " + grade;
	}
	
	
	
}
