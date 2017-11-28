
// This is an implementation for the problem of finding the longest increasing subsequence in an array
// Running time: O(n*log(n)) (optimal)
public class LongestIncreasingSubsequence {
	
	// Returns an array containing the longest increasing subsequence that exists in the given array
	// This subsequence contains the values, but this implementation can be modified (see comment below) to return the array of the indices of the subsequence instead
	static int[] longestIncreasingSubsequence(int[] X) {
		int N = X.length, L = 0;
		int[] P = new int[N], M = new int[N+1];
		for (int i = 0; i < N; i++) {
			int lo = 1;
			int hi = L;
			while (lo <= hi) {
				int mid = (lo+hi+1)/2;
				if (X[M[mid]] < X[i]) {
					lo = mid+1;
				} else {
					hi = mid-1;
				}
			}
			int newL = lo;
			P[i] = M[newL-1];
			M[newL] = i;
			if (newL > L) {
				L = newL;
			}
		}
		int[] S = new int[L];
		int k = M[L];
		for (int i = L-1; i >= 0; i--) {
			// Use S[i] = k in the line below to return an array of the indices of the subsequence (instead of the values)
			S[i] = X[k];
			k = P[k];
		}
		return S;
	}
	
}
