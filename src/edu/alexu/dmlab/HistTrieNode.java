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

}
