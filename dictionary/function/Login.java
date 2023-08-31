package dictionary.function;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import dictionary.dao.DictionaryDAO;
import dictionary.vo.MemberVO;

public class Login {
	DictionaryDAO dao = new DictionaryDAO();
	BufferedReader br = null;
	MemberVO member = null;
	
	public String login() {
		br = new BufferedReader(new InputStreamReader(System.in));
		String id = null;
		String password = null;
		
		try {
			System.out.println("<로그인>");
			System.out.print("아이디를 입력해주세요. : ");
			id = br.readLine();
			System.out.print("비밀번호를 입력해주세요. : ");
			password = br.readLine();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		MemberVO result = dao.memberSelect(id);
		if(result == null) {
			System.out.println("\n존재하지 않는 아이디입니다.");
			return null;
		} else if(result.getId().equals(id) && result.getPassword().equals(password)) {
			System.out.println("\n로그인에 성공하였습니다.");
			return id;
		} else {
			System.out.println("\n아이디 와 비밀번호가 일치하지 않습니다.");
			return null;
		}
	}		
}
	
	


