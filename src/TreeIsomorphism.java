import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

// An implementation to check whether two unrooted trees are isomorphic
// It does this by rooting the trees in their center(s) and subsequently labeling the nodes and comparing the labels
// Running time: O(n) (optimal)
public class TreeIsomorphism {
	
	// A helper class to store integers as a list
	static class IntegerList extends ArrayList<Integer> {}
	
	// A tree:
	// * Create a new Tree using the constructor Tree(V, E)
	// * Use addEdge to add edges
	// * Use checkIso to check whether two trees are isomorphic
	static class Tree {
		
		int V, E, root = 0, treeHeight = 0;
		IntegerList[] edges, children, verticesAtLevel;
		int[] levelsOfVertices, parents;
		
		// Normal constructor
		Tree(int V, int E) {
			this.V = V;
			this.E = E;
			this.edges = new IntegerList[V];
			for (int i = 0; i < V; i++) {
				this.edges[i] = new IntegerList();
			}
		}
		
		// Add an edge to this tree
		void addEdge(int a, int b) {
			edges[a].add(b);
			edges[b].add(a);
		}
		
		// Constructor used for cloning
		Tree(int V, int E, int root, IntegerList[] edges, IntegerList[] children, IntegerList[] verticesAtLevel, int[] levelsOfVertices, int[] parents, int treeHeight) {
			this.V = V;
			this.E = E;
			this.treeHeight = treeHeight;
			this.root = root;
			this.edges = edges;
			this.children = children;
			this.verticesAtLevel = verticesAtLevel;
			this.levelsOfVertices = levelsOfVertices;
			this.parents = parents;
		}
		
		// Trees can be cloned
		@Override
		public Tree clone() {
			return new Tree(V, E, root, edges, children, verticesAtLevel, levelsOfVertices, parents, treeHeight);
		}
		
		// Get the candidate roots of this tree (always has size 1 or 2)
		List<Integer> getCandidateRoots() {
			List<Integer> roots = new ArrayList<>();
			if (V <= 2) {
				for (int i = 0; i < V; i++) {
					roots.add(i);
				}
				return roots;
			}
			int left = V;
			int[] leftEdges = new int[V];
			boolean[] had = new boolean[V];
			Stack<Integer> todo = new Stack<>();
			for (int i = 0; i < V; i++) {
				leftEdges[i] = edges[i].size();
				if (leftEdges[i] == 1) {
					todo.add(i);
				}
			}
			while (true) {
				if (left <= 2) {
					for (int i = 0; i < V; i++) {
						if (!had[i]) {
							roots.add(i);
						}
					}
					return roots;
				}
				Stack<Integer> newTodo = new Stack<>();
				for (int i : todo) {
					had[i] = true;
					left--;
					leftEdges[i] = 0;
					for (int j : edges[i]) {
						if (leftEdges[j] > 0) {
							leftEdges[j]--;
							if (leftEdges[j] == 1) {
								newTodo.add(j);
							}
						}
					}
				}
				todo = newTodo;
			}
		}
		
		// Set the root of this graph to a given vertex
		void root(int root) {
			this.root = root;
			parents = new int[V];
			this.children = new IntegerList[V];
			for (int i = 0; i < V; i++) {
				this.children[i] = new IntegerList();
			}
			determineTreeHeight(root, 1, -1);
			verticesAtLevel = new IntegerList[V+1];
			levelsOfVertices = new int[V];
			for (int i = 0; i <= V; i++) {
				verticesAtLevel[i] = new IntegerList();
			}
			addVertexToLevel(treeHeight, root, -1);
		}
		
		// Determine the height of this tree
		void determineTreeHeight(int vertex, int height, int parent) {
			parents[vertex] = parent;
			if (height > treeHeight) {
				treeHeight = height;
			}
			for (int neighbor : edges[vertex]) {
				if (neighbor != parent) {
					children[vertex].add(neighbor);
					determineTreeHeight(neighbor, height+1, vertex);
				}
			}
		}
		
		// Assign a vertex to a given level
		void addVertexToLevel(int level, int vertex, int parent) {
			levelsOfVertices[vertex] = level;
			verticesAtLevel[level].add(vertex);
			for (int neighbor : children[vertex]) {
				if (neighbor != parent) {
					addVertexToLevel(level-1, neighbor, vertex);
				}
			}
		}
		
	}
	
