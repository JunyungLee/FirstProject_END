package dictionary.function;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import dictionary.dao.DictionaryDAO;
import dictionary.dao.secret.SecretCodeGenerator;
import dictionary.vo.MemberVO;
import dictionary.vo.ScoreVO;
import dictionary.vo.TestVO;

public class ShowInfo {
	DictionaryDAO dao = new DictionaryDAO();
	BufferedReader br = null;
	MemberVO member = null;
	ScoreVO score = null;
	SecretCodeGenerator scg = new SecretCodeGenerator();
	
	public void showInfo(String id) {
		MemberVO result = dao.memberSelect(id);
		ScoreVO grade = dao.scoreSelect(id);
		
		System.out.println();
		System.out.println("\t                  <MY PROFILE>");
		System.out.println("\t\t------------------------------------");
		System.out.println("\t\t\tNAME  : " + result.getName());
		System.out.println("\t\t\tPHONE : " + processPhone(result.getPhone()));
		System.out.println("\t\t\tID    : " + result.getId());
		System.out.println("\t\t\tGRADE : " + grade.getGrade());
	}

	private String processPhone(String phone) {
		String[] arr = phone.split("");
		StringBuilder result = new StringBuilder();
		
		for(int i = 0; i < 11; i++) {
			result.append(arr[i]);
			if(i==2 || i==6)
				result.append("-");
		}
		
		return result.toString();
	}
	
	public boolean changePw(String id) {
		br = new BufferedReader(new InputStreamReader(System.in));
		String password = null;
		String changepw = null;
		String passwordcheck = null;

		while (true) {
			System.out.println("\n<비밀번호 변경>");
			try {
				System.out.print("현재 비밀번호를 입력해주세요. : ");
				password = br.readLine();

				MemberVO result = dao.memberSelect(id);
				if (result.getPassword().equals(password)) {
					System.out.print("변경할 비밀번호 입력 : ");
					changepw = br.readLine();
					System.out.print("변경할 비밀번호 확인 : ");
					passwordcheck = br.readLine();
					if (changepw.equals(passwordcheck)) {
						if (password.equals(changepw)) {
							System.out.println("\n현재 비밀번호와 비밀번호가 일치합니다.");
							continue;
						}
						result.setPassword(changepw);
						int check = dao.memberUpdate(result);
						if (check == 1) {
							System.out.println("\n\n\n비밀번호가 변경되었습니다.\n\n");
							return true;
						} else
							System.out.println("\n\n\n비밀번호 변경에 실패했습니다.\n\n");
					} else {
						System.out.println("\n확인한 비밀번호와 일치하지 않습니다.");
					}

				} else {
					System.out.println("\n현재 비밀번호가 일치하지 않습니다.");
				}
			} catch (IOException e) {
				System.out.println("[경고] - 오류 발생");
			}
		}
	}
		
	public boolean chageName(String id) {
		br = new BufferedReader(new InputStreamReader(System.in));
		String password = null;
		String newName = null;
		String nameCheck = null;
		
		while (true) {
			System.out.println("\n<이름 변경>");
			try {
				System.out.print("현재 비밀번호를 입력해주세요. : ");
				password = br.readLine();

				MemberVO result = dao.memberSelect(id);
				if (result.getPassword().equals(password)) {
					System.out.print("변경할 이름 입력 : ");
					while(true) {
						newName = br.readLine();
						if(newName.length() < 2 || newName.length() > 6) {
							System.out.println("2 - 6자리 이름만 사용 가능합니다.");
							System.out.println("다시 입력해주세요. : ");
							continue;
						}else {
							break;
						}
					}
					System.out.print("변경할 이름 확인 : ");
					nameCheck = br.readLine();
					if (newName.equals(nameCheck)) {
						if (dao.memberSelect(id).getName().equals(newName)) {
							System.out.println("\n현재 이름과 변경할 이름이 일치합니다.");
							continue;
						}
						result.setName(newName);
						int check = dao.memberUpdate(result);
						if (check == 1) {
							System.out.println("\n\n\n이름이 변경되었습니다.\n\n");
							return false;
						} else
							System.out.println("\n\n\n이름 변경에 실패했습니다.\n\n");
					} else {
						System.out.println("\n확인한 이름과 일치하지 않습니다.");
					}

				} else {
					System.out.println("\n현재 비밀번호가 일치하지 않습니다.");
				}
			} catch (IOException e) {
				System.out.println("[경고] - 오류 발생");
			}
		}
	}

