package dictionary.function;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import dictionary.dao.DictionaryDAO;
import dictionary.dao.secret.SecretCodeGenerator;
import dictionary.etc.HangMan;
import dictionary.etc.LoadingFont;
import dictionary.vo.DictionVO;

public class DictionaryMain {
	static Rank rank = new Rank();
	static Test test = new Test();
	static Login login = new Login();
	static Search search = new Search();
	static Register reg = new Register();
	static ShowInfo info = new ShowInfo();
	static HangMan hangMan = new HangMan();
	static FindIDPW find = new FindIDPW();
	static DictionaryDAO dao = new DictionaryDAO();
	static SearchDictionList sdl = new SearchDictionList();
	static SecretCodeGenerator scg = new SecretCodeGenerator();
	
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	static boolean changeSuccess = false; // 회원정보 변경을 위한 boolean
	static int secretNum = 99; //미니게임 실행을 위한 변수
	
	public static void main(String[] args) {
		String userID = null;
		int choiceNum;
		
		//printLoading();	//로딩
		while (true) {
			// 첫번째 메인
			while (true) {
				changeSuccess = false;
				// 회원가입, 로그인 선택 창 출력
				printFirstMain();
				// 선택값 입력받기
				choiceNum = getNum(5);

				// 명령 처리
				switch (choiceNum) {
				case 1: // 로그인
					if ((userID = login.login()) != null) {
						getAnyKey();
						break;
					}else {
						getAnyKey();
						continue;
					}
				case 2: // 회원가입
					reg.register();
					getAnyKey();
					continue;
				case 3: // 아이디 찾기
					findID();
					continue;
				case 4: // 비밀번호 찾기
					findPW();
					continue;
				case 5: // 종료
					boolean exit = checkExit(); // 종료하는지 재확인
					if (exit) // 종료
						return;
					else // 다시 첫 화면 띄움
						continue;
				}
				
				// 로그인 성공
				break;
			}

			while (true) {
				if (changeSuccess)
					break;
				// 메인화면 출력
				printMain(userID);
				// 선택값 입력받기
				choiceNum = -1;
				choiceNum = getNumMain(6);

				// 명령 처리
				switch (choiceNum) {
				case 1: // 검색
					printSearch(userID);
					break;
				case 2: // 전체 목록
					searchAll(userID);
					break;
				case 3: // 시험보기
					startTest(userID);
					break;
				case 4: // 내정보
					InfoMain(userID);
					break;
				case 5: // 랭킹
					printRank(userID);
					break;
				case 6: // 종료
					boolean exit = checkExit(); // 종료하는지 재확인
					if (exit) // 종료
						return;
					else // 다시 첫 화면 띄움
						continue;
				case 99:
					hiddenGame();
					break;
				}

			}
			continue;
		}
	
	}
	
	// 0. 메인화면------------------------
	private static void printFirstMain() {
		clear();
		System.out.println(
				  "     ███╗   ███╗██╗███╗   ██╗██╗     ██████╗ ██████╗ ████████╗\r\n"
				+ "     ████╗ ████║██║████╗  ██║██║    ██╔════╝ ██╔══██╗╚══██╔══╝\r\n"
				+ "     ██╔████╔██║██║██╔██╗ ██║██║    ██║  ███╗██████╔╝   ██║   \r\n"
				+ "     ██║╚██╔╝██║██║██║╚██╗██║██║    ██║   ██║██╔═══╝    ██║   \r\n"
				+ "     ██║ ╚═╝ ██║██║██║ ╚████║██║    ╚██████╔╝██║        ██║   \r\n"
				+ "     ╚═╝     ╚═╝╚═╝╚═╝  ╚═══╝╚═╝     ╚═════╝ ╚═╝        ╚═╝   \r\n"
				+ "                                                              \r\n");
		System.out.println(
				  "==================================================================\r\n"
				+ "  1. 로그인      2. 회원가입      3. 아이디찾기     4. 비밀번호찾기    5.종료\r\n"
				+ "==================================================================");
	}
	
	
	// 0. 로그인 후 메인화면
	private static void printMain(String userID) {
		clear();
		System.out.println(
						  "     ███╗   ███╗██╗███╗   ██╗██╗     ██████╗ ██████╗ ████████╗\r\n"
						+ "     ████╗ ████║██║████╗  ██║██║    ██╔════╝ ██╔══██╗╚══██╔══╝\r\n"
						+ "     ██╔████╔██║██║██╔██╗ ██║██║    ██║  ███╗██████╔╝   ██║   \r\n"
						+ "     ██║╚██╔╝██║██║██║╚██╗██║██║    ██║   ██║██╔═══╝    ██║   \r\n"
						+ "     ██║ ╚═╝ ██║██║██║ ╚████║██║    ╚██████╔╝██║        ██║   \r\n"
						+ "     ╚═╝     ╚═╝╚═╝╚═╝  ╚═══╝╚═╝     ╚═════╝ ╚═╝        ╚═╝   \r\n"
						+ "                                                              ");
		System.out.println("사용자 : " + userID);
		System.out.println(
				"=========================================================================\r\n"
						+ "    1. 검색   2. 전체 목록   3. 시험보기   4. 내정보    5. 랭킹   6. 종료\r\n"
						+ "=========================================================================");
	}
	
