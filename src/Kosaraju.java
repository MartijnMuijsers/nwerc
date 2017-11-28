import java.util.List;

// Kosaraju's algorithm finds the strongly connected components in a directed graph
// Since the algorithm is recursive and the number of nodes may be quite large, this implementation uses the anti-deep recursion stack (see AntiDeepRecursionStack.java)
// Running time: O(n) (optimal)
public class Kosaraju {
	
	// A node (vertex) in the graph, with a list of children and parents
	// (A directed edge a -> b means b is a child of a)
	static class Node {
		List<Node> children;
		List<Node> parents;
		boolean visited = false;
		Node assigned = null;
	}
	
	// Kosaraju's algorithm for strongly connected components
	// * Input: nodes (list of nodes in the graph, with correctly initialized children and parents)
	// * Changes: L, Li (intermediary), node.visited for each node
	// * Output: node.assigned for each node, which is the root of the SCC of the node
	static Node[] nodes;
	static Node[] L;
	static int Li;
	
	// Runs the algorithm (uses input 'nodes', writes output 'node.assigned' for each node)
	static void kosaraju() {
		L = new Node[nodes.length];
		Li = nodes.length - 1;
		for (Node node : nodes) {
			// Call to deep method
			visit(node);
			AntiDeepRecursionStack.executeStack();
		}
		for (Node node : L) {
			// Call to deep method
			assign(node, node);
			AntiDeepRecursionStack.executeStack();
		}
	}
	
	// Visit a node (depth-first search auxiliary method used by the algorithm)
	static void visit(Node node) {
		if (!node.visited) {
			// After recursive calls
			AntiDeepRecursionStack.push(() -> {
				L[Li] = node;
				Li--;
			});
			// Recursive calls
			for (Node node2 : node.children) {
				AntiDeepRecursionStack.push(() -> visit(node2));
			}
			// Before recursive calls
			node.visited = true;
		}
	}
	
	// Assign a node to a subcomponent with a specific root
	static void assign(Node node, Node root) {
		if (node.assigned == null) {
			// Recursive calls
			for (Node node2 : node.parents) {
				AntiDeepRecursionStack.push(() -> assign(node2, root));
			}
			// Before recursive calls
			node.assigned = root;
		}
	}
	
}
