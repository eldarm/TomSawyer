package com.eldar.TomSawyer;

import java.awt.Graphics;

abstract public class Corp {
    public static int depth = -1;
    public static double noise = -1.0;

	public static void SetDepthNoise(int depth, double noise) {		
		Corp.depth = depth;
		Corp.noise = noise;
	}
    
	protected Drone corp;

	public Corp() {
		if (depth < 0 || noise < 0) throw new RuntimeException("Depth and noise are not set.");
	    corp = new Drone(null, depth);
		Simulate();		  
	}
	
	public abstract void Simulate(); 
	
	public void Draw(Graphics g, int height, int width) {
	  final int step = height / (depth + 1);
	  final int size = (int) Math.min(step / 3, width / Math.pow(2, depth)) ;
	  corp.Draw(g, width / 2, size, width / 2, step, size);
	}
}
