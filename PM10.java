package com.masim.predictionAgents;

import com.masim.utils.SimulationFramework;

import jade.core.Agent;

public class PM10 extends Agent {
	boolean started;
	boolean suspend;
	
	int nbrOfMsgParam=0;
	public double hu;
	public double temp;
	public  double ws;
	
	public int getNbrOfMsgParam() {
		return nbrOfMsgParam;
	}


	public void setNbrOfMsgParam(int nbrOfMsgParam) {
		this.nbrOfMsgParam = nbrOfMsgParam;
	}


	public boolean isStarted() {
		return started;
	}


	public void setStarted(boolean started) {
		this.started = started;
	}


	public boolean isSuspend() {
		return suspend;
	}


	public void setSuspend(boolean suspend) {
		this.suspend = suspend;
	}


	public void setup(){

		addBehaviour(new MakePredictionBehaviour(this,SimulationFramework.SIMSPEED));
	}

}
