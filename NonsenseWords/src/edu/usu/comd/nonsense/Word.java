package edu.usu.comd.nonsense;

import java.util.ArrayList;
import java.util.List;

public class Word {
	
	private List<Glyph> glyphList;
	
	public Word() {
		glyphList = new ArrayList<Glyph>();
	}
	
	public Word(List<Glyph> glyphList)
	{
		this.glyphList = glyphList;
	}
	
	public void addGlyph(Glyph g)
	{
		glyphList.add(g);
	}

	public String getString() {
		StringBuilder sb = new StringBuilder();
		glyphList.stream().forEach(g -> sb.append(g.getChars()));
		return sb.toString();
	}
	
	public Glyph getGlyph(int i)
	{
		return glyphList.get(i);
	}
	
	

}
