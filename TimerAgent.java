package com.masim.core;

import com.masim.utils.SimulationFramework;

import jade.core.Agent;

public class TimerAgent extends Agent {

	@Override
	protected void setup() {
		
		super.setup();
		
		addBehaviour(new TimerBehavior(this, SimulationFramework.SIMSPEED));
	}
}
