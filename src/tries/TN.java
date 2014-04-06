package tries;

import java.util.Map;
import java.util.TreeMap;

class TN {
	static int counter = 0;
	int id;
	//Map<Character, TN> children = new TreeMap<Character, TN>();
	TN []children =new TN[27];
	boolean leaf;
	TN parent;
	char character;

	public TN(TN p, char x) {
		initalize(p, x);
	}

	public void initalize(TN p, char x) {
		this.id = counter;
		counter++;
		parent = p;
		character = x;
	}


}