	// 0-1. 아이디/비밀번호 찾기
	private static void findID() {
		find.findID();
		getAnyKey();
	}
	
	private static void findPW() {
		find.findPW();
		getAnyKey();
	}
	
	// 0-2 히든 게임
		private static void hiddenGame() {
			System.out.println("\n\n\n축하합니다. 숨겨진 게임을 실행합니다.\n\n");
			tSleep(1500);
			hangMan.startGame();
			getAnyKey();
		}
	
	// 1. 검색----------------------------------
	private static void printSearch(String id) {
		clear();
		System.out.println("\n\n");
		System.out.println(
						  "     ███████╗███████╗ █████╗ ██████╗  ██████╗██╗  ██╗\r\n"
						+ "     ██╔════╝██╔════╝██╔══██╗██╔══██╗██╔════╝██║  ██║\r\n"
						+ "     ███████╗█████╗  ███████║██████╔╝██║     ███████║\r\n"
						+ "     ╚════██║██╔══╝  ██╔══██║██╔══██╗██║     ██╔══██║\r\n"
						+ "     ███████║███████╗██║  ██║██║  ██║╚██████╗██║  ██║\r\n"
						+ "     ╚══════╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝\r\n"
						+ "                                                ");
		System.out.println("\n\n");
		try {
			String path = search.keySearch(id);
			if(path == null) {
				return;
			}
			System.out.println("================================================================");
			scg.decodeFile(path);
			System.out.println("================================================================");
			getAnyKey();
		} catch (IOException e) {
			System.out.println("[경고] - 오류 발생");
		}
	}
	
	// 2. 전체 목록---------------------------
	private static void searchAll(String id) {
		List<DictionVO> list = sdl.getList(id); // 사전 리스트 불러오기
		int maxIndex = list.size(); // 검색 가능한 마지막 숫자

		while (true) {
			clear();
			System.out.println(
					  "                    ██╗     ██╗███████╗████████╗\r\n"
					+ "                    ██║     ██║██╔════╝╚══██╔══╝\r\n"
					+ "                    ██║     ██║███████╗   ██║   \r\n"
					+ "                    ██║     ██║╚════██║   ██║   \r\n"
					+ "                    ███████╗██║███████║   ██║   \r\n"
					+ "                    ╚══════╝╚═╝╚══════╝   ╚═╝   \r\n"
					+ "                            ");
			System.out.println("나의 등급 : " + dao.scoreSelect(id).getGrade());
			System.out.println("================================================================");
			int num = 1;
			for (DictionVO v : list) {
				System.out.println(num++ + ". " + "<" + v.getGrade() + ">" + " 제목: " + v.getKeyword());
			}

			System.out.println(
					  "==================================================================\r\n"
					+ "                   1. 선택                  2. 나가기                \r\n"
					+ "==================================================================");

			int choice = getNum(2);

			switch (choice) {
			case 1: // 선택
				boolean run = true;
				while (run) { // 읽을 수 있는거 선택 할 때 까지 계속 받기
					System.out.print("원하는 항목의 번호를 입력해 주세요.\n");
					int index = getNum(maxIndex);
					if (sdl.showDiction(list, id, index)) {
						getAnyKey();

						run = false;
					}
				}
				break;
				
			case 2: // 나가기
				return;
			}
		}
		
	}
	
	// 3. 시험 보기 --------------------------
		private static void startTest(String id) {
			clear();
			System.out.println(
					  "     ████████╗███████╗███████╗████████╗\r\n"
					+ "     ╚══██╔══╝██╔════╝██╔════╝╚══██╔══╝\r\n"
					+ "        ██║   █████╗  ███████╗   ██║   \r\n"
					+ "        ██║   ██╔══╝  ╚════██║   ██║   \r\n"
					+ "        ██║   ███████╗███████║   ██║   \r\n"
					+ "        ╚═╝   ╚══════╝╚══════╝   ╚═╝   \r\n"
					+ "                                  ");
			int score = test.start(id);
			String grade = null;
			
			switch (score) {
			case 100:
				grade = "A";
				break;
			case 90:
				grade = "A";
				break;
			case 80:
				grade = "B";
				break;
			case 70:
				grade = "C";
				break;
			case 60:
				grade = "D";
				break;
			default:
				grade = "F";
				break;

			}
			System.out.println("\n\n\n" + score + "점 입니다!\n\n\n");
			System.out.println("현 등급에서는 \"" + grade + "\" 등급 까지의 문서를 열람할 수 있습니다.\n");
			getAnyKey();
		}
		
