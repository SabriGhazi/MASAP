package com.masim.emissionAgents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;

public class ModeratePollutionVoting extends TickerBehaviour {

	EmissionUnit _agent;
	public ModeratePollutionVoting(Agent a, long p){
		super(a, p);
		_agent=(EmissionUnit)a;
	}
	@Override
	protected void onTick() {
		
		
		
	}
	


}
