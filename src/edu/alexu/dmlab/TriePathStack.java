package edu.alexu.dmlab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public class TriePathStack extends Trie {

<<<<<<< HEAD
	public TriePathStack(String name, int wcount, int depth) {
		int words = 0;
=======
	public TriePathStack(String name,int depth) {
		// TODO Auto-generated constructor stub
>>>>>>> 9af9f816c1fef301cbeb30f677924d15d20b5df7
		root = (TrieNode) new TrieNode(null, '\0');
		try {
			String line;

			BufferedReader in = new BufferedReader(new FileReader(name));
<<<<<<< HEAD
			while (true) {
=======
			int words = 0;
			while (true) {

>>>>>>> 9af9f816c1fef301cbeb30f677924d15d20b5df7
				line = in.readLine();
				if (line == null || line.equals(""))
					break;
				insertString(line);
				words++;
<<<<<<< HEAD
				if (wcount == 0)
					break;
				wcount--;
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long s=System.currentTimeMillis();
		DFS(depth);
		Global.time=System.currentTimeMillis()-s;
		
	}

	// * u should not ever use this function */
	public void BuildActiveNode(int depth) throws Exception {
		// throw new Exception("Do not use me");
=======
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BuildActiveNode(depth);
	}
	public void BuildActiveNode(int depth) {
>>>>>>> 9af9f816c1fef301cbeb30f677924d15d20b5df7
		root.getDescendant(root.activeNodes, depth, 0);
		// System.out.println("root" + root.id + root.activeNodes);
		Stack<TrieNode> StackTraversal = new Stack<TrieNode>();
		StackTraversal.push(root);
<<<<<<< HEAD
		// root.instack = true;
		Set<Entry<Character, TrieNode>> children = root.children.entrySet();
		Set<Entry<Character, TrieNode>> children2 = null;
		Iterator<Entry<Character, TrieNode>> ChildrenIterator = children
				.iterator();
=======
		Set<Entry<Character, TrieNode>> children = root.children.entrySet();
		Set<Entry<Character, TrieNode>> children2 = null;
		Iterator<Entry<Character, TrieNode>> ChildrenIterator = children.iterator();
>>>>>>> 9af9f816c1fef301cbeb30f677924d15d20b5df7
		Iterator<Entry<Character, TrieNode>> ParentIterator = null;
		TrieNode child = null;
		TrieNode parent = null;
		boolean ChangeIterator = false;
<<<<<<< HEAD
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
=======
		if (ChildrenIterator.hasNext()) 
			child = ChildrenIterator.next().getValue();
		while(StackTraversal.size()!=0)
		{
			while(child != null)
			{
				parent = StackTraversal.peek();
				child.BuildActiveNodes(depth);
				//update active nodes
				for (TrieNode n : child.activeNodes.keySet())
					n.activeNodes.put(child, child.activeNodes.get(n));
				StackTraversal.push(child);
				children2 = parent.children.entrySet();
				ParentIterator = children2.iterator();
				if(!ParentIterator.hasNext())
					break;
				child = ParentIterator.next().getValue();
			}
			if(parent != StackTraversal.peek())
				ChangeIterator = true;
			parent = StackTraversal.pop();
			if(ChangeIterator)
			{
				//parent.activeNodes.clear();
>>>>>>> 9af9f816c1fef301cbeb30f677924d15d20b5df7
				children = parent.children.entrySet();
				ChildrenIterator = children.iterator();
				ChangeIterator = false;
			}
<<<<<<< HEAD
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
=======
			if(ChildrenIterator.hasNext())
			child = ChildrenIterator.next().getValue();
			else
				child = null;
		}
		
>>>>>>> 9af9f816c1fef301cbeb30f677924d15d20b5df7
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
<<<<<<< HEAD
		long startTime = System.currentTimeMillis();
		TriePathStack r = new TriePathStack("C:\\Users\\Mostafa\\Desktop\\dataset\\word.format", 50000, 1);
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
=======
		TriePathStack r = new TriePathStack("test.txt", 1);
		
		HashSet<String> m = r.Matches();
		for(String s: m){
			System.out.println(s);
		}
		
		r.Stats();
	}

}
>>>>>>> 9af9f816c1fef301cbeb30f677924d15d20b5df7
