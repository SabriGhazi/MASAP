package com.masim.gameTheory;

import com.masim.utils.SimulationFramework;

import jade.core.Agent;

public class GameManagerAgent extends Agent {

	
	protected void setup() {
		addBehaviour(new RunGamePD(this,SimulationFramework.SIMSPEED));
	}
}
