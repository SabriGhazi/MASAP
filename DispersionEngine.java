package com.masim.utils;

//import com.googlecode.surfaceplotter.*;
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.MultipleGradientPaint.ColorSpaceType;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFrame;

import com.masim.core.SourceTYpe;
import com.masim.emissionAgents.SourceConfigInfo;
import com.masim.gameTheory.PrisonnerDelimmaUtl;
import com.masim.ui.Console;

/**
 * DispersionEngine
 * 
 * @author sabri ghazi
 * 
 */
public class DispersionEngine extends JDialog {

	private ArrayList<Particle> particles = new ArrayList<Particle>(0);
	public static int nHeight;
	public static int nWidth;

	public static double pm10nbr = 0;
	public static double soxnbr = 0;
	public static double ozone = 0;
	public static double cox = 0;
	public static double nox = 0;

	public static double pm10lvelGassian = 0;
	public static double soxlvelGassian = 0;

	public static double noxlvelGassian = 0;
	public static double coxlvelGassian = 0;

	public static double currentWS = 5.31;

	public ArrayList<SourceConfigInfo> sources = new ArrayList<SourceConfigInfo>();

	private int x = 0;
	private int y = 0;
	private BufferStrategy bufferstrat = null;
	private Canvas render;
	Random r = new Random();
	DecimalFormat df;
	int direction = 0;
	Graphics imageGraphics = null;
	BufferedImage bim = null;

