package edu.usu.comd.nonsense;

import java.util.Comparator;
import java.util.List;


public class Letter {
	
	public static final Letter VOWEL = new Letter(Glyph.makeGlyphList(new String[]{"a","e","i","o","u"}), 0);
	public static final Letter CONSONANT = new Letter(Glyph.makeGlyphList(new String[]{"b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "p", "q", "r", "s", "t", "v", "w", "x", "y", "x","z"}), 1);
	public static final Letter BLEND = new Letter(Glyph.makeGlyphList(new String[]{"bl","br","ch","cl","cr","dr","fl","fr","gl","gr","pl","pr","sc","sh","sk","sl","sm","sn","sp","st","sw","th","tr","tw","wr"}), 2);
	
	private List<Glyph> possibilities;
	private int priority;
	
	Letter(List<Glyph> possibilities, int priority)
	{
		this.possibilities = possibilities;
		this.priority = priority;
	}
	
	public List<Glyph> getPossibilities() 
	{
		return possibilities;
	}
	
	public int getPriority(){
		return priority;
	}
	
	public int getNumberOfCharacters() {
		return possibilities.get(0).getSize();
	}
	
	public static Comparator<Letter> comparator = new Comparator<Letter>() {
		@Override
		public int compare(Letter o1, Letter o2) {
			return o1.priority - o2.priority;
		}
	};

	public Glyph getRandomGlyph() {
		return possibilities.get((int) (Math.random() * possibilities.size()));
	}

	
}
