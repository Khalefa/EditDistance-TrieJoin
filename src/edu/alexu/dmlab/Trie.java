package edu.alexu.dmlab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Trie {
	TrieNode root;

	public Trie() {
		root = new TrieNode(null, '\0');
	}

	public void BuildActiveNode(int depth) {
		root.getDescendant(root.activeNodes, depth, 0);
		System.out.println("root" + root.id + root.activeNodes);
		ArrayList<TrieNode> queue = new ArrayList<TrieNode>();
		for (TrieNode c : root.children.values()) {
			queue.add(c);
		}
		while (!queue.isEmpty()) {
			// get the first node of the queue
			TrieNode n = queue.remove(0);
			n.BuildActiveNodes(depth);// --using mine
			for (TrieNode c : n.children.values()) {
				queue.add(c);

			}
		}
	}

	public static void insertString(TrieNode root, String s) {
		TrieNode v = root;
		for (char ch : s.toCharArray()) {
			TrieNode next = v.children.get(ch);
			if (next == null)
				v.children.put(ch, next = new TrieNode(v, ch));
			v = next;
		}
		v.leaf = true;
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

	public Trie(String name, int depth) {
		root = new TrieNode(null, '\0');
		try {
			String line;

			BufferedReader in = new BufferedReader(new FileReader(name));
			int words = 0;
			while (true) {

				line = in.readLine();
				if (line == null || line.equals(""))
					break;
				insertString(root, line);
				words++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BuildActiveNode(depth);
	}

	public static void main(String[] args) throws Exception {
		
	}
}
