import java.util.Arrays;

// The Floyd-Warshall algorithm solves the all-pairs shortest distance problem
// It computes the shortest distance between every pair of vertices in a graph
// Running time: O(|V|^3) (optimal for dense graphs, for sparse graphs use Dijkstra from each vertex)
public class FloydWarshall {
	
	static boolean negativeCycle;
	
	// Computes the shortest distance between every pair of vertices
	// Input: the edge lengths of the graph, where graph[i][i] must always be 0, and graph[i][j] must be Double.POSITIVE_INFINITY if there is no edge from i to j
	// This method sets negativeCycle to true if there exists a negative-weight cycle, and false otherwise
	// If there is a negative-weight cycle, the notion of shortest distance no longer applies, and the output distances are useless
	public double[][] floydWarshall(double[][] graph) {
		negativeCycle = false;
		double[][] distances;
		int n = graph.length;
		distances = Arrays.copyOf(graph, n);
		for (int k = 0; k < n; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					distances[i][j] = Math.min(distances[i][j], distances[i][k] + distances[k][j]);
				}
			}
			if (distances[k][k] < 0.0) {
				negativeCycle = true;
			}
		}
		return distances;
	}
	
}
