package dictionary.vo;

public class TestLogVO {

	private int t_id;
	private String id;
	private String ox;
	private int count;
	
	
	public TestLogVO() {
		super();
	}

	public TestLogVO(String id, int t_id,String ox, int count) {
		super();
		this.t_id = t_id;
		this.id = id;
		this.ox = ox;
		this.count = count;
	}

	public int getT_id() {
		return t_id;
	}

	public void setT_id(int t_id) {
		this.t_id = t_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOx() {
		return ox;
	}

	public void setOx(String ox) {
		this.ox = ox;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "문제 번호 : " + t_id + " 회원 아이디 : " + id + " 정답 유무 : " + ox
					+ " 시험 응시 횟수 : " + count;
	}
	
	
}
