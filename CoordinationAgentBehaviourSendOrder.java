package com.masim.core;

import com.masim.utils.SimulationFramework;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class CoordinationAgentBehaviourSendOrder extends OneShotBehaviour {

	Agent _agnt;
	public CoordinationAgentBehaviourSendOrder(Agent a){
		_agnt=a;
	}
	
	@Override
	public void action() {
		int size=SimulationFramework.sourcesInfo.size();
		
	}// fin de l'action.
}
