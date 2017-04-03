package com.masim.emissionAgents;

import com.masim.gameTheory.PrisonnerDelimmaUtl;
import com.masim.gameTheory.VoteStrategyUtl;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

public class ModeratePollutionGameTheoryPD extends TickerBehaviour {

	EmissionUnit _agent;
	public ModeratePollutionGameTheoryPD(Agent a, long period) {
		super(a, period);
		_agent=(EmissionUnit)a;
	}
	
	protected void onTick() {
        if(PrisonnerDelimmaUtl.getInstance().started)
		_agent.cooperateOrDefect();
	}
}