package dictionary.dao.secret;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
	private BufferedReader br = null;
	private BufferedWriter bw = null;
	
	
	public String read(String input) {
		StringBuilder result = new StringBuilder();
		
		try {
			br = new BufferedReader(new FileReader(input));
			
			String temp;
			
			while((temp = br.readLine()) != null) {
				result.append(temp + "\n");
			}
			
			
		} catch (FileNotFoundException e) {
			System.out.println("[경고] - 파일 존재하지 않습니다.");
		} catch (IOException e) {
			System.out.println("[경고] - 오류 발생");
		} finally {
			close();
		}
		
		return result.toString();
	}
	
	public boolean write(String input, String path) {
		
		try {
			bw = new BufferedWriter(new FileWriter(path));
			
			bw.write(input);
			
			bw.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	private void close() {
		if(br != null)
			try {br.close();} catch (IOException e) {}
		if(bw != null)
			try {bw.close();} catch (IOException e) {}
	}
}
