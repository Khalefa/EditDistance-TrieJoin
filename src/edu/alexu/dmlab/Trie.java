package edu.alexu.dmlab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
<<<<<<< HEAD
=======

>>>>>>> 9af9f816c1fef301cbeb30f677924d15d20b5df7
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

<<<<<<< HEAD
import edu.alexu.util.Hist;

public class Trie {
	TrieNode root;

	// auxiliaries access to nodes directly for debugging only
	// HashMap<Integer, TrieNode> nodes = new HashMap<Integer, TrieNode>();

=======
import java.util.Map;

public class Trie {
	TrieNode root;
	public static Map<String, Integer> caching_strings=new HashMap<String, Integer>();
>>>>>>> 9af9f816c1fef301cbeb30f677924d15d20b5df7
	public Trie() {
		root = new TrieNode(null, '\0');
	}

<<<<<<< HEAD
	public void insertString(String s) {
		int[] curr_hist = new int[27];
		if (Global.hist_prune)
			Hist.build_hist(s, curr_hist);

		TrieNode v = root;
		v.length_interval.add(s.length());
		if (Global.hist_prune)
			root.hist.insert(curr_hist);
		// nodes.put(v.id, v);
		int d = 1;
		for (char ch : s.toCharArray()) {
			int x = Hist.id(ch);
			if (Global.hist_prune)
				curr_hist[x]--;

			TrieNode next = v.children.get(ch);
			if (next == null) {
				v.children.put(ch, next = new TrieNode(v, ch));
			} else
				next.subtries++;
			next.length_interval.add(s.length() - d);
			if (Global.hist_prune)
				next.hist.insert(curr_hist);
			// nodes.put(next.id, next);
			d++;
			v = next;
=======
	

	public void insertString(String s) {
		TrieNode v = root;
		if(caching_strings.get(s)!=null)
			return;
		else
			caching_strings.put(s, 1);
		int StringLength = s.length();
		int CharIndex = 0;
		if(v.MaxLength< StringLength)
		{
			v.MinLength = v.MaxLength;
			v.MaxLength = StringLength;
		}
		else
			v.MaxLength = StringLength;
		
		v.Subtries++;
		for (char ch : s.toCharArray()) {
			TrieNode next = v.children.get(ch);
			if (next == null)
				v.children.put(ch, next = new TrieNode(v, ch));
			next.Subtries++;
			if(next.MaxLength< (StringLength-CharIndex))
			{
				next.MinLength = next.MaxLength;
				next.MaxLength = StringLength - CharIndex;
			}
			else
				v.MaxLength = StringLength - CharIndex;
			v = next;
			CharIndex++;
>>>>>>> 9af9f816c1fef301cbeb30f677924d15d20b5df7
		}
		v.leaf = true;
	}

<<<<<<< HEAD
	public Trie(String name, int wcount) {
=======
	public Trie(String name) {
>>>>>>> 9af9f816c1fef301cbeb30f677924d15d20b5df7
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
<<<<<<< HEAD
				if (wcount == 0)
					break;
				wcount--;
=======
>>>>>>> 9af9f816c1fef301cbeb30f677924d15d20b5df7
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
<<<<<<< HEAD
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

=======

		
	}
>>>>>>> 9af9f816c1fef301cbeb30f677924d15d20b5df7
	public HashSet<String> Matches() {
		HashSet<String> m = new HashSet<String>();
		for (TrieNode t : GetLeafs())
			m.addAll(t.getMatched());
		return m;
	}

	public ArrayList<TrieNode> GetLeafs() {
<<<<<<< HEAD
=======

>>>>>>> 9af9f816c1fef301cbeb30f677924d15d20b5df7
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

<<<<<<< HEAD
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
		Trie r = new Trie(name, 0);
=======

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
		System.out.println(printActiveNodes(root));
	}
	public static int printActiveNodes(TrieNode node){
		int ret = 0;
		System.out.println(node.character+"  "+node.id +"  "+node.Subtries+"=> "+node.activeNodes.toString());
		ret += node.activeNodes.size();
		for (Character ch : node.children.keySet()) {
			ret += printActiveNodes(node.children.get(ch));
		}
		return ret;
	}
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();

		String name = "c:\\data\\querylog.format";// word.format";//tiny.txt";//word.format";
		Trie r = new Trie(name);
>>>>>>> 9af9f816c1fef301cbeb30f677924d15d20b5df7
		// r.Stats();
		// for(TrieNode t: r.GetLeafs()){
		// if (t.Text()=="sarah")
		// System.out.println(t.Text()+" "+ t.getMatched());
		// }
		long endTime = System.currentTimeMillis();
<<<<<<< HEAD
		r.Print();
=======
>>>>>>> 9af9f816c1fef301cbeb30f677924d15d20b5df7
		System.out.println(endTime - startTime);
	}

}
