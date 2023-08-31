package dictionary.vo;

public class DatafileVO {

	private int d_id;
	private String fileName;
	
	
	public DatafileVO() {
		super();
	}

	public DatafileVO(int d_id, String fileName) {
		super();
		this.d_id = d_id;
		this.fileName = fileName;
	}

	public int getD_id() {
		return d_id;
	}

	public void setD_id(int d_id) {
		this.d_id = d_id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "사전 번호 : " + d_id + " 파일 이름 : " + fileName;
	}
	
	
}
