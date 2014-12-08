package quickHull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import base.Vector2d;
import base.Vector2d.AngleComparator;
import base.Vector2d.YComparator;

public class QuickHullAlgorithm {
	static ArrayList<Vector2d> answer;
	
	public static void procRecursive(ArrayList<Vector2d> dots, Vector2d left, Vector2d right) {		
		Vector2d dot = dots.get(0);
		Vector2d.LineDistanceComparator cc = left.new LineDistanceComparator(left, right);		
		for (Vector2d v : dots) {
			if (cc.compare(dot, v) == -1) {
				dot = v;
			}
		}
		ArrayList<Vector2d> ldots = new ArrayList<>();
		ArrayList<Vector2d> rdots = new ArrayList<>();
		for (Vector2d v : dots) {
			if (Vector2d.leftTurn(left, dot, v) > 0) {
				ldots.add(v);
			}
			if (Vector2d.leftTurn(dot, right, v) > 0) {
				rdots.add(v);
			}
		}
		if(ldots.size() != 0)
			procRecursive(ldots, left, dot);
		answer.add(dot);
		if(rdots.size() != 0)
			procRecursive(rdots, dot, right);
		
		
	}
	
	public static ArrayList<Vector2d> algorithm(ArrayList<Vector2d> dots) {		
		Vector2d left = dots.get(0);
		Vector2d right = dots.get(0);
		
		Vector2d.XComparator cc = left.new XComparator();
		for (Vector2d v : dots) {
			if (cc.compare(v, left) == -1) 
				left = v;
			if (cc.compare(v, right) == 1) {
				right = v;
			}
		}
		
		answer = new ArrayList<>();
		
		ArrayList<Vector2d> ldots = new ArrayList<>();
		ArrayList<Vector2d> rdots = new ArrayList<>();
		for (Vector2d v : dots) {
			if (Vector2d.leftTurn(left, right, v) > 0) {
				ldots.add(v);
				continue;
			}
			if (Vector2d.leftTurn(right, left, v) > 0) {
				rdots.add(v);
				continue;
			}			
		}
		
		answer.add(left);
		if(ldots.size() != 0)
			procRecursive(ldots, left, right);
		answer.add(right);
		if(rdots.size() != 0)
			procRecursive(rdots, right, left);
		
		
		return answer;
	}
}
