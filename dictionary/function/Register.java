package dictionary.function;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import dictionary.dao.DictionaryDAO;
import dictionary.vo.MemberVO;

public class Register {
	DictionaryDAO dao = new DictionaryDAO();
	BufferedReader br = null;
	MemberVO member = null;
	
	public boolean register() {
		br = new BufferedReader(new InputStreamReader(System.in));
		String id = null;
		String password = null;
		String name = null;
		String phone = null;
		
		
		try {
			// 아이디
			System.out.println("<회원가입>");
			System.out.print("아이디를 입력해 주세요. : ");
			
			while(true) {
			id = br.readLine();
			
			if(!check(id, 3, 10)) {
				System.out.println("3 ~ 10 자리의 영어, 숫자로 이루어진 아이디만 사용 가능합니다.");
				System.out.print("다시 입력해 주세요. : ");
				continue;
			}
			
			if(dao.memberSelect(id) == null)  // 사용 가능 아이디
				break;
			
			System.out.println("이미 사용중인 아이디 입니다.");
			System.out.print("다시 입력해 주세요. : ");
			
			}
			
			// 비밀번호
			// while, 비밀번호 확인
			System.out.print("비밀번호를 입력해 주세요. : ");
			while (true) {
				password = br.readLine();
				
				if(!check(password, 3, 10)) {
					System.out.println("3 ~ 10 자리의 영어, 숫자로 이루어진 비밀번호만 사용 가능합니다.");
					System.out.print("다시 입력해 주세요. : ");
					continue;
				}
				else
					break;
			}
			
			// 이름
			System.out.print("본인의 이름을 입력해 주세요. : ");
			while (true) {
				name = br.readLine();
				
				if(name.length() > 6 || name.length() < 2) {
					System.out.println("2 ~ 6 자리의 이름만 사용 가능합니다.");
					System.out.print("다시 입력해 주세요. : ");
					continue;
				}
				else
					break;
			}
			
			// 폰 번호
			System.out.println("휴대폰 번호는 나중에 아이디, 비밀번호 찾기 시에 사용됩니다.");
			System.out.print("본인의 휴대푠 번호를 입력해 주세요. (- 제외하고 입력) [ex) 01011112222] : ");
			while (true) {
				phone = br.readLine();
				
				if(!checkPhone(phone)) {
					System.out.println("총 11자리의 휴대폰 번호를 입력해 주세요.");
					System.out.print("다시 입력해 주세요. : ");
					continue;
				}
				else {
					if(dao.memberSelectByPhone(phone) == null)
						break;
					else {
						System.out.println("이미 존재하는 휴대폰 번호 입니다.");
						System.out.print("다시 입력해 주세요. : ");
						continue;
					}
				}
			}
			
			
			//DB 저장
			member = new MemberVO(id, name, password, phone);
			int result = dao.memberInsert(member);
			dao.scoreDefault(member);
			if(result == 1) {
				System.out.println("\n회원가입 성공.");
				return true;
			}
			else {
				System.out.println("\n회원가입 실패.. 메인 화면으로 돌아갑니다.");
			}
			
		} catch (IOException e) {
			System.out.println("[경고] - 오류 발생");
		} 
		return false;

	}
	
	private boolean check(String input, int min, int max) {
		char[] charArr = input.toCharArray();
		
		for(char c : charArr) {
			if(!(c >= 'a' && c <= 'z') && !(c >= 'A' && c <= 'Z') && !(c >= '0' && c <= '9'))
				return false;
		}
		
		if(input.length() > max || input.length() < min) 
			return false;
		
		return true;
	}
	
	private boolean checkPhone(String input) {
		char[] charArr = input.toCharArray();
		
		for(char c : charArr) {
			if(!(c >= '0' && c <= '9'))
				return false;
		}
		
		if(input.length() != 11) 
			return false;
		
		return true;
	}

}
