package edu.usu.comd.nonsense;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Creator {
				
	/*
	 * 0) Make sure lists don't have duplicates right next to each other.
	 * 
	 * 1) Persist the list of PhonemeLists to a map on file. 
	 * 
	 * 2) 
	 */
	
	public static void main(String[] args)
	{
		List<Word> wordList = createList(Arrays.asList(new Phoneme(Arrays.asList(new String[]{"a","e","i"}), 1, "subvowel"), new Phoneme(Arrays.asList(new String[]{"v"}), 0, "v")), 4);
		System.out.println(wordList.size());
		for (Word s : wordList)
		{
			System.out.println(s.getString());
		}
		
		wordList = createList(Arrays.asList(new Phoneme(Arrays.asList(new String[]{"v"}), 0, "v"), new Phoneme(Arrays.asList(new String[]{"a","e","i"}), 1, "subvowel")), 4);
		System.out.println(wordList.size());
		for (Word s : wordList)
		{
			System.out.println(s.getString());
		}
	}
	
	private static List<Word> createList(List<Phoneme> letters, int numberToPractice)
	{	
		Phoneme mostImportantLetter = findMostImportantLetter(letters);
		int numberOfResults = findNumberOfResults(mostImportantLetter, numberToPractice);
		List<Word> wordList = new ArrayList<Word>(numberOfResults);
		IntStream.range(0, numberOfResults).forEach(i -> wordList.add(new Word()));
		
		for (Phoneme l : letters)
		{
			if (l.equals(mostImportantLetter))
			{
				IntStream.range(0, numberOfResults)
						.forEach(i -> wordList.get(i).addGlyph(l.getPossibilities().get(i / numberToPractice)));
			}
			else
			{
				wordList.stream().forEach(w -> w.addGlyph(l.getRandomGlyph()));
			}
			
			Collections.shuffle(wordList);
		}
		
		return wordList.stream()
				.filter(w -> w.getGlyph(0).charAt(0) != 'x')
				.filter(w -> Blacklist.blackList.contains(w) == false)
				.collect(Collectors.toList());
	}
	
	public static String createOutputString(List<Phoneme> letters, int numberToPractice)
	{
		List<Word> wordList = createList(letters, numberToPractice);
		StringBuilder sb = new StringBuilder();
		for (Word w : wordList)
		{
			sb.append(w.getString());
			sb.append("\n");
		}
		return sb.toString();
	}
	
	private static Phoneme findMostImportantLetter(List<Phoneme> letters)
	{
		return letters.stream().max(Phoneme.comparator).get();
	}
	
	private static int findNumberOfResults(Phoneme letter, int numberToPractice)
	{
		return letter.getPossibilities().size() * numberToPractice;
	}
}
