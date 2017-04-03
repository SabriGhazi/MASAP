package com.masim.utils;

import jade.util.leap.Iterator;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Stack;

import javax.swing.JDialog;
import javax.swing.JFrame;

import com.masim.core.SourceTYpe;
import com.masim.emissionAgents.SourceConfigInfo;
import com.masim.ui.Console;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class DispertionEngineGaussianPlum extends JDialog {

	private BufferStrategy bufferstrat = null;
	private Canvas render;
	Graphics imageGraphics = null;
	DecimalFormat df;
	public int nHeight = 800;
	public int nWidth = 800;
	public ArrayList<Double> pnt = new ArrayList<Double>();
	public ArrayList<Integer> xis = new ArrayList<Integer>();
	public ArrayList<Integer> yis = new ArrayList<Integer>();

	public static ArrayList<SourceConfigInfo> source = new ArrayList<SourceConfigInfo>();

	public static void testIt() {

		SourceConfigInfo s = new SourceConfigInfo(350, 0,
				new Location(400, 400), null, PollutantType.PM10,
				SourceTYpe.Point);
		s.setSourceName("S1");
		source.add(s);
/*
		SourceConfigInfo s1 = new SourceConfigInfo(600, 0,
				new Location(40, 400), null, PollutantType.PM10,
				SourceTYpe.Point);
		s.setSourceName("S2");
		source.add(s1);

		SourceConfigInfo s2 = new SourceConfigInfo(100, 0,
				new Location(400, 40), null, PollutantType.PM10,
				SourceTYpe.Point);
		s.setSourceName("S3");
		source.add(s2);

		SourceConfigInfo s3 = new SourceConfigInfo(50, 0, new Location(100,
				200), null, PollutantType.PM10, SourceTYpe.Point);
		s.setSourceName("S4");
		source.add(s3);
*/
	}

	public DispertionEngineGaussianPlum() {

		super();
		setTitle("Gaussian Dispertion");
		setIgnoreRepaint(true);

		df = new DecimalFormat("0.00");

		render = new Canvas();
		render.setIgnoreRepaint(true);

		nHeight = 800;// (int)
						// Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		nWidth = 800;// (int)
						// Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		this.setSize(nHeight + 10, nWidth + 10);

		setBounds(0, 0, 800, 800);
		add(render);
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		render.createBufferStrategy(2);
		bufferstrat = render.getBufferStrategy();
		testIt();
		for (SourceConfigInfo s : source) {
			runmdl(s);
		}
	}

	public void render() {
		do {
			do {
				Graphics2D g2d = (Graphics2D) bufferstrat.getDrawGraphics();

				// g2d.setFont(f);
				g2d.setColor(Color.WHITE);
				g2d.fillRect(0, 0, 800, 800);
				rendGrid(g2d);
				// g2d.drawImage(bim, 0, 0, this);
				// renderParticles(g2d);
				rendSources(g2d);
				//
				// rendInformationPanel(g2d);
				g2d.dispose();
			} while (bufferstrat.contentsRestored());
			bufferstrat.show();
		} while (bufferstrat.contentsLost());
	}

	private static Color brightness(double scale) {

		int r = (int) Math.min(255, 46 * 1 / scale);
		int g = (int) Math.min(255, 46 * 1 / scale);
		int b = (int) Math.min(255, 46 * 1 / scale);

		return new Color(r, g, b);
	}

	public void rendSources(Graphics2D g2d) {

		
		double px = 100;
		double py = 200;

	
		for (SourceConfigInfo sci : source) {
			int i = 0;
			int dec = (int) sci.getGeoLocation().y;
			int r = 50;
			int rr = 50;
			// Stack<Double> s= new Stack<Double>();
			// for(double d: pnt)s.push(d);
			i = sci.gaussianPlumVals.size();
			Stack<Double> t= new Stack<Double>();
			while (!sci.gaussianPlumVals.isEmpty()) {

				Double dd = sci.gaussianPlumVals.pop();
				t.add(dd);
				g2d.setColor(brightness(dd));
				g2d.fillOval((int)sci.getGeoLocation().x, (int)sci.getGeoLocation().y, r, r);
				i--;
				rr = r;
				r = i * 10;
				dec = r - rr;
			}
			
			while(!t.isEmpty()){
				sci.gaussianPlumVals.push(t.pop());
			}
			
		}
	}

	public void runmdl(SourceConfigInfo s) {
		// java.awt.color
		double x = 100;
		double y = 10;
		double c = 0;

		double rx = 100;
		double ry = 100;

		double sigy = 0.197 * Math.pow(x, 0.908);

		double sigz = 0.1120 * Math.pow(x, 0.9100);

		while (x <= 1500) {
			if (x < 10000) {
				sigy = 0.197 * Math.pow(x, 0.908);
			} else if (x >= 10000) {
				sigy = 0.285 * Math.pow(x, 0.867);
			}
			if ((x > 100) && (x <= 500)) {
				sigz = 0.1120 * Math.pow(x, 0.9100);
			} else if ((5 < x) && (x < 50)) {
				sigz = 0.1014 * Math.pow(x, 0.926);
			} else if (x > 50) {
				sigz = 0.1154 * Math.pow(x, 0.9109);
			}
			// while (y < 1000) {
			c = ((s.getEmisionRate() * 1000) / (Math.PI * sigy * sigz * 2.12))
					* (Math.exp((-1 / 2) * Math.pow(y / sigy, 2)))
					* Math.exp((-1 / 2) * Math.pow(10 / sigz, 2));
			s.gaussianPlumVals.push(c);
			System.out.println(c);
			pnt.add(c);
			xis.add((new Integer((int) rx)));
			// y = y + 100;
			ry = ry + 3;
			// }
			// xis.add(( new Integer((int)rx)));
			// y = 100;
			x = x + 100;
			ry = 100;
			rx = rx + 3;
		}
	}

	public double abs(double v) {
		return ((v > 0) ? (v) : (v * -1));
	}

	private void rendGrid(Graphics2D g2d) {

		double xStart = 0;
		double yStart = 0;
		g2d.setColor(Color.ORANGE);

		int numberOfBoxH = nHeight / 100;
		int numberOfBoxV = nWidth / 100;
		for (int i = 0; i < numberOfBoxV; i++) {
			for (int j = 0; j < numberOfBoxH; j++) {
				Rectangle2D rec2D = new Rectangle2D.Double(xStart, yStart, 100,
						100);
				Rectangle2D rec2D2 = new Rectangle2D.Double(xStart + 0.2,
						yStart + 0.2, 100 + 0.2, 100 + 0.2);
				Rectangle2D rec2D3 = new Rectangle2D.Double(xStart + 0.3,
						yStart + 0.3, 100 + 0.3, 100 + 0.3);
				g2d.draw(rec2D);
				g2d.draw(rec2D2);
				g2d.draw(rec2D3);
				yStart = yStart + 100;
			}
			xStart = xStart + 100;
			yStart = 0;
		}
	}

	public void loop() {
		while (true) {
			// if (SimulationFramework.getCurrentSimProcessState() ==
			// SimProcessState.Started) {
			// }
			render();
			try {
				Thread.sleep(SimulationFramework.SIMSPEED);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		DispertionEngineGaussianPlum d = new DispertionEngineGaussianPlum();		
		d.show(true);
		d.loop();
	}
}