package edu.alexu.dmlab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.util.Map;

public class TrieDynamic extends Trie {

	public TrieDynamic() {
		// TODO Auto-generated constructor stub
	}

	public static void insertString(TrieNode root, String s, int depth) {
		root.getDescendant(root.activeNodes, depth, 0);
		TrieNode v = root;
		Map<TrieNode, Integer> activeNodes = v.activeNodes;
		TrieNode next = v;
		for (char ch : s.toCharArray()) {
			next = v.children.get(ch);
			if (next == null)
				v.children.put(ch, next = new TrieNode(v, ch));
			for (TrieNode n : v.activeNodes.keySet())
				n.activeNodes.put(v, v.activeNodes.get(n));
			next.BuildActiveNodes(depth);
			activeNodes = next.activeNodes;
			v = next;
		}
		for (TrieNode n : next.activeNodes.keySet())
			n.activeNodes.put(v, v.activeNodes.get(n));
		v.leaf = true;
	}

	public TrieDynamic(String name, int depth) {
		root = new TrieNode(null, '\0');
		try {
			String line;

			BufferedReader in = new BufferedReader(new FileReader(name));
			int words = 0;
			while (true) {

				line = in.readLine();
				if (line == null || line.equals(""))
					break;
				insertString(root, line, depth);
				words++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// BuildActiveNode(depth);
	}

	public static void main(String[] args) {
		TrieDynamic r = new TrieDynamic("tiny.txt", 1);
		r.Stats();
	}

}
