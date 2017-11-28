import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

// Dijkstra is an algorithm that solves the single-source shortest path problem
// This problem is to find the distance in a graph, starting from a single point
// This ca be the distance to all other points, or to a specific goal
// Running time: O(|E|+|V|*log(|V|)) (optimal)
public class Dijkstra {
	
	// A helper class to store edges as a list
	static class EdgeList extends ArrayList<Edge> {}
	
	// An edge in the graph, with a 'from' and 'to' vertex, and a length 'length'
	// The 'from' variable is not used in Dijkstra's algorithm
	static class Edge {
		
		int from, to, length;
		
		Edge(int from,int to,int length) {
			this.from=from;
			this.to=to;
			this.length=length;
		}
		
	}
	
	// A path from the starting point, ending at 'node' and with length 'length'
	static class Path implements Comparable<Path> {
		
		int node;
		long length;
		
		Path(int node, long length) {
			this.node = node;
			this.length = length;
		}
		
		// Enables paths to be compared (sorted) on length (or otherwise on ending node)
		@Override
		public int compareTo(Path o) {
			int dCompare = Long.compare(length, o.length);
			if (dCompare != 0) {
				return dCompare;
			}
			return Integer.compare(node, o.node);
		};
		
	}
	
	// N: number of nodes
	// edges: each edges[a] is a list of the edges from node a
	// Calculates the minimum distance from initialNode to goal
	static long dijkstraWithGoal(int N, EdgeList[] edges, int initialNode, int goal) {
		long[] best = new long[N];
		Arrays.fill(best, Long.MAX_VALUE);
		Queue<Path> Q = new PriorityQueue<>();
		best[initialNode] = 0;
		Q.add(new Path(initialNode, 0));
		while (Q.size() > 0) {
			Path p = Q.poll();
			if (p.length >= best[goal]) {
				break;
			}
			if (p.length > best[p.node]) {
				continue;
			}
			for (Edge e : edges[p.node]) {
				long newLength = p.length+e.length;
				if (newLength < best[e.to]) {
					best[e.to] = newLength;
					Q.add(new Path(e.to, newLength));
				}
			}
		}
		return best[goal];
	}
	
	// N: number of nodes
	// edges: each edges[a] is a list of the edges from node a
	// Calculates the minimum distance from initialNode to all nodes in the graph
	// The difference between this and dijkstraWithGoal is only:
	// * One less if-check in the while-loop
	// * Returning the whole array of minimum distances instead of only one value
	static long[] dijkstraWithoutGoal(int N, EdgeList[] edges, int initialNode) {
		long[] best = new long[N];
		Arrays.fill(best, Long.MAX_VALUE);
		Queue<Path> Q = new PriorityQueue<>();
		best[initialNode] = 0;
		Q.add(new Path(initialNode, 0));
		while (Q.size() > 0) {
			Path p = Q.poll();
			if (p.length > best[p.node]) {
				continue;
			}
			for (Edge e : edges[p.node]) {
				long newLength = p.length+e.length;
				if (newLength < best[e.to]) {
					best[e.to] = newLength;
					Q.add(new Path(e.to, newLength));
				}
			}
		}
		return best;
	}
	
}
