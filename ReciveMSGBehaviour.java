package com.masim.predictionAgents;

import com.masim.core.RunStepMsg;
import com.masim.ui.Console;
import com.masim.utils.SimulationFramework;
import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class ReciveMSGBehaviour extends TickerBehaviour {

	Agent _agent;

	public ReciveMSGBehaviour(Agent a, long period) {
		super(a, period);
		_agent = a;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onTick() {
		ACLMessage msg = _agent.receive();
		if (msg != null) {
			try {
				Object obj = msg.getContentObject();

				if (obj.getClass() == RunStepMsg.class) {
					_agent.addBehaviour(new MakePredictionBehaviour(_agent,
							SimulationFramework.SIMSPEED));
					ACLMessage reply = msg.createReply();
					reply.setSender(_agent.getAID());
					reply.setContent("<StepDone>");
				}
			} catch (UnreadableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
