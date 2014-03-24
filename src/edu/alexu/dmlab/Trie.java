package edu.alexu.dmlab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;

public class Trie {
	TrieNode root;

	public Trie() {
		root = new TrieNode(null, '\0');
	}

	

	public void insertString(String s) {
		TrieNode v = root;
		int StringLength = s.length();
		int CharIndex = 0;
		if(v.MaxLength< StringLength)
		{
			v.MinLength = v.MinLength;
			v.MaxLength = StringLength;
		}
		else
			v.MaxLength = StringLength;
		for (char ch : s.toCharArray()) {
			TrieNode next = v.children.get(ch);
			if (next == null)
				v.children.put(ch, next = new TrieNode(v, ch));
			if(next.MaxLength< (StringLength-CharIndex))
			{
				next.MinLength = next.MinLength;
				next.MaxLength = StringLength - CharIndex;
			}
			else
				v.MaxLength = StringLength - CharIndex;
			v = next;
			CharIndex++;
		}
		v.leaf = true;
	}

	public Trie(String name) {
		root = (TrieNode) new TrieNode(null, '\0');
		try {
			String line;

			BufferedReader in = new BufferedReader(new FileReader(name));
			int words = 0;
			while (true) {

				line = in.readLine();
				if (line == null || line.equals(""))
					break;
				insertString(line);
				words++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	public void Stats() {
		HashMap<TrieNode, Integer> h1 = root.getDescendant(1, 0);
		HashMap<TrieNode, Integer> h2 = root.getDescendant(2, 0);
		HashMap<TrieNode, Integer> h3 = root.getDescendant(3, 0);
		HashMap<TrieNode, Integer> h100 = root.getDescendant(100, 0);
		System.out.println("h1 " + h1.size());
		System.out.println("h2 " + h2.size());
		System.out.println("h3 " + h3.size());
		System.out.println("h100 " + h100.size());
		System.out.println(root.counter);
	}

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();

		String name = "c:\\data\\querylog.format";// word.format";//tiny.txt";//word.format";
		Trie r = new Trie(name);
		// r.Stats();
		// for(TrieNode t: r.GetLeafs()){
		// if (t.Text()=="sarah")
		// System.out.println(t.Text()+" "+ t.getMatched());
		// }
		long endTime = System.currentTimeMillis();
		System.out.println(endTime - startTime);
	}

}
