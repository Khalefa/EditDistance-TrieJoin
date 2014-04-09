<<<<<<< HEAD
package edu.alexu.dmlab;

import java.util.Arrays;

public class HistTrieNode extends TrieNode {

	public HistTrieNode(TrieNode p, char x) {
		super(p, x);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private boolean h(int[] maxHist2, int[] minHist2, int x) {
		int sum = 0;
		for (int i = 0; i < minHist2.length; i++) {
			sum += Math.max(0, minHist2[i] - maxHist2[i]);
		}
		return sum <= x;
	}

	int[] minHist = new int[26];
	int[] maxHist = new int[26];

	// if(d<= depth && h(maxHist,n.children.get(c).minHist,depth-d))
	// activeNodes.put(n.children.get(c), d);
	// +//
	void minHist() {
		Arrays.fill(minHist, 1 << 28);
		for (char c : children.keySet()) {
			((HistTrieNode) children.get(c)).minHist();
			int[] temp = ((HistTrieNode) children.get(c)).minHist;
			for (int i = 0; i < temp.length; i++) {
				if (i == (c - 'a'))
					temp[i]++;
				minHist[i] = Math.min(minHist[i], temp[i]);
			}
		}
		for (int i = 0; i < minHist.length; i++) {
			if (minHist[i] == 1 << 28)
				minHist[i] = 0;
		}
	}

	void maxHist() {
		for (char c : children.keySet()) {
			((HistTrieNode) children.get(c)).maxHist();
			int[] temp = ((HistTrieNode) children.get(c)).maxHist;
			for (int i = 0; i < temp.length; i++) {
				if (i == (c - 'a'))
					temp[i]++;
				maxHist[i] = Math.max(maxHist[i], temp[i]);
			}
		}
	}

}
=======
package edu.alexu.dmlab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class HistTrieNode extends TrieNode {

	public HistTrieNode(TrieNode p, char x) {
		super(p, x);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private boolean h(int x) {//x holds the depth
		int sum = 0;
		for (int i = 0; i < minHist.length; i++) {
			sum += Math.max(0, minHist[i] - maxHist[i]);
		}
		return sum <= x;
	}

	void minHist() {
		Arrays.fill(minHist, 1 << 28);
		for (char c : children.keySet()) {
			((HistTrieNode) children.get(c)).minHist();
			int[] temp =((HistTrieNode) children.get(c)).minHist;
			for (int i = 0; i < temp.length; i++) {
				if (i == (c - 'a'))
					temp[i]++;
				minHist[i] = Math.min(minHist[i], temp[i]);
			}
		}
		for (int i = 0; i < minHist.length; i++) {
			if (minHist[i] == 1 << 28)
				minHist[i] = 0;
		}
	}

	void maxHist() {
		for (char c : children.keySet()) {
			((HistTrieNode) children.get(c)).maxHist();
			int[] temp =((HistTrieNode) children.get(c)).maxHist;
			for (int i = 0; i < temp.length; i++) {
				if (i == (c - 'a'))
					temp[i]++;
				maxHist[i] = Math.max(maxHist[i], temp[i]);
			}
		}
	}
	@Override protected  void mybuildActiveNodes(int depth) {

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

				if (l <= depth && h(depth-l))
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
					if (m <= depth && h(depth-m))
						activeNodes.put(c, m);
				}

			}
		}
		// add myself & my Descendant
		activeNodes.put(this, 0);
		getDescendant(activeNodes, depth, 0);

	}
	@Override
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
					if (vv <= depth &&   h(depth-vv)) {
						descendents.put(c, vv);
						queue.add(new pair(c, vv));
					}
				}
			}
		}
		return descendents;
	}

}
>>>>>>> 9af9f816c1fef301cbeb30f677924d15d20b5df7
