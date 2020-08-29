package com.seccanj.zigzagpuzzle;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class Dictionary {

	private static final String ILLEGAL_CHARACTERS = "-'/.1234567890";
	
	private static final String SAMPLE_CSV_FILE_PATH = "./italian-dictionary.csv";

	private Set<Word> tempWords = new HashSet<>();
	private List<Word> words = new ArrayList<>();
	
	public Dictionary() {
        try (
            Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
        ) {
            // Reading Records One by One in a String array
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
            	Word p = new Word();
            	p.word = nextRecord[0].toLowerCase().trim();
            	p.length = p.word.length();
            	
            	if (p.length >= 2 && !containsIllegalCharacters(p.word)) {
                	tempWords.add(p);
            	}
            }

            words.addAll(tempWords);
            
    		Collections.sort(words, new Comparator<Word>() {

    			@Override
    			public int compare(Word p1, Word p2) {
    				return p2.length - p1.length;
    			}
    			
    		});
            
        } catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void findLongestWords(List<String> letters) {
		int maxLen = 0;

		System.out.println("Parole più lunghe:");
		
		for (int i=0; i<words.size(); i++) {
			Word p = words.get(i);
			
			if (p.length < maxLen - 2) {
				break;
			}
			
			if (possible(letters, p)) {
				if (p.length > maxLen) {
					maxLen = p.length;
				}
				
				System.out.println(p.word.length() + " " + p.word);
			}
		}
	}

	private boolean possible(List<String> letters, Word word) {
		String p = word.word;
		List<String> l = new ArrayList<String>(letters);
		
		for (int i=0; i<p.length(); i++) {
			if (l.contains(""+p.charAt(i))) {
				l.remove(""+p.charAt(i));
			} else {
				return false;
			}
		}
		
		return true;
	}

	private boolean containsIllegalCharacters(String word) {
		for (int i=0; i<ILLEGAL_CHARACTERS.length(); i++) {
			char c = ILLEGAL_CHARACTERS.charAt(i);
			if (word.indexOf(c) >= 0) {
				return true;
			}
		}
		
		return false;
	}
	
	public String findRandomWord(int len) {
		String result = null;
		
		List<Word> desiredLenWords = new ArrayList<>();

		for (Word word: words) {
			if (word.length == len) {
				desiredLenWords.add(word);
			} else if (word.length < len) {
				break;
			}
		}
		
		if (!desiredLenWords.isEmpty()) {
			result = desiredLenWords.get((int)Math.floor(Math.random()*desiredLenWords.size())).word;
			
			System.out.println("Found word '"+result+"'");

			result = result.replace('à', 'a');
			result = result.replace('è', 'e');
			result = result.replace('é', 'e');
			result = result.replace('ì', 'i');
			result = result.replace('ò', 'o');
			result = result.replace('ù', 'u');
		}
		
		return result;
	}
}
