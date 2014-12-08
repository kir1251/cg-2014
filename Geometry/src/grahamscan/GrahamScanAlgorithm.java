package grahamscan;

import base.Vector2d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class GrahamScanAlgorithm {
	
	private static int peekNext(Stack<Integer> st) {
		int tmp = st.pop();
		int ans = st.peek();
		st.push(tmp);
		return ans;
	}
	
	public static ArrayList<Vector2d> algorithm(ArrayList<Vector2d> dots) {
		ArrayList<Vector2d> hull = new ArrayList<>();
		Vector2d start = dots.get(0);		
		Vector2d.YComparator cc = start.new YComparator();
		for (Vector2d v : dots) {
			if (cc.compare(v, start) == -1) {
				start = v;
			}
		}
		dots.remove(start);
		
		Collections.sort(dots, start.new AngleComparator(start));
		Collections.reverse(dots);
		dots.add(0, start);
		Stack<Integer> st = new Stack<>();
		st.push(0);
		st.push(1);		
		for (int i = 2; i < dots.size(); i++) {			
			while (Vector2d.leftTurn(dots.get(peekNext(st)), dots.get(st.peek()), dots.get(i)) < 0) {
				st.pop();
			}
			st.push(i);
		}
		
		while (!st.isEmpty()){
			hull.add(dots.get(st.pop()));
		}
		return hull;
	}
}
