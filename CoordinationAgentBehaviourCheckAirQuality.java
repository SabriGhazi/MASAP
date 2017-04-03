package com.masim.core;

import com.masim.utils.SimProcessState;
import com.masim.utils.SimulationFramework;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

public class CoordinationAgentBehaviourCheckAirQuality extends TickerBehaviour {

	CoordinatorAgent c_agent;
	public CoordinationAgentBehaviourCheckAirQuality(Agent a, long period) {
		super(a, period);
		c_agent=(CoordinatorAgent)a;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onTick() {
		// Get Prediction about air quality for the next k hours, based on this Prediction
		// Fired the moderate behaviour
		if((SimulationFramework.getCurrentSimProcessState()== SimProcessState.Started)&&
				(SimulationFramework.getCurrentEnv().sim_PM10.getData().size()>1)){
			if(!c_agent.moderationISrenning){
			 	int t=SimulationFramework.getCurrentEnv().T;
			 	double currentPM10Val=SimulationFramework.getCurrentEnv().sim_PM10.getData().get(t);
			 	if(currentPM10Val>100){
			 		c_agent.moderationISrenning=true;
			 		c_agent.addBehaviour(new CoordinationAgentBehaviourSendOrder(c_agent));
			 		
			 	}
			}
		}
	}
}
