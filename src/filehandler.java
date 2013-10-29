import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.StringTokenizer;


public class filehandler {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BufferedReader in = new BufferedReader(new FileReader("inter_test.txt"));
		PrintWriter out = new PrintWriter("sm.txt");
		HashSet<String> o = new HashSet<String>();
		while(true){
			String line = in.readLine();
			if(line == null || line.equals("")) break;
			if(!line.startsWith("s")) continue;
			String [] s = line.split(" ");
			for (int i = 5; i < s.length; i++) { // m => 3 s => 5
				o.add(s[i]);
			}
		}
		in.close();
		System.out.println(o.size());
		for (String string : o) {
			out.println(string);
		}
		out.close();
	}

}
