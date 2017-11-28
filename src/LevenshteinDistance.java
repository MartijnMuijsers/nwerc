
// The Levenshtein distance is a common form of edit distance: the minimum number of replacements, deletions or insertions (all per character) needed to transform one string into another string
// This implementation is a variation of the Wagner–Fischer algorithm
// Running time: O(length1*length2) (optimal)
public class LevenshteinDistance {
	
	// Computes the Levenshtein distance between two given arrays using dynamic programming
	// This is a Levenshtein distance implementation with specific costs for replacement, deletion and insertion
	// For a default Levenshtein distance, these three costs are all equal to 1
	static int levenshteinDistanceSpecificCosts(int[] s1, int[] s2, int replacementCost, int deletionCost, int insertionCost) {
		int l1 = s1.length + 1;
		int l2 = s2.length + 1;
		int[] cost = new int[l1];
		int[] newCost = new int[l1];
		int[] cumulativeCost1 = new int[l1];
		int[] cumulativeCost2 = new int[l2];
		for (int i = 1; i < l1; i++) {
			cumulativeCost1[i] = cumulativeCost1[i - 1] + deletionCost;
		}
		for (int j = 1; j < l2; j++) {
			cumulativeCost2[j] = cumulativeCost2[j - 1] + insertionCost;
		}
		for (int i = 0; i < l1; i++) {
			cost[i] = cumulativeCost1[i];
		}
		for (int j = 1; j < l2; j++) {
			newCost[0] = cumulativeCost2[j];
			for (int i = 1; i < l1; i++) {
				boolean match = s1[i - 1] == s2[j - 1];
				int replacementCaseCost = cost[i - 1] + (match ? 0 : replacementCost);
				int insertionCaseCost = cost[i] + insertionCost;
				int deletionCaseCost = newCost[i - 1] + deletionCost;
				newCost[i] = Math.min(Math.min(replacementCaseCost, insertionCaseCost), deletionCaseCost);
			}
			int[] swap = cost;
			cost = newCost;
			newCost = swap;
		}
		return cost[l1 - 1];
	}
	
}
