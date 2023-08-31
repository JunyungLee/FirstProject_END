package dictionary.function;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import dictionary.dao.DictionaryDAO;
import dictionary.dao.secret.SecretCodeGenerator;
import dictionary.vo.ScoreVO;
import dictionary.vo.TestLogVO;
import dictionary.vo.TestVO;

public class Test {
	private SecretCodeGenerator scg = new SecretCodeGenerator();
	private DictionaryDAO dao = new DictionaryDAO();
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private Random r = null; // 문제 번호 랜덤으로 생성하기 위함
	private Set<Integer> numSet = null; // 문제 번호 저장
	private Map<Integer, String> xo = null; // 문제별로 정답여부 저장
	private List<TestVO> list = null;
	private int maxNum = -1;

	public int start(String id) {
		r = new Random();
		numSet = new HashSet<Integer>();
		xo = new HashMap<Integer, String>();
		list = new ArrayList<TestVO>();
		
		String userGrade = dao.scoreSelect(id).getGrade();
		list = dao.testSelectByGrade(userGrade);
		maxNum = list.size();
		
		// 시험문제 추첨
		raffle(userGrade);
		// 안내 방송
		info();
		// 시험 진행
		doTest();
		// TESTLOG 테이블에 저장
		saveLog(id);
		// 점수 계산
		int score = calcScore();

		// SCORE 테이블에 저장
		saveScore(id, score);

		return score;

	}
	
	private void raffle(String userGrade) {
		while (numSet.size() < 10) {
			numSet.add(r.nextInt(maxNum));
		}
	}
	
	private void info() {
		scg.decodeFile("./files/quest/information.txt");
		try {Thread.sleep(5000);} catch (InterruptedException e) {}
	}

	private void doTest() {
		TestVO test = null;
		int count = 1;
		for (int num : numSet) {
			try {
				test = list.get(num);
				System.out.println("===========================================\n");
				System.out.println("<"+count++ + "번 문제>   난이도 : <" + test.getGrade() + ">");
				System.out.println("===========================================");
				scg.decodeFile(test.getQuest());
				System.out.println("===========================================\n");
				
				// 입력 받기 (1~4 사이의 값을 받을 때 까지)
				while (true) {
					try {
						System.out.print("정답을 입력해 주세요. : ");
						int answer = Integer.parseInt(br.readLine());
						
						// 1~4 사이가 아니면 다시
						if(answer > 4 || answer < 1)
							throw new NumberFormatException();

						if (answer == test.getAnswer()) {
							xo.put(num + 1, "O");
							System.out.println("\nദ്ദി˙∇˙)ว 정답입니다! ദ്ദി˙∇˙)ว");
						} else {
							xo.put(num + 1, "X");
							System.out.println("\no(╥﹏╥)o 틀렸습니다.. o(╥﹏╥)o");
						}
						break;
					} catch (NumberFormatException e) {
						System.out.println("1~4 사이의 숫자만 입력해 주세요.");
						continue;
					}
				}
				
			} catch (IOException e) {
				System.out.println("[경고] - 오류 발생");
			} 
			System.out.println();
			try {Thread.sleep(1000);} catch (InterruptedException e) {}
		}
	}

	private int calcScore() {
		int score = 0;
		for (int t_id : xo.keySet()) {
			String temp = xo.get(t_id);
			if (temp.equals("O"))
				score += 10;
		}
		return score;
	}
	
	private void saveLog(String id) {
		TestLogVO testLog = null;
		int maxCount = dao.testLogSelectMaxCount(id);
		for (int t_id : xo.keySet()) {
			String temp = xo.get(t_id);
			testLog = new TestLogVO(id, t_id, temp, maxCount + 1);
			dao.testLogInsert(testLog);
		}
	}
	
	private void saveScore(String id, int score) {
		String grade = null;
		if(score >= 90)
			grade = "A";
		else if(score >= 80)
			grade = "B";
		else if(score >= 70)
			grade = "C";
		else if(score >= 60)
			grade = "D";
		else if(score >= 50)
			grade = "E";
		else
			grade = "F";
		
		
		int s_score = dao.scoreSelectS_SCORE(id);
		ScoreVO scoreVO = new ScoreVO(id, score, s_score + score, grade);
		dao.scoreUpdate(scoreVO);
	}

}