	public boolean chagePhone(String id) {
		br = new BufferedReader(new InputStreamReader(System.in));
		String password = null;
		String newPhone = null;
		String phoneCheck = null;
		
		while (true) {
			System.out.println("\n<번호 변경>");
			try {
				System.out.print("현재 비밀번호를 입력해주세요. : ");
				password = br.readLine();

				MemberVO result = dao.memberSelect(id);
				if (result.getPassword().equals(password)) {
					System.out.print("변경할 번호 입력 : ");
					while(true) {
						newPhone = br.readLine();
						if(newPhone.length() != 11) {
							System.out.print("11자리의 정확한 번호를 입력해주세요 : ");
							continue;
						}else {
							break;
						}
					}
					System.out.print("변경할 번호 확인 : ");
					phoneCheck = br.readLine();
					if (newPhone.equals(phoneCheck)) {
						if (dao.memberSelect(id).getName().equals(newPhone)) {
							System.out.println("\n현재 번호와 변경할 번호가 일치합니다.");
							continue;
						}
						result.setPhone(newPhone);
						int check = dao.memberUpdate(result);
						if (check == 1) {
							System.out.println("\n\n\n번호가 변경되었습니다.\n\n");
							return false;
						} else
							System.out.println("\n\n\n번호 변경에 실패했습니다.\n\n");
					} else {
						System.out.println("\n확인한 번호와 일치하지 않습니다.");
					}

				} else {
					System.out.println("\n현재 비밀번호가 일치하지 않습니다.");
				}
			} catch (IOException e) {
				System.out.println("[경고] - 오류 발생");
			}
		}
	}
	
	public boolean unregister(String id) {
		br = new BufferedReader(new InputStreamReader(System.in));
		String password = null;
		String answer = null;

		while(true) {					
			System.out.println("\n<회원탈퇴>");
		try {
			System.out.print("현재 비밀번호를 입력해주세요. : ");
			password = br.readLine();

		} catch (IOException e) {
			System.out.println("[경고] - 오류 발생");
		}

		MemberVO result = dao.memberSelect(id);
		
		if (result.getPassword().equals(password)) {
			while(true) {
				try {
					System.out.print("정말 탈퇴하시겠습니까? (Y/N) : ");
					answer = br.readLine();
					if (answer.equalsIgnoreCase("y")) {
						dao.memberDelete(result);
						System.out.println("\n\n\n탈퇴되었습니다.\n\n");
						return true;
					} else if (answer.equalsIgnoreCase("n")) {
						System.out.println("\n\n\n회원 탈퇴를 취소합니다.\n\n");
						return false;
					} else {
						System.out.println("Y or N 중에 입력해주세요");
						continue;
					}
					
				} catch (IOException e) {
					System.out.println("[경고] - 오류 발생");
				}
			}
		} else {
			System.out.println("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
			continue;
		}
	}
}
	
	public void review(String id) {
		List<Integer> list = dao.testLogSelectIncorrect(id);
		System.out.println("================================================================");
		System.out.println("                         오 답 노 트                            ");
		System.out.println("================================================================");
		if(list.size() == 0) {
			System.out.println("\n틀린 문제가 없습니다.\n");
		}
		
		int count = 1;
		for(int t_id : list) {
			TestVO vo = dao.testSelect(t_id);
			int answer = vo.getAnswer();
			
			System.out.println("("+ count++ +")");
			scg.decodeFile(vo.getQuest());
			System.out.println("<정답> : " + answer+"\n");
			
		}
	}
}
