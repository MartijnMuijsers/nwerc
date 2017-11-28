import java.util.Stack;

// Sometimes, recursive solutions are harder to use due to the recursion depth exceeding the stack size
// In this case, the stack can be simulated in normal memory to avoid stack overflow errors
// This class provides such a stack
// How to use the anti-deep recursion stack:
// -> A 'deep method' is a method that recursively calls itself or other methods and may thereby lead to deep recursion
// 1. Make sure every call to a deep method from a non-deep method (such as visit(node) as called from kosaraju() in Kosaraju.java) is followed by executeStack()
// 2. Make sure, in the body of each deep method, statements are made in reverse and wrapped by push(..) (the statements before the first recursive call need not be wrapped by push(..))
public class AntiDeepRecursionStack {
	
	// An action that can be pushed to the stack
	static interface Action {
		public void execute();
	}
	
	// The simulated stack
	static Stack<Action> stack = new Stack<>();
	
	// Executes all actions on the stack
	static void executeStack() {
		while (stack.size() > 0) {
			stack.pop().execute();
		}
	}
	
	// Adds a new action to the stack
	static void push(Action action) {
		stack.add(action);
	}
	
}
