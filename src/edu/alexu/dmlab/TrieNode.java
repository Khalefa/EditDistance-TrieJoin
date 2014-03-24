package edu.alexu.dmlab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;


class TrieNode {

	public TrieNode(TrieNode p, char x) {
		// super(p, x);
		initalize(p, x);
		
		activeNodes = new HashMap<TrieNode, Integer>();
	}

	static int counter = 0;
	int id;
	Map<Character, TrieNode> children = new TreeMap<Character, TrieNode>();
	boolean leaf;
	TrieNode parent;
	char character;
	Map<TrieNode, Integer> activeNodes = null;// = new HashMap<TrieNode,
												// Integer>();
	int MaxLength = 0;//holding maximum length of string in its subtrie for length pruning
	int MinLength = 0;//holding minimum length of string in its subtrie for length pruning

	public void initalize(TrieNode p, char x) {
		this.id = counter;
		counter++;
		parent = p;
		character = x;
	}

	private int min(int i, Object v) {
		if (v == null)
			return i;
		int vv = (Integer) v;
		if (vv > i)
			return i;
		return vv;
	}
private int abs(int number)
{
	return (number < 0) ? -number : number;
	
}
	public Map<TrieNode, Integer> getDescendant(
			Map<TrieNode, Integer> descendents, int depth, int k) {
		class pair {
			public TrieNode n;
			public int depth;

			public pair(TrieNode n, int depth) {
				this.n = n;
				this.depth = depth;
			}
		}

		ArrayList<pair> queue = new ArrayList<pair>();
		queue.add(new pair(this, k));
		if (k > depth)
			return descendents;
		descendents.put(this, k);
		while (!queue.isEmpty()) {
			// get the first node of the queue
			pair p = queue.remove(0);
			// add children to the queue
			if (p.depth < depth) {
				for (TrieNode c : p.n.children.values()) {
					Object v = descendents.get(c);
					int vv = min(p.depth + 1, v);
					if (vv <= depth) {
						descendents.put(c, vv);
						queue.add(new pair(c, vv));
					}
				}
			}
		}
		return descendents;
	}

	public HashMap<TrieNode, Integer> getDescendant(int depth, int k) {
		HashMap<TrieNode, Integer> descendents = new HashMap<TrieNode, Integer>();
		getDescendant(descendents, depth, k);
		return descendents;
	}

	/* Author and maintained By Moustafa */
	private void buildActiveNodes(int depth) {
		Map<TrieNode, Integer> parentActiveNodes = parent.activeNodes;

		for (TrieNode n : parentActiveNodes.keySet()) {
			if (n.id == parent.id && depth >= 1)
				activeNodes.put(n, parentActiveNodes.get(n) + 1);// checking
																	// for
																	// the
																	// parent
			for (char c : n.children.keySet()) {
				int d = parentActiveNodes.get(n);
				// if(n.children.get(c).parent.fromParent!=fromParent &&
				// c!=fromParent)d++;
				if (c != character && n.children.get(c).parent.id != this.id)
					d++;
				// if(d<= depth &&
				// h(maxHist,n.children.get(c).minHist,depth-d))
				// activeNodes.put(n.children.get(c), d);
				if (d <= depth)
					activeNodes.put((TrieNode) n.children.get(c), d);
			}
		}

	}

	// by Khalefa
	private void mybuildActiveNodes(int depth) {

		Map<TrieNode, Integer> parentActiveNodes = parent.activeNodes;
		// deletion
		// add all p active node to this, with distance +1 if possible
		for (TrieNode n : parentActiveNodes.keySet()) {
			if(  abs(n.MaxLength -this.MaxLength) >depth && abs(n.MinLength -this.MinLength) >depth)
				continue;
			if (n == parent)
				activeNodes.put(n, 1);
			else {
				int l = parentActiveNodes.get(n) + 1;

				if (l <= depth)
					activeNodes.put(n, l);
			}
		}
		for (TrieNode p : parentActiveNodes.keySet()) {
			// if p.c=c // we have a match
			int d = parentActiveNodes.get(p);
			if(abs(p.MaxLength -this.MaxLength) >depth && abs( p.MinLength -this.MinLength) >depth)
				continue;
			if (p.character == character) {
				getDescendant(activeNodes, depth, d);
			}

			for (TrieNode c : p.children.values()) {
				if(abs(c.MaxLength -this.MaxLength) >depth && abs(c.MinLength -this.MinLength) >depth)
					continue;
				if (c == this)
					continue;
				// insertion
				if (c.character == character) {// we have a match
					c.getDescendant(activeNodes, depth, d);
				} else

				if (d <= depth) {

					int m = min(d + 1, activeNodes.get(c));
					if (m <= depth)
						activeNodes.put(c, m);
				}

			}
		}
		// add myself & my Descendant
		activeNodes.put(this, 0);
		getDescendant(activeNodes, depth, 0);

		// System.out.println("Active node " + id + ":" + Text() + ":"
		// + activeNodes);
	}

	public void BuildActiveNodes(int depth) {
		mybuildActiveNodes(depth);
	}

	public String Text() {
		String s = "";
		if (parent != null)
			s = parent.Text() + character;
		return s;
	}

	@Override
	public String toString() {
		return id + ":" + character;

	};

	@Override
	public int hashCode() {
		return id;
	}

	public HashSet<String> getMatched() {
		if (!leaf)
			return null;
		HashSet<String> l = new HashSet<String>();
		for (TrieNode n : activeNodes.keySet()) {
			if (n == this)
				continue;
			if (n.leaf)
				l.add(Text() + "-" + n.Text());
		}
		return l;
	}
}