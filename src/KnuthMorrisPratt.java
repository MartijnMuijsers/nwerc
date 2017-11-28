
// Knuth-Morris-Pratt solves the problem of substring search, through preprocessing the string to a jumptable
// This can be either determining whether a substring is present in a string, or counting the number of occurences of a substring
// Running time: O(n) (optimal)
public class KnuthMorrisPratt {
	
	// Pattern (substring)
	static int[] A;
	// Text to search in (string)
	static int[] B;
	// Jumptable
	static int[] jt;
	
	// Fills jt according to pattern A (call this once before calling kmpSearch() or kmpCount())
	static void kmpPreprocess() {
		jt = new int[A.length + 1];
		int i = 0, j = -1;
		jt[0] = -1;
		while (i < A.length) {
			while (j >= 0 && A[i] != A[j]) {
				j = jt[j];
			}
			i++;
			j++;
			jt[i] = j;
		}
	}
	
	// Returns whether A exists in B (requires jt to be filled)
	static boolean kmpSearch() {
		int i = 0, j = 0;
		while (i < B.length) {
			while (j >= 0 && B[i] != A[j]) {
				j = jt[j];
			}
			i++;
			j++;
			if (j == A.length) {
				return true;
			}
		}
		return false;
	}
	
	// Returns the number of occurences of A in B (requires jt to be filled)
	static int kmpCount() {
		int count = 0;
		int i = 0, j = 0;
		while (i < B.length) {
			while (j >= 0 && B[i] != A[j]) {
				j = jt[j];
			}
			i++;
			j++;
			if (j == A.length) {
				j = jt[j];
				count++;
			}
		}
		return count;
	}
	
}
