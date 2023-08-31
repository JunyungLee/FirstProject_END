package dictionary.function;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import dictionary.dao.DictionaryDAO;
import dictionary.vo.MemberVO;

public class FindIDPW {
	private DictionaryDAO dao = null;
	private BufferedReader br = null;
	
	// 이 메소드 끝나고 main 에서 일시정지 필요
	public boolean findID() {
		dao = new DictionaryDAO();
		br = new BufferedReader(new InputStreamReader(System.in));
		List<MemberVO> list = null;
		
		String name = null;
		String phone = null;
		String resultID = null;
		
		
		
		// 이름 입력 받기
		System.out.print("회원가입 때 사용했던 이름을 입력해 주세요. : ");
		while (true) {
			try {
				name = br.readLine();
				
				if(name.length() < 2 || name.length() > 6) {
					System.out.println("이름은 2 ~ 6 자리 입니다.");
					System.out.print("다시 입력해 주세요. : ");
					continue;
				} else {
					list = dao.memberSelectByName(name);
					if(list == null) {
						System.out.println("존재하지 않는 이름입니다.");
						return false;
					} else break;
				}
			} catch (IOException e) {
				System.out.println("[경고] - 오류 발생");
			}
		}
		
		// 폰번호 입력 받기
		System.out.print("회원가입 때 사용했던 휴대폰번호 을 입력해 주세요. ('-' 제외) : ");
		while(true) {
			try {
				phone = br.readLine();

				if (!checkPhone(phone)) {
					System.out.println("정확한 번호를 입력해 주세요.");
					System.out.print("다시 입력해 주세요. : ");
					continue;
				} else {
					for(MemberVO temp : list) {
						if(phone.equals(temp.getPhone())) {
							resultID = temp.getId();
							break;
						} 
					}
					if (resultID == null) {
						System.out.println("입력하신 정보와 일치하는 아이디가 없습니다.");
						return false;
					} else break;
				}
			} catch (IOException e) {
				System.out.println("[경고] - 오류 발생");
			}
		}
		
		System.out.println("\n"+name + " 님의 아이디는   " + resultID + "   입니다.");
		return true;
	}
	
	
	
	public boolean findPW() {
		dao = new DictionaryDAO();
		br = new BufferedReader(new InputStreamReader(System.in));
		MemberVO vo = null;
		
		String id = null;
		String name = null;
		String phone = null;
		
		// 아이디 입력 받기
		System.out.print("비밀번호를 찾을 아이디를 입력해 주세요. : ");
		while (true) {
			try {
				id = br.readLine();

				if (!check(id, 3, 10)) {
					System.out.println("아이디는 3 ~ 10 자리의 영어, 숫자로 이루어진 문자열 입니다.");
					System.out.print("다시 입력해 주세요. : ");
					continue;
				} else {
					vo = dao.memberSelect(id);
					if(vo == null) {
						System.out.println("ID가 존재하지 않습니다.");
						return false;
					} else break;
				}
			} catch (IOException e) {
				System.out.println("[경고] - 오류 발생");
			}
		}
		
		// 이름 입력 받기
		System.out.print("회원가입 때 사용했던 이름을 입력해 주세요. : ");
		while (true) {
			try {
				name = br.readLine();
				
				if(name.length() < 2 || name.length() > 6) {
					System.out.println("이름은 2 ~ 6 자리 입니다.");
					System.out.print("다시 입력해 주세요. : ");
					continue;
				} else break;
			} catch (IOException e) {
				System.out.println("[경고] - 오류 발생");
			}
		}
		
		// 폰번호 입력 받기
		System.out.print("회원가입 때 사용했던 휴대폰번호 을 입력해 주세요. ('-' 제외) : ");
		while(true) {
			try {
				phone = br.readLine();

				if (!checkPhone(phone)) {
					System.out.println("정확한 번호를 입력해 주세요.");
					System.out.print("다시 입력해 주세요. : ");
					continue;
				} else break;
			} catch (IOException e) {
				System.out.println("[경고] - 오류 발생");
			}
		}
		
		if(vo.getName().equals(name) && vo.getPhone().equals(phone)) {
			System.out.println("\n"+name + " 님의 비밀번호는   " + vo.getPassword() + "   입니다.");
			return true;
		} else {
			System.out.println("입력하신 정보와 아이디의 정보가 일치하지 않습니다.");
			return false;
		}
		
		
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
