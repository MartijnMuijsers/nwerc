import java.util.LinkedList;

// Ford-Fulkerson solves the max-flow problem
// This calculates the maximum flow from a source to a sink in a flow network (a directed graph where each edge has a maximum capacity)
// Running time: O(|E|*f), where f is the resulting maximum flow (sometimes but not always optimal, Edmonds-Karp is an alternative)
public class FordFulkerson {
	
	/**
	 * Explanation of variables used by the implementation
	 * 
	 * Uses (input, this must be set before running fordFulkerson()):
	 * N = number of nodes
	 * c = matrix of capacities (n by n)
	 * S = source node
	 * t = sink node
	 * 
	 * Intermediary (used internally by the implementation):
	 * p = parent nodes of nodes
	 * f = flow through nodes
	 * 
	 * Sets (output):
	 * rc = residual capacities (n by n)
	 * (the flow between two nodes i and j can be calculated as c[i][j]-rc[i[j])
	 * max_flow = max flow
	 */
	static int N, S, t;
	static int[][] c;
	static int[] f;
	static int[] p;
	static int[][] rc;
	static int max_flow;
	
	// Run the algorithm (uses input variables and fills output variables)
	static void fordFulkerson() {
	    max_flow = 0;
	    rc = new int[N][];
	    f = new int[N];
	    p = new int[N];
	    for (int i = 0; i < N; i++) {
	    	rc[i] = c[i].clone();
	    }
	    while (true) {
			long path_flow = bfs();
			if (path_flow == 0) {
		 	    break;
			}
			for (int v = t; v != S; v = p[v]) {
			    int u = p[v];
			    rc[u][v] -= path_flow;
			    rc[v][u] += path_flow;
			}
	        max_flow += path_flow;
	    }
	}
	
	// Breadth-first search implementation used by the algorithm
	static int bfs() {
	    boolean[] visited = new boolean[N];
	    visited[S] = true;
	    LinkedList<Integer> q = new LinkedList<>();
	    q.add(S);
	    p[S] = -1;
	    f[S] = Integer.MAX_VALUE;
	    while (!q.isEmpty()){
			int u = q.poll();
			for (int v = 0; v < N; v++){
		  	    if (!visited[v] && rc[u][v] > 0) {
			    	q.add(v);
			    	p[v] = u;
			    	visited[v] = true;
			    	f[v] = Math.min(f[u], rc[u][v]);
			    }
			}
	    }
	    return visited[t]?f[t]:0;
	}
	
}
