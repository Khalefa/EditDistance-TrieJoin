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
		// root.activeNodes.clear();
		while (!queue.isEmpty()) {
			// get the first node of the queue
			TrieNode n = queue.remove(0);
			// n.BuildActiveNodes(depth);// --using mine
			for (TrieNode c : n.children.values()) {
				c.BuildActiveNodes(depth);
				queue.add(c);
			}
			 n.activeNodes=null;
		}
	}

	public TrieTraversal(String name, int depth) {
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
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BuildActiveNode(depth);
		
	}

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();

		String name = "tiny.txt";// "C:\\Users\\Mostafa\\Desktop\\dataset\\querylog.format";//
									// word.format";//tiny.txt";//word.format";
		TrieTraversal r = new TrieTraversal(name, 1);
		long endTime = System.currentTimeMillis();
		HashSet<String> m = r.Matches();
		for (String s : m) {
			System.out.println(s);
		}

		long sendTime = System.currentTimeMillis();
		System.out.println(sendTime - startTime);
		System.out.println(endTime - startTime);
		//r.Stats();
	}

}
