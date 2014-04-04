package edu.alexu.util;

public class Interval {

	public Interval() {
		// TODO Auto-generated constructor stub
	}

	int min = Integer.MAX_VALUE;
	int max = Integer.MIN_VALUE;

	public void add(int l) {
		if (min > l)
			min = l;
		if (max < l)
			max = l;
	}

	@Override
	public String toString() {

		return "[" + min + "," + max + "]";
	};

	public boolean intersect(Interval i) {
		return min <= i.max && i.min <= max;
	}

	public static int distance(int min, int max, int min1, int max1) {
		if (min <= max1 && min1 <= max)
			return 0;
		else
			return Math.min(Math.abs(min - max1), Math.abs(min1 - max));
	}

	public int distance(Interval i) {
		if (intersect(i))
			return 0;
		else
			return Math.min(Math.abs(min - i.max), Math.abs(i.min - max));
	}

	public boolean intersect(Interval i, int d) {
		return min - d <= i.max && i.min <= max + d;
	}
}