	// 4. 회원 정보 관리----------------------
	private static void InfoMain(String id) {
		boolean run = true;
		while(run) {
		printInfo(id);
			
		run = choiceInfo(id);
			
		if(changeSuccess) break;
		}
	}
	
	private static void printInfo(String id) {	// 정보 메인 화면
		clear();
		System.out.println(""
					+ "\t\t   ██╗███╗   ██╗███████╗ ██████╗ \r\n"
					+ "\t\t   ██║████╗  ██║██╔════╝██╔═══██╗\r\n"
					+ "\t\t   ██║██╔██╗ ██║█████╗  ██║   ██║\r\n"
					+ "\t\t   ██║██║╚██╗██║██╔══╝  ██║   ██║\r\n"
					+ "\t\t   ██║██║ ╚████║██║     ╚██████╔╝");
		System.out.println();

			
		info.showInfo(id);
		System.out.println();
		System.out.println(
					  "\t==============================================================\r\n"
					+ "   	1. 오답노트   2. 개인정보 변경 3. 회원탈퇴  4. 돌아가기 \r\n"
					+ "\t==============================================================");

	}
		
	private static boolean choiceInfo(String id) {	// 내정보 첫 화면에서 선택
		int choiceNum;
		choiceNum = getNum(4);
		
		switch(choiceNum) {
		case 1 : // 오답노트
			clear();
			info.review(id);
			getAnyKey();
			break;
		case 2 : // 개인정보 변경
			changeInfo(id);
			break;
		case 3 : // 회원탈퇴
			printUnregisterLoop(id);
			break;
		case 4 : // 돌아가기
			return false;
		}
		return true;
	}
	
	private static void printchangeInfo(String id) {	// 개인정보 변경 화면
		clear();
		System.out.println(""
					+ "\t\t   ██╗███╗   ██╗███████╗ ██████╗ \r\n"
					+ "\t\t   ██║████╗  ██║██╔════╝██╔═══██╗\r\n"
					+ "\t\t   ██║██╔██╗ ██║█████╗  ██║   ██║\r\n"
					+ "\t\t   ██║██║╚██╗██║██╔══╝  ██║   ██║\r\n"
					+ "\t\t   ██║██║ ╚████║██║     ╚██████╔╝");
		System.out.println();

			
		info.showInfo(id);
		System.out.println();
		System.out.println(
					  "\t==============================================================\r\n"
					+ "   	1. 비밀번호 변경   2. 이름 변경 3. 번호 변경  4. 돌아가기 \r\n"
					+ "\t==============================================================");

	}
		
	private static boolean changeInfo(String id) { // 내정보에서 개인정보 변경시 선택
		printchangeInfo(id);
		int choiceNum;
		choiceNum = getNum(4);
			
		switch(choiceNum) {
		case 1 : // 비밀번호 변경
			printChangePwLoop(id);
			break;
		case 2 : // 이름변경
			printChangeNameLoop(id);;
			break;
		case 3 : // 번호변경
			printChangePhoneLoop(id);
			break;
		case 4 : // 돌아가기
			return false;
		}
		return true;
	}
		
	private static void printChangePwLoop(String id) {	// 비밀번호 변경
		String result;
		while(true){
			System.out.print("\n비밀번호를 변경하시겠습니까? (Y/N) : ");
			try {
				result = br.readLine();
				if(result.equalsIgnoreCase("Y")) {
					changeSuccess = info.changePw(id);
					getAnyKey();
					return;
				} else if(result.equalsIgnoreCase("N")) {
					return;
				} else {
					System.out.println("Y or N 중에 입력해주세요.");
				}
			} catch (IOException e2) {
				System.out.println("[경고] - 다시 입력해주세요.");
				}
		}
	}
		
	private static void printChangeNameLoop(String id) {	// 이름 변경
		String result;
		while(true){
			System.out.print("\n이름을 변경하시겠습니까? (Y/N) : ");
			try {
				result = br.readLine();
				if(result.equalsIgnoreCase("Y")) {
					changeSuccess = info.chageName(id);
					getAnyKey();
					return;
				} else if(result.equalsIgnoreCase("N")) {
					return;
				} else {
					System.out.println("Y or N 중에 입력해주세요.");
				}
			} catch (IOException e2) {
				System.out.println("[경고] - 다시 입력해주세요.");
				}
		}
	}
		
