<<<<<<< HEAD
package edu.alexu.dmlab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

import edu.alexu.util.Hist;
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
	// * histogram */
	Hist hist = new Hist();

	public Interval length_interval = new Interval();
	public int subtries = 1;

	public TrieNode(TrieNode p, char x) {
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
			if (n == parent && depth > 0)
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
		// for (TrieNode n : activeNodes.keySet())
		// s = s + n.id + " " + n.character + ",";
		return id + ":" + Text() + ":" + length_interval.toString() + ":" + s
				+ "\n" + hist + "\n";

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
			int e = activeNodes.get(n);
			if (!length_interval.intersect(n.length_interval, d - e)) {
				todel.add(n);
			}
		}
		// single branch pruning
		if (this.parent.children.size() == 1) {
			if (activeNodes.containsKey(parent))
				if (!leaf && !parent.leaf)
					todel.add(parent);
		}
		// remove parent of parents, up to depth
		// count pruning
		if (subtries == 1) {
			for (TrieNode n : children.values()) { // it should be only one
				todel.add(n);
			}
		}
		// hash pruning A*
		if (Global.hist_prune)
			for (TrieNode n : activeNodes.keySet()) {
				int e = activeNodes.get(n);
				int diff = hist.Diff(n.hist);
				if (diff > 2 * (d - e)) {
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
=======
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
	int Subtries  = 0;//used for count pruning
	int[] minHist = new int[26];
	int[] maxHist = new int[26];
	public void initalize(TrieNode p, char x) {
		this.id = counter;
		counter++;
		parent = p;
		character = x;
	}

	protected int min(int i, Object v) {
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
protected boolean lengthPruning(int minlength1,int minlength2,int maxlength1,int maxlength2,int depth)
{
int count = 0;
if(abs(minlength2-minlength1)>depth)
	count++;
if(abs(maxlength2-maxlength1)>depth)
	count++;
if(abs(maxlength2-minlength1)>depth)
	count++;
if(abs(minlength2-maxlength1)>depth)
	count++;
if(count>2)
	return true;
return false;
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
		if (k > depth )
			return descendents;
		descendents.put(this, k);
		while (!queue.isEmpty()) {
			// get the first node of the queue
			pair p = queue.remove(0);
			// add children to the queue
			if (p.depth < depth) {
				for (TrieNode c : p.n.children.values()) {
					//this part is important for trie traversal to work properly
					if(lengthPruning(c.MinLength,p.n.MinLength,c.MaxLength,p.n.MaxLength, depth))
						continue;
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
	protected void mybuildActiveNodes(int depth) {

		Map<TrieNode, Integer> parentActiveNodes = parent.activeNodes;
		
		// deletion
		// add all p active node to this, with distance +1 if possible
		for (TrieNode n : parentActiveNodes.keySet()) {
			if(lengthPruning(this.MinLength,n.MinLength,this.MaxLength,n.MaxLength, depth))
				continue;
			
			if (n == parent) 
			{
			if(Subtries>=2)//count pruning 
					activeNodes.put(n, 1);
			}
			else {
				int l = parentActiveNodes.get(n) + 1;

				if (l <= depth)
					activeNodes.put(n, l);
			}
		}
		for (TrieNode p : parentActiveNodes.keySet()) {
			// if p.c=c // we have a match
			if(lengthPruning(this.MinLength,p.MinLength,this.MaxLength,p.MaxLength, depth))
				continue;
			if(p==parent && Subtries<2)
				continue;
			int d = parentActiveNodes.get(p);
			if (p.character == character) {
				getDescendant(activeNodes, depth, d);
			}

			for (TrieNode c : p.children.values()) {
				if(lengthPruning(this.MinLength,c.MinLength,this.MaxLength,c.MaxLength, depth))
					continue;
				if(c==parent && Subtries<2)
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
>>>>>>> 9af9f816c1fef301cbeb30f677924d15d20b5df7
}