package edu.usu.comd.nonsense;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Creator {
				
	/*
	 * Gameplan:
	 * 
	 * 0) Make sure random letters are coming back random
	 * 
	 * 1) Flesh out blends
	 * 
	 * 2) Flesh out custom Letter types
	 * 
	 * 3) Add checks for valid word types -- CCCCVC doesn't fly.
	 * 
	 * 3) Add file output support for Kate
	 * 
	 * 4) Wrap it in a GUI for easy creation and testing
	 * 
	 * 5) Add in reporting --> Click each glpyh to see if it was good / bad. Keep Score. Show ALL glyphs and % of accuracy
	 */
	
	public static void main(String[] args)
	{
		List<Word> wordList = createList(Arrays.asList(new Letter(Glyph.makeGlyphList("a","e","i"), 1), new Letter(Glyph.makeGlyphList("v"), 0)), 4);
		System.out.println(wordList.size());
		for (Word s : wordList)
		{
			System.out.println(s.getString());
		}
		
		wordList = createList(Arrays.asList(new Letter(Glyph.makeGlyphList("v"), 0), new Letter(Glyph.makeGlyphList("a","e","i"), 1)), 4);
		System.out.println(wordList.size());
		for (Word s : wordList)
		{
			System.out.println(s.getString());
		}
	}
	
	private static List<Word> createList(List<Letter> letters, int numberToPractice)
	{
		
		//New game plan:
		// Screw first time / last time
		// Generate list of empty Strings. Expensive. Could be char arrays. Of correct length. That.
		
		Letter mostImportantLetter = findMostImportantLetter(letters);
		int numberOfResults = findNumberOfResults(mostImportantLetter, numberToPractice);
		List<Word> wordList = new ArrayList<Word>(numberOfResults);
		IntStream.range(0, numberOfResults).forEach(i -> wordList.add(new Word()));
		
		for (Letter l : letters)
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
				.filter(w -> w.getGlyph(0).getChars()[0] != 'x')
				.filter(w -> Blacklist.blackList.contains(w) == false)
				.collect(Collectors.toList());
	}
	
	private static Letter findMostImportantLetter(List<Letter> letters)
	{
		return letters.stream().max(Letter.comparator).get();
	}
	
	private static int findNumberOfResults(Letter letter, int numberToPractice)
	{
		return letter.getPossibilities().size() * numberToPractice;
	}
}
