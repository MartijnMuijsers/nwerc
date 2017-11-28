
// Union-find is a data structure of a given number of nodes, where initially, all nodes form a group of themselevs
// Then, there is a merge operation, that allows the groups that two nodes are in to be merged
// Additionally, there is a find operation, that finds the root of the group that a node belongs to
// This way, it can be easily checked whether two nodes are in the same group by checking whether the roots of their groups are the same
// Running time: O(n) to initialize, near-O(1) for find and merge (optimal)
public class UnionFind {
	
	// Initializes a union-find structure for N nodes
	// Call this before using find(x) and merge(x, y)
	static void init(int N) {
		root = new int[N];
		rank = new int[N];
		for (int i = 1; i < N; i++) {
			root[i] = i;
		}
	}
	
	static int[] root, rank;
	
	// Find the root of the group x belongs to
	static int find(int x) {
		if (root[x] != x) {
			root[x] = find(root[x]);
		}
		return root[x];
	}
	
	// Merge the groups that x and y belong to
	static void merge(int x, int y) {
		x = find(x);
		y = find(y);
		if (x == y) {
			return;
		}
		if (rank[x] < rank[y]) {
			root[x] = y;
		} else if (rank[x] >= rank[y]) {
			root[y] = x;
			if (rank[x] == rank[y]) {
				rank[x]++;
			}
		}
	}
	
}