	// Auxiliary method to compare two integer tuples (given as lists)
	static int compareTuples(IntegerList tuple1, IntegerList tuple2) {
		int i = 0;
		while (true) {
			if (i == tuple1.size()) {
				if (i == tuple2.size()) {
					return 0;
				}
				return -1;
			} else if (i == tuple2.size()) {
				return 1;
			} else {
				int valueCompare = Integer.compare(tuple1.get(i), tuple2.get(i));
				if (valueCompare != 0) {
					return valueCompare;
				}
			}
			i++;
		}
	}
	
	// Auxiliary method to check if two integer tuples (given as lists) are equal
	static boolean tuplesEquals(IntegerList tuple1, IntegerList tuple2) {
		return compareTuples(tuple1, tuple2) == 0;
	}
	
	// A comparator for comparing labels of a rooted tree
	static Comparator<int[]> rootedLabelComparator = (o1, o2) -> {
		int i = 0; while (true) {
			if (i == o1.length) {
				if (i == o2.length) {
					return 0;
				}
				return -1;
			} else if (i == o2.length) {
				return 1;
			} else {
				int valueCompare = Integer.compare(o1[i], o2[i]);
				if (valueCompare != 0) {
					return valueCompare;
				}
			}
			i++;
		}
	};
	
	// Returns whether two given trees are isomorphic
	static boolean checkIso(Tree T1, Tree T2x) {
		if (T1.V != T2x.V || T1.E != T2x.E) {
			return false;
		}
		T1.root(T1.getCandidateRoots().get(0));
		tryRoots: for (int T2root : T2x.getCandidateRoots()) {
			Tree T2 = T2x.clone();
			T2.root(T2root);
			Tree[] T = {T1, T2};
			int V = T1.V;
			int[][] labels = new int[2][V];
			int[][] L = new int[2][V];
			int[] Lsize = new int[2];
			Integer[][] tupleIs = new Integer[2][V];
			int[] tupleIsSize = new int[2];
			boolean[][] tupleMarked = new boolean[2][V];
			final IntegerList[][] tuples = new IntegerList[2][V];
			List<Comparator<Integer>> tupleComparators = new ArrayList<>(2);
			for (int Ti = 0; Ti < 2; Ti++) {
				final int fT = Ti;
				tupleComparators.add((o1, o2) -> compareTuples(tuples[fT][o1], tuples[fT][o2]));
				for (int i = 0; i < V; i++) {
					tuples[Ti][i] = new IntegerList();
					if (T[Ti].children[i].size() == 0 && T[Ti].levelsOfVertices[i] == 1) {
						L[Ti][Lsize[Ti]] = i;
						Lsize[Ti]++;
					}
				}
			}
			for (int level = 2; level <= V; level++) {
				for (int Ti = 0; Ti < 2; Ti++) {
					tupleIsSize[Ti] = 0;
					for (int Li = 0; Li < Lsize[Ti]; Li++) {
						int vertex = L[Ti][Li];
						int parent = T[Ti].parents[vertex];
						if (parent != -1) {
							tuples[Ti][parent].add(labels[Ti][vertex]);
							if (!tupleMarked[Ti][parent]) {
								tupleMarked[Ti][parent] = true;
								tupleIs[Ti][tupleIsSize[Ti]] = parent;
								tupleIsSize[Ti]++;
							}
						}
						Arrays.sort(tupleIs[Ti], 0, tupleIsSize[Ti], tupleComparators.get(Ti));
					}
				}
				if (tupleIsSize[0] != tupleIsSize[1]) {
					continue tryRoots;
				}
				for (int i = 0; i < tupleIsSize[0]; i++) {
					if (!tuplesEquals(tuples[0][tupleIs[0][i]], tuples[1][tupleIs[1][i]])) {
						continue tryRoots;
					}
				}
				for (int Ti = 0; Ti < 2; Ti++) {
					int currentLabel = 1; Lsize[Ti] = 0;
					for (int vertexAtLevel : T[Ti].verticesAtLevel[level]) {
						if (T[Ti].children[vertexAtLevel].size() == 0) {
							L[Ti][Lsize[Ti]] = vertexAtLevel;
							Lsize[Ti]++;
						}
					}
					for (int i = 0; i < tupleIsSize[Ti]; i++) {
						if (i > 0 && !tuplesEquals(tuples[Ti][tupleIs[Ti][i]], tuples[Ti][tupleIs[Ti][i-1]])) {
							currentLabel++;
						}
						labels[Ti][tupleIs[Ti][i]] = currentLabel;
						if (T[Ti].levelsOfVertices[tupleIs[Ti][i]] == level) {
							L[Ti][Lsize[Ti]] = tupleIs[Ti][i];
							Lsize[Ti]++;
						}
					}
				}
			}
			if (labels[0][T1.root] == labels[1][T2.root]) {
				return true;
			}
		} return false;
	}
	
}