	public DispersionEngine(int width, int height, String title) {
		super();
		setTitle(title);
		setIgnoreRepaint(true);

		df = new DecimalFormat("0.00");

		render = new Canvas();
		render.setIgnoreRepaint(true);
		this.particles.clear();
		pm10nbr = 0;
		soxnbr = 0;
		cox = 0;
		nox = 0;
		ozone = 0;

		nHeight = 500;// (int)
		// Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		nWidth = 1000;// (int)
		// Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		this.setSize(nHeight + 10, nWidth + 10);

		setBounds(0, 0, width, height);
		// render.setBounds(nWidth-(width/2), nHeight-(height/2), width,
		// height);

		/*
		 * try {
		 * 
		 * bim = ImageIO.read(new File("d:\\data\\map.jpg")); // imageGraphics
		 * =bim.createGraphics(); } catch (IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
	}

	public void showDispertionEngin() {
		add(render);
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		render.createBufferStrategy(2);
		bufferstrat = render.getBufferStrategy();
	}

	int indx = 0;

	// This is a bad game loop example but it is quick to write and easy to
	// understand
	// If you want to know how to do a good one use the all knowing google.
	public void loop() {
		while (true) {
			if (SimulationFramework.getCurrentSimProcessState() == SimProcessState.Started) {
				if (SimulationFramework.useGausianModel == 0) {
					if (indx == sources.size())
						indx = 0;
					if (!sources.get(indx).isSuspended()) {
						for (int i = 0; i < this.sources.get(indx)
								.getEmisionRate(); i++) {
							double size = r.nextDouble() * r.nextInt(4);
							addParticle(true, this.sources.get(indx).getP(),
									size);
							switch (this.sources.get(indx).getP()) {
							case COx:
								cox += size;
								break;
							case PM10:
								pm10nbr += size;
								break;
							case NOx:
								nox += size;
								break;
							case SOx:
								soxnbr += size;
								break;
							default:
								break;
							}
						}
					}
					update();
				} else {
					for (SourceConfigInfo s : sources) {
						runmdl(s);
					}
					computeAVG();
				}
			}
			render();
			try {
				Thread.sleep(SimulationFramework.SIMSPEED);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void update() {
		SourceConfigInfo s = this.sources.get(indx);
		currentWS = SimulationFramework.getCurrentEnv().ws.getData().get(
				SimulationFramework.getCurrentEnv().T);
		indx++;
		x = (int) s.getGeoLocation().getX();// p.x;
		y = (int) s.getGeoLocation().getY();// p.y;

		for (int i = 0; i <= particles.size() - 2; i++) {
			if (particles.get(i).update()) {

				switch (particles.get(i).getPollutantType()) {
				case PM10:
					pm10nbr -= particles.get(i).getSize();
					break;
				case SOx:
					soxnbr -= particles.get(i).getSize();
					break;
				case COx:
					cox -= particles.get(i).getSize();
					break;
				case NOx:
					nox -= particles.get(i).getSize();
					break;
				default:
					break;
				}
				particles.remove(i);
			}
		}
	}//

	public Font f = new Font("Times", 0, 9);

	public void render() {
		do {
			do {
				Graphics2D g2d = (Graphics2D) bufferstrat.getDrawGraphics();

				g2d.setFont(f);
				g2d.setColor(Color.black);
				g2d.fillRect(0, 0, nWidth, nHeight);
				g2d.drawImage(bim, 0, 0, this);
				if (SimulationFramework.useGausianModel == 0) {
					renderParticles(g2d);
					rendSource(g2d);
				} else
					// computeAVG();
					// drawGausianPlum(g2d);

					rendGrid(g2d);
				rendInformationPanel(g2d);
				g2d.dispose();
			} while (bufferstrat.contentsRestored());
			bufferstrat.show();
		} while (bufferstrat.contentsLost());
	}

	String label = "";
	String label2 = "";

	private void rendSource(Graphics2D g2d) {

		for (SourceConfigInfo s : sources) {

			label = s.getSourceName() + " ER :" + df.format(s.getEmisionRate())
					+ s.getP().toString();
			// +" agn
			// :"+PrisonnerDelimmaUtl.getInstance().eAgentList.get(s.getSourceName()).offenderAgent;
			/*
			 * if (PrisonnerDelimmaUtl.getInstance().eAgentList.containsKey(s
			 * .getSourceName())) { label2 = //
			 * PrisonnerDelimmaUtl.getInstance()
			 * .eAgentList.get(s.getSourceName()).personnality+" "+ " RW = " +
			 * df.format(PrisonnerDelimmaUtl.getInstance().eAgentList
			 * .get(s.getSourceName()).rewards) + " P =" +
			 * df.format(PrisonnerDelimmaUtl.getInstance().eAgentList
			 * .get(s.getSourceName()).P) + " Q =" +
			 * df.format(PrisonnerDelimmaUtl.getInstance().eAgentList
			 * .get(s.getSourceName()).Q) + " Pf_avg =" +
			 * df.format(PrisonnerDelimmaUtl.getInstance().eAgentList
			 * .get(s.getSourceName()).pf_AVG) + " S =" +
			 * df.format(PrisonnerDelimmaUtl.getInstance().eAgentList
			 * .get(s.getSourceName()).lastChoices[0]); }
			 */

			g2d.setColor(Color.BLACK);
			g2d.fillRoundRect((int) s.getGeoLocation().getX() - 12, (int) s
					.getGeoLocation().getY() - 15, 50, 18, 0, 0);
			g2d.setColor(Color.white);

			g2d.drawString(label, (int) s.getGeoLocation().getX() - 5, (int) s
					.getGeoLocation().getY() - 5);

			// g2d.drawString(label2, (int) s.getGeoLocation().getX() - 5, (int)
			// s
			// .getGeoLocation().getY() + 6);
			g2d.setColor(Color.ORANGE);
			g2d.fillOval((int) s.getGeoLocation().getX() - 2, (int) s
					.getGeoLocation().getY() - 2, 7, 7);
		}

	}

	/**
	 * Draw the information about current variables.
	 * 
	 * @param g2d
	 */
	private void rendInformationPanel(Graphics2D g2d) {

		g2d.setColor(Color.WHITE);
		g2d.fill3DRect(1005, 37, 250, 60, false);
		g2d.setColor(Color.BLACK);
		g2d.drawString("NP: " + particles.size(), 1010, 50);
		g2d.drawString("Sources : " + sources.size(), 1010, 65);
		int t = SimulationFramework.getCurrentEnv().T;
		g2d.drawString(
				"Current simulation time : "
						+ SimulationFramework.getCurrentEnv().time.get(t)
								.toString(), 1010, 76);
		g2d.drawString("Current wind speed : " + currentWS, 1010, 86);
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

	public void renderParticles(Graphics2D g2d) {
		for (int i = 0; i <= particles.size() - 1; i++) {
			particles.get(i).render(g2d);
		}
	}

	public void addParticle(boolean bool, PollutantType pt, double size) {

		double dx, dy;
		direction = r.nextInt(4);
		// if(direction==4)direction=0;
		if (direction == 0) {
			dx = r.nextDouble() * 2;// (Math.random()*3);
			dy = r.nextDouble() * 2;
		} else if (direction == 1) {
			dx = r.nextDouble() * 2;
			dy = r.nextDouble() * -2;
		} else if (direction == 2) {
			dx = r.nextDouble() * -2;
			dy = r.nextDouble() * 2;
		} else {
			dx = r.nextDouble() * -2;
			dy = r.nextDouble() * -2;
		}

		particles.add(new Particle(x, y, dx, dy, size, 600, Color.white, 0.8,
				pt));
		// if (pt == PollutantType.PM10)
		// pm10nbr = pm10nbr + 1;
	}

	public synchronized double getCurrentVals(PollutantType pt) {
		double retVal = 0;
		for (Particle p : particles) {
			if (p.getPollutantType() == pt)
				retVal = retVal + 1;
		}
		return retVal;
	}

	// This is the gaussian plume dispersion region.
	public static void runmdl(SourceConfigInfo s) {

		if (!s.isSuspended()) {
			double x = 100;
			double y = 10;
			double c = 0;

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
				c = ((s.getEmisionRate() * 1000 * DecayTerm(x, 2.12, s.getP())) / (Math.PI
						* sigy * sigz * 2.12))
						* (Math.exp((-1 / 2) * Math.pow(y / sigy, 2)))
						* Math.exp((-1 / 2) * Math.pow(10 / sigz, 2));
				s.gaussianPlumVals.push(c);
				x = x + 100;
			}
		}
	}

	public static double DecayTerm(double x, double U, PollutantType pltype) {
		double R = 0;
		if (pltype == PollutantType.SOx)
			R = 0.31;
		else if (pltype == PollutantType.NOx)
			R = 0.45;
		else
			R = 0;
		if (R == 0)
			return 1;
		else if (R > 0)
			return Math.exp(1 / (R * x / U));
		return 1;
	}

	private static Color brightness(double scale) {
		int r = (int) Math.min(255, 46 * 1 / scale);
		int g = (int) Math.min(255, 46 * 1 / scale);
		int b = (int) Math.min(255, 46 * 1 / scale);
		return new Color(r, g, b);
	}

	// Draw gausian plum.
	public void drawGausianPlum(Graphics2D g2d) {

		double px = 100;
		double py = 200;

		for (SourceConfigInfo sci : sources) {
			int i = 0;
			int dec = (int) sci.getGeoLocation().y;
			int r = 50;
			int rr = 50;
			// Stack<Double> s= new Stack<Double>();
			// for(double d: pnt)s.push(d);
			i = sci.gaussianPlumVals.size();
			Stack<Double> t = new Stack<Double>();
			while (!sci.gaussianPlumVals.isEmpty()) {

				Double dd = sci.gaussianPlumVals.pop();
				t.add(dd);
				g2d.setColor(brightness(dd));
				g2d.fillOval((int) sci.getGeoLocation().x,
						(int) sci.getGeoLocation().y, r, r);
				i--;
				rr = r;
				r = i * 10;
				dec = r - rr;
			}

			while (!t.isEmpty()) {
				sci.gaussianPlumVals.push(t.pop());
			}
		}
	}

	public void computeAVG() {

		double sommePM10 = 0;
		double sommesox = 0;
		double sommecox = 0;
		double sommenox = 0;

		int number = 0;

		for (SourceConfigInfo s : sources) {
			number = s.gaussianPlumVals.size();
			switch (s.getP()) {
			case PM10:
				sommePM10 = sommePM10 + s.getGaussianSum();
				break;
			case SOx:
				sommesox = sommesox + s.getGaussianSum();
				break;
			case NOx:
				sommecox = sommecox + s.getGaussianSum();
				break;
			case COx:
				sommenox = sommenox + s.getGaussianSum();
				break;
			default:
				break;
			}
		}
		pm10lvelGassian = (sommePM10)  / ( 150);
		soxlvelGassian =  (sommesox)   / ( 150);
		coxlvelGassian =  (sommecox)   / ( 150);
		noxlvelGassian =  (sommenox)   / ( 150);
	}
}
