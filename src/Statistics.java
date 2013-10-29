import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;


public class Statistics {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BufferedReader in = new BufferedReader(new FileReader("sm.txt"));
		HashSet<String> s = new HashSet<String>();
		while(true){
			String line = in.readLine();
			if(line == null || line.equals("")) break;
			s.add(line);
		}
		in.close();
		in = new BufferedReader(new FileReader("mm.txt"));
		HashSet<String> m = new HashSet<String>();
		while(true){
			String line = in.readLine();
			if(line == null || line.equals("")) break;
			m.add(line);
		}
		
		int [] edit = new int[s.size()*m.size()];
		int [] hist = new int[s.size()*m.size()];
		int [] mask = new int[s.size()*m.size()];
		
		int index = 0;
		for (String i : s) {
			for (String j : m) {
				
				edit[index] = edit(i,j);
				hist[index] = hist(i,j);
				mask[index] = mask(i,j);
				
				index ++;
			}
		}
		
		Arrays.sort(edit);
		Arrays.sort(hist);
		Arrays.sort(mask);
			
		print(edit);
		print(hist);
		print(mask);
	}

	static void print(int[] s){
		System.out.println("Mean : " + sum(s) / s.length);
		System.out.println("Min : " + s[0]);
		System.out.println("1st Q : " + s[(s.length + 1) / 4]);
		System.out.println("Median : " + s[(s.length + 1) / 2]);
		System.out.println("3rd Q : " + s[3 * (s.length + 1) / 4]);
		System.out.println("Max : " + s[s.length - 1]);
	}
	
	private static int sum(int[] edit) {
		int k = 0;
		for (int i = 0; i < edit.length; i++) {
			k += edit[i];
		}
		return k;
	}

	private static int mask(String i, String j) {
		char [] ii = i.toCharArray();
		char [] jj = j.toCharArray();
		int count = 0;
		for (char a = 'a'; a <= 'z'; a++) {
			boolean s = false;
			for (int k = 0; k < ii.length; k++) {
				if(ii[k]==a){s = true; break;}
			}
			
			for (int k = 0; k < jj.length; k++) {
				if(jj[k]==a){s = !s; break;}
			}
			count+= s?1:0;
		}
		return count;
	}

	private static int hist(String i, String j) {
		char [] ii = i.toCharArray();
		char [] jj = j.toCharArray();
		int count = 0;
		for (char a = 'a'; a <= 'z'; a++) {
			int s =0;
			for (int k = 0; k < ii.length; k++) {
				if(ii[k]==a)s++;
			}
			for (int k = 0; k < jj.length; k++) {
				if(jj[k]==a)s--;
			}
			count+= Math.abs(s);
		}
		return count;
	}

	private static int edit(String a, String b) {
		int m = a.length();
		int n = b.length();
		int[][] len = new int[m + 1][n + 1];
		for (int i = 0; i <= m; i++) {
			len[i][0] = i;
		}
		for (int j = 0; j <= n; j++) {
			len[0][j] = j;
		}
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (a.charAt(i) == b.charAt(j)) {
					len[i + 1][j + 1] = len[i][j];
				} else {
					len[i + 1][j + 1] = 1 + Math.min(len[i][j], Math.min(len[i + 1][j], len[i][j + 1]));
				}
			}
		}
		return len[m][n];
	}

}
