package edu.alexu.util;

public class Hist {
	int[] minHist = new int[26 + 1];
	int[] maxHist = new int[26 + 1];
	
	public Hist() {
		for(int i=0;i<27;i++){
			minHist[i]=Integer.MAX_VALUE;
			maxHist[i]=Integer.MIN_VALUE;
		}		
	}

	public static void build_hist(String s, int c[]) {
		for (int j = 0; j < s.length(); j++) {
			int indx = s.charAt(j) - 'a';
			if (indx <= 26 && indx >= 0)
				c[indx]++;
			else
				c[26]++; // non alphabet character
		}
	}

	public static int id(char x) {
		int indx = x - 'a';
		if (indx <= 26 && indx >= 0)
			return indx;
		else
			return 26;
	}

	public void insert(int curr_hist[]) {
		for (int i = 0; i < 27; i++) {
			minHist[i] = Utils.min(minHist[i], curr_hist[i]);
			maxHist[i] = Utils.max(maxHist[i], curr_hist[i]);
		}
	}

	static public int abs_diff(int a[], int b[]) {
		int diff = 0;
		for (int i = 0; i < 27; i++)
			diff = diff + Math.abs(a[i] - b[i]);
		return diff;
	}

	public int Diff(Hist h) {
		int d = 0;
		for (int i = 0; i < 27; i++)
			d = d
					+ Interval.distance(minHist[i], maxHist[i], h.minHist[i],
							h.maxHist[i]);
		return d;
	}

	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < 26; i++) {
			char ss = (char) ('a' + i);
			String min = "", max = "";
			if (maxHist[i] > 0) {
				for (int j = 0; j < minHist[i]; j++)
					min = min + ss;
				for (int j = 0; j < maxHist[i]; j++)
					max = max + ss;
				s = s + min + "-" + max;
			}
		}
		return s;
	}
}
