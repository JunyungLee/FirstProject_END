package dictionary.function;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import dictionary.dao.DictionaryDAO;
import dictionary.vo.DictionVO;

public class Search {
	private String memberGrade;
	private String dictionGrade;
	private int d_Id;	//키워드를 이용해 path을 받기 위한 변수
	private String keyword;		//키워드 입력 값 변수
	private String index;	//Like로 검색된 키워드에서 고르기
	
	public String keySearch(String id) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		DictionaryDAO dao = new DictionaryDAO();
		List<DictionVO> list = new ArrayList<>();
		List<DictionVO> resultList = new ArrayList<DictionVO>();
		
		
		while(true) {
			System.out.print("검색할 정보의 키워드를 입력해주세요. : ");
			keyword = br.readLine();
			keyword = keyword.toUpperCase();
			
			if(keyword.length()==0) {
				System.out.println("\n\n\n검색하신 키워드를 찾을 수 없습니다.\n\n");
				continue;
			}
			
			// 띄어쓰기로 나눠서 저장
			List<String> keywordList = new ArrayList<String>(Arrays.asList(keyword.split(" ")));
			
			
			//문자열이 공백인 인덱스 삭제
			for(int i = 0; i < keywordList.size(); i++) {
				if(keywordList.get(i).length()==0) 
					keywordList.remove(i--);
			}
			
			for (String temp : keywordList) {
				// like 검색 후 데이터 저장을 위한 list생성
				list = dao.dictionSelectLike(temp);
				
				// resultList에 없는거만 추가
				for(DictionVO voIn : list) {
					boolean isExist = false; // resultList에 존재하는지
					for(DictionVO voExist : resultList) {
						if(voExist.getD_id() == voIn.getD_id()) {
							isExist = true;
							break;
						}
					}
					
					if(isExist) { // 이미 있는 내용이면 넘어가기
						continue;
					} else { // 없으면 추가
						resultList.add(voIn);
					}
				}
			}
			
			if(!resultList.isEmpty()) {
				break;
			}
			
			System.out.println("\n\n\n검색하신 키워드를 찾을 수 없습니다.\n\n");
		}
		//등급비교를 위한 변수
		memberGrade = dao.scoreSelect(id).getGrade();
		dictionGrade = resultList.get(0).getGrade();
		
		
		//데이터 읽기 및 등급비교
		if(resultList.size() == 1) {
			if(gradeCompare(memberGrade.charAt(0), dictionGrade.charAt(0))) {
				keySearch(id);
			}else {
				d_Id = dao.dictionSelectKeyword(resultList.get(0).getKeyword()).getD_id();
			}
			
			//@@@@@@@@@@@@@범위를 초과하는 값을 입력했을 때 상황 수정하기
		}else {
			resultList.sort(Collections.reverseOrder());
			int num = 1;
			System.out.println("\n나의 등급 : " + memberGrade);
			System.out.println("================================================================");
			for(DictionVO v : resultList) {
				System.out.println(num++ + ". " + "<" + v.getGrade() + ">" + " 제목: " + v.getKeyword());
			}
			System.out.println(num + ".  나가기");
			System.out.println("================================================================");
			System.out.println("\n중복되는 키워드가 들어간 검색 목록입니다.");
			while(true) {
				System.out.print("\n원하시는 결과를 번호로 선택해주세요. : ");
				index = br.readLine();
				try {
					if (Integer.parseInt(index) <= num - 1 && Integer.parseInt(index) > 0) {
						break;
					} else if(Integer.parseInt(index) == num) {
						return null;
					} else {
						System.out.println("검색하신 번호의 파일을 찾을 수 없습니다.");
						continue;
					}
				} catch (Exception e) {
					System.out.println("숫자 값을 입력해 주세요.");
					continue;
				}
				
			}
			
			while(true) {
				if(gradeCompare(memberGrade.charAt(0), resultList.get(Integer.parseInt(index)-1).getGrade().charAt(0))) {
					while(true) {
						System.out.print("\n원하시는 결과를 번호로 선택해주세요. : ");
						index = br.readLine();
						
						try {
							if (Integer.parseInt(index) <= num - 1 && Integer.parseInt(index) > 0) {
								break;
							} else if (Integer.parseInt(index) == num) {
								return null;
							} else {
								System.out.println("검색하신 번호의 파일을 찾을 수 없습니다.");
							}
						} catch (Exception e) {
							System.out.println("숫자 값을 입력해 주세요.");
						}
						
					}
				}else {
					d_Id = resultList.get(Integer.parseInt(index)-1).getD_id();
					break;
				}
			}
		}
		
		
		String path = dao.dataFileSelect(d_Id).getFileName();
		
		return path;
		
	}
	
	public boolean gradeCompare(char a, char b) {
		if(a > b) {
			System.out.println("\n열람할 수 없는 등급입니다."); 
			System.out.println("등급에 맞는 번호를 입력해주세요. [현재 등급 : \"" + a + "\"]");
			return true;
		}
		
		return false;
	}
	
}
