package com.masim.qlearning;

import com.masim.emissionAgents.EmissionUnit;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class TrainQleanringBehavior extends OneShotBehaviour{

	EmissionUnit qla;
	 public TrainQleanringBehavior(Agent a) {
		super();
		// TODO Auto-generated constructor stub
		qla=(EmissionUnit)a;
		
	}
	
	@Override
	public void action() {
	qla.Trainnig=true;
	Qlearning.train(qla);
	qla.Trainnig=false;
		
	}
	
	

}
