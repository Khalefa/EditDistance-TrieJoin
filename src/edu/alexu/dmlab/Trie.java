package edu.alexu.dmlab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Trie {
	TrieNode root;

	// auxilaries access to nodes directly for debugging only
	HashMap<Integer, TrieNode> nodes = new HashMap<Integer, TrieNode>();

	public Trie() {
		root = new TrieNode(null, '\0');
	}

	public void insertString(String s) {

		TrieNode v = root;
		v.length_interval.add(s.length());
		nodes.put(v.id, v);
		int d = 1;
		for (char ch : s.toCharArray()) {
			//v.subtries++;
			TrieNode next = v.children.get(ch);
			if (next == null) {
				v.children.put(ch, next = new TrieNode(v, ch));
			} else
				next.subtries++;
			next.length_interval.add(s.length() - d);
			nodes.put(next.id, next);
			d++;
			v = next;
		}
		v.leaf = true;
	}

	public Trie(String name, int wcount) {
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
				if(wcount==0)break;
				wcount--;
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

	public HashSet<String> Matches() {
		HashSet<String> m = new HashSet<String>();
		for (TrieNode t : GetLeafs())
			m.addAll(t.getMatched());
		return m;
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

	public ArrayList<TrieNode> GetNodes() {

		ArrayList<TrieNode> queue = new ArrayList<TrieNode>();
		ArrayList<TrieNode> nodes = new ArrayList<TrieNode>();

		for (TrieNode c : root.children.values()) {
			queue.add(c);
		}
		nodes.add(root);
		while (!queue.isEmpty()) {
			// get the first node of the queue
			TrieNode n = queue.remove(0);
			nodes.add(n);
			for (TrieNode c : n.children.values()) {
				queue.add(c);
			}
		}
		return nodes;
	}

	public long getActiveNode() {
		long count = 0;
		for (TrieNode n : GetNodes())
			if (n.activeNodes != null)
				count += n.activeNodes.size();
		return count;
	}

	public void Print() {
		for (TrieNode n : GetNodes())
			System.out.println(n);

	}

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();

		String name = "test.txt";// c:\\data\\querylog.format";// word.format";//tiny.txt";//word.format";
		Trie r = new Trie(name,0);
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
