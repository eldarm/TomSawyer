package com.eldar.TomSawyer;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Drone {
	protected static Random rand = new Random();
	
	public Drone parent;
	public Drone left;
	public Drone right;
	public Color color = null;
	public boolean starter = false; 
	
	public Drone(Drone parent, int depth) {
		if (depth > 0) {
			final int ld = depth - 1;
			left = new Drone(this, ld);
			right = new Drone(this, ld);
		}
		this.parent = parent;
	}
	
	public void Paint(Color color) {
		if (this.color == null) {
			this.color = color;
			if (parent != null) parent.Paint(mutate(color));
			if (left != null)   left.Paint(mutate(color));
			if (right != null)  right.Paint(mutate(color));			
		}
	}
		
	private Color mutate(Color color) {
		return new Color(mutateColor(color.getRed()), mutateColor(color.getGreen()), mutateColor(color.getBlue()));
	}
	
	private int mutateColor(int color) {
		int delta = (int) (255.0 * Corp.noise / 100.0 + rand.nextGaussian() * Corp.noise);
		if (rand.nextDouble() > 0.5) delta = -delta;
		return (int)Math.max(0, Math.min(255, color + delta));
	}
	
	public void Draw(Graphics g, int width, int y, int x, int step, int size) {
	  int newY = y + step;
	  int leftX = x - width / 2;
	  int rightX = x + width / 2;
	  if (left != null) {
		  g.setColor(Color.RED);
		  g.drawLine(x, y, leftX, newY);
	  }
	  if (right != null) {
		  g.setColor(Color.RED);
		  g.drawLine(x, y, rightX, newY);
	  }
      if (starter) {
    	  g.setColor(Color.RED);
    	  g.fillOval(x - size / 2 - 4, y - size / 2 - 4, size + 8, size + 8);
      }
	  g.setColor(color);
	  g.fillOval(x - size / 2, y - size / 2, size, size);
	  g.setColor(Color.BLUE);
	  g.drawOval(x - size / 2, y - size / 2, size, size);
	  if (left != null) {
		  left.Draw(g, width / 2, newY, leftX, step, size);
	  }
	  if (right != null) {
		  right.Draw(g, width / 2, newY, rightX, step, size);
	  }
	}

}
