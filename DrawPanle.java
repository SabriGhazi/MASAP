package com.masim.ui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

public class DrawPanle extends JPanel {


	ArrayList<Point> pnts=new ArrayList<Point>();


	public void AddPoint(double x, double y){

		Point p= new Point();
		p.setLocation(x, y);
		pnts.add(p);
	}

	@Override
	public void paint(Graphics g) {
		for (Point it : pnts) {
			g.fillRoundRect(it.x*10, it.y*10, 2, 2, 2, 2);
		}

		super.paint(g);
	}
}
