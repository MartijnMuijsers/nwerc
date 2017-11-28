import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;

// Graham's scan finds the convex hull of a set of points
// Running time O(n*log(n)) (optimal)
public class GrahamScan {
	
	// Executes a Graham's scan on the list of points in P
	// Returns a stack of points that describe the convex hull, in counter-clockwise order
	static Stack<Point> grahamScan(Point[] P) {
		Stack<Point> convexHull = new Stack<>();
		int n = P.length;
		Point[] Q = new Point[n];
		for (int i = 0; i < n; i++) {
			Q[i] = P[i];
		}
		Arrays.sort(Q, Point.yxOrder);
		Arrays.sort(Q, 1, n, Q[0].polarOrder());
		convexHull.push(Q[0]);
		int k1;
		for (k1 = 1; k1 < n; k1++) {
		 	if (!Q[0].equals(Q[k1])) {
		 		break;
			}
		}
		if (k1 == n) {
			return convexHull;
		}
		int k2;
		for (k2 = k1+1; k2 < n; k2++) {
		 	if (ccw(Q[0], Q[k1], Q[k2]) != 0) {
		  		break;
			}
		}
		convexHull.push(Q[k2-1]);
		for (int i = k2; i < n; i++) {
			Point top = convexHull.pop();
			while (ccw(convexHull.peek(), top, Q[i]) <= 0) {
				top = convexHull.pop();
			}
			convexHull.push(top);
			convexHull.push(Q[i]);
		}
		return convexHull;
	}
	
	// Auxiliary ccw-function (uses spanned polygon area to determine the relative direction of vectors)
	static int ccw(Point a, Point b, Point c) {
		double area2 = (b.x-a.x)*(c.y-a.y)-(b.y-a.y)*(c.x-a.x);
		if (area2 < 0) {
			return -1;
		} else if (area2 > 0) {
			return 1;
		} else {
			return  0;
		}
	}
	
	// A point in the plane
	static class Point {
		
		double x, y;
		
		Point(double x, double y) {
			this.x = x;
			this.y = y;
		}
		
		double distance(Point p) {
			double dx = x-p.x;
			double dy = y-p.y;
			return Math.sqrt(dx*dx+dy*dy);
		}
		
		static Comparator<Point> yxOrder = (p1, p2) -> {
			int yCompare = Double.compare(p1.y, p2.y);
			if (yCompare != 0) {
				return yCompare;
			}
			return Double.compare(p1.x, p2.x);
		};
		
		Comparator<Point> polarOrder() {
			return (p1, p2) -> {
				double dx1 = p1.x-x;
				double dy1 = p1.y-y;
				double dx2 = p2.x-x;
				double dy2 = p2.y-y;
				if (dy1 >= 0 && dy2 < 0) {
					return -1;
				} else if (dy2 >= 0 && dy1 < 0) {
					return 1;
				} else if (dy1 == 0 && dy2 == 0) {
					if (dx1 >= 0 && dx2 < 0) {
						return -1;
					} else if (dx2 >= 0 && dx1 < 0) {
						return 1;
					} else {
						return 0;
					}
				} else {
					return -ccw(Point.this, p1, p2);
				}
			};
		}
		
		@Override
		public boolean equals(Object obj) {
			Point p = (Point) obj;
			return x == p.x && y == p.y;
		}
		
	}
	
}
