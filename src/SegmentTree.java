
// Segment trees are a data structure that allow for logarithmic-time updates or queries on a range of data (such as an array)
// Two implementations are provided: one that allows range updates and index queries, and one that allows index updates and range queries
// These queries and updates always run in O(log(n)), which is optimal
// Note: a type of segment tree exists that allows for range updates and range queries, both in logarithmic time, but it is not included here
// An alternative to segment trees with the same complexity but lower memory usage are Fenwick trees
public class SegmentTree {
	
	// Type 1: Range update, index query
	// This implementation allows for adding a value to an entire range (can be modified to any operation), or getting the value at a specific index
	static class SegmentTree1 {
		
		int l, r, half, value;
		SegmentTree1 sub1, sub2;
		
		// Create a segment tree for an inclusive index range l to r
		SegmentTree1(int l, int r) {
			this.l = l;
			this.r = r;
			if (l != r) {
				half = (l + r) / 2;
				sub1 = new SegmentTree1(l, half);
				sub2 = new SegmentTree1(half + 1, r);
			}
		}
		
		// Change the values in the inclusive index range from l to r (adds the given value to the existing value)
		void change(int l, int r, int change) {
			if (l <= this.l && r >= this.r) {
				value += change;
				return;
			}
			if (l <= this.half) {
				sub1.change(l, r, change);
			}
			if (r >= this.half + 1) {
				sub2.change(l, r, change);
			}
		}
		
		// Get the value at index i
		int value(int i) {
			if (l == r) {
				return value;
			}
			if (i <= half) {
				return sub1.value(i) + value;
			}
			return sub2.value(i) + value;
		}
		
	}
	
	// Type 2: Range query, index update
	// This implementations allows for adding a value to a specific index (can be modified to any operation), or getting the sum of entire range
	static class SegmentTree2 {
		
		int l, r, half, value;
		SegmentTree2 sub1, sub2;
		
		// Create a segment tree for an inclusive index range l to r
		SegmentTree2(int l, int r) {
			this.l = l;
			this.r = r;
			if (l != r) {
				half = (l + r) / 2;
				sub1 = new SegmentTree2(l, half);
				sub2 = new SegmentTree2(half + 1, r);
			}
		}
		
		// Get the sum of all values in an inclusive index range from l to r
		int value(int l, int r) {
			if (l <= this.l && r >= this.r) {
				return value;
			}
			int toReturn = 0;
			if (l <= this.half) {
				toReturn += sub1.value(l, r);
			}
			if (r >= this.half + 1) {
				toReturn += sub2.value(l, r);
			}
			return toReturn;
		}
		
		// Change the value at index i (adds the given value to the existing value)
		void change(int i, int change) {
			value += change;
			if (l == r) {
				return;
			}
			if (i <= this.half) {
				sub1.change(i, change);
			}
			if (i >= this.half + 1) {
				sub2.change(i, change);
			}
		}
		
	}
	
}
