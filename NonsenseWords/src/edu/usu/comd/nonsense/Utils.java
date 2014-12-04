package edu.usu.comd.nonsense;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Utils {
	
	private static List<Phoneme> phonemeList = null;
	
	public static List<Phoneme> getPhonemeList() 
	{
		if (phonemeList == null)
		{
			try {
				phonemeList = readPhonemeList();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return phonemeList;
	}
	
	public static void removePhoneme(Phoneme p)
	{
		phonemeList.remove(p);
		try {
			writeOutPhonemeList(phonemeList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void addPhoneme(Phoneme p)
	{
		phonemeList.add(p);
		try {
			writeOutPhonemeList(phonemeList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		List<Phoneme> phonemeList = readPhonemeList();
		for (Phoneme p : phonemeList)
		{
			System.out.println(p.getName());
		}
	}
	
	private static void writeOutPhonemeList(List<Phoneme> phonemeList) throws IOException
	{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(phonemeList);
		FileWriter fw = new FileWriter(new File("phonemes.json"));
		fw.write(json);
		fw.close();
	}
	
	private static List<Phoneme> readPhonemeList() throws FileNotFoundException
	{
		StringBuilder sb = new StringBuilder();
		File f = new File("phonemes.json");
		Scanner a = new Scanner(new FileReader(f));
		
		while (a.hasNextLine())
		{
			sb.append(a.nextLine());
		}
		
		String json = sb.toString();

		Gson gson = new Gson();
		Type listType = new TypeToken<ArrayList<Phoneme>>(){}.getType();
		List<Phoneme> phonemeList = (List<Phoneme>) gson.fromJson(json, listType);
		return phonemeList;

	}

}
