package com.masim.core;

import java.io.IOException;
import java.rmi.activation.ActivationID;

import com.masim.ui.Console;
import com.masim.utils.SimProcessState;
import com.masim.utils.SimulationFramework;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class TimerBehavior extends TickerBehaviour {

	Agent _agent;
	int recption=0;

	public TimerBehavior(Agent a, long period) {
		super(a, period);
		_agent=a;

	}


	@Override
	protected void onTick() {
		/*if(SimulationFramework.getCurrentSimProcessState()==SimProcessState.Started){
			/*ACLMessage msg= _agent.receive();
			if(msg!=null){
				int t=SimulationFramework.getCurrentEnv().T;
				if(t<SimulationFramework.getCurrentEnv().getMax_T()){
					SimulationFramework.getCurrentEnv().T=t+1;
				}
			}
		}
*/
	}

}
