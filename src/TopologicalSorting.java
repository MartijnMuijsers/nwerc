import java.util.ArrayList;
import java.util.List;

// Topological sorting is the problem of sorting nodes in a directed graph so that each node will appears in the resulting list later than all of its ancestors
// In the case that the directed graph contains a cycle, topological sorting is impossible
// This implementation is a variation on depth-first search, and either successfully topologically sorts the given nodes, or reports that there exists a cycle
// Running time: O(n) (optimal)
public class TopologicalSorting {
	
	// A helper class to store integers as a list
	static class IntegerList extends ArrayList<Integer> {}
	
	// Input: N (number of nodes), edges (array of side N, contains per node v a list of nodes that v has an edge to)
	static int N;
	static IntegerList[] edges;
	// Intermediary: used by the algorithm
	static int[] marked;
	// Output: ordered list of nodes, with size N
	static List<Integer> L;
	
	// Executes the algorithm
	// Returns true if topological sorting succeeded, returns false if not: in that case, the graph contains a cycle
	static boolean topologicalSort() {
		L = new ArrayList<>(N);
		marked = new int[N];
		for (int i = 0; i < N; i++) {
			if (marked[i] == 0) {
				if (!visit(i)) {
					return false;
				}
			}
		}
		return true;
	}
	
	// Auxiliarly visit method used by the algorithm (variation on depth-first search)
	static boolean visit(int n) {
		if (marked[n] == 1) {
			return false;
		}
		if (marked[n] == 0) {
			marked[n] = 1;
			for (int m : edges[n]) {
				if (!visit(m)) {
					return false;
				}
			}
			marked[n] = 2;
			L.add(n);
		}
		return true;
	}
	
}
