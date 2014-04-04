package edu.alexu.dmlab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public class TriePathStack extends Trie {

	public TriePathStack(String name, int wcount, int depth) {
		int words = 0;
		root = (TrieNode) new TrieNode(null, '\0');
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
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		DFS(depth);
	}

	// * u should not ever use this function */
	public void BuildActiveNode(int depth) throws Exception {
		// throw new Exception("Do not use me");
		root.getDescendant(root.activeNodes, depth, 0);
		// System.out.println("root" + root.id + root.activeNodes);
		Stack<TrieNode> StackTraversal = new Stack<TrieNode>();
		StackTraversal.push(root);
		// root.instack = true;
		Set<Entry<Character, TrieNode>> children = root.children.entrySet();
		Set<Entry<Character, TrieNode>> children2 = null;
		Iterator<Entry<Character, TrieNode>> ChildrenIterator = children
				.iterator();
		Iterator<Entry<Character, TrieNode>> ParentIterator = null;
		TrieNode child = null;
		TrieNode parent = null;
		boolean ChangeIterator = false;
		if (ChildrenIterator.hasNext())
			child = ChildrenIterator.next().getValue();
		while (StackTraversal.size() != 0) {
			while (child != null) {
				parent = StackTraversal.peek();
				child.BuildActiveNodes(depth);
				// update active nodes
				for (TrieNode n : child.activeNodes.keySet())
					n.activeNodes.put(child, child.activeNodes.get(n));
				StackTraversal.push(child);
				// child.instack = true;
				children2 = parent.children.entrySet();
				ParentIterator = children2.iterator();
				if (!ParentIterator.hasNext())
					break;
				child = ParentIterator.next().getValue();
			}
			if (parent != StackTraversal.peek()) {
				ChangeIterator = true;
			}

			parent = StackTraversal.pop();
			// parent.instack = false;
			if (ChangeIterator) {
				parent.activeNodes.clear();
				children = parent.children.entrySet();
				ChildrenIterator = children.iterator();
				ChangeIterator = false;
			}
			if (ChildrenIterator.hasNext())
				child = ChildrenIterator.next().getValue();
			else
				child = null;
		}

	}

	// Implement a straightforward DFS algorithm on the trie
	public void DFS(int depth) {
		Stack<TrieNode> stack = new Stack<TrieNode>();
		stack.push(root);
		root.getDescendant(root.activeNodes, depth, 0);

		while (!stack.isEmpty()) {
			// pop the element from stack
			TrieNode n = stack.pop();
			// System.out.println(n.id-1);

			for (TrieNode x : n.children.values()) {
				stack.push(x);
				x.BuildActiveNodes(depth);
			}

			if (!n.leaf && Global.delete_not_needed_activenodes)
				n.activeNodes = null;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long startTime = System.currentTimeMillis();
		TriePathStack r = new TriePathStack("c:\\data\\word.format", 50000, 1);
		System.out.println("-----------------------------------------------");
		// r.DFS(1);
		HashSet<String> m = r.Matches();
		System.out.println(m.size());
		long endTime = System.currentTimeMillis();
		System.out.println(endTime - startTime);
		// for (String s : m) {
		// System.out.println(s);
		// }

		// r.Stats();
	}

}