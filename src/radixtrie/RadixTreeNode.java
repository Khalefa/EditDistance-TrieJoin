/*
The MIT License

Copyright (c) 2008 Tahseen Ur Rehman

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/

package radixtrie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

class RadixTreeNode<T> implements Comparable<RadixTreeNode<T>> {
    private String key;

    private List<RadixTreeNode<T>> childern;
    public RadixTreeNode<T> parent;
    public Map<RadixTreeNode<T>, Integer> activenodes;//holding active nodes and the depth value will be stored in the value of the node
    public boolean visited ;//holding boolean if this node is visited or not
    private boolean real;
    public int siblingindex = 0;
    private T value;

    /**
     * intailize the fields with default values to avoid null reference checks
     * all over the places
     */
    public RadixTreeNode(RadixTreeNode<T> root) {
        key = "";
        childern = new ArrayList<RadixTreeNode<T>>();
        activenodes  = new TreeMap<RadixTreeNode<T>, Integer>();
        real = false;
        visited = false;
        parent = root;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T data) {
        this.value = data;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String value) {
        this.key = value;
    }

    public boolean isReal() {
        return real;
    }

    public void setReal(boolean datanode) {
        this.real = datanode;
    }

    public List<RadixTreeNode<T>> getChildern() {
        return childern;
    }
    public void setChildern(List<RadixTreeNode<T>> childern) {
        this.childern = childern;
        for(RadixTreeNode<T> node : childern)
        	node.parent = this;
    }
    public Map<RadixTreeNode<T>, Integer> getActivenodes() {
        return activenodes;
    }
    public void setActivenodes(Map<RadixTreeNode<T>, Integer> activenode) {
        this.activenodes = activenode;
    }
    public /*Map<RadixTreeNode<T>, Integer>*/ void BuildActivenodes(int depth){
    	Map<RadixTreeNode<T>, Integer> activeNodes = new TreeMap<RadixTreeNode<T>, Integer>();
		Map<RadixTreeNode<T>, Integer> parentActiveNodes = parent.getActivenodes();
		// deletion
		// add all p active node to this, with distance +1 if possible
		for (RadixTreeNode<T> n : parentActiveNodes.keySet()) {
			if (n == parent && depth > 0)
				activeNodes.put(n, 1);
			else{
				try{
				
				int l = parentActiveNodes.get(n) + n.key.length() ;//to be checked

				if (l <= depth)
					activeNodes.put(n, l);
				}
				catch(Exception e)
				{
					System.out.println(key+" -->Node: "+n.value+" --> parent depth "+parentActiveNodes.get(n)+" -->current key length "+n.key.length()+"--->"+parentActiveNodes.toString());
					
				}
			}
		}
		for (RadixTreeNode<T> p : parentActiveNodes.keySet()) {
			// if p.c=c // we have a match
			if(p==parent)
				continue;
			try{
			int d = parentActiveNodes.get(p);
		
			if (p.key == key) {
				getDescendant(activeNodes, depth, d);
			}

			for (RadixTreeNode<T> c : p.childern) {
				if (c == this)
					continue;
				// insertion
				if (c.key == key) {// we have a match
					c.getDescendant(activeNodes, depth, d);
				} else

				if (d <= depth) {

					int m = min(d + c.key.length(), activeNodes.get(c));//to be checked
					if (m <= depth)
						activeNodes.put(c, m);
				}

			}
			
			}
			catch(Exception e)
			{
				System.out.println(key+" -->Node: "+p.value+" --> parent depth "+parentActiveNodes.get(p)+" -->current key length "+p.key.length()+"--->"+parentActiveNodes.toString());
				
			}
		}
		// add myself & my Descendant
		activeNodes.put(this, 0);

		// System.out.println("Active node " + id + ":" + Text() + ":"
		// + activeNodes);
		setActivenodes(activeNodes);
    }

	private int min(int i, Object v) {
		if (v == null)
			return i;
		int vv = (Integer) v;
		if (vv > i)
			return i;
		return vv;
	}
	public Map<RadixTreeNode<T>, Integer> getDescendant(
			Map<RadixTreeNode<T>, Integer> descendents, int depth, int k) {
		class pair {
			public RadixTreeNode<T> n;
			public int depth;

			public pair(RadixTreeNode<T> n, int depth) {
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
				for (RadixTreeNode<T> c : p.n.childern) {
					Object v = descendents.get(c);
					int vv = min(p.depth + c.key.length() , v);//needs to be checked
					if (vv <= depth) {
						descendents.put(c, vv);
						queue.add(new pair(c, vv));
					}
				}
			}
		}
		return descendents;
	}

	public HashMap<RadixTreeNode<T>, Integer> getDescendant(int depth, int k) {
		HashMap<RadixTreeNode<T>, Integer> descendents = new HashMap<RadixTreeNode<T>, Integer>();
		getDescendant(descendents, depth, k);
		return descendents;
	}
	public HashSet<String> getMatched() {
		if (childern.size()>0)
			return null;
		HashSet<String> l = new HashSet<String>();
		for (RadixTreeNode<T> n : activenodes.keySet()) {
			if (n == this)
				continue;
			if (n.getChildern().size()==0)
				l.add(Text() + "-" + n.Text());
		}
		return l;
	}
	public String Text() {
		String s = "";
		if (parent != null)
			s = parent.Text() + key;
		return s;
	}
	@Deprecated
	public int getNumberOfMatchingCharacters(String key) {
		int numberOfMatchingCharacters = 0;
        while (numberOfMatchingCharacters < key.length() && numberOfMatchingCharacters < this.getKey().length()) {
            if (key.charAt(numberOfMatchingCharacters) != this.getKey().charAt(numberOfMatchingCharacters)) {
                break;
            }
            numberOfMatchingCharacters++;
        }
		return numberOfMatchingCharacters;
	}

    
    @Override
    public String toString() {
		return key;
    	
    }

	@Override
	public int compareTo(RadixTreeNode<T> arg0) {
		// TODO Auto-generated method stub
		if(arg0.equals(this))
			return 0;
		else
			return 1;
	}
}
