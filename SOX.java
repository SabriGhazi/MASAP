package com.masim.predictionAgents;

import jade.core.Agent;

public class SOX extends Agent {

	public void setup(){
		addBehaviour(new MakeSOXPredictionBehavioir(this));
	}
}
