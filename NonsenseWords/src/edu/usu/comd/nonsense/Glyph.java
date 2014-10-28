package edu.usu.comd.nonsense;

import java.util.ArrayList;
import java.util.List;

public class Glyph {
	
	private char[] chars;
	
	public Glyph(char[] chars)
	{
		this.chars = chars;
	}
	
	public int getSize() 
	{
		return chars.length;
	}
	
	public char[] getChars()
	{
		return chars;
	}
	
	public static List<Glyph> makeGlyphList(String... array)
	{
		List<Glyph> g = new ArrayList<Glyph>();
		for (String s : array)
		{
			g.add(new Glyph(s.toCharArray()));
		}
		
		return g;
	}

}
