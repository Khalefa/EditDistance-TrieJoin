package edu.alexu.dmlab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public class TriePathStack extends Trie {

	public TriePathStack(String name,int depth) {
		// TODO Auto-generated constructor stub
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
		BuildActiveNode(depth);
	}
	public void BuildActiveNode(int depth) {
		root.getDescendant(root.activeNodes, depth, 0);
		// System.out.println("root" + root.id + root.activeNodes);
		Stack<TrieNode> StackTraversal = new Stack<TrieNode>();
		StackTraversal.push(root);
		Set<Entry<Character, TrieNode>> children = root.children.entrySet();
		Set<Entry<Character, TrieNode>> children2 = null;
		Iterator<Entry<Character, TrieNode>> ChildrenIterator = children.iterator();
		Iterator<Entry<Character, TrieNode>> ParentIterator = null;
		TrieNode child = null;
		TrieNode parent = null;
		boolean ChangeIterator = false;
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
				parent.activeNodes.clear();
				children = parent.children.entrySet();
				ChildrenIterator = children.iterator();
				ChangeIterator = false;
			}
			if(ChildrenIterator.hasNext())
			child = ChildrenIterator.next().getValue();
			else
				child = null;
		}
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TriePathStack r = new TriePathStack("test.txt", 1);
		r.Stats();
	}

}
