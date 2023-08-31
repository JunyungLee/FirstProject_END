package dictionary.etc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class HangMan {
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	List<String> drawing = null; // 행맨 그림
	List<String> words = null; // 출제가능한 단어들
	Set<Character> existChoice = null; // 존재하는 글자
	Set<Character> notExistChoice = null; // 존재하지 않는 글자
	Set<Character> exist = null; // 정답 글자들 저장할 set
	
	int deadCount; // 10이 되면 게임 종료 (기회 11번)
	int count;
	
	public void startGame() {
		
		// 초기화 작업 (리스트에서 단어 하나 뽑아서 정답 Set에 저장)
		String word = init(); 
		int length = word.length();
		
		boolean playing = true;
		boolean didWin = false;
		while(playing) {
			
			// 시도한 글자 출력
			// 행맨 그림 출력
			// 지금 단어 상황 출력
			printInfo(word, length);
			
			// 카운트 초과로 패배
			if(deadCount >= 10) {
				break;
			}
			// 모두 맞춰서 승리
			else if(exist.size() == existChoice.size()) {
				didWin = true;
				break;
			}
			
			// 글자 하나 입력받기
			char input = getWord();
			
			// 정답 유무 확인 및 Set에 저장
			check(input);
		}
		
		if(didWin) {
			System.out.println("축하합니다! " + count +"번 만에 맞추셨네요!");
		}
		else {
			System.out.println("목이 메달려 죽었습니다...");
			System.out.println("정답은 \"" + word + "\" 였습니다.");
		}
		
	}
	
	private void check(char input) {
		String sInput = String.valueOf(input);
		String upper = sInput.toUpperCase();
		String lower = sInput.toLowerCase();
		
		// 정답인 글자면 대소문자 모두 existChoice에 저장
		if(exist.contains(input)) { 
			existChoice.add(upper.charAt(0));
			existChoice.add(lower.charAt(0));
		} 
		// 아니면 notExistChoice에 저장
		else {
			notExistChoice.add(upper.charAt(0));
			notExistChoice.add(lower.charAt(0));
			deadCount++;
		}
		count++;
		return;
	}
	
	private char getWord() {
		while (true) {
			String input = null;
			System.out.print("원하는 글자를 입력해 주세요 : ");
			
			// 입력받기
			try {
				input = br.readLine();
				
				// 입력받은 값이 한글자가 아니라면 다시 입력
				if (input.length() != 1) {
					System.out.println("한 글자만 입력할 수 있습니다.");
					continue;
				} 
			} catch (IOException e) {
				System.out.println("오류가 발생했습니다. 다시 입력해주세요.");
				continue;
			}
			char cInput = input.charAt(0);
			// 알파벳이 아니라면 다시 입력
			if( !(cInput >= 'a' && cInput <= 'z') && !(cInput >= 'A' && cInput <= 'Z')  ) {
				System.out.println("알파벳만 입력할 수 있습니다.");
				continue;
			}
			// 이미 입력한 값이면 다시 입력
			else if(existChoice.contains(cInput) || notExistChoice.contains(cInput)) {
				System.out.println("시도한적 없는 글자를 입력해주세요.");
				continue;
			}
			
			return input.charAt(0);
					
		}
		
	}
	
	private void printInfo(String word, int length) {
		clear();
		System.out.println(
				  "     ██╗  ██╗ █████╗ ███╗   ██╗ ██████╗ ███╗   ███╗ █████╗ ███╗   ██╗\n"
				+ "     ██║  ██║██╔══██╗████╗  ██║██╔════╝ ████╗ ████║██╔══██╗████╗  ██║\n"
				+ "     ███████║███████║██╔██╗ ██║██║  ███╗██╔████╔██║███████║██╔██╗ ██║\n"
				+ "     ██╔══██║██╔══██║██║╚██╗██║██║   ██║██║╚██╔╝██║██╔══██║██║╚██╗██║\n"
				+ "     ██║  ██║██║  ██║██║ ╚████║╚██████╔╝██║ ╚═╝ ██║██║  ██║██║ ╚████║\n"
				+ "     ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝\n"
				+ "                                                                     ");
		// 시도한 글자 출력
		System.out.print("\n이미 시도한 글자 : ");
		for (char c : notExistChoice)
			// 대문자만 출력
			if(c >= 'A' && c <= 'Z') System.out.print(c + "  ");
		for (int i = 0; i < 2; i++)
			System.out.println();

		// 행맨 그림 출력
		System.out.println(drawing.get(deadCount));
		for (int i = 0; i < 3; i++)
			System.out.println();

		// 지금 단어 상황 출력
		System.out.print("    ");
		for (int i = 0; i < length; i++) {
			char temp = word.charAt(i);
			
			// 정답 고른거 중에 있으면 단어 부분 출력
			if (existChoice.contains(temp)) {
				System.out.print(temp);
			} 
			// 아니면 '_' 출력
			else {
				System.out.print("_");
			}
			System.out.print("  ");
		}
		for (int i = 0; i < 2; i++)
			System.out.println();
	}
	
	private String init() {
		exist = new HashSet<Character>();
		existChoice = new HashSet<Character>();
		notExistChoice = new HashSet<Character>();
		int size = words.size(); // 단어가 들어있는 리스트 사이즈
		count = 0;
		deadCount = 0;
		
		// 리스트에서 랜덤 인덱스 뽑기
		Random r = new Random();
		int idx = r.nextInt(size);
		
		// 뽑인 단어 저장
		String word = words.get(idx);
		
		String upper = word.toUpperCase();
		String lower = word.toLowerCase();
		
		// 단어 글자들 exist Set에 저장 (대,소문자 둘 다 저장)
		for(int i = 0; i < word.length(); i++) {
			exist.add(upper.charAt(i));
			exist.add(lower.charAt(i));
		}
		
		
		
		return word;
	}
	
	private void clear() {
		for(int i = 0; i < 50; i++)
			System.out.println();
	}
	
	
	public HangMan() {
		words = new ArrayList<String>();
		words.add("println");
		words.add("length");
		words.add("BufferedReader");
		words.add("while");
		words.add("Exception");
		words.add("ArrayList");
		words.add("HashMap");
		words.add("Scanner");
		words.add("private");
		words.add("extends");
		words.add("implements");
		words.add("RuntimeException");
		words.add("array");
		words.add("StringBuilder");
		words.add("Generic");
		words.add("continue");
		words.add("BufferedWriter");
		words.add("Socket");
		words.add("polymorphism");
		words.add("SELECT");
		words.add("DELETE");
		words.add("DROP");
		words.add("UPDATE");
		words.add("programming");
		words.add("DECODE");
		words.add("Object");
		words.add("abstract");
		words.add("function");
		words.add("Runnable");
		words.add("package");
		words.add("overriding");
		words.add("overloading");
		words.add("String");
		words.add("syncronized");
		words.add("Trigger");
		words.add("default");
		words.add("inheritance");
		words.add("InputStreamReader");
		words.add("InputStreamWriter");
		words.add("Segmentation");
		words.add("Collections");
		words.add("Iterator");
		words.add("Enumeration");
		words.add("hashCode");
		words.add("Stack");
		words.add("Queue");
		words.add("exist");
		words.add("contains");
		words.add("DataBase");
		words.add("executeQuery");
		words.add("executeUpdate");
		words.add("Thread");
		words.add("reference");
		words.add("Debugging");
		words.add("Serialization");
		words.add("Annotation");
		
		drawing = new ArrayList<String>();
		drawing.add(
				  "                                        \n"
				+ "                                        \n"
				+ "                                        \n"
				+ "                                        \n"
				+ "                                        \n"
				+ "                                        \n"
				+ "                                   --------");
		drawing.add(
				  "                                         \n"                    
				+ "                                         \n"
				+ "                                         \n"
				+ "                                       |\n"
				+ "                                       |\n"
				+ "                                       |\n"
				+ "                                   --------");
		drawing.add(
				  "                                          \n"                    
				+ "                                       | \n"
				+ "                                       |\n"
				+ "                                       |\n"
				+ "                                       |\n"
				+ "                                       |\n"
				+ "                                   --------");
		drawing.add(
				  "                                     __\n"
				+ "                                       | \n"
				+ "                                       |\n"
				+ "                                       |\n"
				+ "                                       |\n"
				+ "                                       |\n"
				+ "                                   --------");
		drawing.add(
				  "                                     __\n"
				+ "                                    |  | \n"
				+ "                                       |\n"
				+ "                                       |\n"
				+ "                                       |\n"
				+ "                                       |\n"
				+ "                                   --------");
		drawing.add(
				  "                                     __\n"
				+ "                                    |  | \n"
				+ "                                    O  |\n"
				+ "                                       |\n"
				+ "                                       |\n"                    
				+ "                                       |\n"                    
				+ "                                   --------");
		drawing.add(
				  "                                     __\n"
				+ "                                    |  | \n"
				+ "                                    O  |\n"
				+ "                                    |  |\n"
				+ "                                       |\n"
				+ "                                       |\n"
				+ "                                   --------");
		drawing.add(
				  "                                     __\n"
				+ "                                    |  | \n"
				+ "                                    O  |\n"
				+ "                                   /|  |\n"
				+ "                                       |\n"
				+ "                                       |\n"
				+ "                                   --------");
		drawing.add(
				  "                                     __\n"
				+ "                                    |  | \n"
				+ "                                    O  |\n"
				+ "                                   /|\\ |\n"                    
				+ "                                       |\n"
				+ "                                       |\n"                    
				+ "                                   --------");
		drawing.add(
				  "                                     __\n"
				+ "                                    |  | \n"
				+ "                                    O  |\n"
				+ "                                   /|\\ |\n"
				+ "                                   /   |\n"
				+ "                                       |\n"
				+ "                                   --------");
		drawing.add(
				  "                                     __\n"
				+ "                                    |  | \n"
				+ "                                    O  |\n"
				+ "                                   /|\\ |\n"
				+ "                                   / \\ |\n"
				+ "                                       |\n"
				+ "                                   --------");
	}

}
