import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;


public class pruningpower {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BufferedReader in = new BufferedReader(new FileReader("s.txt"));
		HashSet<String> s = new HashSet<String>();
		while(true){
			String line = in.readLine();
			if(line == null || line.equals("")) break;
			s.add(line);
		}
		in.close();
		in = new BufferedReader(new FileReader("m.txt"));
		HashSet<String> m = new HashSet<String>();
		while(true){
			String line = in.readLine();
			if(line == null || line.equals("")) break;
			m.add(line);
		}
		
		int [] edit = new int[s.size()*m.size()];
		int [] hist = new int[s.size()*m.size()];
		int [] mask = new int[s.size()*m.size()];
		int [] length = new int[s.size()*m.size()];
		
		int index = 0;
		for (String i : s) {
			for (String j : m) {
				
				edit[index] = edit(i,j);
				hist[index] = hist(i,j);
				mask[index] = mask(i,j);
				length[index] = Math.abs(i.length()-j.length());
				
				index ++;
			}
		}
		
//		Arrays.sort(edit);
//		Arrays.sort(hist);
//		Arrays.sort(mask);
//		Arrays.s
			
		// d = 0
		print("Number of mismatches = ",edit, 0);
		
		print("pruning power of histogram differance (2d) = ",hist, 0);
		print("pruning power of bit mask differance (2d) = ",mask, 0);
		print("pruning power of length differance (d) = ",length, 0);
		
		print("pruning power of histogram differance (2d-len) = ",hist,length, 0);
		print("pruning power of bit mask differance (2d-len) = ",mask,length, 0);
		
		
		// d = 1
		print("Number of mismatches = ",edit, 1);
		
		print("pruning power of histogram differance (2d) = ",hist, 2);
		print("pruning power of bit mask differance (2d) = ",mask, 2);
		print("pruning power of length differance (d) = ",length, 1);
		
		print("pruning power of histogram differance (2d-len) = ",hist,length, 2);
		print("pruning power of bit mask differance (2d-len) = ",mask,length, 2);
		
		// d = 2
		print("Number of mismatches = ",edit, 2);
		
		print("pruning power of histogram differance (2d) = ",hist, 4);
		print("pruning power of bit mask differance (2d) = ",mask, 4);
		print("pruning power of length differance (d) = ",length, 2);
		
		print("pruning power of histogram differance (2d-len) = ",hist,length, 4);
		print("pruning power of bit mask differance (2d-len) = ",mask,length, 4);
		
		// d = 3
		print("Number of mismatches = ",edit, 3);
		
		print("pruning power of histogram differance (2d) = ",hist, 6);
		print("pruning power of bit mask differance (2d) = ",mask, 6);
		print("pruning power of length differance (d) = ",length, 3);
		
		print("pruning power of histogram differance (2d-len) = ",hist,length, 6);
		print("pruning power of bit mask differance (2d-len) = ",mask,length, 6);
	}

	static void print(String ma,int[] s,int limit){
		int count = 0;
		for (int i = 0; i < s.length; i++) {
			if(s[i]>limit) count++;
		}
		System.out.println("$$" + ma + count + "$$");
	}
	
	static void print(String ma,int[] s,int[] len,int limit){
		int count = 0;
		for (int i = 0; i < len.length; i++) {
			if(s[i]>limit - len [i]) count++;
		}
		System.out.println("$$" + ma + count + "$$");
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
