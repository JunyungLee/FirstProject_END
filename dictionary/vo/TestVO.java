package dictionary.vo;

public class TestVO {

	private int t_id;
	private String quest;
	private int answer;
	private String grade;
	
	
	public TestVO() {
		super();
	}

	public TestVO(int t_id, String quest, int answer, String grade) {
		super();
		this.t_id = t_id;
		this.quest = quest;
		this.answer = answer;
		this.grade = grade;
	}

	public int getT_id() {
		return t_id;
	}

	public void setT_id(int t_id) {
		this.t_id = t_id;
	}

	public String getQuest() {
		return quest;
	}

	public void setQuest(String quest) {
		this.quest = quest;
	}

	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}

	
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "문제 번호 : " + t_id + " 문제 내용 : " + quest + " 정답 : " + answer;
	}
	
	
	
}
