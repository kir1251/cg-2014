package jarvis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import base.Vector2d;
import base.Vector2d.AngleComparator;
import base.Vector2d.YComparator;

public class JarvisAlgorithm {
	public static ArrayList<Vector2d> algorithm(ArrayList<Vector2d> inDots) {
		ArrayList<Vector2d> hull = new ArrayList<>();
		ArrayList<Vector2d> dots = new ArrayList<>();
		for(Vector2d v : inDots) {
			dots.add(v);
		}
		Vector2d start = dots.get(0);		
		
		Vector2d.YComparator cc = start.new YComparator();
		for (Vector2d v : dots) {
			if (cc.compare(v, start) == -1) {
				start = v;
			}
		}		
		hull.add(start);		
		
		Vector2d next = start;				
		
		do {
			Vector2d a = Vector2d.ZERO;
			for (Vector2d v : dots) {
				if (v != next && (a.equals(Vector2d.ZERO) || a.sub(next).wedge(v.sub(next)) < 0)) {
					a = v;
				}
			}
			next = a;			
			dots.remove(next);
			hull.add(next);
		} while(next != start);
		
		return hull;
	}
}
