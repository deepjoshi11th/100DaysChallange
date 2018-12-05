package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class Tester {
	public static Set<Character> extras = new HashSet<Character>();
	public static Set<String> actors = new HashSet<String>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println("Hello, World");
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(new File("temp.txt")));
			BufferedReader br = new BufferedReader(new FileReader(new File("story.txt")));
			String line;
			while((line=br.readLine())!=null) {
				if(line.length()>0) {
					if(isValidDailouge(line)) {
						//printOtherCharacters(line);
						//countActors(line);
						line = removeBracketed(line);
						line = removePunctuation(line);
						line = removeActors(line);
						
						printOtherCharacters(line);
						countActors(line);
						//System.out.println(line);
						pw.println(line);
						String[] words = separateWords(line);
					}
				}
			}
			br.close();
			System.out.println(extras);
			System.out.println(actors);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static String removePunctuation(String line) {
		char[] puncts = {',','.',':',';','?','!','_'};
		String ret = line;
		int index;
		for(int i=0;i<puncts.length;i++) {
			do {
				index = ret.indexOf(puncts[i]);
				if(index!=-1) {
					if(index<ret.length()-1) {
						ret = ret.substring(0,index)+ret.substring(index+1);
					} else {
						ret = ret.substring(0,index);
					}
					
				}
			}while(index!=-1);
		}
		index=532;
		do {
			index = ret.indexOf("—");
			if(index!=-1) {
				if(index<ret.length()-1) {
					if((ret.charAt(index+1)!=' ')&&(ret.charAt(index-1)!=' ')) {
						ret = ret.replace('—', ' ');
					} else {
						ret = ret.substring(0,index)+ret.substring(index+1);
					}
					
				} else {
					ret = ret.substring(0,index);
				}
				
			}
		}while(index!=-1);
		
		return ret;
	}
	private static String removeActors(String line) {
		String ret = line;
		String[] words = separateWords(line);
		if(words!=null) {
			for(int i=0;(i<words.length)&&(words[i]!=null);i++) {
				if(allCaps(words[i])) {
					int firstIndex = ret.indexOf(words[i]);
					int secondIndex = firstIndex + words[i].length();
					if((firstIndex!=-1)&&(secondIndex!=-1)&&(secondIndex>firstIndex)) {
						ret = ret.substring(0,firstIndex)+ret.substring(secondIndex+1,ret.length());	
					}
				}
			}
		}
		return ret;
	}
	private static String removeBracketed(String line) {
		String ret = line;
		int firstIndex = line.indexOf('[');
		int secondIndex = line.indexOf(']');
		if((firstIndex!=-1)&&(secondIndex!=-1)&&(secondIndex>firstIndex)) {
			if((secondIndex<line.length())&&(line.charAt(secondIndex)==' ')) {
				ret = line.substring(0,firstIndex)+line.substring(secondIndex+2,line.length());
			} else {
				ret = line.substring(0,firstIndex)+line.substring(secondIndex+1,line.length());
			}
			
		}
		return ret;
	}
	private static boolean isValidDailouge(String line) {
		if(line.startsWith(" ")) {
			return false;
		}
		if((line.indexOf("ACT")!=-1)||(line.indexOf("SCENE")!=-1)) {
			return false;
		}
		return true;
	}
	private static void countActors(String line) {
		String[] words = separateWords(line);
		if((words!=null)&&(words.length>1)) {
			for(int i=0;i<words.length;i++) {
				if(words[i]!=null) {
					if((allCaps(words[i]))&&(words[i].length()>1)) {
						actors.add(words[i]);
					}
				}
			}
		}
	}
	private static boolean allCaps(String string) {
		if((string==null)||string.length()<2) {
			return false;
		}
		for(int i=0;i<string.length();i++) {
			if((string.charAt(i)<'A')||(string.charAt(i)>'Z')) {
				return false;
			}
		}
		return true;
	}
	private static void printOtherCharacters(String line) {
		char[] chars = line.toCharArray();
		char[] output = new char[line.length()];
		for(int i=0,j=0;i<chars.length;i++) {
			char letter = chars[i];
			if(((letter>='A')&&(letter<='Z'))||((letter>='a')&&(letter<='z'))) {
				output[j++] = letter;
			} else {
				extras.add(letter);
				//System.out.print(letter);
			}
		}
	}	
	private static String[] separateWords(String line) {
		String[] words = new String[100];
		int firstIndex = 0,secondIndex = 0;
		int i = 0;
		String word;
		while(firstIndex!=-1) {
			secondIndex = line.indexOf(" ",firstIndex+1);
			if(secondIndex==-1) {
				secondIndex=line.length();
			}
			if(firstIndex==0) {
				word = line.substring(firstIndex, secondIndex);
			} else {
				word = line.substring(firstIndex+1, secondIndex);
			}
			if(word.endsWith(",")||word.endsWith(".")||word.endsWith("?")||word.endsWith("!")) {
				word = word.substring(0,word.length()-1);
			}
			words[i++] = word;
			//System.out.println(word);
			firstIndex = line.indexOf(" ", secondIndex);
		}
		return words;
	}
}