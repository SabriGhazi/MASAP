package com.masim.utils;

	import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.Random;

/**
 * @see DispersionEngine
 * @author sabri ghazi
 *
 */
	public class Particle {

	   private double x;
	   private double y;
	   private double dx;
	   private double dy;
	   private double size;
	   private double life;
	   private double dlife=0.8;
	   private PollutantType pollutantType;
	   
	   
	   
	   
	   public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getDx() {
		return dx;
	}

	public void setDx(double dx) {
		this.dx = dx;
	}

	public double getDy() {
		return dy;
	}

	public void setDy(double dy) {
		this.dy = dy;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public double getLife() {
		return life;
	}

	public void setLife(double life) {
		this.life = life;
	}

	public double getDlife() {
		return dlife;
	}

	public void setDlife(double dlife) {
		this.dlife = dlife;
	}

	public PollutantType getPollutantType() {
		return pollutantType;
	}

	public void setPollutantType(PollutantType pollutantType) {
		this.pollutantType = pollutantType;
	}

	public boolean update(){
		      x += dx;
		      y += dy;
		      life=life-(DispersionEngine.currentWS);
		      if((x>DispersionEngine.nWidth)||(y>DispersionEngine.nHeight))
		    	  return true;
		      if((life <= 0))
		         return true;
		      else return false;
	   }

	   public void render(Graphics g){
		      Graphics2D g2d = (Graphics2D) g.create();
		      g2d.setColor(getColor(pollutantType));
		      g2d.fill(new Ellipse2D.Double(x-(size/2), y-(size/2), size, size));
		      g2d.dispose();
	   }
	   
	   /**
	    * Which color is used for the pollutant.
	    * @param PollutantType
	    * @return Color
	    */
	   private Color getColor(PollutantType p) {

		   if(p== PollutantType.PM10)
			   return Color.RED.darker();
		   else if(p==PollutantType.SOx)
			   return Color.YELLOW.darker();
		   else if (p==PollutantType.NOx)
			   return Color.GREEN.darker();
		   else if(p==PollutantType.O3)
			   return Color.MAGENTA.darker();
		   else if(p==PollutantType.COx)
			   return Color.blue.darker();
		   	
		return Color.black;
	}
	   
	   public Particle(double x, 
			   double y, 
			   double dx, 
			   double dy, 
			   double size, 
			   double life, 
			   Color c,
			   double pdlife,
			   PollutantType pt){
		      this.x = x;
		      this.y = y;
		      this.dx = dx;
		      this.dy = dy;
		      this.size = size;
		      this.life = life;
		      this.dlife=pdlife;
		      this.pollutantType=pt;
		   }

	}
