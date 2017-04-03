package com.masim.emissionAgents;

import com.masim.utils.SimProcessState;
import com.masim.utils.SimulationFramework;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

public class GreedyStrategy extends TickerBehaviour {
	EmissionUnit _agent;
	long period;
	public GreedyStrategy(Agent a, long period) {
		super(a, period);
		_agent=(EmissionUnit)a;
	}

	@Override
	protected void onTick() {
		
		if(SimulationFramework.getCurrentSimProcessState()==SimProcessState.Started){
			
			((SourceConfigInfo) SimulationFramework.sourcesInfo.get(_agent.getLocalName())).resumeEmission();
			
		}

	}

}