	private static void printChangePhoneLoop(String id) {	// 번호 변경
		String result;
		while(true){
			System.out.print("\n번호를 변경하시겠습니까? (Y/N) : ");
			try {
				result = br.readLine();
				if(result.equalsIgnoreCase("Y")) {
					changeSuccess = info.chagePhone(id);
					getAnyKey();
					return;
				} else if(result.equalsIgnoreCase("N")) {
					return;
				} else {
					System.out.println("Y or N 중에 입력해주세요.");
				}
			} catch (IOException e2) {
				System.out.println("[경고] - 다시 입력해주세요.");
				}
		}
	}
		
	private static void printUnregisterLoop(String id) {	// 회원 탈퇴
		String result;
		while(true) {
			System.out.print("\n회원을 탈퇴하시겠습니까? (Y/N) : ");
			try {
				result = br.readLine();
				if(result.equalsIgnoreCase("Y")) {
					changeSuccess = info.unregister(id);
					getAnyKey();
					break;
				} else if(result.equalsIgnoreCase("N")) {
					return;
				} else {
					System.out.println("Y or N 중에 입력해주세요.");
					continue;
				}
			} catch (IOException e2) {
				System.out.println("[경고] - 다시 입력해주세요.");
				continue;
			}
		}
	}
		
	// 5. 랭킹--------------------------------
	private static void printRank(String id) {
		clear();
		System.out.println(
					"\t\t██████╗  █████╗ ███╗   ██╗██╗  ██╗\r\n"
			      + "\t\t██╔══██╗██╔══██╗████╗  ██║██║ ██╔╝\r\n"
			      + "\t\t██████╔╝███████║██╔██╗ ██║█████╔╝ \r\n" 
			      + "\t\t██╔══██╗██╔══██║██║╚██╗██║██╔═██╗ \r\n"
				  + "\t\t██║  ██║██║  ██║██║ ╚████║██║  ██╗\r\n" 
			      + "\t\t╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝");

		rank.viewRank(id);
		getAnyKey();
	}
	
	// 종료 확인 문구-------------------
	private static boolean checkExit() {
		clear();
		System.out.println(
				  "==================================================================\r\n"
				+ "                                                                  \r\n"
				+ "                          종료 하시겠습니까?                           \r\n"
				+ "                                                                  \r\n"
				+ "                                                                  \r\n"
				+ "                    1. Yes               2. No                    \r\n"
				+ "                                                                  \r\n"
				+ "==================================================================");
		int input = getNum(2);
		if(input == 1) {
			System.out.println("이용해 주셔서 감사합니다.");
			return true;
		}
		else
			return false;
		
	}
	
	// 숫자 입력 받기----------------------
	private static int getNum(int maxNum) {	
		int input = -1;
		while (true) {
			System.out.print(">> ");
			try {
				input = Integer.parseInt(br.readLine());
				if (input < 1 || input > maxNum) {
					System.out.println("검색하신 번호의 항목을 찾을 수 없습니다.");
					continue;
				}
				break;
			} catch (Exception e) {
				System.out.println("숫자 값을 입력해 주세요.");
			}
		}

		return input;
	}
	
	private static int getNumMain(int maxNum) {
		int input = -1;
		while (true) {
			System.out.print(">> ");
			try {
				input = Integer.parseInt(br.readLine());
				if(input == secretNum) {
					return input;
				}
				if (input < 1 || input > maxNum) {
					System.out.println("검색하신 번호의 항목을 찾을 수 없습니다.");
					System.out.println("enter입력시 전체 목록을 출력합니다.");
					continue;
				}
				break;
			} catch (Exception e) {
				System.out.println("숫자 값을 입력해 주세요.");
			}
		}

		return input;
	}
	
	// 아무 키 입력받아 종료---------
	private static void getAnyKey() {
		System.out.println("\n계속하려면 아무 문자를 입력해 주세요.");
		System.out.print(">> ");
		try {br.readLine();} catch (IOException e) {}
	}
	
	// 로딩 화면------------------------
	private static void printLoading() {	
		LoadingFont lf = new LoadingFont();
		int idx = 0;
		for (int i = 0; i < 65; i+=2) {
			clear();
			if(i%2==0) {
				idx++;
				idx = idx%8;
			}
			System.out.println(lf.loading.get(idx));
			for (int j = 0; j < i; j++) {
				System.out.print("■");
			}
			for (int j = 0; j < 65-i-1; j++) {
				System.out.print("□");
			}
			tSleep(100);
		}
		
	}
	
	// 콘솔 창 클리어------------
	private static void clear() {	
		for (int i = 0; i < 50; i++)
			System.out.println();
	}
	
	// Thread.sleep으로 딜레이 주기-------
	private static void tSleep(int msec) {	
		try {Thread.sleep(msec);} catch (InterruptedException e) {}
	}

}
