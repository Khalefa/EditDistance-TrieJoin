package edu.alexu.dmlab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class TrieTraversal extends Trie {

	// public TrieTraversal(String file, int depth) {
	// super(file,depth);
	// }

	public void BuildActiveNode(int depth) {
		root.getDescendant(root.activeNodes, depth, 0);
		// System.out.println("root" + root.id + root.activeNodes);
		ArrayList<TrieNode> queue = new ArrayList<TrieNode>();
		for (TrieNode c : root.children.values()) {
			c.BuildActiveNodes(depth);
			queue.add(c);
		}
		if (Global.delete_not_needed_activenodes)
			root.activeNodes = null;
		while (!queue.isEmpty()) {
			// get the first node of the queue
			TrieNode n = queue.remove(0);
			// n.BuildActiveNodes(depth);// --using mine
			for (TrieNode c : n.children.values()) {
				c.BuildActiveNodes(depth);
				queue.add(c);
			}
			if (Global.delete_not_needed_activenodes && !n.leaf)
				n.activeNodes = null;
		}
	}

	public ArrayList<TrieNode> GetLeafs() {
		ArrayList<TrieNode> queue = new ArrayList<TrieNode>();
		ArrayList<TrieNode> leafs = new ArrayList<TrieNode>();

		for (TrieNode c : root.children.values()) {
			queue.add(c);
		}
		while (!queue.isEmpty()) {
			// get the first node of the queue
			TrieNode n = queue.remove(0);
			if (n.leaf)
				leafs.add(n);

			for (TrieNode c : n.children.values()) {
				queue.add(c);
			}
		}
		return leafs;
	}

	public TrieTraversal(String name, int wcount, int depth) {
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
				if (wcount == 0)
					break;
				wcount--;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BuildActiveNode(depth);
	}

	public HashSet<String> Matches() {
		HashSet<String> m = new HashSet<String>();
		for (TrieNode t : GetLeafs())
			m.addAll(t.getMatched());
		return m;
	}

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();

		String name = "test.txt";// word.format";//tiny.txt";//word.format";
		Global.prune = false; // 74
		TrieTraversal r = new TrieTraversal(name, 0,0);

		r.Print();
		// r.Matches();
		// r.Stats();

		System.out.println("active nodes" + r.getActiveNode());
		System.out.println("Matched" + r.Matches().size());
		for (String t : r.Matches()) {
			// System.out.println(t);
		}

		long endTime = System.currentTimeMillis();

		System.out.println("Time " + (endTime - startTime));

	}

}
