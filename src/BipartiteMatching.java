import java.util.ArrayList;
import java.util.Arrays;

// Bipartite matching is the problem of creating a maximal matching a bipartite graph
// This implementation is a variation of the Hopcroft–Karp algorithm
// Running time: O(|E|*sqrt(|V|)) (optimal)
public class BipartiteMatching {
	
	// A helper class to store integers as a list
	static class IntegerList extends ArrayList<Integer> {}
	
	// A bipartite graph:
	// * Create a new BipartiteGraph using the constructor
	// * Use addEdge to add edges
	// * Call maxMatching to get the size of the maximal matching in this bipartite graph
	// * maxMatching also fills match, which describes such a maximal matching, matching each right node to a left node (or -1 if not matched)
	static class BipartiteGraph {
		
		// n = number of nodes on the left
		// m = nodes on the right
		int n, m;
		// Used internally by the algorithm: L, visited, match
		// match can be used after running maxMatching to get a maximal matching in the graph
		IntegerList[] L;
		boolean[] visited;
		int[] match;
		
		// Construct a bipartite graph with n left nodes and m right nodes
		BipartiteGraph(int n, int m) {
			this.n = n;
			this.m = m;
			L = new IntegerList[n];
			for (int i = 0; i < n; i++) {
				L[i] = new IntegerList();
			}
			visited = new boolean[m];
			match = new int[m];
		}
		
		// Add an edge between a left node 0 <= v1 < n and a right node 0 <= v2 < m
		void addEdge(int v1, int v2) {
			L[v1].add(v2);
		}
		
		// Get the size of the maximal matching in this bipartite graph (and fill match with such a matching)
		int maxMatching() {
			int ans = 0;
			Arrays.fill(match, -1);
			for (int i = 0; i < n; i++) {
				Arrays.fill(visited, false);
				ans += dfs(i)?1:0;
			}
			return ans;
		}
		
		// Depth-first search implementation used by the algorithm
		boolean dfs(int u) {
			for (int i = L[u].size()-1 ; i >= 0; i--) {
				int v = L[u].get(i);
				if (!visited[v]) {
					visited[v] = true;
					if (match[v] == -1 || dfs(match[v])) {
						match[v] = u;
						return true;
					}
				}
			}
			return false;
		}
		
	}
	
}
