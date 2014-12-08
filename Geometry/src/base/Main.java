package base;

import grahamscan.GrahamScanAlgorithm;
import jarvis.JarvisAlgorithm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

import quickHull.QuickHullAlgorithm;

public class Main {
	static final String inFile = "input.txt";
	static final String outListFile = "output.txt";
	static final String outSVGFile = "output.svg";
	
	static BufferedReader br;
	static StringTokenizer st;
	static PrintWriter out;

	static int nextInt() throws IOException {
		if (st == null || !st.hasMoreElements()) {
			st = new StringTokenizer(br.readLine());
		}
		return Integer.parseInt(st.nextToken());
	}
	
	
	static double nextDouble() throws IOException {
		if (st == null || !st.hasMoreElements()) {
			st = new StringTokenizer(br.readLine());
		}
		return Double.parseDouble(st.nextToken());
	}
	
	private static Vector2d processDot(Vector2d dot) {
		return dot.mul(100).add(new Vector2d(50, 50));
	}
	
	private static String formatLine(Vector2d dot1, Vector2d dot2) {
		dot1 = processDot(dot1);
		dot2 = processDot(dot2);
		return String.format("<line x1=\"%f\" y1=\"%f\"\n"
				+ "\t\tx2=\"%f\" y2=\"%f\"\n"
				+ "\tstyle=\"stroke:rgb(0,0,255);stroke-width:0.3\"/>" ,		 
				dot1.x(), dot1.y(), dot2.x(), dot2.y());
	}
	
	private static String formatCircle(Vector2d dot) {
		dot = processDot(dot);
		return String.format("<circle cx=\"%f\" cy=\"%f\" r=\"2\" fill=\"red\"/>" ,dot.x(), dot.y());
		
	}
	
	
	static void dotsToSVG(ArrayList<Vector2d> dotsIn, ArrayList<Vector2d> hullIn, String fileName) throws IOException{
		ArrayList<Vector2d> dots = (ArrayList<Vector2d>) dotsIn.clone();
		ArrayList<Vector2d> hull = (ArrayList<Vector2d>) hullIn.clone();
		
//		for (Vector2d v : dots) {
//			v.set(v.mul(20).add(new Vector2d(100, 100)));
//		}		
		
		out = new PrintWriter(fileName);
		out.println("<?xml version=\"1.0\"?>\n" +
            "<svg width=\"800\" height=\"800\"\n" +
            "viewPort=\"0 0 800 800\" version=\"1.1\"\n" +
            "xmlns=\"http://www.w3.org/2000/svg\">\n");			
				
		
		for (int i = 0; i < hull.size() - 1; i++) {
			out.println(formatLine(hull.get(i), hull.get(i + 1)));	
		}
		
		out.println(formatLine(hull.get(hull.size() - 1), hull.get(0)));
		
		for (int i = 0; i < dots.size(); i++) {
			out.println(formatCircle(dots.get(i)));
		}
		out.println("</svg>");
		out.close();
	}
	
	static void dotsToFile(ArrayList<Vector2d> dots, String fileName) throws IOException{
		out = new PrintWriter(fileName);
		for (int i = 0; i < dots.size() - 1; i++) {
			out.println(String.format("%f %f" , dots.get(i).x(), dots.get(i).y()));	
		}
		out.close();
	}
	
	static ArrayList<Vector2d> dotsFromFile(String fileName) throws IOException {
		br = new BufferedReader(new FileReader(fileName));
		ArrayList<Vector2d> dots = new ArrayList<Vector2d>();
		int count = nextInt();
		for (int i = 0; i < count; i++) {
			dots.add(new Vector2d(nextDouble(), nextDouble()));
		}
		return dots;
	}
	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		ArrayList<Vector2d> dots;
		try {
			dots = dotsFromFile(inFile);
		} catch (IOException e) { 
			e.printStackTrace();
			return;
		}
		
		ArrayList<Vector2d> grahamHull = GrahamScanAlgorithm.algorithm(dots);
		ArrayList<Vector2d> jarvisHull = JarvisAlgorithm.algorithm(dots);
		ArrayList<Vector2d> quickHull = QuickHullAlgorithm.algorithm(dots);
		//TODO: other algorithms
		
		try {
			dotsToFile(grahamHull, "Graham" + outListFile);
			dotsToSVG(dots, grahamHull, "Graham" + outSVGFile);
			
			dotsToFile(jarvisHull, "Jarvis" + outListFile);
			dotsToSVG(dots, jarvisHull, "Jarvis" + outSVGFile);
			
			dotsToFile(quickHull, "QuickHull" + outListFile);
			dotsToSVG(dots, quickHull, "QuickHull" + outSVGFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
