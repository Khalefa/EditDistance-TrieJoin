package edu.alexu.dmlab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

import edu.alexu.util.Interval;

class TrieNode {
	static int counter = 0;
	int id;
	Map<Character, TrieNode> children = new TreeMap<Character, TrieNode>();
	boolean leaf;
	TrieNode parent;
	char character;
	Map<TrieNode, Integer> activeNodes = null;// = new HashMap<TrieNode,
												// Integer>();
	public Interval length_interval = new Interval();
	public int subtries = 1;

	public TrieNode(TrieNode p, char x) {
		// super(p, x);
		initalize(p, x);
		activeNodes = new HashMap<TrieNode, Integer>();
	}

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

	/* Authored and maintained By Moustafa */
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
			if (n == parent && depth >0)
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
			if (p.character == character) {
				getDescendant(activeNodes, depth, d);
			}

			for (TrieNode c : p.children.values()) {
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
		if (Global.prune)
			prune(depth);
	}

	public String Text() {
		String s = "";
		if (parent != null)
			s = parent.Text() + character;
		return s;
	}

	@Override
	public String toString() {
		String s = "";
		for (TrieNode n : activeNodes.keySet())
			s = s + n.id + " " + n.character + ",";
		return id + ":" + Text() + ":" + length_interval.toString() + ":" + s;

	};

	@Override
	public int hashCode() {
		return id;
	}

	//
	// prune the active node set
	public void prune(int d) {
		ArrayList<TrieNode> todel = new ArrayList<TrieNode>();
		// length pruning
		for (TrieNode n : activeNodes.keySet()) {
			if (!length_interval.intersect(n.length_interval, d)) {
				todel.add(n);
			}

		}
		// single branch pruning
		if (this.parent.children.size() == 1) {
			todel.add(parent);
		}
		// count pruning
		if(subtries==1){
			for(TrieNode n: children.values()){ //it should be only one
				todel.add(n);
			}
		}
		for (TrieNode n : todel) {
			activeNodes.remove(n);
		}
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