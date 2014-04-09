package edu.alexu.dmlab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import edu.alexu.util.Utils;

public class EditDistance {
	ArrayList<String> words = new ArrayList<String>();
	public long count=0;
	public EditDistance(String filename,int wcount, int depth) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));

			String line;
			while (true) {
				line = in.readLine();
				
				if (line == null || line.equals(""))
					break;
				if (!words.contains(line))
					words.add(line);
				else
					System.out.println("repreated" + line);
				if(wcount==0)break;
				wcount--;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		calcEdit(depth);
	}

	public void calcEdit(int depth) {
		
		for (int i = 0; i < words.size(); i++){
			String a=(String)words.get(i);
			for (int j = i + 1; j < words.size(); j++) {
				String b=(String)words.get(j);
				int ed=Edit(a,b,depth);
				if(ed <=depth)count+=2;
			}
		}
		System.out.println("count"+count);
	}

	public static int Edit(String str1, String str2, int depth) {
		int diff=str1.length()-str2.length();
		if(diff<0)diff=-diff;
		if(diff>depth)return Integer.MAX_VALUE;
		int[][] distance = new int[str1.length() + 1][str2.length() + 1];

		for (int i = 0; i <= str1.length(); i++)
			distance[i][0] = i;
		for (int j = 1; j <= str2.length(); j++)
			distance[0][j] = j;

		for (int i = 1; i <= str1.length(); i++) {

			int min_dist = Integer.MAX_VALUE;
			for (int j = 1; j <= str2.length(); j++) {
				distance[i][j] = Utils
						.min(distance[i - 1][j] + 1,
								distance[i][j - 1] + 1,
								distance[i - 1][j - 1]
										+ ((str1.charAt(i - 1) == str2
												.charAt(j - 1)) ? 0 : 1));
				min_dist = Utils.min(min_dist, distance[i][j]);
			}
			if (min_dist > depth)
				return Integer.MAX_VALUE;
		}
		return distance[str1.length()][str2.length()];
	}
}
