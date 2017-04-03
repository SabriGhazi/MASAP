package com.masim.emissionAgents;

import java.util.ArrayList;
import java.util.Stack;

import com.masim.core.SourceTYpe;
import com.masim.ui.Console;
import com.masim.utils.CBox;
import com.masim.utils.Location;
import com.masim.utils.Pollutant;
import com.masim.utils.PollutantType;

/**
 * Source Configuration Informations.
 * 
 * @author sabri ghazi
 * 
 */
public class SourceConfigInfo {

	double emisionRate;
	double maxEmissionRate;
	double minEmissionRate;
	double production;

	boolean uncontrolled;
	
	Location geoLocation;
	CBox isLocatedOnCbox;
	PollutantType p;
	SourceTYpe type;
	String SourceName;

	boolean suspended;

	public Stack<Double> gaussianPlumVals = new Stack<Double>();

	public double getMaxEmissionRate() {
		return maxEmissionRate;
	}

	public void setMaxEmissionRate(double pmaxEmissionRate) {
		this.maxEmissionRate = pmaxEmissionRate;
	}

	public double getMinEmissionRate() {
		return minEmissionRate;
	}

	public void setMinEmissionRate(double minEmissionRate) {
		this.minEmissionRate = minEmissionRate;
	}

	public String getSourceName() {
		return SourceName;
	}

	public void setSourceName(String sourceName) {
		SourceName = sourceName;
	}

	/**
	 * Emission source parameters.
	 * 
	 * @param emisionRate
	 * @param production
	 * @param geoLocation
	 * @param isLocatedOnCbox
	 * @param p
	 *            see {@link PollutantType}
	 * @param type
	 *            see {@link SourceTYpe}
	 */
	public SourceConfigInfo(long emisionRate, long production,
			Location geoLocation, CBox isLocatedOnCbox, PollutantType p,
			SourceTYpe type) {
		super();
		this.emisionRate = emisionRate;
		this.production = production;
		this.geoLocation = geoLocation;
		this.isLocatedOnCbox = isLocatedOnCbox;
		this.p = p;
		this.type = type;
	}

	public SourceConfigInfo() {
	}

	public double getEmisionRate() {
		return emisionRate;
	}

	public void setEmisionRate(double emisionRate) {
		this.emisionRate = emisionRate;
	}

	public double getProduction() {
		return production;
	}

	public void setProduction(double production) {
		this.production = production;
	}

	public Location getGeoLocation() {
		return geoLocation;
	}

	public void setGeoLocation(Location geoLocation) {
		this.geoLocation = geoLocation;
	}

	public CBox getIsLocatedOnCbox() {
		return isLocatedOnCbox;
	}

	public void setIsLocatedOnCbox(CBox isLocatedOnCbox) {
		this.isLocatedOnCbox = isLocatedOnCbox;
	}

	public PollutantType getP() {
		return p;
	}

	public void setP(PollutantType pollutantType) {
		this.p = pollutantType;
	}

	public SourceTYpe getType() {
		return type;
	}

	public void setType(SourceTYpe type) {
		this.type = type;
	}

	public boolean isSuspended() {
		return suspended;
	}

	
	public boolean isUncontrolled() {
		return uncontrolled;
	}

	public void setUncontrolled(boolean uncontrolled) {
		this.uncontrolled = uncontrolled;
	}

	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}

	public void reduceEmission() {
		if (emisionRate > minEmissionRate)
			emisionRate = emisionRate - (emisionRate / 30);
		else
			emisionRate = minEmissionRate;
	}

	/*
	 * 
	 */
	public void resumeEmission() {
		if (emisionRate < maxEmissionRate)
			emisionRate = emisionRate + (emisionRate / 30);
		else
			emisionRate = maxEmissionRate;
	}

	public void reduceEmissionBy(double val) {
		emisionRate = emisionRate - val;
	}

	public double getGaussianSum() {
		if (isSuspended())
			return 0;
		double sm = 0;
		double v;
		double avg=0;
		Stack<Double> temp = new Stack<Double>();
		while (!gaussianPlumVals.isEmpty()) {
			v = gaussianPlumVals.pop();
			temp.push(v);
			sm = v + sm;
		}
		avg=sm/temp.size();
		while (!temp.empty())
			gaussianPlumVals.push(temp.pop());
		return avg;
	}
}