package com.masim.emissionAgents;

import com.masim.ui.Console;
import com.masim.utils.SimProcessState;
import com.masim.utils.SimulationFramework;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

/**
 * The pollute Behaviour of the source. 
 * @author Sabri Ghazi
 *
 */
public class ModeratePollutionNAIVE extends TickerBehaviour {

	EmissionUnit _agent;
	long period;
	public ModeratePollutionNAIVE(Agent a, long period) {
		super(a, period);
		_agent=(EmissionUnit)a;
	}

	@Override
	protected void onTick() {

		if(SimulationFramework.getCurrentSimProcessState()==SimProcessState.Started){
			//if a simulation step was already run
			if(SimulationFramework.getCurrentEnv().sim_PM10.getData().size()>1){
				//fire the moderate pollution behaviour.
				_agent.addBehaviour(new NaiveModerationOFPollutionBehaviour(_agent));
			}
		}
	}
}
