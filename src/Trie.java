import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.security.KeyStore.Builder;
import java.util.*;


public class Trie {
	static class TrieNode {
		static int counter = 0;
		int id;
		Map<Character, TrieNode> children = new TreeMap<Character, TrieNode>();
		boolean leaf;
		TrieNode parent;
		char fromParent;
		Map<TrieNode,Integer> activeNodes = new HashMap<Trie.TrieNode, Integer>();
		int [] minHist = new int[26];
		int [] maxHist = new int[26];
		public TrieNode(TrieNode p,char x) {
			this.id = counter;
			counter++;
			parent = p;
			fromParent = x;//holding current node character
		}
		void buildActiveNodes(int depth){
			Map<TrieNode,Integer> parentActiveNodes = parent.activeNodes;
			
			for (TrieNode n : parentActiveNodes.keySet()) {
				if(n.id==parent.id && depth>=1) activeNodes.put(n, parentActiveNodes.get(n)+1);//checking for the parent
				for (char c : n.children.keySet()) {
					int d = parentActiveNodes.get(n);
					//if(n.children.get(c).parent.fromParent!=fromParent && c!=fromParent)d++;
					if ( c != fromParent && n.children.get(c).parent.id!=this.id) d++;
					//if(d<= depth && h(maxHist,n.children.get(c).minHist,depth-d)) activeNodes.put(n.children.get(c), d);
					if(d<= depth ) activeNodes.put(n.children.get(c), d);
				}
			}
			
		}
		private boolean h(int[] maxHist2, int[] minHist2, int x) {
			int sum = 0;
			for (int i = 0; i < minHist2.length; i++) {
				sum += Math.max(0, minHist2[i]-maxHist2[i]);
			}
			return sum<=x;
		}
		@Override
		public int hashCode() {
			return id;
		}
		void minHist(){
			Arrays.fill(minHist, 1<<28);
			for (char c : children.keySet()) {
				children.get(c).minHist();
				int [] temp = children.get(c).minHist;
				for (int i = 0; i < temp.length; i++) {
					if (i == (c-'a')) temp[i]++;
					minHist[i] = Math.min(minHist[i], temp[i]);
				}
			}
			for (int i = 0; i < minHist.length; i++) {
				if(minHist[i] == 1<<28)minHist[i] = 0;
			}
		}
		void maxHist(){
			for (char c : children.keySet()) {
				children.get(c).maxHist();
				int [] temp = children.get(c).maxHist;
				for (int i = 0; i < temp.length; i++) {
					if (i == (c-'a')) temp[i]++;
					maxHist[i] = Math.max(maxHist[i], temp[i]);
				}
			}
		}
		@Override
		public String toString() {
			return "TrieNode [id=" + id + "]";
		}
	}

	
	static void buildActiveNodes(TrieNode root, int depth){
		root(root,root.activeNodes,0,depth);
		children(root,depth);
	}
	
	private static void children(TrieNode root, int depth) {
		for (char c : root.children.keySet()) {
			root.children.get(c).buildActiveNodes(depth);
			children(root.children.get(c), depth);
		}
	}

	static void root(TrieNode node, Map<TrieNode,Integer> activeNodes,int depth,int limit){
		if(depth > limit)return;
		
		activeNodes.put(node, depth);
		
		for (char c : node.children.keySet()) {
			root(node.children.get(c),activeNodes , depth+1, limit);
		}
		
	}
	
	public static void insertString(TrieNode root, String s) {
		TrieNode v = root;
		for (char ch : s.toCharArray()) {
			TrieNode next = v.children.get(ch);
			if (next == null)
				v.children.put(ch, next = new TrieNode(v,ch));
			v = next;
		}
		v.leaf = true;
	}

	public static void printSorted(TrieNode node, String s) {
		for (Character ch : node.children.keySet()) {
			printSorted(node.children.get(ch), s + ch);
		}
		if (node.leaf) {
			System.out.println(s);
		}
	}
	
	public static void printID(TrieNode node){
		System.out.println(node.fromParent+" => "+node.id);
		for (Character ch : node.children.keySet()) {
			printID(node.children.get(ch));
		}
	}
	public static void printMinHist(TrieNode node){
		System.out.println(node.fromParent+" => "+Arrays.toString(node.minHist));
		for (Character ch : node.children.keySet()) {
			printMinHist(node.children.get(ch));
		}
	}
	public static void printMaxHist(TrieNode node){
		System.out.println(node.fromParent+" => "+Arrays.toString(node.maxHist));
		for (Character ch : node.children.keySet()) {
			printMaxHist(node.children.get(ch));
		}
	}
	public static int printActiveNodes(TrieNode node){
		int ret = 0;
		System.out.println(node.fromParent+" => "+node.activeNodes.toString());
		ret += node.activeNodes.size();
		for (Character ch : node.children.keySet()) {
			ret += printActiveNodes(node.children.get(ch));
		}
		return ret;
	}
	// Usage example
	public static void main(String[] args) throws Exception {
		TrieNode root = new TrieNode(null,'\0');
		BufferedReader in = new BufferedReader(new FileReader("test.txt"));
		while(true){
			String line = in.readLine();
			if(line == null || line.equals("")) break;
			insertString(root, line);
		}
//		insertString(root, "hello");
//		insertString(root, "world");
//		insertString(root, "hi");
		root.minHist();
		root.maxHist();
		buildActiveNodes(root, 2);
//		printID(root);
		System.out.println("============================");
//		printMinHist(root);
		System.out.println("============================");
//		printMaxHist(root);
		System.out.println("============================");
		System.out.println(printActiveNodes(root));
		System.out.println("============================");
		System.out.println(root.children.toString());
	}
}
