package edu.usu.comd.nonsense;

import java.util.ArrayList;
import java.util.List;

public class Word {
	
	private List<String> glyphList;
	
	public Word() {
		glyphList = new ArrayList<String>();
	}
	
	public Word(List<String> glyphList)
	{
		this.glyphList = glyphList;
	}
	
	public void addGlyph(String g)
	{
		glyphList.add(g);
	}

	public String getString() {
		StringBuilder sb = new StringBuilder();
		glyphList.stream().forEach(g -> sb.append(g));
		return sb.toString();
	}
	
	public String getGlyph(int i)
	{
		return glyphList.get(i);
	}
	
}
