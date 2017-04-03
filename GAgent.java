/**
 * 
 */
package com.masim.core;

import java.util.Date;

import com.masim.utils.Location;

import jade.core.Agent;

/**
 * The super agent's class.
 * @author sabri ghazi
 */
public class GAgent extends Agent {

	
	
	Location geoLocation;
	long ID;
	Date LifeTime;
	long timeOutDelai;
	
	boolean isMobile;
	/**
	 * 
	 */
	public GAgent() {
		// TODO Auto-generated constructor stub
	}



	public Location getGeoLocation() {
		return geoLocation;
	}

	public void setGeoLocation(Location geoLocation) {
		this.geoLocation = geoLocation;
	}

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public Date getLifeTime() {
		return LifeTime;
	}

	public void setLifeTime(Date lifeTime) {
		LifeTime = lifeTime;
	}

	public long getTimeOutDelai() {
		return timeOutDelai;
	}

	public void setTimeOutDelai(long timeOutDelai) {
		this.timeOutDelai = timeOutDelai;
	}



	public boolean isMobile() {
		return isMobile;
	}



	public void setMobile(boolean isMobile) {
		this.isMobile = isMobile;
	}

	
}
