package base;

import java.util.Comparator;
import java.math.*;

public class Vector2d {
	private double x, y;
	
	public double x() {
		return x;			
	}
	
	public double y() {
		return y;			
	}
	
	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Vector2d v) {
		return (v.x == x && v.y == y);
	}
	
	public Vector2d add(Vector2d v) {
		return new Vector2d(this.x + v.x, this.y + v.y);		
	}
	
	public Vector2d sub(Vector2d v) {
		return new Vector2d(this.x - v.x, this.y - v.y);
	}
	
	public Vector2d dot(Vector2d v) {
		return new Vector2d(this.x * v.x, this.y * v.y);			
	}
	
	public Vector2d mul(double d) {
		return new Vector2d(this.x * d, this.y * d);
	}
	
	public Vector2d reverse() {
		return this.mul(-1);
	}
	
	public void set(Vector2d v) {
		this.x = v.x;
		this.y = v.y;
	}
	
	public double wedge(Vector2d v) {
		return this.x * v.y - this.y * v.x;			
	}
	
	public double scalar(Vector2d v) {
		return this.x * v.x + this.y * v.y;
	}	
	
	public double length() {
		return Math.sqrt(x * x + y * y); 
	}
	
	double distanceToLine(Vector2d a, Vector2d b) {
		return this.sub(a).wedge(b.sub(a)) / b.sub(a).length();
	}
	
	public String toString() {
		return String.format("(%f, %f)", x, y);
	}
		
	
	public class XComparator implements Comparator<Vector2d> {
		@Override
		public int compare(Vector2d v1, Vector2d v2) {
			if (Double.compare(v1.x, v2.x) != 0) {
				return Double.compare(v1.x, v2.x);
			} else {
				return Double.compare(v1.y, v2.y);
			}
		}						
	}
	
	public class YComparator implements Comparator<Vector2d> {
		
		
		
		@Override
		public int compare(Vector2d v1, Vector2d v2) {
			if (Double.compare(v1.y, v2.y) != 0) {
				return Double.compare(v1.y, v2.y);
			} else {
				return Double.compare(v1.x, v2.x);
			}
		}						
	}
	
	double angle() {		
		
		if (x == 0) return Math.PI * (1 - Math.signum(y) / 2);
				
		if (x > 0) {
			if (y > 0)
				return Math.atan(y / x);			
			if (y < 0) return Math.atan(y / x) + Math.PI * 2;
		} else {
			return Math.atan(y / x) + Math.PI;
		}
		return 0;
	}
	
	public class AngleComparator implements Comparator<Vector2d> {
		private Vector2d base;
		private double zeroAngle = 0;;
		public AngleComparator (Vector2d v) {
			base = v;
		}
		public AngleComparator (Vector2d v, Vector2d x) {
			base = v;
			zeroAngle = x.angle();
		}
		
		public int compare(Vector2d v1, Vector2d v2) {
			Vector2d d1 = v1.sub(base);
			Vector2d d2 = v2.sub(base);
			
			double a1 = (d1.angle() - zeroAngle) % (Math.PI * 2);			
			double a2 = (d2.angle() - zeroAngle) % (Math.PI * 2);
			
			if (Double.compare(a1, a2) != 0) {
				return Double.compare(a1, a2);
			} else {
				return Double.compare(v1.length(), v2.length());
			}
		}									
	}
	
	public class LineDistanceComparator implements Comparator<Vector2d> {
		private Vector2d vl, vr;
		public LineDistanceComparator (Vector2d v1, Vector2d v2) {
			vl = v1;
			vr = v2;
		}
		
		public int compare(Vector2d v1, Vector2d v2) {			
			
			double a1 = Math.abs(v1.distanceToLine(vl, vr));
			double a2 = Math.abs(v2.distanceToLine(vl, vr));						
			
			if (Double.compare(a1, a2) != 0) {
				return Double.compare(a1, a2);
			} else {
				return Double.compare(v1.x, v2.x);
			}
		}									
	}
	
	
	public static final Vector2d ZERO = new Vector2d(0, 0);	
	
	public static int leftTurn(Vector2d a, Vector2d b, Vector2d c) {
		return Double.compare(c.sub(a).wedge(b.sub(a)), 0);
	}
}		
