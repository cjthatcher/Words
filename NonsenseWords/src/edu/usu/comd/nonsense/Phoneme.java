package edu.usu.comd.nonsense;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;


public class Phoneme {
	
	private static int maxPriority = 10;
	
	public static final Phoneme VOWEL = new Phoneme(Arrays.asList(new String[]{"a","e","i","o","u"}), 0, "vowel");
	public static final Phoneme CONSONANT = new Phoneme(Arrays.asList(new String[]{"b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "p", "q", "r", "s", "t", "v", "w", "x", "y", "x","z"}), 1, "consonant");
	public static final Phoneme BLEND = new Phoneme(Arrays.asList(new String[]{"bl","br","ch","cl","cr","dr","fl","fr","gl","gr","pl","pr","sc","sh","sk","sl","sm","sn","sp","st","sw","th","tr","tw","wr"}), 2, "blend");
	
	private String name;
	private List<String> possibilities;
	private int priority;
	
	private transient String tooltipText;
	
	public Phoneme(List<String> possibilities, int priority, String name)
	{
		this.possibilities = possibilities;
		this.priority = priority;
		this.name = name;
	}
	
	public List<String> getPossibilities() 
	{
		return possibilities;
	}
	
	public int getPriority(){
		return priority;
	}
	
	public int getNumberOfCharacters() {
		return possibilities.get(0).length();
	}
	
	public static Comparator<Phoneme> comparator = new Comparator<Phoneme>() {
		@Override
		public int compare(Phoneme o1, Phoneme o2) {
			return o1.priority - o2.priority;
		}
	};

	public String getRandomGlyph() {
		return possibilities.get((int) (Math.random() * possibilities.size()));
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getTooltipText() {
		if (tooltipText == null)
		{
			StringBuilder sb = new StringBuilder();
			for (String s : possibilities)
			{
				sb.append(s);
				sb.append(", ");
			}
			sb.delete(sb.length() - 2, sb.length());
			
			tooltipText = sb.toString();
		}
		return tooltipText;
	}
	
	public void setAsTopPriority() {
		maxPriority = maxPriority + 1;
		this.priority = maxPriority;
	}

	public boolean isHighestPriority() {
		return this.priority == maxPriority;
	}
	
	public Phoneme clone()
	{
		return new Phoneme(new LinkedList<String>(possibilities), 1, name);
	}
	
	public void setName(String s)
	{
		this.name = s;
	}
}
