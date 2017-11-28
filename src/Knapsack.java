import java.util.ArrayList;
import java.util.List;

// The knapsack problem is the problem of selecting items from a list, so that their total weight does not exceed a given capacity and their total value is maximized
// Running time: O(n*capacity) (optimal, if capacity is small)
public class Knapsack {
	
	// Returns a list of items to choose to achieve the maximum value, without the total weight exceeding the capacity
	// Input:
	// * capacity :the maximum capacity (maximum total weight of the chosen items)
	// * values: the values of the items
	// * weights: the weights of the items
	// Can be easily modified (see comments below) to return the optimal achieveable value directly
	static List<Integer> knapsack(int capacity, int[] values, int[] weights) {
		int n = values.length;
		int[][] bestValue = new int[n + 1][capacity + 1];
		for (int i = 1; i <= n; i++) {
			int itemIndex = i - 1;
			for (int j = 0; j <= capacity; j++) {
				int weightIfNotIncluded = bestValue[i - 1][j];
				if (j >= weights[itemIndex]) {
					int weightIfIncluded = bestValue[i - 1][j - weights[itemIndex]] + values[itemIndex];
					bestValue[i][j] = Math.max(weightIfIncluded, weightIfNotIncluded);
				} else {
					bestValue[i][j] = weightIfNotIncluded;
				}
			}
		}
		// If you only want the resulting optimal value, you can return bestValue[n][capacity] here
		// The code makes a list of the choice of items that leads to the optimal value
		// This is done by backtracking
		List<Integer> chosen = new ArrayList<>();
		int j = capacity;
		for (int i = n; i > 0; i--) {
			if (bestValue[i][j] != bestValue[i - 1][j]) {
				int itemIndex = i - 1;
				chosen.add(itemIndex);
				j -= weights[itemIndex];
			}
		}
		return chosen;
	}
	
}
