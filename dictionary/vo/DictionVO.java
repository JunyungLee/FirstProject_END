package dictionary.vo;

public class DictionVO implements Comparable<DictionVO> {

	private int d_id;
	private String keyword;
	private String grade;
	
	
	@Override
	public int compareTo(DictionVO o) {
		if(this.grade.charAt(0) > o.grade.charAt(0))
			return 1;
		else if(this.grade.charAt(0) == o.grade.charAt(0))
			return 0;
		else if(this.grade.charAt(0) < o.grade.charAt(0))
			return -1;
		return 0;
	}
	
	public DictionVO() {
		super();
	}

	public DictionVO(int d_id, String keyword, String grade) {
		super();
		this.d_id = d_id;
		this.keyword = keyword;
		this.grade = grade;
	}

	public int getD_id() {
		return d_id;
	}

	public void setD_id(int d_id) {
		this.d_id = d_id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "사전 등록번호 : " + d_id + " 검색 키워드 : " + keyword + " 열람등급 : " + grade;
	}
	
	
	
}
