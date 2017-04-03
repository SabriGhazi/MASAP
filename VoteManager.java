package com.masim.gameTheory;

import com.masim.utils.SimulationFramework;

import jade.core.Agent;

public class VoteManager extends Agent {

protected void setup() {
		addBehaviour(new RunVoteBhvr(this,SimulationFramework.SIMSPEED));
	}
}
