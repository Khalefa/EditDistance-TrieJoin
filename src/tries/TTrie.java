package tries;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.rmi.CORBA.Util;

import edu.alexu.dmlab.Global;
import edu.alexu.dmlab.Trie;
import edu.alexu.util.Hist;
import edu.alexu.util.Utils;

public class TTrie {
	TN root;
	public TTrie() {
		root = new TN(null, '\0');
	}

	public void insertString(String s) {
		TN v = root;
		for (char ch : s.toCharArray()) {
				TN next = v.children[Utils.id(ch)];
			if (next == null) {
				v.children[Utils.id(ch)]= next = new TN(v, ch);
			} 
			v = next;
		}
		v.leaf = true;
	}

	public TTrie(String name, int wcount) {
		root = new TN(null, '\0');
		int words = 0;
		try {
			String line;

			BufferedReader in = new BufferedReader(new FileReader(name));

			while (true) {
				line = in.readLine();
				if (line == null || line.equals(""))
					break;
				insertString(line);
				words++;
				if (wcount == 0)
					break;
				wcount--;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Global.limit = words;
	}

	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();

		String name = "test.txt";// c:\\data\\querylog.format";// word.format";//tiny.txt";//word.format";
		Trie r = new Trie(name, 0);
		// r.Stats();
		// for(TrieNode t: r.GetLeafs()){
		// if (t.Text()=="sarah")
		// System.out.println(t.Text()+" "+ t.getMatched());
		// }
		long endTime = System.currentTimeMillis();
		r.Print();
		System.out.println(endTime - startTime);
	}

}
