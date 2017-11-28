// Heap’s algorithm executes an operation for all permutations of the array A
// Running time: O(number of permutations) = O(n!) (optimal)
public class HeapsAlgorithm {
	
	// The generate method takes an array A, and calls the output method for every permutation of A
	// Note that this implementation will modify the array in-place and will not leave the original order intact
	static void generate(int[] A) {
		int n = A.length;
		int[] c = new int[n];
		output(A);
		int i = 1;
		while (i < n) {
			if (c[i] < i) {
				if ((i&1)==0) {
					swap(A, 0, i);
				} else {
					swap(A, c[i], i);
				}
				output(A);
				c[i]++;
				i = 1;
			} else {
				c[i] = 0;
				i++;
			}
		}
	}
	
	// Auxiliary method to swap two values in an array
	static void swap(int[] A, int i, int j) {
		int t = A[i];
		A[i] = A[j];
		A[j] = t;
	}
	
	// Any operation you wish to run on every permutation of A
	static void output(int[] A) {
		// Do something with A here
	}
	
}
