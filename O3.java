package com.masim.predictionAgents;

import jade.core.Agent;

public class O3 extends Agent {
	boolean started;
	boolean suspend;
	
	
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

		addBehaviour(new MakeO3PredictionBehavior(this));
	}

}
