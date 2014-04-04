package edu.alexu.dmlab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import edu.alexu.util.*;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	static void test(String name, int limit, int depth) {
		long startTime = System.currentTimeMillis();

		Trie r;
		if (Global.alg == "ps")
			r = new TriePathStack(name, limit, depth);
		else if (Global.alg == "tt")
			r = new TrieTraversal(name, limit, depth);
		else
			r = new Trie(name, limit);

		// r.Print();
		HashSet<String> m = r.Matches();
		System.out.print(Global.str());
		System.out.print("\t" + r.getActiveNode());
		System.out.print("\t " + m.size());

		long endTime = System.currentTimeMillis();
		System.out.println("\t" + (endTime - startTime));
	}

	static void runtest() {
		String n = "c:\\data\\word.format";
		// n = "test.txt";

		int d = 2;
		int limit = -2;

		TrieNode.counter = 0;
		test(n, limit, d);

	}

	static void test() {
		// Global.prune = false;
		// Global.delete_not_needed_activenodes = false;
		// runtest();
		/*
		 * Global.prune = true; Global.delete_not_needed_activenodes = false;
		 * runtest(); Global.prune = false; Global.delete_not_needed_activenodes
		 * = true; runtest();
		 */
		Global.prune = true;
		Global.delete_not_needed_activenodes = true;
		runtest();
	}

	public static void main(String[] args) {
		// EditDistance e=new EditDistance("c:\\data\\word.format",50000, 1);
		// 50000 count54518

		// active nodes212798Matched52821
		// time2907
		// test();

		Global.alg = "ps";
		test();
		// Global.alg = "tt";
		// test();
	}

}