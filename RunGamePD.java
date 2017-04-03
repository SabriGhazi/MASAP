package com.masim.gameTheory;

import com.masim.core.CooperationStrategy;
import com.masim.ui.Console;
import com.masim.utils.SimulationFramework;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

public class RunGamePD extends TickerBehaviour {
	Agent _agent;

	public RunGamePD(Agent a, long period) {
		super(a, period);
		_agent = a;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onTick() {
		if (SimulationFramework.currentStrategy == CooperationStrategy.NEGOTIATION) {
			if (!PrisonnerDelimmaUtl.getInstance().started)
				_agent.addBehaviour(new StartGameBehavior());
			else {
				if(SimulationFramework.getCurrentEnv().T<=SimulationFramework.getCurrentEnv().getMax_T())
				PrisonnerDelimmaUtl.ComputeRewards();
			}
		}
	}
}