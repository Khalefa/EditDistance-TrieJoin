package edu.alexu.dmlab;

import java.util.HashMap;
import java.util.HashSet;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	static void a(String name, int depth) {
		long startTime = System.currentTimeMillis();
		TriePathStack r = new TriePathStack(name, depth);
		System.out.println("-----------------------------------------------");
		// r.DFS(1);
		HashSet<String> m = r.Matches();
		System.out.println(m.size());
		long endTime = System.currentTimeMillis();
		System.out.println("time" + (endTime - startTime));
	}

	static void b(String name, int depth) {
		long startTime = System.currentTimeMillis();

		// String name = "c:\\data\\word.format";//
		// "C:\\Users\\Mostafa\\Desktop\\dataset\\querylog.format";//
		// word.format";//tiny.txt";//word.format";
		TrieTraversal r = new TrieTraversal(name, depth);
		HashSet<String> m = r.Matches();
		System.out.println(m.size());
		long endTime = System.currentTimeMillis();

		System.out.println("time" + (endTime - startTime));
	}
/*word format 2
 * 	Words146034
	-----------------------------------------------
	5803239
	time140368
	Exception in thread "main" java.lang.NullPointerException
		at edu.alexu.dmlab.TrieNode.getMatched(TrieNode.java:275)
		at edu.alexu.dmlab.Trie.Matches(Trie.java:98)
		at edu.alexu.dmlab.Test.b(Test.java:30)
		at edu.alexu.dmlab.Test.main(Test.java:44)
*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String n = "c:\\data\\word.format";//word.format";
		//n = "test.txt";
		a(n, 2);
		TrieNode.counter = 0;
		Trie.caching_strings = new HashMap<String, Integer>();
		b(n, 2);
	}

}
