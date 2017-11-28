
// This is an implementation allowing the calculation of the area of an arbitrary polygon
// Running time: O(n) (optimal)
public class PolygonArea {
	
	// A point
	static class Point {
		
		double x, y;
		
	}
	
	// Returns the area of the polygon described by points P
	// If P is given in counter-clockwise order, this area will be > 0
	// If P is given in clockwise order, this area will be < 0, but then the absolute value can be taken to get the area
	// As such, the result can also be used to determine whether P describes a polygon in counter-clockwise or clockwise order
	static double polygonArea(Point[] P) {
		int n = P.length;
		double sum = 0;
		for (int i = 0; i < n; i++) {
			int j = i-1;
			if (j < 0) {
				j += n;
			}
			int k = i+1;
			if (k >= n) {
				k -= n;
			}
			sum += (P[i].x * P[k].y) - (P[i].y * P[k].x);
		}
		return sum;
	}
	
}
