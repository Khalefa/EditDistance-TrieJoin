package edu.alexu.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class Utils {
	
	public static int min(int a, int b, int c) {
		return Math.min(Math.min(a, b), c);
	}
	public static int min(int a, int b) {
		return Math.min(a, b);
	}
	public static int max(int a, int b) {
		return Math.max(a, b);
	}
	public static void cleanFile(String filename) {
		HashSet<String> unquie_words = new HashSet<String>();
		int words = 0;
		try {
			String line;

			BufferedReader in = new BufferedReader(new FileReader(filename));

			while (true) {

				line = in.readLine();
				if (line == null || line.equals(""))
					break;
				if (!unquie_words.contains(line))
					unquie_words.add(line);
				else
					System.out.println("repreated" + line);
				words++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print(words - unquie_words.size());
	}

}
