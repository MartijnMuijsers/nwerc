// MergeSort is an algorithm that sorts an array by recursively sorting two halves and then merging the results
// This implementation also counts the number of inversions in an array
// This is the number of indices i,j in an array A so that i < j but A[i] > A[j] 
// Running time: O(n*log(n))) (optimal)
public class MergeSort {
	
	// Sorts the array A, and returns the number of inversions that existed in A beforehand
	static long sortAndCountInversions(int[] A) {
		return merge(A, new int[A.length], 0, A.length-1);
	}
	
	// Solves the subproblem of sorting A in the index range l to r (both inclusive)
	static long merge(int[] A, int[] B, int l, int r) {
		if (l == r) {
			return 0;
		}
		int length = r-l+1;
		int dif = (length-1)/2;
		int b1 = l;
		int e1 = l+dif;
		int b2 = e1+1;
		int e2 = r;
		long inversions = merge(A, B, b1, e1)+merge(A, B, b2, e2);
		int i = 0;
		int i1 = b1;
		int i2 = b2;
		while (i < length) {
			if (i1 > e1) {
				B[i] = A[i2];
				i2++;
			} else if (i2 > e2) {
				B[i] = A[i1];
				i1++;
			} else if (A[i2] > A[i1]) {
				B[i] = A[i1];
				i1++;
			} else if (A[i2] == A[i1]) {
				B[i] = A[i1];
				i1++;
			} else {
				B[i] = A[i2];
				inversions += e1-i1+1;
				i2++;
			}
			i++;
		}
		System.arraycopy(B, 0, A, l, length);
		return inversions;
	}
	
}
